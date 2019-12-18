###  必要文件功能
- build.gradle 总项目finance的构建文件
- dependencies.gradle 自定义的全局依赖文件
- setting.gradle 总项目与子项目的关系约束

### https加密
- 使用ssl安全套接层协议
- 具体操作
- 1. cmd中先使用jdk的命令生成内网离线keystore文件
 keytool -genkeypair -alias "tomcat" -keylg "RSA" -keystore "自定义的证书放置位置"
- 2. 在要配置https的工程中配置ssl协议
    service:
       ssl: enable
       key-store: file:path
       key-password: 1中配置的密码
        
