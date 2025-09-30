package mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailClient {

    // Nếu true => chạy mock (no network)
    public static final boolean USE_MOCK = true;

    public static boolean testImapLogin(String imapHost, int imapPort, String username, String password) {
        if (USE_MOCK) {
            // luôn thành công khi mock
            return true;
        }
        try {
            Properties props = new Properties();
            props.put("mail.imap.host", imapHost);
            props.put("mail.imap.port", String.valueOf(imapPort));
            props.put("mail.imap.ssl.enable", "false");

            Session session = Session.getInstance(props);
            Store store = session.getStore("imap");
            store.connect(imapHost, imapPort, username, password);
            store.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Message> fetchInbox(String imapHost, int imapPort, String username, String password) throws Exception {
        if (USE_MOCK) {
            // Tạo danh sách Message giả để hiển thị trên UI
            List<Message> result = new ArrayList<>();
            Session s = Session.getDefaultInstance(new Properties());
            MimeMessage m1 = new MimeMessage(s);
            m1.setFrom(new InternetAddress("alice@example.com"));
            m1.setSubject("Welcome to Secure Mail (mock)");
            m1.setText("Đây là email giả dùng để demo inbox.");
            result.add(m1);

            MimeMessage m2 = new MimeMessage(s);
            m2.setFrom(new InternetAddress("no-reply@service.local"));
            m2.setSubject("Thông báo: Tài khoản của bạn đã được tạo (mock)");
            m2.setText("Nội dung thông báo giả.");
            result.add(m2);

            return result;
        }

        List<Message> result = new ArrayList<>();
        Properties props = new Properties();
        props.put("mail.imap.host", imapHost);
        props.put("mail.imap.port", String.valueOf(imapPort));
        props.put("mail.imap.ssl.enable", "false");
        Session session = Session.getInstance(props);

        Store store = session.getStore("imap");
        store.connect(imapHost, imapPort, username, password);
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);
        Message[] messages = inbox.getMessages();
        for (Message msg : messages) {
            result.add(msg);
        }
        inbox.close(false);
        store.close();
        return result;
    }

    public static void sendMail(String smtpHost, int smtpPort,
                                String username, String password,
                                String from, String to, String subject, String body) throws Exception {
        if (USE_MOCK) {
            // Không gửi thật, chỉ in ra console để debug
            System.out.println("=== MOCK sendMail ===");
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println("Subject: " + subject);
            System.out.println("Body: " + body);
            System.out.println("=====================");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");

        Session session = Session.getInstance(props);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject);
        msg.setText(body, "utf-8");

        Transport transport = session.getTransport("smtp");
        transport.connect(smtpHost, smtpPort, username, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    }
}
