package com.xcontent.service.dashboard;

import com.xcontent.model.dashboard.DashboardData;
import java.util.concurrent.CompletableFuture;

public class DashboardService {
    public CompletableFuture<DashboardData> getInitialData() {
        // TODO: 実際のデータ取得ロジックを実装
        return CompletableFuture.supplyAsync(() -> new DashboardData());
    }
}
