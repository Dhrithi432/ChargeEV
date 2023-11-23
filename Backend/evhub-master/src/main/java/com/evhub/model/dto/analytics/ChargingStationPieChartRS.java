package com.evhub.model.dto.analytics;

import com.evhub.constants.AppsConstants;

import java.util.HashMap;
import java.util.Map;

public class ChargingStationPieChartRS {

    private Map<AppsConstants.Status, Integer> statusWiseChargingStationMap;

    public Map<AppsConstants.Status, Integer> getStatusWiseChargingStationMap() {
        if (statusWiseChargingStationMap == null) {
            statusWiseChargingStationMap = new HashMap<>();
        }
        return statusWiseChargingStationMap;
    }

    public void setStatusWiseChargingStationMap(Map<AppsConstants.Status, Integer> statusWiseChargingStationMap) {
        this.statusWiseChargingStationMap = statusWiseChargingStationMap;
    }
}
