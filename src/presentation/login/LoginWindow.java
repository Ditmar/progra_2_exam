package presentation.login;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;

import presentation.components.Image;
import presentation.components.TextField;
import presentation.dashboard.DashboardWindow;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import domain.model.User;
import presentation.login.contract.LoginPresenterContract;
import presentation.login.contract.LoginViewContract;
import infrastructure.theme.AppColors;
import infrastructure.theme.AppCursors;
import infrastructure.theme.AppFonts;
import domain.model.Role;

public class LoginWindow extends JFrame implements LoginViewContract {
    private String title;
    private JPanel leftPanel, rightPanel;
    private LoginPresenterContract presenter;
    private TextField nameText;
    private TextField passwordText;
    private JComboBox<Role> comboBox;
    private JLabel errorLabel;
    private JButton enterButton;

    public LoginWindow(LoginPresenterContract presenter) {
        this.presenter = presenter;
        build();
    }

    private void build() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(this);
        this.setLayout(null);
        this.setVisible(true);
        this.setContainers();
        this.setLabels();
        this.setTextFields();
        this.setComboBox();
        this.setButton();
        this.setCheckBox();
        this.setImages();
    }

    private void setImages() {
        Image image = new Image("/Users/ditmar/progra2.2026/lesson4/example1/resources/images/AhaConvert_homer.jpg");
        image.setSize(new Dimension(300, 300));
        image.setLocation((leftPanel.getWidth() - image.getWidth()) / 2, 200);
        leftPanel.add(image);

    }

    private void setCheckBox() {
        JCheckBox yesForm = new JCheckBox("Si");
        yesForm.setSize(45, 25);
        yesForm.setFocusable(false);
        yesForm.setBackground(AppColors.three);
        yesForm.setLocation((rightPanel.getWidth() - yesForm.getWidth()) / 2, 450);
        rightPanel.add(yesForm);
        JCheckBox noForm = new JCheckBox("No");
        noForm.setSize(60, 25);
        noForm.setFocusable(false);
        noForm.setBackground(AppColors.three);
        noForm.setLocation((rightPanel.getWidth() - noForm.getWidth()) / 2 + 40, 450);
        rightPanel.add(noForm);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(noForm);
        buttonGroup.add(yesForm);
    }

    private void setButton() {
        enterButton = new JButton("Entrar");
        enterButton.setSize(250, 45);
        enterButton.setLocation((rightPanel.getWidth() - enterButton.getWidth()) / 2, 500);
        enterButton.setFocusable(true);
        enterButton.setForeground(AppColors.secondary);
        enterButton.setBackground(AppColors.primary);
        enterButton.setCursor(AppCursors.HAND);
        enterButton.addActionListener(
                e -> presenter.onLoginClicked(this.nameText.getText(), this.passwordText.getText(),
                        (Role) this.comboBox.getSelectedItem()));
        rightPanel.add(enterButton);

    }

    private void setComboBox() {
        this.comboBox = new JComboBox<Role>(Role.values());
        comboBox.setSize(220, 30);
        comboBox.setLocation((rightPanel.getWidth() - comboBox.getWidth()) / 2, 400);
        comboBox.setForeground(Color.DARK_GRAY);
        comboBox.setBackground(Color.WHITE);
        ((JLabel) comboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(comboBox);
    }

    private void setTextFields() {
        this.nameText = new TextField("Nombre de usuario");
        nameText.setLocation((rightPanel.getWidth() - nameText.getWidth()) / 2, 300);
        nameText.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(nameText);

        this.passwordText = new TextField("password");
        passwordText.setSize(260, 40);
        passwordText.setLocation((rightPanel.getWidth() - passwordText.getWidth()) / 2, 350);
        passwordText.setForeground(Color.DARK_GRAY);
        passwordText.setBackground(Color.WHITE);
        passwordText.setCaretColor(Color.BLUE);
        passwordText.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(passwordText);
    }

    private void setLabels() {
        JLabel mainTitle = new JLabel("Kiosko de ventas");
        mainTitle.setBounds(100, 10, 150, 40);
        mainTitle.setForeground(AppColors.three);
        mainTitle.setFont(AppFonts.TITLE_H1);
        leftPanel.add(mainTitle);

        JLabel loginTitle = new JLabel("Login");
        loginTitle.setBounds(200, 10, 150, 40);
        loginTitle.setForeground(AppColors.background);
        loginTitle.setFont(AppFonts.TITLE_H2);
        rightPanel.add(loginTitle);

        JLabel description = new JLabel("Recordar credenciales");
        description.setBounds(200, 200, 150, 40);
        description.setForeground(AppColors.background);
        rightPanel.add(description);

        errorLabel = new JLabel();
        errorLabel.setBounds(200, 550, 150, 40);
        errorLabel.setForeground(AppColors.background);
        rightPanel.add(errorLabel);
    }

    private void setContainers() {
        leftPanel = new JPanel();
        leftPanel.setSize(600, 800);
        leftPanel.setLocation(0, 0);
        leftPanel.setBackground(AppColors.primary);
        rightPanel = new JPanel();
        rightPanel.setSize(500, 800);
        rightPanel.setLocation(600, 0);
        rightPanel.setBackground(AppColors.secondary);
        rightPanel.setLayout(null);
        leftPanel.setLayout(null);
        this.add(leftPanel);
        this.add(rightPanel);
    }

    public void showWindow() {
        this.setVisible(true);
    }

    public void hideWindow() {
        this.setVisible(false);
    }

    public String getTitle() {
        return this.title;
    }

    @Override
    public void showError(String message) {
        errorLabel.setText(message);
    }

    @Override
    public void setLoading(Boolean loading) {
        if (loading) {
            enterButton.setText("Loading...");
            enterButton.setCursor(AppCursors.WAIT);
        } else {
            enterButton.setText("Entrar");
            enterButton.setCursor(null);
        }
    }

    @Override
    public void navigateToDashboard(User user) {
        // JOptionPane.showMessageDialog(null, "Navegar al Dashboard!");
        DashboardWindow dashboard = new DashboardWindow();
    }

    @Override
    public void clearError() {
        errorLabel.setText("");
    }
}