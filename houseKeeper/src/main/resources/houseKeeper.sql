/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50723
Source Host           : localhost:3306
Source Database       : housekeeper

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-11-08 13:24:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_xn_account_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_account_account`;
CREATE TABLE `tb_xn_account_account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '财务ID',
  `account_title` varchar(20) NOT NULL COMMENT '财务标题',
  `user_id` int(11) NOT NULL COMMENT '所属哪个用户的账务',
  `type_id` int(11) NOT NULL COMMENT '财务分类ID',
  `account_fee` double NOT NULL COMMENT '金额',
  `account_type` varchar(50) NOT NULL COMMENT '财务类型:现金还是各种银行',
  `curmonth` varchar(6) NOT NULL COMMENT '创建月份:YYYYmm',
  `curday` varchar(8) NOT NULL COMMENT '创建日期:YYYYmmdd',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) NOT NULL COMMENT '备注信息',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`account_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `selected` (`user_id`,`type_id`,`curday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_account_account
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_account_money
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_account_money`;
CREATE TABLE `tb_xn_account_money` (
  `money_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `in_fee` double NOT NULL DEFAULT '0' COMMENT '收入金额',
  `out_fee` double NOT NULL DEFAULT '0' COMMENT '支出金额',
  `total_fee` double NOT NULL DEFAULT '0' COMMENT '总资产',
  `curmonth` varchar(6) NOT NULL COMMENT '创建月份',
  `curday` varchar(8) NOT NULL COMMENT '创建日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`money_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `selected` (`user_id`,`curday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_account_money
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_account_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_account_type`;
CREATE TABLE `tb_xn_account_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账务类别ID',
  `user_id` int(11) NOT NULL COMMENT '所属哪个用户的账户类别',
  `type_name` varchar(50) NOT NULL COMMENT '账务类别名称',
  `parent_type` varchar(4) NOT NULL COMMENT '所属父类别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`type_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `selected` (`user_id`,`parent_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_account_type
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_data_channel
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_data_channel`;
CREATE TABLE `tb_xn_data_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `curday` varchar(100) NOT NULL COMMENT '日期',
  `partner` varchar(255) NOT NULL COMMENT '合作方',
  `app_name` varchar(100) NOT NULL COMMENT '应用名称',
  `channel_num` varchar(255) NOT NULL COMMENT '渠道号',
  `active_num` varchar(100) NOT NULL COMMENT '激活数',
  `fee` varchar(100) NOT NULL COMMENT '单价',
  `sum_fee` varchar(100) NOT NULL COMMENT '总额',
  `status` varchar(10) NOT NULL COMMENT '开放状态',
  `partner_type` varchar(100) NOT NULL COMMENT '合作方式',
  `createtime` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_data_channel
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_data_dic
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_data_dic`;
CREATE TABLE `tb_xn_data_dic` (
  `data_dic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据字典ID',
  `data_dic_name` varchar(255) NOT NULL COMMENT '数据字典名称',
  `data_dic_code` varchar(255) NOT NULL COMMENT '数据字典代码',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '数据字典描述',
  `is_ok` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`data_dic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_data_dic
-- ----------------------------
INSERT INTO `tb_xn_data_dic` VALUES ('1', '性别', 'SEX', '2018-11-08 10:50:36', '2018-11-08 10:50:36', '性别', '0');

-- ----------------------------
-- Table structure for tb_xn_data_dic_term
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_data_dic_term`;
CREATE TABLE `tb_xn_data_dic_term` (
  `data_dic_term_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据字典项ID',
  `data_dic_code` varchar(255) NOT NULL COMMENT '数据字典代码',
  `data_dic_term_code` varchar(255) NOT NULL COMMENT '数据字典项代码',
  `data_dic_term_value` varchar(255) NOT NULL COMMENT '数据字典项值',
  `remark` varchar(255) DEFAULT NULL COMMENT '数据字典项描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_ok` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`data_dic_term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_data_dic_term
-- ----------------------------
INSERT INTO `tb_xn_data_dic_term` VALUES ('1', 'SEX', '1', '男', '男', '2018-11-08 11:59:59', '2018-11-08 11:59:59', '0');
INSERT INTO `tb_xn_data_dic_term` VALUES ('2', 'SEX', '2', '女', '女', '2018-11-08 12:00:05', '2018-11-08 12:00:05', '0');

-- ----------------------------
-- Table structure for tb_xn_exam_paper
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_paper`;
CREATE TABLE `tb_xn_exam_paper` (
  `paper_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
  `create_paper_id` int(11) NOT NULL COMMENT '出卷人ID',
  `paper_name` varchar(50) NOT NULL COMMENT '试卷名',
  `paper_score` int(4) NOT NULL COMMENT '试卷总分',
  `total_time` int(4) NOT NULL COMMENT '试卷总时间(分钟)',
  `start_time` datetime NOT NULL COMMENT '试卷开考时间',
  `end_time` datetime NOT NULL COMMENT '试卷结束时间',
  `curday` varchar(8) NOT NULL COMMENT '创建日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_allowed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试卷状态，是否可考',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`paper_id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_paper
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_exam_paper_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_paper_config`;
CREATE TABLE `tb_xn_exam_paper_config` (
  `id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '试卷配置关系表主键',
  `paper_id` int(11) NOT NULL COMMENT '试卷ID',
  `type_id` int(11) NOT NULL COMMENT '题型ID',
  `type_num` tinyint(4) NOT NULL COMMENT '题型数量',
  `type_score` tinyint(4) NOT NULL COMMENT '题型分数(每道)',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_paper_config
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_exam_paper_create
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_paper_create`;
CREATE TABLE `tb_xn_exam_paper_create` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '试卷生成关系表ID',
  `paper_id` int(11) NOT NULL COMMENT '试卷ID',
  `type_id` int(11) NOT NULL COMMENT '题型ID',
  `question_id` int(11) NOT NULL COMMENT '题目ID',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_paper_create
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_exam_question
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_question`;
CREATE TABLE `tb_xn_exam_question` (
  `question_id` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '题目ID',
  `type_id` int(11) NOT NULL COMMENT '题型ID',
  `question_title` varchar(255) NOT NULL COMMENT '题目标题',
  `optionA` varchar(100) DEFAULT NULL COMMENT '选择A',
  `optionB` varchar(100) DEFAULT NULL COMMENT '选择B',
  `optionC` varchar(100) DEFAULT NULL COMMENT '选择C',
  `optionD` varchar(100) DEFAULT NULL COMMENT '选择D',
  `answer` varchar(500) NOT NULL COMMENT '正确答案',
  `curday` varchar(8) NOT NULL COMMENT '创建日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `question_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '题目状态，是否可分配',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`question_id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_question
-- ----------------------------
INSERT INTO `tb_xn_exam_question` VALUES ('1', '3', 'String 是最基本的数据类型吗？', null, null, null, null, '不是。Java中的基本数据类型只有8个：byte、short、int、long、float、double、char、boolean；除了基本类型（primitive type），剩下的都是引用类型（reference type），Java 5以后引入的枚举类型也算是一种比较特殊的引用类型。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('2', '3', 'int和Integer有什么区别？', null, null, null, null, 'Java是一个近乎纯洁的面向对象编程语言，但是为了编程的方便还是引入了基本数据类型，但是为了能够将这些基本数据类型当成对象操作，Java为每一个基本数据类型都引入了对应的包装类型（wrapper class），int的包装类就是Integer，从Java 5开始引入了自动装箱/拆箱机制，使得二者可以相互转换。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('3', '3', '用最有效率的方法计算2乘以8？ ', null, null, null, null, ' 2 << 3（左移3位相当于乘以2的3次方，右移3位相当于除以2的3次方）。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('4', '3', '数组有没有length()方法？String有没有length()方法？ ', null, null, null, null, '数组没有length()方法，有length 的属性。String 有length()方法。JavaScript中，获得字符串的长度是通过length属性得到的，这一点容易和Java混淆。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('5', '3', '阐述静态变量和实例变量的区别。 ', null, null, null, null, '静态变量是被static修饰符修饰的变量，也称为类变量，它属于类，不属于类的任何一个对象，一个类不管创建多少个对象，静态变量在内存中有且仅有一个拷贝；实例变量必须依存于某一实例，需要先创建对象然后通过对象才能访问到它。静态变量可以实现让多个对象共享内存。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('6', '3', '是否可以从一个静态（static）方法内部发出对非静态（non-static）方法的调用？', null, null, null, null, '不可以，静态方法只能访问静态成员，因为非静态方法的调用要先创建对象，在调用静态方法时可能对象并没有被初始化。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('7', '3', 'Error和Exception有什么区别？', null, null, null, null, 'Error表示系统级的错误和程序不必处理的异常，是恢复不是不可能但很困难的情况下的一种严重问题；比如内存溢出，不可能指望程序能处理这样的情况；Exception表示需要捕捉或者需要程序进行处理的异常，是一种设计或实现问题；也就是说，它表示如果程序运行正常，从不会发生的情况。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('8', '3', '列出一些你常见的运行时异常？ ', null, null, null, null, '- ArithmeticException（算术异常） - ClassCastException （类转换异常） - IllegalArgumentException （非法参数异常） - IndexOutOfBoundsException （下标越界异常） - NullPointerException （空指针异常） - SecurityException （安全异常）', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('9', '3', 'List、Set、Map是否继承自Collection接口？ ', null, null, null, null, 'List、Set 是，Map 不是。Map是键值对映射容器，与List和Set有明显的区别，而Set存储的零散的元素且不允许有重复元素（数学中的集合也是如此），List是线性结构的容器，适用于按数值索引访问元素的情形。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('10', '3', 'Collection和Collections的区别？', null, null, null, null, 'Collection是一个接口，它是Set、List等容器的父接口；Collections是个一个工具类，提供了一系列的静态方法来辅助容器操作，这些方法包括对容器的搜索、排序、线程安全化等等。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('11', '3', 'Java中如何实现序列化，有什么意义？ ', '', '', '', '', '序列化就是一种用来处理对象流的机制，所谓对象流也就是将对象的内容进行流化。可以对流化后的对象进行读写操作，也可将流化后的对象传输于网络之间。序列化是为了解决对象流读写操作时可能引发的问题（如果不进行序列化可能会存在数据乱序的问题）。', '20180107', '2018-01-07 15:54:35', '2018-01-10 14:59:37', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('12', '3', 'JDBC能否处理Blob和Clob？', null, null, null, null, 'Blob是指二进制大对象（Binary Large Object），而Clob是指大字符对象（Character Large Objec），因此其中Blob是为存储大的二进制数据而设计的，而Clob是为存储大的文本数据而设计的。', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('13', '3', 'Java中是如何支持正则表达式操作的？ ', null, null, null, null, 'Java中的String类提供了支持正则表达式操作的方法，包括：matches()、replaceAll()、replaceFirst()、split()。此外，Java中可以用Pattern类表示正则表达式对象，它提供了丰富的API进行各种正则表达式操作', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('14', '3', '获得一个类的类对象有哪些方式？', null, null, null, null, '- 方法1：类型.class，例如：String.class - 方法2：对象.getClass()，例如：\"hello\".getClass() - 方法3：Class.forName()，例如：Class.forName(\"java.lang.String\")', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('15', '3', '什么是UML？', '', '', '', '', 'UML是统一建模语言（Unified Modeling Language）的缩写，它发表于1997年，综合了当时已经存在的面向对象的建模语言、方法和过程，是一个支持模型化和软件系统开发的图形化语言，为软件开发的所有阶段提供模型化和可视化支持。', '20180107', '2018-01-07 15:54:35', '2018-01-10 15:00:23', '0', '0', '数据库');
INSERT INTO `tb_xn_exam_question` VALUES ('16', '1', '下列说法正确的是 ', 'A.JAVA程序的main方法必须写在类里面', 'B.JAVA程序中可以有多个main方法', 'C.JAVA程序中类名必须与文件名一样', 'D.JAVA程序的main方法中如果只有一条语句，可以不用{}(大括号)括', 'A', '20180107', '2018-01-07 15:54:35', '2018-01-10 15:00:10', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('17', '1', '变量命名规范说法正确的是 ', 'A.变量由字母、下划线、数字、$符号随意组成；', 'B.变量不能以数字作为开头；', 'C.A和a在java中是同一个变量；', 'D.不同类型的变量，可以起相同的名字；', 'B', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('18', '1', '表达式(11+3*8)/4%3的值是', 'A.31', 'B.0', 'C.1', 'D.2', 'D', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('19', '1', '下面有关for循环的描述正确的是', 'A.for循环体语句中，可以包含多条语句，但要用大括号括起来', 'B.for循环只能用于循环次数已经确定的情况 ', 'C.在for循环中，不能使用break语句跳出循环 ', 'D.for循环是先执行循环体语句，后进行条件判断', 'A', '20180107', '2018-01-07 15:54:35', '2018-01-10 14:59:49', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('20', '1', '在Java中,关于构造方法，下列说法错误的是', 'A.构造方法的名称必须与类名相同 ', 'B.构造方法可以带参数', 'C.构造方法不可以重载', 'D.构造方法绝对不能有返回值', 'C', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('21', '1', '在Java语言中，下列关于类的继承的描述，正确的是', 'A.一个类可以继承多个父类', 'B.一个类可以具有多个子类', 'C.子类可以使用父类的所有方法', 'D.子类一定比父类有更多的成员方法', 'B', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('22', '1', '下列选项中关于Java中super关键字的说法正确的是 ', 'A.super关键字是在子类对象内部指代其父类对象的引用', 'B.super关键字不仅可以指代子类的直接父类，还可以指代父类的父类', 'C.子类通过super关键字只能调用父类的方法，而不能调用父类的属性', 'D.子类通过super关键字只能调用父类的属性，而不能调用父类的方法', 'A', '20180107', '2018-01-07 15:54:35', '2018-01-10 14:59:29', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('23', '1', '下列选项中关于Java中封装的说法错误的是', 'A.封装就是将属性私有化，提供共有的方法访问私有属性', 'B.属性的访问方法包括setter方法和getter方法', 'C.setter方法用于赋值，getter方法用于取值', 'D.包含属性的类都必须封装属性，否则无法通过编译', 'D', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('24', '1', '分析选项中关于Java中this关键字的说法正确的是 ', 'A.this关键字是在对象内部指代自身的引用', 'B.this关键字可以在类中的任何位置使用', 'C.this关键字和类关联，而不是和特定的对象关联', 'D.同一个类的不同对象共用一个this', 'A', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('25', '1', '关于面向对象的说法正确的是', 'A.类可以让我们用程序模拟现实世界中的实体', 'B.有多少个实体就要创建多少个类', 'C.对象的行为和属性被封装在类中，外界通过调用类的方法来获得，', 'D.现实世界中的某些实体不能用类来描述', 'A', '20180107', '2018-01-07 15:54:35', '2018-01-07 15:54:35', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('26', '1', '有关类的说法，正确的是', 'A.类具有封装性，所以类的数据是不能被访问的 ', 'B.类具有封装性，但可以通过类的公共接口访问类中的数据  ', 'C.声明一个类时，必须使用 public 修饰符 ', 'D.每个类中必须有 main 方法，否则程序无法运行', 'B', '20180107', '2018-01-07 15:54:35', '2018-01-10 15:00:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('27', '2', '关于类的描叙正确的是', 'A.在类中定义的变量称为类的成员变量，在别的类中可以直接使用 ', 'B.局部变量的作用范围仅仅在定义它的方法内，或者是在定义它的控  ', 'C.使用别的类的方法仅仅需要引用方法的名字即可', 'D.一个类的方法使用该类的另一个方法时可以直接引用方法名', 'BD', '20180107', '2018-01-07 15:54:36', '2018-01-10 14:59:10', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('28', '2', '下列关于字符串的描叙中错误的是 ', 'A.字符串是对象 ', 'B.String对象存储字符串的效率比StringBuffer高', 'C.可以使用StringBuffer sb=\"这里是字符串\"声明并初始化StringBuffer ', 'D.String类提供了许多用来操作字符串的方法：连接，提取，查询等', 'BC', '20180107', '2018-01-07 15:54:36', '2018-01-16 10:28:58', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('29', '2', '下面关于数组的说法中，错误的是 ', 'A.在类中声明一个整数数组作为成员变量，如果没有给它赋值，数值 ', 'B.数组可以在内存空间连续存储任意一组数据 ', 'C.数组必须先声明，然后才能使用', 'D.数组本身是一个对象', 'AB', '20180107', '2018-01-07 15:54:36', '2018-01-10 14:59:22', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('30', '2', '在Java中，下面对于构造函数的描述错误的是', 'A.类不一定要显式定义构造函数 ', 'B.构造函数的返回类型是void ', 'C.如果构造函数不带任何参数，那么构造函数的名称和类名可以不同', 'D.一个类可以定义多个构造函数', 'BC', '20180107', '2018-01-07 15:54:36', '2018-01-10 14:59:04', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('31', '2', '在Java接口中，下列选项中有效的方法声明是 ', 'A.public void aMethod()', 'B. void aMethod() ', 'C.protected void aMethod() ', 'D.private void aMethod() ', 'AB', '20180107', '2018-01-07 15:54:36', '2018-01-09 14:48:56', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('35', '2', '如下哪些不是java的关键字？', 'A.const', 'B.NULL', 'C.false', 'D.this', 'BC', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('36', '2', '有关线程的哪些叙述是对的？', 'A.一旦一个线程被创建，它就立即开始运行。', 'B.使用start()方法可以使一个线程成为可运行的，但是它不一定立即开始运行。', 'C.当一个线程因为抢先机制而停止运行，它被放在可运行队列的前面。', 'D.一个线程可能因为不同的原因停止并进入就绪状态。', 'BCD', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('37', '2', '下面的哪些声明是合法的？', 'A.long 1 = 499 ', 'B.int i = 4L', 'C.float f =1.1', 'D.double d = 34.4', 'AD', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('38', '2', '从下列选项中选择正确的Java表达式', 'A.int k=new String(“aa”) ', 'B.String str=String(“bb”)', 'C.char c=74', 'D.long j=8888', 'BCD', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('39', '2', '关于Java语言，下列描述正确的是', 'A.switch 不能够作用在String类型上', 'B.List， Set， Map都继承自Collection接口', 'C.Java语言支持goto语句', 'D.GC是垃圾收集器，程序员不用担心内存管理', 'AD', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('43', '2', '不能用来修饰interface的有', 'A．private', 'B．public ', 'C．protected ', 'D.static', 'ACD', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('44', '2', 'Java网络程序设计中，下列正确的描述是 ', 'A.Java网络编程API建立在Socket基础之上', 'B.Java网络接口只支持TCP以及其上层协议 ', 'C.Java网络接口只支持UDP以及其上层协议', 'D.Java网络接口支持IP以上的所有高层协议', 'AD', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('45', '1', '在字节流中，可以使用文件名作为参数的类有', 'A.DataInputStream ', 'B.BufferedReader ', 'C.FileInputStream ', 'D.FileReader', 'C', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('62', '1', '下面有关for循环的描述正确的是', 'A.for循环体语句中，可以包含多条语句，但要用大括号括起来 ', 'B.for循环只能用于循环次数已经确定的情况', 'C.在for循环中，不能使用break语句跳出循环', 'D.for循环是先执行循环体语句，后进行条件判断 ', 'A', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('63', '1', '在java中下列关于自动类型转换说法正确的是', 'A.基本数据类型和String相加结果一定是字符串型 ', 'B.char类型和int类型相加结果一定是字符 ', 'C. double类型可以自动转换为int ', 'D.char + int + double +\"\" 结果一定是double', 'A', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('64', '1', '文档标签@exception 在文档注释中的格式一般是', 'A.@exception 类名 描述 ', 'B.@exception 异常描述  ', 'C.@exception 异常变量名 描述  ', 'D.不存在文档标签@exception', 'A', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('65', '1', '下面关于内部类的说法，错误的是', 'A.除 static 内部类外，不能在类内声明 static 成员  ', 'B.内部类可用 abstract 修饰定义为抽象类，也可以用 private 或 protected', 'C.内部类可作为其他类的成员，而且可访问它所在类的成员  ', 'D.内部类不能有自己的成员方法和成员变量', 'D', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('66', '1', '设有定义：String s=“World”,下列语句错误的是', 'A.int m=s.indexOf(‘r’)', 'B.char c=s.charAt(0)  ', 'C.int n=s.length()  ', ' D.String str=s.append(‘2’)', 'D', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('67', '1', '作为 Java 应用程序入口的 main 方法，其声明格式可以是', 'A.public static void main(String[] args)  ', 'B.public static int main(String[] args)  ', 'C.public void main(String[] args) ', 'D.public int main(String[] args) ', 'A', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('68', '1', '关于事件监听，正确的是', 'A.一个事件监听器只能监听一个组件 ', 'B.一个事件监听器只能监听处理一种事件 ', 'C.一个组件可以注册多个事件监听器，一个事件监听器也可以注册到多个', ' D.一个组件只能引发一种事件  ', 'C', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('69', '1', '下面关于多态性的说法，正确的是', 'A.一个类中不能有同名的方法 ', 'B.子类中不能有和父类中同名的方法  ', 'C.子类中可以有和父类中同名且参数相同的方法', 'D.多态性就是方法的名字可以一样，但返回的类型必须不一样', 'C', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('70', '1', '当Oracle服务器启动时，下列哪种文件不是必须的', ' A．数据文件', 'B．控制文件', 'C．日志文件 ', 'D．归档日志文件', 'D', '20180107', '2018-01-07 15:54:31', '2018-01-07 15:54:31', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('71', '1', '在Oracle中，当用户要执行SELECT语句时，下列哪个进程从磁盘获得用户需要的数据', 'A．用户进程 ', 'B．服务器进程 ', 'C．日志写入进程', 'D．检查点进程（CKPT）', 'B', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('72', '1', '在以下用户中，哪个用户不能删除？', ' A．一个与数据库连接的用户', 'B．一个没有任何模式对象的用户', 'C．一个带有只读表的用户 ', 'D．在任何时间可以删除所有用户', 'A', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('73', '1', '当查询数据字典DBA_USERS时，这个数据字典将显示什么信息？', 'A．所有用户的表空间份额（quote）', 'B．当前用户的表空间份额', ' C．数据库用户被创建的日期', 'D．当前用户在一个表空间上是否具有无限的份额', 'A', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('74', '1', '要显示概要文件DOG_PROJECT的资源限制信息，请问应该查询如下哪一个数据字典？', 'A．DBA_USERS', 'B．DBA_TABLES ', 'C．DBA_OBJECTS', 'D．DBA_PROFILES', 'D', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('75', '1', '在以下的数据字典的视图中，将查询哪一个以显示每个用户所使用的磁盘空间？', ' A．ALL_USERS', 'B．DBA_USERS ', 'C．USER_USERS', 'D．DBA_TS_USERS', 'D', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('76', '1', '在Oracle中，下面哪条语句当COMM字段为空时显示0，不为空时显示COMM的值', 'A．SELECT？ename,？NVL(comm,？0)？FROM？emp？', 'B．SELECT？ename,？NULL(comm,？0)？FROM？emp？', 'C．SELECT？ename,？NULLIF(comm,？0)？FROM？emp？', 'D．SELECT？ename,？DECODE(comm,？NULL,？0)？FROM？emp？', 'A', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('77', '1', '在Oracle中，下面用于限制分组函数的返回值的子句是', 'A．WHERE', 'B．HAVING', 'C．ORDER BY', 'D．无法限定分组函数的返回值 ', 'B', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('78', '1', '在Oracle中，有一个名为seq的序列对象，以下语句能返回序列值但不会引起序列值增加的是', 'A．select seq.ROWNUM from dual', 'B．select seq.ROWID from dual', 'C．select seq.CURRVAL from dual', 'D．select seq.NEXTVAL from dual', 'C', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('79', '1', '如果要创建一个名为WUDA的数据库，则需要什么？', 'A．一个控制文件', 'B．系统（SYSTEM）表空间', 'C．WUDA数据库中的一个用户名', 'D．一个具有所需全部权限的合法账户', 'D', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('80', '1', '看SQL语句“SELECT name,status FROM v$controlfile”这一语句将显示以下哪个结果？', 'A．显示MAXDATAFILE的值', 'B．确定最后一个检查点所发生的时间', 'C．显示所有数据文件的名字和状态', 'D．显示所有控制文件的个数、名字、状态和位置', 'D', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('81', '3', 'JDK和JRE的区别是什么？', null, null, null, null, 'Java运行时环境(JRE)是将要执行Java程序的Java虚拟机。它同时也包含了执行applet需要的浏览器插件。Java开发工具包(JDK)是完整的Java软件开发包，包含了JRE，编译器和其他的工具(比如：JavaDoc，Java调试器)，可以让开发者开发、编译、执行Java应用程序。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('82', '3', '”static”关键字是什么意思？Java中是否可以覆盖(override)一个private或者是static的方法？', null, null, null, null, '“static”关键字表明一个成员变量或者是成员方法可以在没有所属的类的实例变量的情况下被访问。Java中static方法不能被覆盖，因为方法覆盖是基于运行时动态绑定的，而static方法是编译时静态绑定的。static方法跟类的任何实例都不相关，所以概念上不适用。', '20180107', '2018-01-07 15:54:34', '2018-01-07 15:54:34', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('83', '3', 'Java中的方法覆盖(Overriding)和方法重载(Overloading)是什么意思？', null, null, null, null, 'Java中的方法重载发生在同一个类里面两个或者是多个方法的方法名相同但是参数不同的情况。与此相对，方法覆盖是说子类重新定义了父类的方法。方法覆盖必须有相同的方法名，参数列表和返回类型。覆盖者可能不会限制它所覆盖的方法的访问。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('84', '3', '什么是值传递和引用传递？', null, null, null, null, '对象被值传递，意味着传递了对象的一个副本。因此，就算是改变了对象副本，也不会影响源对象的值。对象被引用传递，意味着传递的并不是实际的对象，而是对象的引用。因此，外部对引用对象所做的改变会反映到所有的对象上。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('85', '3', '进程和线程的区别是什么？', null, null, null, null, '进程是执行着的应用程序，而线程是进程内部的一个执行序列。一个进程可以有多个线程。线程又叫做轻量级进程。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('86', '3', '同步方法和同步代码块的区别是什么？', null, null, null, null, '在Java语言中，每一个对象有一把锁。线程可以使用synchronized关键字来获取对象上的锁。synchronized关键字可应用在方法级别(粗粒度锁)或者是代码块级别(细粒度锁)。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('87', '3', '在监视器(Monitor)内部，是如何做线程同步的？程序应该做哪种级别的同步？', null, null, null, null, '监视器和锁在Java虚拟机中是一块使用的。监视器监视一块同步代码块，确保一次只有一个线程执行同步代码块。每一个监视器都和一个对象引用相关联。线程在获取锁之前不允许执行同步代码。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('88', '3', '为什么集合类没有实现Cloneable和Serializable接口？', null, null, null, null, '克隆(cloning)或者是序列化(serialization)的语义和含义是跟具体的实现相关的。因此，应该由集合类的具体实现来决定如何被克隆或者是序列化。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('90', '3', 'hashCode()和equals()方法的重要性体现在什么地方？', null, null, null, null, 'Java中的HashMap使用hashCode()和equals()方法来确定键值对的索引，当根据键获取值的时候也会用到这两个方法。如果没有正确的实现这两个方法，两个不同的键可能会有相同的hash值，因此，可能会被集合认为是相等的。而且，这两个方法也用来发现重复元素。所以这两个方法的实现对HashMap的精确性和正确性是至关重要的。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('93', '3', 'Comparable和Comparator接口是干什么的？列出它们的区别。', null, null, null, null, 'Java提供了只包含一个compareTo()方法的Comparable接口。这个方法可以个给两个对象排序。具体来说，它返回负数，0，正数来表明输入对象小于，等于，大于已经存在的对象。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('94', '3', 'HashSet和TreeSet有什么区别？', null, null, null, null, 'HashSet是由一个hash表来实现的，因此，它的元素是无序的。add()，remove()，contains()方法的时间复杂度是O(1)。另一方面，TreeSet是由一个树形的结构来实现的，它里面的元素是有序的。因此，add()，remove()，contains()方法的时间复杂度是O(logn)。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('95', '3', 'Java中垃圾回收有什么目的？什么时候进行垃圾回收？', null, null, null, null, '垃圾回收的目的是识别并且丢弃应用不再使用的对象来释放和重用资源。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('96', '3', 'finalize()方法什么时候被调用？析构函数(finalization)的目的是什么？', null, null, null, null, '在释放对象占用的内存之前，垃圾收集器会调用对象的finalize()方法。一般建议在该方法中释放对象持有的资源。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('98', '3', 'Java中的两种异常类型是什么？他们有什么区别？', null, null, null, null, 'Java中有两种异常：受检查的(checked)异常和不受检查的(unchecked)异常。不受检查的异常不需要在方法或者是构造函数上声明，就算方法或者是构造函数的执行可能会抛出这样的异常，并且不受检查的异常可以传播到方法或者是构造函数的外面。相反，受检查的异常必须要用throws语句在方法或者是构造函数上声明。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('99', '3', 'Java中Exception和Error有什么区别？', null, null, null, null, 'Exception和Error都是Throwable的子类。Exception用于用户程序可以捕获的异常情况。Error定义了不期望被用户程序捕获的异常。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('100', '3', 'throw和throws有什么区别？', null, null, null, null, 'throw关键字用来在程序中明确的抛出异常，相反，throws语句用来表明方法不能处理的异常。每一个方法都必须要指定哪些异常不能处理，所以方法的调用者才能够确保处理可能发生的异常，多个异常是用逗号分隔的。', '20180107', '2018-01-07 15:54:32', '2018-01-07 15:54:32', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('101', '3', '异常处理的时候，finally代码块的重要性是什么？', null, null, null, null, '无论是否抛出异常，finally代码块总是会被执行。就算是没有catch语句同时又抛出异常的情况下，finally代码块仍然会被执行。最后要说的是，finally代码块主要用来释放资源，比如：I/O缓冲区，数据库连接。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('102', '3', 'finally代码块和finalize()方法有什么区别？', null, null, null, null, '无论是否抛出异常，finally代码块都会执行，它主要是用来释放应用占用的资源。finalize()方法是Object类的一个protected方法，它是在对象被垃圾回收之前由Java虚拟机来调用的。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');
INSERT INTO `tb_xn_exam_question` VALUES ('103', '3', 'Applet和普通的Java应用程序有什么区别？', null, null, null, null, 'applet是运行在启用了java的浏览器中，Java应用程序是可以在浏览器之外运行的独立的Java程序。但是，它们都需要有Java虚拟机。进一步来说，Java应用程序需要一个有特定方法签名的main函数来开始执行。Java applet不需要这样的函数来开始执行。', '20180107', '2018-01-07 15:54:33', '2018-01-07 15:54:33', '0', '0', 'Java开发');

-- ----------------------------
-- Table structure for tb_xn_exam_score
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_score`;
CREATE TABLE `tb_xn_exam_score` (
  `score_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `exam_paper_id` int(11) NOT NULL COMMENT '考试用户ID',
  `paper_id` int(11) NOT NULL COMMENT '试卷ID',
  `single_score` tinyint(4) NOT NULL COMMENT '单选题分数',
  `multiple_score` tinyint(4) NOT NULL COMMENT '多选题分数',
  `brief_score` tinyint(4) NOT NULL DEFAULT '0' COMMENT '简答题分数',
  `curday` varchar(8) NOT NULL COMMENT '创建日期',
  `submit_time` datetime NOT NULL COMMENT '交卷时间,即创建时间',
  `paper_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '试卷状态，0代表未考，1代表已考，2代表已批阅',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`score_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_score
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_exam_solution
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_solution`;
CREATE TABLE `tb_xn_exam_solution` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户答题关系表主键ID',
  `paper_id` int(11) NOT NULL COMMENT '试卷ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `question_id` int(11) NOT NULL COMMENT '题目ID',
  `user_solution` varchar(500) NOT NULL COMMENT '用户答案',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_solution
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_exam_type
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_exam_type`;
CREATE TABLE `tb_xn_exam_type` (
  `type_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '题型ID',
  `type_name` varchar(20) NOT NULL COMMENT '题型名',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `remark` varchar(20) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`type_id`),
  KEY `is_ok` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_exam_type
-- ----------------------------
INSERT INTO `tb_xn_exam_type` VALUES ('1', '单选题', '2018-01-07 15:37:42', '2018-01-07 15:37:45', '0', '单选题');
INSERT INTO `tb_xn_exam_type` VALUES ('2', '多选题', '2018-01-07 15:38:13', '2018-01-07 15:38:15', '0', '多选题');
INSERT INTO `tb_xn_exam_type` VALUES ('3', '简答题', '2018-01-07 15:38:28', '2018-01-07 15:38:31', '0', '简答题');

-- ----------------------------
-- Table structure for tb_xn_sys_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_admin_log`;
CREATE TABLE `tb_xn_sys_admin_log` (
  `log_id` varchar(50) NOT NULL COMMENT '日志ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `user_name` varchar(50) NOT NULL COMMENT '用户姓名',
  `log_type` int(4) NOT NULL COMMENT '日志类型',
  `log_ip` varchar(50) NOT NULL COMMENT '日志记录IP',
  `log_name` varchar(50) NOT NULL COMMENT '操作名称',
  `log_content` varchar(255) NOT NULL COMMENT '操作内容',
  `log_result` varchar(50) NOT NULL COMMENT '操作结果',
  `curday` int(8) NOT NULL COMMENT '当前日期',
  `log_time` datetime NOT NULL COMMENT '日志时间',
  `log_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '日志时间戳',
  `is_ok` int(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_admin_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_file`;
CREATE TABLE `tb_xn_sys_file` (
  `file_id` varchar(255) NOT NULL COMMENT '文件ID',
  `file_name` varchar(255) NOT NULL COMMENT '文件名',
  `file_type` tinyint(4) NOT NULL COMMENT '文件类型',
  `file_path` varchar(255) NOT NULL COMMENT '文件存储位置',
  `upload_by` int(8) NOT NULL COMMENT '上传人',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `curday` int(8) NOT NULL COMMENT '创建日期',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_sys_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_log`;
CREATE TABLE `tb_xn_sys_log` (
  `log_id` varchar(50) NOT NULL COMMENT '主键ID(UUID)',
  `user_id` int(11) NOT NULL COMMENT '请求人ID',
  `request_ip` varchar(100) NOT NULL COMMENT '请求IP',
  `request_method` varchar(255) NOT NULL COMMENT '请求方法名',
  `request_class` varchar(255) NOT NULL COMMENT '请求类',
  `curday` varchar(8) NOT NULL COMMENT '创建日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_xn_sys_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_module`;
CREATE TABLE `tb_xn_sys_module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '模块ID',
  `module_name` varchar(50) NOT NULL COMMENT '模块名称',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级模块ID',
  `module_level` tinyint(1) NOT NULL COMMENT '菜单级别',
  `action_url` varchar(100) DEFAULT NULL COMMENT '访问URL',
  `is_allowed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否可分配(0:可以,1:不可以)',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效(0:有效,1:无效)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`module_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `selected` (`parent_id`,`module_level`,`is_allowed`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_module
-- ----------------------------
INSERT INTO `tb_xn_sys_module` VALUES ('1', '系统管理', '0', '1', '', '0', '0', '2017-11-28 16:58:17', '2018-01-07 14:49:43', '系统管理');
INSERT INTO `tb_xn_sys_module` VALUES ('2', '用户管理', '1', '2', '/system/user/showAllUser.do', '0', '0', '2017-11-28 16:58:17', '2018-01-07 14:49:53', '用户管理');
INSERT INTO `tb_xn_sys_module` VALUES ('3', '角色管理', '1', '2', '/system/role/showAllRole.do', '0', '0', '2017-11-28 16:58:17', '2018-01-07 14:49:31', '角色管理');
INSERT INTO `tb_xn_sys_module` VALUES ('4', '模块管理', '1', '2', '/system/module/showAllModule.do', '0', '0', '2017-11-28 16:58:17', '2018-01-07 14:42:09', '模块管理');
INSERT INTO `tb_xn_sys_module` VALUES ('10', '账务管理', '0', '1', '', '0', '0', '2017-12-01 09:15:31', '2018-01-07 14:49:18', '账务管理');
INSERT INTO `tb_xn_sys_module` VALUES ('12', '个人账务分类管理', '10', '2', '/account/type/showPersonalType.do', '0', '0', '2017-12-01 17:17:41', '2018-01-07 14:48:53', '个人账务分类管理');
INSERT INTO `tb_xn_sys_module` VALUES ('13', '个人账务管理', '10', '2', '/account/account/showPersonalAccount.do', '0', '0', '2017-12-04 10:24:29', '2018-01-07 14:49:06', '个人账务管理');
INSERT INTO `tb_xn_sys_module` VALUES ('14', '数据统计', '0', '1', '', '0', '0', '2018-01-03 10:47:49', '2018-01-07 14:48:42', '数据统计');
INSERT INTO `tb_xn_sys_module` VALUES ('15', '总账务统计', '14', '2', '/account/account/showAllAccount.do', '0', '0', '2018-01-03 10:48:32', '2018-01-07 14:48:33', '总账务统计');
INSERT INTO `tb_xn_sys_module` VALUES ('16', '总资产统计', '14', '2', '/account/money/showAllMoney.do', '0', '0', '2018-01-03 10:49:00', '2018-01-09 09:47:53', '总资产统计');
INSERT INTO `tb_xn_sys_module` VALUES ('17', '考试管理', '0', '1', '', '0', '0', '2018-01-07 14:41:53', '2018-01-07 14:41:53', '考试管理');
INSERT INTO `tb_xn_sys_module` VALUES ('18', '题型管理', '17', '2', '/exam/type/showAllType.do', '0', '0', '2018-01-07 14:51:17', '2018-01-07 15:01:17', '题型管理');
INSERT INTO `tb_xn_sys_module` VALUES ('19', '题库管理', '17', '2', '/exam/question/showAllQuestion.do', '0', '0', '2018-01-07 14:52:30', '2018-01-07 14:52:30', '题库管理');
INSERT INTO `tb_xn_sys_module` VALUES ('20', '试卷管理', '17', '2', '/exam/paper/showAllPaper.do', '0', '0', '2018-01-07 14:53:15', '2018-01-08 12:27:18', '试卷管理');
INSERT INTO `tb_xn_sys_module` VALUES ('24', '在线考试', '0', '1', null, '0', '0', '2018-01-17 15:08:03', '2018-01-17 15:31:10', '在线考试');
INSERT INTO `tb_xn_sys_module` VALUES ('25', '所有试卷', '24', '2', '/exam/exam/showAllClientPaper.do', '0', '0', '2018-01-17 15:28:53', '2018-01-17 15:28:53', '所有试卷');
INSERT INTO `tb_xn_sys_module` VALUES ('26', '批阅试卷', '17', '2', '/exam/score/showAllScore.do', '0', '0', '2018-01-17 15:31:01', '2018-01-19 13:45:00', '批阅试卷');
INSERT INTO `tb_xn_sys_module` VALUES ('27', '已考试卷', '24', '2', '/exam/score/showPersonalScore.do', '0', '0', '2018-01-17 15:32:08', '2018-01-17 15:32:08', '已考试卷');
INSERT INTO `tb_xn_sys_module` VALUES ('28', '渠道数据', '14', '2', '/data/channel/showAllChannelData.do', '1', '0', '2018-01-22 11:56:06', '2018-04-15 18:10:03', '渠道数据');
INSERT INTO `tb_xn_sys_module` VALUES ('29', '日志管理', '33', '2', '/system/log/showAllLog.do', '1', '0', '2018-01-23 15:27:03', '2018-11-08 13:16:53', '日志管理');
INSERT INTO `tb_xn_sys_module` VALUES ('30', '系统日志', '33', '2', '/system/log/showAllAdminLog.do', '0', '0', '2018-01-23 15:27:03', '2018-11-08 13:18:40', '系统日志');
INSERT INTO `tb_xn_sys_module` VALUES ('31', '文件管理', '33', '2', '/system/file/showAllFile.do', '0', '0', '2018-01-23 15:27:03', '2018-11-08 13:13:24', '文件管理');
INSERT INTO `tb_xn_sys_module` VALUES ('32', '数据字典', '33', '2', '/data/dic/showAllDataDic.do', '0', '0', '2018-11-08 10:47:56', '2018-11-08 13:21:03', '数据字典');
INSERT INTO `tb_xn_sys_module` VALUES ('33', '功能管理', '0', '1', null, '0', '0', '2018-11-08 13:13:06', '2018-11-08 13:13:06', '功能管理');

-- ----------------------------
-- Table structure for tb_xn_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_role`;
CREATE TABLE `tb_xn_sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_role
-- ----------------------------
INSERT INTO `tb_xn_sys_role` VALUES ('1', '超级管理员', '0', '2017-11-28 16:47:04', '2018-11-08 13:17:44', '超级管理员');

-- ----------------------------
-- Table structure for tb_xn_sys_role_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_role_module`;
CREATE TABLE `tb_xn_sys_role_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `module_id` int(11) NOT NULL COMMENT '模块ID',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `isDeleted` (`is_ok`)
) ENGINE=InnoDB AUTO_INCREMENT=439 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_role_module
-- ----------------------------
INSERT INTO `tb_xn_sys_role_module` VALUES ('417', '1', '1', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('418', '1', '10', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('419', '1', '14', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('420', '1', '17', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('421', '1', '24', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('422', '1', '33', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('423', '1', '2', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('424', '1', '3', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('425', '1', '4', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('426', '1', '12', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('427', '1', '13', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('428', '1', '15', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('429', '1', '16', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('430', '1', '18', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('431', '1', '19', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('432', '1', '20', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('433', '1', '25', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('434', '1', '26', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('435', '1', '27', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('436', '1', '30', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('437', '1', '31', '0');
INSERT INTO `tb_xn_sys_role_module` VALUES ('438', '1', '32', '0');

-- ----------------------------
-- Table structure for tb_xn_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_xn_sys_user`;
CREATE TABLE `tb_xn_sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_name` varchar(50) NOT NULL COMMENT '账号名称',
  `user_pwd` varchar(50) NOT NULL COMMENT '账号密码',
  `user_face` varchar(50) DEFAULT NULL COMMENT '用户头像,可为空',
  `user_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '用户状态：0：启用，1：禁用',
  `is_ok` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  KEY `isDeleted` (`is_ok`),
  KEY `login` (`user_name`(10),`user_pwd`(10))
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_xn_sys_user
-- ----------------------------
INSERT INTO `tb_xn_sys_user` VALUES ('1', '1', 'admin', '0173701f7d981e89c8c1009eaca38c3b', 'f4244d737d7c4e049aab2fb5d68afd3a.jpg', '0', '0', '2017-11-28 18:06:19', '2018-04-15 18:18:18', '超级管理员');
