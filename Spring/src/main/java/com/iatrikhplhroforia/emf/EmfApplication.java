package com.iatrikhplhroforia.emf;

import com.google.firebase.auth.ExportedUserRecord;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ListUsersPage;
import com.iatrikhplhroforia.emf.user.FirebaseUserRepo;
import com.iatrikhplhroforia.emf.user.RegisteredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmfApplication implements CommandLineRunner {

    @Autowired
    FirebaseUserRepo repo;

    private static String url = "jdbc:sqlite:C:/Users/Spiridoula/Desktop/MED/mppl17041_PitsiosDimitris_MI/Final/emf.db";

    public static String getUrl(){
        return url;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmfApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ListUsersPage page = FirebaseAuth.getInstance().listUsers(null);
        RegisteredUser x;
        for (ExportedUserRecord user : page.iterateAll()) {
            x = new RegisteredUser(user.getUid(), user.getEmail());
            repo.save(x);
        }
    }
}
