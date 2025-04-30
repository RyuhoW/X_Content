import pandas as pd
import numpy as np
from sklearn.preprocessing import StandardScaler
from typing import Tuple, List, Dict
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class DataProcessor:
    def __init__(self):
        self.scaler = StandardScaler()
        
    def preprocess_text(self, text: str) -> str:
        """テキストの前処理を行う"""
        try:
            # 小文字化
            text = text.lower()
            # 基本的なクリーニング
            # TODO: より高度なテキスト前処理を実装
            return text
        except Exception as e:
            logger.error(f"Error in text preprocessing: {e}")
            raise

    def extract_features(self, content: Dict) -> np.ndarray:
        """コンテンツから特徴量を抽出"""
        try:
            features = []
            # テキスト長
            features.append(len(content.get('text', '')))
            # ハッシュタグ数
            features.append(len(content.get('hashtags', [])))
            # メディア数
            features.append(len(content.get('media_urls', [])))
            # 投稿時間の特徴量
            post_time = pd.to_datetime(content.get('scheduled_time'))
            features.extend([
                post_time.hour,
                post_time.weekday(),
                post_time.month
            ])
            return np.array(features)
        except Exception as e:
            logger.error(f"Error in feature extraction: {e}")
            raise

    def prepare_data(self, 
                    contents: List[Dict], 
                    labels: List[float]) -> Tuple[np.ndarray, np.ndarray]:
        """データセットの準備"""
        try:
            X = np.array([self.extract_features(content) for content in contents])
            y = np.array(labels)
            
            # 特徴量のスケーリング
            X_scaled = self.scaler.fit_transform(X)
            
            return X_scaled, y
        except Exception as e:
            logger.error(f"Error in data preparation: {e}")
            raise 