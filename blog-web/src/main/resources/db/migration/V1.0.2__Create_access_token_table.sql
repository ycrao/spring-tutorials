CREATE TABLE `access_token` (
   `id` bigint unsigned NOT NULL AUTO_INCREMENT,
   `user_id` bigint NOT NULL COMMENT '用户id',
   `token` varchar(60) NOT NULL COMMENT 'token',
   `expired_at` int(11) NOT NULL DEFAULT  0 COMMENT '过期那一刻时间戳',
   `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   UNIQUE KEY `token_unique` (`token`) USING BTREE,
   KEY `user_token` (`user_id`,`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;