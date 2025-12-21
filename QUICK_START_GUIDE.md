# 快速参考指南 - 骨架代码使用说明

## 📋 目录

1. [项目结构](#项目结构)
2. [快速启动](#快速启动)
3. [API 端点](#api-端点)
4. [常见任务](#常见任务)
5. [开发规范](#开发规范)
6. [常见问题](#常见问题)

---

## 项目结构

```
SoftwareInvolution11/
├── backend/                          # Java Spring Boot 后端
│   ├── common/                       # 通用模块
│   ├── gateway-service/              # API 网关 (端口 8080)
│   ├── user-service/                 # 用户服务 (端口 8081) ✅ 完成
│   ├── health-service/               # 健康服务 (端口 8082)
│   ├── message-service/              # 消息服务 (端口 8083)
│   ├── security-service/             # 安全服务 (端口 8084)
│   ├── ai-service/                   # AI 服务 (端口 8085)
│   └── scheduler-service/            # 调度服务 (端口 8086)
│
├── android-elderly-app/              # Android 应用
│   └── app/src/main/java/
│       └── com/example/yinling/
│           ├── ui/                   # UI 层 (Compose)
│           ├── viewmodel/            # ViewModel 层
│           └── data/                 # 数据层
│
├── flutter-family-app/               # Flutter 应用
│   ├── lib/
│   │   ├── screens/                  # 屏幕
│   │   ├── providers/                # Riverpod 提供者
│   │   ├── services/                 # 服务层
│   │   ├── models/                   # 数据模型
│   │   ├── utils/                    # 工具类
│   │   └── config/                   # 配置文件
│   └── pubspec.yaml                  # 依赖配置
│
└── docs/                             # 文档
    ├── ARCHITECTURE.md               # 架构设计
    ├── API_SPECIFICATION.md          # API 规范
    └── DATABASE_DESIGN.md            # 数据库设计
```

---

## 快速启动

### 1. 后端启动 (Windows PowerShell)

```powershell
# 进入后端目录
cd backend

# 编译所有模块
mvn clean install -DskipTests

# 启动网关 (必须先启)
Start-Process "java" -ArgumentList "-jar", "gateway-service\target\gateway-service-1.0.0.jar" -NoNewWindow

# 启动用户服务
Start-Process "java" -ArgumentList "-jar", "user-service\target\user-service-1.0.0.jar" -NoNewWindow

# 启动其他服务 (可选)
Start-Process "java" -ArgumentList "-jar", "health-service\target\health-service-1.0.0.jar" -NoNewWindow
```

### 2. Android 启动

```bash
cd android-elderly-app

# 获取依赖
flutter pub get

# 构建运行
flutter run

# 或者用 Android Studio 打开 build.gradle
```

### 3. Flutter 启动

```bash
cd flutter-family-app

# 获取依赖
flutter pub get

# 运行
flutter run

# 或指定设备
flutter run -d <device_id>
```

### 4. 验证启动成功

```bash
# 测试 API
curl http://localhost:8080/api/auth/login \
  -X POST \
  -H "Content-Type: application/json" \
  -d '{"phone":"13800138000","password":"123456"}'

# 预期响应
# {
#   "code": -1,
#   "message": "用户不存在",
#   "data": null
# }
```

---

## API 端点

### 用户认证 (User Service)

| 方法 | 端点 | 说明 |
|------|------|------|
| POST | `/api/auth/register` | 用户注册 |
| POST | `/api/auth/login` | 用户登录 |
| GET | `/api/auth/user/{userId}` | 获取用户信息 |
| POST | `/api/auth/verify-token` | 验证令牌 |

**登录请求例**:
```json
{
  "phone": "13800138000",
  "password": "password123"
}
```

**登录响应例**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "userId": "user123",
    "phone": "13800138000",
    "nickname": "张三",
    "avatar": "https://...",
    "userType": "ELDER",
    "accessToken": "eyJhbGc...",
    "refreshToken": "eyJhbGc...",
    "expiresIn": 86400000
  }
}
```

### 健康管理 (Health Service)

| 方法 | 端点 | 说明 |
|------|------|------|
| POST | `/api/health/report` | 上报健康数据 |
| GET | `/api/health/list` | 获取健康数据列表 |
| POST | `/api/health/medication-reminders` | 创建用药提醒 |
| GET | `/api/health/medication-reminders` | 获取用药提醒列表 |
| POST | `/api/health/medication-reminders/{id}/mark-taken` | 标记已服用 |

### 消息服务 (Message Service)

| 方法 | 端点 | 说明 |
|------|------|------|
| POST | `/api/message/send` | 发送消息 |
| GET | `/api/message/list` | 获取消息列表 |
| GET | `/api/message/sessions` | 获取消息会话 |
| POST | `/api/message/{id}/read` | 标记已读 |

### 安全服务 (Security Service)

| 方法 | 端点 | 说明 |
|------|------|------|
| GET | `/api/security/alerts` | 获取告警列表 |
| POST | `/api/security/geofence` | 创建地理围栏 |
| POST | `/api/security/location` | 上报位置 |

---

## 常见任务

### 添加新的 API 端点

#### 1. 创建 DTO (数据传输对象)

```java
// src/main/java/com/example/yinling/user/dto/UserUpdateRequest.java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    @NotBlank(message = "昵称不能为空")
    private String nickname;
    
    private String avatar;
    
    @Pattern(regexp = "^[MF]$", message = "性别只能是 M 或 F")
    private String gender;
}
```

#### 2. 在 Mapper 中添加方法

```java
// src/main/java/com/example/yinling/user/mapper/UserMapper.java
@Update("UPDATE user SET nickname = #{nickname}, updated_at = NOW() WHERE user_id = #{userId}")
int updateUserInfo(String userId, String nickname);
```

#### 3. 在 Service 中添加业务方法

```java
// src/main/java/com/example/yinling/user/service/UserService.java
Result<User> updateUser(String userId, UserUpdateRequest request);
```

#### 4. 实现 Service 方法

```java
// src/main/java/com/example/yinling/user/service/impl/UserServiceImpl.java
@Override
public Result<User> updateUser(String userId, UserUpdateRequest request) {
    User user = userMapper.selectById(userId);
    if (user == null) {
        return Result.notFound("用户不存在");
    }
    
    user.setNickname(request.getNickname());
    user.setAvatar(request.getAvatar());
    user.setGender(request.getGender());
    
    userMapper.updateById(user);
    return Result.success(user);
}
```

#### 5. 在 Controller 中添加端点

```java
// src/main/java/com/example/yinling/user/controller/UserController.java
@PutMapping("/user/{userId}")
public Result<User> updateUser(
    @PathVariable String userId,
    @Valid @RequestBody UserUpdateRequest request) {
    return userService.updateUser(userId, request);
}
```

### 添加新的 Flutter 屏幕

```dart
// lib/screens/new_screen.dart
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';

class NewScreen extends ConsumerStatefulWidget {
  const NewScreen({Key? key}) : super(key: key);

  @override
  ConsumerState<NewScreen> createState() => _NewScreenState();
}

class _NewScreenState extends ConsumerState<NewScreen> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('新屏幕')),
      body: Center(
        child: Text('内容'),
      ),
    );
  }
}
```

### 添加新的 Android Screen

```kotlin
// app/src/main/java/com/example/yinling/ui/screens/NewScreen.kt
@Composable
fun NewScreen(viewModel: NewViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("新屏幕") })
        },
        content = { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center,
            ) {
                Text("内容")
            }
        }
    )
}
```

---

## 开发规范

### 后端编码规范

#### 1. 命名规范
- 包名: `com.example.yinling.{service}.{layer}`
- 类名: PascalCase (例: `UserService`)
- 常量: UPPER_SNAKE_CASE (例: `MAX_RETRY_COUNT`)
- 方法名: camelCase (例: `getUserById`)

#### 2. 异常处理
```java
// ✅ 正确做法
try {
    // 业务逻辑
} catch (Exception e) {
    logger.error("处理失败", e);
    throw new BusinessException(-1, "处理失败");
}

