package zup.bancodigitalapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zup.bancodigitalapi.model.Conta;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByEmail(final String email);
    Optional<Conta> findByCpf(final String cpf);
}
