package application.controller;

import application.DTO.EmprestimoDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import application.service.EmprestimoService;
import application.service.LivroService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@RestController
@RequestMapping("api/emprestimo")
@AllArgsConstructor
public class EmprestimoController {

    private LivroService livroService;

    private EmprestimoService emprestimoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        Livro livro = livroService.getLivroByIsbn(emprestimoDTO.getIsbn())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado!"));
        Emprestimo emprestimo = Emprestimo.builder()
                .livro(livro)
                .dataEmprestimo(LocalDate.now())
                .cliente(emprestimoDTO.getCliente())
                .build();

        emprestimoService.save(emprestimo);
        return emprestimo.getId();
    }
}
