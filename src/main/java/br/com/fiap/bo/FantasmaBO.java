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

import java.util.ArrayList;

public class FantasmaBO {
    private FantasmaDAO fantasmaDAO;
    private UsuarioDAO usuarioDAO;

    public ArrayList<FantasmaTO> findAll() {
        fantasmaDAO = new FantasmaDAO();
        return fantasmaDAO.findAll();
    }

    public FantasmaTO findByCodigo(Long codigo) {
        fantasmaDAO = new FantasmaDAO();
        return fantasmaDAO.findByCodigo(codigo);
    }

    public ArrayList<FantasmaTO> findByUsuario(Long codUsuario) {
        fantasmaDAO = new FantasmaDAO();
        return fantasmaDAO.findByUsuario(codUsuario);
    }

    public FantasmaTO save(FantasmaTO fantasma) {
        fantasmaDAO = new FantasmaDAO();
        usuarioDAO = new UsuarioDAO();
        // aqui se implementa a regra de negócios
        // regra: todo fantasma pertence a um usuário que já existe
        if (usuarioDAO.findByCodigo(fantasma.getCodUsuario()) == null) {
            throw new IllegalArgumentException("O usuário informado não existe");
        }
        fantasma.setNome(fantasma.getNome().trim());
        fantasma.setTipo(fantasma.getTipo().trim());
        fantasma.setRaridade(fantasma.getRaridade().trim());
        return fantasmaDAO.save(fantasma);
    }

    public boolean delete(Long codigo) {
        fantasmaDAO = new FantasmaDAO();
        // aqui se implementa a regra de negócios
        return fantasmaDAO.delete(codigo);
    }

    public FantasmaTO update(FantasmaTO fantasma) {
        fantasmaDAO = new FantasmaDAO();
        // aqui se implementa a regra de negócios
        FantasmaTO atual = fantasmaDAO.findByCodigo(fantasma.getCodigo());
        if (atual == null) {
            return null;
        }
        // regra: o dono do fantasma não muda na alteração (continua o que está no banco)
        fantasma.setCodUsuario(atual.getCodUsuario());
        fantasma.setNome(fantasma.getNome().trim());
        fantasma.setTipo(fantasma.getTipo().trim());
        fantasma.setRaridade(fantasma.getRaridade().trim());
        return fantasmaDAO.update(fantasma);
    }
}
