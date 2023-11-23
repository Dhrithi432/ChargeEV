package com.evhub.service.security;

import com.evhub.model.domain.user.User;
import com.evhub.model.dto.user.UserDTO;
import com.evhub.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserDTO getUserByUserName(String userName) {
        User user = this.userRepository.findByUserName(userName);
        return new UserDTO(user);
    }
}
