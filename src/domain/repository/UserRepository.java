package domain.repository;

import domain.model.Role;

import domain.model.User;

public interface UserRepository {
    User findByCredentials(String username, String password, Role role);
}