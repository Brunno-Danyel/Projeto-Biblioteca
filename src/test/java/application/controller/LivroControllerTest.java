package application.controller;

import application.DTO.LivroDTO;
import application.Exception.BusinessException;
import application.entities.Livro;
import application.service.LivroService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    static String BOOK_API = "/api/livros";

    @Autowired
    MockMvc mvc;

    @MockBean
    LivroService service;

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void salvandoLivroTeste() throws Exception {

        LivroDTO dto = salvandoNovoLivro();

        Livro salvarLivro = Livro.builder().id(1L).autor("Brunno").titulo("A noite estrelada").isbn("12345").build();

        BDDMockito.given(service.save(Mockito.any(Livro.class))).willReturn(salvarLivro);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("titulo").value(dto.getTitulo()))
                .andExpect(MockMvcResultMatchers.jsonPath("autor").value(dto.getAutor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(dto.getIsbn()))
        ;
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficiente para criação do livro")
    public void salvandoLivroInvalidoTeste() throws Exception {


        String json = new ObjectMapper().writeValueAsString(new LivroDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect((MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(3))));
    }

    @Test
    @DisplayName("Deve lançar um erro ao tentar cadastrar livro com isbn já cadastrado")
    public void salvarLivroComIsbnDuplicadoTeste() throws Exception {

        LivroDTO dto = salvandoNovoLivro();
        String json = new ObjectMapper().writeValueAsString(dto);
        String mensagemErro = "Isbn já cadastrado";
        BDDMockito.given(service.save(Mockito.any(Livro.class))).willThrow(new BusinessException(mensagemErro));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(BOOK_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("errors", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("errors[0]").value(mensagemErro));
    }

    @Test
    @DisplayName("Deve obter detalhes de um livro especifico")
    public void obterDetalhesDeUmLivroTeste() throws Exception {

        //Cenario
        Long id = 1l;
        Livro livro = Livro.builder()
                .id(id)
                .titulo(salvandoNovoLivro().getTitulo())
                .autor(salvandoNovoLivro().getAutor())
                .isbn(salvandoNovoLivro().getIsbn())
                .build();

        BDDMockito.given(service.getById(id)).willReturn(Optional.of(livro));

        //execucao
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/" + id))
                .accept(MediaType.APPLICATION_JSON);

        //Verificacao
        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("titulo").value(salvandoNovoLivro().getTitulo()))
                .andExpect(MockMvcResultMatchers.jsonPath("autor").value(salvandoNovoLivro().getAutor()))
                .andExpect(MockMvcResultMatchers.jsonPath("isbn").value(salvandoNovoLivro().getIsbn()));


    }

    @Test
    @DisplayName("Deve retornar um not found quando o livro procurado não existir!")
    public void livroNaoEncontradoTeste() throws Exception {


        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(BOOK_API.concat("/" + 1))
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Deve fazer uma deleção de um livro")
    public void deletarLivroTeste() throws Exception {

        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.of(Livro.builder().id(1l).build()));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("Deve retornar um not found caso o livro para deleção não for encontrado")
    public void deletarLivroExceptionTeste() throws Exception {

        BDDMockito.given(service.getById(Mockito.anyLong())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(BOOK_API.concat("/" + 1));

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private LivroDTO salvandoNovoLivro() {
        return LivroDTO.builder().autor("Brunno").titulo("A noite estrelada").isbn("12345").build();
    }


}
