package com.example.Fashionecomerce.service.user;

import com.example.Fashionecomerce.dtos.UserDto;
import com.example.Fashionecomerce.model.User;
import com.example.Fashionecomerce.request.CreateUserRequest;
import com.example.Fashionecomerce.request.UserUpdateRequest;

public interface IUserService {
    User
    createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    User getUserById(Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
