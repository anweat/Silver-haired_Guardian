package com.yinling.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinling.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据手机号查询用户
     */
    User selectByPhone(String phone);
}
