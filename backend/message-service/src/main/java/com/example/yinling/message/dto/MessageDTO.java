package com.example.yinling.message.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageRequest {
    @NotBlank(message = "接收者ID不能为空")
    private String receiverId;

    @NotBlank(message = "消息内容不能为空")
    private String content;

    private String messageType; // 默认为 text

    private String mediaUrl;

    private Long timestamp;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {
    private String id;
    private String senderId;
    private String senderName;
    private String senderAvatar;
    private String receiverId;
    private String content;
    private String messageType;
    private String mediaUrl;
    private Boolean isRead;
    private Long readTime;
    private Long timestamp;
    private Long createdAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageSessionDTO {
    private String id;
    private String userId;
    private String contactId;
    private String contactName;
    private String contactAvatar;
    private String lastMessage;
    private Long lastMessageTime;
    private Integer unreadCount;
    private Integer isPinned;
    private Long createdAt;
    private Long updatedAt;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageListRequest {
    private String contactId;
    private int page;
    private int pageSize;
}
