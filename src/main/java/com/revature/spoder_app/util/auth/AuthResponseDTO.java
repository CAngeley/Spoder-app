package com.revature.spoder_app.util.auth;

import lombok.Data;

/**
 * This class is used to represent the response that is sent back to the client when they successfully authenticate.
 */
@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
