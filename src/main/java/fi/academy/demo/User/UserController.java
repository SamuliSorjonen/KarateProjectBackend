package fi.academy.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Optional<UserEntity> getUser(Principal user){
        return userService.findByUsername(user);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> addUser(@RequestBody UserEntity user){
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, Principal user){
        return userService.deleteById(id, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editOwnDetails(@PathVariable int id, @RequestBody UserEntity newDetails, Principal user){
        return userService.editOwnDetails(id, newDetails, user);
    }
    @PutMapping("/addstyle/{id}")
    public ResponseEntity<?> addStyle(@PathVariable int id, @RequestParam long styleId,Principal user){
        return userService.addStyle(id, styleId, user);
    }
    @DeleteMapping("/removestyle/{id}")
    public ResponseEntity<?>removeStyle(@PathVariable int id, @RequestParam long styleId,Principal user){
        return userService.removeStyle(id, styleId, user);
    }
}
