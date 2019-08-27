package com.example.zoom.Service;

import com.example.zoom.entity.AuthUser;

public interface UserService  {

    AuthUser findUserByEmailAddress(String email);
}
