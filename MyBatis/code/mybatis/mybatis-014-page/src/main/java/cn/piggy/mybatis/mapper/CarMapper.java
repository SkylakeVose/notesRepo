package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarMapper {

    /**
     * 分页查询
     * @param startIndex 起始下标
     * @param pageSize  每页显示的分页条数
     * @return
     */
    List<Car> selectByPage(@Param("startIndex") Integer startIndex, @Param("pageSize") Integer pageSize);
}
