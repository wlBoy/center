/*
Navicat MySQL Data Transfer

Source Server         : fenggaowei
Source Server Version : 50711
Source Host           : 10.0.42.160:3306
Source Database       : ar

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2019-01-08 15:48:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_suggest
-- ----------------------------
DROP TABLE IF EXISTS `admin_suggest`;
CREATE TABLE `admin_suggest` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `NAME` text,
  `CONTENT` text,
  `SUGGEST` text,
  `REPORT_ID` varchar(255) NOT NULL,
  `ADD_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for crm_org
-- ----------------------------
DROP TABLE IF EXISTS `crm_org`;
CREATE TABLE `crm_org` (
  `ORGID` varchar(255) NOT NULL,
  `sCode` varchar(255) DEFAULT NULL,
  `sName` varchar(255) DEFAULT NULL,
  `idEmployee` varchar(255) DEFAULT NULL,
  `sMemo` varchar(255) DEFAULT NULL,
  `Created` varchar(255) DEFAULT NULL,
  `Updated` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UpdatedBy` varchar(255) DEFAULT NULL,
  `pathid` varchar(255) DEFAULT NULL,
  `idparent` varchar(255) DEFAULT NULL,
  `bfail` varchar(255) DEFAULT NULL,
  `orgtype` varchar(255) DEFAULT NULL,
  `sname2` varchar(255) DEFAULT NULL,
  `pathid2` varchar(255) DEFAULT NULL,
  `babstract` varchar(255) DEFAULT NULL,
  `org_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ORGID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for crm_user
-- ----------------------------
DROP TABLE IF EXISTS `crm_user`;
CREATE TABLE `crm_user` (
  `USERID` varchar(255) DEFAULT NULL,
  `sCode` varchar(255) DEFAULT NULL,
  `sName` varchar(255) DEFAULT NULL,
  `idDep` varchar(255) DEFAULT NULL,
  `idScheme` varchar(255) DEFAULT NULL,
  `sPhoto` varchar(255) DEFAULT NULL,
  `sMemo` varchar(255) DEFAULT NULL,
  `lLoginUser` varchar(255) DEFAULT NULL,
  `sIDCard` varchar(255) DEFAULT NULL,
  `dBirthday` varchar(255) DEFAULT NULL,
  `sAddress` varchar(255) DEFAULT NULL,
  `sPostcode` varchar(255) DEFAULT NULL,
  `emTerritory` varchar(255) DEFAULT NULL,
  `sTel` varchar(255) DEFAULT NULL,
  `sTelHome` varchar(255) DEFAULT NULL,
  `sMobile` varchar(255) DEFAULT NULL,
  `sMail` varchar(255) DEFAULT NULL,
  `sMailPersonal` varchar(255) DEFAULT NULL,
  `sIM` varchar(255) DEFAULT NULL,
  `Created` varchar(255) DEFAULT NULL,
  `CreatedBy` varchar(255) DEFAULT NULL,
  `Updated` varchar(255) DEFAULT NULL,
  `UpdatedBy` varchar(255) DEFAULT NULL,
  `bAdmin` varchar(255) DEFAULT NULL,
  `emSex` varchar(255) DEFAULT NULL,
  `emStatus` varchar(255) DEFAULT NULL,
  `sPhoto_title` varchar(255) DEFAULT NULL,
  `stid` varchar(255) DEFAULT NULL,
  `issales` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `dirtelphone` varchar(255) DEFAULT NULL,
  `indate` varchar(255) DEFAULT NULL,
  `outdate` varchar(255) DEFAULT NULL,
  `needfilllog` varchar(255) DEFAULT NULL,
  `bmanager` varchar(255) DEFAULT NULL,
  `bcheck` varchar(255) DEFAULT NULL,
  `idorg` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `buref` varchar(255) DEFAULT NULL,
  `certcn` varchar(255) DEFAULT NULL,
  `idorgperf` varchar(255) DEFAULT NULL,
  `handtodep` varchar(255) DEFAULT NULL,
  `handtoemp` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL COMMENT '角色',
  `partTime` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for product_suggest
-- ----------------------------
DROP TABLE IF EXISTS `product_suggest`;
CREATE TABLE `product_suggest` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `NAME` text,
  `CONTENT` text,
  `SUGGEST` text,
  `SOURCE` text,
  `REPORT_ID` varchar(255) NOT NULL,
  `ADD_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for project_suggest
-- ----------------------------
DROP TABLE IF EXISTS `project_suggest`;
CREATE TABLE `project_suggest` (
  `ID` varchar(36) NOT NULL,
  `USER_ID` varchar(36) NOT NULL,
  `NAME` text,
  `CONTENT` text,
  `SUGGEST` text,
  `REPORT_ID` varchar(255) NOT NULL,
  `ADD_TIME` datetime DEFAULT CURRENT_TIMESTAMP,
  `TYPE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_report
-- ----------------------------
DROP TABLE IF EXISTS `tb_report`;
CREATE TABLE `tb_report` (
  `ID` varchar(255) NOT NULL,
  `USER_ID` varchar(255) DEFAULT NULL,
  `RESPONSIBILITIES` text,
  `DEVELOP_WORK` text,
  `DEPLOY_WORK` text,
  `TEST_WORK` text,
  `SALE_HISTORY` text,
  `NEW_CUSTOMER` text,
  `BIG_PROJECT` longtext,
  `ADMIN_WORK` text,
  `SECRECT_WORK` text,
  `PERSONAL_GOALS` text,
  `PROGRESS` text,
  `PROBLEM` text,
  `TARGET` text,
  `PRODUCT_SUGGEST` text,
  `PROJECT_SUGGEST` text,
  `ADMIN_SUGGEST` text,
  `STATUS` text,
  `SUGGEST` text,
  `TYPE` varchar(255) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `UPDATE_TIME` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `REPORT_YEAR` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_self_assessment
-- ----------------------------
DROP TABLE IF EXISTS `tb_self_assessment`;
CREATE TABLE `tb_self_assessment` (
  `SELF_ASSESSMENT_ID` varchar(255) NOT NULL COMMENT '自我评价的主键',
  `USER_ID` varchar(255) NOT NULL COMMENT '用户ID',
  `PM` int(11) NOT NULL COMMENT '计划管理(Project Management)',
  `IC` int(11) NOT NULL COMMENT '执行力（implementation capacity）',
  `SV` int(11) NOT NULL COMMENT '解决力（Solvency）',
  `RP` int(11) NOT NULL COMMENT '挑战承压（Resistance To Pressure）',
  `SD` int(11) NOT NULL COMMENT '尽职自律（self-discipline）',
  `TS` int(11) NOT NULL COMMENT ' 工作态度和协作精神（teamwork spirit）',
  `SI` int(11) NOT NULL COMMENT '文明建设和制度执行	（System implementation）',
  `CW` int(11) NOT NULL COMMENT '保密工作（Confidential work）',
  `EXCELLENT_EXAMPLES` text COMMENT '优秀典型事例',
  `SELF_COMMENT` text COMMENT '自我评语',
  `LEADER_COMMENT` text COMMENT '上级评语',
  `DEPARTMENT_COMMENT` text COMMENT '横向部门评语',
  `DEP_PM` int(11) DEFAULT NULL,
  `DEP_IC` int(11) DEFAULT NULL,
  `DEP_SV` int(11) DEFAULT NULL,
  `DEP_RP` int(11) DEFAULT NULL,
  `DEP_SD` int(11) DEFAULT NULL,
  `DEP_TS` int(11) DEFAULT NULL,
  `DEP_SI` int(11) DEFAULT NULL,
  `DEP_CW` int(11) DEFAULT NULL,
  `ADD_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_TIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` int(11) NOT NULL,
  `SELF_SUM` double DEFAULT NULL,
  `DEP_SUM` double DEFAULT NULL,
  `REVIEW_BY` varchar(255) DEFAULT NULL,
  `REVIEW_RESULT` int(11) DEFAULT NULL,
  `REVIEW_LEVEL` varchar(255) DEFAULT NULL,
  `ASSESSMENT_YEAR` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`SELF_ASSESSMENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vote_result
-- ----------------------------
DROP TABLE IF EXISTS `vote_result`;
CREATE TABLE `vote_result` (
  `id` varchar(255) NOT NULL COMMENT 'ID',
  `voterId` varchar(255) DEFAULT NULL COMMENT '投票人ID',
  `voterName` varchar(255) DEFAULT NULL COMMENT '投票人名称',
  `goldPrize` varchar(255) DEFAULT NULL COMMENT '金奖项投票结果',
  `silverPrize` varchar(2550) DEFAULT NULL COMMENT '银奖项投票结果',
  `bronzePrize` varchar(2550) DEFAULT NULL COMMENT '铜奖项投票结果',
  `bestTeamPrize` varchar(255) DEFAULT NULL COMMENT '最佳团队奖投票结果',
  `creativePrize` varchar(255) DEFAULT NULL COMMENT '创新奖投票结果',
  `qualityPrize` varchar(255) DEFAULT NULL COMMENT '质量奖投票结果',
  `marketDevelopmentPrize` varchar(255) DEFAULT NULL COMMENT '市场开拓奖投票结果',
  `deligentPrize` varchar(255) DEFAULT NULL COMMENT '勤勉尽责奖投票结果',
  `productInnovationPrize` varchar(255) DEFAULT NULL COMMENT '产品革新奖投票结果',
  `mostPotentialPrize` varchar(255) DEFAULT NULL COMMENT '最具潜力奖投票结果',
  `demoProjectPrize` varchar(255) DEFAULT NULL COMMENT '示范项目奖投票结果',
  `newComerPrize` varchar(255) DEFAULT NULL COMMENT '新人奖投票结果',
  `goodWifePrize` varchar(255) DEFAULT NULL COMMENT '贤内助奖投票结果',
  `voteYear` int(8) DEFAULT NULL COMMENT '投票年份',
  `voteTime` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '投票时间',
  `ticketStatus` tinyint(4) DEFAULT NULL COMMENT '票状态：0代表有效，1代表无效',
  `voteStatus` int(8) DEFAULT NULL COMMENT '投票状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for candidate_user
-- ----------------------------
DROP TABLE IF EXISTS `candidate_user`;
CREATE TABLE `candidate_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userNo` int(11) DEFAULT NULL COMMENT '候选人编号',
  `userName` varchar(255) DEFAULT NULL COMMENT '候选人姓名',
  `userDept` varchar(255) DEFAULT NULL COMMENT '候选人部门',
  `selectYear` int(11) DEFAULT NULL COMMENT '投票年份',
  `userVoteType` varchar(255) DEFAULT NULL COMMENT '候选人类型',
  `addTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '添加时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vote_result_sum
-- ----------------------------
DROP TABLE IF EXISTS `vote_result_sum`;
CREATE TABLE `vote_result_sum` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `candidateId` varchar(255) DEFAULT NULL COMMENT '候选人ID',
  `prizeType` varchar(255) DEFAULT NULL COMMENT '奖项',
  `voteId` varchar(255) DEFAULT NULL COMMENT '投票结果ID',
  `voteYear` int(8) DEFAULT NULL COMMENT '评选年份',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- VIEW STRUCTURE FOR vw_vote_result_sum
-- ----------------------------
DROP VIEW IF EXISTS `vw_vote_result_sum`;
CREATE VIEW `vw_vote_result_sum` AS 
SELECT
	s.candidateId,
	c.userName AS candidateName,
	c.userDept AS candidateDept,
	s.prizeType,
	v.voteYear,
	v.ticketStatus,
	count(1) AS count
FROM
	vote_result_sum s
LEFT JOIN candidate_user c ON s.candidateId = c.id
LEFT JOIN vote_result v ON s.voteId = v.id group by s.candidateId,v.voteYear,s.prizeType;