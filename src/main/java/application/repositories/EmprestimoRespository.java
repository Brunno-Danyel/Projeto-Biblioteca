package application.repositories;

import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmprestimoRespository extends JpaRepository<Emprestimo, Long> {


    @Query(value = " select case when (count(1.id)) > 0 then true else false end " + " from Emprestimo e where e.livro =:livro and (e.retornoDoLivro is null or l.retornoDoLivro is false)")
    boolean existsByLivroAndNotRetornoDoLivro(@Param("livro") Livro livro);

    @Query(value = "select e from emprestimo as e join e.livro as l where l.isbn = :isbn or e.clientes =:clientes")
    Page<Emprestimo> findByLivroIsbnOrCliente(@Param("isbn") String isbn, @Param("clientes") String clientes, Pageable pageable);

    Page<Emprestimo> findByLivro(Livro livro, Pageable pageable);
}
