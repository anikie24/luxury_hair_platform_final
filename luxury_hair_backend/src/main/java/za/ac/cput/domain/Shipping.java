package za.ac.cput.domain;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;


@Entity
    public class Shipping {
         @Id
        private String shippingID;
        private String orderID;
        private String shippingCourier;
        private int trackingNumber;
        private double shippingCost;
        private String shippingDate;
        private String estimatedDeliveryDate;
        private String deliveryStatus;

        protected Shipping() {
        }

        protected Shipping(Builder builder) {
            this.shippingID = builder.shippingID;
            this.orderID = builder.orderID;
            this.shippingCourier = builder.shippingCourier;
            this.trackingNumber = builder.trackingNumber;
            this.shippingCost = builder.shippingCost;
            this.shippingDate = builder.shippingDate;
            this.estimatedDeliveryDate =builder.estimatedDeliveryDate;
            this.deliveryStatus = builder.deliveryStatus;
        }

        public String getShippingID() {
            return shippingID;
        }

        public String getOrderID() {
            return orderID;
        }

        public String getShippingCourier() {
            return shippingCourier;
        }

        public int getTrackingNumber() {
            return trackingNumber;
        }

        public double getShippingCost() {
            return shippingCost;
        }

        public String getShippingDate() {
            return shippingDate;
        }

        public String getEstimatedDeliveryDate() {
            return estimatedDeliveryDate;
        }

        public String getDeliveryStatus() {
            return deliveryStatus;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Shipping shipping = (Shipping) o;
            return trackingNumber == shipping.trackingNumber && Double.compare(shippingCost, shipping.shippingCost) == 0 && Objects.equals(shippingID, shipping.shippingID) && Objects.equals(orderID, shipping.orderID) && Objects.equals(shippingCourier, shipping.shippingCourier) && Objects.equals(shippingDate, shipping.shippingDate) && Objects.equals(estimatedDeliveryDate, shipping.estimatedDeliveryDate) && Objects.equals(deliveryStatus, shipping.deliveryStatus);
        }

        @Override
        public int hashCode() {
            return Objects.hash(shippingID, orderID, shippingCourier, trackingNumber, shippingCost, shippingDate, estimatedDeliveryDate, deliveryStatus);
        }

        @Override
        public String toString() {
            return "Shipping{" +
                    "shippingID='" + shippingID + '\'' +
                    ", orderID='" + orderID + '\'' +
                    ", shippingCourier='" + shippingCourier + '\'' +
                    ", trackingNumber=" + trackingNumber +
                    ", shippingCost=" + shippingCost +
                    ", shippingDate=" + shippingDate +
                    ", estimatedDeliveryDate=" + estimatedDeliveryDate +
                    ", deliveryStatus='" + deliveryStatus + '\'' +
                    '}';
        }
        public static class Builder{
            private String shippingID;
            private String orderID;
            private String shippingCourier;
            private int trackingNumber;
            private double shippingCost;
            private String shippingDate;
            private String estimatedDeliveryDate;
            private String deliveryStatus;

            public Builder setShippingID(String shippingID) {
                this.shippingID = shippingID;
                return this;
            }

            public Builder setOrderID(String orderID) {
                this.orderID = orderID;
                return this;
            }

            public Builder setShippingCourier(String shippingCourier) {
                this.shippingCourier = shippingCourier;
                return this;
            }

            public Builder setTrackingNumber(int trackingNumber) {
                this.trackingNumber = trackingNumber;
                return this;
            }

            public Builder setShippingCost(double shippingCost) {
                this.shippingCost = shippingCost;
                return this;
            }

            public Builder setShippingDate(String shippingDate) {
                this.shippingDate = shippingDate;
                return this;
            }

            public Builder setEstimatedDeliveryDate(String estimatedDeliveryDate) {
                this.estimatedDeliveryDate = estimatedDeliveryDate;
                return this;

            }

            public Builder setDeliveryStatus(String deliveryStatus) {
                this.deliveryStatus = deliveryStatus;
                return this;
            }

            public Builder copy(Shipping ship){
                this.shippingID = ship.shippingID;
                this.orderID = ship.orderID;
                this.shippingCourier = ship.shippingCourier;
                this.trackingNumber = ship.trackingNumber;
                this.shippingCost = ship.shippingCost;
                this.shippingDate = ship.shippingDate;
                this.estimatedDeliveryDate = ship.estimatedDeliveryDate;
                this.deliveryStatus = ship.deliveryStatus;
                return this;
            }
            public Shipping build(){
                return new Shipping(this);

            }
        }
    }

