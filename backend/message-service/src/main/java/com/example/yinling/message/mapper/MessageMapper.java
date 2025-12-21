package com.example.yinling.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yinling.message.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    /**
     * 获取两个用户之间的消息
     */
    @Select("SELECT * FROM message WHERE ((sender_id = #{userId} AND receiver_id = #{contactId}) OR (sender_id = #{contactId} AND receiver_id = #{userId})) AND deleted_at IS NULL ORDER BY timestamp DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<Message> selectMessages(String userId, String contactId, int pageSize, int offset);

    /**
     * 获取用户收到的消息
     */
    @Select("SELECT * FROM message WHERE receiver_id = #{userId} AND deleted_at IS NULL ORDER BY timestamp DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<Message> selectReceivedMessages(String userId, int pageSize, int offset);

    /**
     * 获取用户未读消息数
     */
    @Select("SELECT COUNT(*) FROM message WHERE receiver_id = #{userId} AND is_read = 0 AND deleted_at IS NULL")
    long countUnreadMessages(String userId);

    /**
     * 获取用户与某个联系人的未读消息数
     */
    @Select("SELECT COUNT(*) FROM message WHERE receiver_id = #{userId} AND sender_id = #{senderId} AND is_read = 0 AND deleted_at IS NULL")
    long countUnreadMessagesFrom(String userId, String senderId);

    /**
     * 标记消息为已读
     */
    @Update("UPDATE message SET is_read = 1, read_time = NOW() WHERE id = #{id}")
    int markAsRead(String id);

    /**
     * 标记所有来自某个发送者的消息为已读
     */
    @Update("UPDATE message SET is_read = 1, read_time = NOW() WHERE receiver_id = #{userId} AND sender_id = #{senderId} AND is_read = 0")
    int markAllAsReadFrom(String userId, String senderId);

    /**
     * 获取用户的最后一条消息
     */
    @Select("SELECT * FROM message WHERE ((sender_id = #{userId} AND receiver_id = #{contactId}) OR (sender_id = #{contactId} AND receiver_id = #{userId})) AND deleted_at IS NULL ORDER BY timestamp DESC LIMIT 1")
    Message selectLastMessage(String userId, String contactId);

    /**
     * 删除消息
     */
    @Update("UPDATE message SET deleted_at = NOW() WHERE id = #{id}")
    int deleteMessage(String id);
}
