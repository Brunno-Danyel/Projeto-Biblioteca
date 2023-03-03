package application.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    private String clienteEmail;

    @JoinColumn(name = "id_livro")
    @ManyToOne
    private Livro livro;

    private LocalDate dataEmprestimo;

    private boolean retornoDoLivro;
}
