package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface CarMapper {

    /**
     * 获取Car的总记录条数
     * @return
     */
    Long selectTotal();

    /**
     * 查询所有的Car信息，但是启用了驼峰命名自动映射
     * @return
     */
    List<Car> selectAllByMapUnderscoreToCamelCase();

    /**
     * 查询所有的Car信息，使用resultMap标签进行结果映射
     * @return
     */
    List<Car> selectAllByResultMap();

    /**
     * 查询所有Car，返回一个大Map集合
     * 大Map集合：
     *      key：每条记录的主键值
     *      value: 返回的记录
     * @return
     */
    @MapKey("id")   // 将查询结果的id值作为大Map集合的key
    Map<Long, Map<String, Object>> selectAllRetMap();

    /**
     * 查询所有Car信息，返回一个存放Map集合的List集合
     * @return
     */
    List<Map<String, Object>> selectAllRetListMap();

    /**
     * 根据id获取汽车信息，将汽车信息放到Map集合中
     * @param id
     * @return
     */
    Map<String, Object> selectByIdRetMap(Long id);

    /**
     * 根据品牌进行模糊查询
     * @param brand
     * @return
     */
    Car selectByBrandLike(String brand);

    /**
     * 获取所有的Car
     * @return
     */
    List<Car> selectAll();

    /**
     * 根据id查询Car信息
     * @param id
     * @return
     */
    Car selectById(Long id);
}
