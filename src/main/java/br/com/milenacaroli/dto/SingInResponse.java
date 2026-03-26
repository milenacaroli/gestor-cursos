package br.com.milenacaroli.dto;

public record SingInResponse(
        String token,
        Long expiresIn
) {
}
