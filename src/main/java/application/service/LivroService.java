package application.service;

import application.entities.Livro;
import org.springframework.stereotype.Service;

import java.util.Optional;



public interface LivroService {


    Livro save(Livro any);

    Optional<Livro> getById(Long id);

    void delete(Livro livro);

    Livro atualizar(Livro livro);

    Optional<Livro> getLivroByIsbn(String isbn);

}
