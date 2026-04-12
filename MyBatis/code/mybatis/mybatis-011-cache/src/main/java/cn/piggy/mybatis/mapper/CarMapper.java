package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {

    /**
     * 根据id获取汽车信息
     * @param id
     * @return
     */
    Car selectById(Long id);
}
