package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DbController;
import model.ServiceModel;
import utils.StringUtils;

/**
 * Servlet implementation class AddServiceServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_ADD_SERVICE })
public class AddServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DbController dbController;
       
	/**
	 * Initializes the AddServiceServlet by creating a new instance of DbController.
	 * @see HttpServlet#HttpServlet()
	 */
    public AddServiceServlet() {
    	this.dbController = new DbController();
    }


	/**
	 * Handles HTTP POST requests sent to the servlet, adding a new service to the database.
	 * Retrieves attributes from the current session, extracts service information from request parameters,
	 * creates a ServiceModel object for the new service, and adds the service to the database using DbController.
	 * @param request The HttpServletRequest object representing the request.
	 * @param response The HttpServletResponse object representing the response.
	 * @throws ServletException If the servlet encounters a ServletException.
	 * @throws IOException If an I/O error occurs while processing the request.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		
		// Extract service information from add service page request parameters
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int price = Integer.parseInt(request.getParameter("price"));
		
		// creating a service model for new added service 
		ServiceModel newService = new ServiceModel();
		newService.setTitle(title);
		newService.setDescription(description);
		newService.setPrice(price);
		
		// Adding the register mentor(model) in database
		int result = dbController.addService(newService, currentUser);
		
		//checking for exception
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_SERVICES);

		} else {
			// Code will be written in later weeks (ERROR 404 PAGE)
		}
	}

}
