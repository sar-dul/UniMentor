package model;

import java.io.File;

import javax.servlet.http.Part;

import utils.StringUtils;

public class MentorModel {

	private String name;
	private String username;
	private String email;
	private String phone;
	private String password;
	private String universityName;
	private String universityCountry;
	private String major;
	private int tuitionFee;
	private String scholarship;
	private String profilePhotoUrl;
	
	/**
     * Default constructor for MentorModel.
     */
	public MentorModel() {
	}
	
	/**
     * Constructs a MentorModel object with the specified attributes.
     *
     * @param name              The name of the mentor
     * @param username          The username of the mentor
     * @param email             The email of the mentor
     * @param phone             The phone number of the mentor
     * @param password          The password of the mentor
     * @param universityName    The name of the mentor's university
     * @param universityCountry The country of the mentor's university
     * @param major             The major of the mentor
     * @param tuitionFee        The tuition fee of the mentor
     * @param scholarship       The scholarship information of the mentor
     * @param imagePart         The image part for the mentor's profile photo
     */
	public MentorModel(String name, String username, String email, String phone, String password, String universityName,
			String universityCountry, String major, int tuitionFee, String scholarship, Part imagePart) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.universityName = universityName;
		this.universityCountry = universityCountry;
		this.major = major;
		this.tuitionFee = tuitionFee;
		this.scholarship = scholarship;
		this.profilePhotoUrl = getImageUrl(imagePart);
	}

	/**
     * Returns the name of the mentor.
     *
     * @return The name of the mentor
     */
	public String getName() {
		return name;
	}

	/**
     * Sets the name of the mentor.
     *
     * @param name The name of the mentor to set
     */
	public void setName(String name) {
		this.name = name;
	}

	/**
     * Returns the username of the mentor.
     *
     * @return The username of the mentor
     */
	public String getUsername() {
		return username;
	}

	/**
     * Sets the username of the mentor.
     *
     * @param username The username of the mentor to set
     */
	public void setUsername(String username) {
		this.username = username;
	}

	
	/**
     * Returns the email of the mentor.
     *
     * @return The email of the mentor
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the mentor.
     *
     * @param email The email of the mentor to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the mentor.
     *
     * @return The phone number of the mentor
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the mentor.
     *
     * @param phone The phone number of the mentor to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the password of the mentor.
     *
     * @return The password of the mentor
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the mentor.
     *
     * @param password The password of the mentor to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the name of the mentor's university.
     *
     * @return The name of the mentor's university
     */
    public String getUniversityName() {
        return universityName;
    }

    /**
     * Sets the name of the mentor's university.
     *
     * @param universityName The name of the mentor's university to set
     */
    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    /**
     * Returns the country of the mentor's university.
     *
     * @return The country of the mentor's university
     */
    public String getUniversityCountry() {
        return universityCountry;
    }

    /**
     * Sets the country of the mentor's university.
     *
     * @param universityCountry The country of the mentor's university to set
     */
    public void setUniversityCountry(String universityCountry) {
        this.universityCountry = universityCountry;
    }

    /**
     * Returns the major of the mentor.
     *
     * @return The major of the mentor
     */
    public String getMajor() {
        return major;
    }

    /**
     * Sets the major of the mentor.
     *
     * @param major The major of the mentor to set
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Returns the tuition fee of the mentor.
     *
     * @return The tuition fee of the mentor
     */
    public int getTuitionFee() {
        return tuitionFee;
    }

    /**
     * Sets the tuition fee of the mentor.
     *
     * @param tuitionFee The tuition fee of the mentor to set
     */
    public void setTuitionFee(int tuitionFee) {
        this.tuitionFee = tuitionFee;
    }

    /**
     * Returns the scholarship information of the mentor.
     *
     * @return The scholarship information of the mentor
     */
    public String getScholarship() {
        return scholarship;
    }

    /**
     * Sets the scholarship information of the mentor.
     *
     * @param scholarship The scholarship information of the mentor to set
     */
    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }
	
    /**
     * Returns the profile photo url of the mentor.
     *
     * @return The profile photo url of the mentor
     */
	public String getProfilePhotoUrl() {
		return profilePhotoUrl;
	}

	/**
     * Sets the profile photo URL of the mentor using a Part object.
     *
     * @param part The Part object representing the mentor's profile photo
     */
	public void setProfilePhotoUrlFromPart(Part part) {
		this.profilePhotoUrl = getImageUrl(part);
	}
	
	/**
     * Sets the profile photo URL of the mentor from a database value.
     *
     * @param profilePhotoUrl The URL of the mentor's profile photo from the database
     */
	public void setProfilePhotoUrlFromDb(String profilePhotoUrl) {
		this.profilePhotoUrl = profilePhotoUrl;
	}
	
	/**
     * Utility method to extract the image URL from a Part object.
     *
     * @param part The Part object representing the mentor's profile photo
     * @return The URL of the mentor's profile photo
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
