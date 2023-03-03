package application.controller;

import application.dto.EmprestimoDTO;
import application.dto.EmprestimoFilterDTO;
import application.dto.LivroDTO;
import application.dto.RetornoLivroDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import application.service.EmprestimoService;
import application.service.LivroService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/emprestimo")
@AllArgsConstructor
public class EmprestimoController {

    private LivroService livroService;

    private EmprestimoService emprestimoService;

    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long criarEmprestimo(@RequestBody EmprestimoDTO emprestimoDTO){
        Livro livro = livroService.getLivroByIsbn(emprestimoDTO.getIsbn())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Livro não encontrado!"));
        Emprestimo emprestimo = Emprestimo.builder()
                .livro(livro)
                .dataEmprestimo(LocalDate.now())
                .cliente(emprestimoDTO.getCliente())
                .build();

        emprestimoService.save(emprestimo);
        return emprestimo.getId();
    }

    @PatchMapping("{id}")
    public void retornoDoLivro(@PathVariable Long id, @RequestBody RetornoLivroDTO retornoLivroDTO){
        Emprestimo emprestimo = emprestimoService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emprestimo não encontrado!"));
        emprestimo.setRetornoDoLivro(retornoLivroDTO.getRetornoDoLivro());

        emprestimoService.atualizar(emprestimo);
    }

    @GetMapping("{id}")
    public Emprestimo buscaEmprestimo(@PathVariable Long id){
        Emprestimo emprestimo = emprestimoService.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Emprestimo não econtrado"));
        return emprestimo;
    }

    @GetMapping
    Page<EmprestimoDTO> find(EmprestimoFilterDTO dto, PageRequest pageRequest){
        Page<Emprestimo> result = emprestimoService.find(dto, pageRequest);
        List<EmprestimoDTO> emprestimos = result
                .getContent()
                .stream()
                .map(entity -> {

                    Livro livro = entity.getLivro();
                    LivroDTO livroDTO = modelMapper.map(livro, LivroDTO.class);
                    EmprestimoDTO emprestimoDTO = modelMapper.map(entity, EmprestimoDTO.class);
                    emprestimoDTO.setLivroDTO(livroDTO);
                    return emprestimoDTO;

                }).collect(Collectors.toList());
        return new PageImpl<EmprestimoDTO>(emprestimos, pageRequest, result.getTotalElements());
    }
}
