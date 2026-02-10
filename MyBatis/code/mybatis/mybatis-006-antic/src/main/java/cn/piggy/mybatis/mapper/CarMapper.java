package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;

import java.util.List;

public interface CarMapper {
    /**
     * 根据汽车类型查询汽车
     * @param carType
     * @return
     */
    List<Car> selectByCarType(String carType);

    /**
     * 查询所有汽车信息，并通过形参排序
     * @param ascOrDesc
     * @return
     */
    List<Car> selectAllByAscOrDesc(String ascOrDesc);
}
