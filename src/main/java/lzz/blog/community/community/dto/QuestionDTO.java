package lzz.blog.community.community.dto;

import lombok.Data;
import lzz.blog.community.community.model.User;

@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Long creator;
    private Integer viewCount;//阅读数
    private Integer commentCount;//评论数
    private Integer likeCount;//点赞数
    private User user;
}
