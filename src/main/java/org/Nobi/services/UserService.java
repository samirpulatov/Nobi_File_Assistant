package org.Nobi.services;

import org.Nobi.database.UserRepository;
import org.Nobi.dto.User;
import org.Nobi.enums.UserState;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    public boolean userExists(Long chat_id) {
        return userRepository.existsByChatId(chat_id);
    }



}
