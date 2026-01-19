package com.projet.backendproject.control;

import com.projet.backendproject.dto.UserRequest;
import com.projet.backendproject.model.User;
import com.projet.backendproject.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ✅ READ ALL
    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // ✅ READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE
    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserRequest req) {
        User user = new User();
        user.setName(req.name);
        user.setEmail(req.email);
        user.setPassword(req.password); // plus tard: hash BCrypt
        user.setRole(req.role);
        user.setIsActive(req.isActive != null ? req.isActive : true);
        user.setLastLogin(null);

        User saved = userRepository.save(user);

        return ResponseEntity
                .created(URI.create("/api/users/" + saved.getId()))
                .body(saved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserRequest req) {
        return userRepository.findById(id).map(existing -> {

            if (req.name != null) existing.setName(req.name);
            if (req.email != null) existing.setEmail(req.email);
            if (req.password != null) existing.setPassword(req.password);
            if (req.role != null) existing.setRole(req.role);
            if (req.isActive != null) existing.setIsActive(req.isActive);

            User updated = userRepository.save(existing);
            return ResponseEntity.ok(updated);

        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
