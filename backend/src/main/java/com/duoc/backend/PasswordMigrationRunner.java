package com.duoc.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class PasswordMigrationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(PasswordMigrationRunner.class);

    @Value("${app.security.migrate-plain-passwords:false}")
    private boolean migratePlainPasswords;

    @Autowired
    private UserService userService;

    @Override
    public void run(ApplicationArguments args) {
        if (!migratePlainPasswords) {
            return;
        }

        int migratedUsers = userService.migratePlainTextPasswords();
        logger.info("Password migration completed. Migrated {} users.", migratedUsers);
    }
}