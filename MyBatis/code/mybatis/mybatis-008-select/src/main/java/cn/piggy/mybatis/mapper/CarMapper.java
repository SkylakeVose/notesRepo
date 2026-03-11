package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;

public interface CarMapper {

    /**
     * 根据id查询Car信息
     * @param id
     * @return
     */
    Car selectById(Long id);
}
