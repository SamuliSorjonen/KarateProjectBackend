package fi.academy.demo.karatekas;

import fi.academy.demo.karatestyles.KarateStyleEntity;
import fi.academy.demo.karatestyles.KarateStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KaratekaService {

    @Autowired
    KaratekaRepository karatekaRepository;

    @Autowired
    KarateStyleRepository karateStyleRepository;

    public Iterable<KaratekaEntity> findAll() {
        Iterable<KaratekaEntity> allKarates = karatekaRepository.findAll();
        return allKarates;
    }

    public ResponseEntity<?> findById(long id) {
        Optional<KaratekaEntity> wantedKarateka = karatekaRepository.findById(id);
        if (!wantedKarateka.isPresent()) {
            return new ResponseEntity<>("There is no karateka with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(wantedKarateka, HttpStatus.OK);
    }

    public ResponseEntity<?> saveKarateka(KaratekaEntity karateka) {
        karatekaRepository.save(karateka);
        URI loc = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8080)
                .path("/karatekas/{id}")
                .buildAndExpand(karateka.getId())
                .toUri();
        return ResponseEntity.created(loc).build();
    }

    public ResponseEntity<?> updateKarateka(KaratekaEntity karateka, long id) {
        Optional<KaratekaEntity> currentKarateka = karatekaRepository.findById(id);
        karateka.setId(id);
        karateka.setTradition(currentKarateka.get().getTradition());
        karateka.setFounderOf(currentKarateka.get().getFounderOf());
        karatekaRepository.save(karateka);
        return new ResponseEntity<>(karateka, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteKarateka(long id) {
        Optional<KaratekaEntity> karatekaToBeDeleted = karatekaRepository.findById(id);
        if (karatekaToBeDeleted.isPresent()) {
            List<KarateStyleEntity> stylesFounded = karatekaToBeDeleted.get().getFounderOf();
            for(KarateStyleEntity k : stylesFounded){
                k.setFounder(null);
                karateStyleRepository.save(k);
            }
            karatekaToBeDeleted.get().setId(id);
            karatekaToBeDeleted.get().setTradition(null);
            karatekaRepository.save(karatekaToBeDeleted.get());
            karatekaRepository.deleteById(id);
            return new ResponseEntity<>("Karateka with id deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not find karateka with given id", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> addTradition(long styleId, long id) {
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(styleId);
        Optional<KaratekaEntity> karateka = karatekaRepository.findById(id);
        if (style.isPresent() && karateka.isPresent()) {
            List<KarateStyleEntity> styles = karateka.get().getTradition();
            if(styles.contains(style.get())){
                return new ResponseEntity<>("Karateka already has this style", HttpStatus.BAD_REQUEST);
            }
            styles.add(style.get());
            karateka.get().setId(id);
            karateka.get().setTradition(styles);
            karatekaRepository.save(karateka.get());
            return new ResponseEntity<>(karateka.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not add style to karateka", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> removeTradition(long styleId, long id) {
        Optional<KarateStyleEntity> style = karateStyleRepository.findById(styleId);
        Optional<KaratekaEntity> karateka = karatekaRepository.findById(id);
        if (style.isPresent() && karateka.isPresent()) {
            List<KarateStyleEntity> styles = karateka.get().getTradition();
            if (!styles.contains(style.get())) {
                return new ResponseEntity<>("Karateka does not have this style", HttpStatus.BAD_REQUEST);
            }
            if (styles.contains(style.get())) {
                styles.remove(style.get());
            }
            karateka.get().setId(id);
            karatekaRepository.save(karateka.get());
            return new ResponseEntity<>(karateka.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Could not remove style from karateka", HttpStatus.NOT_FOUND);
    }
}