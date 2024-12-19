package com.edutecno.dao;

import com.edutecno.modelo.Usuario;
import com.edutecno.procesaconexion.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	private	 Connection connection;
	
	//CONSTRUCTOR QUE RECIBE UNA CONEXION A LA BD:
	public  UsuarioDAO () {
		this.connection = ConexionBD.getConnection(); //?
	}
	
	
	
	public boolean crearUsuario(Usuario usuario) {
		String sql = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, usuario.getUsername());
			stmt.setString(2, usuario.getPassword());
			stmt.setString(3, usuario.getEmail());
			
			return stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Usuario buscarUsuario(String username, String password) {
		String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Usuario(
					    rs.getString("username"),
						rs.getString("password"),
						rs.getString("email")
						);
			}
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}

	public boolean modificarUsuario(Usuario usuario) {
		String sql = "UPDATE usuarios SET password = ?, email = ? WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, usuario.getPassword());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getUsername());
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		return false;
	}
	}

	public Usuario buscarUsuarioPorNombre(String username) {
		String sql = "SELECT * FROM usuarios WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Usuario (
							rs.getString("username"),
							rs.getString("password"),
							rs.getString("email")
							);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean eliminarUsuario(String username) {
		String sql = "DELETE FROM usuarios WHERE username = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, username);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
}