package br.com.fiap.bo;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import br.com.fiap.dao.FantasmaDAO;
import br.com.fiap.dao.UsuarioDAO;
import br.com.fiap.to.FantasmaTO;
import br.com.fiap.to.ReciclagemTO;
import br.com.fiap.to.TransporteTO;
import br.com.fiap.to.UsuarioTO;

import java.util.ArrayList;

public class UsuarioBO {
    private UsuarioDAO usuarioDAO;
    private FantasmaDAO fantasmaDAO;

    // regras de pontuação (Essência)
    private static final double ESSENCIA_POR_QUILO = 5.0;
    private static final double ESSENCIA_POR_KM_BICICLETA = 3.0;
    private static final double ESSENCIA_POR_KM_OUTROS = 2.0;

    public ArrayList<UsuarioTO> findAll() {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findAll();
    }

    public UsuarioTO findByCodigo(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        return usuarioDAO.findByCodigo(codigo);
    }

    public UsuarioTO save(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        // aqui se implementa a regra de negócios
        usuario.setNome(usuario.getNome().trim());
        usuario.setEmail(usuario.getEmail().trim().toLowerCase());
        // regra: não pode existir outro usuário com o mesmo e-mail
        if (usuarioDAO.findByEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail");
        }
        // regra: todo usuário novo começa com 0 de Essência (o cliente não pode escolher)
        usuario.setEssencia(0.0);
        usuario.setTitulo(obterTituloCacador(0.0));
        return usuarioDAO.save(usuario);
    }

    // altera apenas nome e e-mail. A Essência e o Título NÃO podem ser digitados aqui:
    // eles só mudam pelas regras de reciclagem e transporte (evita "trapaça").
    public UsuarioTO update(UsuarioTO usuario) {
        usuarioDAO = new UsuarioDAO();
        // aqui se implementa a regra de negócios
        UsuarioTO atual = usuarioDAO.findByCodigo(usuario.getCodigo());
        if (atual == null) {
            return null;
        }
        String novoEmail = usuario.getEmail().trim().toLowerCase();
        // regra: o e-mail não pode pertencer a outro usuário
        UsuarioTO outro = usuarioDAO.findByEmail(novoEmail);
        if (outro != null && !outro.getCodigo().equals(atual.getCodigo())) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail");
        }
        atual.setNome(usuario.getNome().trim());
        atual.setEmail(novoEmail);
        // Essência e Título continuam os que já estão no banco
        return usuarioDAO.update(atual);
    }

    public boolean delete(Long codigo) {
        usuarioDAO = new UsuarioDAO();
        fantasmaDAO = new FantasmaDAO();
        // aqui se implementa a regra de negócios
        // regra: não se exclui um usuário que ainda possui fantasmas capturados
        ArrayList<FantasmaTO> fantasmas = fantasmaDAO.findByUsuario(codigo);
        if (fantasmas != null && !fantasmas.isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir o usuário: ele ainda possui fantasma(s) capturado(s)");
        }
        return usuarioDAO.delete(codigo);
    }

    // soma a Essência da reciclagem, recalcula e salva o Título
    public UsuarioTO registrarReciclagem(Long codigo, ReciclagemTO reciclagem) {
        usuarioDAO = new UsuarioDAO();
        double ganho = calcularEssenciaReciclagem(reciclagem.getQuilos());
        UsuarioTO usuario = usuarioDAO.findByCodigo(codigo);
        if (usuario == null) {
            return null;
        }
        return somarEssencia(usuario, ganho);
    }

    // soma a Essência do transporte sustentável, recalcula e salva o Título
    public UsuarioTO registrarTransporte(Long codigo, TransporteTO transporte) {
        usuarioDAO = new UsuarioDAO();
        double ganho = calcularEssenciaTransporte(transporte.getTipoTransporte(), transporte.getKm());
        UsuarioTO usuario = usuarioDAO.findByCodigo(codigo);
        if (usuario == null) {
            return null;
        }
        return somarEssencia(usuario, ganho);
    }

    // regra: 5 de Essência por quilo reciclado (o tipo do material não altera o cálculo)
    public double calcularEssenciaReciclagem(Double quilos) {
        if (quilos == null || quilos <= 0) {
            throw new IllegalArgumentException("A quantidade de quilos deve ser maior que zero");
        }
        return arredondar(quilos * ESSENCIA_POR_QUILO);
    }

    // regra: bicicleta vale 3 de Essência por km; qualquer outro meio sustentável vale 2 por km
    public double calcularEssenciaTransporte(String tipoTransporte, Double km) {
        if (tipoTransporte == null || tipoTransporte.isBlank()) {
            throw new IllegalArgumentException("Informe o tipo de transporte");
        }
        if (km == null || km <= 0) {
            throw new IllegalArgumentException("A distância em km deve ser maior que zero");
        }
        if (tipoTransporte.trim().equalsIgnoreCase("bicicleta")) {
            return arredondar(km * ESSENCIA_POR_KM_BICICLETA);
        }
        return arredondar(km * ESSENCIA_POR_KM_OUTROS);
    }

    // regra: o Título de Caçador depende da Essência acumulada
    public String obterTituloCacador(double essencia) {
        if (essencia >= 100) {
            return "Caçador Lendário";
        } else if (essencia >= 60) {
            return "Caçador Raro";
        } else if (essencia >= 30) {
            return "Caçador Iniciante";
        }
        return "Aprendiz de Caçador";
    }

    private UsuarioTO somarEssencia(UsuarioTO usuario, double ganho) {
        usuario.setEssencia(arredondar(usuario.getEssencia() + ganho));
        usuario.setTitulo(obterTituloCacador(usuario.getEssencia()));
        return usuarioDAO.update(usuario);
    }

    // mantém no máximo 2 casas decimais (igual à coluna NUMBER(10,2) do banco)
    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }
}
