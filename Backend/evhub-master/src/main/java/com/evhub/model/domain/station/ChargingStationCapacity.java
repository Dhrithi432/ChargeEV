package com.evhub.model.domain.station;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.bson.types.ObjectId;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChargingStationCapacity {

    private static final long serialVersionUID = 6770198331724216939L;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private Integer chargingStationID;

    private BigDecimal capacity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date capturedDate;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

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

    public Date getCapturedDate() {
        return capturedDate;
    }

    public void setCapturedDate(Date capturedDate) {
        this.capturedDate = capturedDate;
    }
}
