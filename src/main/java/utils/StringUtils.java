package utils;

public class StringUtils {
	
	// Start: DB Connection
		public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
		public static final String LOCALHOST_URL = "jdbc:mysql://localhost:3306/uni_mentor";
		public static final String LOCALHOST_USERNAME = "root";
		public static final String LOCALHOST_PASSWORD = "";
		
		public static final String IMAGE_ROOT_PATH = "/Users/sardulojha/eclipse-workspace/UniMentor/src/main/webapp/resources/";
		public static final String IMAGE_DIR_USER = IMAGE_ROOT_PATH + "users/";

	// End: DB Connection

	// Start: Queries
		public static final String REGISTER_MENTOR_QUERY = "INSERT INTO mentor ("
				+ "mentor_name, mentor_username, mentor_email, mentor_phone, mentor_password, university_name, university_country, major, tuition_fee, scholarship, mentor_photo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		public static final String REGISTER_SEEKER_QUERY = "INSERT INTO seeker ("
				+ "seeker_name, seeker_username, seeker_email, seeker_phone, seeker_password, seeker_location, education_level, seeker_photo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		public static final String UPDATE_SEEKER_QUERY = "UPDATE seeker SET seeker_name = ?, seeker_username = ?, seeker_email = ?, seeker_phone = ?, seeker_password = ?, seeker_photo = ? WHERE seeker_username = ?";
		public static final String UPDATE_MENTOR_QUERY = "UPDATE mentor SET mentor_name = ?, mentor_username = ?, mentor_email = ?, mentor_phone = ?, mentor_password = ?, mentor_photo = ? WHERE mentor_username = ?";
		public static final String UPDATE_MENTOR_PASSWORD_QUERY = "UPDATE mentor SET mentor_password = ? WHERE mentor_username = ?";
		public static final String UPDATE_SEEKER_PASSWORD_QUERY = "UPDATE seeker SET seeker_password = ? WHERE seeker_username = ?";
		
		public static final String DELETE_MENTOR_QUERY = "DELETE FROM mentor WHERE mentor_username = ?";
		public static final String DELETE_SEEKER_QUERY = "DELETE FROM seeker WHERE seeker_username = ?";
		
		public static final String ADD_SERVICE_QUERY = "INSERT INTO service (service_title, description, price, creation_date, rating, service_users, status, mentor_username) VALUES (?, ?, ?, ?, 0, 0, \"Active\", ?)";
		public static final String DELETE_SERVICE_QUERY = "DELETE FROM service WHERE service_id = ?";
		public static final String UPDATE_SERVICE_QUERY = "UPDATE service SET service_title = ?, description = ?, price = ? WHERE service_id = ?";
		
		public static final String QUERY_MENTOR_CHECK = "SELECT * FROM mentor WHERE mentor_username = ?";
		public static final String QUERY_SEEKER_CHECK = "SELECT * FROM seeker WHERE seeker_username = ?";
		
		public static final String GET_LOGIN_MENTOR_INFO_FROM_USERNAME = "SELECT * FROM mentor WHERE mentor_username = ?";
		public static final String GET_LOGIN_MENTOR_INFO_FROM_EMAIL = "SELECT * FROM mentor WHERE mentor_email = ?";
		public static final String GET_LOGIN_MENTOR_INFO_FROM_PHONE = "SELECT * FROM mentor WHERE mentor_phone = ?";
		
		public static final String GET_LOGIN_SEEKER_INFO_FROM_USERNAME = "SELECT * FROM seeker WHERE seeker_username = ?";
		public static final String GET_LOGIN_SEEKER_INFO_FROM_EMAIL = "SELECT * FROM seeker WHERE seeker_email = ?";
		public static final String GET_LOGIN_SEEKER_INFO_FROM_PHONE = "SELECT * FROM seeker WHERE seeker_phone = ?";
		
		public static final String QUERY_GET_ALL_SERVICES = "SELECT * FROM service";
		public static final String QUERY_GET_SERVICE_FROM_ID = "SELECT * FROM service WHERE service_id = ?";
		public static final String QUERY_SEARCH_ALL_SERVICE_FROM_TITLE = "SELECT * FROM service WHERE service_title LIKE ?";
		public static final String QUERY_SEARCH_ALL_MENTOR_SERVICE_FROM_TITLE = "SELECT * FROM service WHERE mentor_username = ? AND service_title LIKE ?";
		public static final String QUERY_GET_MENTOR_INFO = "SELECT * FROM mentor WHERE mentor_username = ?";
		public static final String QUERY_GET_SEEKER_INFO = "SELECT * FROM seeker WHERE seeker_username = ?";
		public static final String QUERY_GET_MENTORS_SERVICES = "SELECT * FROM service WHERE mentor_username = ?";
		public static final String QUERY_GET_TOP_MENTORS = "SELECT * FROM mentor WHERE mentor_username IN (SELECT mentor_username FROM ( SELECT mentor_username, AVG(rating) AS average_rating FROM service GROUP BY mentor_username ORDER BY average_rating DESC LIMIT 5 ) AS top_mentors);";
		
		public static final String QUERY_GET_SEEKER_IMAGE = "SELECT seeker_photo FROM seeker WHERE seeker_username = ?";
		public static final String QUERY_GET_MENTOR_IMAGE = "SELECT mentor_photo FROM mentor WHERE mentor_username = ?";
		
		
		
	// End: Queries

		
	// Start: Parameter names
	
		// Register Mentor Parameter names
		public static final String MENTOR_NAME = "mentor_name";
		public static final String MENTOR_USERNAME = "mentor_username";
		public static final String MENTOR_EMAIL = "mentor_email";
		public static final String MENTOR_PHONE_NUMBER = "mentor_phonenumber";
		public static final String MENTOR_PASSWORD = "mentor_password";
		public static final String MENTOR_RETYPE_PASSWORD = "mentor_retypepassword";
		public static final String MENTOR_UNIVERSITY_NAME = "mentor_universityname";
		public static final String MENTOR_UNIVERSITY_COUNTRY = "mentor_universitycountry";
		public static final String MENTOR_MAJOR = "mentor_major";
		public static final String MENTOR_TUITION_FEE = "mentor_tuitionfee";
		public static final String MENTOR_SCHOLARSHIP = "mentor_scholarship";
		public static final String MENTOR_PROFILE_PHOTO = "mentor_profilephoto";
		
		// Register Seeker Parameter names
		public static final String SEEKER_NAME = "seeker_name";
		public static final String SEEKER_USERNAME = "seeker_username";
		public static final String SEEKER_EMAIL = "seeker_email";
		public static final String SEEKER_PHONE_NUMBER = "seeker_phonenumber";
		public static final String SEEKER_PASSWORD = "seeker_password";
		public static final String SEEKER_RETYPE_PASSWORD = "seeker_retypepassword";
		public static final String SEEKER_LOCATION = "seeker_location";
		public static final String SEEKER_EDUCATION_LEVEL = "seeker_educationlevel";
		public static final String SEEKER_PROFILE_PHOTO = "seeker_profilephoto";
		
		// Login Parameter names
		public static final String USERNAME = "username";
		public static final String PASSWORD = "password";

		
	// End: Parameter names

		
	// Start: Validation Messages
		
		// Register Page Messages
		public static final String MESSAGE_SUCCESS_REGISTER = "Successfully Registered!";
		public static final String MESSAGE_ERROR_NAMES = "Enter valid  name (No numbers or special characters).";
		public static final String MESSAGE_ERROR_USERNAME = "Enter valid username (More than 6 characters, No special characters except underscore \"_\").";
		public static final String MESSAGE_ERROR_PHONE_NUMBER = "Enter valid phone number (Format: +977-9XXXXXXXXX).";
		public static final String MESSAGE_ERROR_PASSWORD = "Password must include atleast one uppercase, lowercase and special character (More than 6 characters).";	
		public static final String MESSAGE_ERROR_USERNAME_EXISTS = "Username is already registered.";
		public static final String MESSAGE_ERROR_EMAIL_EXISTS = "Email is already registered.";
		public static final String MESSAGE_ERROR_PHONE_NUMBER_EXISTS = "Phone number is already registered.";
		public static final String MESSAGE_ERROR_PASSWORD_UNMATCHED = "Password is not matched.";
		public static final String MESSAGE_ERROR_TUITION = "Enter valid tuition fee (Only Numbers).";
		public static final String MESSAGE_ERROR_IMAGE = "Enter proper image file (jpg, jpeg , png, gif,).";
		public static final String MESSAGE_ERROR_PRICE = "Enter valid price (Only Numbers).";
		public static final String MESSAGE_ERROR_INCORRECT_DATA = "Please correct all the fields.";
		public static final String MESSAGE_ERROR_INCORRECT_OLD_PASSWORD = "Old password does not match.";

		// Login Page Messages
		public static final String MESSAGE_SUCCESS_LOGIN = "Successfully LoggedIn!";
		public static final String MESSAGE_ERROR_LOGIN = "Either username or password is not correct!";
		public static final String MESSAGE_ERROR_CREATE_ACCOUNT = "Account for this username is not registered! Please create a new account.";

		// Other Messages
		public static final String MESSAGE_ERROR_SERVER = "An unexpected server error occurred.";
		public static final String MESSAGE_SUCCESS_DELETE = "Successfully Deleted!";
		public static final String MESSAGE_ERROR_DELETE = "Cannot delete the user!";

		public static final String MESSAGE_SUCCESS = "successMessage";
		public static final String MESSAGE_ERROR = "errorMessage";
		
	// End: Validation Messages

		
	// Start: JSP Route
		public static final String PAGE_URL_LOGIN = "/pages/login.jsp";
		public static final String PAGE_URL_REGISTER_MENTOR = "/pages/register-mentor.jsp";
		public static final String PAGE_URL_REGISTER_SEEKER = "/pages/register-seeker.jsp";
		public static final String PAGE_URL_WELCOME = "/pages/welcome.jsp";
		public static final String PAGE_URL_FOOTER = "/pages/footer.jsp";
		public static final String PAGE_URL_HEADER = "/pages/header.jsp";
		public static final String PAGE_URL_SERVICES = "/pages/services.jsp";
		public static final String PAGE_URL_PROFILE = "/pages/profile.jsp";
		public static final String PAGE_URL_ADD = "/pages/add.jsp";
		public static final String PAGE_URL_UPDATE = "/pages/update.jsp";
		
		public static final String URL_LOGIN = "/login.jsp";
		public static final String URL_INDEX = "/index.jsp";
		public static final String URL_WELCOME = "/welcome.jsp";
		public static final String URL_REGISTER_MENTOR = "/register-mentor.jsp";
		public static final String URL_REGISTER_SEEKER = "/register-seeker.jsp";
		public static final String URL_PROFILE = "/profile.jsp";
		public static final String URL_SERVICE = "/services.jsp";
		public static final String URL_HEADER = "/header.jsp";
		public static final String URL_FOOTER = "/footer.jsp";
		public static final String URL_ADD = "/add.jsp";
		public static final String URL_UPDATE = "/update.jsp";
	// End: JSP Route

		
	// Start: Servlet Route
		public static final String SERVLET_URL_LOGIN = "/login";
		public static final String SERVLET_URL_REGISTER_MENTOR = "/registermentor";
		public static final String SERVLET_URL_REGISTER_SEEKER = "/registerseeker";
		public static final String SERVLET_URL_LOGOUT = "/logoutuser";
		public static final String SERVLET_URL_SERVICES = "/services";
		public static final String SERVLET_URL_INDEX = "/index";
		public static final String SERVLET_URL_PROFILE = "/profile";
		public static final String SERVLET_URL_ADD_SERVICE = "/addservice";
		public static final String SERVLET_URL_MODIFY = "/modify";
		public static final String SERVLET_URL_UPDATE_PASSWORD = "/updatepassword";
	// End: Servlet Route

		
	// Start: Normal Text
		public static final String SERVICE_LISTS = "serviceLists";
		public static final String MENTOR_SERVICE_LISTS = "mentorServiceLists";
		public static final String TOP_MENTORS_LISTS = "topMentorsLists";
		
	// End: Normal Text

}
