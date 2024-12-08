package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import connection.Conexao;
import model.Carona;
import model.Usuario;

public class CaronaDAO {

    public void cadasatrarCarona(Carona c, int userId) throws ClassNotFoundException {
        String sql = "INSERT INTO carona (saida, chegada, dataCarona, horario, valor, vagas, carro, fk_Usuario_ID_usuario) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = Conexao.getConexao().prepareStatement(sql);

            ps.setString(1, c.getSaida());
            ps.setString(2, c.getChegada());
            ps.setString(3, c.getDataCarona());
            ps.setString(4, c.getHorario());
            ps.setFloat(5, c.getValor());
            ps.setInt(6, c.getVagas());
            ps.setString(7, c.getCarro());
            ps.setInt(8, userId);

            ps.executeUpdate();
            System.out.println("Carona cadastrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar carona: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public List<Carona> buscarCarona(String origem, String destino, String data) {
        String sql = "SELECT * FROM carona WHERE saida = ? AND chegada = ? AND dataCarona = ?";
        try {
            PreparedStatement ps = null;

            ps = Conexao.getConexao().prepareStatement(sql);

            ps.setString(1, origem);
            ps.setString(2, destino);
            ps.setString(3, data);
            ResultSet rs = ps.executeQuery();
            List<Carona> caronas = new ArrayList<>();
            while (rs.next()) {
                Carona carona = new Carona();
                carona.setIdCarona(rs.getInt("ID_carona"));
                carona.setSaida(rs.getString("saida"));
                carona.setChegada(rs.getString("chegada"));
                carona.setDataCarona(rs.getString("dataCarona"));
                carona.setHorario(rs.getString("horario"));
                carona.setValor(rs.getFloat("valor"));
                carona.setVagas(rs.getInt("vagas"));
                carona.setCarro(rs.getString("carro"));
                carona.setIdUsuario(rs.getInt("fk_Usuario_ID_usuario"));
                caronas.add(carona);
            }
            return caronas;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erro ao buscar carona: " + e.getMessage());
            return null;


        }
    }
}