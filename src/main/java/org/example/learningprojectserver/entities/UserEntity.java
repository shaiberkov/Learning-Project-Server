package org.example.learningprojectserver.entities;

import jakarta.persistence.*;
import org.example.learningprojectserver.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String userId;
    @Transient
    private String password;
    @Transient
    private String passwordConfirm;
    @Column(nullable = false)
    private String passwordHash;
    @Column(nullable = false)
    private String salt;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @Column(name = "otp")
    private String otp;
    @Column(name="otpTimestamp")
    private Long otpTimestamp;
//    @Column(nullable = false)
//    private int age;
    @Column(nullable = true)
    private String profilePicture;
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
    private List<Session> sessionList=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public String getUserId() {
        return userId;
    }

    public List<Session> getSessionList() {
        return sessionList;
    }

    public void setSessionList(List<Session> sessionList) {
        this.sessionList = sessionList;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private UserProgressEntity userProgressEntity;
//
//
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private QuestionHistoryEntity questionHistoryEntity;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<TestResultEntity> userTestsResult;
//    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER ,cascade = CascadeType.ALL)
//    private List<TestEntity> userTests;





//    public UserProgressEntity getUserProgressEntitiy() {
//        return userProgressEntity;
//    }
//
//    public void setUserProgressEntitiy(UserProgressEntity userProgressEntity) {
//        this.userProgressEntity = userProgressEntity;
//    }

    public UserEntity() {
    }

//    public UserProgressEntity getUserProgressEntity() {
//        return userProgressEntity;
//    }
//
//    public void setUserProgressEntity(UserProgressEntity userProgressEntity) {
//        this.userProgressEntity = userProgressEntity;
//    }
//
//    public List<TestResultEntity> getUserTestsResult() {
//        return userTestsResult;
//    }
//
//    public void setUserTestsResult(List<TestResultEntity> userTestsResult) {
//        this.userTestsResult = userTestsResult;
//    }
//
//    public List<TestEntity> getUserTests() {
//        return userTests;
//    }
//
//    public void setUserTests(List<TestEntity> userTests) {
//        this.userTests = userTests;
//    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getPassword() {
        return password;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setOtpTimestamp(Long otpTimestamp) {
        this.otpTimestamp = otpTimestamp;
    }

    public Long getOtpTimestamp() {
        return otpTimestamp;
    }

    public String getSalt() {
        return salt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

//    public int getAge() {
//        return age;
//    }

//    public QuestionHistoryEntity getQuestionHistoryEntity() {
//        return questionHistoryEntity;
//    }
//
//    public void setQuestionHistoryEntity(QuestionHistoryEntity questionHistoryEntity) {
//        this.questionHistoryEntity = questionHistoryEntity;
//    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setAge(int age) {
//        this.age = age;
//    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", salt='" + salt + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", otp='" + otp + '\'' +
                ", otpTimestamp=" + otpTimestamp +
                ", profilePicture='" + profilePicture + '\'' +
//                ", userProgressEntity=" + userProgressEntity +
//                ", questionHistoryEntity=" + questionHistoryEntity +
                '}';
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}





