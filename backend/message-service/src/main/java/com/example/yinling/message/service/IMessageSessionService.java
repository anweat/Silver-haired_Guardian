package com.example.yinling.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yinling.message.dto.MessageSessionDTO;
import com.example.yinling.message.entity.MessageSession;
import java.util.List;

public interface IMessageSessionService extends IService<MessageSession> {
    /**
     * 获取用户的消息会话列表
     */
    List<MessageSessionDTO> getSessionList(String userId);

    /**
     * 获取用户有未读消息的会话
     */
    List<MessageSessionDTO> getSessionsWithUnread(String userId);

    /**
     * 获取或创建消息会话
     */
    MessageSessionDTO getOrCreateSession(String userId, String contactId, String contactName, String contactAvatar);

    /**
     * 标记会话为已读
     */
    boolean markSessionAsRead(String userId, String contactId);

    /**
     * 置顶会话
     */
    boolean pinSession(String userId, String sessionId);

    /**
     * 取消置顶
     */
    boolean unpinSession(String userId, String sessionId);

    /**
     * 删除会话
     */
    boolean deleteSession(String userId, String contactId);

    /**
     * 获取用户的总未读消息数
     */
    long getTotalUnreadCount(String userId);

    /**
     * 更新会话的最后消息
     */
    void updateLastMessage(String userId, String contactId, String lastMessage);
}
