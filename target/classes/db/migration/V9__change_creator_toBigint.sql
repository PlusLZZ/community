/*alter table QUESTION alter column CREATOR BIGINT;
alter table COMMENT alter column COMMENTATOR BIGINT not null;*/

ALTER TABLE `blog`.`question`
MODIFY COLUMN `creator` bigint(20) DEFAULT NULL AFTER `gmt_modified`;
ALTER TABLE `blog`.`comment`
MODIFY COLUMN `commentator` bigint(20) NOT NULL AFTER `type`;