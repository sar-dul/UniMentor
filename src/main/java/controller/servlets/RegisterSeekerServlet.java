package controller.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import controller.database.DbController;
import model.SeekerModel;
import utils.StringUtils;
import utils.ValidationUtils;

/**
 * Servlet implementation class RegisterSeekerServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_REGISTER_SEEKER })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class RegisterSeekerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DbController dbController;
	
	/**
	 * Constructs a new RegisterSeekerServlet.
	 * Initializes a new instance of DbController for database operations.
     * @see HttpServlet#HttpServlet()
     */
    public RegisterSeekerServlet() {
    	this.dbController = new DbController();
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles POST requests for registering seekers.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract mentor information from register page request parameters
		String name = request.getParameter(StringUtils.SEEKER_NAME);
		String username = request.getParameter(StringUtils.SEEKER_USERNAME);
		String email= request.getParameter(StringUtils.SEEKER_EMAIL);
		String phone = request.getParameter(StringUtils.SEEKER_PHONE_NUMBER);
		String password = request.getParameter(StringUtils.SEEKER_PASSWORD);
		String retypePassword = request.getParameter(StringUtils.SEEKER_RETYPE_PASSWORD);
		String location = request.getParameter(StringUtils.SEEKER_LOCATION);
		String educationLevel = request.getParameter(StringUtils.SEEKER_EDUCATION_LEVEL);
		Part profilePhoto = request.getPart(StringUtils.SEEKER_PROFILE_PHOTO);
		
		// validation flag
		boolean is_valid = true;
		
		// message to store all error messages
		StringBuilder errorMessage = new StringBuilder();
		
		// full name validation
		if (!ValidationUtils.isAlphabetical(name)) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_NAMES);	
		}

		// user name validation
		if (!ValidationUtils.isUsername(username)) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_USERNAME);
		}
		
		// phone validation
		if (!ValidationUtils.isPhone(phone)) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_PHONE_NUMBER);
		}
		
		// password validation
		if (!ValidationUtils.isPassword(password)) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_PASSWORD);
		}
		if (!password.equals(retypePassword)) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_PASSWORD_UNMATCHED);
		}
		
		// image extension validation
		if (!ValidationUtils.isImage(profilePhoto.getSubmittedFileName()) && !(profilePhoto.getSubmittedFileName() == "")) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_IMAGE);
		}
		
		// checking if user name exists
		boolean username_exist = dbController.presentInSeeker("seeker_username", username);
		if (username_exist) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_USERNAME_EXISTS);
		}

		// checking if email exists
		boolean email_exist = dbController.presentInSeeker("seeker_email", email);
		if (email_exist) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_EMAIL_EXISTS);
		}

		// checking if number exists
		boolean number_exist = dbController.presentInSeeker("seeker_phone", phone);
		if (number_exist) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_PHONE_NUMBER_EXISTS);
		}
		
		// if input is invalid then
		if (!is_valid) {
			//  set error request attribute
			request.setAttribute(StringUtils.MESSAGE_ERROR, errorMessage.toString());
		}
				
		// if every input is valid then
		else {
			
			// Create model for registered seeker
			SeekerModel seeker = new SeekerModel(name, username, email, phone, password, location, educationLevel, profilePhoto);
					
			// Adding the register seeker(model) in database
			int result = dbController.registerSeeker(seeker);
			
			//checking for exception
			if (result == 1) {
				
				// Upload image in tomcat server
				String savePath = StringUtils.IMAGE_DIR_USER;
			    String fileName = seeker.getProfilePhotoUrl();
			    if(!(fileName.equals("default-profile.png")))
		    		profilePhoto.write(savePath + fileName);
			    
			    //  set success request attribute
			    request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_REGISTER);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}

		//  Redirect to register seeker page with request attributes
		request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_SEEKER).forward(request, response);
	
	}
}
