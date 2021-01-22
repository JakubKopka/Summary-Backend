package pl.kopka.summary.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kopka.summary.constant.UserConst;
import pl.kopka.summary.domain.Role;
import pl.kopka.summary.domain.model.Billing;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.exception.exceptions.EmailExistException;
import pl.kopka.summary.exception.exceptions.EmailNotFoundException;
import pl.kopka.summary.exception.exceptions.PasswordNotMachException;
import pl.kopka.summary.exception.exceptions.UsernameExistException;
import pl.kopka.summary.repository.UserRepo;
import pl.kopka.summary.security.JwtTokenProvider;

import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private EmailService emailService;
    @Autowired
    private BillingService billingService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(UserConst.FOUND_USER_BY_USERNAME + username);
        }
        user.setLastLoginDateDisplay(user.getLastLoginDate());
        user.setLastLoginDate(new Date());
        userRepo.save(user);
        return user;
    }

    public User findUserByUsername(String username) {
        return userRepo.findUserByUsername(username);
    }

    public User register(User userForm) throws UsernameExistException, EmailExistException {
        validateNewUsernameAndEmail(null, userForm);
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(encodePassword(userForm.getPassword()));
        user.setUserId(generateUserId());
        user.setFirstName(userForm.getFirstName());
        user.setEmail(userForm.getEmail());
        user.setJoinDate(new Date());
        user.setActive(true);
        user.setNotLocked(true);
        user.setRoles(getUserRoles());
        Billing billing = new Billing();
        billing = billingService.addNewBilling(billing);
        user.setBilling(billing);
        userRepo.save(user);
        LOGGER.info("New user was created! " + user.getUsername());
        LOGGER.info("Sending mail to: " + user.getEmail());
        emailService.sendWelcomingEmail(user);
        return user;
    }

    private void validateNewUsernameAndEmail(User currentUser, User userForm) throws UsernameExistException, EmailExistException {
        User userByUsername = userRepo.findUserByUsername(userForm.getUsername());
        User userByEmail = userRepo.findUserByEmail(userForm.getEmail());
        if(currentUser != null){
            if(userByUsername != null && !currentUser.getId().equals(userByUsername.getId())){
                throw new UsernameExistException(UserConst.USERNAME_ALREADY_EXISTS);
            }
            if(userByEmail != null && !currentUser.getId().equals(userByEmail.getId())){
                throw new EmailExistException(UserConst.EMAIL_ALREADY_EXISTS);
            }
        }else {
            if (userByUsername != null) {
                throw new UsernameExistException(UserConst.USERNAME_ALREADY_EXISTS);
            }
            if (userByEmail != null) {
                throw new EmailExistException(UserConst.EMAIL_ALREADY_EXISTS);
            }
        }
    }

    private String generateUserId() {
        return RandomStringUtils.randomNumeric(20);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    private String[] getUserRoles() {
        String[] roles = {String.valueOf(Role.ROLE_USER)};
        return roles;
    }

    public String getUserToken(User user) {
        return jwtTokenProvider.generateJwtToken(user);
    }

    public User getCurrentLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userRepo.findUserByUsername(currentPrincipalName);
    }

    public void resetPasswordByEmail(User userForm) throws EmailNotFoundException, UsernameExistException, EmailExistException {
        User user = userRepo.findUserByEmail(userForm.getEmail());
        if (user == null) {
            throw new EmailNotFoundException(UserConst.NO_USER_FOUND_BY_EMAIL + userForm.getEmail());
        }
        String newPassword = generateNewPassword();
        user.setPassword(encodePassword(newPassword));
        userRepo.save(user);
        LOGGER.info("New password for user: " + user.getUsername() + " " + newPassword);
        emailService.sendNewPasswordEmail(user, newPassword);
    }

    private String generateNewPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public User updateUser(User userForm) throws PasswordNotMachException, UsernameExistException, EmailExistException {
        User user = getCurrentLoginUser();
        validateNewUsernameAndEmail(user, userForm);
        if (!userForm.getPassword().equals("")) {
            if (!bCryptPasswordEncoder.matches(userForm.getPassword(), user.getPassword())) {
                throw new PasswordNotMachException(UserConst.PASSWORD_NOT_MACH);
            }
            user.setPassword(encodePassword(userForm.getNewPassword()));
        }
        user.setFirstName(userForm.getFirstName());
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        userRepo.save(user);
        return user;
    }
}
