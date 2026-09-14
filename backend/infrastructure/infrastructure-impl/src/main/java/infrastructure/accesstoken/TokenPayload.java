package infrastructure.accesstoken;

public record TokenPayload(long userId, String login, String role) {}
