package istad.co.mobilebankingapi.service;

import istad.co.mobilebankingapi.dto.auth.RegisterRequest;
import istad.co.mobilebankingapi.dto.auth.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    void verify(String userId);
}
