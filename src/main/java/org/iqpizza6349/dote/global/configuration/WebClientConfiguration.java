package org.iqpizza6349.dote.global.configuration;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    private static final DefaultUriBuilderFactory FACTORY = new DefaultUriBuilderFactory();

    private static final HttpClient CLIENT = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .responseTimeout(Duration.ofSeconds(2))
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5));
                connection.addHandlerLast(new WriteTimeoutHandler(5));
            }).resolver(DefaultAddressResolverGroup.INSTANCE);
//            .secure(sslContextSpec -> sslContextSpec.sslContext(
//                    SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
//            ));

    @Bean
    public WebClient webClient() {
        FACTORY.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        return WebClient.builder()
                .uriBuilderFactory(FACTORY)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024))
                .clientConnector(new ReactorClientHttpConnector(CLIENT))
                .build();
    }

    @Bean
    public ConnectionProvider connectionProvider() {
        return ConnectionProvider.builder("http-pool")
                .maxConnections(10)
                .pendingAcquireTimeout(Duration.ofMillis(0))
                .pendingAcquireMaxCount(-1)
                .maxIdleTime(Duration.ofSeconds(1))
                .build();
    }
}
