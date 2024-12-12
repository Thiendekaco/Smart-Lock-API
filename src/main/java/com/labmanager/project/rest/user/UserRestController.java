package com.labmanager.project.rest.user;


import com.labmanager.project.entity.user.User;
import com.labmanager.project.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    public UserRestController(UserService userService){
        this.userService = userService;

    }

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId){
        return userService.findById(userId);
    }

    @DeleteMapping("/user/{userId}")
    public String deleteUserById(@PathVariable int userId){
        return userService.deleteUserById(userId);
    }


    @PostMapping("/user")
    public void createNewUser(@RequestBody User user){
        userService.save(user);
    }



}
