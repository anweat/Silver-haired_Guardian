enum LogLevel {
  debug,
  info,
  warning,
  error,
  fatal,
}

class Logger {
  static const String _tag = '[YinLing]';
  static LogLevel _logLevel = LogLevel.debug;

  static void setLogLevel(LogLevel level) {
    _logLevel = level;
  }

  static void debug(String message, {String? tag}) {
    _log(LogLevel.debug, message, tag);
  }

  static void info(String message, {String? tag}) {
    _log(LogLevel.info, message, tag);
  }

  static void warning(String message, {String? tag}) {
    _log(LogLevel.warning, message, tag);
  }

  static void error(String message, {String? tag, Object? exception, StackTrace? stackTrace}) {
    _log(LogLevel.error, message, tag);
    if (exception != null) {
      print('$_tag Exception: $exception');
    }
    if (stackTrace != null) {
      print('$_tag StackTrace: $stackTrace');
    }
  }

  static void fatal(String message, {String? tag}) {
    _log(LogLevel.fatal, message, tag);
  }

  static void _log(LogLevel level, String message, String? tag) {
    if (level.index < _logLevel.index) {
      return;
    }

    final finalTag = tag ?? _tag;
    final timestamp = DateTime.now().toIso8601String();
    final levelString = level.toString().split('.').last.toUpperCase();

    print('[$timestamp] [$levelString] $finalTag $message');
  }
}
