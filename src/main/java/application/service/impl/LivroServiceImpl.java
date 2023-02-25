package application.service.impl;

import application.Exception.BusinessException;
import application.entities.Livro;
import application.repositories.LivroRepository;
import application.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LivroServiceImpl implements LivroService {

    private LivroRepository livroRepository;

    public LivroServiceImpl(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    @Override
    public Livro save(Livro livro) {
        if(livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new BusinessException("Isbn já cadastrado");
        }
        return livroRepository.save(livro);
    }

    @Override
    public Optional<Livro> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Livro livro) {

    }

    @Override
    public Livro atualizar(Livro livro) {
        return null;
    }


}
