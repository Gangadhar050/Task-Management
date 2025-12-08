package com.TaskManagement.ServiceIMPL;


import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.UserAuthResponseDTO;
import com.TaskManagement.DTO.UserRegisterRequestDTO;
import com.TaskManagement.Entity.User;
import com.TaskManagement.Enum.Permission;
import com.TaskManagement.Enum.Role;
import com.TaskManagement.Exception.ResourceNotFoundException;
import com.TaskManagement.Repository.UserRepository;
import com.TaskManagement.Security.JWTUtil;
import com.TaskManagement.Security.PermissionConfig;
import com.TaskManagement.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionConfig permissionConfig;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder =new BCryptPasswordEncoder();
@Override
    public UserAuthResponseDTO register(UserRegisterRequestDTO request) {
        Optional<User> existing = userRepository.findByUserEmail(request.getUserEmail());
        if (existing.isPresent()) {
            throw new ResourceNotFoundException("User already exist");
        }
        User user= new User();
        user.setUserName(request.getUserName());
        user.setUserEmail(request.getUserEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        userRepository.save(user);
        String token = jwtUtil.generateToken(user);

        return new UserAuthResponseDTO(token,"User register Succesfully");
    }
    @Override
    public String login(LoginRequestDTO loginRequestDTO){
        User user = userRepository.findByUserEmail(loginRequestDTO.getUserEmail()).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(),user.getPassword())){
            throw new ResourceNotFoundException("Invalid Credincial");
        }
        return jwtUtil.generateToken(user);

    }
    //For Permission
    @Override
    public User updateRole(Long id, Role role){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("usernot found"));
        Role newRole = Role.valueOf(role.name());
        user.setRole(role);
        return userRepository.save(user);
    }
@Override
public void deleteUser(User user, Long id){
        Set<Permission> perm = permissionConfig.getrolePermission().get(user.getRole());
        if (!perm.contains(Permission.USER_MANAGE)){
            throw new RuntimeException("Access denied");
        }
        userRepository.save(user);
    }

}