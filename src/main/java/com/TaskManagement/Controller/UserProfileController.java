package com.TaskManagement.Controller;

import com.TaskManagement.DTO.UserProfileDTO;
import com.TaskManagement.Service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userprofile")
@RequiredArgsConstructor
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @PostMapping("/createProfile")
    public ResponseEntity<UserProfileDTO>createProfile(@RequestBody UserProfileDTO dto){
//        UserProfileDTO create = userProfileService.craeteProfile(dto);
//        return ResponseEntity.ok(create);
//                             OR
         return ResponseEntity.ok(userProfileService.craeteProfile(dto));
    }
    @GetMapping("/allProfiles")
    public ResponseEntity<List<UserProfileDTO>>getAllProfile(){
       List<UserProfileDTO>allProfiles= userProfileService.getAllProfile();
        return ResponseEntity.ok(allProfiles);
    }
    @GetMapping("/{userOrganizationEmail}")
    public ResponseEntity<UserProfileDTO>getProfileByOrganizationEmail(@PathVariable String userOrganizationEmail ){
        return ResponseEntity.ok(userProfileService.getProfileByOrganizationEmail(userOrganizationEmail));
    }
    @PutMapping("/update/{userOrganizationEmail}")
    public ResponseEntity <UserProfileDTO>updateUserProfie(@PathVariable String userOrganizationEmail,@RequestBody UserProfileDTO dto){
        return ResponseEntity.ok(userProfileService.updateUserProfie(userOrganizationEmail, dto));
    }

    @PostMapping("/public/test")
    public  String TestingApi(){
        return "user Profile API Is Working";
    }
}
