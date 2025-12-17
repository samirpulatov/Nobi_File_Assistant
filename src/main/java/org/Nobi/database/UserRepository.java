package org.Nobi.database;

import org.Nobi.dto.User;
import org.Nobi.enums.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs,rowNum) -> {
       var user = new User();
       user.setChat_id(rs.getLong("chat_id"));
       return user;
    };


    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable() {
        LOGGER.info("Creating table User");
        jdbcTemplate.execute("""
        CREATE TABLE IF NOT EXISTS users (
                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                        chat_id LONG UNIQUE NOT NULL,
                        userName TEXT,
                        firstName TEXT,
                        lastName TEXT,
                        userRole TEXT NOT NULL
            );
        """);
    }

    public void saveUser(User user) {
        LOGGER.info("Saving User {}", user);
        System.out.println("Saving user: " + user.toString());
        Long chat_id = user.getChat_id();
        String userName = user.getUserName();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        UserRole userRole = user.getUserRole();
        jdbcTemplate.update(
        """
            INSERT OR IGNORE INTO users(chat_id, userName, firstName, lastName, userRole)
            VALUES (?, ?, ?, ?, ?)
            """,
            chat_id,
            userName,
            firstName,
            lastName,
            userRole.name()
        );
    }

    public User getUser(Long chat_id) {
        LOGGER.info("Retrieving User {}", chat_id);
        String sql =
                """
                SELECT chat_id FROM users WHERE chat_id = ?;
                """;
        return jdbcTemplate.queryForObject(sql,userRowMapper,chat_id);
    }


}
