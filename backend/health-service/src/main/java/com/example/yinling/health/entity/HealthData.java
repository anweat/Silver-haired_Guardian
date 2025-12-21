package com.example.yinling.health.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("health_data")
public class HealthData {
    @TableId
    private String id;

    private String userId;

    private Integer heartRate; // 心率（次/分）

    private String bloodPressure; // 血压（如：120/80）

    private Double temperature; // 体温（℃）

    private Double bloodSugar; // 血糖（mg/dL）

    private Integer steps; // 步数

    private Double distance; // 距离（km）

    private Integer calories; // 卡路里

    private String remarks; // 备注

    private LocalDateTime recordTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
