package application.service;

import application.DTO.EmprestimoFilterDTO;
import application.entities.Emprestimo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmprestimoService {

    Emprestimo save(Emprestimo any);

    Optional<Emprestimo> getById(Long id);

    Emprestimo atualizar(Emprestimo emprestimo);

    Page<Emprestimo> find(EmprestimoFilterDTO filterDTO, Pageable pageable);
}
