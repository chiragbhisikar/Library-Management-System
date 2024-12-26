package com.chiragbhisikar.Library.Management.System.DTO;

import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.chiragbhisikar.Library.Management.System.Model.Copy;
import com.chiragbhisikar.Library.Management.System.Model.Deposit;
import com.chiragbhisikar.Library.Management.System.Model.UserRole;
import lombok.Data;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private GenderTypes gender;
    private String email;
    private String contact_no;
    private Long enrollment_no;
    private String originalAddress;
    private String currentAddress;
//    private String password;
    private Boolean is_active;
    private Set<Copy> copies = new HashSet<>();
    private Set<Deposit> deposits = new HashSet<>();
    private Collection<UserRole> roles = new HashSet<>();
}
