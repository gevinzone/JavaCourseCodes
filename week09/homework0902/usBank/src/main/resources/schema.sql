CREATE TABLE `account` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `username` varchar(64) NOT NULL,
                           `balance` decimal(10,0) NOT NULL DEFAULT '0',
                           `frozen` decimal(10,0) NOT NULL DEFAULT '0',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `account_username_idx` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `account_change_log` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `business_id` int NOT NULL DEFAULT '0',
                           `user_id` int NOT NULL DEFAULT '0',
                           `amount` decimal(10,0) NOT NULL DEFAULT '0',
                           `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           `status` tinyint NOT NULL DEFAULT '0',
                           PRIMARY KEY (`id`),
                           UNIQUE KEY `pay_log_business_id_idx` (`business_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;