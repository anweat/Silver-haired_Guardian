#!/bin/bash

# ===================================
# 银龄守候项目 - 数据库备份脚本
# ===================================

set -e

# 配置
BACKUP_DIR="./backups"
DATE=$(date +%Y%m%d_%H%M%S)
DB_HOST="${DB_MASTER_HOST:-localhost}"
DB_PORT="${DB_MASTER_PORT:-3306}"
DB_USER="${DB_MASTER_USERNAME:-root}"
DB_PASS="${DB_MASTER_PASSWORD:-Yinling@2025}"

# 数据库列表
DATABASES=(
    "yinling_main"
    "yinling_health"
    "yinling_message"
    "yinling_security"
    "yinling_ai"
    "nacos_config"
)

# 创建备份目录
mkdir -p $BACKUP_DIR

echo "开始备份数据库..."

# 备份每个数据库
for DB in "${DATABASES[@]}"; do
    echo "备份数据库: $DB"
    mysqldump -h$DB_HOST -P$DB_PORT -u$DB_USER -p$DB_PASS \
        --single-transaction \
        --routines \
        --triggers \
        --events \
        $DB | gzip > $BACKUP_DIR/${DB}_${DATE}.sql.gz
    
    echo "完成: $BACKUP_DIR/${DB}_${DATE}.sql.gz"
done

# 删除7天前的备份
echo "清理旧备份文件..."
find $BACKUP_DIR -name "*.sql.gz" -mtime +7 -delete

echo "备份完成！"
echo "备份文件位置: $BACKUP_DIR"
