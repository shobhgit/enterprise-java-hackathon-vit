package com.ruralhealth.publichealth.dto;

import com.ruralhealth.publichealth.entity.enums.CleanlinessStatus;
import com.ruralhealth.publichealth.entity.enums.FacilityType;
import jakarta.validation.constraints.*;

public class FacilityRequest {
 @NotBlank(message="name must not be blank")
 private String name;
 @NotNull(message="type is required")
 private FacilityType type;
 @NotBlank(message="location must not be blank")
 private String location;
 @NotNull(message="cleanlinessStatus is required")
 private CleanlinessStatus cleanlinessStatus;

 public FacilityRequest() {}
 public String getName(){return name;} public void setName(String v){name=v;}
 public FacilityType getType(){return type;} public void setType(FacilityType v){type=v;}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public CleanlinessStatus getCleanlinessStatus(){return cleanlinessStatus;} public void setCleanlinessStatus(CleanlinessStatus v){cleanlinessStatus=v;}
}
