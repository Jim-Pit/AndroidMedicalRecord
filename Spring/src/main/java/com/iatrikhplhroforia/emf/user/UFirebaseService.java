package com.iatrikhplhroforia.emf.user;

import com.google.firebase.auth.*;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UFirebaseService {

    @Autowired
    FirebaseUserRepo repo;

    public String createNewUser(String email, String password) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setPassword(password)
                .setDisabled(false);

        UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

        repo.save( new RegisteredUser(userRecord.getUid(),userRecord.getEmail()) );

        return "Successfully created new user: " + userRecord.getUid();
    }

    public List<String> getUsers_Email() throws FirebaseAuthException {
        // List<ExportedUserRecord> results = new ArrayList<>();
        List<String> results = new ArrayList<>();

        int i=1;
        // Start listing users from the beginning, 1000 at a time.
        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        while (page != null) {
            for (ExportedUserRecord user : page.getValues()) {
                results.add(user.getEmail());
            }
            //results.add("<--- End Of Page " + i++ + "--->");
            page = page.getNextPage();
        }

        /*
        // Iterate through all users. This will still retrieve users in batches,
        // buffering no more than 1000 users in memory at a time.
        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        for (ExportedUserRecord user : page.iterateAll()) {
            System.out.println("User: " + user.getUid());
        }
        */
        return results;
    }

    public List<RegisteredUser> getUsers() throws FirebaseAuthException {
        List<RegisteredUser> results = new ArrayList<>();

        for (RegisteredUser user : repo.findAll()) {
            results.add(user);
        }
        return results;
    }

}
