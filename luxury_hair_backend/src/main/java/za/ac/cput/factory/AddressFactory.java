package za.ac.cput.factory;

import za.ac.cput.domain.Address;
import za.ac.cput.util.Helper;

public class AddressFactory {
    public static Address buildAdd(Long addressId, String streetName, String province, String city, int zipCode) {

        if (Helper.isNullOrEmpty(String.valueOf(addressId)) || Helper.isNullOrEmpty(streetName)
                || Helper.isNullOrEmpty(province) || Helper.isNullOrEmpty(city)
                || Helper.isNullOrEmpty(String.valueOf(zipCode))) {
            return null;
        }


        return new Address.Builder()
                .setAddressId(addressId)
                .setStreetName(streetName)
                .setProvince(province)
                .setCity(city)
                .setZipCode(zipCode)
                .build();
    }
}