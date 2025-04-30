import numpy as np
import pandas as pd
from sklearn.metrics import mean_squared_error, mean_absolute_error, r2_score
import matplotlib.pyplot as plt
import seaborn as sns
import logging
from typing import Dict, List
import json

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class ModelEvaluator:
    def __init__(self, reports_dir: str):
        self.reports_dir = reports_dir

    def evaluate_model(self, y_true: np.ndarray, y_pred: np.ndarray) -> Dict:
        """モデルの評価メトリクスを計算"""
        try:
            metrics = {
                'mse': mean_squared_error(y_true, y_pred),
                'rmse': np.sqrt(mean_squared_error(y_true, y_pred)),
                'mae': mean_absolute_error(y_true, y_pred),
                'r2': r2_score(y_true, y_pred)
            }
            
            return metrics
        except Exception as e:
            logger.error(f"Model evaluation failed: {e}")
            raise

    def generate_evaluation_report(self, 
                                 metrics: Dict,
                                 feature_importance: Dict,
                                 version: str):
        """評価レポートの生成"""
        try:
            timestamp = pd.Timestamp.now().strftime('%Y%m%d_%H%M%S')
            report_path = f"{self.reports_dir}/evaluation_report_v{version}_{timestamp}.json"
            
            report = {
                'version': version,
                'timestamp': timestamp,
                'metrics': metrics,
                'feature_importance': feature_importance
            }
            
            with open(report_path, 'w') as f:
                json.dump(report, f, indent=4)
            
            logger.info(f"Evaluation report saved: {report_path}")
            return report
        except Exception as e:
            logger.error(f"Report generation failed: {e}")
            raise

    def plot_predictions(self, y_true: np.ndarray, y_pred: np.ndarray, version: str):
        """予測結果の可視化"""
        try:
            plt.figure(figsize=(10, 6))
            plt.scatter(y_true, y_pred, alpha=0.5)
            plt.plot([y_true.min(), y_true.max()], 
                    [y_true.min(), y_true.max()], 
                    'r--', lw=2)
            plt.xlabel('Actual Engagement Rate')
            plt.ylabel('Predicted Engagement Rate')
            plt.title('Prediction vs Actual')
            
            plot_path = f"{self.reports_dir}/prediction_plot_v{version}.png"
            plt.savefig(plot_path)
            plt.close()
            
            logger.info(f"Prediction plot saved: {plot_path}")
        except Exception as e:
            logger.error(f"Plot generation failed: {e}")
            raise
