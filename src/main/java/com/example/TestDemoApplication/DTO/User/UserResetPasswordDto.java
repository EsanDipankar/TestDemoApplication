package com.example.TestDemoApplication.DTO.User;

public class UserResetPasswordDto {
    private String userId;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String newCnfPassword;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewCnfPassword() {
        return newCnfPassword;
    }

    public void setNewCnfPassword(String newCnfPassword) {
        this.newCnfPassword = newCnfPassword;
    }



}
