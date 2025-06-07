package com.zappcomments.zappcomments.commentservice.api.client;

import com.zappcomments.zappcomments.commentservice.api.client.exception.CommentClientBadGatewayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RestClientFactory {


    private final RestClient.Builder builder;

    public RestClient restClient() {
        return builder.baseUrl("http://localhost:8081")
                .requestFactory(generateClientHttpRequestFactory())
                .defaultStatusHandler(HttpStatusCode::isError, (request, response) -> {
                    throw new CommentClientBadGatewayException("O serviço de moderação está indisponível. Tente novamente mais tarde.");
                })
                .build();
    }


    private ClientHttpRequestFactory generateClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();

        factory.setReadTimeout(Duration.ofSeconds(5));
        factory.setConnectTimeout(Duration.ofSeconds(3));

        return factory;
    }
}
