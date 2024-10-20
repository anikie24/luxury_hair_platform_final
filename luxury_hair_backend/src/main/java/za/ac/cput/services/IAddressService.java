package za.ac.cput.services;

import za.ac.cput.domain.Address;

import java.util.List;

public interface IAddressService extends IService<Address, Long> {

    List<Address> getAll();
}