package za.ac.cput.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Address;
import za.ac.cput.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Address> getAll() {
        return repository.findAll();
    }

    @Override
    public Address create(Address address) {
        return repository.save(address);
    }

    @Override
    public Address read(Long id) {
        Optional<Address> address = repository.findById(id);
        return address.orElse(null);
    }

    @Override
    public Address update(Address address) {
        return null;
    }

}