package com.example.yinling.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yinling.health.dto.MedicationReminderDTO;
import com.example.yinling.health.dto.MedicationReminderResponseDTO;
import com.example.yinling.health.entity.MedicationReminder;
import java.util.List;

public interface IMedicationReminderService extends IService<MedicationReminder> {
    /**
     * 创建用药提醒
     */
    MedicationReminderResponseDTO createReminder(String userId, MedicationReminderDTO dto);

    /**
     * 获取用户的所有用药提醒
     */
    List<MedicationReminderResponseDTO> getUserReminders(String userId);

    /**
     * 获取用户待提醒的用药
     */
    List<MedicationReminderResponseDTO> getPendingReminders(String userId);

    /**
     * 获取用户未来24小时需要提醒的用药
     */
    List<MedicationReminderResponseDTO> getUpcomingReminders(String userId);

    /**
     * 标记用药已完成
     */
    boolean markAsTaken(String userId, String reminderId);

    /**
     * 更新用药提醒
     */
    MedicationReminderResponseDTO updateReminder(String userId, String reminderId, MedicationReminderDTO dto);

    /**
     * 删除用药提醒
     */
    boolean deleteReminder(String userId, String reminderId);

    /**
     * 获取用户当天的用药统计
     */
    MedicationStatDTO getTodayStatistics(String userId);
}
