
package com.ruralhealth.healthcare.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class AppointmentForm {

    @NotNull(message = "Please select a resident")
    private Long residentId;

    @NotNull(message = "Please select a health centre")
    private Long healthCentreId;

    @NotNull(message = "Please select a health worker")
    private Long healthWorkerId;

    @NotNull(message = "Please select an appointment date and time")
    @FutureOrPresent(message = "Appointment must be in the present or future")
    private LocalDateTime scheduledAt;

    @Size(max = 500, message = "Reason must not exceed 500 characters")
    private String reason;

    public AppointmentForm() {
    }

    public Long getResidentId() {
        return residentId;
    }

    public void setResidentId(Long residentId) {
        this.residentId = residentId;
    }

    public Long getHealthCentreId() {
        return healthCentreId;
    }

    public void setHealthCentreId(Long healthCentreId) {
        this.healthCentreId = healthCentreId;
    }

    public Long getHealthWorkerId() {
        return healthWorkerId;
    }

    public void setHealthWorkerId(Long healthWorkerId) {
        this.healthWorkerId = healthWorkerId;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
