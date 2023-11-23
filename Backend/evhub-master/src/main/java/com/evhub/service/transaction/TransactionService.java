package com.evhub.service.transaction;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.domain.transaction.Transaction;
import com.evhub.model.domain.user.User;
import com.evhub.model.dto.transaction.TransactionDTO;
import com.evhub.repository.transaction.TransactionRepository;
import com.evhub.service.station.ChargingStationService;
import com.evhub.service.user.UserService;
import com.evhub.util.CalendarUtil;
import com.evhub.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChargingStationService chargingStationService;

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionDTO addTransaction(TransactionDTO transactionRQ) throws AppsException {
        ValidatorUtil.validateTransactionDTO(transactionRQ);

        Transaction transaction = new Transaction();

        User user = this.userService.getUserEntityByID(transactionRQ.getUserID());

        if (user.getUserRole() == AppsConstants.UserRole.USER) {
            Date startDateTime = CalendarUtil.getDefaultParsedDateTime(transactionRQ.getStartDateTimeStr());
            Date endDateTime = CalendarUtil.getDefaultParsedDateTime(transactionRQ.getEndDateTimeStr());

            if (CalendarUtil.isBefore(startDateTime, endDateTime)) {
                ChargingStation chargingStation = this.chargingStationService.getChargingStationEntity(transactionRQ.getChargingStationID());

                if (chargingStation.getStatus() == AppsConstants.Status.ACTIVE) {
                    transaction.setChargingStation(chargingStation);
                    transaction.setUser(user);
                    transaction.setChargingType(transactionRQ.getChargingType());
                    transaction.setPaymentMethod(transactionRQ.getPaymentMethod());


                    transaction.setStartDateTime(startDateTime);
                    transaction.setEndDateTime(endDateTime);

                    transaction.setEnergyConsumed(transactionRQ.getEnergyConsumed());
                    transaction.setTotalCost(transactionRQ.getTotalCost());
                    transaction.setCreatedDate(new Date());

                    transaction = this.transactionRepository.saveAndFlush(transaction);

                    return new TransactionDTO(transaction);
                } else {
                    throw new AppsException("Inactive charging status");
                }
            } else {
                throw new AppsException("Start date time is before End date time");
            }
        } else {
            throw new AppsException("Invalid user");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TransactionDTO getTransactionByID(Integer transactionID) throws AppsException {
        Transaction transaction = this.getTransactionEntity(transactionID);
        return new TransactionDTO(transaction);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    private Transaction getTransactionEntity(Integer transactionID) throws AppsException {
        if (transactionID != null) {
            if (this.transactionRepository.existsById(transactionID)) {
                Transaction transaction = this.transactionRepository.getReferenceById(transactionID);
                return transaction;
            } else {
                throw new AppsException("Transaction is not exists");
            }
        } else {
            throw new AppsException("Invalid transaction ID");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TransactionDTO> getTransactionsByUserID(Integer userID) throws AppsException {
        User user = this.userService.getUserEntityByID(userID);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<Transaction> transactions = this.transactionRepository.findAllByUser(user);

        for (Transaction transaction : transactions) {
            transactionDTOList.add(new TransactionDTO(transaction));
        }

        return transactionDTOList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<TransactionDTO> getTransactionsByStationID(Integer stationID) throws AppsException {
        ChargingStation station = this.chargingStationService.getChargingStationEntity(stationID);
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        List<Transaction> transactions = this.transactionRepository.findAllByChargingStation(station);

        for (Transaction transaction : transactions) {
            transactionDTOList.add(new TransactionDTO(transaction));
        }

        return transactionDTOList;
    }
}
