package domain.usecase;

import domain.model.Role;
import domain.model.User;
import domain.repository.UserRepository;

public class LoginUseCase {
    private final UserRepository repository;

    public LoginUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public User execute(String username, String password, Role role) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Nombre de usuario no valido");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password no valido");
        }
        User user = repository.findByCredentials(username, password, role);
        if (user == null) {
            throw new IllegalArgumentException("Credenciales incorrectas");
        }
        return user;
    }
}