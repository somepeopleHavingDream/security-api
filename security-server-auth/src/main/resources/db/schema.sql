create table oauth_client_details (
  client_id VARCHAR(256) PRIMARY KEY comment '客户端Id',
  resource_ids VARCHAR(256) comment '客户端能够访问的资源Id',
  client_secret VARCHAR(256) comment '客户端的密钥',
  scope VARCHAR(256) comment '客户端能够访问的范围',
  authorized_grant_types VARCHAR(256) comment '授权码模式，密码模式，刷新token',
  web_server_redirect_uri VARCHAR(256) comment '授权地址',
  authorities VARCHAR(256) comment '授予的权限',
  access_token_validity INTEGER comment '令牌访问时间',
  refresh_token_validity INTEGER comment '刷新令牌有效期',
  additional_information VARCHAR(4096) comment '添加的条件',
  autoapprove VARCHAR(256) comment '自动授权'
) comment 'oauth 客户端详情表';

create table oauth_client_token (
  token_id VARCHAR(256) comment '令牌Id',
  token BLOB comment '令牌',
  authentication_id VARCHAR(256) PRIMARY KEY comment '对应的认证',
  user_name VARCHAR(256) comment '授权的用户名',
  client_id VARCHAR(256) comment '授权给的客户端'
) comment '客户端授权令牌';

create table oauth_access_token (
  token_id VARCHAR(256) comment '令牌Id',
  token BLOB comment '令牌',
  authentication_id VARCHAR(256) PRIMARY KEY comment '认证Id',
  user_name VARCHAR(256) comment '用户名称',
  client_id VARCHAR(256) comment '客户端Id',
  authentication BLOB comment '认证',
  refresh_token VARCHAR(256) comment '刷新令牌'
) comment 'Oauth 访问令牌';

create table oauth_refresh_token (
  token_id VARCHAR(256),
  token BLOB,
  authentication BLOB
);

create table oauth_code (
  code VARCHAR(256), authentication BLOB
);

create table oauth_approvals (
	userId VARCHAR(256),
	clientId VARCHAR(256),
	scope VARCHAR(256),
	status VARCHAR(10),
	expiresAt DATETIME,
	lastModifiedAt DATETIME
);


CREATE TABLE SPRING_SESSION (
   PRIMARY_ID CHAR(36) NOT NULL,
   SESSION_ID CHAR(36) NOT NULL,
   CREATION_TIME BIGINT NOT NULL,
   LAST_ACCESS_TIME BIGINT NOT NULL,
   MAX_INACTIVE_INTERVAL INT NOT NULL,
   EXPIRY_TIME BIGINT NOT NULL,
   PRINCIPAL_NAME VARCHAR(100),
   CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
 );

 CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
 CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
 CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

 CREATE TABLE SPRING_SESSION_ATTRIBUTES (
  SESSION_PRIMARY_ID CHAR(36) NOT NULL,
  ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
  ATTRIBUTE_BYTES BLOB NOT NULL,
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
  CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
 );

 CREATE INDEX SPRING_SESSION_ATTRIBUTES_IX1 ON SPRING_SESSION_ATTRIBUTES (SESSION_PRIMARY_ID);