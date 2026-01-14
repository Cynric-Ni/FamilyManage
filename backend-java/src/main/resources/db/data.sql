-- ============================================
-- 家庭管理系统 - 初始数据
-- ============================================

-- 插入初始超级管理员账户
-- 注意：密码为明文 "123"，仅用于测试环境
-- 生产环境部署前必须修改密码并使用 BCrypt 加密
INSERT INTO users (
    username,
    password,
    email,
    role,
    status,
    gender,
    family_role,
    created_at,
    updated_at
) VALUES (
    'admin',                    -- 用户名
    '123',                      -- 密码（明文，仅测试用）
    'admin@family.com',         -- 邮箱
    'ADMIN',                    -- 角色：超级管理员
    'ACTIVE',                   -- 状态：启用
    NULL,                       -- 性别：未设置
    '管理员',                   -- 家庭角色
    CURRENT_TIMESTAMP,          -- 创建时间
    CURRENT_TIMESTAMP           -- 更新时间
);

-- 可选：插入测试用的家庭成员账户
INSERT INTO users (
    username,
    password,
    email,
    role,
    status,
    phone,
    gender,
    family_role,
    created_at,
    updated_at
) VALUES (
    'member',                   -- 用户名
    '123',                      -- 密码（明文，仅测试用）
    'member@family.com',        -- 邮箱
    'MEMBER',                   -- 角色：家庭成员
    'ACTIVE',                   -- 状态：启用
    '13800138000',              -- 手机号
    'MALE',                     -- 性别：男
    '爸爸',                     -- 家庭角色
    CURRENT_TIMESTAMP,          -- 创建时间
    CURRENT_TIMESTAMP           -- 更新时间
);

-- 可选：插入测试用的游客账户
INSERT INTO users (
    username,
    password,
    email,
    role,
    status,
    gender,
    family_role,
    created_at,
    updated_at
) VALUES (
    'guest',                    -- 用户名
    '123',                      -- 密码（明文，仅测试用）
    'guest@family.com',         -- 邮箱
    'GUEST',                    -- 角色：游客
    'ACTIVE',                   -- 状态：启用
    'OTHER',                    -- 性别：其他
    '访客',                     -- 家庭角色
    CURRENT_TIMESTAMP,          -- 创建时间
    CURRENT_TIMESTAMP           -- 更新时间
);
