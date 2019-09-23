package fi.academy.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository){
        this.repository=repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = repository.findByUsername(username);
        if (user.isPresent()){
        return new User(user.get().getUsername(), user.get().getPassword(), Collections.emptyList());
        } else{
            throw new UsernameNotFoundException(username);
        }

}}


