package com.evhub.model.domain.transaction;

import com.evhub.constants.AppsConstants;
import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.domain.user.User;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "t_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "transaction_id")
    private Integer transactionID;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "charging_station_id", nullable = false)
    private ChargingStation chargingStation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date_time", nullable = false)
    private Date startDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date_time", nullable = false)
    private Date endDateTime;

    //Energy Consumed (kWh)
    @Column(name = "energy_consumed", nullable = false)
    private BigDecimal energyConsumed;

    @Column(name = "total_cost", nullable = false)
    private BigDecimal totalCost;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private AppsConstants.PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "charging_type", nullable = false)
    private AppsConstants.ChargingType chargingType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChargingStation getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(ChargingStation chargingStation) {
        this.chargingStation = chargingStation;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public BigDecimal getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(BigDecimal energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public AppsConstants.PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(AppsConstants.PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public AppsConstants.ChargingType getChargingType() {
        return chargingType;
    }

    public void setChargingType(AppsConstants.ChargingType chargingType) {
        this.chargingType = chargingType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
