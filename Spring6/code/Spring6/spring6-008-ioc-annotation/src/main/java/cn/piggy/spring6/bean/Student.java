package cn.piggy.spring6.bean;

import org.springframework.stereotype.Repository;

// 省略value属性，spring会默认生成首字母小写的类名作为bean的id（student）
@Repository
public class Student {
}
