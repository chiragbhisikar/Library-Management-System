package com.chiragbhisikar.Library.Management.System.Request.User;

import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.chiragbhisikar.Library.Management.System.Model.Role;
import lombok.Data;

import java.util.Optional;
import java.util.Set;

@Data
public class UserUpdateRequest {
    private Optional<String> firstName = Optional.empty();
    private Optional<String> lastName = Optional.empty();
    private Optional<String> fatherName = Optional.empty();
    private Optional<GenderTypes> gender;
    private Optional<String> email = Optional.empty();
    private Optional<String> contactNo = Optional.empty();
    private Optional<Long> enrollmentNo = Optional.empty();
    private Optional<String> originalAddress = Optional.empty();
    private Optional<String> currentAddress = Optional.empty();
    private Optional<Set<Role>> userRoles = Optional.empty();
}

/*
All Parameter Are Optional
{
    "firstName": "Chirag",
    "lastName": "Bhisikar",
    "fatherName": "Rajeshbhai",
    "gender": "MALE",
    "email": "chiragbhisikar@gmail.com",
    "contactNo": "9016625319",
    "enrollmentNo": "200202101001",
    "originalAddress": "D-1391 Sweety Park Odhav Ahmedabad-382415",
    "currentAddress": "D-1401 Sweety Park Odhav Ahmedabad-382415",
    "userRoles": [
        "ROLE_USER",
        "ROLE_ADMIN" // => Optional
    ],
    "password": "password"
}
*/