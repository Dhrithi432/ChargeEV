package com.evhub.repository.station;

import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargingStationRepository extends JpaRepository<ChargingStation, Integer> {

    List<ChargingStation> findAllByOwner(User owner);

    List<ChargingStation> findAllByVendor(User vendor);
}
