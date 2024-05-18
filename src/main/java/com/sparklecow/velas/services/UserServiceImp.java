package com.sparklecow.velas.services;

import com.sparklecow.velas.config.jwt.JwtUtils;
import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.repositories.ActivateTokenRepository;
import com.sparklecow.velas.repositories.UserRepository;
import com.sparklecow.velas.services.email.EmailService;
import com.sparklecow.velas.services.email.EmailTemplate;
import com.sparklecow.velas.services.mappers.UserMapper;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final ActivateTokenRepository tokenRepository;
    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void create(UserRegisterDto userRegisterDto) throws MessagingException {
        User user = userMapper.toUser(userRegisterDto);
        user.setRoles(List.of(Role.USER));
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
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new ResponseAuthDto(jwtUtils.generateToken(user));
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        ActivateToken tokenResult = tokenRepository.findByToken(token).orElseThrow(RuntimeException::new);
        if(LocalDateTime.now().isAfter(tokenResult.getExpiredAt())){
            sendValidationEmail(tokenResult.getUser());
            throw new RuntimeException("Token expired");
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
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User update(UserUpdateDto userUpdateDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user = userMapper.updateUser(userUpdateDto, user);
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
    }
}
