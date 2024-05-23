package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DbController;
import model.MentorModel;
import utils.StringUtils;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_INDEX })
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DbController controller = new DbController();

	/**
     * Handles HTTP GET requests.
     * Retrieves top mentors' information from the database, adds it to the request attribute,
     * and forwards the request to the index page for display.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get the top mentors info from database and store in topMentors arraylist
		ArrayList<MentorModel> topMentors = controller.getTopMentorsInfo();
		
		// adding the arraylist in request attribute to display in index page
		request.setAttribute(StringUtils.TOP_MENTORS_LISTS, topMentors);
		
		// Redirect to index page with attributes in request
		request.getRequestDispatcher(StringUtils.URL_INDEX).forward(request, response);
	}
	
}
