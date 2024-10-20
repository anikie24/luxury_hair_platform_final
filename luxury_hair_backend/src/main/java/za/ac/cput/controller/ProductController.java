package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import za.ac.cput.domain.Product;
import za.ac.cput.services.ProductService;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;


    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(
            @RequestParam("hairTexture") String hairTexture,
            @RequestParam("hairStyle") String hairStyle,
            @RequestParam("hairSize") String hairSize,
            @RequestParam("hairColor") String hairColor,
            @RequestParam("hairStock") boolean hairStock,
            @RequestParam("hairPrice") double hairPrice,
            @RequestParam("image") MultipartFile image) throws IOException {

        Product product = service.createProduct( hairTexture,  hairStyle,  hairSize,
                hairColor,  hairStock,  hairPrice,  image);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }


    @GetMapping("/read/{productId}")
    public Product read(@PathVariable Long productId){
        return service.read(productId);
    }

    //    @PostMapping("/update")
//    public Product update(@RequestBody Product product){
//        return service.update(product);
//    }
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(
            @RequestParam("productId") long productId,
            @RequestParam("hairTexture") String hairTexture,
            @RequestParam("hairStyle") String hairStyle,
            @RequestParam("hairSize") String hairSize,
            @RequestParam("hairColor") String hairColor,
            @RequestParam("hairStock") boolean hairStock,
            @RequestParam("hairPrice") double hairPrice,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Product product = service.updateProduct(productId, hairTexture, hairStyle, hairSize,
                hairColor, hairStock, hairPrice, image);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(service.getall());
    }


    @GetMapping("/image/{productId}")
    public ResponseEntity<String> getProductImage(@PathVariable Long productId) {
        try {
            Optional<byte[]> optionalImageBytes = Optional.ofNullable(service.getProductImageById(productId));


            if (optionalImageBytes.isPresent()) {
                String base64Image = Base64.getEncoder().encodeToString(optionalImageBytes.get());
                return new ResponseEntity<>(base64Image, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Image not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving image", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long productId) {
        boolean deleted = service.delete(productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /*@GetMapping("/image/{productId}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long productId) {
        return service.getProductImageById(productId)
                .map(imageBytes -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                        .body(imageBytes))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }*/

}