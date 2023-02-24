package application.repositories;

import application.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    boolean existsByIsbn(String isbn);
}
