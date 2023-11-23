package com.evhub.controller.transaction;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.common.ResponseDTO;
import com.evhub.model.dto.transaction.TransactionDTO;
import com.evhub.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(value = "/addTransaction", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<TransactionDTO>> addTransaction(@RequestBody TransactionDTO transactionRQ) {
        ResponseDTO<TransactionDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            TransactionDTO transactionDTO = this.transactionService.addTransaction(transactionRQ);

            response.setResult(transactionDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getTransactionByID/{transactionID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<TransactionDTO>> getTransactionByID(@PathVariable Integer transactionID) {
        ResponseDTO<TransactionDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            TransactionDTO transactionDTO = this.transactionService.getTransactionByID(transactionID);

            response.setResult(transactionDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getTransactionsByUserID/{userID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<TransactionDTO>>> getTransactionsByUserID(@PathVariable Integer userID) {
        ResponseDTO<List<TransactionDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<TransactionDTO> transactions = this.transactionService.getTransactionsByUserID(userID);

            response.setResult(transactions);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getTransactionsByStationID/{stationID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<TransactionDTO>>> getTransactionsByStationID(@PathVariable Integer stationID) {
        ResponseDTO<List<TransactionDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<TransactionDTO> transactions = this.transactionService.getTransactionsByStationID(stationID);

            response.setResult(transactions);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
