package com.evhub.repository.transaction;

import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.domain.transaction.Transaction;
import com.evhub.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findAllByChargingStation(ChargingStation chargingStation);

    List<Transaction> findAllByUser(User user);
}
