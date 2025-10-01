package ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import mail.MailClient;
import jakarta.mail.Address;
import jakarta.mail.Message;

import java.util.List;

public class InboxController {

    @FXML private ListView<String> listInbox;

    @FXML
    public void initialize() {
        try {
            List<Message> messages = MailClient.fetchInbox(
                    MainController.getEmail(),
                    MainController.getPassword()
            );

            listInbox.getItems().clear();
            for (Message msg : messages) {
                Address[] froms = msg.getFrom();
                String from = (froms != null && froms.length > 0) ? froms[0].toString() : "Unknown";
                listInbox.getItems().add(from + " - " + msg.getSubject());
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Lỗi tải Inbox");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}
