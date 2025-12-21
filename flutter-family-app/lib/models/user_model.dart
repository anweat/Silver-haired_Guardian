import 'package:json_annotation/json_annotation.dart';

part 'user_model.g.dart';

@JsonSerializable()
class User {
  @JsonKey(name: 'userId')
  final String userId;

  @JsonKey(name: 'phone')
  final String phone;

  @JsonKey(name: 'nickname')
  final String nickname;

  @JsonKey(name: 'avatar')
  final String? avatar;

  @JsonKey(name: 'gender')
  final String? gender; // M/F

  @JsonKey(name: 'birthday')
  final String? birthday;

  @JsonKey(name: 'userType')
  final String userType; // ELDER/CHILD/DOCTOR

  @JsonKey(name: 'status')
  final int status; // 0: 正常, 1: 禁用

  @JsonKey(name: 'createdAt')
  final String createdAt;

  @JsonKey(name: 'updatedAt')
  final String? updatedAt;

  User({
    required this.userId,
    required this.phone,
    required this.nickname,
    this.avatar,
    this.gender,
    this.birthday,
    required this.userType,
    required this.status,
    required this.createdAt,
    this.updatedAt,
  });

  factory User.fromJson(Map<String, dynamic> json) => _$UserFromJson(json);

  Map<String, dynamic> toJson() => _$UserToJson(this);
}

@JsonSerializable()
class LoginResponse {
  @JsonKey(name: 'code')
  final int code;

  @JsonKey(name: 'message')
  final String message;

  @JsonKey(name: 'data')
  final LoginData? data;

  LoginResponse({
    required this.code,
    required this.message,
    this.data,
  });

  factory LoginResponse.fromJson(Map<String, dynamic> json) =>
      _$LoginResponseFromJson(json);

  Map<String, dynamic> toJson() => _$LoginResponseToJson(this);
}

@JsonSerializable()
class LoginData {
  @JsonKey(name: 'userId')
  final String userId;

  @JsonKey(name: 'phone')
  final String phone;

  @JsonKey(name: 'nickname')
  final String nickname;

  @JsonKey(name: 'avatar')
  final String? avatar;

  @JsonKey(name: 'userType')
  final String userType;

  @JsonKey(name: 'accessToken')
  final String accessToken;

  @JsonKey(name: 'refreshToken')
  final String refreshToken;

  @JsonKey(name: 'expiresIn')
  final int expiresIn; // 秒数

  LoginData({
    required this.userId,
    required this.phone,
    required this.nickname,
    this.avatar,
    required this.userType,
    required this.accessToken,
    required this.refreshToken,
    required this.expiresIn,
  });

  factory LoginData.fromJson(Map<String, dynamic> json) =>
      _$LoginDataFromJson(json);

  Map<String, dynamic> toJson() => _$LoginDataToJson(this);
}

@JsonSerializable()
class HealthData {
  @JsonKey(name: 'id')
  final String id;

  @JsonKey(name: 'userId')
  final String userId;

  @JsonKey(name: 'heartRate')
  final int heartRate; // 心率

  @JsonKey(name: 'bloodPressure')
  final String bloodPressure; // 血压 (如: "120/80")

  @JsonKey(name: 'temperature')
  final double temperature; // 温度

  @JsonKey(name: 'recordTime')
  final String recordTime;

  HealthData({
    required this.id,
    required this.userId,
    required this.heartRate,
    required this.bloodPressure,
    required this.temperature,
    required this.recordTime,
  });

  factory HealthData.fromJson(Map<String, dynamic> json) =>
      _$HealthDataFromJson(json);

  Map<String, dynamic> toJson() => _$HealthDataToJson(this);
}

@JsonSerializable()
class MedicationReminder {
  @JsonKey(name: 'id')
  final String id;

  @JsonKey(name: 'userId')
  final String userId;

  @JsonKey(name: 'medicationName')
  final String medicationName; // 药物名称

  @JsonKey(name: 'dosage')
  final String dosage; // 剂量

  @JsonKey(name: 'frequency')
  final String frequency; // 频率 (每天2次等)

  @JsonKey(name: 'nextReminderTime')
  final String nextReminderTime;

  @JsonKey(name: 'lastTakenTime')
  final String? lastTakenTime;

  @JsonKey(name: 'status')
  final int status; // 0: 未服用, 1: 已服用, 2: 已过期

  MedicationReminder({
    required this.id,
    required this.userId,
    required this.medicationName,
    required this.dosage,
    required this.frequency,
    required this.nextReminderTime,
    this.lastTakenTime,
    required this.status,
  });

  factory MedicationReminder.fromJson(Map<String, dynamic> json) =>
      _$MedicationReminderFromJson(json);

  Map<String, dynamic> toJson() => _$MedicationReminderToJson(this);
}

@JsonSerializable()
class Message {
  @JsonKey(name: 'id')
  final String id;

  @JsonKey(name: 'senderId')
  final String senderId;

  @JsonKey(name: 'senderName')
  final String senderName;

  @JsonKey(name: 'receiverId')
  final String receiverId;

  @JsonKey(name: 'content')
  final String content;

  @JsonKey(name: 'messageType')
  final String messageType; // text/image/audio/video

  @JsonKey(name: 'timestamp')
  final String timestamp;

  @JsonKey(name: 'isRead')
  final bool isRead;

  Message({
    required this.id,
    required this.senderId,
    required this.senderName,
    required this.receiverId,
    required this.content,
    required this.messageType,
    required this.timestamp,
    required this.isRead,
  });

  factory Message.fromJson(Map<String, dynamic> json) =>
      _$MessageFromJson(json);

  Map<String, dynamic> toJson() => _$MessageToJson(this);
}

@JsonSerializable()
class Alert {
  @JsonKey(name: 'id')
  final String id;

  @JsonKey(name: 'userId')
  final String userId;

  @JsonKey(name: 'alertType')
  final String alertType; // fall_detection/health_anomaly/medication_overdue

  @JsonKey(name: 'description')
  final String description;

  @JsonKey(name: 'severity')
  final String severity; // high/medium/low

  @JsonKey(name: 'timestamp')
  final String timestamp;

  @JsonKey(name: 'isAcknowledged')
  final bool isAcknowledged;

  Alert({
    required this.id,
    required this.userId,
    required this.alertType,
    required this.description,
    required this.severity,
    required this.timestamp,
    required this.isAcknowledged,
  });

  factory Alert.fromJson(Map<String, dynamic> json) => _$AlertFromJson(json);

  Map<String, dynamic> toJson() => _$AlertToJson(this);
}
