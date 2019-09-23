package fi.academy.demo.User;

import fi.academy.demo.karatestyles.KarateStyleEntity;
import fi.academy.demo.karatestyles.KarateStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private KarateStyleRepository karateStyleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Optional<UserEntity> findByUsername(Principal user){
        return userRepository.findByUsername(user.getName());
    }


    public ResponseEntity<?> addUser(UserEntity user){
        if(userRepository.existsByUsername(user.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(user.getEmail())){
            return new ResponseEntity<>("Email is taken", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteById(int id, Principal user){
        Optional<UserEntity>currentUser = userRepository.findByUsername(user.getName());
        if(currentUser.get().getUser_id()!=id||!currentUser.isPresent()){
            return new ResponseEntity<>("Unauthorized request", HttpStatus.BAD_REQUEST);
        }
        userRepository.delete(currentUser.get());
        return new ResponseEntity<>("Deleted succesfully", HttpStatus.OK);
    }

    public ResponseEntity<?> editOwnDetails(int id, UserEntity newDetails, Principal user){
    Optional<UserEntity> currentUser = userRepository.findByUsername(user.getName());
    if(currentUser.get().getUser_id()!=id || !currentUser.isPresent()){
        return new ResponseEntity<>("Unauthorized request", HttpStatus.BAD_REQUEST);
    }
    newDetails.setUser_id(id);
    newDetails.setPassword(passwordEncoder.encode(newDetails.getPassword()));
    newDetails.setKarateStyles(currentUser.get().getKarateStyles());
    userRepository.save(newDetails);
        return new ResponseEntity<>("Details edited successfully", HttpStatus.OK);
    }

    public ResponseEntity<?> addStyle(int id, long styleId, Principal user){
        Optional<UserEntity> currentUser = userRepository.findByUsername(user.getName());
        if(currentUser.get().getUser_id()!=id || !currentUser.isPresent()){
            return new ResponseEntity("Unauthorized request", HttpStatus.BAD_REQUEST);
        }
        Optional<KarateStyleEntity> styleToAdd = karateStyleRepository.findById(styleId);
        List<KarateStyleEntity> allStyles = currentUser.get().getKarateStyles();
        if(currentUser.isPresent()&& styleToAdd.isPresent()) {
            if(!allStyles.contains(styleToAdd.get())) {
                allStyles.add(styleToAdd.get());
                currentUser.get().setUser_id(id);
                currentUser.get().setKarateStyles(allStyles);
                userRepository.save(currentUser.get());
                return new ResponseEntity("Style added to your stylelist successfully!!", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Your karatelist already has this style", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> removeStyle(int id, long styleId, Principal user){
        Optional<UserEntity> currentUser = userRepository.findByUsername(user.getName());
        if(currentUser.get().getUser_id()!=id||!currentUser.isPresent()){
            return new ResponseEntity<>("Unauthorized request", HttpStatus.BAD_REQUEST);
        }
        List<KarateStyleEntity>allStyles = currentUser.get().getKarateStyles();
        Optional<KarateStyleEntity> styleToRemove = karateStyleRepository.findById(styleId);
        if(allStyles.contains(styleToRemove.get())){
            allStyles.remove(styleToRemove.get());
            currentUser.get().setKarateStyles(allStyles);
            userRepository.save(currentUser.get());
            return new ResponseEntity<>("Style removed success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not remove style", HttpStatus.NOT_FOUND);
    }

}
