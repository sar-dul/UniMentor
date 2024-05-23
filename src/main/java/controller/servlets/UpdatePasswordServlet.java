package controller.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.database.DbController;
import model.MentorModel;
import model.SeekerModel;
import utils.StringUtils;
import utils.ValidationUtils;

/**
 * Servlet implementation class UpdatePasswordServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_UPDATE_PASSWORD })
public class UpdatePasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DbController dbController = new DbController();

	/**
     * Handles POST requests to update passwords
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");
		
		// getting old and new password from request parameters
		String oldPassword = request.getParameter("old_password");
		String newPassword = request.getParameter("new_password");
		
		// Getting the correct(previous) password from the database
		String correctPassword;
		if (currentUserType.equals("mentor")) { // FOR MENTOR
			correctPassword = dbController.getMentorInfo(currentUser).getPassword();
		}
		else { // FOR SEEKER
			correctPassword = dbController.getSeekerInfo(currentUser).getPassword();
        }

		
		//  If old password entered matches password in database
		if (oldPassword.equals(correctPassword)) {
			
			// validation for new password
			if(!ValidationUtils.isPassword(newPassword)) {
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_PASSWORD);
				request.setAttribute("isUpdate", 1);
				request.getRequestDispatcher(StringUtils.SERVLET_URL_PROFILE).include(request, response);
				return;
			}
			
			// Put method if successful validation
			doPut(request, response);
		}
		
		// redirect to profile servlet with error message if old password does not match
		else {
			request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_INCORRECT_OLD_PASSWORD);
			request.setAttribute("isUpdate", 1);
			request.getRequestDispatcher(StringUtils.SERVLET_URL_PROFILE).include(request, response);
		}
	}

	/**
     * Handles PUT requests to update passwords in the database
     *
     * @param request  The HttpServletRequest object
     * @param response The HttpServletResponse object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
     */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting attributes from current session
		HttpSession userSession = request.getSession();
		String currentUser = (String) userSession.getAttribute("username");
		String currentUserType = (String) userSession.getAttribute("type");
		
		// FOR MENTOR
		if (currentUserType.equals("mentor")) {
			
			// Update the mentor in database
			int result = dbController.updateMentorPassword(currentUser, request.getParameter("new_password"));
			
			//checking for exception when updating
			if (result > 0) {
				request.setAttribute("isUpdate", 1);
				request.getRequestDispatcher(StringUtils.PAGE_URL_WELCOME).include(request, response);
			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
		}
		
		// FOR SEEKER
		else {
			
			// Update the seeker in database
			int result = dbController.updateSeekerPassword(currentUser, request.getParameter("new_password"));
			
			//checking for exception when updating
			if (result > 0) {
				request.setAttribute("isUpdate", 1);
				request.getRequestDispatcher(StringUtils.PAGE_URL_WELCOME).include(request, response);
			} else {
				// Internal server error
				request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
			}
        }
		
	}

}
