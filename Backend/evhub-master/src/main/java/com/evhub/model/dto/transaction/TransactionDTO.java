package com.evhub.model.dto.transaction;

import com.evhub.constants.AppsConstants;
import com.evhub.model.domain.transaction.Transaction;
import com.evhub.util.CalendarUtil;

import java.math.BigDecimal;

public class TransactionDTO {

    private Integer transactionID;

    private Integer userID;

    private Integer chargingStationID;

    private String startDateTimeStr;

    private String endDateTimeStr;

    private BigDecimal energyConsumed;

    private BigDecimal totalCost;

    private AppsConstants.PaymentMethod paymentMethod;

    private AppsConstants.ChargingType chargingType;

    private String createdDateStr;

    public TransactionDTO() {
    }

    public TransactionDTO(Transaction transaction) {
        this.transactionID = transaction.getTransactionID();
        this.userID = transaction.getUser().getUserID();
        this.chargingStationID = transaction.getChargingStation().getChargingStationID();
        this.startDateTimeStr = CalendarUtil.getDefaultFormattedDateTimeMaskNull(transaction.getStartDateTime());
        this.endDateTimeStr = CalendarUtil.getDefaultFormattedDateTimeMaskNull(transaction.getEndDateTime());
        this.energyConsumed = transaction.getEnergyConsumed();
        this.totalCost = transaction.getTotalCost();
        this.paymentMethod = transaction.getPaymentMethod();
        this.chargingType = transaction.getChargingType();
        this.createdDateStr = CalendarUtil.getDefaultFormattedDateTimeMaskNull(transaction.getCreatedDate());
    }

    public Integer getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Integer transactionID) {
        this.transactionID = transactionID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getChargingStationID() {
        return chargingStationID;
    }

    public void setChargingStationID(Integer chargingStationID) {
        this.chargingStationID = chargingStationID;
    }

    public String getStartDateTimeStr() {
        return startDateTimeStr;
    }

    public void setStartDateTimeStr(String startDateTimeStr) {
        this.startDateTimeStr = startDateTimeStr;
    }

    public String getEndDateTimeStr() {
        return endDateTimeStr;
    }

    public void setEndDateTimeStr(String endDateTimeStr) {
        this.endDateTimeStr = endDateTimeStr;
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

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }
}
