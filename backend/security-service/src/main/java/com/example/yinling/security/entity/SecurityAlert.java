package com.example.yinling.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("security_alert")
public class SecurityAlert {
    @TableId
    private String id;

    private String userId;

    private String alertType; // fall_detection, geofence_violation, unusual_activity, emergency_call

    private String description;

    private String severity; // high, medium, low

    private Integer status; // 0: 未处理, 1: 已处理, 2: 误报

    private LocalDateTime alertTime;

    private LocalDateTime handleTime;

    private String handledBy;

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
