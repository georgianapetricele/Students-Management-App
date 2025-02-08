package org.example.mpp_backend.service;


import org.example.mpp_backend.dto.RequestRes;
import org.example.mpp_backend.model.User;
import org.example.mpp_backend.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.mpp_backend.repository.UserAccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {


    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RequestRes register(RequestRes registrationRequest) {
        RequestRes response = new RequestRes();

        try{
            UserAccount userAccount = new UserAccount();
            userAccount.setEmail(registrationRequest.getEmail());
            userAccount.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            userAccount.setRole(registrationRequest.getRole());
            userAccount.setName(registrationRequest.getName());

            UserAccount result= userAccountRepository.save(userAccount);
            if(result.getId()>0){
                response.setStatusCode(200);
                response.setUserAccount(result);
                response.setMessage("User registered successfully");
            }
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public RequestRes login(RequestRes loginRequest) {
        RequestRes response = new RequestRes();

        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                            loginRequest.getPassword())
            );
            var userAccount = userAccountRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwtToken = jwtUtils.generateToken(userAccount);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(),userAccount);

            response.setStatusCode(200);
            response.setToken(jwtToken);
            response.setRole(userAccount.getRole());
            response.setRefreshToken(refreshToken);
            response.setExpirationTime("24Hrs");
            response.setMessage("Successfully Logged In");
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public RequestRes refreshToken(RequestRes refreshTokenRequest) {
        RequestRes response = new RequestRes();

        try{
            String email=jwtUtils.extractUsername(refreshTokenRequest.getToken());
            UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow();
            if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(),userAccount)){
                var jwtToken = jwtUtils.generateToken(userAccount);

                response.setStatusCode(200);
                response.setToken(jwtToken);
                response.setRefreshToken(refreshTokenRequest.getToken());
                response.setExpirationTime("24Hrs");
                response.setMessage("Token Refreshed Successfully");
            }
            response.setStatusCode(200);
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public RequestRes getAllUserAccounts() {
        RequestRes response = new RequestRes();

        try{
            List<UserAccount> userAccounts = userAccountRepository.findAll();
            if(!userAccounts.isEmpty()){
                response.setStatusCode(200);
                response.setUserAccounts(userAccounts);
                response.setMessage("User Accounts fetched successfully");
            }
            else {
                response.setStatusCode(404);
                response.setMessage("No User Accounts found");
            }
        }
        catch(Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }

        return response;
    }

    public RequestRes getUsersById(Integer id) {
        RequestRes reqRes = new RequestRes();
        try {
            UserAccount userById = userAccountRepository.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
            reqRes.setUserAccount(userById);
            reqRes.setStatusCode(200);
            reqRes.setMessage("User with id '" + id + "' found successfully");
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }


    public RequestRes deleteUserById(Integer id) {
        RequestRes reqRes = new RequestRes();
        try {
            Optional<UserAccount> userById = userAccountRepository.findById(id);
            if(userById.isPresent()){
                userAccountRepository.deleteById(id);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User with id '" + id + "' deleted successfully");
            }
            else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User with id '" + id + "' not found");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred: " + e.getMessage());
        }
        return reqRes;
    }

    public RequestRes updateUser(Integer userId,UserAccount updatedUser){
        RequestRes reqRes = new RequestRes();
        try {
            Optional<UserAccount> userOptional = userAccountRepository.findById(userId);
            if (userOptional.isPresent()) {
                UserAccount existingUser = userOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode the password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }

                UserAccount savedUser = userAccountRepository.save(existingUser);
                reqRes.setUserAccount(savedUser);
                reqRes.setStatusCode(200);
                reqRes.setMessage("User updated successfully");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }
        } catch (Exception e) {
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while updating user: " + e.getMessage());
        }
        return reqRes;
    }

    public RequestRes getMyInfo(String email){
        RequestRes reqRes = new RequestRes();
        try {
            Optional<UserAccount> userOptional = userAccountRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                reqRes.setUserAccount(userOptional.get());
                reqRes.setStatusCode(200);
                reqRes.setMessage("successful");
            } else {
                reqRes.setStatusCode(404);
                reqRes.setMessage("User not found for update");
            }

        }catch (Exception e){
            reqRes.setStatusCode(500);
            reqRes.setMessage("Error occurred while getting user info: " + e.getMessage());
        }
        return reqRes;

    }

}
