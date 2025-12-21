package com.example.yinling.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("fall_detection")
public class FallDetection {
    @TableId
    private String id;

    private String userId;

    private Double latitude; // 纬度

    private Double longitude; // 经度

    private String address; // 地址

    private Integer confidence; // 置信度（0-100）

    private String status; // confirmed, pending, false_alarm

    private LocalDateTime detectionTime;

    private LocalDateTime confirmedTime;

    private String confirmedBy; // 由谁确认

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
