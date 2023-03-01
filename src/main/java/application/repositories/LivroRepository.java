package application.repositories;

import application.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Book;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Override
    Optional<Livro> findById(Long aLong);

    Optional<Livro> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);
}
