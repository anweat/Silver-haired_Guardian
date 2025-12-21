package com.example.yinling.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.yinling.health.entity.HealthData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HealthDataMapper extends BaseMapper<HealthData> {
    /**
     * 根据用户ID查询健康数据
     */
    @Select("SELECT * FROM health_data WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY record_time DESC LIMIT #{pageSize} OFFSET #{offset}")
    List<HealthData> selectByUserId(String userId, int pageSize, int offset);

    /**
     * 根据用户ID和日期查询健康数据
     */
    @Select("SELECT * FROM health_data WHERE user_id = #{userId} AND DATE(record_time) = #{date} AND deleted_at IS NULL")
    List<HealthData> selectByUserIdAndDate(String userId, String date);

    /**
     * 查询用户在指定时间范围内的健康数据
     */
    @Select("SELECT * FROM health_data WHERE user_id = #{userId} AND record_time BETWEEN #{startTime} AND #{endTime} AND deleted_at IS NULL ORDER BY record_time DESC")
    List<HealthData> selectByUserIdAndDateRange(String userId, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * 获取用户最新的健康数据
     */
    @Select("SELECT * FROM health_data WHERE user_id = #{userId} AND deleted_at IS NULL ORDER BY record_time DESC LIMIT 1")
    HealthData selectLatestByUserId(String userId);

    /**
     * 统计用户的健康数据数量
     */
    @Select("SELECT COUNT(*) FROM health_data WHERE user_id = #{userId} AND deleted_at IS NULL")
    long countByUserId(String userId);
}
