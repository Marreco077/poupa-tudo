package com.marreco.poupa_tudo.services;

import com.github.f4b6a3.uuid.UuidCreator;
import com.marreco.poupa_tudo.dtos.UserCreateDTO;
import com.marreco.poupa_tudo.dtos.UserResponseDTO;
import com.marreco.poupa_tudo.entity.User;
import com.marreco.poupa_tudo.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("Esse e-mail já está cadastrado");
        }

        UUID newId = UuidCreator.getTimeOrderedEpoch();

        String passwordHash = BCrypt.hashpw(dto.password(), BCrypt.gensalt());

        User newUser = new User(
                newId,
                dto.name(),
                dto.lastName(),
                dto.email(),
                passwordHash
        );

        userRepository.save(newUser);

        return new UserResponseDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getLastName(),
                newUser.getEmail()
        );
    }

}
