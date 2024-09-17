DROP TABLE IF EXISTS `db_rvc`.`tb_cmd_record`;
CREATE TABLE `db_rvc`.`tb_cmd_record`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `vin`          VARCHAR(20) NOT NULL COMMENT '车架号',
    `cmd_id`       VARCHAR(64) NOT NULL COMMENT '指令ID',
    `cmd_type`     VARCHAR(50) NOT NULL COMMENT '指令类型',
    `cmd_param`    JSON                 DEFAULT NULL COMMENT '指令参数',
    `cmd_state`    SMALLINT    NOT NULL COMMENT '指令状态',
    `failure_code` INT                  DEFAULT NULL COMMENT '失败代码',
    `start_time`   TIMESTAMP            DEFAULT NULL COMMENT '开始时间',
    `end_time`     TIMESTAMP            DEFAULT NULL COMMENT '结束时间',
    `account_type` VARCHAR(50)          DEFAULT NULL COMMENT '账号类型',
    `account_id`   VARCHAR(64)          DEFAULT NULL COMMENT '账号ID',
    `province`     VARCHAR(50)          DEFAULT NULL COMMENT '省',
    `city`         VARCHAR(50)          DEFAULT NULL COMMENT '市',
    `county`       VARCHAR(50)          DEFAULT NULL COMMENT '区',
    `description`  VARCHAR(255)         DEFAULT NULL COMMENT '备注',
    `create_time`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`    VARCHAR(64)          DEFAULT NULL COMMENT '创建者',
    `modify_time`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    `modify_by`    VARCHAR(64)          DEFAULT NULL COMMENT '修改者',
    `row_version`  INT                  DEFAULT 1 COMMENT '记录版本',
    `row_valid`    TINYINT              DEFAULT 1 COMMENT '记录是否有效',
    PRIMARY KEY (`id`),
    UNIQUE KEY (`cmd_id`),
    INDEX `idx_vin` (`vin`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='远控命令记录表';