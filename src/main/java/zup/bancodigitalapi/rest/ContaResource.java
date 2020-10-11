package zup.bancodigitalapi.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zup.bancodigitalapi.model.Conta;
import zup.bancodigitalapi.service.ContaService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ContaResource {
    @Autowired
    private ContaService service;

    @GetMapping("/api/conta")
    public List<Conta> getAllConta() {
        return service.findAll();
    }

    @GetMapping("/api/conta/{cpf}")
    public ResponseEntity<Conta> getConta(@PathVariable("cpf") String cpf) {
        final Conta conta = service.findByCpf(cpf);

        return ResponseEntity.ok(conta);
    }

    @PostMapping("/api/conta")
    public ResponseEntity<Conta> createConta(@Valid @RequestBody Conta conta) {
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/".concat(conta.getCpf())).build().toUri();
        if (service.calculateAge(conta.getDataNascimento()) < 18) {
            return ResponseEntity.badRequest().body(conta);
        } else if (service.differentEmail(conta.getEmail()) == 1) {
            return ResponseEntity.badRequest().body(conta);
        } else if (service.differentCpf(conta.getCpf()) == 1) {
            return ResponseEntity.badRequest().body(conta);
        } else {
            service.save(conta);

            return ResponseEntity.created(uri).body(null);
        }
    }
}
