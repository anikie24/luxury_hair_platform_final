package za.ac.cput.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String email;
    private String password;
    private String fullName;
    private String userType;

    public UserLogin() {}


    private UserLogin(Builder builder) {
        this.userId = builder.userId;
        this.email = builder.email;
        this.password = builder.password;
        this.fullName = builder.fullName;
        this.userType = builder.userType;
    }

    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Long getUserId() { return userId; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public String getFullName() { return fullName; }

    public String getUserType() {
        return userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(userId, userLogin.userId) && Objects.equals(email, userLogin.email) && Objects.equals(password, userLogin.password) && Objects.equals(fullName, userLogin.fullName) && Objects.equals(userType, userLogin.userType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, password, fullName, userType);
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }

    public void setPassword(String encode) {
        this.password = encode;
    }

    public static class Builder {
        private Long userId;
        private String email;
        private String password;
        private String fullName;
        private String userType;

        public Builder setUserId(Long id) {
            this.userId = id;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setUserType(String userType) {
            this.userType = userType;
            return this;
        }

        public Builder copy(UserLogin userLogin){
            this.userId = userLogin.userId;
            this.email = userLogin.email;
            this.password = userLogin.password;
            this.fullName = userLogin.fullName;
            this.userType = userLogin.userType;
            return this;
        }

        public UserLogin build() {
            return new UserLogin(this);
        }
    }
}