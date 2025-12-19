package org.Nobi.services;

import org.Nobi.database.UserRepository;
import org.Nobi.enums.UserState;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserStateService {

    private final UserRepository userRepository;

    public UserStateService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserState getUserState(Long chatId) {
        return userRepository.getUserState(chatId);
    }

    public void setUserState(Long chatId, UserState userState) {
        userRepository.updateUserState(chatId, userState);
    }

//    public void clear(Long chatId) {
//        userStateMap.remove(chatId);
//    }
}
