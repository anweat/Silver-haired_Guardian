class DateTimeUtils {
  /// 格式化日期时间
  static String formatDateTime(DateTime dateTime, {String format = 'yyyy-MM-dd HH:mm:ss'}) {
    return _formatDate(dateTime, format);
  }

  /// 格式化日期
  static String formatDate(DateTime dateTime, {String format = 'yyyy-MM-dd'}) {
    return _formatDate(dateTime, format);
  }

  /// 格式化时间
  static String formatTime(DateTime dateTime, {String format = 'HH:mm:ss'}) {
    return _formatDate(dateTime, format);
  }

  /// 相对时间（如：刚刚、5分钟前、2小时前）
  static String relativeTime(DateTime dateTime) {
    final now = DateTime.now();
    final difference = now.difference(dateTime);

    if (difference.inSeconds < 60) {
      return '刚刚';
    } else if (difference.inMinutes < 60) {
      return '${difference.inMinutes}分钟前';
    } else if (difference.inHours < 24) {
      return '${difference.inHours}小时前';
    } else if (difference.inDays == 1) {
      return '昨天';
    } else if (difference.inDays < 7) {
      return '${difference.inDays}天前';
    } else {
      return formatDate(dateTime);
    }
  }

  /// 检查是否是今天
  static bool isToday(DateTime dateTime) {
    final now = DateTime.now();
    return dateTime.year == now.year &&
        dateTime.month == now.month &&
        dateTime.day == now.day;
  }

  /// 检查是否是昨天
  static bool isYesterday(DateTime dateTime) {
    final yesterday = DateTime.now().subtract(const Duration(days: 1));
    return dateTime.year == yesterday.year &&
        dateTime.month == yesterday.month &&
        dateTime.day == yesterday.day;
  }

  /// 获取今天的开始时间
  static DateTime getTodayStart() {
    final now = DateTime.now();
    return DateTime(now.year, now.month, now.day);
  }

  /// 获取今天的结束时间
  static DateTime getTodayEnd() {
    final now = DateTime.now();
    return DateTime(now.year, now.month, now.day, 23, 59, 59);
  }

  static String _formatDate(DateTime dateTime, String format) {
    String result = format;

    // 年
    result = result.replaceAll('yyyy', dateTime.year.toString());
    result = result.replaceAll('yy', dateTime.year.toString().substring(2));

    // 月
    result = result.replaceAll('MM', _padZero(dateTime.month));
    result = result.replaceAll('M', dateTime.month.toString());

    // 日
    result = result.replaceAll('dd', _padZero(dateTime.day));
    result = result.replaceAll('d', dateTime.day.toString());

    // 小时
    result = result.replaceAll('HH', _padZero(dateTime.hour));
    result = result.replaceAll('H', dateTime.hour.toString());
    result = result.replaceAll('hh', _padZero(dateTime.hour % 12));
    result = result.replaceAll('h', (dateTime.hour % 12).toString());

    // 分钟
    result = result.replaceAll('mm', _padZero(dateTime.minute));
    result = result.replaceAll('m', dateTime.minute.toString());

    // 秒
    result = result.replaceAll('ss', _padZero(dateTime.second));
    result = result.replaceAll('s', dateTime.second.toString());

    return result;
  }

  static String _padZero(int value) {
    return value.toString().padLeft(2, '0');
  }
}
