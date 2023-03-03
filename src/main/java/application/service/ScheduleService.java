package application.service;

import application.entities.Emprestimo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    private static final String EMPRESTIMOS_ATRASADOS = "0 0 0 1/1 * ?";

    @Value("${application.mail.message}")
    private String messages;

    @Autowired
    private EmprestimoService emprestimoService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = EMPRESTIMOS_ATRASADOS)
    public void enviarEmailEmprestimosAtrasados(){
          List<Emprestimo> todosEmprestimos = emprestimoService.buscarTodosEmprestimosAtrasados();
         List<String> emailList =  todosEmprestimos.stream().map(emprestimos -> emprestimos.getClienteEmail()).collect(Collectors.toList());

         emailService.sendMails(messages, emailList);
    }
}
