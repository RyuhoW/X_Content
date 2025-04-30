import schedule
import time
from datetime import datetime, timedelta
import logging
from typing import Optional

from data_collection.collector import DataCollector
from training.trainer import ModelTrainer
from evaluation.evaluator import ModelEvaluator
from versioning.model_manager import ModelManager

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class AutoTrainer:
    def __init__(self,
                 collector: DataCollector,
                 trainer: ModelTrainer,
                 evaluator: ModelEvaluator,
                 model_manager: ModelManager,
                 performance_threshold: float = 0.7):
        self.collector = collector
        self.trainer = trainer
        self.evaluator = evaluator
        self.model_manager = model_manager
        self.performance_threshold = performance_threshold

    def train_and_evaluate(self) -> Optional[str]:
        """モデルのトレーニングと評価を実行"""
        try:
            # データ収集
            logger.info("Collecting training data...")
            df = self.collector.get_training_data(days=30)
            
            # 特徴量準備
            X, y = self.trainer.prepare_features(df)
            
            # モデルトレーニング
            logger.info("Training model...")
            training_results = self.trainer.train_model(X, y)
            
            # 性能評価
            if training_results['validation_score'] < self.performance_threshold:
                logger.warning(
                    f"Model performance below threshold: "
                    f"{training_results['validation_score']:.3f}"
                )
                return None
            
            # モデルの保存
            version = datetime.now().strftime('v%Y%m%d')
            self.trainer.save_model(version)
            
            # 評価レポートの生成
            self.evaluator.generate_evaluation_report(
                training_results,
                training_results['feature_importance'],
                version
            )
            
            # モデル登録
            self.model_manager.register_model(
                f"models/model_{version}.joblib",
                f"models/scaler_{version}.joblib",
                training_results,
                version
            )
            
            logger.info(f"New model version deployed: {version}")
            return version
        except Exception as e:
            logger.error(f"Auto-training failed: {e}")
            raise

    def schedule_training(self, interval_hours: int = 24):
        """定期的なトレーニングのスケジュール設定"""
        schedule.every(interval_hours).hours.do(self.train_and_evaluate)
        
        while True:
            schedule.run_pending()
            time.sleep(60) 