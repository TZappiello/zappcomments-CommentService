package com.zappcomments.zappcomments.commentservice.domain.service;

import com.zappcomments.zappcomments.commentservice.api.model.*;
import com.zappcomments.zappcomments.commentservice.common.IdGenerator;
import com.zappcomments.zappcomments.commentservice.domain.model.Post;
import com.zappcomments.zappcomments.commentservice.domain.model.PostId;
import com.zappcomments.zappcomments.commentservice.domain.repository.PostRepository;
import com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQListener;
import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.zappcomments.zappcomments.commentservice.rabbitmq.RabbitMQConfig.TEXT_PROCESSOR_SERVICE_POST_PROCESSING;

@Slf4j
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


        PostInputConsumer postInputConsumer = PostInputConsumer.builder()
                .postId(post.getId().getValue().toString())
                .postBody(post.getBody())
                .build();

/*        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setHeader("id", id);
            return message;
        };*/

        rabbitTemplate.convertAndSend(TEXT_PROCESSOR_SERVICE_POST_PROCESSING,
                "", postInputConsumer);

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

    public PostOutput getOne(TSID postId) {
        log.info("Fetching comment with ID: {}", postId);
        Post post = getPost(postId);
        log.info("Comment found: {}", post);
        return getBuild(post);
    }

    public Page<PostSummaryOutput> getAll(Pageable pageable) {
        log.info("Fetching all comments with pagination: {}", pageable);
        Page<Post> res = postRepository.findAll(pageable);

        log.info("Comments fetched: {} items", res.getTotalElements());
        return res.map(post -> PostSummaryOutput.builder()
                .id(post.getId().getValue().toString())
                .title(post.getTitle())
                .summary(post.getBody().substring(0, 100))
                .author(post.getAuthor())
                .build());
    }

    private Post getPost(TSID postId) {
        return postRepository.findById(new PostId(postId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    private static PostOutput getBuild(Post post) {
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