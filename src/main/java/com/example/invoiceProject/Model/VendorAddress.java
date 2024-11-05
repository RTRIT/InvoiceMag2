package com.example.invoiceProject.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "VendorAddress")
public class VendorAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "BINARY(16)")
    private UUID vendorAddressUuid;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String postCode;

    // Trường fullAddress sẽ không được tự động tính toán khi khai báo

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
        return street + " " + city + " " + country + " " + postCode;
    }

    // Gọi phương thức này nếu các trường khác được thay đổi
    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateFullAddress() {
        this.fullAddress = calculateFullAddress();
    }
}
