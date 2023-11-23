package com.evhub.model.dto.station;

import com.evhub.constants.AppsConstants;
import com.evhub.model.domain.station.ChargingStation;

public class ChargingStationDTO {

    private Integer chargingStationID;

    private Integer vendorID;

    private Integer ownerID;

    private String location;

    private AppsConstants.Status status;

    public ChargingStationDTO() {
    }

    public ChargingStationDTO(ChargingStation chargingStation) {
        this.chargingStationID = chargingStation.getChargingStationID();
        this.vendorID = chargingStation.getVendor().getUserID();
        this.ownerID = chargingStation.getOwner().getUserID();
        this.location = chargingStation.getLocation();
        this.status = chargingStation.getStatus();
    }

    public Integer getChargingStationID() {
        return chargingStationID;
    }

    public void setChargingStationID(Integer chargingStationID) {
        this.chargingStationID = chargingStationID;
    }

    public Integer getVendorID() {
        return vendorID;
    }

    public void setVendorID(Integer vendorID) {
        this.vendorID = vendorID;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(Integer ownerID) {
        this.ownerID = ownerID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
