/*alter table COMMENT alter column ID BIGINT auto_increment;*/
ALTER TABLE `blog`.`comment`
MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT FIRST;