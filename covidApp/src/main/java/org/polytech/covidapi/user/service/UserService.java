package org.polytech.covidapi.user.service;

import org.polytech.covidapi.user.domain.User;
import org.polytech.covidapi.user.exception.domain.EmailExistException;
import org.polytech.covidapi.user.exception.domain.EmailNotFoundException;
import org.polytech.covidapi.user.exception.domain.UserNotFoundException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface UserService {

    User register(String firstName, String lastName, String email, String password) throws UserNotFoundException, EmailExistException, MessagingException, UnsupportedEncodingException;

    List<User> getUsers();

    User findUserById(Long id);

    User addNewUser(String firstName, String lastName, String email, String role, boolean isNonLocked, boolean isActive) throws UserNotFoundException,  EmailExistException, IOException;

    User updateUser(String newFirstName, String newLastName, String newEmail, String role, boolean isNonLocked, boolean isActive) throws UserNotFoundException, EmailExistException, IOException;

    User findUserByEmail(String email);

    void deleteUser(Long id) throws IOException;

    User loadUserById(Long id);

    void resetPassword(String password, Long id) throws MessagingException, EmailNotFoundException;

    void save(User user);
}
