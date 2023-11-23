package com.evhub.security;

import com.evhub.model.dto.user.UserDTO;
import com.evhub.model.dto.user.UserLoginRS;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenAuthenticationService {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static void addAuthentication(HttpServletResponse res, UserDTO userDTO, String accessToken, String refreshToken) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON.toString());
        userDTO.setPassword(null);

        UserLoginRS loginRS = new UserLoginRS();

        loginRS.setAccessToken(accessToken);
        loginRS.setRefreshToken(refreshToken);
        loginRS.setUserDTO(userDTO);

        res.getWriter().write(objectMapper.writeValueAsString(loginRS));
    }
}
