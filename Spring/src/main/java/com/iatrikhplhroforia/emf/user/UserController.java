package com.iatrikhplhroforia.emf.user;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UFirebaseService service;

    @GetMapping("/AllUsers")
    public List<RegisteredUser> getUsers() throws FirebaseAuthException {
        return service.getUsers();
    }

    @PostMapping("/NewUser/{email}/{pwd}")
    public String insertUser(@PathVariable String email, @PathVariable String pwd) throws FirebaseAuthException {
        return service.createNewUser(email, pwd);
    }

    @PostMapping("/NewUser")
    public String insertUser(@RequestBody Map<String, String> body) throws FirebaseAuthException {
        String email = body.get("mail");
        String password = body.get("pwd");

        return service.createNewUser(email, password);
    }

    @PostMapping("/NewUser2")
    public String insertUser(@RequestBody User user) throws FirebaseAuthException {
        String email = user.getEmail();
        String password = user.getPassword();

        return service.createNewUser(email, password);
    }

}
