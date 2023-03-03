package application.service;

import application.dto.EmprestimoFilterDTO;
import application.entities.Emprestimo;
import application.entities.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface EmprestimoService {

    Emprestimo save(Emprestimo emprestimo);

    Optional<Emprestimo> getById(Long id);

    Emprestimo atualizar(Emprestimo emprestimo);

    Page<Emprestimo> find(EmprestimoFilterDTO filterDTO, Pageable pageable);

    Page<Emprestimo> getEmprestimosByLivros(Livro livro, Pageable pageable);

    List<Emprestimo> buscarTodosEmprestimosAtrasados();

}
