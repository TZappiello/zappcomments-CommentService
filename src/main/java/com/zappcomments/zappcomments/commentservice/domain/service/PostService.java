package com.zappcomments.zappcomments.commentservice.domain.service;

import com.zappcomments.zappcomments.commentservice.api.model.PostData;
import com.zappcomments.zappcomments.commentservice.api.model.PostInput;
import com.zappcomments.zappcomments.commentservice.api.model.PostOutput;
import com.zappcomments.zappcomments.commentservice.common.IdGenerator;
import com.zappcomments.zappcomments.commentservice.domain.model.Post;
import com.zappcomments.zappcomments.commentservice.domain.model.PostId;
import com.zappcomments.zappcomments.commentservice.domain.repository.PostRepository;
import com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQListener;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQConfig.TEXT_PROCESSOR_SERVICE_POST_PROCESSING;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQListener rabbitMQListener;

    public PostOutput createPost(PostInput input) {
        Post post = Post.builder()
                .id(new PostId(IdGenerator.generateTSID()))
                .title(input.getTitle())
                .body(input.getBody())
                .author(input.getAuthor())
                .build();

        var id = post.getId().getValue();

        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("id", id);
            return message;
        };

        rabbitTemplate.convertAndSend(TEXT_PROCESSOR_SERVICE_POST_PROCESSING,
                "", input, messagePostProcessor);

        PostData postData = rabbitMQListener.transformPostData();

        post.setWordCount(postData.getWordCount());
        post.setCalculatedValue(postData.getCalculatedValue());

        post = postRepository.save(post);

        return PostOutput.builder()
                .id(post.getId().getValue())
                .title(post.getTitle())
                .body(post.getBody())
                .author(post.getAuthor())
                .wordCount(post.getWordCount())
                .calculatedValue(post.getCalculatedValue())
                .build();
    }
}