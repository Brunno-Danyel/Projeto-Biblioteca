package application.repositories;

import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRespository extends JpaRepository<Emprestimo, Long> {


    @Query(value = " select case when (count(1.id)) > 0 then true else false end " + " from Emprestimo e where e.livro =:livro and (e.retornoDoLivro is null or l.retornoDoLivro is false)")
    boolean existsByLivroAndNotRetornoDoLivro(@Param("livro") Livro livro);
}
