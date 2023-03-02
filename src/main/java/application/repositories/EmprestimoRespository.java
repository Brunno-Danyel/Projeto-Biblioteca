package application.repositories;

import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRespository extends JpaRepository<Emprestimo, Long> {


    boolean existsByLivroAndNotRetornoDoLivro(Livro livro);
}
