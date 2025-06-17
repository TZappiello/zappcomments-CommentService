package com.zappcomments.zappcomments.commentservice.api.controller;

import com.zappcomments.zappcomments.commentservice.api.model.PostInput;
import com.zappcomments.zappcomments.commentservice.api.model.PostOutput;
import com.zappcomments.zappcomments.commentservice.common.IdGenerator;
import com.zappcomments.zappcomments.commentservice.domain.model.Post;
import com.zappcomments.zappcomments.commentservice.domain.model.PostId;
import com.zappcomments.zappcomments.commentservice.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQConfig.TEXT_PROCESSOR_SERVICE_POST_PROCESSING;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final RabbitTemplate rabbitTemplate;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostOutput create(@RequestBody @Validated PostInput input) {


        Post post = Post.builder()
                .id(new PostId(IdGenerator.generateTSID()))
                .title(input.getTitle())
                .body(input.getBody())
                .author(input.getAuthor())
                .wordCount(123)
                .calculatedValue(12.45)
                .build();

        post = postRepository.save(post);

        return
                PostOutput.builder()
                        .id(post.getId().getValue())
                        .title(post.getTitle())
                        .body(post.getBody())
                        .author(post.getAuthor())
                        .wordCount(post.getWordCount())
                        .calculatedValue(post.getCalculatedValue())
                        .build();
    }

}
