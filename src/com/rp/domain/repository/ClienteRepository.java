package com.rp.domain.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.rp.domain.Cliente;
import com.rp.domain.Sexo;

public class ClienteRepository {
	private Connection conn;

	public ClienteRepository(String url, String usuario, String senha) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			try {
				conn = DriverManager.getConnection(url, usuario, senha);
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				System.out.println("Erro ao conectar no banco");
			}

		} catch (ClassNotFoundException e) {
			System.out.println("O driver JDBC não foi encontrado");
		}
	}

	public int inserirCliente(Cliente c) {
		String sql = "insert into cliente(nome, data_nascimento, sexo, cpf) values (?,?,?,?)";

		try {
			PreparedStatement ps = conn.prepareStatement(sql,
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, c.getNome());
			ps.setDate(2, new java.sql.Date(c.getDataNascimento().getTime()));
			ps.setString(3, c.getSexo().getGenero());
			ps.setString(4, c.getCpf());
			int result = ps.executeUpdate();
			if (result > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				c.setId(rs.getInt(1));
			}

			conn.commit();
			return result;
		} catch (SQLException e) {
			System.out.println("Erro ao incluir cliente");
			e.printStackTrace();
		}
		return -1;
	}

	public boolean removerCliente(Cliente c) {
		try {
			String sql = "delete from cliente where id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, c.getId());
			int result = ps.executeUpdate();
			conn.commit();
			return result > 0;

		} catch (Exception e) {
			System.out.print("Erro ao remover usuario");
			e.printStackTrace();
		}
		return false;
	}

	public List<Cliente> consultarCliente(String nome) {
		try {
			String sql = "select id, nome, data_nascimento, sexo, cpf from cliente where nome like ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + nome + "%");

			ResultSet rs = ps.executeQuery();

			List<Cliente> clientes = new ArrayList<Cliente>();
			while (rs.next()) {
				Cliente c = new Cliente();
				c.setId(rs.getInt("id"));
				c.setNome(rs.getString("nome"));
				c.setDataNascimento(rs.getDate("data_nascimento"));
				c.setSexo(Sexo.getSexo(rs.getString("sexo")));
				c.setCpf(rs.getString("cpf"));
				clientes.add(c);
			}
			return clientes;

		} catch (Exception e) {
			System.out.println("Erro ao consultar cliente");
			e.printStackTrace();
		}
		return null;
	}
	
	public void updateCliente(Cliente c) {
		String sql = "update cliente set nome = ? where id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, c.getNome());
			ps.setInt(2, c.getId());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			System.out.println("Erro ao atualizar nome do cliente");
		}
	}
	
}
