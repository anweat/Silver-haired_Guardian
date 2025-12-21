import 'package:dio/dio.dart';

class ApiService {
  static const String baseUrl = 'http://localhost:8080/api';
  late Dio _dio;

  ApiService() {
    _dio = Dio(
      BaseOptions(
        baseUrl: baseUrl,
        connectTimeout: const Duration(seconds: 30),
        receiveTimeout: const Duration(seconds: 30),
        contentType: 'application/json',
      ),
    );

    // 添加请求拦截器
    _dio.interceptors.add(
      InterceptorsWrapper(
        onRequest: (options, handler) {
          // 添加认证令牌到请求头
          final token = _getToken();
          if (token != null) {
            options.headers['Authorization'] = 'Bearer $token';
          }
          return handler.next(options);
        },
        onError: (DioException error, handler) {
          // 处理401错误 - 令牌过期
          if (error.response?.statusCode == 401) {
            // TODO: 刷新令牌或重新登录
          }
          return handler.next(error);
        },
      ),
    );
  }

  String? _getToken() {
    // TODO: 从本地存储获取令牌
    return null;
  }

  /// 用户登录
  Future<Map<String, dynamic>> login({
    required String phone,
    required String password,
  }) async {
    try {
      final response = await _dio.post(
        '/auth/login',
        data: {
          'phone': phone,
          'password': password,
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 用户注册
  Future<Map<String, dynamic>> register({
    required String phone,
    required String nickname,
    required String password,
  }) async {
    try {
      final response = await _dio.post(
        '/auth/register',
        data: {
          'phone': phone,
          'nickname': nickname,
          'password': password,
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 获取用户信息
  Future<Map<String, dynamic>> getUser({required String userId}) async {
    try {
      final response = await _dio.get('/auth/user/$userId');
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 获取健康数据列表
  Future<Map<String, dynamic>> getHealthDataList({
    int page = 1,
    int pageSize = 20,
  }) async {
    try {
      final response = await _dio.get(
        '/health/list',
        queryParameters: {
          'page': page,
          'pageSize': pageSize,
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 获取消息列表
  Future<Map<String, dynamic>> getMessageList({
    int page = 1,
    int pageSize = 20,
  }) async {
    try {
      final response = await _dio.get(
        '/message/list',
        queryParameters: {
          'page': page,
          'pageSize': pageSize,
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 上报健康数据
  Future<Map<String, dynamic>> reportHealthData({
    required String heartRate,
    required String bloodPressure,
    required String temperature,
  }) async {
    try {
      final response = await _dio.post(
        '/health/report',
        data: {
          'heartRate': heartRate,
          'bloodPressure': bloodPressure,
          'temperature': temperature,
          'timestamp': DateTime.now().toIso8601String(),
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 获取用药提醒列表
  Future<Map<String, dynamic>> getMedicationReminders() async {
    try {
      final response = await _dio.get('/health/medication-reminders');
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 标记用药提醒已完成
  Future<Map<String, dynamic>> markMedicationAsTaken({
    required String reminderId,
  }) async {
    try {
      final response = await _dio.post(
        '/health/medication-reminders/$reminderId/mark-taken',
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 获取实时告警
  Future<Map<String, dynamic>> getRealTimeAlerts() async {
    try {
      final response = await _dio.get('/security/alerts');
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  /// 发送消息
  Future<Map<String, dynamic>> sendMessage({
    required String receiverId,
    required String content,
    String messageType = 'text',
  }) async {
    try {
      final response = await _dio.post(
        '/message/send',
        data: {
          'receiverId': receiverId,
          'content': content,
          'messageType': messageType,
          'timestamp': DateTime.now().toIso8601String(),
        },
      );
      return response.data;
    } on DioException catch (e) {
      throw _handleError(e);
    }
  }

  Exception _handleError(DioException error) {
    if (error.response != null) {
      final message = error.response?.data['message'] ?? '请求失败';
      return Exception(message);
    } else if (error.type == DioExceptionType.connectionTimeout) {
      return Exception('连接超时');
    } else if (error.type == DioExceptionType.receiveTimeout) {
      return Exception('接收超时');
    } else {
      return Exception('网络错误: ${error.message}');
    }
  }
}
