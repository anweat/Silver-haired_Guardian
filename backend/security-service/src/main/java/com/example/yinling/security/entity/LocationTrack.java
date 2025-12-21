package com.example.yinling.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("location_track")
public class LocationTrack {
    @TableId
    private String id;

    private String userId;

    private Double latitude;

    private Double longitude;

    private String address;

    private Double speed; // 速度（m/s）

    private Double accuracy; // 精度

    private LocalDateTime timestamp;

    private LocalDateTime createdAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
