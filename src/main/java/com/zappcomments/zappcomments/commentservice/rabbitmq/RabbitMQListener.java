package com.zappcomments.zappcomments.commentservice.rabbitmq;

import com.zappcomments.zappcomments.commentservice.api.model.PostData;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQConfig.POST_PROCESSOR_SERVICE_POST_TRANSFORM;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private PostData postData;

    @RabbitListener(queues = POST_PROCESSOR_SERVICE_POST_TRANSFORM)
    public void handlerPostProcessor(@Payload PostData postData) {

        this.postData = PostData.builder()
                .postId(postData.getPostId())
                .wordCount(postData.getWordCount())
                .calculatedValue(postData.getCalculatedValue())
                .build();

        log.info("PostInput received: {}", postData);
    }

    @SneakyThrows
    public PostData transformPostData() {
        Thread.sleep(Duration.ofSeconds(4));
        return this.postData;
    }

}
