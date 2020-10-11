package zup.bancodigitalapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zup.bancodigitalapi.exception.ContaNotFoundException;
import zup.bancodigitalapi.model.Conta;
import zup.bancodigitalapi.repository.ContaRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {
    @Autowired
    private ContaRepository repository;

    public List<Conta> findAll() {
        return repository.findAll();
    }

    public Conta findByCpf(final String cpf) {
        final Optional<Conta> conta = repository.findByCpf(cpf);

        if (conta.isPresent()) {
            return conta.get();
        } else {
            throw new ContaNotFoundException();
        }
    }

    public int differentEmail(final String email) {
        final Optional<Conta> conta = repository.findByEmail(email);

        if (conta.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int differentCpf(final String cpf) {
        final Optional<Conta> conta = repository.findByCpf(cpf);

        if (conta.isPresent()) {
            return 1;
        } else {
            return 0;
        }
    }

    public int calculateAge(final LocalDate dataNascimento) {
        LocalDate atual = LocalDate.now();

        return Period.between(dataNascimento, atual).getYears();
    }

    public Conta save(final Conta conta) {
        return repository.save(conta);
    }
}
