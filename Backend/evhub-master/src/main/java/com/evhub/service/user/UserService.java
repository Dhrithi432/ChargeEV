package com.evhub.service.user;

import com.evhub.constants.AppsConstants;
import com.evhub.exception.AppsException;
import com.evhub.model.domain.user.User;
import com.evhub.model.dto.user.UserDTO;
import com.evhub.repository.user.UserRepository;
import com.evhub.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUserName(username);

        if (user == null) {
            LOG.error("ERROR : User not found in database : {}", username);
            throw new UsernameNotFoundException("User not found in database");
        } else {
            LOG.info("INFO : User found in database : {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole().getDescription()));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO registerUser(UserDTO registerUserDTO) throws AppsException {
        // Validate user DTO
        this.validateUserDTO(registerUserDTO);

        User user = this.userRepository.findByUserName(registerUserDTO.getUserName());

        if (user != null) {
            LOG.error("ERROR : User already exists : {}", user.getPhoneNumber());
            throw new AppsException("User already exits");
        } else {
            user = new User();

            user.setUserName(registerUserDTO.getUserName());
            user.setPhoneNumber(registerUserDTO.getPhoneNumber());
            user.setFirstName(registerUserDTO.getFirstName());
            user.setLastName(registerUserDTO.getLastName());
            user.setUserRole(registerUserDTO.getUserRole());
            user.setEmail(registerUserDTO.getEmail());
            user.setStatus(AppsConstants.Status.ACTIVE);

            user.setPassword(this.generateEncodedPassword(registerUserDTO.getPassword()));

            user = this.userRepository.saveAndFlush(user);

            return new UserDTO(user);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO getUserByID(Integer userID) throws AppsException {
        User user = this.getUserEntityByID(userID);
        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserEntityByID(Integer userID) throws AppsException {
        if (userID != null) {
            if (this.userRepository.existsById(userID)) {
                User user = this.userRepository.getReferenceById(userID);
                return user;
            } else {
                throw new AppsException("Can not find user in the database");
            }
        } else {
            throw new AppsException("Invalid user ID");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> getAllUsers() throws AppsException {
        List<UserDTO> userDTOList = new ArrayList<>();
        List<User> userList = this.userRepository.findAll();

        for (User user : userList) {
            userDTOList.add(new UserDTO(user));
        }

        return userDTOList;
    }

    public String generateEncodedPassword(String plainPassword) throws AppsException {
        return PasswordUtil.generateEncodedPassword(new BCryptPasswordEncoder(), plainPassword);
    }

    private void validateUserDTO(UserDTO userDTO) throws AppsException {
        if (StringUtils.isEmpty(userDTO.getUserName())) {
            throw new AppsException("User name is not valid");
        }
        if (StringUtils.isEmpty(userDTO.getEmail())) {
            throw new AppsException("User email is not valid");
        }
        if (StringUtils.isEmpty(userDTO.getFirstName())) {
            throw new AppsException("User first name is not valid");
        }
        if (StringUtils.isEmpty(userDTO.getLastName())) {
            throw new AppsException("User last name is not valid");
        }
        if (StringUtils.isEmpty(userDTO.getPhoneNumber())) {
            throw new AppsException("User phone number is not valid");
        }
        if (userDTO.getUserRole() == null) {
            throw new AppsException("User role is not valid");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO updateUser(Integer userID, UserDTO updateUserDTO) throws AppsException {
        User user = this.getUserEntityByID(userID);

        if (!StringUtils.isEmpty(updateUserDTO.getUserName())) {
            User userByUserName = this.userRepository.findByUserName(updateUserDTO.getUserName());

            if (user.getUserID().equals(userByUserName.getUserID())) {
                user.setUserName(updateUserDTO.getUserName());
            } else {
                throw new AppsException("User name is already exists");
            }
        }

        if (!StringUtils.isEmpty(updateUserDTO.getFirstName())) {
            user.setFirstName(updateUserDTO.getFirstName());
        }
        if (!StringUtils.isEmpty(updateUserDTO.getLastName())) {
            user.setLastName(updateUserDTO.getLastName());
        }
        if (!StringUtils.isEmpty(updateUserDTO.getPhoneNumber())) {
            user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }
        if (!StringUtils.isEmpty(updateUserDTO.getEmail())) {
            user.setEmail(updateUserDTO.getEmail());
        }

        user = this.userRepository.saveAndFlush(user);

        return new UserDTO(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO deleteUser(Integer userID) throws AppsException {
        User user = this.getUserEntityByID(userID);

        if (user.getStatus() == AppsConstants.Status.INACTIVE) {
            throw new AppsException("User name is already deleted");
        }

        user.setStatus(AppsConstants.Status.INACTIVE);
        user = this.userRepository.saveAndFlush(user);

        return new UserDTO(user);
    }
}
