package com.example.yinling.health.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationStatDTO {
    /**
     * 今天应该服用的药物次数
     */
    private long totalReminders;

    /**
     * 今天已服用的次数
     */
    private long takenCount;

    /**
     * 未服用次数
     */
    private long pendingCount;

    /**
     * 完成率（百分比）
     */
    private double completionRate;

    public MedicationStatDTO(long totalReminders, long takenCount) {
        this.totalReminders = totalReminders;
        this.takenCount = takenCount;
        this.pendingCount = totalReminders - takenCount;
        this.completionRate = totalReminders > 0 ? (takenCount * 100.0) / totalReminders : 0;
    }
}
