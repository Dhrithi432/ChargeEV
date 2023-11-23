package com.evhub.repository.analytics;

import com.evhub.util.DecimalCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AnalyticsJDBCRepository {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Map<String, BigDecimal> getHourlyEnergyConsumedDataMap(Date startDate, Date endDate) {
        final Map<String, BigDecimal> results = new HashMap<>();

        Map<String, Object> params = new HashMap<>();

        StringBuilder SQL = new StringBuilder();

        SQL.append("SELECT DATE_FORMAT(tt.created_date, '%H') AS hour_interval, \n");
        SQL.append("       SUM(tt.energy_consumed) AS count_per_hour \n");
        SQL.append("FROM t_transaction tt \n");
        SQL.append("WHERE tt.created_date BETWEEN :fromDate AND :toDate \n");
        SQL.append("GROUP BY DATE_FORMAT(tt.created_date, '%H') \n");
        SQL.append("ORDER BY hour_interval \n");

        params.put("fromDate", startDate);
        params.put("toDate", endDate);

        namedParameterJdbcTemplate.query(SQL.toString(), params, rs -> {
            while (rs.next()) {
                String hour = rs.getString("hour_interval");

                results.putIfAbsent(hour, DecimalCalculator.getDefaultZero());

                BigDecimal count = results.get(hour);
                results.put(hour, DecimalCalculator.add(rs.getBigDecimal("count_per_hour"), count));

            }
            return results;
        });

        return results;
    }
}
