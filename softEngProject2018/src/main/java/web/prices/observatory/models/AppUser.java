package web.prices.observatory.models;

import javax.persistence.*;

@Entity
@Table(name="users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "email" )
    private String email;
    @Column(name="password_hash")
    private String password;
    @Column(name = "enabled")
    private Boolean isEnabled;
    @Column(name = "admin")
    private Boolean isAdmin;
    @Column(name = "jwt")
    private String jwt;

    public AppUser(){}

    public AppUser(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AppUser(long id, String username, String email, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getJwt() {return jwt;}

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
