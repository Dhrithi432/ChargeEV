package com.evhub.model.dto.station;

import java.math.BigDecimal;

public class ChargingStationCapacityRQ {

    private Integer chargingStationID;

    private BigDecimal capacity;

    public Integer getChargingStationID() {
        return chargingStationID;
    }

    public void setChargingStationID(Integer chargingStationID) {
        this.chargingStationID = chargingStationID;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }
}
