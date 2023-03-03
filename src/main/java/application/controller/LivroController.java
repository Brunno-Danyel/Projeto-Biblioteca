package application.controller;

import application.dto.EmprestimoDTO;
import application.dto.LivroDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import application.service.EmprestimoService;
import application.service.LivroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    LivroService service;

    @Autowired
    EmprestimoService emprestimoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO salvarLivros(@RequestBody @Valid LivroDTO livroDTO) {
        Livro livro = modelMapper.map(livroDTO, Livro.class);
        livro = service.save(livro);
        return modelMapper.map(livro, LivroDTO.class);
    }

    @GetMapping("{id}")
    public LivroDTO getLivro(@PathVariable Long id) {
        return service
                .getById(id)
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable Long id) {
        Livro livro = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(livro);
    }

    @PutMapping("{id}")
    public LivroDTO atualizarLivro(@PathVariable Long id, LivroDTO dto) {
        Livro livro = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        livro.setAutor(dto.getAutor());
        livro.setTitulo(dto.getTitulo());
        livro = service.atualizar(livro);
        return modelMapper.map(livro, LivroDTO.class);
    }

    @GetMapping("{id}/emprestimos")
    public Page<EmprestimoDTO> emprestimosByLivro(@PathVariable Long id, Pageable pageable) {
        Livro livro = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Page<Emprestimo> result = emprestimoService.getEmprestimosByLivros(livro, pageable);
        List<EmprestimoDTO> list = result.getContent()
                .stream()
                .map(emprestimos -> {
                        Livro emprestimoLivro = emprestimos.getLivro();
                        LivroDTO livroDTO = modelMapper.map(emprestimoLivro, LivroDTO.class);
                        EmprestimoDTO emprestimoDTO = modelMapper.map(emprestimos, EmprestimoDTO.class);
                        emprestimoDTO.setLivroDTO(livroDTO);
                        return emprestimoDTO;
        }).collect(Collectors.toList());
        return new PageImpl<EmprestimoDTO>(list, pageable, result.getTotalElements());

    }

}
