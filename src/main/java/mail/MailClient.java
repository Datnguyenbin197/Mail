package mail;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * MailClient demo local (GreenMail)
 */
public class MailClient {

    private static GreenMail greenMail;

    public static void startDemoServer() {
        if (greenMail != null) return;

        ServerSetup smtp = new ServerSetup(3025, null, ServerSetup.PROTOCOL_SMTP);
        ServerSetup imap = new ServerSetup(3143, null, ServerSetup.PROTOCOL_IMAP);
        greenMail = new GreenMail(new ServerSetup[]{smtp, imap});
        greenMail.start();

        greenMail.setUser("user1@localhost", "user1", "password1");
        greenMail.setUser("user2@localhost", "user2", "password2");

        System.out.println("Demo Mail Server started (user1/password1, user2/password2)");
    }

    public static void sendMail(String from, String to, String subject, String body) throws Exception {
        startDemoServer();

        Properties props = new Properties();
        props.put("mail.smtp.host", "localhost");
        props.put("mail.smtp.port", "3025");
        props.put("mail.smtp.auth", "false");

        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setText(body);

        Transport.send(msg);
        System.out.println("Mail sent: " + from + " -> " + to + " [" + subject + "]");
    }

    public static List<Message> fetchInbox(String user, String password) throws Exception {
        startDemoServer();

        Properties props = new Properties();
        props.put("mail.imap.host", "localhost");
        props.put("mail.imap.port", "3143");
        props.put("mail.imap.ssl.enable", "false");

        Session session = Session.getInstance(props);
        Store store = session.getStore("imap");
        store.connect("localhost", user, password);

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();

        List<Message> result = new ArrayList<>();
        for (Message m : messages) result.add(m);

        inbox.close(false);
        store.close();
        return result;
    }
}
