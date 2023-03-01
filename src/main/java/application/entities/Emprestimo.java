package application.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Emprestimo {

    private Long id;

    private String cliente;

    private Livro livro;

    private LocalDate dataEmprestimo;

    private boolean retornoDoLivro;
}
