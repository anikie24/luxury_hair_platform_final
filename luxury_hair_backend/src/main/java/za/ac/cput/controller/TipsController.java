package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Tips;
import za.ac.cput.repository.TipsRepository;
import za.ac.cput.services.TipsService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/tips")
@CrossOrigin(origins = "http://localhost:5173")
public class TipsController {

    @Autowired
    private TipsService tipsService;

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Tips create(@RequestParam("id") int id, @RequestParam("image") MultipartFile image) throws IOException {
        byte[] imageBytes = image.getBytes();
        Tips tips = new Tips.Builder().setId(id).setImage(imageBytes).build();
        return tipsService.create(tips);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Tips> getTip(@PathVariable Integer id) {
        Tips tip = tipsService.read(id);
        if (tip != null) {
            return ResponseEntity.ok(tip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Tips>> getAllTips() {
        List<Tips> tipsList = tipsService.getall();
        return ResponseEntity.ok(tipsList);
    }

    @GetMapping("/get-with-image/{id}")
    public ResponseEntity<String> getTipWithImage(@PathVariable Integer id) {
        Tips tip = tipsService.read(id);
        if (tip != null) {
            String base64Image = Base64.getEncoder().encodeToString(tip.getImage());
            return ResponseEntity.ok("data:image/jpeg;base64," + base64Image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Tips> updateTip(@RequestBody Tips tip) {
        Tips updatedTip = tipsService.update(tip);
        return ResponseEntity.ok(updatedTip);
    }
}
