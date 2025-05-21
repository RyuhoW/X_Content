# 詳細設計書

## 1. 概要

X.com Content Generator

## 2. システム構成

以下にシステム全体の構成を示します。

### 2.1 コンポーネント図

以下のPlantUML図は、アプリケーションの全体構成を示しています。

``` plantuml
@startuml
package "X.com Content Generator" {
  [UI (JavaFX)] --> [Controller (Java)]
  [Controller (Java)] --> [Service Layer (Java)]
  [Service Layer (Java)] --> [Repository Layer (Java)]
  [Service Layer (Java)] --> [Machine Learning Module (Python/R)]
  [Repository Layer (Java)] --> [Database (SQLite)]
  [Machine Learning Module (Python/R)] --> [Data Visualization (R)]
}

package "External Systems" {
  [OAuth 2.0 Provider] --> [Controller (Java)]
  [Social Media APIs] --> [Service Layer (Java)]
}
@enduml
```

---

## 3. 重要な設計の詳細

### 3.1 テスト計画の詳細

#### 単体テスト

- 各クラスおよびメソッド単位でのテストを実施。
- 例外的シナリオ（例: 無効な入力、APIエラー）をカバー。
- JUnit 5を利用。

#### 結合テスト

- サービス層とリポジトリ層間、あるいはサービス層と機械学習モジュール間のインターフェースを確認。
- TestFXを使用してUIの結合テストを実施。

#### エンドツーエンドテスト

- UI操作からバックエンド、機械学習モジュール、データベースまでの一連のフローを検証。
- PythonおよびRスクリプトの統合テストを含む。

#### Python/Rスクリプトのテスト

- Pythonスクリプト: pytestを使用してユニットテストと統合テストを実施。
- Rスクリプト: testthatを使用してスクリプトの正確性を確認。

---

### 3.2 データモデルの正規化

- **正規化レベル**: 第3正規形を採用し、冗長性を排除。
- **テーブル設計**:
  - **Users**: ユーザー情報（ID、名前、認証トークン）。
  - **Contents**: コンテンツ情報（ID、テキスト、関連するユーザーID）。
  - **Schedules**: スケジュール情報（ID、投稿日時、関連するコンテンツID）。
- **パフォーマンス要件**: 大規模データセットでのクエリ性能を考慮し、重要な列にインデックスを付加。

---

### 3.3 PythonおよびRスクリプトの統合フロー

REST API経由でPythonおよびRスクリプトを統合。

- **フロー**:
  - JavaバックエンドがPythonまたはRスクリプトを呼び出すREST APIを提供。
  - Pythonスクリプトはエンゲージメント予測を実行。
  - Rスクリプトはデータ視覚化を提供。

#### Pythonスクリプトの統合

- モジュール: `EngagementPredictor.py`
- エンドポイント: `/api/ml/predict`
  - リクエスト: JSON形式で特徴量データを送信。
  - レスポンス: エンゲージメントの予測値を返却。

#### Rスクリプトの統合

- モジュール: `Visualization.R`
- エンドポイント: `/api/ml/visualize`
  - リクエスト: JSON形式でデータセットを送信。
  - レスポンス: プロット画像（Base64エンコード）を返却。

---

## 4. フォルダ構造

以下のフォルダ構造を採用。

``` tree
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── xcontent/
│   │           ├── application/
│   │           ├── config/
│   │           ├── controller/
│   │           ├── model/
│   │           │   ├── analytics/
│   │           │   └── auth/
│   │           ├── repository/
│   │           │   └── impl/
│   │           ├── service/
│   │           │   ├── api/
│   │           │   └── auth/
│   │           └── util/
│   └── resources/
│       ├── db/
│       ├── fxml/
│       ├── images/
│       ├── styles/
│       └── machine_learning/
│           ├── python/
│           │   ├── EngagementPredictor.py
│           │   └── DataPreprocessor.py
│           └── r/
│               └── Visualization.R
```

---

## 5. パフォーマンス要件

- 機械学習モデルの予測処理時間: **1秒以内**。
- データベースクエリの応答時間: **100ms以内**（インデックスの最適化を実施）。

---

## 6. 機械学習モデルの学習データ

- **データ取得方法**: 公開されているSNSデータセットを利用。
- **データクリーニング**:
  - 欠損値の処理。
  - ノイズデータの削除。
- **バイアス除去**:
  - データセットにおける特定の属性（例: 地域、言語）の偏りを緩和。

---

## 7. 依存関係の管理

- **Java**: Mavenで依存関係を管理。
- **Python**: Pipenvで仮想環境と依存関係を管理。
- **R**: renvを利用して依存関係をロック。

---

## 8. デプロイメント戦略

- **ビルドツール**: jlinkおよびjpackageを使用。
- **マルチプラットフォーム対応**:
  - Windows: MSI形式のインストーラーを提供。
  - macOS: DMG形式のインストーラーを提供。
  - Linux: DEB形式のインストーラーを提供。
- **Docker**:
  - バックエンド部分をDockerコンテナ化。
  - PythonおよびRスクリプトも対応。

---

## 9. ログとモニタリングの設計

- **ロギング**:
  - Log4jを利用してエラーログとパフォーマンスログを記録。
  - ログレベル: DEBUG、INFO、WARN、ERROR。

- **モニタリング**:
  - Prometheusを利用してアプリケーションのパフォーマンスを監視。
  - メトリクス例:
    - API応答時間
    - 機械学習モデルの処理時間
    - メモリ使用量

---

## 10. 今後必要な作業

1. **詳細なUIモックアップの作成**

``` plantuml
@startuml
[*] --> Dashboard
Dashboard --> ContentCreation : "Create Content"
Dashboard --> ScheduleManagement : "Manage Schedules"
ContentCreation --> Dashboard : "Back to Dashboard"
ScheduleManagement --> Dashboard : "Back to Dashboard"
@enduml
```

2. **精度検証**
   - 学習済みモデルのリリース時に、実データで精度を検証。
   - 継続的なモデル更新計画を策定。
