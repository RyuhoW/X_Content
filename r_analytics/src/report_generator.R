library(rmarkdown)
library(DT)

generate_analysis_report <- function(data, output_file) {
  # 分析の実行
  trends <- analyze_engagement_trends(data)
  ts_analysis <- analyze_time_series(data)
  stats <- perform_statistical_tests(data)
  
  # レポートテンプレート
  report_template <- '
  ---
  title: "Engagement Analysis Report"
  date: "`r format(Sys.time(), "%Y-%m-%d")`"
  output: 
    html_document:
      theme: cosmo
      toc: true
      toc_float: true
  ---
  
  ```{r setup, include=FALSE}
  knitr::opts_chunk$set(echo = FALSE, warning = FALSE, message = FALSE)
  ```
  
  ## Executive Summary
  
  This report provides a comprehensive analysis of engagement patterns.
  
  ## Engagement Trends
  
  ```{r}
  create_engagement_heatmap(trends)
  ```
  
  ## Time Series Analysis
  
  ```{r}
  create_time_series_plot(ts_analysis$forecast)
  ```
  
  ## Statistical Analysis
  
  ### A/B Test Results
  
  ```{r}
  kable(summary(stats$ab_test))
  ```
  
  ### Correlation Analysis
  
  ```{r}
  kable(summary(stats$correlation))
  ```
  '
  
  # レポート生成
  rmarkdown::render(
    input = textConnection(report_template),
    output_file = output_file,
    output_format = "html_document"
  )
} 