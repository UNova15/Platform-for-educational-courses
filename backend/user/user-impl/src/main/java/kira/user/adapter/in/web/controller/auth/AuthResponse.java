package kira.user.adapter.in.web.controller.auth;

public record AuthResponse(long userId, String login, String accessToken) {}
