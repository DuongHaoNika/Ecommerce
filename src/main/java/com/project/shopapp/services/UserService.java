package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        if(userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        User user = User
                .builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountId(userDTO.getFacebookAccountId())
                .googleAccountId(userDTO.getGoogleAccountId())
                .build();
        userRepository.save(user);
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found"));
        user.setRole(role);

        if(user.getFacebookAccountId() == 0 || user.getGoogleAccountId() == 0){
            String password = userDTO.getPassword();
//            String encodedPassword =
        }

        return userRepository.save(user);
    }

    @Override
    public String login(String phoneNumber, String password) {
        return "";
    }
}
