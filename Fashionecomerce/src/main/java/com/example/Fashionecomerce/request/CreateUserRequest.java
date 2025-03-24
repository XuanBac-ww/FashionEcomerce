package com.example.Fashionecomerce.request;

import com.example.Fashionecomerce.model.Address;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Address> addressList;
}
