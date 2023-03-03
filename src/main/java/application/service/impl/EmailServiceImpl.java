package application.service.impl;

import application.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${application.mail.default-remetente}")
    private String remetente;

    @Override
    public void sendMails(String messages, List<String> emailList) {
        String[] mails = emailList.toArray(new String[emailList.size()]);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(remetente);
        mailMessage.setSubject("Livro com empréstimo atrasado");
        mailMessage.setText(messages);
        mailMessage.setTo(mails);

        javaMailSender.send(mailMessage);
    }
}
