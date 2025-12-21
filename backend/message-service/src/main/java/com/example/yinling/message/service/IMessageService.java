package com.example.yinling.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yinling.message.dto.MessageResponseDTO;
import com.example.yinling.message.dto.SendMessageRequest;
import com.example.yinling.message.entity.Message;
import java.util.List;

public interface IMessageService extends IService<Message> {
    /**
     * 发送消息
     */
    MessageResponseDTO sendMessage(String userId, SendMessageRequest request);

    /**
     * 获取消息列表
     */
    List<MessageResponseDTO> getMessages(String userId, String contactId, int page, int pageSize);

    /**
     * 获取收到的消息
     */
    List<MessageResponseDTO> getReceivedMessages(String userId, int page, int pageSize);

    /**
     * 标记消息为已读
     */
    boolean markAsRead(String userId, String messageId);

    /**
     * 标记来自某个用户的所有消息为已读
     */
    boolean markAllAsReadFrom(String userId, String senderId);

    /**
     * 获取未读消息数
     */
    long getUnreadCount(String userId);

    /**
     * 获取来自某个用户的未读消息数
     */
    long getUnreadCountFrom(String userId, String senderId);

    /**
     * 删除消息
     */
    boolean deleteMessage(String userId, String messageId);
}
