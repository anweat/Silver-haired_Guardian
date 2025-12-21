package com.example.yinling.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.yinling.health.dto.HealthDataDTO;
import com.example.yinling.health.dto.HealthDataResponseDTO;
import com.example.yinling.health.entity.HealthData;
import java.util.List;

public interface IHealthDataService extends IService<HealthData> {
    /**
     * 上报健康数据
     */
    HealthDataResponseDTO reportHealthData(String userId, HealthDataDTO dto);

    /**
     * 获取用户的健康数据列表
     */
    List<HealthDataResponseDTO> getHealthDataList(String userId, int page, int pageSize);

    /**
     * 获取用户最新的健康数据
     */
    HealthDataResponseDTO getLatestHealthData(String userId);

    /**
     * 获取用户指定日期的健康数据
     */
    List<HealthDataResponseDTO> getHealthDataByDate(String userId, String date);

    /**
     * 获取用户指定时间范围的健康数据
     */
    List<HealthDataResponseDTO> getHealthDataByDateRange(String userId, long startTime, long endTime);

    /**
     * 删除健康数据
     */
    boolean deleteHealthData(String userId, String healthDataId);
}
