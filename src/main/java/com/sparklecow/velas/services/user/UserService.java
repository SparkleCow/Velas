package com.sparklecow.velas.services.user;

import com.sparklecow.velas.entities.user.*;
import com.sparklecow.velas.exceptions.AdminRoleNotFoundException;
import com.sparklecow.velas.exceptions.UserNotFoundException;
import jakarta.mail.MessagingException;

import java.util.List;

public interface UserService{

    public String extractUsername(String token);
    public boolean validateAdminRole(String token) throws AdminRoleNotFoundException;
    public boolean validate(String token);
    public void create(UserRegisterDto userRegisterDto) throws MessagingException;
    public List<User> findAll();
    public User findById(Long id) throws UserNotFoundException;
    public User update(UserUpdateDto userUpdateDto, Long id) throws UserNotFoundException;
    public void deleteById(Long id) throws UserNotFoundException;
    ResponseAuthDto authenticate(UserLoginDto userLoginDto);
}
