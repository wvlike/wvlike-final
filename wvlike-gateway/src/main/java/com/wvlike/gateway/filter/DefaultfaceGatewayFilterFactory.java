package com.wvlike.gateway.filter;

import io.netty.buffer.ByteBufAllocator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @program: cst-ump
 * @description:
 * @author: Leo
 * @create: 2019-08-10 10:08
 **/
@Slf4j
@Component
public class DefaultfaceGatewayFilterFactory extends AbstractGatewayFilterFactory<DefaultfaceGatewayFilterFactory.Config> {


    private List<String> unFilterUrl = new ArrayList<String>();

    {
        unFilterUrl.add("/interface/getToken");
    }

    public DefaultfaceGatewayFilterFactory() {
        super(Config.class);
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }


    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (!config.enabled) {
                return chain.filter(exchange);
            }
            URI uri = request.getURI();
            if (unFilterUrl.contains(uri.getPath())) {
                return chain.filter(exchange);
            }
            //得到请求体
            //String bodyStr = resolveBodyFromRequest(request);
//            logger.info("请求参数 -- bodyStr:"+bodyStr);
//            Map map = gson.fromJson(bodyStr, Map.class);
            //String appid = (String) map.get("appId");
            //String token = (String) map.get("token");
//            HttpHeaders headers = request.getHeaders();
//            String token = headers.getFirst("token");
//            String appid = headers.getFirst("appId");
//            //判断token
//            if (ObjectUtils.isEmpty(token) || ObjectUtils.isEmpty(appid)) {
//                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//                byte[] bytes = "{\"code\":\"410\",\"msg\":\"token错误\"}".getBytes(StandardCharsets.UTF_8);
//                DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
//                return exchange.getResponse().writeWith(Flux.just(buffer));
//            }
//            

            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
            //request = request.mutate().uri(uri).build();
            //DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
            //Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
            //request = new ServerHttpRequestDecorator(request) {
            //    @Override
            //    public Flux<DataBuffer> getBody() {
            //        return bodyFlux;
            //    }
            //};
            //封装request，传给下一级
            //return chain.filter(exchange.mutate().request(request).build());
            return chain.filter(exchange);
        };
    }

    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     *
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();

        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

}
