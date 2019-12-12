## hazelcast的配置文件必须使用
- hazelcast.xml
- 否则不能启动hazelcast读取

-- 配置中的内容在源码的hazelcast-default.xml内容
- 需要手动打开管理中心<management-center enabled="true">

###### http://localhost:8080/hazelcast-mancenter</management-center>
- 管理中心默认端口为8080
- 默认路径为hazelcast-mancenter