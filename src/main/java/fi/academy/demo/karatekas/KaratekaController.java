package fi.academy.demo.karatekas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/karatekas")
public class KaratekaController {

    @Autowired
    private KaratekaService karatekaService;

    @GetMapping("")
    public Iterable<KaratekaEntity> findAll() {
        return karatekaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOneById(@PathVariable long id){
        return karatekaService.findById(id);
    }

    @PostMapping("")
    public ResponseEntity<?> saveKarateka(@RequestBody KaratekaEntity karateka){
     return karatekaService.saveKarateka(karateka);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKarateka(@RequestBody KaratekaEntity karateka, @PathVariable long id){
        return karatekaService.updateKarateka(karateka, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKarateka(@PathVariable long id){
        return karatekaService.deleteKarateka(id);
    }

    @PutMapping("add/{id}")
    public ResponseEntity<?> addTradition(@RequestParam long styleId, @PathVariable long id){
        return karatekaService.addTradition(styleId, id);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<?>removeTradition(@RequestParam long styleId, @PathVariable long id){
        return karatekaService.removeTradition(styleId, id);
    }
}
