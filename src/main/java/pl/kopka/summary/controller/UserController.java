package pl.kopka.summary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.kopka.summary.aspect.MeasureTimeAspect;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.exception.exceptions.EmailExistException;
import pl.kopka.summary.exception.exceptions.EmailNotFoundException;
import pl.kopka.summary.exception.exceptions.PasswordNotMachException;
import pl.kopka.summary.exception.exceptions.UsernameExistException;
import pl.kopka.summary.service.AuthenticateService;
import pl.kopka.summary.service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = {"/", "/user"})
@CrossOrigin
public class UserController {

    private AuthenticateService authenticateService;
    private UserService userService;

    @Autowired
    public UserController(AuthenticateService authenticateService, UserService userService) {
        this.authenticateService = authenticateService;
        this.userService = userService;
    }

    @PostMapping("/login")
//    @MeasureTimeAspect
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user){
        authenticateService.authenticateUser(user.getUsername(), user.getPassword());
        User authUser = userService.findUserByUsername(user.getUsername());
        HttpHeaders httpHeaders = authenticateService.getJwtHeader(authUser);
        Map<String, Object> map = new HashMap<>();
        map.put("token", userService.getUserToken(authUser));
        map.put("user", authUser);
        return new ResponseEntity<>(map, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/reset-password")
//    @MeasureTimeAspect
    public ResponseEntity<?> resetPassword(@RequestBody User user) throws EmailNotFoundException, UsernameExistException, EmailExistException {
        userService.resetPasswordByEmail(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify-token")
//    @MeasureTimeAspect
    public ResponseEntity<User> getUserInfo(){
        return new ResponseEntity<>(userService.getCurrentLoginUser(), HttpStatus.OK);
    }

    @PostMapping("/register")
//    @MeasureTimeAspect
    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException {
        User newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/update")
//    @MeasureTimeAspect
    public ResponseEntity<User> updateUser(@RequestBody User user) throws PasswordNotMachException, UsernameExistException, EmailExistException {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
