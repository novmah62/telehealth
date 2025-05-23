package com.drewsec.prescription_service.mapper;

import com.drewsec.prescription_service.dto.request.PrepareRequest;
import com.drewsec.prescription_service.dto.request.PrescriptionItemRequest;
import com.drewsec.prescription_service.dto.request.PrescriptionRequest;
import com.drewsec.prescription_service.dto.response.DigitalSignatureResponse;
import com.drewsec.prescription_service.dto.response.PrescriptionItemResponse;
import com.drewsec.prescription_service.dto.response.PrescriptionResponse;
import com.drewsec.prescription_service.entity.DigitalSignature;
import com.drewsec.prescription_service.entity.Prescription;
import com.drewsec.prescription_service.entity.PrescriptionItem;

import java.util.List;

public class PrescriptionMapper {

    public static Prescription toEntityFromPrepare(PrepareRequest prepareReq, String prescriptionCode) {
        var entity = new Prescription();
        entity.setDoctorId(prepareReq.doctorId());
        entity.setPatientId(prepareReq.patientId());
        entity.setDiagnosisSummary(prepareReq.diagnosisSummary());
        entity.setPrescriptionCode(prescriptionCode);
        return entity;
    }

    public static void updateEntityFromRequest(
            Prescription entity,
            PrescriptionRequest req,
            DigitalSignature signature,
            String sqrcImageBase64
    ) {
        entity.setType(req.type());
        entity.setItems(mapToItems(req.items()));
        entity.setSignature(signature);
        entity.setSqrcImageBase64(sqrcImageBase64);
    }

    public static PrescriptionResponse toResponse(Prescription entity) {
        return PrescriptionResponse.builder()
                .id(entity.getId())
                .prescriptionCode(entity.getPrescriptionCode())
                .doctorId(entity.getDoctorId())
                .patientId(entity.getPatientId())
                .items(mapToItemResponses(entity.getItems()))
                .diagnosisSummary(entity.getDiagnosisSummary())
                .type(entity.getType())
                .issuedAt(entity.getIssuedAt())
                .signature(mapToSignatureResponse(entity.getSignature()))
                .sqrcImageBase64(entity.getSqrcImageBase64())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .version(entity.getVersion())
                .build();
    }


    private static List<PrescriptionItem> mapToItems(List<PrescriptionItemRequest> reqItems) {
        return reqItems.stream()
                .map(r -> {
                    var item = new PrescriptionItem();
                    item.setMedicineCode(r.medicineCode());
                    item.setQuantity(r.quantity());
                    item.setDosage(r.dosage());
                    item.setNote(r.note());
                    return item;
                })
                .toList();
    }

    private static List<PrescriptionItemResponse> mapToItemResponses(List<PrescriptionItem> items) {
        return items.stream()
                .map(i -> PrescriptionItemResponse.builder()
                        .medicineCode(i.getMedicineCode())
                        .quantity(i.getQuantity())
                        .dosage(i.getDosage())
                        .note(i.getNote())
                        .build())
                .toList();
    }


    private static DigitalSignatureResponse mapToSignatureResponse(DigitalSignature sig) {
        if (sig == null) return null;
        return DigitalSignatureResponse.builder()
                .signatureValueBase64(sig.getSignatureValueBase64())
                .algorithm(sig.getAlgorithm())
                .certificateSerial(sig.getCertificateSerial())
                .build();
    }
}

