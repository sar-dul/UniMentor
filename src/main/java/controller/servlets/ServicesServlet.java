package controller.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DbController;
import model.MentorModel;
import model.ServiceModel;
import utils.StringUtils;

/**
 * Servlet implementation class ServicesServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_SERVICES })
public class ServicesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DbController controller = new DbController();


	/**
     * Handles GET requests to display services based on user type (seeker or mentor)
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");
		
		// Extract search information from register page request parameters
		String searchKey = request.getParameter("search");
		
		// FOR SEEKER
		if (currentUserType.equals("seeker")) {
			
			// Declaring services arraylist to store all services
			ArrayList<ServiceModel> services;
			
			// Get searched services if search button clicked
			if (searchKey == null) {
				services = controller.getAllServiceInfo();
			}
			
			// Get all services for default
			else {
				services = controller.getSearchedServiceInfo(searchKey);
			}
			
			// adding arraylist of services in request attribute to display in  services jsp
			request.setAttribute(StringUtils.SERVICE_LISTS, services);
			request.setAttribute("isSeeker", true);
			
			// Initializing and declaring hashMap to connect mentor details with their service
	        HashMap<String, MentorModel> hashMap = new HashMap<>();
	        
	        // Loop on each items on arraylist
	        for (ServiceModel service: services) {	
	        	// Add mentor name as key and its model as value
	        	String currUsername = service.getMentorUsername();
	        	MentorModel mentor = controller.getMentorInfo(currUsername);
		        hashMap.put(service.getMentorUsername(), mentor);
	        }
	        
	        // adding the mentor hashmap in request attribute to display in services page
	        request.setAttribute("mentorMap", hashMap);	
		}
		
		// FOR MENTOR
		else {
			
			// Declaring services arraylist to store mentor's all services
			ArrayList<ServiceModel> mentor_services;
			
			// Get searched services if search button clicked
			if (searchKey == null) {
				mentor_services = controller.getAllMentorServiceInfo(currentUser);
			}
			
			// Get all mentor's services for default
			else {
				mentor_services = controller.getSearchedServiceInfoMentor(searchKey, currentUser);
			}

			// adding arraylist of mentor's services in request attribute to display in  services jsp
			request.setAttribute(StringUtils.MENTOR_SERVICE_LISTS, mentor_services);
			request.setAttribute("isMentor", true);
			
			// Initializing and declaring hashMap to connect mentor details with their service
			HashMap<String, MentorModel> hashMap = new HashMap<>();
	        
			// Loop on each items on arraylist
	        for (ServiceModel service: mentor_services) {	
	        	// Add mentor name as key and its model as value
	        	String currUsername = service.getMentorUsername();
	        	MentorModel mentor = controller.getMentorInfo(currUsername);
		        hashMap.put(service.getMentorUsername(), mentor);
	        }
	        
	        // adding the mentor hashmap in request attribute to display in services page
	        request.setAttribute("mentorMap", hashMap);	
		}
		
		// Redirect to services page with attributes in request
		request.getRequestDispatcher(StringUtils.PAGE_URL_SERVICES).forward(request, response);
	}
	
	
	/**
     * Handles POST requests by delegating to doGet method
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}
