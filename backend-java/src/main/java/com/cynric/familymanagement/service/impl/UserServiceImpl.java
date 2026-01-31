package com.cynric.familymanagement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cynric.familymanagement.dto.request.RegisterRequest;
import com.cynric.familymanagement.dto.response.UserResponse;
import com.cynric.familymanagement.entity.User;
import com.cynric.familymanagement.enums.UserRoleEnum;
import com.cynric.familymanagement.enums.UserStatusEnum;
import com.cynric.familymanagement.mapper.UserMapper;
import com.cynric.familymanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * BCrypt 密码加密器
     * 强度为 10（2^10 次迭代）
     */
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    /**
     * 用户注册
     *
     * 注册流程：
     * 1. 验证两次密码是否一致
     * 2. 检查用户名是否已存在
     * 3. 检查邮箱是否已存在（如果提供）
     * 4. 检查手机号是否已存在（如果提供）
     * 5. 使用 BCrypt 加密密码
     * 6. 设置默认角色和状态
     * 7. 保存到数据库
     * 8. 返回用户信息（不包含密码）
     *
     * @param request 注册请求信息
     * @return 注册成功后的用户信息
     * @throws RuntimeException 如果验证失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)  // 开启事务，出错自动回滚
    public UserResponse register(RegisterRequest request) {

        // ========== 1. 验证两次密码是否一致 ==========
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // ========== 2. 检查用户名是否已存在 ==========
        QueryWrapper<User> usernameWrapper = new QueryWrapper<>();
        usernameWrapper.eq("username", request.getUsername());
        Long usernameCount = userMapper.selectCount(usernameWrapper);
        if (usernameCount > 0) {
            throw new RuntimeException("用户名已存在");
        }

        // ========== 3. 检查邮箱是否已存在（如果提供） ==========
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            QueryWrapper<User> emailWrapper = new QueryWrapper<>();
            emailWrapper.eq("email", request.getEmail());
            Long emailCount = userMapper.selectCount(emailWrapper);
            if (emailCount > 0) {
                throw new RuntimeException("邮箱已被注册");
            }
        }

        // ========== 4. 检查手机号是否已存在（如果提供） ==========
        if (request.getPhone() != null && !request.getPhone().trim().isEmpty()) {
            QueryWrapper<User> phoneWrapper = new QueryWrapper<>();
            phoneWrapper.eq("phone", request.getPhone());
            Long phoneCount = userMapper.selectCount(phoneWrapper);
            if (phoneCount > 0) {
                throw new RuntimeException("手机号已被注册");
            }
        }

        // ========== 5. 使用 BCrypt 加密密码 ==========
        String encryptedPassword = passwordEncoder.encode(request.getPassword());

        // ========== 6. 构建 User 对象 ==========
        User user = User.builder()
                .username(request.getUsername())
                .password(encryptedPassword)  // 存储加密后的密码
                .email(request.getEmail())
                .phone(request.getPhone())
                .familyRole(request.getFamilyRole())
                .role(UserRoleEnum.MEMBER)  // 默认角色：家庭成员
                .status(UserStatusEnum.ACTIVE)  // 默认状态：启用
                .build();

        // ========== 7. 保存到数据库 ==========
        int result = userMapper.insert(user);
        if (result != 1) {
            throw new RuntimeException("注册失败，请稍后重试");
        }

        // ========== 8. 转换为 UserResponse 返回 ==========
        return convertToUserResponse(user);
    }

    /**
     * 将 User 实体转换为 UserResponse DTO
     * 这个方法会过滤掉敏感信息（如密码）
     *
     * @param user User 实体
     * @return UserResponse DTO
     */
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())  // 修正：使用 getRole() 而不是 getRoleEnum()
                .status(user.getStatus())
                .gender(user.getGender())
                .familyRole(user.getFamilyRole())
                .avatarUrl(user.getAvatarUrl())
                .displayName(user.getDisplayName())  // 使用 User 实体的 getDisplayName() 方法
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
