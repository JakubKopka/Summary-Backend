package pl.kopka.summary.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.kopka.summary.domain.model.User;
import pl.kopka.summary.exception.exceptions.*;
import pl.kopka.summary.repository.UserRepo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class UserServiceTest {

    @Mock private UserRepo userRepo;
    @Mock private EmailService emailService;
    @Mock BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock private Authentication authentication;


    @InjectMocks UserService userService;

    @BeforeEach
    private void init(){
        authentication = mock(Authentication.class, Mockito.RETURNS_DEEP_STUBS);
        MockitoAnnotations.openMocks(this);
    }

    private User createUser(){
        User user = new User();
        user.setUsername("TestUser");
        user.setEmail("test@test.test");
        return user;
    }

    @Test
    void shouldLoadUserByUsername() {
        //given
        User testUser = createUser();
        when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(testUser);
        //when
        UserDetails userDetails = userService.loadUserByUsername(testUser.getUsername());
        //then
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUser.getUsername(), userDetails.getUsername());
    }

    @Test
    void shouldNotLoadUserByUsername() {
        //given
        User testUser = createUser();
        when(userRepo.findUserByUsername(any(String.class))).thenReturn(null);
        //when
        //then
        Assertions.assertThrows(UsernameNotFoundException.class, ()->{
            userService.loadUserByUsername(testUser.getUsername());
        });
    }

    @Test
    void shouldResetPasswordByEmail() throws EmailNotFoundException {
        //given
        User testUser = createUser();
        when(userRepo.findUserByEmail(testUser.getEmail())).thenReturn(testUser);

        //when
        userService.resetPasswordByEmail(testUser);
        //then
        Mockito.verify(userRepo).save(any());
        Mockito.verify(emailService).sendNewPasswordEmail(any(), any());
    }

    @Test
    void shouldNotResetPasswordByEmail() {
        //given
        User testUser = createUser();
        when(userRepo.findUserByUsername(testUser.getUsername())).thenReturn(null);

        //when
        //then
        Assertions.assertThrows(EmailNotFoundException.class, ()->{
            userService.resetPasswordByEmail(testUser);
        });
    }
}