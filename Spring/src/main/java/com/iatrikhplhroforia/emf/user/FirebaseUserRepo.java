package com.iatrikhplhroforia.emf.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirebaseUserRepo extends CrudRepository<RegisteredUser, String> {

    RegisteredUser findByEmail(String email);

}
