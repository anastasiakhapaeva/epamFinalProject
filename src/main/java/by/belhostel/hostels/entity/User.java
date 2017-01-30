package by.belhostel.hostels.entity;

/**
 * Created by Roman on 07.12.2016.
 */
public class User extends Entity {

    /** The user id. */
    private int userId;

    /** The username. */
    private String username;

    /** The password. */
    private String password;

    /** The profile. */
    private UserProfile profile;

    /** The money. */
    private double money = 0.0;

    /** The discount. */
    private double discount = 0.0;

    /** The is admin. */
    private boolean isAdmin;

    /** The is banned. */
    private boolean isBanned;

    /**
     * Instantiates a new user.
     */
    public User() {
    }

    /**
     * Gets the profile.
     *
     * @return the profile
     */
    public UserProfile getProfile() {
        return profile;
    }

    /**
     * Sets the profile.
     *
     * @param profile the new profile
     */
    public void setProfile(UserProfile profile) {
        this.profile = profile;
    }

    /**
     * Gets the money.
     *
     * @return the money
     */
    public double getMoney() {
        return money;
    }

    /**
     * Sets the money.
     *
     * @param money the new money
     */
    public void setMoney(double money) {
        this.money = money;
    }

    /**
     * Instantiates a new user.
     *
     * @param userId the user id
     * @param username the username
     * @param password the password
     */
    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * Instantiates a new user.
     *
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }



    /**
     * Gets the user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     *
     * @param userId the new user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     *
     * @param username the new username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the discount.
     *
     * @return the discount
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount.
     *
     * @param discount the new discount
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }

    /**
     * Checks if is admin.
     *
     * @return true, if is admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Sets the admin.
     *
     * @param admin the new admin
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Checks if is banned.
     *
     * @return true, if is banned
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Sets the banned.
     *
     * @param banned the new banned
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
