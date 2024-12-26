package com.chiragbhisikar.Library.Management.System.Model;

import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String fatherName;

    @Enumerated(EnumType.STRING)
    private GenderTypes gender;

    @Column(unique = true)
    @NaturalId
    private String email;
    private String contactNo;

    @Column(unique = true)
    @NaturalId
    private Long enrollmentNo;

    private String originalAddress;
    private String currentAddress;

    private String password;

    @Column(name = "is_active", columnDefinition = "boolean default false")
    private Boolean is_active;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Set<Copy> copies = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Deposit> deposits = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> userRoles = new HashSet<>();
}
