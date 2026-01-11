package org.Nobi.services;

import org.Nobi.entity.User;
import org.Nobi.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //check if user exits and if not then save a new user
    public void initIfNeeded(Update update) {
        Long chat_id = update.getMessage().getChatId();

        if(!checkUserByChatId(chat_id)) {
            var from = update.getMessage().getFrom();
            saveUser(new User(
                    chat_id,
                    from.getUserName(),
                    from.getFirstName(),
                    from.getLastName()
            ));
        }
    }

    // save a new user
    public void saveUser(User user) {
        userRepository.saveNewUser(user);
    }

    // check if user with given chat_id exists in users table
    private boolean checkUserByChatId(Long chat_id) {
        return userRepository.existsByChatId(chat_id);
    }

}
