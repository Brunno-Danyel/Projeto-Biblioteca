package application.service;

import java.util.List;

public interface EmailService {


    void sendMails(String messages, List<String> emailList);

}
