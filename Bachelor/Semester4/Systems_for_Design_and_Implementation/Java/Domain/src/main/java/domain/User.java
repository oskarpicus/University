package domain;

public class User extends Entity<Long>{
    private String username;
    private String password;

    public User(Long aLong, String username, String password) {
        super(aLong);
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for the username of a User
     * @return username: String, the username of the current User
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username of a User
     * @param username: String, the desired username
     * username of the current User is set to {@param username}
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password of a User
     * @return password: String, the password of the current User
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of a User
     * @param password: String, the desired password
     * password of the current User is set to {@param password}
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)
            return true;
        if(obj instanceof User){
            User user = (User)obj;
            return this.getId().equals(user.getId()) && this.getUsername().equals(user.getUsername()) && this.getPassword().equals(user.getPassword());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString()+" "+getUsername()+" "+getPassword();
    }
}
