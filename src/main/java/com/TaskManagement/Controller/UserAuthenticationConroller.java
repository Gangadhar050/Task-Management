package com.TaskManagement.Controller;

import ch.qos.logback.classic.Logger;
import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.UserAuthResponseDTO;
import com.TaskManagement.DTO.UserRegisterRequestDTO;
import com.TaskManagement.Entity.User;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Service.UserService;
import com.TaskManagement.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserAuthenticationConroller {

    @Autowired
    private UserService userService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(UserAuthenticationConroller.class);


    //    @PostMapping("/register")
//    public ResponseEntity<UserAuthResponseDTO>register(@RequestBody UserRegisterRequestDTO dto){
//        UserAuthResponseDTO registered = userAuthenticateService.register(dto);
//        return ResponseEntity.ok(registered) ;
//    }
    //OR
    @PostMapping("/register")
    public ResponseEntity<String>register(@RequestBody UserRegisterRequestDTO dto) {

        logger.info("📩 Register Request Received: userName={}, userEmail={}, role={}",
                dto.getUserName(), dto.getUserEmail(), dto.getRole());

        UserAuthResponseDTO registered = userService.register(dto);
        logger.info("✅ Registration successful for user: {}", dto.getUserEmail());

        return ResponseEntity.ok("Registred Successfuly"+registered);
    }
    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDTO>login(@RequestBody LoginRequestDTO requestDTO){
        String logined = userService.login(requestDTO);
        return ResponseEntity.ok(new UserAuthResponseDTO(logined));
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<User>updateUser(@PathVariable Long id, @RequestParam Role role){
        return ResponseEntity.ok(userService.updateRole(id, role));
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?>deleteUser(@PathVariable Long id,@RequestBody User user){
        userService.deleteUser(user, id);
        return ResponseEntity.ok("User Deleted Succesfully");
    }

    @GetMapping("/working")
    public ResponseEntity<String>welcome(){
        return ResponseEntity.ok("Authentication Working Fine");

    }
}

