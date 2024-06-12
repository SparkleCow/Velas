package com.sparklecow.velas.services.user;

import com.sparklecow.velas.entities.user.*;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService{
    public boolean validate(String token);
    public void create(UserRegisterDto userRegisterDto) throws MessagingException;
    public List<User> findAll();
    public User findById(Long id);
    public User update(UserUpdateDto userUpdateDto, Long id);
    public void deleteById(Long id);
    ResponseAuthDto authenticate(UserLoginDto userLoginDto);
}
