package org.vcbs.member.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    PasswordEncoder passwordEncoder = createDelegatingPasswordEncoder();

    /**
     * spring boot推荐业务自己实现一个 PasswordEncoderFactories.createDelegatingPasswordEncoder
     * 而不是直接使用项目的default；主要说明时考虑到最佳实践随时变更，但作为框架不能；
     *
     * @return {@link PasswordEncoder}
     */
    private static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "argon2@SpringSecurity_v5_8";

        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8());

        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(passwordEncoder.encode("password")).roles("USER").build());
        
        return manager;
    }
}
