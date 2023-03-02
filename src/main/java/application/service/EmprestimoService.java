package application.service;

import application.entities.Emprestimo;

import java.util.Optional;

public interface EmprestimoService {

    Emprestimo save(Emprestimo any);

    Optional<Emprestimo> getById(Long id);

    Emprestimo atualizar(Emprestimo emprestimo);
}
