package com.example.restfulwebservice.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
     public List<User> retrieveAllUsers(){
        return service.findALl();
     }

     // GET /users/1 or /users/10 -> String
     @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
         User user = service.findOne(id);
         if(user == null){
             throw new UserNotFoundException(String.format("ID[%s] not found", id));
         }

         return user;
     }

     @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
         User savedUser = service.save(user);

         URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                 .path("/{id}")
                 .buildAndExpand(savedUser.getId())
                 .toUri();

         return ResponseEntity.created(location).build();
     }

     @DeleteMapping("/users/{id}")
     public void deleteUser(@PathVariable int id){
        User delUser = service.deleteById(id);
        if(delUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
     }

     @PutMapping("/users")
    public User modifiedUser(@RequestBody User user){
        User modifiedUser = service.modifiedUser(user);
        if(modifiedUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", user.getId()));
        }
        return modifiedUser;
     }



}