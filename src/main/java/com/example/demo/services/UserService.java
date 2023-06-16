package com.example.demo.services;
import com.example.demo.entity.User;
import com.example.demo.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;
@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;
    public void save(@NotNull User user) {
        try {
            user.setPassword(new BCryptPasswordEncoder()
                    .encode(user.getPassword()));
            userRepository.save(user);
        }catch (Exception e){
            System.out.printf(e.getMessage());
        }

    }
    public Optional<User> findByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(this.userRepository.findByUsername(username));

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}