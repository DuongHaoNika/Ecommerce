package com.project.shopapp.controller;


import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.dtos.UserLoginDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult bindingResult
    ){
        try {
            if(bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
            }
            if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
                return ResponseEntity.badRequest().body("Passwords do not match!");
            }
            try {
                userService.createUser(userDTO);
                return ResponseEntity.ok("Register OK!");
            }
            catch(DataNotFoundException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        return ResponseEntity.ok(token);
    }
}
