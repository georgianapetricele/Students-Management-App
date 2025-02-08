package org.example.mpp_backend.service;

import lombok.AllArgsConstructor;
import org.example.mpp_backend.model.User;
import org.example.mpp_backend.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService{
    private UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getAllUsersPaginated(int pageNumber){
        int pageSize = 50;
        Page<User> users=userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        //users.forEach(user -> user.setNrStudents(user.getStudents().size()));
        return users.getContent();
    }


    public User findUserById(Long id){
        if(userRepository.findById(id).isPresent()){
            return userRepository.findById(id).get();
        } else {
            throw new IllegalArgumentException("User not found with ID " + id);
        }
    }

    public User addUser(User user){
        userRepository.save(user);
        return user;
    }

    public User updateUser(Long id, User user){
        if(userRepository.findById(id).isPresent()){
            user.setUserId(id);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("User not found with ID " + id);
        }
    }

    public void deleteUser(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found with ID " + id);
        }
    }
}
