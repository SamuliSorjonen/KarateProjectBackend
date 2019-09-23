package fi.academy.demo.karatestyles;

import fi.academy.demo.karatekas.KaratekaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/styles")
public class KarateStyleController {

    @Autowired
    private KarateStyleService karateStyleService;
    @Autowired
    private KarateStyleRepository karateStyleRepository;


    @GetMapping("")
    public Iterable<KarateStyleEntity> findAll() {
        return karateStyleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOnebyId(@PathVariable long id) {
        return karateStyleService.findOneById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> saveStyle(@RequestBody KarateStyleEntity style, Principal user){
        return karateStyleService.saveStyle(style, user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKaratestyle(@RequestBody KarateStyleEntity style, @PathVariable long id){
        return karateStyleService.updateKarateStyle(style, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKarateStyle(@PathVariable long id){
      return karateStyleService.deleteKarateStyle(id);
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<?> addKarateka(@RequestParam long karatekaId, @PathVariable long id){
        return karateStyleService.addKarateka(karatekaId, id);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeKarateka(@RequestParam long karatekaId, @PathVariable long id){
        return karateStyleService.removeKarateka(karatekaId, id);
    }

    @PutMapping("/addfounder/{id}")
    public ResponseEntity<?> addFounder(@RequestParam long karatekaId, @PathVariable long id){
        return karateStyleService.addFounder(karatekaId, id);
    }

    @PutMapping("/removefounder/{id}")
    public ResponseEntity<?> removeFounder(@PathVariable long id){
        return karateStyleService.removeFounder(id);
    }
}
