package eformer.front.eformer_frontend.model;

import eformer.front.eformer_frontend.connectors.UsersConnector;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class User {
    private Integer userId;

    private String username;

    private String fullName;

    private String email;

    private String password;

    private Integer adLevel;

    private LocalDateTime createTime;

    private String role;

    public User(String username, String fullName, String email,
                String password, Integer adLevel) {
        setUsername(username);
        setFullName(fullName);
        setEmail(email);
        setPassword(password);
        setAdLevel(adLevel);
        setRole("");
    }

    public User(JSONObject json) {
        UsersConnector.mapToObject(json, this);
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdLevel() {
        return adLevel;
    }

    public void setAdLevel(Integer adLevel) {
        this.adLevel = adLevel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setCreateTime(String createTime) {
        setCreateTime(LocalDateTime.parse(createTime));
    }

    public boolean isCustomer() {
        return getAdLevel() == 0;
    }

    public boolean isManager() {
        return getAdLevel() >= 2;
    }

    public boolean isGuest() {
        return getAdLevel() < 0 || getUserId() < 0;
    }

    public boolean isEmployee() {
        return getAdLevel() >= 1;
    }

    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }
}
