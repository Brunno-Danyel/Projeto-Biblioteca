package application.service;

import application.entities.Livro;

import java.util.Optional;


public interface LivroService {


    Livro save(Livro any);

    Optional<Livro> getById(Long id);

    void delete(Livro livro);

    Livro atualizar(Livro livro);
}
