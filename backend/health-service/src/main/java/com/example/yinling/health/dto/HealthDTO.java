package com.example.yinling.health.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataDTO {
    @NotNull(message = "心率不能为空")
    private Integer heartRate;

    @NotBlank(message = "血压不能为空")
    private String bloodPressure;

    @NotNull(message = "体温不能为空")
    private Double temperature;

    private Double bloodSugar;

    private Integer steps;

    private Double distance;

    private Integer calories;

    private String remarks;

    private Long recordTime; // 时间戳（毫秒）
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthDataResponseDTO {
    private String id;
    private String userId;
    private Integer heartRate;
    private String bloodPressure;
    private Double temperature;
    private Double bloodSugar;
    private Integer steps;
    private Double distance;
    private Integer calories;
    private String remarks;
    private Long recordTime;
    private Long createdAt;
    private Long updatedAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationReminderDTO {
    @NotBlank(message = "药物名称不能为空")
    private String medicationName;

    @NotBlank(message = "剂量不能为空")
    private String dosage;

    @NotBlank(message = "频率不能为空")
    private String frequency;

    @NotBlank(message = "提醒时间不能为空")
    private String reminderTime;

    private String remarks;

    private Long startDate;

    private Long endDate;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationReminderResponseDTO {
    private String id;
    private String userId;
    private String medicationName;
    private String dosage;
    private String frequency;
    private String reminderTime;
    private Long nextReminderTime;
    private Long lastTakenTime;
    private Integer status;
    private String remarks;
    private Long startDate;
    private Long endDate;
    private Long createdAt;
    private Long updatedAt;
}
