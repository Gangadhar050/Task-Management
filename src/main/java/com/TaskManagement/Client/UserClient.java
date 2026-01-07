package com.TaskManagement.Client;

import com.TaskManagement.Enum.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/users/{userOfficalEmail}/roles")
    Set<Role> getRole(@PathVariable String userOfficalEmail);
}


