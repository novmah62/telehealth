package com.drewsec.prescription_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class PrescriptionItem {

    @Column(name = "medicine_code", nullable = false, length = 20)
    private String medicineCode;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "dosage", nullable = false, length = 100)
    private String dosage;

    @Column(name = "note", nullable = false, length = 200)
    private String note;

}
