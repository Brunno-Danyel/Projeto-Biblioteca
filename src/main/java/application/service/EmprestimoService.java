package application.service;

import application.dto.EmprestimoFilterDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmprestimoService {

    Emprestimo save(Emprestimo any);

    Optional<Emprestimo> getById(Long id);

    Emprestimo atualizar(Emprestimo emprestimo);

    Page<Emprestimo> find(EmprestimoFilterDTO filterDTO, Pageable pageable);

    Page<Emprestimo> getEmprestimosByLivros(Livro livro, Pageable pageable);
}
