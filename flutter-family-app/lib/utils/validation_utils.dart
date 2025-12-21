class ValidationUtils {
  /// 验证手机号
  static bool isValidPhone(String phone) {
    final regex = RegExp(r'^1[3-9]\d{9}$');
    return regex.hasMatch(phone);
  }

  /// 验证邮箱
  static bool isValidEmail(String email) {
    final regex = RegExp(
      r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$',
    );
    return regex.hasMatch(email);
  }

  /// 验证密码（至少6位）
  static bool isValidPassword(String password) {
    return password.length >= 6;
  }

  /// 验证身份证号
  static bool isValidIdCard(String idCard) {
    // 简单的身份证验证（18位数字或17位数字+X）
    final regex = RegExp(r'^[0-9]{17}[0-9X]$');
    return regex.hasMatch(idCard.toUpperCase());
  }

  /// 验证URL
  static bool isValidUrl(String url) {
    final regex = RegExp(
      r'^https?://(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)$',
    );
    return regex.hasMatch(url);
  }

  /// 验证汉字
  static bool isValidChinese(String text) {
    final regex = RegExp(r'^[\u4e00-\u9fa5]+$');
    return regex.hasMatch(text);
  }

  /// 检查字符串是否为空或仅包含空格
  static bool isEmpty(String? text) {
    return text == null || text.trim().isEmpty;
  }

  /// 检查字符串长度
  static bool isLengthInRange(String text, int min, int max) {
    final length = text.length;
    return length >= min && length <= max;
  }

  /// 验证用户名（3-20个字母、数字或下划线）
  static bool isValidUsername(String username) {
    final regex = RegExp(r'^[a-zA-Z0-9_]{3,20}$');
    return regex.hasMatch(username);
  }

  /// 验证昵称（2-20个字符，可包含汉字、字母、数字、下划线）
  static bool isValidNickname(String nickname) {
    final regex = RegExp(r'^[\u4e00-\u9fa5a-zA-Z0-9_]{2,20}$');
    return regex.hasMatch(nickname);
  }
}
