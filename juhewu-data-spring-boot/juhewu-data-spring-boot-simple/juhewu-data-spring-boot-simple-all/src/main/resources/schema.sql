DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
    `id` int NOT NULL AUTO_INCREMENT,
    `chinese_name` varchar(50) DEFAULT NULL,
    `id_card` varchar(50) DEFAULT NULL,
    `fixed_phone` varchar(50) DEFAULT NULL,
    `mobile_phone` varchar(50) DEFAULT NULL,
    `address` varchar(50) DEFAULT NULL,
    `email` varchar(50) DEFAULT NULL,
    `bank_card` varchar(50) DEFAULT NULL,
    `password` varchar(50) DEFAULT NULL,
    `key` varchar(50) DEFAULT NULL,
    `test` varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

SET FOREIGN_KEY_CHECKS = 1;
