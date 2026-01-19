package com.projet.backendproject.dto;

import com.projet.backendproject.model.Role;

public class UserRequest {
    public String name;
    public String email;
    public String password;
    public Role role;
    public Boolean isActive;
}
