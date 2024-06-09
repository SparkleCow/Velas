package com.sparklecow.velas.services.mappers;

import com.sparklecow.velas.entities.shoppingCar.ShoppingCar;
import com.sparklecow.velas.entities.user.User;
import com.sparklecow.velas.entities.user.UserRegisterDto;
import com.sparklecow.velas.entities.user.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    public User toUser(UserRegisterDto userRegisterDto){
        ShoppingCar shoppingCar = new ShoppingCar(null, new ArrayList<>());
        User user = new User(
                null,
                userRegisterDto.firstName(),
                userRegisterDto.lastName(),
                userRegisterDto.username(),
                userRegisterDto.email(),
                passwordEncoder.encode(userRegisterDto.password()),
                List.of());
        shoppingCar.setUser(user);
        user.setShoppingCar(shoppingCar);
        return user;
    }

    public User updateUser(UserUpdateDto userUpdateDto, User user) {
        if(userUpdateDto.firstName() != null && !userUpdateDto.firstName().equals(""))
            user.setFirstName(userUpdateDto.firstName());
        if(userUpdateDto.lastName() != null && !userUpdateDto.lastName().equals(""))
            user.setLastName(userUpdateDto.lastName());
        if(userUpdateDto.email() != null && !userUpdateDto.email().equals(""))
            user.setEmail(userUpdateDto.email());
        if(userUpdateDto.username() != null && !userUpdateDto.username().equals(""))
            user.setUsername(passwordEncoder.encode(userUpdateDto.username()));
        return user;
    }
}
