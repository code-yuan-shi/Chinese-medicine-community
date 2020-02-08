package com.bbgu.zmz.community.dto;

import com.bbgu.zmz.community.model.Comment;
import com.bbgu.zmz.community.model.User;
import lombok.Data;

@Data
public class ReplyDTO {
    private Comment comment;
    private User user;
    private Long count;
    private String time;
}
