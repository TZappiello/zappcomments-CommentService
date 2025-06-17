package com.zappcomments.zappcomments.commentservice.domain.repository;

import com.zappcomments.zappcomments.commentservice.domain.model.Comment;
import com.zappcomments.zappcomments.commentservice.domain.model.CommentId;
import com.zappcomments.zappcomments.commentservice.domain.model.Post;
import com.zappcomments.zappcomments.commentservice.domain.model.PostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, PostId> {
}
