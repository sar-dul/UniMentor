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
import model.MentorModel;
import utils.StringUtils;
import utils.ValidationUtils;

/**
 * Servlet implementation class RegisterMentorServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_REGISTER_MENTOR })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class RegisterMentorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DbController dbController;
       
    /**
	 * Constructs a new RegisterMentorServlet.
	 * Initializes a new instance of DbController for database operations.
     * @see HttpServlet#HttpServlet()
     */
    public RegisterMentorServlet() {
    	this.dbController = new DbController();
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     * Handles POST requests for registering mentors.
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Extract mentor information from register page request parameters
		String name = request.getParameter(StringUtils.MENTOR_NAME);
		String username = request.getParameter(StringUtils.MENTOR_USERNAME);
		String email= request.getParameter(StringUtils.MENTOR_EMAIL);
		String phone = request.getParameter(StringUtils.MENTOR_PHONE_NUMBER);
		String password = request.getParameter(StringUtils.MENTOR_PASSWORD);
		String retypePassword = request.getParameter(StringUtils.MENTOR_RETYPE_PASSWORD);
		String universityName = request.getParameter(StringUtils.MENTOR_UNIVERSITY_NAME);
		String universityCountry = request.getParameter(StringUtils.MENTOR_UNIVERSITY_COUNTRY);
		String major = request.getParameter(StringUtils.MENTOR_MAJOR);
		int tuitionFee = Integer.parseInt(request.getParameter(StringUtils.MENTOR_TUITION_FEE));
		String scholarship = request.getParameter(StringUtils.MENTOR_SCHOLARSHIP);
		Part profilePhoto = request.getPart(StringUtils.MENTOR_PROFILE_PHOTO);
		
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
		
		// tuition fee validation
		if (!ValidationUtils.isNumeric(Integer.toString(tuitionFee))) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_TUITION);
		}
		
		// image extension validation
		if (!ValidationUtils.isImage(profilePhoto.getSubmittedFileName()) && !(profilePhoto.getSubmittedFileName() == "")) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_IMAGE);
		}
		
		// checking if user name exists
		boolean username_exist = dbController.presentInMentor("mentor_username", username);
		if (username_exist) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_USERNAME_EXISTS);
		}

		// checking if email exists
		boolean email_exist = dbController.presentInMentor("mentor_email", email);
		if (email_exist) {
			is_valid = false;
			errorMessage.append(StringUtils.MESSAGE_ERROR_EMAIL_EXISTS);
		}

		// checking if number exists
		boolean number_exist = dbController.presentInMentor("mentor_phone", phone);
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
			
			// Create model for registered mentor
			MentorModel mentor = new MentorModel(name, username, email, phone, password, universityName, 
					universityCountry, major, tuitionFee, scholarship, profilePhoto);
			
			// Adding the register mentor(model) in database
			int result = dbController.registerMentor(mentor);
			
			//checking for exception
			if (result > 0) {

				// Upload image in tomcat server
				String savePath = StringUtils.IMAGE_DIR_USER;
			    String fileName = mentor.getProfilePhotoUrl();
			    if(!(fileName.equals("default-profile.png")))
		    		profilePhoto.write(savePath + fileName);

			 	
				//  set success request attribute
				request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_REGISTER);

			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}

		//  Redirect to register mentor page with request attributes
		request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_MENTOR).forward(request, response);
	
	}
	
}
