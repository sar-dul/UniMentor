package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DbController;
import model.LoginModel;
import utils.StringUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_LOGIN })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private final DbController dbController;
       
	/**
	 * Initializes the LoginServlet by creating a new instance of DbController,
	 * enabling interaction with the underlying database.
	 *
	 * @see HttpServlet#HttpServlet()
	 */
    public LoginServlet() {
    	this.dbController = new DbController();
    }

	
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles HTTP POST requests.
     * Extracts username and password from the request parameters, validates login credentials,
     * sets session attributes for logged-in users, and redirects accordingly.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract username and password from the request parameters
		String userName = request.getParameter(StringUtils.USERNAME);
		String password = request.getParameter(StringUtils.PASSWORD);

		// Create a LoginModel object to hold user credentials
		LoginModel loginModel = new LoginModel(userName, password);
		
		// Call DBController to validate login credentials
		int loginResult = dbController.getLoginInfo(loginModel);

		// Handle login results with appropriate messages and redirects	
		
		if (loginResult == 1) { // FOR MENTOR (LOGIN SUCCESS)
			
			// getting image from database
			String mentorImage = dbController.getMentorImageName(userName);
			
			// creating session and setting attributes
        	HttpSession userSession = request.getSession();
        	userSession.setAttribute("type", "mentor");
			userSession.setAttribute("username", userName);
			userSession.setAttribute("image", mentorImage);
			userSession.setMaxInactiveInterval(30*30);
			
			// creating cookie
			Cookie userCookie= new Cookie("user", userName);
			userCookie.setMaxAge(30*60);
			response.addCookie(userCookie);
			
			// redirect to welcome page 
			response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_WELCOME);
		
		} else if (loginResult == 2) {	 // FOR SEEKER (LOGIN SUCCESS)
			
			// getting image from database
			String seekerImage = dbController.getSeekerImageName(userName);
					
			// creating session and setting attributes
        	HttpSession userSession = request.getSession();
        	userSession.setAttribute("type", "seeker");
			userSession.setAttribute("username", userName);
			userSession.setAttribute("image", seekerImage);
			userSession.setMaxInactiveInterval(30*30);
			
			// creating cookie
			Cookie userCookie= new Cookie("user", userName);
			userCookie.setMaxAge(30*60);
			response.addCookie(userCookie);
			
			// redirect to welcome page
			response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_WELCOME);
					
		} else if (loginResult == 0) {
			// Username or password mismatch
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_LOGIN);
			request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request,
					response);
			
		} else if (loginResult == -1) {
			// Username not found
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_CREATE_ACCOUNT);
			request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request, response);
			
		} else {
			// Internal server error
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request,
					response);
		}
	}
	
}
