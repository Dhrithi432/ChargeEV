package com.evhub.service.station;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.domain.station.ChargingStation;
import com.evhub.model.domain.station.ChargingStationCapacity;
import com.evhub.model.domain.user.User;
import com.evhub.model.dto.station.ChargingStationCapacityRQ;
import com.evhub.model.dto.station.ChargingStationDTO;
import com.evhub.repository.charging.ChargingStationCapacityRepository;
import com.evhub.repository.station.ChargingStationRepository;
import com.evhub.service.user.UserService;
import com.evhub.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ChargingStationService {

    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ChargingStationCapacityRepository chargingStationCapacityRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public ChargingStationDTO saveChargingStation(ChargingStationDTO saveChargingStationDTO) throws AppsException {
        ChargingStation chargingStation = new ChargingStation();

        ValidatorUtil.validateChargingStationDTO(saveChargingStationDTO);

        User vendor = this.userService.getUserEntityByID(saveChargingStationDTO.getVendorID());
        if (vendor.getUserRole() != AppsConstants.UserRole.VENDOR) {
            throw new AppsException("Invalid vendor");
        }

        User owner = this.userService.getUserEntityByID(saveChargingStationDTO.getOwnerID());
        if (owner.getUserRole() != AppsConstants.UserRole.OWNER) {
            throw new AppsException("Invalid owner");
        }

        List<ChargingStation> chargingStations = this.chargingStationRepository.findAllByOwner(owner);

        if (ValidatorUtil.isEmpty(chargingStations)) {
            chargingStation.setVendor(vendor);
            chargingStation.setOwner(owner);
            chargingStation.setLocation(saveChargingStationDTO.getLocation());
            chargingStation.setStatus(AppsConstants.Status.ACTIVE);

            chargingStation = this.chargingStationRepository.saveAndFlush(chargingStation);

            return new ChargingStationDTO(chargingStation);
        } else {
            throw new AppsException("This owner already exists in another Charging Station");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ChargingStationDTO updateChargingStation(Integer chargingStationID, ChargingStationDTO updateStationDTO) throws AppsException {
        ChargingStation chargingStation = this.getChargingStationEntity(chargingStationID);

        if (updateStationDTO.getVendorID() != null) {
            User vendor = this.userService.getUserEntityByID(updateStationDTO.getVendorID());

            if (vendor.getUserRole() != AppsConstants.UserRole.VENDOR) {
                throw new AppsException("Invalid vendor");
            }

            chargingStation.setVendor(vendor);
        }

        if (updateStationDTO.getOwnerID() != null) {
            User owner = this.userService.getUserEntityByID(updateStationDTO.getOwnerID());

            if (owner.getUserRole() != AppsConstants.UserRole.OWNER) {
                throw new AppsException("Invalid owner");
            }

            if (!Objects.equals(chargingStation.getOwner().getUserID(), owner.getUserID())) {
                List<ChargingStation> chargingStations = this.chargingStationRepository.findAllByOwner(owner);
                if (!ValidatorUtil.isEmpty(chargingStations)) {
                    throw new AppsException("This owner already exists in another Charging Station");
                }
            }

            chargingStation.setOwner(owner);
        }

        if (!StringUtils.isEmpty(updateStationDTO.getLocation())) {
            chargingStation.setLocation(updateStationDTO.getLocation());
        }

        if (updateStationDTO.getStatus() != null) {
            chargingStation.setStatus(updateStationDTO.getStatus());
        }

        chargingStation = this.chargingStationRepository.saveAndFlush(chargingStation);

        return new ChargingStationDTO(chargingStation);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ChargingStation getChargingStationEntity(Integer chargingStationID) throws AppsException {
        if (chargingStationID != null) {
            if (this.chargingStationRepository.existsById(chargingStationID)) {
                ChargingStation station = this.chargingStationRepository.getReferenceById(chargingStationID);
                return station;
            } else {
                throw new AppsException("Can not find charging station in the database");
            }
        } else {
            throw new AppsException("Invalid charging station ID");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public ChargingStationDTO getChargingStationByID(Integer chargingStationID) throws AppsException {
        ChargingStation chargingStation = this.getChargingStationEntity(chargingStationID);
        return new ChargingStationDTO(chargingStation);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChargingStationDTO> getAllChargingStations() throws AppsException {
        final List<ChargingStationDTO> chargingStationDTOList = new ArrayList<>();
        List<ChargingStation> chargingStations = this.chargingStationRepository.findAll();

        for (ChargingStation station : chargingStations) {
            chargingStationDTOList.add(new ChargingStationDTO(station));
        }

        return chargingStationDTOList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChargingStationDTO> getChargingStationByOwnerID(Integer ownerID) throws AppsException {
        User owner = this.userService.getUserEntityByID(ownerID);

        final List<ChargingStationDTO> chargingStationDTOList = new ArrayList<>();
        List<ChargingStation> chargingStations = this.chargingStationRepository.findAllByOwner(owner);

        for (ChargingStation station : chargingStations) {
            chargingStationDTOList.add(new ChargingStationDTO(station));
        }

        return chargingStationDTOList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChargingStationDTO> getChargingStationsByVendorID(Integer vendorID) throws AppsException {
        User vendor = this.userService.getUserEntityByID(vendorID);

        final List<ChargingStationDTO> chargingStationDTOList = new ArrayList<>();
        List<ChargingStation> chargingStations = this.chargingStationRepository.findAllByVendor(vendor);

        for (ChargingStation station : chargingStations) {
            chargingStationDTOList.add(new ChargingStationDTO(station));
        }

        return chargingStationDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteChargingStation(Integer chargingStationID) throws AppsException {
        ChargingStation chargingStation = this.getChargingStationEntity(chargingStationID);
        this.chargingStationRepository.delete(chargingStation);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = AppsException.class)
    public void addStationCapacity(ChargingStationCapacityRQ capacityRQ) throws AppsException {
        Date date = new Date();

        ChargingStationCapacity capacity = new ChargingStationCapacity();

        capacity.setChargingStationID(capacityRQ.getChargingStationID());
        capacity.setCapacity(capacityRQ.getCapacity());
        capacity.setCapturedDate(date);

        this.chargingStationCapacityRepository.save(capacity);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChargingStationCapacity> getAllChargingStationCapacities() throws AppsException {
        return this.chargingStationCapacityRepository.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ChargingStationCapacity> getAllCapacitiesByStationID(Integer stationID) throws AppsException {
        ChargingStation chargingStation = this.getChargingStationEntity(stationID);

        return this.chargingStationCapacityRepository.findByStationID(stationID);
    }
}
