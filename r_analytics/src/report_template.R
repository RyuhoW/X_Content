library(rmarkdown)
library(knitr)
library(ggplot2)
library(dplyr)

#' Create a report template
#' @param template_name The name of the template
#' @param data The data to be used in the report
#' @param output_format The output format (pdf, html, docx)
create_report_template <- function(template_name, data, output_format = "html") {
  template_path <- file.path("reports/templates", paste0(template_name, ".Rmd"))
  
  # Basic template structure
  template_content <- sprintf('
---
title: "%s"
date: "`r format(Sys.time(), \'%%Y-%%m-%%d\')`"
output: %s_document
---

```{r setup, include=FALSE}
knitr::opts_chunk$set(echo = FALSE)
```

## Summary
```{r summary}
# Summary statistics
summary_stats <- summary(data)
knitr::kable(summary_stats)
```

## Visualizations
```{r plots}
# Generate plots based on data
ggplot(data, aes(x = date, y = value)) +
  geom_line() +
  theme_minimal() +
  labs(title = "Trend Analysis")
```
', template_name, output_format)

  # Write template to file
  writeLines(template_content, template_path)
  return(template_path)
}

#' Generate report from template
#' @param template_path Path to the template file
#' @param data Data to be used in the report
#' @param output_dir Output directory for the report
#' @param output_format Output format (pdf, html, docx)
generate_report <- function(template_path, data, output_dir, output_format = "html") {
  output_file <- file.path(output_dir, 
                          paste0("report_", format(Sys.time(), "%Y%m%d_%H%M%S"), 
                                ".", output_format))
  
  rmarkdown::render(template_path,
                   output_format = paste0(output_format, "_document"),
                   output_file = output_file,
                   params = list(data = data))
  
  return(output_file)
} 