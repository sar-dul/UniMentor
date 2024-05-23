package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.database.DbController;
import model.ServiceModel;
import utils.StringUtils;

/**
 * Servlet implementation class ModifyServiceServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_MODIFY })
public class ModifyServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	DbController dbController = new DbController();
       

	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Handles HTTP GET requests by retrieving service information and redirecting to the update page.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Get selected service from request, get all its info from database and store in service as service model
		ServiceModel service = dbController.getServiceInfoFromId(Integer.parseInt(request.getParameter("update_service_id")));
		
		// adding the service in request attribute to display in update page
		request.setAttribute("updateService", service);
		
		// Redirect to update page with attributes in request
		request.getRequestDispatcher(StringUtils.PAGE_URL_UPDATE).forward(request, response);	
	}

	/**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles HTTP POST requests by processing update or delete actions.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// getting id's from hidden input parameters
		String updateId = request.getParameter("update_service_id");
		String deleteId = request.getParameter("delete_service_id");

		// if update button clicked
		if (updateId != null && !updateId.isEmpty()) {
			doPut(request, response);
		}
		
		// if delete button clicked
		if (deleteId != null && !deleteId.isEmpty()) {
			doDelete(request, response);
		}
	}

	/**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     * Handles HTTP PUT requests by updating service information in the database.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract user information from update page request parameters
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		int price = Integer.parseInt(request.getParameter("price"));
		
		// Create a service model for updated service 
		ServiceModel updatedService = new ServiceModel();
		updatedService.setTitle(title);
		updatedService.setDescription(description);
		updatedService.setPrice(price);
		
		// Updating the service in database
		int result = dbController.updateService(updatedService, Integer.parseInt(request.getParameter("update_service_id")));
		
		//checking for exception when updating
		if (result > 0) {
			response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_SERVICES);

		} else {
			// Internal server error
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
		}	
	}

	/**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     * Handles HTTP DELETE requests by deleting services from the database.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Deleting the selected service from database
		if (dbController.deleteService(Integer.parseInt(request.getParameter("delete_service_id"))) == 1) {
			
			// send success message through request and redirect to services servlet
			request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_DELETE);
			response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_SERVICES);
		
		// If deletion failed
		} else {
			// Internal server error
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
		}
	}

}
