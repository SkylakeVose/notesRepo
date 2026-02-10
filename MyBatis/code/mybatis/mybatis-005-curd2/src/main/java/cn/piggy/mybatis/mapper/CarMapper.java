package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;

import java.util.List;

/**
 * 使用mybatis的话，一般叫做xxxMapper
 */
public interface CarMapper {

    /**
     * 新增Car
     * @param car
     * @return
     */
    int insert(Car car);

    /**
     * 根据id删除Car
     * @param id
     * @return
     */
    int deleteById(Long id);

    /**
     * 修改汽车信息
     * @param car
     * @return
     */
    int update(Car car);

    /**
     * 根据id查询汽车
     * @param id
     * @return
     */
    Car selectById(Long id);

    /**
     * 获取所有Car信息
     * @return
     */
    List<Car> selectAll();
}
