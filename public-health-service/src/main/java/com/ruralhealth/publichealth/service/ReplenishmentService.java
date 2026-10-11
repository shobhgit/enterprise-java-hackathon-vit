package com.ruralhealth.publichealth.service;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.WorkStatus;
import java.util.List;
public interface ReplenishmentService { List<ReplenishmentResponse> all(WorkStatus status,Long centreId); ReplenishmentResponse one(Long id); ReplenishmentResponse create(ReplenishmentCreateRequest r); ReplenishmentResponse update(Long id,WorkStatus status); }
