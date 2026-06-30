package presentation.login;

import domain.model.Role;
import domain.model.User;
import domain.usecase.LoginUseCase;
import presentation.login.contract.LoginPresenterContract;
import presentation.login.contract.LoginViewContract;

public class HandlerLoginWindow implements LoginPresenterContract {

    private LoginUseCase loginUseCase;
    private LoginViewContract view;

    public HandlerLoginWindow(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void attach(LoginViewContract view) {
        this.view = view;
    }

    @Override
    public void onLoginClicked(String username, String password, Role role) {
        this.view.clearError();
        this.view.setLoading(true);
        try {
            User user = this.loginUseCase.execute(username, password, role);
            this.view.navigateToDashboard(user);
        } catch (IllegalArgumentException e) {
            this.view.showError(e.getMessage());
        } finally {
            this.view.setLoading(false);
        }
    }

}
