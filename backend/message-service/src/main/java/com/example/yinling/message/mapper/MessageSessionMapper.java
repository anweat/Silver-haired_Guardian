package com.example.yinling.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yinling.message.entity.MessageSession;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface MessageSessionMapper extends BaseMapper<MessageSession> {
    /**
     * 获取用户的消息会话列表
     */
    @Select("SELECT * FROM message_session WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY is_pinned DESC, last_message_time DESC")
    List<MessageSession> selectByUserId(String userId);

    /**
     * 获取用户与某个联系人的会话
     */
    @Select("SELECT * FROM message_session WHERE user_id = #{userId} AND contact_id = #{contactId} AND deleted_at IS NULL")
    MessageSession selectByUserAndContact(String userId, String contactId);

    /**
     * 获取用户有未读消息的会话
     */
    @Select("SELECT * FROM message_session WHERE user_id = #{userId} AND unread_count > 0 AND deleted_at IS NULL ORDER BY is_pinned DESC, last_message_time DESC")
    List<MessageSession> selectSessionsWithUnread(String userId);

    /**
     * 更新会话的最后一条消息
     */
    @Update("UPDATE message_session SET last_message = #{lastMessage}, last_message_time = NOW(), updated_at = NOW() WHERE user_id = #{userId} AND contact_id = #{contactId}")
    int updateLastMessage(String userId, String contactId, String lastMessage);

    /**
     * 更新会话的未读消息数
     */
    @Update("UPDATE message_session SET unread_count = unread_count + 1 WHERE user_id = #{userId} AND contact_id = #{contactId}")
    int incrementUnreadCount(String userId, String contactId);

    /**
     * 清空会话的未读消息数
     */
    @Update("UPDATE message_session SET unread_count = 0 WHERE user_id = #{userId} AND contact_id = #{contactId}")
    int clearUnreadCount(String userId, String contactId);

    /**
     * 置顶会话
     */
    @Update("UPDATE message_session SET is_pinned = 1 WHERE id = #{id}")
    int pinSession(String id);

    /**
     * 取消置顶
     */
    @Update("UPDATE message_session SET is_pinned = 0 WHERE id = #{id}")
    int unpinSession(String id);

    /**
     * 获取用户的总未读消息数
     */
    @Select("SELECT COALESCE(SUM(unread_count), 0) FROM message_session WHERE user_id = #{userId} AND deleted_at IS NULL")
    long getTotalUnreadCount(String userId);
}
