chcp 65001
java -Xdebug -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n -Dfile.encoding=utf-8 -jar ./target/lzpeng-boot-server.jar