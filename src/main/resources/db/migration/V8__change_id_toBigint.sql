/*alter table QUESTION alter column ID BIGINT auto_increment;
alter table USER alter column ID BIGINT auto_increment;*/

ALTER TABLE `blog`.`question`
MODIFY COLUMN `id` bigint(20) NOT NULL AUTO_INCREMENT FIRST;
ALTER TABLE `blog`.`user`
MODIFY COLUMN `ID` bigint(20) NOT NULL AUTO_INCREMENT FIRST;
