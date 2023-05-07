package com.example.restfulwebservice.user;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
     public MappingJacksonValue retrieveAllUsers(){
        List<User> users = service.findALl();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
     }

     /////////////////////////////////////////////
     //URL에서 버전관리
     /////////////////////////////////////////////
     // GET /admin/users/1 or admin/v1/users/10 -> String
     @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
         User user = service.findOne(id);
         if(user == null){
             throw new UserNotFoundException(String.format("ID[%s] not found", id));
         }

         SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                 .filterOutAllExcept("id","name","joinDate","password","ssn");

         FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

         MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
         mappingJacksonValue.setFilters(filterProvider);

         return mappingJacksonValue;
     }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);


        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    /////////////////////////////////////////////
    //PARAM에서 버전 관리
    /////////////////////////////////////////////
    @GetMapping(value = "/users/{id}" , params = "version=1")
    public MappingJacksonValue retrieveUserParamV1(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}",params = "version=2")
    public MappingJacksonValue retrieveUserParamV2(@PathVariable int id){
        User user = service.findOne(id);


        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    /////////////////////////////////////////////
    //Header로 버전관리
    /////////////////////////////////////////////
    @GetMapping(value = "/users/{id}" , headers = "X-API-VERSION=1")
    public MappingJacksonValue retrieveUserHeaderV1(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}",headers = "X-API-VERSION=2")
    public MappingJacksonValue retrieveUserHeaderV2(@PathVariable int id){
        User user = service.findOne(id);


        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }


    /////////////////////////////////////////////
    //마임타입 버전관리
    /////////////////////////////////////////////
    @GetMapping(value = "/users/{id}" ,produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUsermimeV1(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","password","ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping(value = "/users/{id}",produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUsermimeV2(@PathVariable int id){
        User user = service.findOne(id);


        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id","name","joinDate","grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2",filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }





}
