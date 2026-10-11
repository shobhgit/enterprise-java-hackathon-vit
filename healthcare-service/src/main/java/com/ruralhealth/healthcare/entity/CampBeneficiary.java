
package com.ruralhealth.healthcare.entity;

import com.ruralhealth.healthcare.entity.enums.FollowUpStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "camp_beneficiaries", uniqueConstraints = {
        @UniqueConstraint(name = "uk_camp_beneficiary", columnNames = {"camp_id", "resident_id"})})
public class CampBeneficiary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "camp_id", nullable = false)
    private HealthCamp camp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resident_id", nullable = false)
    private Resident resident;

    @Column(length = 2000)
    private String screeningResult;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowUpStatus followUpStatus =
            FollowUpStatus.NOT_REQUIRED;

    public CampBeneficiary() {
    }

    public Long getId() {
        return id;
    }

    public HealthCamp getCamp() {
        return camp;
    }

    public void setCamp(HealthCamp camp) {
        this.camp = camp;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getScreeningResult() {
        return screeningResult;
    }

    public void setScreeningResult(String screeningResult) {
        this.screeningResult = screeningResult;
    }

    public FollowUpStatus getFollowUpStatus() {
        return followUpStatus;
    }

    public void setFollowUpStatus(FollowUpStatus followUpStatus) {
        this.followUpStatus = followUpStatus;
    }
}
