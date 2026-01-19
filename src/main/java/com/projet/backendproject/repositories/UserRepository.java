package com.projet.backendproject.repositories;

import com.projet.backendproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
