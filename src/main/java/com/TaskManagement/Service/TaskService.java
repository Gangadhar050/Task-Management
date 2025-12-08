package com.TaskManagement.Service;

import com.TaskManagement.DTO.TaskDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public interface TaskService {

    TaskDTO createTask(TaskDTO dto);
    List<TaskDTO>getAllTasks();
   List <TaskDTO> getTaskByUser(String assignedToEmail);
    TaskDTO updateTaskStatus(Long taskId,String taskStatus);
//    void deleteTask(Long taskId);
}
