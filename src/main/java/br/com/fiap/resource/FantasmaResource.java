package br.com.fiap.resource;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import br.com.fiap.bo.FantasmaBO;
import br.com.fiap.to.FantasmaTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soulhunter/fantasmas")  // localhost:8080/soulhunter/fantasmas
public class FantasmaResource {
    private FantasmaBO fantasmaBO = new FantasmaBO();

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<FantasmaTO> fantasmas = fantasmaBO.findAll();
            if (fantasmas != null) {
                return ResponseEntity.status(HttpStatus.OK).body(fantasmas);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os fantasmas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os fantasmas");
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        try {
            FantasmaTO fantasma = fantasmaBO.findByCodigo(codigo);
            if (fantasma != null) {
                return ResponseEntity.status(HttpStatus.OK).body(fantasma);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fantasma não encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar o fantasma");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid FantasmaTO fantasma) {
        try {
            FantasmaTO resultado = fantasmaBO.save(fantasma);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o fantasma");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o fantasma");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid FantasmaTO fantasma) {
        try {
            if (fantasmaBO.findByCodigo(codigo) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fantasma não encontrado!");
            }
            fantasma.setCodigo(codigo);
            FantasmaTO resultado = fantasmaBO.update(fantasma);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o fantasma");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o fantasma");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        try {
            if (fantasmaBO.delete(codigo)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fantasma não encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o fantasma");
        }
    }
}
