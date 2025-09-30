package ui;

import app.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import util.Config;

/**
 * LoginController: kiểm tra cú pháp email.
 * Nếu hợp lệ -> lưu session vào MainController và chuyển màn hình.
 * Host/port cố định trong Config (Gmail).
 */
public class LoginController {
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;

    private static final boolean USE_INTERNETADDRESS_VALIDATION = true;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText() == null ? "" : txtEmail.getText().trim();
        String password = txtPassword.getText() == null ? "" : txtPassword.getText();

        if (email.isEmpty()) {
            showAlert("Vui lòng nhập email.", Alert.AlertType.WARNING);
            return;
        }

        if (!isValidEmail(email)) {
            showAlert("Email không hợp lệ. Vui lòng nhập đúng định dạng (vd: user@gmail.com).", Alert.AlertType.ERROR);
            return;
        }

        if (password.isEmpty()) {
            showAlert("Vui lòng nhập App Password (16 ký tự).", Alert.AlertType.WARNING);
            return;
        }

        try {
            // Lưu session với Gmail config
            MainController.setSession(
                    email,
                    password,
                    Config.SMTP_HOST,
                    Config.IMAP_HOST,
                    Config.SMTP_PORT,
                    Config.IMAP_PORT
            );

            MainApp.setRoot("main.fxml");
            System.out.println("Login thành công: " + email);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Lỗi khi chuyển màn hình: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean isValidEmail(String email) {
        if (USE_INTERNETADDRESS_VALIDATION) {
            try {
                InternetAddress ia = new InternetAddress(email);
                ia.validate(); // sẽ ném AddressException nếu sai định dạng
                return true;
            } catch (AddressException ex) {
                return false;
            }
        } else {
            String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
            return email.matches(regex);
        }
    }

    private void showAlert(String msg, Alert.AlertType type) {
        Alert a = new Alert(type);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
