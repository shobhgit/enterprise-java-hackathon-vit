package com.ruralhealth.publichealth.service;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.*;
import java.util.List;
public interface SanitationService {
 List<FacilityResponse> facilities(); FacilityResponse facility(Long id); FacilityResponse createFacility(FacilityRequest r);
 FacilityResponse cleanliness(Long id,CleanlinessStatus status); MaintenanceResponse addMaintenance(Long id,MaintenanceRequest r); List<MaintenanceResponse> maintenance(Long id);
 List<ComplaintResponse> complaints(WorkStatus status); ComplaintResponse complaint(Long id); ComplaintResponse createComplaint(ComplaintRequest r); ComplaintResponse updateComplaint(Long id,ComplaintStatusRequest r);
}
