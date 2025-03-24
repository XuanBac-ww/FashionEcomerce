package com.example.Fashionecomerce.service.user;

import com.example.Fashionecomerce.dtos.AddressDto;
import com.example.Fashionecomerce.dtos.UserDto;
import com.example.Fashionecomerce.model.Address;
import com.example.Fashionecomerce.model.Role;
import com.example.Fashionecomerce.model.User;
import com.example.Fashionecomerce.repository.AddressRepository;
import com.example.Fashionecomerce.repository.RoleRepository;
import com.example.Fashionecomerce.repository.UserRepository;
import com.example.Fashionecomerce.request.CreateUserRequest;
import com.example.Fashionecomerce.request.UserUpdateRequest;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;
    @Override
    public User createUser(CreateUserRequest request) {
        Role userRole = Optional.ofNullable(roleRepository.findByName("ROLE_USER"))
                .orElseThrow(() -> new EntityNotFoundException("Role nor found!"));

        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setRoles(Set.of(userRole));
                    User savedUser = userRepository.save(user);

                    if (req.getAddressList() != null && !req.getAddressList().isEmpty()) {
                        // Lưu địa chỉ và thêm vào danh sách
                        User finalSavedUser = savedUser;
                        List<Address> addresses = req.getAddressList().stream()
                                .map(address -> {
                                    address.setUser(finalSavedUser);
                                    return addressRepository.save(address);
                                })
                                .collect(Collectors.toList());

                        // Quan trọng: Cập nhật danh sách địa chỉ cho người dùng đã lưu
                        savedUser.setAddressList(addresses);

                        // Lưu lại người dùng với danh sách địa chỉ đã cập nhật
                        savedUser = userRepository.save(savedUser);
                    }

                    return savedUser;
                }).orElseThrow(() -> new EntityExistsException("Oops! " + request.getEmail() + " already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new EntityNotFoundException("User not found!");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        // Chuyển đổi danh sách địa chỉ
        if (user.getAddressList() != null && !user.getAddressList().isEmpty()) {
            List<AddressDto> addressDtos = user.getAddressList().stream()
                    .map(this::convertAddressToDto)
                    .collect(Collectors.toList());
            userDto.setAddressList(addressDtos);
        }

        // Chuyển đổi các trường khác nếu cần
        return userDto;
    }

    private AddressDto convertAddressToDto(Address address) {
        AddressDto addressDto = new AddressDto();
        addressDto.setId(address.getId());
        addressDto.setCountry(address.getCountry());
        addressDto.setState(address.getState());
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setMobileNumber(address.getMobileNumber());
        addressDto.setAddressType(String.valueOf(address.getAddressType()));
        return addressDto;
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("The authenticated user: " +authentication.getName());
        String email = authentication.getName();
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new EntityNotFoundException("Log in required!"));
    }
}
