package com.sparklecow.velas.services;

import com.sparklecow.velas.config.jwt.JwtUtils;
import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.repositories.UserRepository;
import com.sparklecow.velas.services.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;

    @Override
    public void create(UserRegisterDto userRegisterDto) {
        User user = userMapper.toUser(userRegisterDto);
        user.setRoles(List.of(Role.USER));
        sendValidationEmail(user);
    }

    public void sentValidationEmail(User user) {
        String token = generateAndSaveToken(user);
        emailService.sendEmail(user.getEmail(), user.getFullName(), EmailTemplate.ACTIVATE_ACCOUNT,
                activationUrl, token, "Activate account");
    }

    private String generateAndSaveToken(User user) {
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
