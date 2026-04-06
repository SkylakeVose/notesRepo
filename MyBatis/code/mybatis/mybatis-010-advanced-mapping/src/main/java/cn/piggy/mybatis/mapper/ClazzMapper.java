package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Clazz;

public interface ClazzMapper {

    /**
     * 分步查询：第一步：查询班级信息
     * @param cid
     * @return
     */
    Clazz selectByStep1(Integer cid);

    /**
     * 根据班级编号查询班级信息
     * @param cid
     * @return
     */
    Clazz selectByCollection(Integer cid);

    /**
     * 分步查询第二部：根据id查询班级
     * @param cid
     * @return
     */
    Clazz selectByIdStep2(Integer cid);
}
