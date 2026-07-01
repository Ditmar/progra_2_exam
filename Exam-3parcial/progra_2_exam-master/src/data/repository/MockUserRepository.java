package data.repository;

import domain.model.Role;
import domain.model.User;
import domain.repository.UserRepository;
import java.util.List;

public class MockUserRepository implements UserRepository {
    private final List<User> users = List.of(
            new User("admin", "1234", Role.ADMIN),
            new User("cajero", "1234", Role.CASHIER),
            new User("client", "1234", Role.CLIENT));

    @Override
    public User findByCredentials(String username, String password, Role role) {
        return users.stream().filter(u -> u.getUsername().equals(username) &&
                u.getPassword().equals(password) && u.getRole().equals(role)).findFirst().orElse(null);
    }

}
