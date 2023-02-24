package application.service;

import application.Exception.BusinessException;
import application.entities.Livro;
import application.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
