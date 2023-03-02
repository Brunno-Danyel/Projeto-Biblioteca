package application.service.impl;

import application.Exception.BusinessException;
import application.entities.Emprestimo;
import application.repositories.EmprestimoRespository;
import org.springframework.beans.factory.annotation.Autowired;

public class EmprestimoServiceImpl {

    @Autowired
    private EmprestimoRespository emprestimoRespository;

    public Emprestimo save(Emprestimo emprestimo){
       if(emprestimoRespository.existsByLivroAndNotRetornoDoLivro(emprestimo.getLivro())){
            throw new BusinessException("Livro já emprestado");
       }
        return emprestimoRespository.save(emprestimo);
    }
}
