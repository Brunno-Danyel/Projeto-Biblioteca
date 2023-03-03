package application.repositories;

import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EmprestimoRespository extends JpaRepository<Emprestimo, Long> {


    @Query(value = " select case when (count(e.id) > 0) then true else false end " + " from Emprestimo e where e.livro =:livro and (e.retornoDoLivro is null or e.retornoDoLivro is false)")
    boolean existsByLivroAndNotRetornoDoLivro(@Param("livro") Livro livro);

    @Query(value = "select e from Emprestimo as e join e.livro as l where l.isbn = :isbn or e.cliente =:cliente")
    Page<Emprestimo> findByLivroIsbnOrCliente(@Param("isbn") String isbn, @Param("cliente") String cliente, Pageable pageable);

    Page<Emprestimo> findByLivro(Livro livro, Pageable pageable);
}
