package com.evhub.repository.charging;

import com.evhub.model.domain.station.ChargingStationCapacity;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@Repository
public class ChargingStationCapacityRepository {

    @Autowired
    private MongoClient client;

    private MongoCollection<ChargingStationCapacity> chargingStationCapacityCollection;

    @PostConstruct
    void init() {
        chargingStationCapacityCollection = client.getDatabase("ev_mdb").getCollection("chargingData", ChargingStationCapacity.class);
    }

    // Save charging capacity to Mongo DB
    public ChargingStationCapacity save(ChargingStationCapacity chargingStationCapacity) {
        chargingStationCapacity.setId(new ObjectId());
        chargingStationCapacityCollection.insertOne(chargingStationCapacity);
        return chargingStationCapacity;
    }

    public List<ChargingStationCapacity> findAll() {
        return chargingStationCapacityCollection.find().into(new ArrayList<>());
    }

    public List<ChargingStationCapacity> findByStationID(Integer chargingStationID) {
        return chargingStationCapacityCollection.find(eq("chargingStationID", chargingStationID)).into(new ArrayList<>());
    }
}
