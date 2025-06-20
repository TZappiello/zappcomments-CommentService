package com.zappcomments.zappcomments.commentservice.domain.repository;

import com.zappcomments.zappcomments.commentservice.domain.model.Post;
import com.zappcomments.zappcomments.commentservice.domain.model.PostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, PostId> {
}
