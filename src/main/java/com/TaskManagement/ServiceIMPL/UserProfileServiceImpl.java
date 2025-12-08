package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.DTO.UserProfileDTO;
import com.TaskManagement.Entity.UserProfile;
import com.TaskManagement.Exception.ResourceNotFoundException;
import com.TaskManagement.Repository.UserProfileRepository;
import com.TaskManagement.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {
    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDTO craeteProfile(UserProfileDTO dto) {
        if (userProfileRepository.findByUserOrganizationEmail(dto.getUserOrganizationEmail()).isPresent()) {
             throw new ResourceNotFoundException("Email Already Existed:"+dto.getUserOrganizationEmail());
        }
        UserProfile userProfile=new UserProfile();

        userProfile.setUserName(dto.getUsername());
        userProfile.setDesignation(dto.getDesignation());
        userProfile.setDepartment(dto.getDepartment());
        userProfile.setUserOrganizationEmail(dto.getUserOrganizationEmail());
        userProfile.setOrganizationName(dto.getOrganizationName());
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setActive(true);
        userProfileRepository.save(userProfile);
        return toDTo(userProfile);
    }

        public List<UserProfileDTO>getAllProfile(){
      return userProfileRepository.findAll().
                stream().map(this::toDTo).collect(Collectors.toList());

//                       OR
//        List<UserProfileDTO> collect = userProfileRepository.findAll().
//                stream().map(this::toDTo).collect(Collectors.toList());
 //       return collect;
}



    public UserProfileDTO getProfileByOrganizationEmail(String userOrganizationEmail){
        UserProfile user = userProfileRepository.findByUserOrganizationEmail(userOrganizationEmail)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return toDTo(user);
    }

//    @Override
//    public UserProfileDTO updateUserProfie(String userOrganizationEmail) {
//        return null;
//    }

    public UserProfileDTO updateUserProfie(String userOrganizationEmail,UserProfileDTO dto){
        UserProfile userProfiles = userProfileRepository.findByUserOrganizationEmail(userOrganizationEmail)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

       userProfiles.setUserName(dto.getUsername());
        userProfiles.setDesignation(dto.getDesignation());
        userProfiles.setDepartment(dto.getDepartment());
        userProfiles.setUserOrganizationEmail(dto.getUserOrganizationEmail());
        userProfiles.setOrganizationName(dto.getOrganizationName());
        userProfiles.setCreatedAt(LocalDateTime.now());
        userProfiles.setActive(dto.isActive());
        userProfileRepository.save(userProfiles);
      return toDTo(userProfiles);
    }

    private UserProfileDTO toDTo(UserProfile userProfile) {

        UserProfileDTO userProfileDTO= new UserProfileDTO();
        userProfileDTO.setUsername(userProfile.getUserName());
        userProfileDTO.setDesignation(userProfile.getDesignation());
        userProfileDTO.setDepartment(userProfile.getDepartment());
        userProfileDTO.setUserOrganizationEmail(userProfile.getUserOrganizationEmail());
        userProfileDTO.setOrganizationName(userProfile.getOrganizationName());
        userProfileDTO.setCreatedAt(LocalDateTime.now());
        userProfileDTO.setActive(userProfile.isActive());
        return userProfileDTO;
    }
}
