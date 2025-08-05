package istad.co.mobilebankingapi.dto.auth;

public record RegisterRequest(
        String username,
        String email,
        String firstName,
        String lastName,
        String password,
        String confirmedPassword
) {
}
