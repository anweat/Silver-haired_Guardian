package com.example.yinling.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("message")
public class Message {
    @TableId
    private String id;

    private String senderId;

    private String senderName;

    private String senderAvatar;

    private String receiverId;

    private String content;

    private String messageType; // text, image, audio, video

    private String mediaUrl; // 媒体URL

    private Boolean isRead;

    private LocalDateTime readTime;

    private LocalDateTime timestamp;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