// ❌ 避免
try {
    // 业务逻辑
} catch (Exception e) {
    e.printStackTrace();
}
```

#### 3. 日志输出
```java
// ✅ 使用 Lombok 的 @Slf4j
@Slf4j
@Service
public class UserService {
    public void doSomething() {
        log.info("开始处理用户");
        log.debug("用户详情: {}", user);
        log.warn("可能的问题: {}", warning);
        log.error("发生错误: {}", error);
    }
}
```

#### 4. 验证规范
```java
// ✅ 使用 Jakarta Validation
@PostMapping("/register")
public Result<User> register(@Valid @RequestBody UserRegisterRequest request) {
    // request 已自动验证
}

// ❌ 避免手动验证
if (request.getPhone() == null || request.getPhone().isEmpty()) {
    // ...
}
```

### Android 编码规范

#### 1. Compose 组件
```kotlin
// ✅ 提取为单独函数
@Composable
fun UserCard(user: User) {
    Card {
        // UI 代码
    }
}

// 在其他地方使用
UserCard(user = user)

// ❌ 避免在主函数中混合所有代码
@Composable
fun MainScreen() {
    Scaffold {
        Card { /* ... */ }
        Card { /* ... */ }
        Button { /* ... */ }
    }
}
```

#### 2. ViewModel 模式
```kotlin
// ✅ 使用 @HiltViewModel
@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    
    fun loadUser(userId: String) {
        viewModelScope.launch {
            // 异步操作
        }
    }
}
```

### Flutter 编码规范

#### 1. 提供者模式
```dart
// ✅ 使用 Riverpod StateNotifier
final userProvider = StateNotifierProvider<UserNotifier, User>((ref) {
  return UserNotifier(ref.watch(apiServiceProvider));
});

