/// 应用配置常量
class AppConfig {
  // API配置
  static const String apiBaseUrl = 'http://localhost:8080/api';
  static const int apiConnectTimeout = 30000; // 毫秒
  static const int apiReceiveTimeout = 30000;

  // 应用信息
  static const String appName = '银龄守候';
  static const String appVersion = '1.0.0';
  static const String appPackageName = 'com.yinling.family';

  // 功能开关
  static const bool enableLogging = true;
  static const bool enableAnalytics = true;
  static const bool enableCrashReporting = true;

  // 缓存配置
  static const int cacheMaxSize = 100 * 1024 * 1024; // 100MB
  static const int cacheDuration = 24 * 60 * 60; // 24小时，秒数

  // 分页配置
  static const int defaultPageSize = 20;
  static const int maxPageSize = 100;

  // 请求配置
  static const int requestTimeoutSeconds = 30;
  static const int retryCount = 3;
  static const int retryDelayMilliseconds = 1000;

  // 消息配置
  static const int messageQueryLimit = 50;
  static const int messagePageSize = 20;

  // 健康数据配置
  static const int healthDataPageSize = 20;
  static const int maxMedicationReminders = 20; // 单个用户最多设置20个提醒

  // 媒体配置
  static const int maxImageSize = 5 * 1024 * 1024; // 5MB
  static const int maxVideoSize = 100 * 1024 * 1024; // 100MB
  static const int maxAudioSize = 50 * 1024 * 1024; // 50MB

  // 定位配置
  static const int locationUpdateInterval = 5000; // 毫秒
  static const double locationAccuracy = 50; // 米

  // 用户界面配置
  static const bool isProduction = false;
  static const String environment = isProduction ? 'production' : 'development';
}

/// 路由配置
class RouteConfig {
  static const String login = '/login';
  static const String register = '/register';
  static const String home = '/home';
  static const String splash = '/splash';
}

/// 用户类型
class UserType {
  static const String elder = 'ELDER'; // 老人
  static const String child = 'CHILD'; // 子女
  static const String doctor = 'DOCTOR'; // 医生
  static const String caregiver = 'CAREGIVER'; // 护理人员
}

/// 消息类型
class MessageType {
  static const String text = 'text'; // 文本
  static const String image = 'image'; // 图片
  static const String audio = 'audio'; // 音频
  static const String video = 'video'; // 视频
  static const String location = 'location'; // 位置
  static const String file = 'file'; // 文件
}

/// 存储键
class StorageKey {
  static const String accessToken = 'access_token';
  static const String refreshToken = 'refresh_token';
  static const String userId = 'user_id';
  static const String userPhone = 'user_phone';
  static const String userNickname = 'user_nickname';
  static const String userType = 'user_type';
  static const String lastLoginTime = 'last_login_time';
  static const String darkMode = 'dark_mode';
  static const String language = 'language';
}
