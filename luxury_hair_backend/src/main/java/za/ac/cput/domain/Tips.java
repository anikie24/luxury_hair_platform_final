package za.ac.cput.domain;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Tips {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Column(length = 1000000)
    private byte[] image;

    public Tips() {}

    public Tips(Builder builder) {
        this.id = builder.id;
        this.image = builder.image;
    }

    public int getId() {
        return id;
    }

    public byte[] getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tips tips = (Tips) o;
        return id == tips.id && Arrays.equals(image, tips.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public static class Builder {
        private int id;
        private byte[] image;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setImage(byte[] image) {
            this.image = image;
            return this;
        }

        public Builder copy(Tips tips) {
            this.id = tips.id;
            this.image = tips.image;
            return this;
        }

        public Tips build() {
            return new Tips(this);
        }
    }
}
