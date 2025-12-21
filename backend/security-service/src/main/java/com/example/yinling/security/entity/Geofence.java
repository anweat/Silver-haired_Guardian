package com.example.yinling.security.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("geofence")
public class Geofence {
    @TableId
    private String id;

    private String userId;

    private String name; // 围栏名称（如：家）

    private Double latitude; // 纬度

    private Double longitude; // 经度

    private Integer radius; // 半径（米）

    private Integer status; // 0: 禁用, 1: 启用

    private String remarks;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
