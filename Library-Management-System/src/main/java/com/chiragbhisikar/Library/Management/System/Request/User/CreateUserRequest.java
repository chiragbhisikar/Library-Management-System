package com.chiragbhisikar.Library.Management.System.Request.User;

import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.chiragbhisikar.Library.Management.System.Model.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String fatherName;
    private GenderTypes gender;
    private String email;
    private String contactNo;
    private Long enrollmentNo;
    private String originalAddress;
    private String currentAddress;
    private String password;
    private Set<Role> userRoles = new HashSet<>();
}

/*
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
        "ROLE_ADMIN"
    ],
    "password": "password"
}
*/





