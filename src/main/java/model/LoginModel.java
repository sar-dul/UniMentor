package model;

/**
 * Model class representing a user login.
 */
public class LoginModel {
    
    // Private fields for username and password
    private String username;
    private String password;
    
    /**
     * Constructs a new LoginModel object with the given username and password.
     *
     * @param username The username of the user
     * @param password The password of the user
     */
    public LoginModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the username of the user.
     *
     * @return The username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     *
     * @return The password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The new password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }   
}

