package lzz.blog.community.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了,要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登陆,请登录后重试"),
    SYS_ERROR(2004, "服务器炸了,给点零钱升级服务器吧!!!"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不在了,换一个试试吧!"),
    CONTENT_IS_EMPTY(2007, "输入内容不能为空!"),
    READ_NOTIFICATION_FAIL(2008, "这不是你的信息哦!!!"),
    NOTIFICATION_NOT_FOUND(2009, "消息不见咯!!!");

    private String message;
    private Integer code;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
