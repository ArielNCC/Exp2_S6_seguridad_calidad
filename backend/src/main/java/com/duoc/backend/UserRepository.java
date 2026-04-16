package com.duoc.backend;

import org.springframework.data.repository.CrudRepository;

// Este código será CREADO AUTOMATICAMENTE por Spring en un Bean llamado userRepository
// CRUD significa Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}