# 学生公寓管理系统（Java后端）
[![wakatime](https://wakatime.com/badge/user/dd052a23-1a65-4e6f-b05e-80bc93d8c93c/project/a79ac574-dcd4-4477-a035-20de20c62706.svg)](https://wakatime.com/badge/user/dd052a23-1a65-4e6f-b05e-80bc93d8c93c/project/a79ac574-dcd4-4477-a035-20de20c62706)

## 启动项目

### 修改数据库配置

修改数据库配置文件，路径为：`backend/src/main/resources/application.yml`

```yaml
spring:
  datasource:
    url: # 数据库地址
    username: # 数据库用户名
    password: # 数据库密码
```

修改阿里云SMS服务的配置文件，路径为：`backend/src/main/resources/application.yml`

```yaml
aliyun:
  sms:
    accessKeyId: # 阿里云的AccessKeyId
    accessKeySecret: # 阿里云的AccessKeySecret
    signName: # 短信签名
    templateCode: # 短信模板
```