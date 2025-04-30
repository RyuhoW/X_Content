library(plumber)
library(jsonlite)

#* @apiTitle X Content Analytics API
#* @apiDescription API for statistical analysis of X content

#* @param data JSON data containing engagement metrics
#* @post /analyze
function(data) {
  tryCatch({
    # JSONデータをデータフレームに変換
    df <- fromJSON(data)
    
    # 分析の実行
    trends <- analyze_engagement_trends(df)
    ts_analysis <- analyze_time_series(df)
    stats <- perform_statistical_tests(df)
    
    # 結果をJSONで返す
    toJSON(list(
      trends = trends,
      forecast = ts_analysis$forecast,
      statistics = stats
    ))
  }, error = function(e) {
    list(error = e$message)
  })
}

#* @get /health
function() {
  list(status = "healthy")
} 