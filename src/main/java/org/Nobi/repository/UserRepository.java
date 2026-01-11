package org.Nobi.repository;

import org.Nobi.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable() {
        LOGGER.info("Creating table User");
        jdbcTemplate.execute("""
        CREATE TABLE IF NOT EXISTS users (
                        user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        chat_id LONG UNIQUE NOT NULL,
                        user_name TEXT,
                        first_name TEXT,
                        last_name TEXT
            );
        """);
    }

    public void saveNewUser(User user) {
        LOGGER.info("Saving User {}", user);
        Long chat_id = user.getChat_id();
        String user_name = user.getUser_name();
        String first_name = user.getFirst_name();
        String last_name = user.getLast_name();
        jdbcTemplate.update(
                """
                    INSERT OR IGNORE INTO users(chat_id, user_name,first_name,last_name)
                    VALUES( ?, ?, ?, ?)
                    """,
                    chat_id,
                    user_name,
                    first_name,
                    last_name
        );
    }

    public boolean existsByChatId(Long chatId) {
        LOGGER.info("Checking for a User with chat_id {}",chatId);
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE chat_id = ?",
                Integer.class,
                chatId
        );

        return count!=null && count > 0;
    }
}
