package ui;

import app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * LoginController demo local.
 */
public class LoginController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Vui lòng nhập đầy đủ email và password (user1/password1 hoặc user2/password2).", Alert.AlertType.WARNING);
            return;
        }

        // Lưu session demo
        MainController.setSession(
                email,
                password,
                "localhost",
                "localhost",
                3025,
                3143
        );

        try {
            MainApp.setRoot("main.fxml");
            System.out.println("Login demo thành công: " + email);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi chuyển màn hình: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
