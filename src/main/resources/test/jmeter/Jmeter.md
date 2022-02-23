       
#**全栈性能测试工具:-JMETER**

**1.下载安装汉化：**

    1.官网地址：http://jmeter.apache.org/download_jmeter.cgi
        官网下载版本步骤：
        →打开链接：https://jmeter.apache.org/download_jmeter.cgi
        →找到：Apache JMeter 4.0 (Requires Java 8 or 9.)
        →找到：Binaries
        →选中：apache-jmeter-4.0.zip
        →点击下载到任意磁盘，解压到英文目录下。
    2.下载压缩包至任意磁盘，直接解压到英文目录下即可，解压后原zip包:apache-jmeter-4.0
    3.配置系统环境变量:
        windows+r :sysdm.cpl --> 打开系统环境变量
        （1）设置jmeter解压目录的JMETER_HOME环境变量：
            JMETER_HOME ：jmeter安装的路径（浏览目录：定位到jmeter-zip包的解压目录）
         （2）设置jmeter的bin目录的path环境变量：
            PATH ：分号后输入jmeter的bin路径（浏览目录：定位到jmeter安装包下的bin目录）
        （3）设置jmeter的classpath变量：
            CLASSPATH：;%JMETER_HOME%\lib\ext\ApacheJMeter_core.jar;%JMETER_HOME%\lib\jorphan.jar;  
        （4）最后点击所有弹出窗后的“确定”，确认保存所设置的3个系统变量     
    4.启动：jmeter下的bin目录，点击打开jmeter.bat文件
    5.语言选择：
        →option→choose language→Chinese simplified
    6.视频介绍地址：http://www.testroad.org/video/sign_details?id=3&v=15

**2.jmeter响应数据乱码解决：**   
    
    1.修改jemter安装路径下：jemter.properties文件中的IOS-8859-1编码为utf-8
    2.修改程序，返回响应数据类型编码为IOS...
    3.jemter添加后置处理器：prev.serDataEncoding("utf-8")
    
**3.jmeter压测：**

    1.jmeter入门
    2.自定义变量模拟多用户
    3.命令行使用：服务器压测方式
    4.redis压测工具:redis-benchmark
    5.springboot打war包

**4.压测接口：**

    1.主要不是并发是多少？而是并发是多少时，网站能够承载的QPS是多少，TPS是多少
      例如：双十一每秒有20万用户订单（TPS） 
      
**4.使用：**
    
    1.启动：F:\install-01\apache-jmeter-4.0\bin\jmeter.bat
    
**5.多用户测试-cvs文件内容读取：**
    
    F:\github-work\buildTool\jmeter\cvs
    
**6.jmeter命令行测试：--linux**

    1,windows上录好jmx
    2.命令行：sh jmeter,sh -n -t xxx.jmx -l result.jtl
    3.将result.jtl导入jmeter
    
**7.redis压测：**
    
    1.redis-benchmark -h 127.0.0.1 -p 6379 -c 100 -n 100000
        100个并发连接 100000个请求
        F:\github-work\buildTool\jmeter\redis
    2.redis-benchmark -h 127.0.0.1 -p 6379 -q -d 100
       存取大小为100字节的数据包 获取qps
        F:\github-work\buildTool\jmeter\redis
    3.redis-benchmark -t set,lpush -n 100000 -q
        仅测试set,lpush 命令
    4.redis-benchmark -n 100000 -q script load "redis.call('set','foo','bar')"
        仅测试某一条命令插入100000条的qps
    
    