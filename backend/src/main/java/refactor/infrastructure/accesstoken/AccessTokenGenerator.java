package refactor.infrastructure.accesstoken;

public interface AccessTokenGenerator {
    String generateAccessToken(long id, String login, String role);
}
