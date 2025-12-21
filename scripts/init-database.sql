-- ===================================
-- 银龄守候项目 - 数据库初始化脚本
-- 创建所有数据库和基础配置
-- ===================================

-- 设置字符集
SET NAMES utf8mb4;

SET CHARACTER SET utf8mb4;

-- ===================================
-- 创建数据库
-- ===================================

-- 主数据库 (用户、角色、权限)
CREATE DATABASE IF NOT EXISTS `yinling_main` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 健康管理数据库
CREATE DATABASE IF NOT EXISTS `yinling_health` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 消息通讯数据库
CREATE DATABASE IF NOT EXISTS `yinling_message` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 安全监控数据库
CREATE DATABASE IF NOT EXISTS `yinling_security` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- AI服务数据库
CREATE DATABASE IF NOT EXISTS `yinling_ai` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Nacos配置数据库
CREATE DATABASE IF NOT EXISTS `nacos_config` DEFAULT CHARACTER
SET
    utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ===================================
-- 切换到主数据库
-- ===================================
USE `yinling_main`;

-- ===================================
-- 用户表
-- ===================================
CREATE TABLE IF NOT EXISTS `user` (
    `user_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `phone` VARCHAR(20) NOT NULL COMMENT '手机号',
    `password` VARCHAR(255) NOT NULL COMMENT '密码(加密)',
    `nickname` VARCHAR(50) NOT NULL COMMENT '昵称',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
    `birthday` DATE DEFAULT NULL COMMENT '生日',
    `user_type` TINYINT NOT NULL DEFAULT 1 COMMENT '用户类型: 1-老年用户, 2-子女用户',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted_at` DATETIME DEFAULT NULL COMMENT '删除时间',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_phone` (`phone`),
    KEY `idx_user_type` (`user_type`),
    KEY `idx_status` (`status`),
    KEY `idx_created_at` (`created_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表';

-- ===================================
-- 家庭成员关系表
-- ===================================
CREATE TABLE IF NOT EXISTS `family_member` (
    `relation_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关系ID',
    `elder_user_id` BIGINT NOT NULL COMMENT '老年用户ID',
    `child_user_id` BIGINT NOT NULL COMMENT '子女用户ID',
    `relation_type` VARCHAR(20) NOT NULL COMMENT '关系类型: 子女、儿媳、女婿等',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '备注昵称',
    `bind_status` TINYINT NOT NULL DEFAULT 1 COMMENT '绑定状态: 0-已解绑, 1-已绑定',
    `bind_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`relation_id`),
    UNIQUE KEY `uk_elder_child` (
        `elder_user_id`,
        `child_user_id`
    ),
    KEY `idx_elder_user_id` (`elder_user_id`),
    KEY `idx_child_user_id` (`child_user_id`),
    KEY `idx_bind_status` (`bind_status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '家庭成员关系表';

-- ===================================
-- 用户设置表
-- ===================================
CREATE TABLE IF NOT EXISTS `user_setting` (
    `setting_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '设置ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `voice_enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '语音交互开关',
    `voice_speed` INT NOT NULL DEFAULT 5 COMMENT '语音速度: 1-10',
    `voice_type` VARCHAR(20) NOT NULL DEFAULT 'xiaoyan' COMMENT '语音类型',
    `font_size` INT NOT NULL DEFAULT 18 COMMENT '字体大小',
    `push_enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '推送通知开关',
    `privacy_mode` TINYINT NOT NULL DEFAULT 0 COMMENT '隐私模式',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`setting_id`),
    UNIQUE KEY `uk_user_id` (`user_id`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户设置表';

-- ===================================
-- 切换到健康管理数据库
-- ===================================
USE `yinling_health`;

-- ===================================
-- 药品信息表
-- ===================================
CREATE TABLE IF NOT EXISTS `medication` (
    `medication_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '药品ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `medicine_name` VARCHAR(100) NOT NULL COMMENT '药品名称',
    `medicine_type` VARCHAR(50) DEFAULT NULL COMMENT '药品类型',
    `specification` VARCHAR(100) DEFAULT NULL COMMENT '规格',
    `manufacturer` VARCHAR(200) DEFAULT NULL COMMENT '生产厂家',
    `expiry_date` DATE DEFAULT NULL COMMENT '有效期',
    `usage_dosage` VARCHAR(200) DEFAULT NULL COMMENT '用法用量',
    `image_url` VARCHAR(255) DEFAULT NULL COMMENT '药品图片URL',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`medication_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_medicine_name` (`medicine_name`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '药品信息表';

-- ===================================
-- 用药提醒表
-- ===================================
CREATE TABLE IF NOT EXISTS `medication_reminder` (
    `reminder_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '提醒ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `medication_id` BIGINT NOT NULL COMMENT '药品ID',
    `reminder_time` TIME NOT NULL COMMENT '提醒时间',
    `dosage` VARCHAR(50) NOT NULL COMMENT '用药剂量',
    `frequency` VARCHAR(50) NOT NULL COMMENT '频率: 每天、隔天等',
    `reminder_type` TINYINT NOT NULL DEFAULT 1 COMMENT '提醒类型: 1-声音, 2-振动, 3-声音+振动',
    `enabled` TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用',
    `start_date` DATE NOT NULL COMMENT '开始日期',
    `end_date` DATE DEFAULT NULL COMMENT '结束日期',
    `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`reminder_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_medication_id` (`medication_id`),
    KEY `idx_reminder_time` (`reminder_time`),
    KEY `idx_enabled` (`enabled`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用药提醒表';

-- ===================================
-- 服药记录表
-- ===================================
CREATE TABLE IF NOT EXISTS `medication_record` (
    `record_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `reminder_id` BIGINT NOT NULL COMMENT '提醒ID',
    `medication_id` BIGINT NOT NULL COMMENT '药品ID',
    `scheduled_time` DATETIME NOT NULL COMMENT '计划时间',
    `actual_time` DATETIME DEFAULT NULL COMMENT '实际时间',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '状态: 0-未服用, 1-已服用, 2-已跳过, 3-忘记',
    `remark` VARCHAR(200) DEFAULT NULL COMMENT '备注',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`record_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_reminder_id` (`reminder_id`),
    KEY `idx_scheduled_time` (`scheduled_time`),
    KEY `idx_status` (`status`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '服药记录表';

-- ===================================
-- 切换到消息通讯数据库
-- ===================================
USE `yinling_message`;

-- ===================================
-- 消息会话表
-- ===================================
CREATE TABLE IF NOT EXISTS `msg_session` (
    `session_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '会话ID',
    `session_type` TINYINT NOT NULL COMMENT '会话类型: 1-单聊, 2-群聊',
    `elder_user_id` BIGINT NOT NULL COMMENT '老年用户ID',
    `child_user_id` BIGINT DEFAULT NULL COMMENT '子女用户ID (单聊时使用)',
    `group_name` VARCHAR(100) DEFAULT NULL COMMENT '群组名称 (群聊时使用)',
    `last_message` VARCHAR(500) DEFAULT NULL COMMENT '最后一条消息',
    `last_message_time` DATETIME DEFAULT NULL COMMENT '最后消息时间',
    `unread_count` INT NOT NULL DEFAULT 0 COMMENT '未读消息数',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-已删除, 1-正常',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`session_id`),
    KEY `idx_elder_user_id` (`elder_user_id`),
    KEY `idx_child_user_id` (`child_user_id`),
    KEY `idx_last_message_time` (`last_message_time`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '消息会话表';

-- ===================================
-- 切换到安全监控数据库
-- ===================================
USE `yinling_security`;

-- ===================================
-- 紧急联系人表
-- ===================================
CREATE TABLE IF NOT EXISTS `emergency_contact` (
    `contact_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '联系人ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `contact_name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    `contact_phone` VARCHAR(20) NOT NULL COMMENT '联系人电话',
    `relation` VARCHAR(20) NOT NULL COMMENT '关系',
    `priority` INT NOT NULL DEFAULT 1 COMMENT '优先级: 1-最高',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`contact_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_priority` (`priority`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '紧急联系人表';

-- ===================================
-- 切换到AI服务数据库
-- ===================================
USE `yinling_ai`;

-- ===================================
-- AI对话会话表
-- ===================================
CREATE TABLE IF NOT EXISTS `ai_session` (
    `session_id` VARCHAR(64) NOT NULL COMMENT '会话ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `session_type` TINYINT NOT NULL DEFAULT 1 COMMENT '会话类型: 1-闲聊, 2-咨询, 3-健康',
    `context` TEXT DEFAULT NULL COMMENT '上下文信息(JSON)',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-已结束, 1-进行中',
    `started_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `ended_at` DATETIME DEFAULT NULL COMMENT '结束时间',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`session_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`),
    KEY `idx_started_at` (`started_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI对话会话表';

-- ===================================
-- AI对话历史表
-- ===================================
CREATE TABLE IF NOT EXISTS `ai_message` (
    `message_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
    `session_id` VARCHAR(64) NOT NULL COMMENT '会话ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` VARCHAR(20) NOT NULL COMMENT '角色: user/assistant',
    `content` TEXT NOT NULL COMMENT '消息内容',
    `content_type` TINYINT NOT NULL DEFAULT 1 COMMENT '内容类型: 1-文本, 2-语音',
    `tokens` INT DEFAULT NULL COMMENT 'Token消耗',
    `cost` DECIMAL(10, 6) DEFAULT NULL COMMENT '费用(元)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`message_id`),
    KEY `idx_session_id` (`session_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_created_at` (`created_at`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = 'AI对话历史表';

-- ===================================
-- 插入测试数据
-- ===================================
USE `yinling_main`;

-- 插入测试用户
INSERT INTO
    `user` (
        `user_id`,
        `phone`,
        `password`,
        `nickname`,
        `gender`,
        `birthday`,
        `user_type`,
        `status`
    )
VALUES (
        1,
        '13800138001',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lFZ6NOrpMCPOqT6jy',
        '张爷爷',
        1,
        '1945-03-15',
        1,
        1
    ),
    (
        2,
        '13800138002',
        '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lFZ6NOrpMCPOqT6jy',
        '张明',
        1,
        '1985-06-20',
        2,
        1
    );
-- 密码都是: Yinling@2025

-- 插入家庭关系
INSERT INTO
    `family_member` (
        `elder_user_id`,
        `child_user_id`,
        `relation_type`,
        `nickname`
    )
VALUES (1, 2, '儿子', '小明');

-- 插入用户设置
INSERT INTO
    `user_setting` (
        `user_id`,
        `voice_enabled`,
        `voice_speed`,
        `voice_type`,
        `font_size`
    )
VALUES (1, 1, 5, 'xiaoyan', 20),
    (2, 1, 5, 'xiaoyan', 16);

-- ===================================
-- 完成
-- ===================================
SELECT '数据库初始化完成!' AS message;