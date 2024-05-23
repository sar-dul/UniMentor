package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controller.database.DbController;
import model.MentorModel;
import model.SeekerModel;
import utils.StringUtils;
import utils.ValidationUtils;

/**
 * Servlet implementation class ProfileServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_PROFILE })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50)
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DbController dbController = new DbController();
	

	/**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * Handles HTTP GET requests by retrieving user profile information and forwarding to the profile page.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");

		// FOR MENTOR (get current mentor info from database as mentor model and set attribute in request)
		if (currentUserType.equals("mentor")) {
			MentorModel mentor = dbController.getMentorInfo(currentUser);
			request.setAttribute("userProfile", mentor);
		}

		// FOR SEEKER (get current seeker info from database as seeker model and set attribute in request)
		else {
			SeekerModel seeker = dbController.getSeekerInfo(currentUser);
			request.setAttribute("userProfile", seeker);
		}

		// Redirect to profile page with attributes in request
		request.getRequestDispatcher(StringUtils.PAGE_URL_PROFILE).forward(request, response);
	}

	/**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles HTTP POST requests for updating user profile or deleting user account.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Checking if update password button clicked
		Object isUpdate = request.getAttribute("isUpdate");	
		if (isUpdate != null) {
			if((Integer)isUpdate == 1) {
				doGet(request, response);
			}
		
		// If update details or delete button clicked
		}else {

			// getting username from input parameters
			String updateUsername = request.getParameter("update_username");
			String deleteUsername = request.getParameter("delete_username");

			// if update button clicked
			if (updateUsername != null && !updateUsername.isEmpty()) {
				doPut(request, response);
			}
			
			// if delete button clicked
			if (deleteUsername != null && !deleteUsername.isEmpty()) {
				doDelete(request, response);
			}
		}
	}

	/**
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     * Handles HTTP PUT requests for updating user profile information.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");
		String currentImage = (String) userSession.getAttribute("image");

		// Extract user information from profile page request parameters
		String name = request.getParameter("name");
		String username = request.getParameter("update_username");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		Part photo = request.getPart("photo");

		// validation flag
		boolean is_valid = true;
		
		// full name validation
		if (!ValidationUtils.isAlphabetical(name)) {
			is_valid = false;
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_NAMES);	
		}

		// user name validation
		if (!ValidationUtils.isUsername(username)) {
			is_valid = false;
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_USERNAME);
		}
		
		// phone validation
		if (!ValidationUtils.isPhone(phone)) {
			is_valid = false;
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_PHONE_NUMBER);
		}
		
		// image extension validation
		if (!ValidationUtils.isImage(photo.getSubmittedFileName()) && !(photo.getSubmittedFileName() == "")) {
			is_valid = false;
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_IMAGE);
		}		
		
		// if not valid then send to do get method and return
		if (!is_valid) {
			doGet(request, response);
			return;
		}
		

		// FOR MENTOR
		if (currentUserType.equals("mentor")) {
			
			// Create a mentor model for updated mentor
			MentorModel updatedMentor = new MentorModel();
			updatedMentor.setName(name);
			updatedMentor.setUsername(username);
			updatedMentor.setEmail(email);
			updatedMentor.setPhone(phone);
			updatedMentor.setPassword(password);
			
			// checking if photo updated
			if (photo.getSize() == 0) {
				updatedMentor.setProfilePhotoUrlFromDb(currentImage);
			} else {
				updatedMentor.setProfilePhotoUrlFromPart(photo);
			}

			// Updating the registered mentor(model) in database
			int result = dbController.updateMentor(currentUser, updatedMentor);

			// checking for exception when updating
			if (result > 0) {

				// Upload new image in tomcat server
				String savePath = StringUtils.IMAGE_DIR_USER;
				String fileName = updatedMentor.getProfilePhotoUrl();

				if (!(photo.getSize() == 0))
					photo.write(savePath + fileName);

				// updating username and image in session
				userSession.setAttribute("username", username);
				userSession.setAttribute("image", fileName);
				
				// redirect to welcome page
				request.getRequestDispatcher(StringUtils.PAGE_URL_WELCOME).forward(request, response);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}

		// FOR SEEKER
		else {
			
			// Create a seeker model for updated seeker
			SeekerModel updatedSeeker = new SeekerModel();
			updatedSeeker.setName(name);
			updatedSeeker.setUsername(username);
			updatedSeeker.setEmail(email);
			updatedSeeker.setPhone(phone);
			updatedSeeker.setPassword(password);
			
			// checking if photo updated
			if (photo.getSize() == 0) {
				updatedSeeker.setProfilePhotoUrlFromDb(currentImage);
			} else {
				updatedSeeker.setProfilePhotoUrlFromPart(photo);
			}

			// Updating the registered seeker(model) in database
			int result = dbController.updateSeeker(currentUser, updatedSeeker);

			// checking for exception when updating
			if (result > 0) {

				// Upload new image in tomcat server
				String savePath = StringUtils.IMAGE_DIR_USER;
				String fileName = updatedSeeker.getProfilePhotoUrl();
				if (!(photo.getSize() == 0))
					photo.write(savePath + fileName);

				// updating username and image in session
				userSession.setAttribute("username", username);
				userSession.setAttribute("image", fileName);
				
				// redirect to welcome page
				request.getRequestDispatcher(StringUtils.PAGE_URL_WELCOME).forward(request, response);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}
	}

	/**
     * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
     * Handles HTTP DELETE requests for deleting user account.
     * @param request The HttpServletRequest object representing the request.
     * @param response The HttpServletResponse object representing the response.
     * @throws ServletException If the servlet encounters a ServletException.
     * @throws IOException If an I/O error occurs while processing the request.
     */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");

		// FOR MENTOR
		if (currentUserType.equals("mentor")) {
			
			// deleting the registered mentor(model) from database
			int result = dbController.deleteMentor(currentUser);

			// checking for exception when deleting
			if (result > 0) {
				request.getRequestDispatcher(StringUtils.SERVLET_URL_LOGOUT).forward(request, response);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}

		// FOR SEEKER
		else {
			
			// deleting the registered seeker(model) from database
			int result = dbController.deleteSeeker(currentUser);

			// checking for exception when deleting
			if (result > 0) {
				request.getRequestDispatcher(StringUtils.SERVLET_URL_LOGOUT).forward(request, response);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}
	}

}
