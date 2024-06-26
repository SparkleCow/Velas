package com.sparklecow.velas.services.user;

import com.sparklecow.velas.config.jwt.JwtUtils;
import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.exceptions.*;
import com.sparklecow.velas.repositories.ActivateTokenRepository;
import com.sparklecow.velas.repositories.UserRepository;
import com.sparklecow.velas.services.email.EmailService;
import com.sparklecow.velas.services.email.EmailTemplate;
import com.sparklecow.velas.services.mappers.UserMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final ActivateTokenRepository tokenRepository;
    private final UserDetailsService userDetailsService;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public String extractUsername(String token){
        return jwtUtils.extractUsername(token);
    }

    @Override
    public boolean validateAdminRole(String token) throws AdminRoleNotFoundException {
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));
        if(user.getRoles().stream().anyMatch(role -> role.name().equals("ADMIN"))){
            return true;
        }
        throw new AdminRoleNotFoundException("Admin role not found");
    }

    @Override
    public boolean validate(String token) throws InvalidTokenException, ExpiredTokenException{
        User user = (User) userDetailsService.loadUserByUsername(jwtUtils.extractUsername(token));
        try{
            if (jwtUtils.validateToken(token, user)) {
                return true;
            } else {
                throw new InvalidTokenException("Invalid token");
            }
        }catch (ExpiredJwtException e){
            throw new ExpiredTokenException("Token expired");
        }
    }

    @Override
    public void create(UserRegisterDto userRegisterDto) throws MessagingException {
        User user = userMapper.toUser(userRegisterDto);
        user.setRoles(List.of(Role.USER));
        sendValidationEmail(user);
    }

    @Override
    public void createAdmin(UserRegisterDto userRegisterDto) throws MessagingException {
        User user = userMapper.toUser(userRegisterDto);
        user.setRoles(List.of(Role.ADMIN));
        sendValidationEmail(user);
    }

    public void sendValidationEmail(User user) throws MessagingException {
        String token = generateAndSaveToken(user);
        emailService.sendEmail(user.getEmail(), user.getUsername(), EmailTemplate.ACTIVATE_ACCOUNT,
                activationUrl, token, "Activate account");
    }

    private String generateAndSaveToken(User user) {
        String generatedToken = generateToken(6);
        ActivateToken token = ActivateToken.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    public String generateToken(int length) {
        String token = "1234567890";
        SecureRandom random = new SecureRandom();
        StringBuilder tokenSb = new StringBuilder();
        int indexRandom;
        for(int i=0;i<length;i++){
            indexRandom = random.nextInt(token.length());
            tokenSb.append(token.charAt(indexRandom));
        }
        return tokenSb.toString();
    }

    @Override
    public ResponseAuthDto authenticate(UserLoginDto userLoginDto) {
        UsernamePasswordAuthenticationToken userAuth =
                new UsernamePasswordAuthenticationToken(userLoginDto.username(), userLoginDto.password());
        authenticationManager.authenticate(userAuth);
        User user = userRepository.findByUsername(userLoginDto.username())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new ResponseAuthDto(jwtUtils.generateToken(user));
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException, ActivationTokenException {
        ActivateToken tokenResult = tokenRepository.findByToken(token).orElseThrow(RuntimeException::new);
        if(LocalDateTime.now().isAfter(tokenResult.getExpiredAt())){
            sendValidationEmail(tokenResult.getUser());
            throw new ActivationTokenException("Activation token has expired or is invalid");
        }
        User user = tokenResult.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        tokenResult.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(tokenResult);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public User update(UserUpdateDto userUpdateDto, Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user = userMapper.updateUser(userUpdateDto, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(id);
    }
}
