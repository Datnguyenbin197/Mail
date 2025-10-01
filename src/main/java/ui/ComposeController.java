package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import mail.MailClient;

public class ComposeController {

    @FXML private TextField txtTo;
    @FXML private TextField txtSubject;
    @FXML private TextArea txtBody;

    @FXML
    private void handleSend(ActionEvent event) {
        try {
            MailClient.sendMail(
                    MainController.getEmail(),
                    txtTo.getText(),
                    txtSubject.getText(),
                    txtBody.getText()
            );

            Alert ok = new Alert(Alert.AlertType.INFORMATION);
            ok.setHeaderText(null);
            ok.setContentText("Mail đã gửi thành công!");
            ok.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Gửi mail thất bại");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
