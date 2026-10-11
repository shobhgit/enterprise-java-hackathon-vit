package com.ruralhealth.publichealth.service;
import com.ruralhealth.publichealth.dto.*;
import java.util.List;
public interface MedicineService {
 List<MedicineResponse> all(Long centreId); MedicineResponse one(Long id); MedicineResponse create(MedicineRequest r);
 List<MedicineResponse> lowStock(Long centreId); IssueResponse issue(Long id,IssueRequest r); List<IssueResponse> issues(Long id); MedicineResponse restock(Long id,RestockRequest r);
}
