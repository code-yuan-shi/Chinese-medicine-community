package com.bbgu.zmz.community.dto;


import com.bbgu.zmz.community.model.Category;
import com.bbgu.zmz.community.model.Topicinfo;
import com.bbgu.zmz.community.model.User;
import lombok.Data;


@Data
public class TopicInfoDTO {
    private Topicinfo topicinfo;
    private User user;
    private Category category;
    private Long comment_count;
    private Long user_count;

}
