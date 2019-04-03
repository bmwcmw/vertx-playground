mvn clean package

 ~/bin/graalvm-ce-1.0.0-rc14/bin/native-image \
 --verbose \
 --no-server \
 --allow-incomplete-classpath \
 -Dio.netty.noUnsafe=true  \
 -H:ReflectionConfigurationFiles=./reflectconfigs/netty.json \
 -H:+ReportUnsupportedElementsAtRuntime \
 -Dfile.encoding=UTF-8 \
--delay-class-initialization-to-runtime=io.netty.handler.codec.http.HttpObjectEncoder \
--delay-class-initialization-to-runtime=io.netty.handler.codec.http2.Http2CodecUtil \
--delay-class-initialization-to-runtime=io.netty.handler.codec.http2.DefaultHttp2FrameWriter \
--delay-class-initialization-to-runtime=io.netty.handler.codec.http.websocketx.WebSocket00FrameEncoder \
 -jar target/native-1.0-SNAPSHOT-fat.jar
