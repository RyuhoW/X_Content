import pandas as pd
import sqlite3
from datetime import datetime, timedelta
import logging
from typing import List, Dict, Optional
import json

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class DataCollector:
    def __init__(self, db_path: str):
        self.db_path = db_path
        self.setup_database()

    def setup_database(self):
        """データベースとテーブルの初期設定"""
        try:
            with sqlite3.connect(self.db_path) as conn:
                conn.execute("""
                    CREATE TABLE IF NOT EXISTS training_data (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        content_text TEXT,
                        hashtags TEXT,
                        media_count INTEGER,
                        post_time TEXT,
                        engagement_rate REAL,
                        impressions INTEGER,
                        collected_at TEXT
                    )
                """)
        except Exception as e:
            logger.error(f"Database setup failed: {e}")
            raise

    def collect_data(self, start_date: datetime, end_date: datetime) -> pd.DataFrame:
        """指定期間のデータを収集"""
        try:
            # APIからデータ取得
            raw_data = self._fetch_from_api(start_date, end_date)
            
            # データの前処理
            processed_data = self._preprocess_data(raw_data)
            
            # データベースに保存
            self._save_to_database(processed_data)
            
            return processed_data
        except Exception as e:
            logger.error(f"Data collection failed: {e}")
            raise

    def _fetch_from_api(self, start_date: datetime, end_date: datetime) -> List[Dict]:
        """APIからデータを取得"""
        # TODO: 実際のAPI実装
        return []

    def _preprocess_data(self, raw_data: List[Dict]) -> pd.DataFrame:
        """データの前処理"""
        try:
            df = pd.DataFrame(raw_data)
            
            # 基本的なクリーニング
            df = df.dropna(subset=['content_text', 'engagement_rate'])
            
            # 特徴量エンジニアリング
            df['media_count'] = df['media_urls'].apply(len)
            df['hashtag_count'] = df['hashtags'].apply(len)
            df['content_length'] = df['content_text'].apply(len)
            
            # 時間関連の特徴量
            df['post_hour'] = pd.to_datetime(df['post_time']).dt.hour
            df['post_day'] = pd.to_datetime(df['post_time']).dt.dayofweek
            
            return df
        except Exception as e:
            logger.error(f"Data preprocessing failed: {e}")
            raise

    def _save_to_database(self, df: pd.DataFrame):
        """データをデータベースに保存"""
        try:
            with sqlite3.connect(self.db_path) as conn:
                df.to_sql('training_data', conn, if_exists='append', index=False)
        except Exception as e:
            logger.error(f"Database save failed: {e}")
            raise

    def get_training_data(self, days: int = 30) -> pd.DataFrame:
        """トレーニング用データの取得"""
        try:
            with sqlite3.connect(self.db_path) as conn:
                query = f"""
                    SELECT * FROM training_data
                    WHERE collected_at >= datetime('now', '-{days} days')
                """
                return pd.read_sql_query(query, conn)
        except Exception as e:
            logger.error(f"Failed to get training data: {e}")
            raise
