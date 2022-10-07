package org.polytech.covidapi.user.service.impl;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.polytech.covidapi.user.domain.User;
import org.polytech.covidapi.user.domain.UserPrincipal;
import org.polytech.covidapi.user.enumeration.Role;
import org.polytech.covidapi.user.exception.domain.EmailExistException;
import org.polytech.covidapi.user.exception.domain.EmailNotFoundException;
import org.polytech.covidapi.user.exception.domain.UserNotFoundException;
import org.polytech.covidapi.user.repository.UserRepository;
import org.polytech.covidapi.user.service.LoginAttemptService;
import org.polytech.covidapi.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.polytech.covidapi.user.constant.UserImplConstant.*;
import static org.polytech.covidapi.user.enumeration.Role.ROLE_USER;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService {
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private LoginAttemptService loginAttemptService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LoginAttemptService loginAttemptService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.loginAttemptService = loginAttemptService;
    }


    @Override
    public User loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findUserById(id);
        if (user == null) {
            LOGGER.error(NO_USER_FOUND_BY_EMAIL + user.getEmail());
            throw new UsernameNotFoundException(NO_USER_FOUND_BY_EMAIL + user.getEmail());
        } else {
            validateLoginAttempt(user);
            userRepository.save(user);
            UserPrincipal userPrincipal = new UserPrincipal(user);
            LOGGER.info(FOUND_USER_BY_EMAIL + user.getEmail());
            return user;
        }
    }

    @Override
    public User register(String firstName, String lastName, String email, String password) throws UserNotFoundException, EmailExistException, MessagingException, UnsupportedEncodingException {
        validateNewEmail(email);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setNotLocked(true);
        user.setRole(ROLE_USER);
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        return user;
    }

    @Override
    public User addNewUser(String firstName, String lastName, String email, String role, boolean isNonLocked, boolean isActive
    ) throws UserNotFoundException, EmailExistException, IOException {
        validateNewEmail(email);
        User user = new User();
        String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(encodePassword(password));
        user.setActive(isActive);
        user.setNotLocked(isNonLocked);
        user.setRole(getRoleEnumName(role));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
        return user;
    }

    @Override
    public User updateUser(String newFirstName, String newLastName, String newEmail, String role, boolean isNonLocked, boolean isActive) throws UserNotFoundException, EmailExistException, IOException {
        User currentUser = validateNewEmail(newEmail);
        currentUser.setFirstName(newFirstName);
        currentUser.setLastName(newLastName);
        currentUser.setEmail(newEmail);
        currentUser.setActive(isActive);
        currentUser.setNotLocked(isNonLocked);
        currentUser.setRole(getRoleEnumName(role));
        userRepository.save(currentUser);
        return currentUser;
    }

    @Override
    public void resetPassword(String password, Long id) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserById(id);
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        LOGGER.info("New user password: " + password);
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void deleteUser(Long id) throws IOException {
        User user = userRepository.findUserById(id);
        userRepository.deleteById(user.getId());
    }

    public Role getRoleEnumName(String role) {
        return Role.valueOf(role.toUpperCase());
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public String generateUserId() {
        return RandomStringUtils.randomNumeric(10);
    }

    public void validateLoginAttempt(User user) {
        if (user.isNotLocked()) {
            if (loginAttemptService.hasExceededMaxAttempts(user.getEmail())) {
                user.setNotLocked(false);
            } else {
                user.setNotLocked(true);
            }
        } else {
            loginAttemptService.evictUserFromLoginAttemptCache(user.getEmail());
        }
    }

    private User validateNewEmail(String newEmail) throws UserNotFoundException, EmailExistException {
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(newEmail)) {
            if (userByNewEmail != null) {
                throw new EmailExistException(EMAIL_ALREADY_EXISTS);
            }
        }
        return null;
    }

}
