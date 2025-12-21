package com.example.yinling.health.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("medication_reminder")
public class MedicationReminder {
    @TableId
    private String id;

    private String userId;

    private String medicationName; // 药物名称

    private String dosage; // 剂量（如：1片、10ml）

    private String frequency; // 频率（如：每天2次、每周3次）

    private String reminderTime; // 提醒时间（如：08:00,20:00）

    private LocalDateTime nextReminderTime; // 下次提醒时间

    private LocalDateTime lastTakenTime; // 上次服用时间

    private Integer status; // 0: 未服用, 1: 已服用, 2: 已过期

    private String remarks; // 备注

    private LocalDateTime startDate; // 开始日期

    private LocalDateTime endDate; // 结束日期

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
