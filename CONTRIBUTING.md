# 贡献指南

感谢您对银龄守候项目的关注！本文档将帮助您了解如何参与项目开发。

## 📋 目录

- [开发环境准备](#开发环境准备)
- [代码规范](#代码规范)
- [提交规范](#提交规范)
- [分支策略](#分支策略)
- [Pull Request流程](#pull-request流程)
- [问题反馈](#问题反馈)

## 🛠️ 开发环境准备

### 1. 克隆项目

```bash
git clone https://github.com/your-org/SoftwareInvolution11.git
cd SoftwareInvolution11
```

### 2. 环境要求

- **Java**: JDK 17+
- **Node.js**: 18+
- **Flutter**: 3.16+
- **Android Studio**: 2023.1+
- **Docker**: 24.0+
- **Maven**: 3.8+

### 3. 配置环境变量

```bash
# 复制环境变量模板
cp .env.example .env

# 编辑 .env 文件，填入真实配置
```

### 4. 启动基础设施

```bash
# 启动MySQL, Redis, RabbitMQ, Nacos
docker-compose up -d

# 初始化数据库
mysql -h localhost -u root -p < scripts/init-database.sql
```

## 📝 代码规范

### Kotlin代码规范

```kotlin
// ✅ 正确示例
class UserRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getUser(userId: Long): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUser(userId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

// ❌ 错误示例
class UserRepository(val apiService: ApiService) {
    fun getUser(userId: Long): User {
        return apiService.getUser(userId)  // 缺少异步处理和错误处理
    }
}
```

### Dart代码规范

```dart
// ✅ 正确示例
class UserRepository {
  final Dio _dio;
  
  const UserRepository(this._dio);
  
  Future<Result<User>> getUser(int userId) async {
    try {
      final response = await _dio.get('/user/$userId');
      return Result.success(User.fromJson(response.data));
    } catch (e) {
      return Result.error(e.toString());
    }
  }
}

// ❌ 错误示例
class UserRepository {
  Dio dio;
  
  UserRepository(this.dio);  // 缺少const构造函数
  
  getUser(userId) {  // 缺少类型声明
    return dio.get('/user/$userId');  // 缺少错误处理
  }
}
```

### Java代码规范

```java
// ✅ 正确示例
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<User> createUser(UserDTO userDTO) {
        try {
            User user = BeanUtil.copyProperties(userDTO, User.class);
            userMapper.insert(user);
            return Result.success(user);
        } catch (Exception e) {
            log.error("创建用户失败", e);
            return Result.error("创建用户失败");
        }
    }
}

// ❌ 错误示例
public class UserService {
    UserMapper userMapper;  // 缺少注解和访问修饰符
    
    public User createUser(UserDTO userDTO) {
        User user = new User();
        // 缺少错误处理和事务管理
        userMapper.insert(user);
        return user;
    }
}
```

### 命名规范

| 类型 | 规范 | 示例 |
|------|------|------|
| 类名 | 大驼峰 | `UserService`, `HealthRepository` |
| 方法名 | 小驼峰 | `getUserInfo()`, `saveHealthData()` |
| 变量名 | 小驼峰 | `userId`, `healthRecord` |
| 常量名 | 全大写+下划线 | `MAX_RETRY_COUNT`, `API_BASE_URL` |
| 包名 | 全小写 | `com.yinling.user`, `com.yinling.health` |

## 📦 提交规范

### Commit Message格式

```
<type>(<scope>): <subject>

<body>

<footer>
```

### Type类型

- **feat**: 新功能
- **fix**: Bug修复
- **docs**: 文档更新
- **style**: 代码格式调整
- **refactor**: 代码重构
- **test**: 测试相关
- **chore**: 构建/工具链更新

### 示例

```bash
# 新功能
git commit -m "feat(user): 添加用户注册功能"

# Bug修复
git commit -m "fix(health): 修复用药提醒时间计算错误"

# 文档更新
git commit -m "docs(readme): 更新快速开始指南"

# 代码重构
git commit -m "refactor(ai): 优化语音识别缓存策略"
```

## 🌿 分支策略

```
main (生产环境)
  ├── develop (开发环境)
  │   ├── feature/user-auth (功能分支)
  │   ├── feature/health-record (功能分支)
  │   └── feature/ai-dialogue (功能分支)
  ├── hotfix/fix-login-bug (热修复分支)
  └── release/v1.0.0 (发布分支)
```

### 分支命名规范

- **功能分支**: `feature/功能名称`
- **修复分支**: `fix/问题描述`
- **热修复分支**: `hotfix/问题描述`
- **发布分支**: `release/版本号`

### 开发流程

1. 从`develop`创建功能分支
```bash
git checkout develop
git pull origin develop
git checkout -b feature/new-feature
```

2. 开发并提交代码
```bash
git add .
git commit -m "feat(module): 添加新功能"
```

3. 推送到远程仓库
```bash
git push origin feature/new-feature
```

4. 创建Pull Request到`develop`分支

## 🔄 Pull Request流程

### 1. 创建PR前检查

- [ ] 代码已通过本地测试
- [ ] 代码符合项目规范
- [ ] 已添加必要的注释
- [ ] 已更新相关文档
- [ ] 没有未解决的冲突

### 2. PR标题格式

```
[功能模块] 简短描述 (#IssueID)
```

示例:
```
[用户模块] 实现用户注册和登录功能 (#12)
[健康管理] 添加用药提醒功能 (#23)
```

### 3. PR描述模板

```markdown
## 变更类型
- [ ] 新功能
- [ ] Bug修复
- [ ] 文档更新
- [ ] 代码重构
- [ ] 性能优化

## 变更内容
简要描述本次PR的主要变更内容...

## 相关Issue
关闭 #123

## 测试说明
- 测试场景1: ...
- 测试场景2: ...

## 截图/录屏 (如适用)
[添加截图或录屏]

## Checklist
- [ ] 代码已通过单元测试
- [ ] 代码已通过集成测试
- [ ] 已添加必要的注释
- [ ] 已更新相关文档
- [ ] 没有引入破坏性变更
```

### 4. Code Review要求

每个PR至少需要1位团队成员的Review和Approve才能合并。

## 🐛 问题反馈

### 提Issue前检查

1. 搜索是否已有类似Issue
2. 确认是否是最新版本的问题
3. 准备复现步骤和相关日志

### Bug报告模板

```markdown
**Bug描述**
清晰简洁地描述Bug...

**复现步骤**
1. 进入'...'
2. 点击'...'
3. 滚动到'...'
4. 看到错误

**预期行为**
描述应该发生什么...

**实际行为**
描述实际发生了什么...

**截图**
如适用，添加截图...

**环境信息**
- OS: [e.g. Android 13]
- 应用版本: [e.g. 1.0.0]
- 设备: [e.g. Pixel 6]

**附加信息**
其他相关信息...
```

### 功能建议模板

```markdown
**功能描述**
清晰简洁地描述建议的功能...

**使用场景**
描述该功能的使用场景...

**解决的问题**
该功能解决什么问题...

**替代方案**
是否考虑过其他方案...

**附加信息**
其他相关信息...
```

## 📚 开发资源

- [项目文档](./docs/)
- [API文档](./docs/03-接口文档/)
- [架构设计](./docs/01-总览文档/02-技术架构总览.md)
- [开发计划](./docs/01-总览文档/03-开发计划与里程碑.md)

## 🤝 社区行为准则

请阅读我们的[行为准则](CODE_OF_CONDUCT.md)，了解社区互动的期望。

## 📞 联系我们

- **邮箱**: dev@yinling.com
- **微信群**: [扫码加入]
- **GitHub Discussions**: [项目讨论区]

---

再次感谢您的贡献！❤️
