package fi.academy.demo.karatestyles;

import fi.academy.demo.karatekas.KaratekaEntity;
import fi.academy.demo.karatekas.KaratekaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class KarateStyleService {

    @Autowired
    KarateStyleRepository karateStyleRepository;
    @Autowired
    KaratekaRepository karatekaRepository;

    public Iterable<KarateStyleEntity> findAll() {
        Iterable<KarateStyleEntity> allKarates = karateStyleRepository.findAll();
        return allKarates;
    }

    public ResponseEntity<?> findOneById(long id) {
        Optional<KarateStyleEntity> wantedStyle = karateStyleRepository.findById(id);
        if (!wantedStyle.isPresent()) {
            return new ResponseEntity<>("There is no style with given id. Please check id and try again.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>( wantedStyle, HttpStatus.OK);
    }

    public ResponseEntity<?> saveStyle(KarateStyleEntity style, Principal user) {
        Iterable<KarateStyleEntity> karateList = karateStyleRepository.findAll();
        for (KarateStyleEntity k : karateList) {
            if (k.getName().toLowerCase().equals(style.getName().toLowerCase())) {
                return new ResponseEntity<>("This style already exists!", HttpStatus.BAD_REQUEST);
            }
        }
        karateStyleRepository.save(style);
        URI loc = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/styles/{id}")
                .buildAndExpand(style.getId())
                .toUri();
        return ResponseEntity.created(loc).build();
    }

    public ResponseEntity<?> updateKarateStyle(KarateStyleEntity style, long id) {
        Optional<KarateStyleEntity> currentStyle = karateStyleRepository.findById(id);
        if (currentStyle.isPresent()) {
            Iterable<KarateStyleEntity> karateList = karateStyleRepository.findAll();
            for (KarateStyleEntity k : karateList) {
                if (k.getName().toLowerCase().equals(style.getName().toLowerCase())) {
                    if (k.getId()==id){
                        break;
                    }
                    return new ResponseEntity<>("Another karate style with given name already exists, could not update to same name!", HttpStatus.BAD_REQUEST);
                }
            }
            style.setId(id);
            style.setPractitioners(currentStyle.get().getPractitioners());
            style.setFounder(currentStyle.get().getFounder());
            karateStyleRepository.save(style);
            return new ResponseEntity<>("Karatestyle updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot find Karatestyle with given id", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteKarateStyle(long id){
        Optional<KarateStyleEntity> currentStyle = karateStyleRepository.findById(id);
        if (currentStyle.isPresent()) {
            karateStyleRepository.deleteById(id);
            return new ResponseEntity<>("Karatestyle " + currentStyle.get().getName() + " deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>( "Nothing could be destroyed!", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addKarateka(long karatekaId, long id){
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(id);
        Optional<KaratekaEntity> karateka = karatekaRepository.findById(karatekaId);
        if (style.isPresent() && karateka.isPresent()) {
            List<KaratekaEntity> karatekas = style.get().getPractitioners();
            if(karatekas.contains(karateka.get())){
                return new ResponseEntity<>("Karatestyle already lists this karateka. Oss!!", HttpStatus.BAD_REQUEST);
            }
            karatekas.add(karateka.get());
            style.get().setId(id);
            style.get().setPractitioners(karatekas);
            karateStyleRepository.save(style.get());
            return new ResponseEntity<>(style.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not add style to karateka", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> removeKarateka(long karatekaId, long id) {
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(id);
        Optional<KaratekaEntity> karateka = karatekaRepository.findById(karatekaId);
        if (style.isPresent() && karateka.isPresent()) {
            List<KaratekaEntity> karatekas = style.get().getPractitioners();
            if (!karatekas.contains(karateka.get())) {
                return new ResponseEntity<>("Karateka is not listed in this style", HttpStatus.NOT_FOUND);
            }
            if (karatekas.contains(karateka.get())) {
                karatekas.remove(karateka.get());
            }
            style.get().setId(id);
            karateStyleRepository.save(style.get());
            return new ResponseEntity<>(style.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not remove karateka from parctitioners list", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addFounder(long karatekaId, long id){
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(id);
        Optional<KaratekaEntity> karateka = karatekaRepository.findById(karatekaId);
        if(style.isPresent()&&karateka.isPresent()) {
            style.get().setFounder(karateka.get());
            karateStyleRepository.save(style.get());
            return new ResponseEntity<>("Founder for style set", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not set founfer", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?>removeFounder(long id){
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(id);
        style.get().setFounder(null);
        karateStyleRepository.save(style.get());
        return new ResponseEntity<>("founder removed successfully", HttpStatus.OK);
    }
}