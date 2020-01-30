CREATE TABLE USER
(
    ID           INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID   varchar(100),
    NAME         varchar(50),
    TOKEN        varchar(36),
    GMT_CREATE   bigint,
    GMT_MODIFIED bigint
);
/*
COMMENT ON COLUMN USER.ACCOUNT_ID IS '账户ID';
COMMENT ON COLUMN USER.GMT_CREATE IS '创建时间';
COMMENT ON COLUMN USER.GMT_MODIFIED IS '时间戳'*/
