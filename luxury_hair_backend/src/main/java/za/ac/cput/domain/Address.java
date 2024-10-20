package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    private String streetName;
    private String city;
    private String province;
    private int zipCode;

    public Address() {
    }

    public Address(Builder builder) {
        this.addressId = builder.addressId;
        this.streetName = builder.streetName;
        this.province = builder.province;
        this.city = builder.city;
        this.zipCode = builder.zipCode;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public int getZipCode() {
        return zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return zipCode == address.zipCode &&
                Objects.equals(addressId, address.addressId) &&
                Objects.equals(streetName, address.streetName) &&
                Objects.equals(province, address.province) &&
                Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, streetName, province, city, zipCode);
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", streetName='" + streetName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", zipCode=" + zipCode +
                '}';
    }

    public static class Builder {
        private Long addressId;
        private String streetName;
        private String province;
        private String city;
        private int zipCode;

        public Builder setAddressId(Long addressId) {
            this.addressId = addressId;
            return this;
        }

        public Builder setStreetName(String streetName) {
            this.streetName = streetName;
            return this;
        }

        public Builder setProvince(String province) {
            this.province = province;
            return this;
        }

        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setZipCode(int zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder copy(Address address) {
            this.addressId = address.addressId;
            this.streetName = address.streetName;
            this.province = address.province;
            this.city = address.city;
            this.zipCode = address.zipCode;
            return this;
        }

        public Address build() {
            return new Address(this);
        }
    }
}