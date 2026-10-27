package br.com.fiap.dao;

// Integrantes da Challenge
//
// Tárik Moussa Alma - RM: 571411
// Giovanni Azevedo - RM: 572894
// Fabricio Aquiles Sales da Silva - RM: 570985
// Carlos Eduardo Tsucamoto Chiarelli - RM: 569574
// Ítalo Neto - RM: 572912

import br.com.fiap.to.UsuarioTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    public ArrayList<UsuarioTO> findAll() {
        ArrayList<UsuarioTO> usuarios = new ArrayList<>();
        String sql = "select * from ddd_usuarios order by codigo";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    usuarios.add(montarUsuario(rs));
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
        return usuarios;
    }

    public UsuarioTO findByCodigo(Long codigo) {
        UsuarioTO usuario = null;
        String sql = "select * from ddd_usuarios where codigo = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setLong(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = montarUsuario(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuario;
    }

    public UsuarioTO findByEmail(String email) {
        UsuarioTO usuario = null;
        String sql = "select * from ddd_usuarios where email = ?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                usuario = montarUsuario(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Erro na consulta: " + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection();
        }
        return usuario;
    }

    public UsuarioTO save(UsuarioTO usuario) {
        String sql = "insert into ddd_usuarios(nome, email, essencia, titulo) values(?, ?, ?, ?)";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setDouble(3, usuario.getEssencia());
            ps.setString(4, usuario.getTitulo());
            if (ps.executeUpdate() > 0) {
                return usuario;
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
        String sql = "delete from ddd_usuarios where codigo = ?";
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

    public UsuarioTO update(UsuarioTO usuario) {
        String sql = "update ddd_usuarios set nome=?, email=?, essencia=?, titulo=? where codigo=?";
        try (PreparedStatement ps = ConnectionFactory.getConnection().prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setDouble(3, usuario.getEssencia());
            ps.setString(4, usuario.getTitulo());
            ps.setLong(5, usuario.getCodigo());
            if (ps.executeUpdate() > 0) {
                return usuario;
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

    // transforma a linha atual do ResultSet em um objeto UsuarioTO
    private UsuarioTO montarUsuario(ResultSet rs) throws SQLException {
        UsuarioTO usuario = new UsuarioTO();
        usuario.setCodigo(rs.getLong("codigo"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setEssencia(rs.getDouble("essencia"));
        usuario.setTitulo(rs.getString("titulo"));
        return usuario;
    }
}
