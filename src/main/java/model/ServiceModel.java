package model;

import java.time.LocalDate;

public class ServiceModel {
	
	private int id;
	private String title;
	private String description;
	private int price;
	private LocalDate creationDate;
	private float rating;
	private int users;
	private String status;
	private String mentorUsername;
	
	/**
     * Default constructor for ServiceModel.
     */
	public ServiceModel() {
	}
	
	/**
     * Constructs a ServiceModel with the specified parameters.
     *
     * @param id            The ID of the service
     * @param title         The title of the service
     * @param description   The description of the service
     * @param price         The price of the service
     * @param creationDate  The creation date of the service
     * @param rating        The rating of the service
     * @param users         The number of users of the service
     * @param status        The status of the service
     * @param mentorUsername The username of the mentor providing the service
     */
	public ServiceModel(int id, String title, String description, int price, LocalDate creationDate, float rating,
			int users, String status, String mentorUsername) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.creationDate = creationDate;
		this.rating = rating;
		this.users = users;
		this.status = status;
		this.mentorUsername = mentorUsername;
	}

	/**
	 * Retrieves the ID of the service.
	 *
	 * @return The ID of the service
	 */
	public int getId() {
	    return id;
	}

	/**
	 * Sets the ID of the service.
	 *
	 * @param id The ID of the service
	 */
	public void setId(int id) {
	    this.id = id;
	}

	/**
	 * Retrieves the title of the service.
	 *
	 * @return The title of the service
	 */
	public String getTitle() {
	    return title;
	}

	/**
	 * Sets the title of the service.
	 *
	 * @param title The title of the service
	 */
	public void setTitle(String title) {
	    this.title = title;
	}

	/**
	 * Retrieves the description of the service.
	 *
	 * @return The description of the service
	 */
	public String getDescription() {
	    return description;
	}

	/**
	 * Sets the description of the service.
	 *
	 * @param description The description of the service
	 */
	public void setDescription(String description) {
	    this.description = description;
	}

	/**
	 * Retrieves the price of the service.
	 *
	 * @return The price of the service
	 */
	public int getPrice() {
	    return price;
	}

	/**
	 * Sets the price of the service.
	 *
	 * @param price The price of the service
	 */
	public void setPrice(int price) {
	    this.price = price;
	}

	/**
	 * Retrieves the creation date of the service.
	 *
	 * @return The creation date of the service
	 */
	public LocalDate getCreationDate() {
	    return creationDate;
	}

	/**
	 * Sets the creation date of the service.
	 *
	 * @param creationDate The creation date of the service
	 */
	public void setCreationDate(LocalDate creationDate) {
	    this.creationDate = creationDate;
	}

	/**
	 * Retrieves the rating of the service.
	 *
	 * @return The rating of the service
	 */
	public float getRating() {
	    return rating;
	}

	/**
	 * Sets the rating of the service.
	 *
	 * @param rating The rating of the service
	 */
	public void setRating(float rating) {
	    this.rating = rating;
	}

	/**
	 * Retrieves the number of users of the service.
	 *
	 * @return The number of users of the service
	 */
	public int getUsers() {
	    return users;
	}

	/**
	 * Sets the number of users of the service.
	 *
	 * @param users The number of users of the service
	 */
	public void setUsers(int users) {
	    this.users = users;
	}

	/**
	 * Retrieves the status of the service.
	 *
	 * @return The status of the service
	 */
	public String getStatus() {
	    return status;
	}

	/**
	 * Sets the status of the service.
	 *
	 * @param status The status of the service
	 */
	public void setStatus(String status) {
	    this.status = status;
	}

	/**
	 * Retrieves the mentor's username providing the service.
	 *
	 * @return The mentor's username providing the service
	 */
	public String getMentorUsername() {
	    return mentorUsername;
	}

	/**
	 * Sets the mentor's username providing the service.
	 *
	 * @param mentorUsername The mentor's username providing the service
	 */
	public void setMentorUsername(String mentorUsername) {
	    this.mentorUsername = mentorUsername;
	}
		
}
