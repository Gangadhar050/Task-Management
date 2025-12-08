package com.TaskManagement.Service;

import com.TaskManagement.DTO.LoginRequestDTO;
import com.TaskManagement.DTO.UserAuthResponseDTO;
import com.TaskManagement.DTO.UserRegisterRequestDTO;
import com.TaskManagement.Entity.User;
import com.TaskManagement.Enum.Role;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserAuthResponseDTO register(UserRegisterRequestDTO dto);
    String login(LoginRequestDTO requestDTO);

    //For Permission
    User updateRole(Long id, Role role);


    void deleteUser(User user, Long id);
}
