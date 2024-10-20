package za.ac.cput.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Tips;
import za.ac.cput.repository.TipsRepository;

import java.util.List;

@Service
public class TipsService implements ITipsServices {

    @Autowired
    private TipsRepository repository;

    @Override
    public List<Tips> getall() { // Fixed method name to camelCase
        return repository.findAll();
    }

    @Override
    public Tips create(Tips tips) { // Changed parameter name for clarity
        return repository.save(tips);
    }

    @Override
    public Tips read(Integer id) { // Changed parameter name for clarity
        return repository.findById(id).orElse(null);
    }

    @Override
    public Tips update(Tips tips) { // Changed parameter name for clarity
        return repository.save(tips);
    }
}
