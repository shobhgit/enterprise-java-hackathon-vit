
package com.ruralhealth.healthcare.config;

import com.ruralhealth.healthcare.entity.User;
import com.ruralhealth.healthcare.entity.enums.UserRole;
import com.ruralhealth.healthcare.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.findByUsername("healthworker").isEmpty()) {
                User healthworker = new User(
                        "healthworker",
                        passwordEncoder.encode("HealthWorker@123"),
                        UserRole.HEALTHWORKER
                );

                userRepository.save(healthworker);
            }

            if (userRepository.findByUsername("officer").isEmpty()) {
                User officer = new User(
                        "officer",
                        passwordEncoder.encode("Officer@123"),
                        UserRole.OFFICER
                );

                userRepository.save(officer);
            }
        };
    }
}
