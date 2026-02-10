package cn.piggy.mybatis.mapper;

import cn.piggy.mybatis.pojo.Car;
import cn.piggy.mybatis.pojo.Log;

import java.util.List;

public interface LogMapper {
    /**
     * 根据日期获取不同的表，获取当天日志数据
     * @param date
     * @return
     */
    List<Log> selectAllByTable(String date);
}
