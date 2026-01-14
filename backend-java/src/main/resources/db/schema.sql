-- ============================================
-- 家庭管理系统 - 用户表结构
-- ============================================

-- 1. 创建用户角色枚举类型
-- ADMIN: 超级管理员，拥有所有权限
-- MEMBER: 家庭成员，拥有基本读写权限
-- GUEST: 游客，只有只读权限
CREATE TYPE user_role AS ENUM ('ADMIN', 'MEMBER', 'GUEST');

-- 2. 创建用户状态枚举类型
-- ACTIVE: 账户启用
-- DISABLED: 账户禁用
CREATE TYPE user_status AS ENUM ('ACTIVE', 'DISABLED');

-- 3. 创建用户表
CREATE TABLE users (
    -- 主键：使用 UUID 提高安全性
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    -- ========== 登录信息 ==========
    -- 用户名：用于登录，必须唯一
    username VARCHAR(50) NOT NULL UNIQUE,

    -- 密码：存储 BCrypt 加密后的哈希值（长度约 60 字符）
    password VARCHAR(255) NOT NULL,

    -- 邮箱：可选，但如果填写必须唯一
    email VARCHAR(100) UNIQUE,

    -- 角色：用户权限等级
    role user_role NOT NULL DEFAULT 'MEMBER',

    -- 状态：账户是否启用
    status user_status NOT NULL DEFAULT 'ACTIVE',

    -- ========== 用户信息 ==========
    -- 手机号：可选，但如果填写必须唯一
    phone VARCHAR(20) UNIQUE,

    -- 性别：MALE/FEMALE/OTHER 或留空
    gender VARCHAR(10),

    -- 家庭角色：如"爸爸"、"妈妈"、"孩子"等
    family_role VARCHAR(50),

    -- 头像 URL：存储头像图片的访问地址
    avatar_url VARCHAR(500),

    -- ========== 审计字段 ==========
    -- 创建时间：记录账户创建时间
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 更新时间：记录最后一次更新时间
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- 创建人：记录是谁创建的这个账户（外键引用 users.id）
    created_by UUID,

    -- 更新人：记录是谁最后更新的这个账户（外键引用 users.id）
    updated_by UUID,

    -- 软删除时间：NULL 表示未删除，有值表示已删除
    deleted_at TIMESTAMP WITH TIME ZONE,

    -- 外键约束
    CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_updated_by FOREIGN KEY (updated_by) REFERENCES users(id)
);

-- 4. 创建索引以提高查询性能
-- 用户名索引（用于登录查询）
CREATE INDEX idx_users_username ON users(username) WHERE deleted_at IS NULL;

-- 邮箱索引（用于邮箱登录或找回密码）
CREATE INDEX idx_users_email ON users(email) WHERE deleted_at IS NULL;

-- 手机号索引（用于手机号登录）
CREATE INDEX idx_users_phone ON users(phone) WHERE deleted_at IS NULL;

-- 角色索引（用于按角色查询用户）
CREATE INDEX idx_users_role ON users(role) WHERE deleted_at IS NULL;

-- 状态索引（用于查询启用/禁用的用户）
CREATE INDEX idx_users_status ON users(status) WHERE deleted_at IS NULL;

-- 软删除索引（用于过滤已删除的用户）
CREATE INDEX idx_users_deleted_at ON users(deleted_at);

-- 5. 创建触发器：自动更新 updated_at 字段
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_users_updated_at
    BEFORE UPDATE ON users
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- 6. 添加表注释
COMMENT ON TABLE users IS '用户表：存储用户登录信息和基本资料';
COMMENT ON COLUMN users.id IS '用户唯一标识（UUID）';
COMMENT ON COLUMN users.username IS '用户名（用于登录）';
COMMENT ON COLUMN users.password IS 'BCrypt 加密后的密码哈希值';
COMMENT ON COLUMN users.email IS '邮箱地址';
COMMENT ON COLUMN users.role IS '用户角色：ADMIN-超级管理员, MEMBER-家庭成员, GUEST-游客';
COMMENT ON COLUMN users.status IS '账户状态：ACTIVE-启用, DISABLED-禁用';
COMMENT ON COLUMN users.phone IS '手机号';
COMMENT ON COLUMN users.gender IS '性别：MALE-男, FEMALE-女, OTHER-其他';
COMMENT ON COLUMN users.family_role IS '家庭角色：如爸爸、妈妈、孩子等';
COMMENT ON COLUMN users.avatar_url IS '头像图片 URL';
COMMENT ON COLUMN users.created_at IS '创建时间';
COMMENT ON COLUMN users.updated_at IS '最后更新时间';
COMMENT ON COLUMN users.created_by IS '创建人 ID';
COMMENT ON COLUMN users.updated_by IS '更新人 ID';
COMMENT ON COLUMN users.deleted_at IS '软删除时间（NULL 表示未删除）';
