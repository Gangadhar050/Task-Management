package com.TaskManagement.ServiceIMPL;

import com.TaskManagement.DTO.TaskDTO;
import com.TaskManagement.Entity.Task;
import com.TaskManagement.Enum.TaskStatus;
import com.TaskManagement.Repository.TaskRepository;
import com.TaskManagement.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    // Create a new task
    public TaskDTO createTask(TaskDTO dto){

        Task task = new Task();
        task.setTaskTitle(dto.getTaskTitle());
        task.setTaskDescription(dto.getTaskDescription());
        task.setAssignedToEmail(dto.getAssignedToEmail());
        task.setAssignedAt(LocalDateTime.now());
        task.setDueDate(dto.getDueDate());
        task.setTaskPriority(dto.getTaskPriority());
        task.setTaskStatus(dto.getTaskStatus());
        taskRepository.save(task);
        return toDTO(task);
    }
// Get all tasks
    public List<TaskDTO>getAllTasks() {
        return taskRepository.findAll()
                .stream().map(this::toDTO).collect(Collectors.toList());
//                           OR
//        List<Task> tasks = taskRepository.findAll();
//        return tasks.stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Get tasks by assigned user's email
    public List<TaskDTO> getTaskByUser(String assignedToEmail) {
        return taskRepository.getByAssignedToEmail(assignedToEmail)
                .stream().map(this::toDTO).collect(Collectors.toList());
//                        OR
//        List<Task> byAssignedToEmail = taskRepository.getByAssignedToEmail(assignedToEmail);
//        return byAssignedToEmail.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public TaskDTO updateTaskStatus(Long taskId, String taskStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));
        task.setTaskStatus(TaskStatus.valueOf(taskStatus));
        taskRepository.save(task);
        return toDTO(task);
    }

    // Convert Task entity to TaskDTO
    private TaskDTO toDTO(Task task) {
        TaskDTO dto=new TaskDTO();
        dto.setId(task.getId());
        dto.setTaskTitle(task.getTaskTitle());
        dto.setTaskDescription(task.getTaskDescription());
        dto.setAssignedToEmail(task.getAssignedToEmail());
        dto.setAssignedAt(task.getAssignedAt());
        dto.setDueDate(task.getDueDate());
        dto.setTaskPriority(task.getTaskPriority());
        dto.setTaskStatus(task.getTaskStatus());
        return dto;
    }
    }

