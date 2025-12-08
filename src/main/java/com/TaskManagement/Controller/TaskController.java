package com.TaskManagement.Controller;

import com.TaskManagement.DTO.TaskDTO;
import com.TaskManagement.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO>createTask(@RequestBody TaskDTO dto){
        TaskDTO createdTask = taskService.createTask(dto);
        return ResponseEntity.ok(createdTask);
    }
    @GetMapping("/allTask")
    public ResponseEntity<List<TaskDTO>>getAllTaska(){
        List<TaskDTO> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(allTasks);
    }
    @GetMapping("/user/{assignedToEmail}")
    public ResponseEntity<List<TaskDTO>>getTaskByUser(@PathVariable("assignedToEmail") String assignedToEmail){
        List<TaskDTO> tasksByUser = taskService.getTaskByUser(assignedToEmail);
        return ResponseEntity.ok(tasksByUser);
    }
    @PatchMapping("/{taskId}/status")
    public ResponseEntity<TaskDTO>updateTaskStatus(@PathVariable Long taskId,@RequestParam String taskStatus){
        TaskDTO dto = taskService.updateTaskStatus(taskId, taskStatus);
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/working")
    public ResponseEntity<String>welcome() {
        return ResponseEntity.ok("Task API is working!");
    }


//    @DeleteMapping("/delete/{taskId}")
//    public ResponseEntity<Void>deleteTask(@PathVariable Long taskId){
//        taskService.deleteTask(taskId);
//        return ResponseEntity.noContent().build();
//    }
}
