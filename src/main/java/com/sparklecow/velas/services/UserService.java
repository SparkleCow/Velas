package com.sparklecow.velas.services;

import com.sparklecow.velas.entities.user.*;

import java.util.List;

public interface UserService{

    public void create(UserRegisterDto userRegisterDto);
    public List<User> findAll();
    public User findById(Long id);
    public User update(UserUpdateDto userUpdateDto, Long id);
    public void deleteById(Long id);
    ResponseAuthDto authenticate(UserLoginDto userLoginDto);
}
