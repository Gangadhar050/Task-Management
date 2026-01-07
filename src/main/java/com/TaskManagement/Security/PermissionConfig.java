package com.TaskManagement.Security;

import com.TaskManagement.Enum.Permission;
import com.TaskManagement.Enum.Role;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class PermissionConfig {
    public static Map<Role,Set<Permission>>getrolePermission(){
        Map<Role,Set<Permission>> map= new HashMap<>();
        map.put(Role.ADMIN,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
                                                       Permission.ISSUE_CREATE,
                                                       Permission.ISSUE_EDIT,
                                                       Permission.COMMENT_ADD,
                                                        Permission.COMMENT_DELETE,
                                                        Permission.COMMENT_DELETE,
                                                       Permission.USER_MANAGE)));

        map.put(Role.MANAGER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
                                                       Permission.ISSUE_CREATE,
                                                       Permission.ISSUE_EDIT,
                                                       Permission.COMMENT_ADD,
                                                       Permission.COMMENT_DELETE)));

        map.put(Role.DEVELOPER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
                                                       Permission.ISSUE_EDIT,
                                                       Permission.COMMENT_ADD)));

        map.put(Role.TESTER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
                                                       Permission.ISSUE_CREATE,
                                                       Permission.COMMENT_ADD)));

        map.put(Role.TESTER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW,
                                                        Permission.COMMENT_ADD )));

        map.put(Role.USER,new HashSet<>(Arrays.asList(Permission.ISSUE_VIEW)));

        return map;
    }
}
