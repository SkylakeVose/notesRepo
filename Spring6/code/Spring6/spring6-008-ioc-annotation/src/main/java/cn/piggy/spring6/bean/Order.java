package cn.piggy.spring6.bean;

import org.springframework.stereotype.Service;

// 省略value属性，spring会默认生成首字母小写的类名作为bean的id（order）
@Service
public class Order {
}
