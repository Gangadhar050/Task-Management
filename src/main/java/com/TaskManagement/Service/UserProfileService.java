package com.TaskManagement.Service;

import com.TaskManagement.DTO.UserProfileDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserProfileService {

    UserProfileDTO craeteProfile(UserProfileDTO dto);

    List<UserProfileDTO> getAllProfile();

    UserProfileDTO getProfileByOrganizationEmail(String userOrganizationEmail);

    UserProfileDTO updateUserProfie(String userOrganizationEmail,UserProfileDTO dto);

//    List<UserProfileDTO> getAllProfile(UserProfileDTO dto);
//    UserProfileDTO findByUserOrganizationMail(UserProfileDTO dto);


}
