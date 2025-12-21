package com.example.yinling.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yinling.health.entity.MedicationReminder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MedicationReminderMapper extends BaseMapper<MedicationReminder> {
    /**
     * 根据用户ID查询所有用药提醒
     */
    @Select("SELECT * FROM medication_reminder WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY next_reminder_time ASC")
    List<MedicationReminder> selectByUserId(String userId);

    /**
     * 查询待提醒的用药（status = 0）
     */
    @Select("SELECT * FROM medication_reminder WHERE user_id = #{userId} AND status = 0 AND next_reminder_time <= NOW() AND deleted_at IS NULL ORDER BY next_reminder_time ASC")
    List<MedicationReminder> selectPendingReminders(String userId);

    /**
     * 根据ID查询用药提醒
     */
    @Select("SELECT * FROM medication_reminder WHERE id = #{id} AND deleted_at IS NULL")
    MedicationReminder selectById(String id);

    /**
     * 标记用药已完成
     */
    @Update("UPDATE medication_reminder SET status = 1, last_taken_time = NOW(), updated_at = NOW() WHERE id = #{id}")
    int markAsTaken(String id);

    /**
     * 标记用药已过期
     */
    @Update("UPDATE medication_reminder SET status = 2, updated_at = NOW() WHERE id = #{id}")
    int markAsExpired(String id);

    /**
     * 查询未来24小时需要提醒的用药
     */
    @Select("SELECT * FROM medication_reminder WHERE user_id = #{userId} AND status IN (0, 1) AND next_reminder_time BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 24 HOUR) AND deleted_at IS NULL ORDER BY next_reminder_time ASC")
    List<MedicationReminder> selectUpcomingReminders(String userId);

    /**
     * 统计用户当天应该服用的药物次数
     */
    @Select("SELECT COUNT(*) FROM medication_reminder WHERE user_id = #{userId} AND DATE(next_reminder_time) = CURDATE() AND status IN (0, 1) AND deleted_at IS NULL")
    long countTodayReminders(String userId);

    /**
     * 统计用户当天已服用的药物次数
     */
    @Select("SELECT COUNT(*) FROM medication_reminder WHERE user_id = #{userId} AND DATE(last_taken_time) = CURDATE() AND status = 1 AND deleted_at IS NULL")
    long countTakenToday(String userId);
}
