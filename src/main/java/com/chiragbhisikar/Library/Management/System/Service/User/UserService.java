package com.chiragbhisikar.Library.Management.System.Service.User;

import com.chiragbhisikar.Library.Management.System.DTO.UserDto;
import com.chiragbhisikar.Library.Management.System.Enum.GenderTypes;
import com.chiragbhisikar.Library.Management.System.Exceptions.AlreadyExistsException;
import com.chiragbhisikar.Library.Management.System.Exceptions.NotFoundException;
import com.chiragbhisikar.Library.Management.System.Model.Role;
import com.chiragbhisikar.Library.Management.System.Model.User;
import com.chiragbhisikar.Library.Management.System.Model.UserRole;
import com.chiragbhisikar.Library.Management.System.Repository.RoleRepository;
import com.chiragbhisikar.Library.Management.System.Repository.UserRoleRepository;
import com.chiragbhisikar.Library.Management.System.Repository.UsersRepository;
import com.chiragbhisikar.Library.Management.System.Request.User.CreateUserRequest;
import com.chiragbhisikar.Library.Management.System.Request.User.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements iUserService {
    private final UsersRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail())
                        && !userRepository.existsByEnrollmentNo(request.getEnrollmentNo())
                )
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setFatherName(request.getFatherName());
                    user.setGender(request.getGender());
                    user.setEmail(request.getEmail());
                    user.setContactNo(request.getContactNo());
                    user.setEnrollmentNo(request.getEnrollmentNo());
                    user.setOriginalAddress(request.getOriginalAddress());
                    user.setCurrentAddress(request.getCurrentAddress());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user = userRepository.save(user);

//                    Setting User Role
                    Set<Role> roles = request.getUserRoles();
                    for (Role role : roles) {
                        UserRole userRole = new UserRole();
                        userRole.setUser(user);
                        Role myRole = roleRepository.findByName(role.getName());
                        userRole.setRole(myRole);
                        userRoleRepository.save(userRole);
                    }

                    return user;
                }).orElseThrow(() -> new AlreadyExistsException("Oops!" + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            request.getFirstName().ifPresent(existingUser::setFirstName);
            request.getLastName().ifPresent(existingUser::setLastName);
            request.getFatherName().ifPresent(existingUser::setFatherName);
            request.getEmail().ifPresent(existingUser::setEmail);
            request.getContactNo().ifPresent(contact ->
                    existingUser.setContactNo(String.valueOf(contact)));
            request.getEnrollmentNo().ifPresent(enrollment ->
                    existingUser.setEnrollmentNo(Long.valueOf(String.valueOf(enrollment))));
            request.getOriginalAddress().ifPresent(existingUser::setOriginalAddress);
            request.getCurrentAddress().ifPresent(existingUser::setCurrentAddress);
            request.getGender().ifPresent(gender ->
                    existingUser.setGender(GenderTypes.valueOf(String.valueOf(gender))));

            request.getUserRoles().ifPresent(roles -> {
                for (Role role : roles) {
                    Role myRole = roleRepository.findByName(role.getName());
                    UserRole userRole = new UserRole();
                    userRole.setUser(existingUser);
                    userRole.setRole(myRole);
                    userRoleRepository.save(userRole);
                }
            });

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).map(existingUser -> {
            existingUser.setIs_active(false);

            return userRepository.save(existingUser);
        }).orElseThrow(() -> new NotFoundException("User not found!"));
//        For Delete User

//        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
//            throw new NotFoundException("User not found!");
//        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
