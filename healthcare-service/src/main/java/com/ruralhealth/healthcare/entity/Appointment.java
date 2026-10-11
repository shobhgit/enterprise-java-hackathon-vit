package com.ruralhealth.healthcare.entity;
import com.ruralhealth.healthcare.entity.enums.AppointmentStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //multiple appointment may be connected to same worker,centre,resident
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_centre_id", nullable = false)
    private HealthCentre healthCentre;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "health_worker_id", nullable = false)
    private HealthWorker healthWorker;
    @Column(nullable = false)
    private LocalDateTime scheduledAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    //with this. the status will be initialiezed with scheduled as default.
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;
    private String reason;

    public Appointment() {
    }

    public Long getId() {
        return id;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public HealthCentre getHealthCentre() {
        return healthCentre;
    }

    public void setHealthCentre(HealthCentre healthCentre) {
        this.healthCentre = healthCentre;
    }

    public HealthWorker getHealthWorker() {
        return healthWorker;
    }

    public void setHealthWorker(HealthWorker healthWorker) {
        this.healthWorker = healthWorker;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
