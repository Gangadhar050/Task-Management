package com.TaskManagement.DTO;

import com.TaskManagement.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequestDTO {
    private String userName;
    private String userEmail;
    private String password;
    private Role role;

}
