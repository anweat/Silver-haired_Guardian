package com.example.yinling.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("message_session")
public class MessageSession {
    @TableId
    private String id;

    private String userId;

    private String contactId;

    private String contactName;

    private String contactAvatar;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    private Integer unreadCount;

    private Integer isPinned; // 0: 未置顶, 1: 已置顶

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @TableLogic
    private LocalDateTime deletedAt;
}
