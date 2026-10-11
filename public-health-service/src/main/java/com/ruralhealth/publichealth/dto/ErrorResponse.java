package com.ruralhealth.publichealth.dto;
import java.util.Map;
public record ErrorResponse(String error, Map<String,String> fields) {
 public ErrorResponse(String error){this(error,null);}
}
