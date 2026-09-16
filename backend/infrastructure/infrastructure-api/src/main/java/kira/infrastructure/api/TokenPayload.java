package kira.infrastructure.api;

public record TokenPayload(long userId, String login, String role) {}
