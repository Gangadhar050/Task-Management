package com.TaskManagement.Service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ReportingService {
    Map<String,Object>burnDown(Long sprintId);
    Map<String,Object>velocity(Long projectId);
}
