package com.zappcomments.zappcomments.commentservice.domain.repository;

import com.zappcomments.zappcomments.commentservice.domain.model.Comment;
import com.zappcomments.zappcomments.commentservice.domain.model.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentId> {

}
