#!/bin/bash

# ===================================
# 银龄守候项目 - 一键部署脚本
# ===================================

set -e  # 遇到错误立即退出

# 颜色定义
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# 打印函数
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# 检查命令是否存在
check_command() {
    if ! command -v $1 &> /dev/null; then
        print_error "$1 未安装，请先安装"
        exit 1
    fi
}

# 检查环境
print_info "检查环境..."
check_command docker
check_command docker-compose
check_command git

# 读取参数
ENVIRONMENT=${1:-dev}
print_info "部署环境: $ENVIRONMENT"

# 加载环境变量
if [ -f .env ]; then
    print_info "加载环境变量..."
    export $(cat .env | grep -v '^#' | xargs)
else
    print_warn ".env 文件不存在，使用默认配置"
fi

# 拉取最新代码
print_info "拉取最新代码..."
git pull origin main

# 停止旧容器
print_info "停止旧容器..."
docker-compose -f docker-compose.full.yml down

# 清理旧镜像
print_info "清理旧镜像..."
docker system prune -f

# 构建镜像
print_info "构建Docker镜像..."
cd backend

# 构建网关服务
print_info "构建网关服务..."
cd gateway-service
mvn clean package -DskipTests
docker build -t yinling/gateway-service:latest .
cd ..

# 构建用户服务
print_info "构建用户服务..."
cd user-service
mvn clean package -DskipTests
docker build -t yinling/user-service:latest .
cd ..

# 构建健康管理服务
print_info "构建健康管理服务..."
cd health-service
mvn clean package -DskipTests
docker build -t yinling/health-service:latest .
cd ..

# 构建消息通讯服务
print_info "构建消息通讯服务..."
cd message-service
mvn clean package -DskipTests
docker build -t yinling/message-service:latest .
cd ..

# 构建安全监控服务
print_info "构建安全监控服务..."
cd security-service
mvn clean package -DskipTests
docker build -t yinling/security-service:latest .
cd ..

# 构建AI服务
print_info "构建AI服务..."
cd ai-service
mvn clean package -DskipTests
docker build -t yinling/ai-service:latest .
cd ..

# 构建定时任务服务
print_info "构建定时任务服务..."
cd scheduler-service
mvn clean package -DskipTests
docker build -t yinling/scheduler-service:latest .
cd ..

cd ..  # 返回根目录

# 启动所有服务
print_info "启动所有服务..."
docker-compose -f docker-compose.full.yml up -d

# 等待服务启动
print_info "等待服务启动..."
sleep 30

# 检查服务状态
print_info "检查服务状态..."
docker-compose -f docker-compose.full.yml ps

# 检查网关健康状态
print_info "检查网关健康状态..."
for i in {1..10}; do
    if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
        print_info "网关服务已启动"
        break
    else
        print_warn "等待网关服务启动... ($i/10)"
        sleep 5
    fi
done

# 显示日志
print_info "服务日志 (Ctrl+C退出):"
docker-compose -f docker-compose.full.yml logs -f

print_info "部署完成！"
print_info "访问地址:"
print_info "  - 网关服务: http://localhost:8080"
print_info "  - Nacos控制台: http://localhost:8848/nacos (nacos/nacos)"
print_info "  - RabbitMQ控制台: http://localhost:15672 (admin/Yinling@2025)"
print_info "  - Grafana: http://localhost:3000 (admin/Yinling@2025)"
