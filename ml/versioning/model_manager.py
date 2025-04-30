import os
import json
import shutil
from datetime import datetime
import logging
from typing import Dict, List, Optional
import joblib

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class ModelManager:
    def __init__(self, model_dir: str, archive_dir: str):
        self.model_dir = model_dir
        self.archive_dir = archive_dir
        self.metadata_file = f"{model_dir}/model_metadata.json"
        self._ensure_directories()
        self._load_metadata()

    def _ensure_directories(self):
        """必要なディレクトリの作成"""
        os.makedirs(self.model_dir, exist_ok=True)
        os.makedirs(self.archive_dir, exist_ok=True)

    def _load_metadata(self):
        """メタデータの読み込み"""
        if os.path.exists(self.metadata_file):
            with open(self.metadata_file, 'r') as f:
                self.metadata = json.load(f)
        else:
            self.metadata = {
                'current_version': None,
                'versions': []
            }

    def _save_metadata(self):
        """メタデータの保存"""
        with open(self.metadata_file, 'w') as f:
            json.dump(self.metadata, f, indent=4)

    def register_model(self, 
                      model_path: str, 
                      scaler_path: str,
                      metrics: Dict,
                      version: str) -> str:
        """新しいモデルの登録"""
        try:
            timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
            
            # メタデータの更新
            model_info = {
                'version': version,
                'timestamp': timestamp,
                'model_path': model_path,
                'scaler_path': scaler_path,
                'metrics': metrics
            }
            
            self.metadata['versions'].append(model_info)
            self.metadata['current_version'] = version
            self._save_metadata()
            
            logger.info(f"Model registered: version {version}")
            return version
        except Exception as e:
            logger.error(f"Model registration failed: {e}")
            raise

    def get_current_model(self) -> tuple:
        """現在のモデルの取得"""
        try:
            if not self.metadata['current_version']:
                raise ValueError("No model currently deployed")
            
            current_version = self.metadata['current_version']
            model_info = next(
                v for v in self.metadata['versions'] 
                if v['version'] == current_version
            )
            
            model = joblib.load(model_info['model_path'])
            scaler = joblib.load(model_info['scaler_path'])
            
            return model, scaler
        except Exception as e:
            logger.error(f"Failed to load current model: {e}")
            raise

    def archive_model(self, version: str):
        """古いモデルのアーカイブ"""
        try:
            model_info = next(
                v for v in self.metadata['versions'] 
                if v['version'] == version
            )
            
            # ファイルの移動
            archive_path = f"{self.archive_dir}/v{version}"
            os.makedirs(archive_path, exist_ok=True)
            
            shutil.move(model_info['model_path'], 
                       f"{archive_path}/model.joblib")
            shutil.move(model_info['scaler_path'], 
                       f"{archive_path}/scaler.joblib")
            
            logger.info(f"Model archived: version {version}")
        except Exception as e:
            logger.error(f"Model archiving failed: {e}")
            raise
