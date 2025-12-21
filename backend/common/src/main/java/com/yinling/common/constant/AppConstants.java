package com.yinling.common.constant;

/**
 * 应用常量
 */
public class AppConstants {

    /**
     * JWT相关常量
     */
    public static class JWT {
        public static final String HEADER = "Authorization";
        public static final String PREFIX = "Bearer ";
        public static final String CLAIM_USER_ID = "userId";
        public static final String CLAIM_USER_TYPE = "userType";
        public static final String CLAIM_PHONE = "phone";
    }

    /**
     * Redis键前缀
     */
    public static class Redis {
        public static final String USER_INFO_PREFIX = "user:info:";
        public static final String USER_TOKEN_PREFIX = "user:token:";
        public static final String VERIFICATION_CODE_PREFIX = "verify:code:";
        public static final String HEALTH_DATA_PREFIX = "health:data:";
        public static final String AI_SESSION_PREFIX = "ai:session:";
        public static final String RATE_LIMIT_PREFIX = "rate:limit:";
    }

    /**
     * 用户类型
     */
    public static class UserType {
        public static final Integer ELDER = 1;      // 老年用户
        public static final Integer CHILD = 2;      // 子女用户
    }

    /**
     * 用户状态
     */
    public static class UserStatus {
        public static final Integer ENABLED = 1;    // 启用
        public static final Integer DISABLED = 0;   // 禁用
    }

    /**
     * 消息类型
     */
    public static class MessageType {
        public static final Integer TEXT = 1;       // 文本
        public static final Integer VOICE = 2;      // 语音
        public static final Integer IMAGE = 3;      // 图片
        public static final Integer VIDEO = 4;      // 视频
    }

    /**
     * 消息状态
     */
    public static class MessageStatus {
        public static final Integer UNREAD = 0;     // 未读
        public static final Integer READ = 1;       // 已读
        public static final Integer DELETED = 2;    // 已删除
    }

    /**
     * 缓存过期时间（秒）
     */
    public static class CacheExpire {
        public static final Long USER_INFO = 3600L;           // 用户信息：1小时
        public static final Long VERIFICATION_CODE = 300L;    // 验证码：5分钟
        public static final Long TOKEN = 86400L;              // Token：1天
        public static final Long HOT_DATA = 300L;             // 热数据：5分钟
    }

    /**
     * 分页参数
     */
    public static class Page {
        public static final Integer DEFAULT_PAGE_NUM = 1;
        public static final Integer DEFAULT_PAGE_SIZE = 10;
        public static final Integer MAX_PAGE_SIZE = 100;
    }
}
