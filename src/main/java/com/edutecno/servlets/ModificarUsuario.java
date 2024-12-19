package com.edutecno.servlets;

import com.edutecno.dao.UsuarioDAO;
import com.edutecno.modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ModificarUsuario")

public class ModificarUsuario<Date> extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String userId = request.getParameter("id");
		
		if (userId != null && ! userId.isEmpty()) {
		
			Usuario usuario = usuarioDAO.buscarUsuarioPorId(Integer.parseInt(userId));
			
			if (usuario != null) {
				
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("modificarUsuario.jsp").forward(request, response);
				
			} else {
				
				request.setAttribute("error", "Usuario no encontrado.");
				response.sendRedirect("listaUsuarios.jsp"); 
			}
		}else {
			
			request.setAttribute("error", "No proporciono un ID de usuario valido.");
			response.sendRedirect("listaUsuarios.jsp");
		}
	}
	
	@Override
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String idStr = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		Date fechaNacimiento = (Date) request.getParameter("fechaNacimiento");
		String animal = request.getParameter("animal");
		
		if (username == null || idStr.isEmpty() || username == null || username.isEmpty() ||
				password == null || password.isEmpty() || email == null || email.isEmpty()) {
			
			request.setAttribute("error", "Todos los campos son obligatorios.");
			request.getRequestDispatcher("modificarUsuario.jsp").forward(request, response);
			return;
		}
		
		int id = Integer.parseInt(idStr);
		
		Usuario usuario = new Usuario (id, nombre, username, password, email, (java.util.Date) fechaNacimiento, animal);
			
		boolean usuarioModificado = usuarioDAO.modificarUsuario(usuario);
		
		if (usuarioModificado) {
		
			response.sendRedirect("listaUsuarios.jsp?mensaje=Usuario modificado exitosamente");
			
		} else {
			
			request.setAttribute("error", "No se pudo modificar el usuario. Intentalo de nuevo.");
			request.getRequestDispatcher("modificarUsuario.jsp").forward(request, response);
		}
	}

}