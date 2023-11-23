package com.evhub.controller.user;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.common.ResponseDTO;
import com.evhub.model.dto.user.UserDTO;
import com.evhub.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/registerUser", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<UserDTO>> registerUser(@RequestBody UserDTO registerUserDTO) {
        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        UserDTO userDTO;
        try {
            userDTO = this.userService.registerUser(registerUserDTO);

            response.setResult(userDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getUserByID/{userID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserByID(@PathVariable Integer userID) {
        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            UserDTO userDTO = this.userService.getUserByID(userID);

            response.setResult(userDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.OK;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @GetMapping(value = "/getAllUsers", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers() {
        ResponseDTO<List<UserDTO>> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            List<UserDTO> userList = this.userService.getAllUsers();

            response.setResult(userList);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.OK;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @PutMapping(value = "/updateUser/{userID}", headers = "Accept=application/json")
    public ResponseEntity<ResponseDTO<UserDTO>> updateUser(@PathVariable Integer userID, @RequestBody UserDTO updateUserDTO) {
        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        UserDTO userDTO;
        try {
            userDTO = this.userService.updateUser(userID, updateUserDTO);

            response.setResult(userDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.CREATED;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/deleteUser/{userID}")
    public ResponseEntity<ResponseDTO<UserDTO>> deleteUser(@PathVariable Integer userID) {
        ResponseDTO<UserDTO> response = new ResponseDTO<>();
        HttpStatus httpStatus;

        try {
            UserDTO userDTO = this.userService.deleteUser(userID);
            response.setResult(userDTO);
            response.setStatus(AppsConstants.ResponseStatus.SUCCESS);
            httpStatus = HttpStatus.OK;
        } catch (AppsException e) {
            response.setStatus(AppsConstants.ResponseStatus.FAILED);
            response.setAppsErrorMessages(e.getAppsErrorMessages());
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return new ResponseEntity<>(response, httpStatus);
    }
}
