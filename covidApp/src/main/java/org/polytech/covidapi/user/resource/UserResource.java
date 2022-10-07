package org.polytech.covidapi.user.resource;

import org.polytech.covidapi.user.domain.HttpResponse;
import org.polytech.covidapi.user.domain.User;
import org.polytech.covidapi.user.domain.UserPrincipal;
import org.polytech.covidapi.user.exception.ExceptionHandling;
import org.polytech.covidapi.user.exception.domain.EmailExistException;
import org.polytech.covidapi.user.exception.domain.EmailNotFoundException;
import org.polytech.covidapi.user.exception.domain.UserNotFoundException;
import org.polytech.covidapi.user.service.UserService;
import org.polytech.covidapi.user.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.polytech.covidapi.user.constant.SecurityConstant.JWT_TOKEN_HEADER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(path = {"/user"})
public class UserResource extends ExceptionHandling {
    public static final String PASSWORD_RESET = "Le mot de passe a bien été réinitialisé : ";
    public static final String USER_DELETED_SUCCESSFULLY = "l'utilisateur a bien été supprimé";
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;

    @Autowired
    public UserResource(AuthenticationManager authenticationManager, UserService userService, JWTTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestParam("username") String email,
                                      @RequestParam("password") String password) {
        authenticate(email, password);
        User loginUser = userService.findUserByEmail(email);
        this.userService.save(loginUser);
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        System.out.println("email"+ email);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<User> logout(@RequestParam("id") Long id) {
        User logoutUser = userService.findUserById(id);
        this.userService.save(logoutUser);

        return new ResponseEntity<>(logoutUser, OK);
    }

    @GetMapping("/getjwt/{id}")
    public ResponseEntity<User> login(@PathVariable(value = "id") Long id) {
        User loginUser = userService.findUserById(id);
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        System.out.println("jwtH: "+jwtHeader);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UserNotFoundException, EmailExistException, MessagingException, UnsupportedEncodingException {
        User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword().replaceAll(" ", ""));
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addNewUser(@RequestParam("firstName") String firstName,
                                           @RequestParam("lastName") String lastName,
                                           @RequestParam("id") String id,
                                           @RequestParam("email") String email,
                                           @RequestParam("role") String role,
                                           @RequestParam("isActive") String isActive,
                                           @RequestParam("isNonLocked") String isNonLocked)
            throws UserNotFoundException, EmailExistException, IOException {
        User newUser = userService.addNewUser(firstName, lastName, email, role, Boolean.parseBoolean(isNonLocked),
                Boolean.parseBoolean(isActive));
        return new ResponseEntity<>(newUser, OK);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestParam("currentUsername") String currentUsername,
                                       @RequestParam("firstName") String firstName,
                                       @RequestParam("lastName") String lastName,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role,
                                       @RequestParam("isActive") String isActive,
                                       @RequestParam("isNonLocked") String isNonLocked,
                                       @RequestParam(value = "profileImage", required = false) MultipartFile profileImage) throws UserNotFoundException, EmailExistException, IOException {
        User updatedUser = userService.updateUser(firstName, lastName, email, role, Boolean.parseBoolean(isNonLocked), Boolean.parseBoolean(isActive));
        return new ResponseEntity<>(updatedUser, OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<User> getUserbyUsername(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, OK);
    }

    @GetMapping("/resetpassword/{id}/{password}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("password") String password, @PathVariable("id") Long id) throws MessagingException, EmailNotFoundException {
        userService.resetPassword(password, id);
        return response(OK, PASSWORD_RESET + password);
    }

    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_SUPER_ADMIN")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("id") Long id) throws IOException {
        userService.deleteUser(id);
        return response(OK, USER_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase(),
                message), httpStatus);
    }

    private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    private void authenticate(String id, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(id, password));
    }
}
