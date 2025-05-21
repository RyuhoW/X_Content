# 必要なパッケージのインストール
required_packages <- c(
  "rmarkdown",
  "knitr",
  "ggplot2",
  "dplyr",
  "tidyr",
  "lubridate",
  "scales",
  "jsonlite",
  "yaml"
)

# パッケージがインストールされていない場合のみインストール
for (package in required_packages) {
  if (!require(package, character.only = TRUE)) {
    install.packages(package, repos = "https://cloud.r-project.org")
  }
}

# renvを使用して環境を固定
if (!require("renv")) {
  install.packages("renv", repos = "https://cloud.r-project.org")
}

# プロジェクト環境の初期化
renv::init()

# 依存関係のスナップショットを作成
renv::snapshot()
