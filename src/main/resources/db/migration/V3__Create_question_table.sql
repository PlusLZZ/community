CREATE TABLE question
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    title         VARCHAR(50),
    description   TEXT,
    gmt_create    BIGINT,
    gmt_modified  BIGINT,
    creator       INT,
    comment_count INT DEFAULT 0,
    view_count    INT DEFAULT 0,
    like_count    INT DEFAULT 0,
    tag           VARCHAR(256)
);
/*
COMMENT ON COLUMN question.title IS '标题';
COMMENT ON COLUMN question.description IS '内容';
COMMENT ON COLUMN question.creator IS '创建人';
COMMENT ON COLUMN question.comment_count IS '阅读数';
COMMENT ON COLUMN question.view_count IS '评论数';
COMMENT ON COLUMN question.like_count IS '点赞数';*/
