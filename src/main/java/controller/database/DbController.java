package controller.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import model.LoginModel;
import model.MentorModel;
import model.PasswordEncryptionWithAes;
import model.SeekerModel;
import model.ServiceModel;
import utils.StringUtils;

public class DbController {
	
	/**
	 * Establishes a connection to the database.
	 *
	 * @return a Connection object representing the database connection.
	 * @throws SQLException if a database access error occurs.
	 * @throws ClassNotFoundException if the JDBC driver class is not found.
	 */
	public Connection getConnection() throws SQLException, ClassNotFoundException {

		Class.forName(StringUtils.DRIVER_NAME);
		return DriverManager.getConnection(StringUtils.LOCALHOST_URL, StringUtils.LOCALHOST_USERNAME,
				StringUtils.LOCALHOST_PASSWORD);

	}
	
	
	/**
	 * Registers a mentor by adding their details to the database.
	 *
	 * @param mentor The MentorModel object containing the mentor's information.
	 * @return 1 if the registration is successful, 0 if registration failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int registerMentor(MentorModel mentor) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.REGISTER_MENTOR_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, mentor.getName());
			stmt.setString(2, mentor.getUsername());
			stmt.setString(3, mentor.getEmail());
			stmt.setString(4, mentor.getPhone());
			stmt.setString(5, PasswordEncryptionWithAes.encrypt(mentor.getUsername(), mentor.getPassword()));
			stmt.setString(6, mentor.getUniversityName());
			stmt.setString(7, mentor.getUniversityCountry());
			stmt.setString(8, mentor.getMajor());
			stmt.setInt(9, mentor.getTuitionFee());
			stmt.setString(10, mentor.getScholarship());
			stmt.setString(11, mentor.getProfilePhotoUrl());

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Registers a seeker by adding their details to the database.
	 *
	 * @param seeker The SeekerModel object containing the seeker's information.
	 * @return 1 if the registration is successful, 0 if registration failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int registerSeeker(SeekerModel seeker) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.REGISTER_SEEKER_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, seeker.getName());
			stmt.setString(2, seeker.getUsername());
			stmt.setString(3, seeker.getEmail());
			stmt.setString(4, seeker.getPhone());
			stmt.setString(5, PasswordEncryptionWithAes.encrypt(seeker.getUsername(), seeker.getPassword()));
			stmt.setString(6, seeker.getLocation());
			stmt.setString(7, seeker.getEducationLevel());
			stmt.setString(8, seeker.getProfilePhotoUrl());

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Validates the username and password provided during login.
	 *
	 * @param loginModel The LoginModel object containing the username and password.
	 * @return 1 if the login is successful for a mentor, 2 if successful for a seeker,
	 *         0 if the username or password is incorrect, and -1 if the username is not found,
	 *         -2 for internal errors.
	 */
	public int getLoginInfo(LoginModel loginModel) {

		// Try-catch block to handle potential SQL or ClassNotFound exceptions
		try {
			// Prepare a statement using the predefined query for mentor's login check
			PreparedStatement st_mentor = getConnection().prepareStatement(StringUtils.QUERY_MENTOR_CHECK);

			// Set the username in the first parameter of the prepared statement
			st_mentor.setString(1, loginModel.getUsername());

			// Execute the query and store the result set
			ResultSet result_m = st_mentor.executeQuery();

			// Check if there's a record returned from the query
			if (result_m.next()) {
				// Get the mentor's username from the database
				String userDb = result_m.getString(StringUtils.MENTOR_USERNAME);
				
				// Get the password from the database
				String passwordDb = result_m.getString(StringUtils.MENTOR_PASSWORD);

				// Decrypted password
				String decryptedPwd = PasswordEncryptionWithAes.decrypt(passwordDb, userDb);
				
				// Check if the username and password match the credentials from the database
				if (userDb.equals(loginModel.getUsername()) && decryptedPwd.equals(loginModel.getPassword())) {
					// Login successful, return 1
					return 1;
				} else {
					// Username or password mismatch, return 0
					return 0;
				}
			} else {
				// Username not found in the mentor table, try seeker table
				
				// Prepare a statement using the predefined query for mentor's login check
				PreparedStatement st_seeker = getConnection().prepareStatement(StringUtils.QUERY_SEEKER_CHECK);

				// Set the username in the first parameter of the prepared statement
				st_seeker.setString(1, loginModel.getUsername());

				// Execute the query and store the result set
				ResultSet result = st_seeker.executeQuery();

				// Check if there's a record returned from the query
				if (result.next()) {
					// Get the mentor's username from the database
					String userDb = result.getString(StringUtils.SEEKER_USERNAME);
					
					// Get the password from the database
					String passwordDb = result.getString(StringUtils.SEEKER_PASSWORD);

					// Decrypted password
					String decryptedPwd = PasswordEncryptionWithAes.decrypt(passwordDb, userDb);
					
					
					// Check if the username and password match the credentials from the database
					if (userDb.equals(loginModel.getUsername()) && decryptedPwd.equals(loginModel.getPassword())) {
						// Login successful, return 1
						return 2;
					} else {
						// Username or password mismatch, return 0
						return 0;
					}
				} else {
					// Username not found in the seeker table (both),  return -1
					return -1;
				}
					
			}

			// Catch SQLException and ClassNotFoundException if they occur
		} catch (SQLException | ClassNotFoundException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			// Return -2 to indicate an internal error
			return -2;
		}
	}
	
	
	/**
	 * Checks if the given value is present in the specified column of the mentor table.
	 *
	 * @param column The column in which to search for the value (username, email, or phone).
	 * @param value  The value to search for in the specified column.
	 * @return true if the value is found in the specified column, false otherwise.
	 */
	public boolean presentInMentor(String column, String value) {
		try {
			PreparedStatement stmt;

			// preparing statement for getting info on the basis of column i.e user name,
			// email or phone
			switch (column) {
			case "mentor_username":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_MENTOR_INFO_FROM_USERNAME);
				break;
			case "mentor_email":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_MENTOR_INFO_FROM_EMAIL);
				break;
			case "mentor_phone":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_MENTOR_INFO_FROM_PHONE);
				break;
			default:
				stmt = null;
				break;
			}

			// Set the user name, email or phone number value in the prepared statement
			stmt.setString(1, value);

			// Execute the query and store the result set
			ResultSet result = stmt.executeQuery();

			// Return true if value found false otherwise
			return result.next();

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return false; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return false; // Server error
		}
	}
	
	/**
	 * Checks if the given value is present in the specified column of the seeker table.
	 *
	 * @param column The column in which to search for the value (username, email, or phone).
	 * @param value  The value to search for in the specified column.
	 * @return true if the value is found in the specified column, false otherwise.
	 */
	public boolean presentInSeeker(String column, String value) {
		try {
			PreparedStatement stmt;

			// preparing statement for getting info on the basis of column i.e user name,
			// email or phone
			switch (column) {
			case "seeker_username":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_SEEKER_INFO_FROM_USERNAME);
				break;
			case "seeker_email":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_SEEKER_INFO_FROM_EMAIL);
				break;
			case "seeker_phone":
				stmt = getConnection().prepareStatement(StringUtils.GET_LOGIN_SEEKER_INFO_FROM_PHONE);
				break;
			default:
				stmt = null;
				break;
			}

			// Set the user name, email or phone number value in the prepared statement
			stmt.setString(1, value);

			// Execute the query and store the result set
			ResultSet result = stmt.executeQuery();

			// Return true if value found false otherwise
			return result.next();

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return false; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return false; // Server error
		}
	}
	
	
	/**
	 * Retrieves information for all services present in the service table.
	 *
	 * @return An ArrayList containing ServiceModel objects representing the services.
	 *         Returns null if an error occurs during database access.
	 */
	public ArrayList<ServiceModel> getAllServiceInfo(){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_ALL_SERVICES);
			ResultSet result = stmt.executeQuery();
			
			ArrayList<ServiceModel> services = new ArrayList<ServiceModel>();
			
			while(result.next()) {
				ServiceModel service = new ServiceModel();
				service.setId(result.getInt("service_id"));
				service.setTitle(result.getString("service_title"));
				service.setDescription(result.getString("description"));
				service.setPrice(result.getInt("price"));
				service.setCreationDate(result.getDate("creation_date").toLocalDate());
				service.setRating(result.getFloat("rating"));
				service.setUsers(result.getInt("service_users"));
				service.setStatus(result.getString("status"));
				service.setMentorUsername(result.getString("mentor_username"));	
				
				services.add(service);
			}
			return services;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Retrieves information for all services offered by a specific mentor.
	 *
	 * @param mentor_username The username of the mentor whose services are to be retrieved.
	 * @return An ArrayList containing ServiceModel objects representing the mentor's services.
	 *         Returns null if an error occurs during database access.
	 */
	public ArrayList<ServiceModel> getAllMentorServiceInfo(String mentor_username){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_MENTORS_SERVICES);
			
			// Set the username in the prepared statement
			stmt.setString(1, mentor_username);
						
			ResultSet result = stmt.executeQuery();
			
			ArrayList<ServiceModel> mentor_services = new ArrayList<ServiceModel>();
			
			while(result.next()) {
				ServiceModel mentor_service = new ServiceModel();
				mentor_service.setId(result.getInt("service_id"));
				mentor_service.setTitle(result.getString("service_title"));
				mentor_service.setDescription(result.getString("description"));
				mentor_service.setPrice(result.getInt("price"));
				mentor_service.setCreationDate(result.getDate("creation_date").toLocalDate());
				mentor_service.setRating(result.getFloat("rating"));
				mentor_service.setUsers(result.getInt("service_users"));
				mentor_service.setStatus(result.getString("status"));
				mentor_service.setMentorUsername(result.getString("mentor_username"));	
				
				mentor_services.add(mentor_service);
			}
			return mentor_services;
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Retrieves mentor details from the database based on the passed username.
	 *
	 * @param mentor_username The username of the mentor whose details are to be retrieved.
	 * @return A MentorModel object containing the mentor's details if found, or null if not found
	 *         or if an error occurs during database access.
	 */
	public MentorModel getMentorInfo(String mentor_username){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_MENTOR_INFO);
			
			// Set the username in the prepared statement
			stmt.setString(1, mentor_username);
						
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) { // Move cursor to the first row
			    MentorModel mentor_info = new MentorModel();
			    mentor_info.setName(result.getString("mentor_name"));
			    mentor_info.setUsername(result.getString("mentor_username"));
			    mentor_info.setEmail(result.getString("mentor_email"));
			    mentor_info.setPhone(result.getString("mentor_phone"));
			    mentor_info.setPassword(PasswordEncryptionWithAes.decrypt(result.getString("mentor_password"), mentor_username));
			    mentor_info.setUniversityName(result.getString("university_name"));
			    mentor_info.setUniversityCountry(result.getString("university_country"));
			    mentor_info.setMajor(result.getString("major"));
			    mentor_info.setTuitionFee(result.getInt("tuition_fee"));
			    mentor_info.setScholarship(result.getString("scholarship"));
			    mentor_info.setProfilePhotoUrlFromDb(result.getString("mentor_photo"));
			    
			    return mentor_info;
			
			}	
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Retrieves seeker details from the database based on the passed username.
	 *
	 * @param seeker_username The username of the seeker whose details are to be retrieved.
	 * @return A SeekerModel object containing the seeker's details if found, or null if not found
	 *         or if an error occurs during database access.
	 */
	public SeekerModel getSeekerInfo(String seeker_username){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_SEEKER_INFO);
			
			// Set the username in the prepared statement
			stmt.setString(1, seeker_username);
						
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) { // Move cursor to the first row
			    SeekerModel seeker_info = new SeekerModel();
			    seeker_info.setName(result.getString("seeker_name"));
			    seeker_info.setUsername(result.getString("seeker_username"));
			    seeker_info.setEmail(result.getString("seeker_email"));
			    seeker_info.setPhone(result.getString("seeker_phone"));
			    seeker_info.setPassword(PasswordEncryptionWithAes.decrypt(result.getString("seeker_password"), seeker_username));
			    seeker_info.setLocation(result.getString("seeker_location"));
			    seeker_info.setEducationLevel(result.getString("education_level"));
			    seeker_info.setProfilePhotoUrlFromDb(result.getString("seeker_photo"));
			    
			    return seeker_info;
			
			}	
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Retrieves the image name (URL) of a seeker based on the passed username.
	 *
	 * @param username The username of the seeker whose image name is to be retrieved.
	 * @return A string representing the image name (URL) of the seeker if found, or null if not found
	 *         or if an error occurs during database access.
	 */
	public String getSeekerImageName(String username){
		
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_SEEKER_IMAGE);
			
			// Set the username in the prepared statement
			stmt.setString(1, username);
						
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) { // Move cursor to the first row
			    return result.getString("seeker_photo");
			
			}	
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Retrieves the image name (URL) of a mentor based on the passed username.
	 *
	 * @param username The username of the mentor whose image name is to be retrieved.
	 * @return A string representing the image name (URL) of the mentor if found, or null if not found
	 *         or if an error occurs during database access.
	 */
	public String getMentorImageName(String username){
			
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_MENTOR_IMAGE);
			
			// Set the username in the prepared statement
			stmt.setString(1, username);
						
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) { // Move cursor to the first row
			    return result.getString("mentor_photo");
			
			}	
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Retrieves information for the top five mentors based on their average service rating.
	 *
	 * @return An ArrayList containing MentorModel objects representing the top five mentors.
	 *         Returns null if an error occurs during database access.
	 */
	public ArrayList<MentorModel> getTopMentorsInfo() {
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_TOP_MENTORS);
			
			ResultSet result = stmt.executeQuery();
			
			ArrayList<MentorModel> topMentors = new ArrayList<MentorModel>();
			
			while(result.next()) {
				MentorModel mentor = new MentorModel();
				
				mentor.setName(result.getString("mentor_name"));
			    mentor.setUsername(result.getString("mentor_username"));
			    mentor.setEmail(result.getString("mentor_email"));
			    mentor.setPhone(result.getString("mentor_phone"));
			    mentor.setPassword(result.getString("mentor_password"));
			    mentor.setUniversityName(result.getString("university_name"));
			    mentor.setUniversityCountry(result.getString("university_country"));
			    mentor.setMajor(result.getString("major"));
			    mentor.setTuitionFee(result.getInt("tuition_fee"));
			    mentor.setScholarship(result.getString("scholarship"));
			    mentor.setProfilePhotoUrlFromDb(result.getString("mentor_photo"));
			    
				topMentors.add(mentor);
			}
			return topMentors;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	
	/**
	 * Updates a mentor's details in the database based on the old username and the updated MentorModel.
	 *
	 * @param username The old username of the mentor to be updated.
	 * @param mentor   The MentorModel object containing the updated mentor's information.
	 * @return 1 if the update is successful, 0 if update failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int updateMentor(String username, MentorModel mentor) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.UPDATE_MENTOR_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, mentor.getName());
			stmt.setString(2, mentor.getUsername());
			stmt.setString(3, mentor.getEmail());
			stmt.setString(4, mentor.getPhone());
			stmt.setString(5, PasswordEncryptionWithAes.encrypt(mentor.getUsername(), mentor.getPassword()));
			stmt.setString(6, mentor.getProfilePhotoUrl());
			
			stmt.setString(7, username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Updates a seeker's details in the database based on the old username and the updated SeekerModel.
	 *
	 * @param username The old username of the seeker to be updated.
	 * @param seeker   The SeekerModel object containing the updated seeker's information.
	 * @return 1 if the update is successful, 0 if update failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int updateSeeker(String username, SeekerModel seeker) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.UPDATE_SEEKER_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, seeker.getName());
			stmt.setString(2, seeker.getUsername());
			stmt.setString(3, seeker.getEmail());
			stmt.setString(4, seeker.getPhone());
			stmt.setString(5, PasswordEncryptionWithAes.encrypt(seeker.getUsername(), seeker.getPassword()));
			stmt.setString(6, seeker.getProfilePhotoUrl());
			
			stmt.setString(7, username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Deletes a mentor from the mentor table based on the passed username.
	 *
	 * @param username The username of the mentor to be deleted.
	 * @return 1 if the deletion is successful, 0 if deletion failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int deleteMentor(String username) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.DELETE_MENTOR_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Deletes a seeker from the seeker table based on the passed username.
	 *
	 * @param username The username of the seeker to be deleted.
	 * @return 1 if the deletion is successful, 0 if deletion failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int deleteSeeker(String username) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.DELETE_SEEKER_QUERY);

			// Set the student information in the prepared statement			
			stmt.setString(1, username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	
	/**
	 * Adds a service to the database in the service table based on the passed ServiceModel and mentor username.
	 *
	 * @param service        The ServiceModel object representing the service to be added.
	 * @param mentor_username The username of the mentor offering the service.
	 * @return 1 if the addition is successful, 0 if addition failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int addService(ServiceModel service, String mentor_username) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.ADD_SERVICE_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, service.getTitle());
			stmt.setString(2, service.getDescription());
			stmt.setInt(3, service.getPrice());
			
			LocalDate currDate = LocalDate.now();
			stmt.setDate(4, Date.valueOf(currDate));
			
			stmt.setString(5, mentor_username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}


	/**
	 * Deletes a service from the database in the service table based on the passed service ID.
	 *
	 * @param service_id The ID of the service to be deleted.
	 * @return 1 if the deletion is successful, 0 if deletion failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int deleteService(int service_id) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.DELETE_SERVICE_QUERY);

			// Set the student information in the prepared statement
			stmt.setInt(1, service_id);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Updates a service in the database based on the passed ServiceModel and service ID.
	 *
	 * @param service   The updated ServiceModel object representing the service.
	 * @param service_id The ID of the service to be updated.
	 * @return 1 if the update is successful, 0 if update failed, -1 for internal errors,
	 *         and -2 for server errors.
	 */
	public int updateService(ServiceModel service, int service_id) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.UPDATE_SERVICE_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, service.getTitle());
			stmt.setString(2, service.getDescription());
			stmt.setInt(3, service.getPrice());
			
			stmt.setInt(4, service_id);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Retrieves service details from the database based on the given service ID.
	 *
	 * @param service_id The ID of the service to retrieve.
	 * @return A ServiceModel object representing the service details if found, otherwise null.
	 */
	public ServiceModel getServiceInfoFromId(int service_id){
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_GET_SERVICE_FROM_ID);
			
			// Set the username in the prepared statement
			stmt.setInt(1, service_id);
						
			ResultSet result = stmt.executeQuery();
			
			if (result.next()) { // Move cursor to the first row
			    ServiceModel service = new ServiceModel();
				service.setId(result.getInt("service_id"));
				service.setTitle(result.getString("service_title"));
				service.setDescription(result.getString("description"));
				service.setPrice(result.getInt("price"));
				service.setCreationDate(result.getDate("creation_date").toLocalDate());
				service.setRating(result.getFloat("rating"));
				service.setUsers(result.getInt("service_users"));
				service.setStatus(result.getString("status"));
				service.setMentorUsername(result.getString("mentor_username"));	
			    
			    return service;
			}	
			return null;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Searches for services in the database based on the provided service title.
	 *
	 * @param service_title The title of the service to search for.
	 * @return An ArrayList of ServiceModel objects representing the found services, or null if an error occurs.
	 */
	public ArrayList<ServiceModel> getSearchedServiceInfo(String service_title) {
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_SEARCH_ALL_SERVICE_FROM_TITLE);
			
			// Set the username in the prepared statement
			stmt.setString(1, service_title + "%");
						
			ResultSet result = stmt.executeQuery();
			
			ArrayList<ServiceModel> searched_services = new ArrayList<ServiceModel>();
			
			while(result.next()) {
				ServiceModel found_service = new ServiceModel();
				found_service.setId(result.getInt("service_id"));
				found_service.setTitle(result.getString("service_title"));
				found_service.setDescription(result.getString("description"));
				found_service.setPrice(result.getInt("price"));
				found_service.setCreationDate(result.getDate("creation_date").toLocalDate());
				found_service.setRating(result.getFloat("rating"));
				found_service.setUsers(result.getInt("service_users"));
				found_service.setStatus(result.getString("status"));
				found_service.setMentorUsername(result.getString("mentor_username"));	
				
				searched_services.add(found_service);
			}
			return searched_services;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	/**
	 * Searches for services offered by a specific mentor in the database based on the provided service title and mentor username.
	 *
	 * @param service_title   The title of the service to search for.
	 * @param mentor_username The username of the mentor offering the service.
	 * @return An ArrayList of ServiceModel objects representing the found services, or null if an error occurs.
	 */
	public ArrayList<ServiceModel> getSearchedServiceInfoMentor(String service_title, String mentor_username) {	
		try {
			PreparedStatement stmt = getConnection()
					.prepareStatement(StringUtils.QUERY_SEARCH_ALL_MENTOR_SERVICE_FROM_TITLE);
			
			// Set the username in the prepared statement
			stmt.setString(1, mentor_username);
			stmt.setString(2, service_title + "%");
						
			ResultSet result = stmt.executeQuery();
			
			ArrayList<ServiceModel> searched_services = new ArrayList<ServiceModel>();
			
			while(result.next()) {
				ServiceModel found_service = new ServiceModel();
				found_service.setId(result.getInt("service_id"));
				found_service.setTitle(result.getString("service_title"));
				found_service.setDescription(result.getString("description"));
				found_service.setPrice(result.getInt("price"));
				found_service.setCreationDate(result.getDate("creation_date").toLocalDate());
				found_service.setRating(result.getFloat("rating"));
				found_service.setUsers(result.getInt("service_users"));
				found_service.setStatus(result.getString("status"));
				found_service.setMentorUsername(result.getString("mentor_username"));	
				
				searched_services.add(found_service);
			}
			return searched_services;
			
		}catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
			
		}
	}
	
	
	/**
	 * Updates the password of a mentor in the database.
	 *
	 * @param username     The username of the mentor whose password is to be updated.
	 * @param new_password The new password to set for the mentor.
	 * @return 1 if the password update is successful, 0 if update failed, -1 for internal errors, and -2 for server errors.
	 */
	public int updateMentorPassword(String username, String new_password) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.UPDATE_MENTOR_PASSWORD_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, PasswordEncryptionWithAes.encrypt(username, new_password));
			stmt.setString(2, username);
			

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
	
	/**
	 * Updates the password of a seeker in the database.
	 *
	 * @param username     The username of the seeker whose password is to be updated.
	 * @param new_password The new password to set for the seeker.
	 * @return 1 if the password update is successful, 0 if update failed, -1 for internal errors, and -2 for server errors.
	 */
	public int updateSeekerPassword(String username, String new_password) {
		try {
			// Prepare a statement using the predefined query for mentor registration
			PreparedStatement stmt = getConnection().prepareStatement(StringUtils.UPDATE_SEEKER_PASSWORD_QUERY);

			// Set the student information in the prepared statement
			stmt.setString(1, PasswordEncryptionWithAes.encrypt(username, new_password));
			stmt.setString(2, username);

			// Execute the update statement and store the number of affected rows
			int result = stmt.executeUpdate();

			// Check if the update was successful (i.e., at least one row affected)
			if (result > 0) {
				return 1; // Registration successful
			} else {
				return 0; // Registration failed (no rows affected)
			}

		} catch (ClassNotFoundException | SQLException ex) {
			// Print the stack trace for debugging purposes
			ex.printStackTrace();
			return -1; // Internal error
		} catch (Exception ex) {
			ex.printStackTrace();
			return -2; // Server error
		}
	}
		
}
