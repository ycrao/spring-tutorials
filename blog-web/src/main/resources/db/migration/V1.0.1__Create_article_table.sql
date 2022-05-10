CREATE TABLE `article` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL COMMENT '用户id',
    # `category_id` bigint NOT NULL DEFAULT 0 COMMENT '分类id',
    `content` text NOT NULL COMMENT '正文',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;