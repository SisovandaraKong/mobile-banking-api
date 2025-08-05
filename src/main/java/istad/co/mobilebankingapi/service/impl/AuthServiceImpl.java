package istad.co.mobilebankingapi.service.impl;

import istad.co.mobilebankingapi.dto.auth.RegisterRequest;
import istad.co.mobilebankingapi.dto.auth.RegisterResponse;
import istad.co.mobilebankingapi.service.AuthService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        if (!registerRequest.password().equals(registerRequest.confirmedPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerRequest.username());
        user.setEmail(registerRequest.email());
        user.setFirstName(registerRequest.firstName());
        user.setLastName(registerRequest.lastName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerRequest.password());
        user.setCredentials(List.of(credential));

        user.setEmailVerified(false);
        user.setEnabled(true);

        try (Response response = keycloak.realm(realm)
                .users()
                .create(user)) {
            if (response.getStatus() == HttpStatus.CREATED.value()) {

                // Verify email
                List<UserRepresentation> userVerification = keycloak.realm(realm)
                        .users()
                        .search(user.getUsername(),true);
                userVerification.stream()
                        .filter(userRepresentation ->
                                userRepresentation.isEmailVerified() == false)
                        .findFirst()
                        .ifPresent(userRepresentation ->
                                verify(userRepresentation.getId()));

                return RegisterResponse.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.valueOf(response.getStatus()),"Failed to create user");
        }
    }

    @Override
    public void verify(String userId) {
        UserResource userResource = keycloak.realm(realm)
                .users()
                .get(userId);
        userResource.sendVerifyEmail();
    }
}
