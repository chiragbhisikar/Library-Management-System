package com.chiragbhisikar.Library.Management.System.Service.User;

import com.chiragbhisikar.Library.Management.System.DTO.UserDto;
import com.chiragbhisikar.Library.Management.System.Model.User;
import com.chiragbhisikar.Library.Management.System.Request.User.CreateUserRequest;
import com.chiragbhisikar.Library.Management.System.Request.User.UserUpdateRequest;

public interface iUserService {
    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
