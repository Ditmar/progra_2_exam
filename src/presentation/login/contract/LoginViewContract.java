package presentation.login.contract;

import domain.model.User;

public interface LoginViewContract {
    void showError(String message);

    void clearError();

    void setLoading(Boolean loading);

    void navigateToDashboard(User user);
}
