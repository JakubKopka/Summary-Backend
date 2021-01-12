package pl.kopka.summary.service;

import com.sun.mail.smtp.SMTPTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kopka.summary.constant.EmailConst;
import pl.kopka.summary.domain.model.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;

@Service
public class EmailService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    public void sendWelcomingEmail(User user) {
        String textMessage = "Hello " + user.getFirstName() + ", \n \n Your new account is created!" + "" + "\n \n The Support Team";
//        sendEmail(user.getEmail(), EmailConst.EMAIL_WELCOMING__SUBJECT, textMessage);
        LOGGER.info(textMessage);
    }

    public void sendNewPasswordEmail(User user, String password) {
        String textMessage = "Hello " + user.getFirstName() + ", \n \n Your new password: " + password + "\n \n The Support Team";
//        sendEmail(user.getEmail(), EmailConst.EMAIL_NEW_PASSWORD_SUBJECT, textMessage);
        LOGGER.info(textMessage);
    }

    public void sendEmail(String email, String subject, String textMessage) {
        try {
            Message message = createEmail(email, subject, textMessage);
            SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(EmailConst.SIMPLE_MAIL_TRANSFER_PROTOCOL);
            smtpTransport.connect(EmailConst.GMAIL_SMTP_SERVER, EmailConst.USERNAME, EmailConst.PASSWORD);
            smtpTransport.sendMessage(message, message.getAllRecipients());
            smtpTransport.close();
        } catch (MessagingException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private Message createEmail(String email, String subject, String messageText) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EmailConst.FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(EmailConst.CC_EMAIL, false));
        message.setSubject(subject);
        message.setText(messageText);
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(EmailConst.SMTP_HOST, EmailConst.GMAIL_SMTP_SERVER);
        properties.put(EmailConst.SMTP_AUTH, true);
        properties.put(EmailConst.SMTP_PORT, EmailConst.DEFAULT_PORT);
        properties.put(EmailConst.SMTP_STARTTLS_ENABLE, true);
        properties.put(EmailConst.SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}
