package com.zappcomments.zappcomments.commentservice.api.client;

import com.zappcomments.zappcomments.commentservice.api.model.ModerationInput;
import com.zappcomments.zappcomments.commentservice.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;

public interface CommentClient {

    ModerationOutput moderateComment(@RequestBody ModerationInput input);


}
