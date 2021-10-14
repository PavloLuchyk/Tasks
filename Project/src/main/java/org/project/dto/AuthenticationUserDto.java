package org.project.dto;


public class AuthenticationUserDto {
    private String username;
    private String password;

    public AuthenticationUserDto() {
    }

    public AuthenticationUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public AuthenticationUserDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthenticationUserDto setPassword(String password) {
        this.password = password;
        return this;
    }
}
