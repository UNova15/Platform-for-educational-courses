package kira.infrastructure.api;

public interface AccessTokenGenerator {
    String generateAccessToken(long id, String login, String role);
}
