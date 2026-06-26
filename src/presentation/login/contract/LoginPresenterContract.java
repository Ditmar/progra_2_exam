package presentation.login.contract;

import domain.model.Role;

public interface LoginPresenterContract {
    void onLoginClicked(String username, String password, Role role);
}