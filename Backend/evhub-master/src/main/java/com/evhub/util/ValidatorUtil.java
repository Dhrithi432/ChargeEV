package com.evhub.util;

import com.evhub.exception.AppsException;
import com.evhub.model.dto.station.ChargingStationDTO;
import com.evhub.model.dto.transaction.TransactionDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;

public class ValidatorUtil {

    public static void validateChargingStationDTO(ChargingStationDTO stationDTO) throws AppsException {
        if (stationDTO.getOwnerID() == null) {
            throw new AppsException("Invalid owner ID");
        }
        if (stationDTO.getVendorID() == null) {
            throw new AppsException("Invalid vendor ID");
        }
        if (stationDTO.getStatus() == null) {
            throw new AppsException("Invalid status");
        }
        if (StringUtils.isEmpty(stationDTO.getLocation())) {
            throw new AppsException("Invalid location");
        }
    }

    public static void validateTransactionDTO(TransactionDTO transactionDTO) throws AppsException {
        if (transactionDTO.getUserID() == null) {
            throw new AppsException("Invalid user ID");
        }
        if (transactionDTO.getChargingStationID() == null) {
            throw new AppsException("Invalid charging station ID");
        }
        if (StringUtils.isEmpty(transactionDTO.getStartDateTimeStr())) {
            throw new AppsException("Invalid start date time");
        }
        if (StringUtils.isEmpty(transactionDTO.getEndDateTimeStr())) {
            throw new AppsException("Invalid end date time");
        }
        if (transactionDTO.getEnergyConsumed() == null
                || transactionDTO.getEnergyConsumed().equals(DecimalCalculator.getDefaultZero())) {
            throw new AppsException("Consumed energy is invalid");
        }
        if (transactionDTO.getTotalCost() == null
                || transactionDTO.getTotalCost().equals(DecimalCalculator.getDefaultZero())) {
            throw new AppsException("Total cost is invalid");
        }
        if (transactionDTO.getPaymentMethod() == null) {
            throw new AppsException("Invalid payment method");
        }
        if (transactionDTO.getChargingType() == null) {
            throw new AppsException("Invalid charging type");
        }
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
