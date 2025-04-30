import pandas as pd
import numpy as np
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestRegressor
from sklearn.preprocessing import StandardScaler
import joblib
import logging
from datetime import datetime
from typing import Tuple, Dict, Optional

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class ModelTrainer:
    def __init__(self, model_dir: str):
        self.model_dir = model_dir
        self.scaler = StandardScaler()
        self.model = RandomForestRegressor(
            n_estimators=100,
            max_depth=10,
            random_state=42
        )

    def prepare_features(self, df: pd.DataFrame) -> Tuple[np.ndarray, np.ndarray]:
        """特徴量の準備"""
        try:
            features = [
                'media_count',
                'hashtag_count',
                'content_length',
                'post_hour',
                'post_day'
            ]
            
            X = df[features].values
            y = df['engagement_rate'].values
            
            X_scaled = self.scaler.fit_transform(X)
            
            return X_scaled, y
        except Exception as e:
            logger.error(f"Feature preparation failed: {e}")
            raise

    def train_model(self, X: np.ndarray, y: np.ndarray) -> Dict:
        """モデルのトレーニング"""
        try:
            # データの分割
            X_train, X_val, y_train, y_val = train_test_split(
                X, y, test_size=0.2, random_state=42
            )
            
            # モデルのトレーニング
            self.model.fit(X_train, y_train)
            
            # 評価メトリクスの計算
            train_score = self.model.score(X_train, y_train)
            val_score = self.model.score(X_val, y_val)
            
            return {
                'train_score': train_score,
                'validation_score': val_score,
                'feature_importance': dict(zip(
                    self.model.feature_names_in_,
                    self.model.feature_importances_
                ))
            }
        except Exception as e:
            logger.error(f"Model training failed: {e}")
            raise

    def save_model(self, version: str):
        """モデルの保存"""
        try:
            timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
            model_path = f"{self.model_dir}/model_v{version}_{timestamp}.joblib"
            scaler_path = f"{self.model_dir}/scaler_v{version}_{timestamp}.joblib"
            
            joblib.dump(self.model, model_path)
            joblib.dump(self.scaler, scaler_path)
            
            logger.info(f"Model saved: {model_path}")
        except Exception as e:
            logger.error(f"Model saving failed: {e}")
            raise
