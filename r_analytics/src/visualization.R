library(ggplot2)
library(plotly)

# エンゲージメントヒートマップ
create_engagement_heatmap <- function(data) {
  ggplot(data, aes(hour, weekday, fill = avg_engagement)) +
    geom_tile() +
    scale_fill_viridis_c() +
    labs(
      title = "Engagement Rate by Hour and Day",
      x = "Hour of Day",
      y = "Day of Week",
      fill = "Avg. Engagement Rate"
    ) +
    theme_minimal()
}

# 時系列トレンド
create_time_series_plot <- function(forecast_data) {
  autoplot(forecast_data) +
    labs(
      title = "Engagement Rate Forecast",
      x = "Time",
      y = "Engagement Rate"
    ) +
    theme_minimal()
}

# インタラクティブなダッシュボード
create_interactive_dashboard <- function(data) {
  plot_ly(data) %>%
    add_trace(
      x = ~timestamp,
      y = ~engagement_rate,
      type = "scatter",
      mode = "lines+markers",
      name = "Engagement Rate"
    ) %>%
    layout(
      title = "Interactive Engagement Analysis",
      xaxis = list(title = "Time"),
      yaxis = list(title = "Engagement Rate")
    )
} 