CREATE TABLE `account_transfer_detail` (
                                           `id` int NOT NULL AUTO_INCREMENT,
                                           `from_id` varchar(64) NOT NULL,
                                           `to_id` varchar(64) DEFAULT NULL,
                                           `amount_us` decimal(10,0) DEFAULT NULL,
                                           `amount_cny` decimal(10,0) DEFAULT NULL,
                                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                           `status` int DEFAULT NULL,
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


