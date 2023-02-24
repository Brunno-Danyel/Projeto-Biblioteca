package application.controller.repositories;

import application.entities.Livro;
import application.repositories.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class LivroRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    LivroRepository livroRepository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um livro na base com o Isbn informado")
    public void retornaVerdadeiroIsbnExistente() {

        //cenario
        String isbn = "123";
        Livro livro = Livro.builder().autor("Brunno Danyel").isbn(isbn).titulo("A noite estrelada").build();
        entityManager.persist(livro);

        //execucao
        boolean existente = livroRepository.existsByIsbn(isbn);

        //verificacao
        assertThat(existente).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um livro na base com o Isbn informado")
    public void retornaFalsoIsbnInexistente() {

        //cenario
        String isbn = "123";

        //execucao
        boolean existente = livroRepository.existsByIsbn(isbn);

        //verificacao
        assertThat(existente).isFalse();
    }
}
