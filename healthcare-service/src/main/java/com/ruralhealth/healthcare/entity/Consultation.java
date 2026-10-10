
package com.ruralhealth.healthcare.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //one consultaiton per appointment
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;
    @Column(nullable = false, length = 2000)
    private String diagnosis;
    @Column(length = 4000)
    private String prescription;
    @Column(length = 2000)
    private String notes;
    @Column(nullable = false)
    private LocalDateTime consultedAt;

    public Consultation() {
    }

    public Long getId() {
        return id;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getConsultedAt() {
        return consultedAt;
    }

    public void setConsultedAt(LocalDateTime consultedAt) {
        this.consultedAt = consultedAt;
    }
}
