package com.ruralhealth.publichealth.dto;
import java.time.LocalDateTime;
public record PendingItem(String type,Long id,String summary,String status,LocalDateTime createdAt) {}
