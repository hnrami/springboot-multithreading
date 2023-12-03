package com.javasample.executor.api.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.javasample.executor.api.entity.Company;
import com.javasample.executor.api.entity.Employee;
import com.javasample.executor.api.entity.School;
import com.javasample.executor.api.entity.Student;
import com.javasample.executor.api.entity.User;
import com.javasample.executor.api.service.CompanyService;
import com.javasample.executor.api.service.EmployeeService;
import com.javasample.executor.api.service.SchoolService;
import com.javasample.executor.api.service.StudentService;
import com.javasample.executor.api.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService service;
    
    @Autowired
    private StudentService studentService;
    
    
    @Autowired
    private SchoolService schoolService;
    
    
    @Autowired
    private EmployeeService employeeService;
    
    
    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            service.saveUsers(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/users", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllUsers() {
       return  service.findAllUsers().thenApply(ResponseEntity::ok);
    }


    @GetMapping(value = "/getUsersByThread", produces = "application/json")
    public  ResponseEntity getUsers(){
        CompletableFuture<List<User>> users1=service.findAllUsers();
        CompletableFuture<List<User>> users2=service.findAllUsers();
        CompletableFuture<List<User>> users3=service.findAllUsers();
        CompletableFuture<List<User>> users4=service.findAllUsers();
        CompletableFuture<List<User>> users5=service.findAllUsers();
        CompletableFuture<List<User>> users6=service.findAllUsers();
        
       
        CompletableFuture.allOf(users1,users2,users3,users4,users5,users6).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
    
    
    @GetMapping(value = "/getallrecordsByThread", produces = "application/json")
    public  ResponseEntity getallrecordsByThread(){
        CompletableFuture<List<User>> users=service.findAllUsers();
        CompletableFuture<List<Company>> company=companyService.findAllCompanys();
        CompletableFuture<List<Employee>> employee=employeeService.findAllEmployees();
        CompletableFuture<List<School>> school=schoolService.findAllSchools();
        CompletableFuture<List<Student>> student=studentService.findAllStudents();
        
       
        CompletableFuture.allOf(users,company,employee,school,student).join();
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
