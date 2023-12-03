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

import com.javasample.executor.api.service.CompanyService;
import com.javasample.executor.api.service.EmployeeService;
import com.javasample.executor.api.service.SchoolService;
import com.javasample.executor.api.service.StudentService;
import com.javasample.executor.api.service.UserService;

@RestController
public class ComapanyController {
  
    
    
    @Autowired
    private CompanyService companyService;

    @PostMapping(value = "/comapanys", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveComapanys(@RequestParam(value = "files") MultipartFile[] files) throws Exception {
        for (MultipartFile file : files) {
            companyService.saveCompanys(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/comapanys", produces = "application/json")
    public CompletableFuture<ResponseEntity> findAllComapanys() {
       return  companyService.findAllCompanys().thenApply(ResponseEntity::ok);
    }


   }
