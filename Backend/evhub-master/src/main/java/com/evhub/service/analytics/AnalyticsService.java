package com.evhub.service.analytics;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.dto.analytics.ChargingStationPieChartRS;
import com.evhub.model.dto.analytics.TransactionPerformanceChartRS;
import com.evhub.repository.analytics.AnalyticsJDBCRepository;
import com.evhub.repository.station.ChargingStationRepository;
import com.evhub.util.CalendarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {

    @Autowired
    private AnalyticsJDBCRepository analyticsJDBCRepository;

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Transactional(propagation = Propagation.SUPPORTS)
    public ChargingStationPieChartRS getChargingStationsPieChartData() throws AppsException {
        Map<AppsConstants.Status, Integer> statusWiseChargingStationMap = new HashMap<>();

        List<ChargingStation> chargingStations = this.chargingStationRepository.findAll();

        for (ChargingStation station : chargingStations) {
            statusWiseChargingStationMap.putIfAbsent(station.getStatus(), 0);

            int count = statusWiseChargingStationMap.get(station.getStatus());
            statusWiseChargingStationMap.put(station.getStatus(), count + 1);
        }

        ChargingStationPieChartRS pieChartRS = new ChargingStationPieChartRS();
        pieChartRS.setStatusWiseChargingStationMap(statusWiseChargingStationMap);

        return pieChartRS;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public TransactionPerformanceChartRS getTransactionPerformanceData() throws AppsException {
        Date now = new Date();
        String nowStr = CalendarUtil.getDefaultFormattedDateMaskNull(now);

        Date startDate = CalendarUtil.getParsedStartDateTime(nowStr);
        Date endDate = CalendarUtil.getParsedEndDateTime(nowStr);

        Map<String, BigDecimal> hourlyEnergyConsumedDataMap = this.analyticsJDBCRepository.getHourlyEnergyConsumedDataMap(startDate, endDate);

        TransactionPerformanceChartRS performanceChartRS = new TransactionPerformanceChartRS();
        performanceChartRS.setHourlyEnergyConsumedDataMap(hourlyEnergyConsumedDataMap);

        return performanceChartRS;
    }
}
