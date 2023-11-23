package com.evhub.model.dto.analytics;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class TransactionPerformanceChartRS {

    private Map<String, BigDecimal> hourlyEnergyConsumedDataMap;

    public Map<String, BigDecimal> getHourlyEnergyConsumedDataMap() {
        if (hourlyEnergyConsumedDataMap == null) {
            hourlyEnergyConsumedDataMap = new HashMap<>();
        }
        return hourlyEnergyConsumedDataMap;
    }

    public void setHourlyEnergyConsumedDataMap(Map<String, BigDecimal> hourlyEnergyConsumedDataMap) {
        this.hourlyEnergyConsumedDataMap = hourlyEnergyConsumedDataMap;
    }
}