class UserNotifier extends StateNotifier<User> {
  final ApiService apiService;
  
  UserNotifier(this.apiService) : super(User.empty());
  
  Future<void> loadUser(String userId) async {
    final user = await apiService.getUser(userId);
    state = user;
  }
}
```

#### 2. Widget 命名
```dart
// ✅ 清晰的命名
class UserListScreen extends ConsumerWidget { }
class UserCard extends StatelessWidget { }
class LoginForm extends ConsumerStatefulWidget { }

// ❌ 不清晰的命名
class MyScreen extends ConsumerWidget { }
class Card2 extends StatelessWidget { }
```

---

## 常见问题

### Q1: 如何修改 API 基础 URL?

**后端**:
```yaml
# application.yml
server:
  servlet:
    context-path: /api
```

**Android**:
```kotlin
// AppModule.kt 中修改 BASE_URL
const val BASE_URL = "http://your-server:8080/api/"
```

**Flutter**:
```dart
// lib/config/app_config.dart
class AppConfig {
  static const String apiBaseUrl = 'http://your-server:8080/api';
}
```

### Q2: 如何添加新的 Redux 操作?

**后端** - 添加 Service 方法:
```java
// 在 UserService 接口中添加
Result<User> changePassword(String userId, String oldPassword, String newPassword);

// 在 UserServiceImpl 中实现
@Override
public Result<User> changePassword(String userId, String oldPassword, String newPassword) {
    User user = userMapper.selectById(userId);
    if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
        return Result.error(-1, "旧密码错误");
    }
    user.setPassword(passwordEncoder.encode(newPassword));
    userMapper.updateById(user);
    return Result.success(user);
}
```

**Android** - 添加 ViewModel 方法:
```kotlin
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {
    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            try {
                val result = apiService.changePassword(oldPassword, newPassword)
                // 更新 UI 状态
            } catch (e: Exception) {
                // 处理错误
            }
        }
    }
}
```

**Flutter** - 添加 Provider 方法:
```dart
class AuthNotifier extends StateNotifier<AuthState> {
    Future<void> changePassword(String oldPassword, String newPassword) async {
        try {
            final response = await apiService.changePassword(oldPassword, newPassword);
            // 更新状态
        } catch (e) {
            state = state.copyWith(error: e.toString());
        }
    }
}
```

### Q3: 如何处理认证令牌过期?

**后端** - Token 自动刷新:
```java
// 在拦截器中处理
if (exception.getStatusCode() == 401) {
    // 尝试用 refreshToken 获取新的 accessToken
}
```

**Android** - Token 拦截器:
```kotlin
// AppModule.kt
val httpClient = OkHttpClient.Builder()
    .addInterceptor(TokenInterceptor(tokenProvider))
    .build()

class TokenInterceptor(private val tokenProvider: TokenProvider) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = tokenProvider.getToken()
        if (token != null) {
            request = request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}
```

**Flutter** - Token 刷新:
```dart
// api_service.dart
_dio.interceptors.add(
  InterceptorsWrapper(
    onResponse: (response, handler) {
      if (response.statusCode == 401) {
        // 刷新令牌
        return _refreshTokenAndRetry(response.requestOptions);
      }
      return handler.next(response);
    },
  ),
);
```

### Q4: 如何运行单元测试?

**后端**:
```bash
cd backend
mvn test

# 运行特定测试
mvn test -Dtest=UserServiceImplTest
```

**Android**:
```bash
cd android-elderly-app
# 单元测试
flutter test

# UI 测试
flutter drive --target=test_driver/app.dart
```

**Flutter**:
```bash
cd flutter-family-app
flutter test

# 生成覆盖率
flutter test --coverage
```

### Q5: 如何部署到生产环境?

参考: [部署指南](../docs/DEPLOYMENT_GUIDE.md)

---

## 技术栈版本

| 技术 | 版本 | 备注 |
|------|------|------|
| Java | 17+ | 必需 |
| Spring Boot | 3.2.x | 最新稳定版 |
| MySQL | 8.0+ | 必需 |
| Redis | 6.0+ | 可选但推荐 |
| Kotlin | 1.9.20+ | Android 开发 |
| Flutter | 3.16+ | 跨平台开发 |
| Dart | 3.2+ | Flutter 语言 |

---

## 联系方式

- **项目经理**: [待定]
- **技术负责人**: [待定]
- **GitHub**: [待定]
- **文档**: 参考 `docs/` 目录

---

**最后更新**: 2024年  
**版本**: 1.0.0

