package application.service.impl;

import application.Exception.BusinessException;
import application.dto.EmprestimoFilterDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import application.repositories.EmprestimoRespository;
import application.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmprestimoServiceImpl implements EmprestimoService {


    private EmprestimoRespository emprestimoRespository;

    public EmprestimoServiceImpl(EmprestimoRespository emprestimoRespository) {
        this.emprestimoRespository = emprestimoRespository;
    }

    public Emprestimo save(Emprestimo emprestimo){
       if(emprestimoRespository.existsByLivroAndNotRetornoDoLivro(emprestimo.getLivro())){
            throw new BusinessException("Livro já emprestado");
       }
        return emprestimoRespository.save(emprestimo);
    }

    @Override
    public Optional<Emprestimo> getById(Long id) {
        return emprestimoRespository.findById(id);
    }

    @Override
    public Emprestimo atualizar(Emprestimo emprestimo) {
        return emprestimoRespository.save(emprestimo);
    }

    @Override
    public Page<Emprestimo> find(EmprestimoFilterDTO filterDTO, Pageable pageable) {
        return emprestimoRespository.findByLivroIsbnOrCliente(filterDTO.getIsbn(), filterDTO.getCustomer(), pageable);
    }

    @Override
    public Page<Emprestimo> getEmprestimosByLivros(Livro livro, Pageable pageable) {
        return emprestimoRespository.findByLivro(livro, pageable);
    }
}
