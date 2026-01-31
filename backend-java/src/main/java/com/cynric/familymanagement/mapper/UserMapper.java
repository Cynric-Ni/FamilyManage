package com.cynric.familymanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cynric.familymanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户 Mapper 接口
 *
 * 继承 MyBatis-Plus 的 BaseMapper，自动获得基础 CRUD 功能
 *
 * BaseMapper<User> 提供的常用方法：
 *
 * 【插入】
 * - int insert(User entity)
 *   插入一条记录
 *
 * 【删除】
 * - int deleteById(UUID id)
 *   根据 ID 删除
 * - int delete(Wrapper<User> wrapper)
 *   根据条件删除
 *
 * 【更新】
 * - int updateById(User entity)
 *   根据 ID 更新（null 字段不更新）
 * - int update(User entity, Wrapper<User> wrapper)
 *   根据条件更新
 *
 * 【查询】
 * - User selectById(UUID id)
 *   根据 ID 查询
 * - User selectOne(Wrapper<User> wrapper)
 *   根据条件查询一条记录
 * - List<User> selectList(Wrapper<User> wrapper)
 *   根据条件查询列表
 * - Long selectCount(Wrapper<User> wrapper)
 *   根据条件查询总数
 * - IPage<User> selectPage(Page<User> page, Wrapper<User> wrapper)
 *   分页查询
 *
 * 【使用示例】
 * // 1. 根据用户名查询
 * QueryWrapper<User> wrapper = new QueryWrapper<>();
 * wrapper.eq("username", "admin");
 * User user = userMapper.selectOne(wrapper);
 *
 * // 2. 查询所有启用的用户
 * QueryWrapper<User> wrapper = new QueryWrapper<>();
 * wrapper.eq("status", UserStatusEnum.ACTIVE);
 * List<User> users = userMapper.selectList(wrapper);
 *
 * // 3. 分页查询
 * Page<User> page = new Page<>(1, 10); // 第1页，每页10条
 * QueryWrapper<User> wrapper = new QueryWrapper<>();
 * IPage<User> result = userMapper.selectPage(page, wrapper);
 *
 * 【自定义方法】
 * 如果需要复杂的 SQL 查询（多表关联、复杂统计等），可以：
 * 1. 在这里定义方法
 * 2. 在 resources/mapper/UserMapper.xml 中编写 SQL
 *
 * 对于简单的 CRUD 操作，直接使用 BaseMapper 提供的方法即可
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 这里可以添加自定义方法
    // 例如：
    // @Select("SELECT * FROM users WHERE username = #{username} AND deleted_at IS NULL")
    // User findByUsername(@Param("username") String username);

}
