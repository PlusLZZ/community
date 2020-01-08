package lzz.blog.community.community.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;//阅读数
    private Integer commentCount;//评论数
    private Integer likeCount;//点赞数


}
