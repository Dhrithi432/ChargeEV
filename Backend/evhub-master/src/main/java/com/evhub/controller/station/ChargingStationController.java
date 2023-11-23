package com.evhub.controller.station;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.common.ResponseDTO;
import com.evhub.model.domain.station.ChargingStationCapacity;
import com.evhub.model.dto.station.ChargingStationCapacityRQ;
import com.evhub.model.dto.station.ChargingStationDTO;
import com.evhub.service.station.ChargingStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/station")
public class ChargingStationController {

    @Autowired
    private ChargingStationService chargingStationService;

    @PostMapping(value = "/saveChargingStation", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<ChargingStationDTO>> saveChargingStation(@RequestBody ChargingStationDTO chargingStationDTO) {
        ResponseDTO<ChargingStationDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            ChargingStationDTO stationDTO = this.chargingStationService.saveChargingStation(chargingStationDTO);

            response.setResult(stationDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(value = "/updateChargingStation/{chargingStationID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<ChargingStationDTO>> updateChargingStation(@PathVariable Integer chargingStationID, @RequestBody ChargingStationDTO updateStationDTO) {
        ResponseDTO<ChargingStationDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            ChargingStationDTO stationDTO = this.chargingStationService.updateChargingStation(chargingStationID, updateStationDTO);

            response.setResult(stationDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getChargingStationByID/{chargingStationID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<ChargingStationDTO>> getChargingStationByID(@PathVariable Integer chargingStationID) {
        ResponseDTO<ChargingStationDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            ChargingStationDTO chargingStationDTO = this.chargingStationService.getChargingStationByID(chargingStationID);

            response.setResult(chargingStationDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getAllChargingStations", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<ChargingStationDTO>>> getAllChargingStations() {
        ResponseDTO<List<ChargingStationDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<ChargingStationDTO> chargingStationDTOList = this.chargingStationService.getAllChargingStations();

            response.setResult(chargingStationDTOList);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getChargingStationsByOwnerID/{ownerID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<ChargingStationDTO>>> getChargingStationByOwnerID(@PathVariable Integer ownerID) {
        ResponseDTO<List<ChargingStationDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<ChargingStationDTO> chargingStationDTOList = this.chargingStationService.getChargingStationByOwnerID(ownerID);

            response.setResult(chargingStationDTOList);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getChargingStationsByVendorID/{vendorID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<ChargingStationDTO>>> getChargingStationsByVendorID(@PathVariable Integer vendorID) {
        ResponseDTO<List<ChargingStationDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<ChargingStationDTO> chargingStationDTOList = this.chargingStationService.getChargingStationsByVendorID(vendorID);

            response.setResult(chargingStationDTOList);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/deleteChargingStation/{chargingStationID}")
    public ResponseEntity<ResponseDTO<Boolean>> deleteChargingStation(@PathVariable Integer chargingStationID) {
        ResponseDTO<Boolean> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            this.chargingStationService.deleteChargingStation(chargingStationID);
            response.setResult(true);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.NO_CONTENT;
        } catch (Exception e) {
            response.setResult(false);
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PostMapping(value = "/addStationCapacity", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<String>> addStationCapacity(@RequestBody ChargingStationCapacityRQ capacityRQ) {
        ResponseDTO<String> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            this.chargingStationService.addStationCapacity(capacityRQ);

            response.setResult("SUCCESS");
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getAllCapacitiesByStationID/{stationID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<ChargingStationCapacity>>> getAllCapacitiesByStationID(@PathVariable Integer stationID) {
        ResponseDTO<List<ChargingStationCapacity>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<ChargingStationCapacity> chargingStationCapacities
                    = this.chargingStationService.getAllCapacitiesByStationID(stationID);

            response.setResult(chargingStationCapacities);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getAllChargingStationCapacities", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<ChargingStationCapacity>>> getAllChargingStationCapacities() {
        ResponseDTO<List<ChargingStationCapacity>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<ChargingStationCapacity> chargingStationCapacities
                    = this.chargingStationService.getAllChargingStationCapacities();

            response.setResult(chargingStationCapacities);
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
