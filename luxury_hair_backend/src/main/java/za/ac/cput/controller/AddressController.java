package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Address;
import za.ac.cput.services.IAddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "http://localhost:5173")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<Address> create(@RequestBody Address address) {
        Address newAddress = addressService.create(address);
        return ResponseEntity.ok(newAddress);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> read(@PathVariable Long id) {
        Address address = addressService.read(id);
        return address != null ? ResponseEntity.ok(address) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Address> update(@RequestBody Address address) {
        Address updatedAddress = addressService.update(address);
        return updatedAddress != null ? ResponseEntity.ok(updatedAddress) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Address>> getAll() {
        List<Address> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }
}