# 银龄守候·双模交互版 - 老年端APP模块设计

> 文档版本：v1.0  
> 更新日期：2025年12月21日  
> 文档状态：已完成

---

## 目录

- [1. 模块概述](#1-模块概述)
- [2. 功能模块划分](#2-功能模块划分)
- [3. 四大金刚按钮模块](#3-四大金刚按钮模块)
- [4. AI视频伴侣模块](#4-ai视频伴侣模块)
- [5. UI/UX设计规范](#5-uiux设计规范)
- [6. 技术实现方案](#6-技术实现方案)
- [7. 数据流设计](#7-数据流设计)

---

## 1. 模块概述

### 1.1 定位与目标
老年端APP是本项目的核心客户端，面向60-80岁老年用户，采用创新的"双模交互"设计：
- **按钮模式**：极简、确定性、零学习成本
- **AI视频伴侣模式**：智能、自然、情感陪伴

### 1.2 设计原则

| 原则 | 说明 | 实现方式 |
|:----|:----|:--------|
| **费茨定律** | 按钮越大、距离越近，越容易点击 | 四个超大按钮占满首屏 |
| **希克定律** | 选项越少，决策越快 | 首屏仅4个核心入口 |
| **一致性原则** | 相同操作产生相同结果 | 统一的交互反馈模式 |
| **容错设计** | 允许犯错，提供撤销 | 重要操作二次确认 |
| **无障碍设计** | 考虑视力、听力下降 | 大字体、高对比、语音反馈 |

### 1.3 技术架构概览

```
┌─────────────────────────────────────────────────────────────┐
│                      老年端APP架构                            │
├─────────────────────────────────────────────────────────────┤
│  表现层 (Presentation Layer)                                 │
│  ├── Jetpack Compose UI                                     │
│  ├── 按钮模式界面 (ButtonModeScreen)                         │
│  ├── AI伴侣界面 (CompanionScreen)                            │
│  └── 通用组件 (共享UI组件)                                    │
├─────────────────────────────────────────────────────────────┤
│  领域层 (Domain Layer)                                       │
│  ├── 用例 (UseCases)                                        │
│  ├── 实体 (Entities)                                        │
│  └── 仓库接口 (Repository Interfaces)                        │
├─────────────────────────────────────────────────────────────┤
│  数据层 (Data Layer)                                         │
│  ├── 网络模块 (Retrofit + OkHttp)                            │
│  ├── 本地存储 (Room + DataStore)                             │
│  ├── AI能力 (语音/图像处理)                                   │
│  └── 系统服务 (相机/通讯录/通话)                              │
└─────────────────────────────────────────────────────────────┘
```

## 2. 功能模块划分

### 2.1 模块全景图

```
老年端APP
├── 核心模块
│   ├── 按钮模式模块 (button-mode)
│   │   ├── 帮我看看 (visual-assist)
│   │   ├── 打电话 (phone-call)
│   │   ├── 听留言 (voice-message)
│   │   └── 找小伴 (ai-companion)
│   └── AI伴侣模块 (ai-companion)
│       ├── 虚拟形象 (avatar)
│       ├── 语音交互 (voice-interaction)
│       └── 智能对话 (smart-dialogue)
├── 支撑模块
│   ├── 用户模块 (user)
│   ├── 健康管理 (health)
│   ├── 安全防护 (security)
│   ├── 消息通讯 (messaging)
│   └── 设置模块 (settings)
└── 基础模块
    ├── 网络通信 (network)
    ├── 本地存储 (storage)
    ├── AI能力 (ai-capability)
    └── 系统权限 (permission)
```

### 2.2 模块依赖关系

```
┌─────────────────────────────────────────────────────────────┐
│                         表现层                               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │ 按钮模式  │  │ AI伴侣  │  │  健康   │  │   设置   │    │
│  └────┬─────┘  └────┬─────┘  └────┬─────┘  └────┬─────┘    │
└───────┼─────────────┼─────────────┼─────────────┼───────────┘
        │             │             │             │
        ▼             ▼             ▼             ▼
┌─────────────────────────────────────────────────────────────┐
│                         领域层                               │
│  ┌──────────────────────────────────────────────────────┐   │
│  │                    UseCases                           │   │
│  │  识别药品 / 发起通话 / 播放留言 / 对话交互 / 设置提醒    │   │
│  └──────────────────────────────────────────────────────┘   │
└─────────────────────────────────────────────────────────────┘
        │
        ▼
┌─────────────────────────────────────────────────────────────┐
│                         数据层                               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐    │
│  │  网络   │  │  存储   │  │  AI能力  │  │  系统   │    │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘    │
└─────────────────────────────────────────────────────────────┘
```

## 3. 四大金刚按钮模块

### 3.1 界面布局设计

```
┌─────────────────────────────────────┐
│           银龄守候                   │  ← 状态栏 (高度48dp)
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │        📷 帮我看看          │   │  ← 按钮1 (高度160dp)
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │        📞 打电话            │   │  ← 按钮2 (高度160dp)
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │        🎧 听留言            │   │  ← 按钮3 (高度160dp)
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │                             │   │
│  │        🤖 找小伴            │   │  ← 按钮4 (高度160dp)
│  │                             │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

### 3.2 帮我看看模块（视觉辅助）

#### 功能说明
帮助老年用户识别药品、阅读文字材料。

#### 交互流程

```
┌─────────────────────────────────────────────────────────────┐
│  点击【帮我看看】                                             │
│       │                                                      │
│       ▼                                                      │
│  显示相机预览界面                                             │
│  ├── 大按钮：拍照                                            │
│  ├── 语音提示："把要看的东西放在框里，按一下拍照"               │
│  └── 取消按钮                                                │
│       │                                                      │
│       ▼ (拍照)                                               │
│  显示加载动画 + 语音："正在识别，请稍等"                       │
│       │                                                      │
│       ▼                                                      │
│  显示识别结果卡片                                             │
│  ├── 药品名称（超大字体）                                     │
│  ├── 用法用量（大字体 + 语音播报）                            │
│  ├── 注意事项（可展开）                                       │
│  └── 操作按钮：设置提醒 / 重新拍照                            │
└─────────────────────────────────────────────────────────────┘
```

#### 数据模型

```kotlin
// 识别请求
data class RecognitionRequest(
    val imageUrl: String,      // 图片OSS地址
    val type: RecognitionType, // MEDICINE/TEXT/GENERAL
    val userId: Long
)

// 识别结果
data class MedicineRecognitionResult(
    val medicineName: String,         // 药品名称
    val genericName: String?,         // 通用名
    val dosage: String,               // 规格
    val usage: String,                // 用法
    val frequency: String,            // 用量
    val precautions: List<String>,    // 注意事项
    val imageUrl: String,             // 原图地址
    val confidence: Float             // 置信度
)
```

### 3.3 打电话模块

#### 功能说明
快速拨打预设的常用联系人电话。

#### 界面设计

```
┌─────────────────────────────────────┐
│  ← 返回        打电话              │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐   │
│  │    👤                       │   │
│  │   儿子                      │   │  ← 联系人1 (头像+名字)
│  │  138****8888               │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    👤                       │   │
│  │   女儿                      │   │  ← 联系人2
│  │  139****9999               │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    👤                       │   │
│  │   老伴                      │   │  ← 联系人3
│  │  137****7777               │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │    🏥                       │   │
│  │   社区医院                   │   │  ← 联系人4
│  │  010-88888888              │   │
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

#### 联系人管理
- 最多支持5个常用联系人
- 由子女端远程配置
- 支持头像、昵称、电话号码
- 支持紧急联系人标记

### 3.4 听留言模块

#### 功能说明
播放子女发送的语音留言消息。

#### 界面设计

```
┌─────────────────────────────────────┐
│  ← 返回        听留言              │
├─────────────────────────────────────┤
│                                     │
│  ┌─────────────────────────────┐   │
│  │  儿子 · 今天 10:30          │   │
│  │  ┌─────────────────────┐   │   │
│  │  │  ▶  0:15            │   │   │  ← 最新留言（自动播放）
│  │  └─────────────────────┘   │   │
│  │  "爸，记得吃药啊..."        │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  女儿 · 昨天 18:20          │   │
│  │  ┌─────────────────────┐   │   │
│  │  │  ▶  0:08            │   │   │
│  │  └─────────────────────┘   │   │
│  └─────────────────────────────┘   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  🎙️ 按住说话，回复留言      │   │  ← 回复按钮
│  └─────────────────────────────┘   │
│                                     │
└─────────────────────────────────────┘
```

### 3.5 找小伴模块（AI伴侣入口）

#### 功能说明
一键呼出AI视频伴侣界面。

#### 入口设计
- 点击按钮直接进入AI伴侣界面
- 支持语音唤醒词："小伴小伴"
- 进入时播放问候语

## 4. AI视频伴侣模块

### 4.1 界面设计

```
┌─────────────────────────────────────┐
│                              ✕     │  ← 关闭按钮
├─────────────────────────────────────┤
│                                     │
│         ┌─────────────┐            │
│         │             │            │
│         │   虚拟形象   │            │  ← 动态虚拟形象
│         │   (Lottie)  │            │     响应语音音量
│         │             │            │
│         └─────────────┘            │
│                                     │
│    "阿姨好，今天需要我帮什么忙？"    │  ← 对话文本
│                                     │
├─────────────────────────────────────┤
│  ┌─────────────────────────────┐   │
│  │         🎤                   │   │  ← 语音输入按钮
│  │       按住说话               │   │
│  └─────────────────────────────┘   │
│                                     │
│  💊 看药品  📖 读文字  📞 打电话  │  ← 快捷功能入口
│                                     │
└─────────────────────────────────────┘
```

### 4.2 虚拟形象引擎

#### 形象类型
| 类型 | 说明 | 实现方式 |
|:----|:----|:--------|
| 默认形象 | 可爱卡通形象 | Lottie动画 |
| 照片形象 | 子女照片生成 | AI形象生成服务 |
| 定制形象 | 付费高级形象 | 多套Lottie动画 |

#### 动画状态
```
虚拟形象状态机
├── Idle (待机)         - 轻微呼吸动画
├── Listening (听取)    - 头部微动，耳朵竖起
├── Thinking (思考)     - 眨眼，略低头
├── Speaking (说话)     - 口型动画同步TTS
└── Happy (开心)        - 笑脸，点头
```

### 4.3 语音交互链路

```
┌─────────────────────────────────────────────────────────────┐
│                      语音交互链路                            │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│  用户按住说话                                                │
│       │                                                     │
│       ▼                                                     │
│  ┌─────────────┐                                           │
│  │  录音采集    │  AudioRecord (16kHz, 16bit, Mono)        │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  ┌─────────────┐                                           │
│  │  语音识别    │  讯飞ASR (流式/实时)                       │
│  │   (ASR)     │  返回识别文本                              │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  ┌─────────────┐                                           │
│  │  意图理解    │  NLU服务                                  │
│  │   (NLU)     │  识别意图+槽位                            │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  ┌─────────────┐                                           │
│  │  业务处理    │  调用对应业务服务                          │
│  │  (Router)   │  药品识别/发起通话等                       │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  ┌─────────────┐                                           │
│  │  回复生成    │  模板+LLM混合                             │
│  │  (Response) │  生成自然语言回复                          │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  ┌─────────────┐                                           │
│  │  语音合成    │  讯飞TTS                                  │
│  │   (TTS)     │  文本转语音                               │
│  └──────┬──────┘                                           │
│         │                                                   │
│         ▼                                                   │
│  播放语音 + 更新形象动画                                     │
│                                                             │
└─────────────────────────────────────────────────────────────┘
```

### 4.4 意图识别设计

#### 支持的意图类型

| 意图ID | 意图名称 | 示例语句 | 响应动作 |
|:------|:--------|:--------|:--------|
| `medicine_check` | 查看药品 | "这个药怎么吃" "帮我看看这个药" | 打开相机识别 |
| `read_text` | 阅读文字 | "读一下这个" "这上面写的啥" | 打开相机OCR |
| `make_call` | 打电话 | "给儿子打电话" "我要打电话" | 拨打联系人 |
| `play_message` | 听留言 | "有没有留言" "听听消息" | 播放最新留言 |
| `set_reminder` | 设置提醒 | "提醒我吃药" "下午三点提醒我" | 创建提醒 |
| `chat` | 闲聊 | "今天天气怎么样" "陪我聊聊" | LLM对话 |
| `help` | 求助 | "你能帮我做什么" | 功能介绍 |

#### 槽位定义

```json
{
  "make_call": {
    "contact_name": "联系人名称",
    "contact_relation": "关系（儿子/女儿/老伴）"
  },
  "set_reminder": {
    "reminder_type": "提醒类型（吃药/喝水/运动）",
    "reminder_time": "提醒时间"
  }
}
```

## 5. UI/UX设计规范

### 5.1 色彩规范

| 用途 | 颜色 | 色值 | 说明 |
|:----|:----|:----|:----|
| 主色 | 暖橙色 | #FF8C42 | 温暖、活力、易识别 |
| 辅色 | 深蓝色 | #2D5A8A | 信任、稳重、高对比 |
| 背景色 | 米白色 | #FFF8F0 | 护眼、柔和 |
| 文字色 | 深灰色 | #333333 | 高可读性 |
| 成功色 | 绿色 | #4CAF50 | 操作成功反馈 |
| 警告色 | 红色 | #E53935 | 风险警示 |

### 5.2 字体规范

| 场景 | 字号 | 字重 | 行高 |
|:----|:----|:----|:----|
| 超大标题 | 32sp | Bold | 1.4 |
| 大标题 | 28sp | Bold | 1.4 |
| 按钮文字 | 24sp | Medium | 1.2 |
| 正文内容 | 20sp | Regular | 1.6 |
| 辅助说明 | 18sp | Regular | 1.5 |

### 5.3 间距规范

| 场景 | 间距 |
|:----|:----|
| 页面边距 | 16dp |
| 模块间距 | 24dp |
| 元素间距 | 12dp |
| 按钮内边距 | 16dp |

### 5.4 交互反馈

| 交互 | 反馈方式 |
|:----|:--------|
| 按钮点击 | 震动(50ms) + 涟漪动画 + 音效 |
| 长按 | 震动(100ms) + 放大动画 |
| 滑动 | 阻尼效果 |
| 加载中 | 旋转动画 + 语音提示 |
| 成功 | 绿色打钩 + 语音确认 |
| 失败 | 红色提示 + 语音说明 |

## 6. 技术实现方案

### 6.1 项目结构

```
app/
├── src/main/
│   ├── java/com/silverage/companion/
│   │   ├── App.kt                    # Application
│   │   ├── MainActivity.kt           # 主Activity
│   │   │
│   │   ├── presentation/             # 表现层
│   │   │   ├── button/               # 按钮模式
│   │   │   │   ├── ButtonModeScreen.kt
│   │   │   │   ├── VisualAssistScreen.kt
│   │   │   │   ├── PhoneCallScreen.kt
│   │   │   │   └── VoiceMessageScreen.kt
│   │   │   ├── companion/            # AI伴侣
│   │   │   │   ├── CompanionScreen.kt
│   │   │   │   ├── AvatarView.kt
│   │   │   │   └── VoiceInputView.kt
│   │   │   ├── common/               # 公共组件
│   │   │   │   ├── BigButton.kt
│   │   │   │   ├── LoadingDialog.kt
│   │   │   │   └── ResultCard.kt
│   │   │   └── theme/                # 主题
│   │   │       ├── Color.kt
│   │   │       ├── Typography.kt
│   │   │       └── Theme.kt
│   │   │
│   │   ├── domain/                   # 领域层
│   │   │   ├── usecase/
│   │   │   │   ├── RecognizeMedicineUseCase.kt
│   │   │   │   ├── MakeCallUseCase.kt
│   │   │   │   ├── PlayMessageUseCase.kt
│   │   │   │   └── VoiceInteractUseCase.kt
│   │   │   ├── model/
│   │   │   │   ├── Medicine.kt
│   │   │   │   ├── Contact.kt
│   │   │   │   └── VoiceMessage.kt
│   │   │   └── repository/
│   │   │       ├── HealthRepository.kt
│   │   │       ├── ContactRepository.kt
│   │   │       └── MessageRepository.kt
│   │   │
│   │   ├── data/                     # 数据层
│   │   │   ├── remote/               # 网络
│   │   │   │   ├── api/
│   │   │   │   │   ├── HealthApi.kt
│   │   │   │   │   ├── MessageApi.kt
│   │   │   │   │   └── AiApi.kt
│   │   │   │   └── dto/
│   │   │   ├── local/                # 本地存储
│   │   │   │   ├── dao/
│   │   │   │   ├── entity/
│   │   │   │   └── AppDatabase.kt
│   │   │   └── repository/           # 仓库实现
│   │   │
│   │   ├── ai/                       # AI能力
│   │   │   ├── asr/                  # 语音识别
│   │   │   │   ├── AsrManager.kt
│   │   │   │   └── XunfeiAsrImpl.kt
│   │   │   ├── tts/                  # 语音合成
│   │   │   │   ├── TtsManager.kt
│   │   │   │   └── XunfeiTtsImpl.kt
│   │   │   ├── nlu/                  # 意图理解
│   │   │   │   ├── NluManager.kt
│   │   │   │   └── IntentRouter.kt
│   │   │   └── avatar/               # 虚拟形象
│   │   │       └── AvatarAnimator.kt
│   │   │
│   │   └── di/                       # 依赖注入
│   │       ├── AppModule.kt
│   │       ├── NetworkModule.kt
│   │       └── AiModule.kt
│   │
│   └── res/
│       ├── drawable/
│       ├── raw/                      # Lottie动画文件
│       │   ├── avatar_idle.json
│       │   ├── avatar_speaking.json
│       │   └── avatar_listening.json
│       └── values/
│           ├── colors.xml
│           ├── strings.xml
│           └── themes.xml
│
├── build.gradle.kts
└── proguard-rules.pro
```

### 6.2 核心依赖

```kotlin
// build.gradle.kts (app)
dependencies {
    // Jetpack Compose
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Lifecycle & ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    
    // Hilt (依赖注入)
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // Room (本地数据库)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    
    // DataStore (KV存储)
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Retrofit + OkHttp (网络)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    
    // CameraX (相机)
    implementation("androidx.camera:camera-camera2:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")
    
    // Lottie (动画)
    implementation("com.airbnb.android:lottie-compose:6.3.0")
    
    // 讯飞SDK (语音)
    implementation(files("libs/Msc.jar"))
    
    // 图片加载
    implementation("io.coil-kt:coil-compose:2.5.0")
    
    // 权限处理
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")
}
```

### 6.3 关键代码示例

#### 四大金刚按钮界面

```kotlin
@Composable
fun ButtonModeScreen(
    navController: NavController,
    viewModel: ButtonModeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F0))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 帮我看看
        BigButton(
            text = "📷 帮我看看",
            backgroundColor = Color(0xFFFF8C42),
            onClick = {
                vibrate(context)
                navController.navigate("visual_assist")
            }
        )
        
        // 打电话
        BigButton(
            text = "📞 打电话",
            backgroundColor = Color(0xFF2D5A8A),
            onClick = {
                vibrate(context)
                navController.navigate("phone_call")
            }
        )
        
        // 听留言
        BigButton(
            text = "🎧 听留言",
            backgroundColor = Color(0xFF4CAF50),
            onClick = {
                vibrate(context)
                navController.navigate("voice_message")
            }
        )
        
        // 找小伴
        BigButton(
            text = "🤖 找小伴",
            backgroundColor = Color(0xFFE91E63),
            onClick = {
                vibrate(context)
                navController.navigate("ai_companion")
            }
        )
    }
}

@Composable
fun BigButton(
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = text,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
```

## 7. 数据流设计

### 7.1 整体数据流

```
┌─────────────────────────────────────────────────────────────┐
│                      数据流架构 (MVI)                         │
├─────────────────────────────────────────────────────────────┤
│                                                             │
│    View                 ViewModel                 Model     │
│  (Compose)              (StateFlow)            (Repository) │
│      │                       │                       │      │
│      │  ──── Intent ────►   │                       │      │
│      │                       │  ──── Action ────►   │      │
│      │                       │                       │      │
│      │                       │  ◄──── Result ────   │      │
│      │  ◄──── State ────    │                       │      │
│      │                       │                       │      │
└─────────────────────────────────────────────────────────────┘
```

### 7.2 药品识别数据流

```
用户点击【帮我看看】
       │
       ▼
┌─────────────────┐
│ VisualAssistVM  │
│                 │
│ Intent:         │
│ - TakePhoto     │
│ - Recognize     │
│                 │
│ State:          │
│ - Idle          │
│ - Capturing     │
│ - Uploading     │
│ - Recognizing   │
│ - Success(data) │
│ - Error(msg)    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐       ┌─────────────────┐
│ RecognizeUseCase│──────►│ HealthRepository│
└─────────────────┘       └────────┬────────┘
                                   │
         ┌─────────────────────────┼─────────────────────────┐
         │                         │                         │
         ▼                         ▼                         ▼
┌─────────────────┐       ┌─────────────────┐       ┌─────────────────┐
│   上传图片至OSS  │       │   调用OCR服务   │       │   本地缓存结果   │
└─────────────────┘       └─────────────────┘       └─────────────────┘
```

### 7.3 本地缓存策略

| 数据类型 | 缓存方式 | 过期策略 |
|:--------|:--------|:--------|
| 用户信息 | DataStore | 登出清除 |
| 联系人列表 | Room | 同步更新 |
| 识别历史 | Room | 保留30天 |
| 语音留言 | 文件 + Room | 保留7天 |
| AI对话记录 | Room | 保留100条 |
