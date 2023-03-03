package application.controller;

import application.dto.EmprestimoDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import application.service.EmprestimoService;
import application.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = EmprestimoController.class)
@AutoConfigureMockMvc
public class EmprestimoControllerTest {

    static final String EMPRESTIMO_API = "/api/emprestimo";

    @Autowired
    MockMvc mvc;

    @MockBean
    private LivroService livroService;

    @MockBean
    private EmprestimoService emprestimoService;

    @Test
    @DisplayName("Deve realizar um emprestimo")
    public void deveRealizarEmprestimo() throws Exception {

        EmprestimoDTO dto = EmprestimoDTO.builder().isbn("123").cliente("Fulano").build();
        String json = new ObjectMapper().writeValueAsString(dto);

        Livro livro = Livro.builder().id(1l).isbn("123").build();
        BDDMockito.given( livroService.getLivroByIsbn("123") ).willReturn(Optional.of(livro) );

        Emprestimo loan = Emprestimo.builder().id(1l).cliente("Fulano").livro(livro).dataEmprestimo(LocalDate.now()).build();
        BDDMockito.given( emprestimoService.save(Mockito.any(Emprestimo.class)) ).willReturn(loan);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(EMPRESTIMO_API)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform( request )
                .andExpect(MockMvcResultMatchers.status().isCreated() )
                .andExpect(MockMvcResultMatchers.content().string("1"))
        ;
    }

}
