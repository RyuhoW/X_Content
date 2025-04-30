library(tidyverse)
library(lubridate)
library(forecast)

# データ分析関数
analyze_engagement_trends <- function(data) {
  data %>%
    mutate(
      date = as_datetime(timestamp),
      hour = hour(date),
      weekday = wday(date, label = TRUE)
    ) %>%
    group_by(hour, weekday) %>%
    summarise(
      avg_engagement = mean(engagement_rate),
      total_posts = n(),
      .groups = "drop"
    )
}

# 時系列分析
analyze_time_series <- function(data) {
  ts_data <- ts(data$engagement_rate, frequency = 24)
  model <- auto.arima(ts_data)
  
  list(
    model = model,
    forecast = forecast(model, h = 24),
    seasonal_pattern = decompose(ts_data)
  )
}

# 統計的検定
perform_statistical_tests <- function(data) {
  # A/Bテスト
  ab_test <- t.test(
    engagement_rate ~ group,
    data = data,
    var.equal = FALSE
  )
  
  # 相関分析
  correlation <- cor.test(
    data$content_length,
    data$engagement_rate
  )
  
  list(
    ab_test = ab_test,
    correlation = correlation
  )
} 