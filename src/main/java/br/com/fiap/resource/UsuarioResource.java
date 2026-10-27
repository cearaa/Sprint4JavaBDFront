package br.com.fiap.resource;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import br.com.fiap.bo.FantasmaBO;
import br.com.fiap.bo.UsuarioBO;
import br.com.fiap.to.FantasmaTO;
import br.com.fiap.to.ReciclagemTO;
import br.com.fiap.to.TransporteTO;
import br.com.fiap.to.UsuarioTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/soulhunter/usuarios")  // localhost:8080/soulhunter/usuarios
public class UsuarioResource {
    private UsuarioBO usuarioBO = new UsuarioBO();
    private FantasmaBO fantasmaBO = new FantasmaBO();

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<UsuarioTO> usuarios = usuarioBO.findAll();
            if (usuarios != null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuarios);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os usuários");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os usuários");
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<?> findByCodigo(@PathVariable Long codigo) {
        try {
            UsuarioTO usuario = usuarioBO.findByCodigo(codigo);
            if (usuario != null) {
                return ResponseEntity.status(HttpStatus.OK).body(usuario);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar o usuário");
        }
    }

    // lista os fantasmas capturados por um usuário
    @GetMapping("/{codigo}/fantasmas")
    public ResponseEntity<?> findFantasmas(@PathVariable Long codigo) {
        try {
            if (usuarioBO.findByCodigo(codigo) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
            }
            List<FantasmaTO> fantasmas = fantasmaBO.findByUsuario(codigo);
            if (fantasmas != null) {
                return ResponseEntity.status(HttpStatus.OK).body(fantasmas);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os fantasmas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao consultar os fantasmas");
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UsuarioTO usuario) {
        try {
            UsuarioTO resultado = usuarioBO.save(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao salvar o usuário");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o usuário");
        }
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<?> update(@PathVariable Long codigo,
                                    @RequestBody @Valid UsuarioTO usuario) {
        try {
            if (usuarioBO.findByCodigo(codigo) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
            }
            usuario.setCodigo(codigo);
            UsuarioTO resultado = usuarioBO.update(usuario);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o usuário");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar o usuário");
        }
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<String> delete(@PathVariable Long codigo) {
        try {
            if (usuarioBO.delete(codigo)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o usuário");
        }
    }

    // regra de negócio: registra uma reciclagem e soma a Essência do usuário
    @PostMapping("/{codigo}/reciclagem")
    public ResponseEntity<?> registrarReciclagem(@PathVariable Long codigo,
                                                 @RequestBody @Valid ReciclagemTO reciclagem) {
        try {
            if (usuarioBO.findByCodigo(codigo) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
            }
            UsuarioTO resultado = usuarioBO.registrarReciclagem(codigo, reciclagem);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar a reciclagem");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar a reciclagem");
        }
    }

    // regra de negócio: registra um transporte sustentável e soma a Essência do usuário
    @PostMapping("/{codigo}/transporte")
    public ResponseEntity<?> registrarTransporte(@PathVariable Long codigo,
                                                 @RequestBody @Valid TransporteTO transporte) {
        try {
            if (usuarioBO.findByCodigo(codigo) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
            }
            UsuarioTO resultado = usuarioBO.registrarTransporte(codigo, transporte);
            if (resultado != null) {
                return ResponseEntity.status(HttpStatus.OK).body(resultado);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao registrar o transporte");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao registrar o transporte");
        }
    }
}
