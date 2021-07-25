package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exception.BaseException;
import com.example.demo.exception.UserException;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findById(String id){
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean matchPassword(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword,encodedPassword);
    }

    // TODO: METHOD 1 CREATE USER
    public User createUser(User user) {
        return userRepository.save(user);
    }


    // TODO: METHOD 2 CREATE USER
    public User saveUser(String email, String password, String name) throws BaseException {

        // validate
        if (Objects.isNull(email)) {
            throw UserException.createEmailNull();
        }
        if (Objects.isNull(password)) {
            throw UserException.createPasswordNull();
        }
        if (Objects.isNull(name)) {
            throw UserException.createNameNull();
        }

        //verify
        if (userRepository.existsByEmail(email)) {
            throw UserException.createEmailDuplicated();
        }

        //save
        User entity = new User();
        entity.setEmail(email);
        entity.setPassword(passwordEncoder.encode(password));
        entity.setName(name);

        return userRepository.save(entity);
    }

    public User updateName(String id,String name) throws BaseException {
        Optional<User> opt = userRepository.findById(id);
        if ( opt.isEmpty()){
            throw UserException.notFound();
        }
        User user = opt.get();
        user.setName(name);
        return userRepository.save(user);
    }

    public void delete(String id){
        userRepository.deleteById(id);
    }
}
