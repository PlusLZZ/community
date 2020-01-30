这是一个第一次学习的博客项目

## github登录
[api构建地址](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

2020/01/13 增加了简单的分页功能,把标题栏做成了一个独立的可以引用的文件.
[增加了热部署的功能] 需要引用
         <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
 在设置里面更改compiler 的选项,勾选自动构建,按住ctrl+shift+alt+?可以打开注册,勾选compiler.automake.allow.when.app.running

数据库版本迁移 mvn flyway:migrate
mybatis xml配置 mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
h2数据库迁移时设置账号密码
create user if not exists sa password '123';
alter user sa admin true;
[POSTMAN](https://chrome.google.com/webstore/detail/coohjcphdfgbiolnekdpbcijmhambjff)
[markdown编辑器](https://pandao.github.io/editor.md/)

##部署
###依赖
-- Git
-- JDK
-- Maven
-- MySql
##步骤
1. yum update更新Centenos
2. yum install git 安装git
3. mkdir App
4. cd App
5. git clone https://github.com/LZZ-MZ/community.git
6. yum install maven  安装maven的时候自动把java安装了
7. mvn -v
8. mvn clean compile package
9  cp src/main/resources/application.properties src/main/resources/application-production.properties
10. vim src/main/resources/application-production.properties
11. java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar
12. # ps -ef |grep java   ps -ef|grep java|grep -v grep
13. jdbc:mysql://cdb-1akego1w.cd.tencentcdb.com:10070/blog
com.mysql.jdbc.Driver