package domain;

import java.io.Serializable;

public class User implements Entity<Long>, Serializable {
    private Long id;
    private String username;
    private String password;

    public User(){

    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for the username of a {@code User}
     * @return username: String, the username of the current {@code User}
     */
    public String getUsername() {
        return username;
    }

    /**
     * Getter for the password of a {@code User}
     * @return password: String, the password of the current {@code User}
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the username of a {@code User}
     * @param username: String, the desired username
     * username of the current {@code User} is set to {@param username}
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for the password of a {@code User}
     * @param password: String, the desired password
     * password of the current {@code User} is set to {@param password}
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

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long aLong) {
        this.id = aLong;
    }
}
