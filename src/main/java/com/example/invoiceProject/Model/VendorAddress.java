package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "VendorAddress")
public class VendorAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String postCode;

    @Transient // Không lưu vào DB
    private String fullAddress;

    public VendorAddress() {
    }
    // Khởi tạo và tính toán fullAddress trong constructor
    public VendorAddress(String street, String city, String country, String postCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.fullAddress = calculateFullAddress();
    }

    // Phương thức để tính toán fullAddress
    public String calculateFullAddress() {
        return street + " " + city + " " + country;
    }

    // Gọi phương thức này nếu các trường khác được thay đổi
    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateFullAddress() {
        this.fullAddress = calculateFullAddress();
    }
}
