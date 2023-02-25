package application.controller.service;

import application.Exception.BusinessException;
import application.entities.Livro;
import application.repositories.LivroRepository;
import application.service.LivroService;
import application.service.impl.LivroServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LivroServiceTest {


    LivroService service;

    @MockBean
    LivroRepository livroRepository;

    @BeforeEach
    public void setup() {
        this.service = new LivroServiceImpl(livroRepository);
    }

    @Test
    @DisplayName("Deve salvar um livro")
    public void deveSalvarLivro() {
        //cenario
        Livro livro = livroValido();
        Mockito.when(livroRepository.save(livro)).thenReturn(livro.builder()
                .id(1L)
                .titulo("A noite estrelada")
                .isbn("12345")
                .autor("Brunno Danyel")
                .build());

        //Execucao
        Livro livroSalvo = service.save(livro);

        //Verificação
        assertThat(livroSalvo.getId()).isNotNull();
        assertThat(livroSalvo.getAutor()).isEqualTo("Brunno Danyel");
        assertThat(livroSalvo.getIsbn()).isEqualTo("12345");
        assertThat(livroSalvo.getTitulo()).isEqualTo("A noite estrelada");

    }

    @Test
    @DisplayName("Deve lançar erro de negocio ao tentar salvar um livro com isbn duplicado")
    public void naoSalvarLivroComIsbnDuplicado() {

        //cenario
        Livro livro = livroValido();
        Mockito.when(livroRepository.existsByIsbn(Mockito.anyString())).thenReturn(true);

        //execucao
        Throwable exception = Assertions.catchThrowable(() -> service.save(livro));

        //verificacoes
        assertThat(exception)
                .isInstanceOf(BusinessException.class)
                .hasMessage("Isbn já cadastrado");

        Mockito.verify(livroRepository, Mockito.never()).save(livro);

    }

    @Test
    @DisplayName("Deve obter um livro por id")
    public void getByIdTeste() {

        Long id = 1l;
        Livro livro = livroValido();
        livro.setId(id);
        Mockito.when(livroRepository.findById(id)).thenReturn(Optional.of(livro));

        Optional<Livro> livroEncontrado = service.getById(id);

        assertThat(livroEncontrado.isPresent()).isTrue();
        assertThat(livroEncontrado.get().getId()).isEqualTo(id);
        assertThat(livroEncontrado.get().getAutor()).isEqualTo(livro.getAutor());
        assertThat(livroEncontrado.get().getTitulo()).isEqualTo(livro.getTitulo());
        assertThat(livroEncontrado.get().getIsbn()).isEqualTo(livro.getIsbn());

    }

    @Test
    @DisplayName("Deve retornar vazio ao obter um livro por id quando ele não existe na base")
    public void livroInexistenteTeste() {

        Long id = 1l;
        Mockito.when(livroRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Livro> livro = service.getById(id);

        assertThat(livro.isPresent()).isFalse();

    }

    @Test
    @DisplayName("Deve deletar um livro")
    public void deletarLivroTeste() {

        Livro livro = Livro.builder().id(1l).build();

        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> service.delete(livro));

        Mockito.verify(livroRepository, Mockito.times(1)).delete(livro);

    }

    @Test
    @DisplayName("Deve ocorrer um erro ao tentar deletar um livro inexistente")
    public void deletarLivroInexistenteTeste(){
        Livro livro = new Livro();

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> service.delete(livro));

        Mockito.verify(livroRepository, Mockito.never()).delete(livro);
    }

    private Livro livroValido() {
        return Livro.builder().autor("Brunno Danyel").isbn("12345").titulo("A noite estrelada").build();
    }


}
