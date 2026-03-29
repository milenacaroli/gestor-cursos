package br.com.milenacaroli.exception;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered(String email) {
        super("E-mail já cadastrado: " + email);
    }
}
