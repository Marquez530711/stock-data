# 使用 Python 基础镜像
FROM python:3.10-slim

# 设置工作目录
WORKDIR /app

# 安装 aktools 及其依赖项
RUN pip install aktools

# 暴露端口 8080
EXPOSE 8080

# 启动命令，确保服务监听 0.0.0.0 地址
CMD ["python", "-m", "aktools", "--host", "0.0.0.0", "--port", "8080"]
