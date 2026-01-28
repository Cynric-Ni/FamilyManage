package com.cynric.familymanagement.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cynric.familymanagement.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
