package model;

import java.io.File;

import javax.servlet.http.Part;

import utils.StringUtils;

public class SeekerModel {
	
	private String name;
	private String username;
	private String email;
	private String phone;
	private String password;
	private String location;
	private String educationLevel;
	private String profilePhotoUrl;
	
	/**
     * Default constructor for SeekerModel.
     */
    public SeekerModel() {
    }

    /**
     * Constructs a SeekerModel with the specified parameters.
     *
     * @param name           The name of the seeker
     * @param username       The username of the seeker
     * @param email          The email of the seeker
     * @param phone          The phone number of the seeker
     * @param password       The password of the seeker
     * @param location       The location of the seeker
     * @param educationLevel The education level of the seeker
     * @param imagePart      The profile photo part of the seeker
     */
    public SeekerModel(String name, String username, String email, String phone, String password, String location,
            String educationLevel, Part imagePart) {
        super();
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.location = location;
        this.educationLevel = educationLevel;
        this.profilePhotoUrl = getImageUrl(imagePart);
    }

    /**
     * Returns the name of the seeker.
     *
     * @return The name of the seeker
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the seeker.
     *
     * @param name The name of the seeker to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the username of the seeker.
     *
     * @return The username of the seeker
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the seeker.
     *
     * @param username The username of the seeker to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the email of the seeker.
     *
     * @return The email of the seeker
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the seeker.
     *
     * @param email The email of the seeker to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the seeker.
     *
     * @return The phone number of the seeker
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the seeker.
     *
     * @param phone The phone number of the seeker to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the password of the seeker.
     *
     * @return The password of the seeker
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the seeker.
     *
     * @param password The password of the seeker to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the location of the seeker.
     *
     * @return The location of the seeker
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the seeker.
     *
     * @param location The location of the seeker to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns the education level of the seeker.
     *
     * @return The education level of the seeker
     */
    public String getEducationLevel() {
        return educationLevel;
    }

    /**
     * Sets the education level of the seeker.
     *
     * @param educationLevel The education level of the seeker to set
     */
    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    /**
     * Returns the profile photo URL of the seeker.
     *
     * @return The profile photo URL of the seeker
     */
    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    /**
     * Sets the profile photo URL of the seeker from the provided Part.
     *
     * @param part The Part containing the profile photo of the seeker
     */
    public void setProfilePhotoUrlFromPart(Part part) {
        this.profilePhotoUrl = getImageUrl(part);
    }
    
    /**
     * Sets the profile photo URL of the seeker from the provided URL.
     *
     * @param profilePhotoUrl The profile photo URL of the seeker to set
     */
	public void setProfilePhotoUrlFromDb(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}
	
	/**
     * Utility method to extract the image URL from a Part object.
     *
     * @param part The Part object representing the seeker's profile photo
     * @return The URL of the seeker's profile photo
     */
	private String getImageUrl(Part part) {
		String savePath = StringUtils.IMAGE_DIR_USER;
		File fileSaveDir = new File(savePath);
		String imageUrlFromPart = null;
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				imageUrlFromPart = s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		
		if (imageUrlFromPart == null || imageUrlFromPart.isEmpty()) {
			imageUrlFromPart = "default-profile.png";
		}
		return imageUrlFromPart;
	}
	
}
