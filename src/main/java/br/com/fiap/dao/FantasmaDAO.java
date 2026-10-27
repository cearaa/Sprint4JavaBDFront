package br.com.fiap.dao;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import br.com.fiap.to.FantasmaTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FantasmaDAO {

    public ArrayList<FantasmaTO> findAll() {
        ArrayList<FantasmaTO> fantasmas = new ArrayList<>();
        String sql = "select * from ddd_fantasmas order by codigo";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    fantasmas.add(montarFantasma(rs));
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        return fantasmas;
    }

    public FantasmaTO findByCodigo(Long codigo) {
        FantasmaTO fantasma = null;
        String sql = "select * from ddd_fantasmas where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fantasma = montarFantasma(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return fantasma;
    }

    // lista os fantasmas de um usuário (dono)
    public ArrayList<FantasmaTO> findByUsuario(Long codUsuario) {
        ArrayList<FantasmaTO> fantasmas = new ArrayList<>();
        String sql = "select * from ddd_fantasmas where cod_usuario = ? order by codigo";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    fantasmas.add(montarFantasma(rs));
                }
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.closeConnection();
        }
        return fantasmas;
    }

    public FantasmaTO save(FantasmaTO fantasma) {
        String sql = "insert into ddd_fantasmas(nome, tipo, raridade, valor_essencia, cod_usuario) values(?, ?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, fantasma.getNome());
            ps.setString(2, fantasma.getTipo());
            ps.setString(3, fantasma.getRaridade());
            ps.setDouble(4, fantasma.getValorEssencia());
            ps.setLong(5, fantasma.getCodUsuario());
            if (ps.executeUpdate() > 0) {
                return fantasma;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    public boolean delete(Long codigo) {
        String sql = "delete from ddd_fantasmas where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return false;
    }

    public FantasmaTO update(FantasmaTO fantasma) {
        String sql = "update ddd_fantasmas set nome=?, tipo=?, raridade=?, valor_essencia=?, cod_usuario=? where codigo=?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, fantasma.getNome());
            ps.setString(2, fantasma.getTipo());
            ps.setString(3, fantasma.getRaridade());
            ps.setDouble(4, fantasma.getValorEssencia());
            ps.setLong(5, fantasma.getCodUsuario());
            ps.setLong(6, fantasma.getCodigo());
            if (ps.executeUpdate() > 0) {
                return fantasma;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return null;
    }

    // transforma a linha atual do ResultSet em um objeto FantasmaTO
    private FantasmaTO montarFantasma(ResultSet rs) throws SQLException {
        FantasmaTO fantasma = new FantasmaTO();
        fantasma.setCodigo(rs.getLong("codigo"));
        fantasma.setNome(rs.getString("nome"));
        fantasma.setTipo(rs.getString("tipo"));
        fantasma.setRaridade(rs.getString("raridade"));
        fantasma.setValorEssencia(rs.getDouble("valor_essencia"));
        fantasma.setCodUsuario(rs.getLong("cod_usuario"));
        return fantasma;
    }
}
