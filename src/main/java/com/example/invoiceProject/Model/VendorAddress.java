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

    @Column
    private String numberAddress;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private Long postCode;

    // Trường fullAddress sẽ không được tự động tính toán khi khai báo
    @Transient // Không lưu vào DB
    private String fullAddress;

    public VendorAddress() {
    }

    // Khởi tạo và tính toán fullAddress trong constructor
    public VendorAddress(String numberAddress, String street, String city, String country, Long postCode) {
        this.numberAddress = numberAddress;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
        this.fullAddress = calculateFullAddress();
    }

    // Phương thức để tính toán fullAddress
    public String calculateFullAddress() {
        return numberAddress + " " + street + " " + city + " " + country + " " + postCode;
    }

    // Gọi phương thức này nếu các trường khác được thay đổi
    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateFullAddress() {
        this.fullAddress = calculateFullAddress();
    }
}
