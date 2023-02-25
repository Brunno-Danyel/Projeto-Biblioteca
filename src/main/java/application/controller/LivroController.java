package application.controller;

import application.DTO.LivroDTO;
import application.Exception.ApiErros;
import application.Exception.BusinessException;
import application.entities.Livro;
import application.service.LivroService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    LivroService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LivroDTO salvarLivros(@RequestBody @Valid LivroDTO livroDTO){
     Livro livro = modelMapper.map(livroDTO, Livro.class);
     livro = service.save(livro);
     return modelMapper.map(livro, LivroDTO.class);
    }

    @GetMapping("{id}")
    public LivroDTO getLivro(@PathVariable Long id){
        return service
                .getById(id)
                .map(livro -> modelMapper.map(livro, LivroDTO.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable Long id){
        Livro livro = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(livro);
    }

    @PutMapping("{id}")
    public LivroDTO atualizarLivro(@PathVariable Long id, LivroDTO dto){
        Livro livro = service.getById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        livro.setAutor(dto.getAutor());
        livro.setTitulo(dto.getTitulo());
        livro = service.atualizar(livro);
        return modelMapper.map(livro, LivroDTO.class);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiErros handleValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErros(bindingResult);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiErros handleBusinessException(BusinessException ex) {
        return new ApiErros(ex);
    }

}
