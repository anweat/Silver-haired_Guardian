import 'package:shared_preferences/shared_preferences.dart';

class StorageService {
  static const String _accessTokenKey = 'access_token';
  static const String _refreshTokenKey = 'refresh_token';
  static const String _userIdKey = 'user_id';
  static const String _phoneKey = 'phone';
  static const String _nicknameKey = 'nickname';

  static late SharedPreferences _prefs;

  static Future<void> init() async {
    _prefs = await SharedPreferences.getInstance();
  }

  /// 保存访问令牌
  static Future<bool> saveAccessToken(String token) async {
    return await _prefs.setString(_accessTokenKey, token);
  }

  /// 获取访问令牌
  static String? getAccessToken() {
    return _prefs.getString(_accessTokenKey);
  }

  /// 保存刷新令牌
  static Future<bool> saveRefreshToken(String token) async {
    return await _prefs.setString(_refreshTokenKey, token);
  }

  /// 获取刷新令牌
  static String? getRefreshToken() {
    return _prefs.getString(_refreshTokenKey);
  }

  /// 保存用户ID
  static Future<bool> saveUserId(String userId) async {
    return await _prefs.setString(_userIdKey, userId);
  }

  /// 获取用户ID
  static String? getUserId() {
    return _prefs.getString(_userIdKey);
  }

  /// 保存用户电话
  static Future<bool> savePhone(String phone) async {
    return await _prefs.setString(_phoneKey, phone);
  }

  /// 获取用户电话
  static String? getPhone() {
    return _prefs.getString(_phoneKey);
  }

  /// 保存用户昵称
  static Future<bool> saveNickname(String nickname) async {
    return await _prefs.setString(_nicknameKey, nickname);
  }

  /// 获取用户昵称
  static String? getNickname() {
    return _prefs.getString(_nicknameKey);
  }

  /// 清除所有用户数据
  static Future<bool> clearUserData() async {
    await _prefs.remove(_accessTokenKey);
    await _prefs.remove(_refreshTokenKey);
    await _prefs.remove(_userIdKey);
    await _prefs.remove(_phoneKey);
    await _prefs.remove(_nicknameKey);
    return true;
  }
}
