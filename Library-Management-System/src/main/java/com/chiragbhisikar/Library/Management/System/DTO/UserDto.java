package com.chiragbhisikar.Library.Management.System.DTO;

import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.chiragbhisikar.Library.Management.System.Model.BookIssue;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private GenderTypes gender;
    private String email;
    private String contactNo;
    private Long enrollmentNo;
    private String originalAddress;
    private String currentAddress;
    //    private String password;
    private Boolean is_active;
    private Set<BookIssue> bookIssues;
    private Set<RoleDto> roles;
}
