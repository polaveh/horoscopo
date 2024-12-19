package com.edutecno.servlets;

import com.edutecno.dao.UsuarioDAO;
import com.edutecno.modelo.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/CrearUsuario")

public class CrearUsuario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private UsuarioDAO usuarioDAO= new UsuarioDAO();

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("registro.jsp").forward(request, response);
	}

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		
		if (username == null || username.isEmpty() ||
				password == null || password.isEmpty() ||
				email == null || email.isEmpty()) {
			
			request.setAttribute("error", "Todos los campos son obligatorios.");
			request.getRequestDispatcher("registro.jsp").forward(request, response);
			return;
		}
		
		Usuario nuevoUsuario = new Usuario(username, password, email);		
		boolean usuarioCreado = usuarioDAO.crearUsuario (nuevoUsuario);
		
		if (usuarioCreado) {			
			request.setAttribute("mensaje", "Usuario creado exitosamente! ¡Inicia Sesion!");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			
		} else {			
			request.setAttribute("error", "No se puede crear el usuario, Intentalo de nuevo.");
			request.getRequestDispatcher("registro.jsp").forward(request, response);
		}
		
	}

}