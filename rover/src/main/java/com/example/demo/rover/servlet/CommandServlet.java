package com.example.demo.rover.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpStatus;

import com.example.demo.rover.domain.ValidationResult;
import com.example.demo.rover.enumeration.ErrorCode;
import com.example.demo.rover.service.CommandsService;

public class CommandServlet extends HttpServlet {

	private static final long serialVersionUID = -6154475799000019575L;

	CommandsService commandService = CommandsService.getInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String command = (String) request.getParameter("cmdStr");
		ValidationResult result = commandService.executeCommand(command);

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String resultMessage = null;

		if (result.getErrorCode() == null) {
			response.setStatus(HttpStatus.OK_200);
			resultMessage = result.getMessage();
		} else if (result.getErrorCode() == ErrorCode.INVALID_REQUEST) {
			response.setStatus(HttpStatus.BAD_REQUEST_400);
			resultMessage = result.getErrorCode().getDescription();
		} else {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
			resultMessage = result.getErrorCode().getDescription();
		}
		out.print(resultMessage);
		out.flush();

	}

}