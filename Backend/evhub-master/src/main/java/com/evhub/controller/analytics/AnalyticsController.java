package com.evhub.controller.analytics;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.common.ResponseDTO;
import com.evhub.model.dto.analytics.ChargingStationPieChartRS;
import com.evhub.model.dto.analytics.TransactionPerformanceChartRS;
import com.evhub.model.dto.station.ChargingStationDTO;
import com.evhub.service.analytics.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping(value = "/getChargingStationsPieChartData", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<ChargingStationPieChartRS>> getChargingStationsPieChartData() {
        ResponseDTO<ChargingStationPieChartRS> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            ChargingStationPieChartRS pieChartData = this.analyticsService.getChargingStationsPieChartData();

            response.setResult(pieChartData);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getTransactionPerformanceData", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<TransactionPerformanceChartRS>> getTransactionPerformanceData() {
        ResponseDTO<TransactionPerformanceChartRS> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            TransactionPerformanceChartRS performanceChartRS = this.analyticsService.getTransactionPerformanceData();

            response.setResult(performanceChartRS);
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
