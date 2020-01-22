package lzz.blog.community.community.service;

import lzz.blog.community.community.enums.CommentTypeEnum;
import lzz.blog.community.community.exception.CustomizeErrorCode;
import lzz.blog.community.community.exception.CustomizeException;
import lzz.blog.community.community.exception.ICustomizeErrorCode;
import lzz.blog.community.community.mapper.CommentMapper;
import lzz.blog.community.community.mapper.QuestionExtMapper;
import lzz.blog.community.community.mapper.QuestionMapper;
import lzz.blog.community.community.model.Comment;
import lzz.blog.community.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        } else {
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionExtMapper.incCommentCount(question);
            //回复问题

        }
    }
}
