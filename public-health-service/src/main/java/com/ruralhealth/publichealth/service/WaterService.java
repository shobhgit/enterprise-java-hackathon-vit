package com.ruralhealth.publichealth.service;
import com.ruralhealth.publichealth.dto.*;
import com.ruralhealth.publichealth.entity.enums.WaterActionStatus;
import java.util.List;
public interface WaterService { List<WaterRecordResponse> all(WaterActionStatus s); WaterRecordResponse one(Long id); WaterRecordResponse create(WaterRecordRequest r); WaterRecordResponse update(Long id,WaterActionRequest r); }
