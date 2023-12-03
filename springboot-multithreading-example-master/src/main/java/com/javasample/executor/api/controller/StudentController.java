package com.javasample.executor.api.controller;

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

import com.javasample.executor.api.service.StudentService;

@RestController
public class StudentController {
     @Autowired
    private StudentService studentService;
    
    
   @PostMapping(value = "/students", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveStudents(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
        	studentService.saveStudents(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/students", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllStudents() {
       return  studentService.findAllStudents().thenApply(ResponseEntity::ok);
    }


  
}
