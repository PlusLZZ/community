package lzz.blog.community.community.mapper;

import lzz.blog.community.community.model.Comment;
import lzz.blog.community.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}