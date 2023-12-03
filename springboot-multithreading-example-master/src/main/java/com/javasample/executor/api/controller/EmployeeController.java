package com.javasample.executor.api.controller;

import com.javasample.executor.api.entity.User;
import com.javasample.executor.api.service.CompanyService;
import com.javasample.executor.api.service.EmployeeService;
import com.javasample.executor.api.service.SchoolService;
import com.javasample.executor.api.service.StudentService;
import com.javasample.executor.api.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class EmployeeController {
   
    
    @Autowired
    private EmployeeService employeeService;
    
   

    @PostMapping(value = "/employees", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveEmployees(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
        	employeeService.saveEmployees(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/employees", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllEmployees() {
       return  employeeService.findAllEmployees().thenApply(ResponseEntity::ok);
    }


}
