from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from typing import List, Optional
import numpy as np
from ..preprocessing.data_processor import DataProcessor
from ..models.engagement_predictor import EngagementPredictor
import logging

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI()

class Content(BaseModel):
    text: str
    hashtags: List[str] = []
    media_urls: List[str] = []
    scheduled_time: str
    
class PredictionResponse(BaseModel):
    engagement_score: float
    confidence: float

# シングルトンとしてモデルとプロセッサーを保持
processor = DataProcessor()
predictor = EngagementPredictor()

@app.post("/predict", response_model=PredictionResponse)
async def predict_engagement(content: Content):
    try:
        # 特徴量抽出
        features = processor.extract_features(content.dict())
        
        # 予測
        prediction = predictor.predict(features.reshape(1, -1))[0]
        
        return PredictionResponse(
            engagement_score=float(prediction),
            confidence=0.95  # TODO: 信頼度の計算を実装
        )
    except Exception as e:
        logger.error(f"Error in prediction API: {e}")
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/health")
async def health_check():
    return {"status": "healthy"} 