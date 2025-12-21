#!/bin/bash

# ===================================
# 银龄守候项目 - 快速启动脚本
# ===================================

set -e

# 颜色定义
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m'

print_step() {
    echo -e "${GREEN}==>${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[警告]${NC} $1"
}

print_error() {
    echo -e "${RED}[错误]${NC} $1"
}

# 欢迎信息
echo "====================================="
echo "  银龄守候项目 - 快速启动"
echo "====================================="
echo ""

# 检查 .env 文件
if [ ! -f .env ]; then
    print_warn ".env 文件不存在"
    print_step "从模板复制环境变量文件..."
    cp .env.example .env
    print_warn "请编辑 .env 文件，填入真实的配置信息"
    echo "按回车键继续..."
    read
fi

# 启动基础设施
print_step "步骤 1/5: 启动基础设施 (MySQL, Redis, RabbitMQ, Nacos)..."
docker-compose up -d

# 等待服务就绪
print_step "等待服务启动 (约60秒)..."
sleep 60

# 检查MySQL是否就绪
print_step "检查MySQL连接..."
until docker exec yinling-mysql-master mysqladmin ping -h localhost -u root -pYinling@2025 --silent &> /dev/null; do
    echo "等待MySQL启动..."
    sleep 5
done
echo "MySQL已就绪"

# 初始化数据库
print_step "步骤 2/5: 初始化数据库..."
docker exec -i yinling-mysql-master mysql -u root -pYinling@2025 < scripts/init-database.sql
echo "数据库初始化完成"

# 后端服务说明
print_step "步骤 3/5: 后端服务"
echo "方式1: 使用IDE (推荐开发环境)"
echo "  - 在IntelliJ IDEA中打开 backend 目录"
echo "  - 启动各个微服务的主类"
echo ""
echo "方式2: 使用Maven命令"
echo "  cd backend/gateway-service && mvn spring-boot:run"
echo ""
echo "方式3: 使用Docker (完整环境)"
echo "  docker-compose -f docker-compose.full.yml up -d"
echo ""
read -p "是否使用Docker启动全部后端服务? (y/n): " use_docker

if [ "$use_docker" = "y" ]; then
    print_step "启动所有后端服务..."
    docker-compose -f docker-compose.full.yml up -d
    sleep 30
    print_step "后端服务已启动"
else
    print_warn "请手动启动后端服务"
    echo "按回车键继续..."
    read
fi

# Android应用说明
print_step "步骤 4/5: Android老年端APP"
echo "1. 在Android Studio中打开 android-elderly-app 目录"
echo "2. 同步Gradle依赖"
echo "3. 运行到模拟器或真机"
echo ""
print_warn "注意: 模拟器访问本机服务请使用 10.0.2.2 而非 localhost"
echo "按回车键继续..."
read

# Flutter应用说明
print_step "步骤 5/5: Flutter子女端APP"
echo "1. 进入flutter-family-app目录"
echo "   cd flutter-family-app"
echo ""
echo "2. 安装依赖"
echo "   flutter pub get"
echo ""
echo "3. 运行代码生成"
echo "   flutter pub run build_runner build --delete-conflicting-outputs"
echo ""
echo "4. 运行应用"
echo "   flutter run"
echo ""

# 完成信息
echo ""
echo "====================================="
print_step "启动完成！"
echo "====================================="
echo ""
echo "📱 服务访问地址:"
echo "  - 网关服务: http://localhost:8080"
echo "  - Nacos控制台: http://localhost:8848/nacos"
echo "    用户名/密码: nacos/nacos"
echo "  - RabbitMQ控制台: http://localhost:15672"
echo "    用户名/密码: admin/Yinling@2025"
echo ""
echo "📚 测试账号:"
echo "  - 老年用户: 13800138001 / Yinling@2025"
echo "  - 子女用户: 13800138002 / Yinling@2025"
echo ""
echo "📖 更多文档请查看:"
echo "  - README.md"
echo "  - docs/ 目录"
echo ""
echo "🎉 开始开发吧！"
