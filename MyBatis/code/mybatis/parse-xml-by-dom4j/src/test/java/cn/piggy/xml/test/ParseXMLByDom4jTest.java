package cn.piggy.xml.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class ParseXMLByDom4jTest {

    @Test
    public void testParseSqlMapperXML() throws Exception {
        SAXReader reader = new SAXReader();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("CarMapper.xml");
        Document document = reader.read(is);

        // 获取namespace
        String xpath = "/mapper";
        Element mapper = (Element) document.selectSingleNode(xpath);
        String namespace = mapper.attributeValue("namespace");
        System.out.println("命名空间：" + namespace);
        
        // 获取mapper节点下所有的子节点
        List<Element> elements = mapper.elements();

        elements.forEach(element -> {
            // 获取sqlId
            String id = element.attributeValue("id");
            System.out.println(id);

            // 获取resultType（如果没有这个属性则会返回null）
            String resultType = element.attributeValue("resultType");
            System.out.println(resultType);

            // 获取标签中的sql语句（获取文本且去除前后空白）
            String sql = element.getTextTrim();
            System.out.println(sql);

            /** MYBATIS封装了JDBC,我们需要将#{}转换成JDBC规范里的？
             * insert into t_car values(null, #{carNum},#{brand},#{guidePrice},#{produceTime},#{carType})
             * ↑ 转换为 → insert into t_car values(null,?,?,?,?,?)
             */
            String newSql = sql.replaceAll("#\\{[0-9A-Za-z_$]*}", "?");
            System.out.println(newSql);
        });
    }

    @Test
    public void testParseMyBatisConfigXML() throws Exception {
        // 创建SAXReader对象
        SAXReader reader = new SAXReader();
        // 获取输入流
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml");
        // 读XML文件，返回document独享，document对象是文档对象，代表了整个XML文件
        Document document = reader.read(is);
        System.out.println(document);

        // 获取文档当中的根标签
        /*Element rootElement = document.getRootElement();
        System.out.println("根节点的名字：" + rootElement.getName());*/

        // 获取default默认的环境id
        // xpath是做标签路径匹配的，能够让我们快速定位XML文件中的元素。
        // 下面的xpath代表：从根下找configuration标签，然后找该标签下的environments子标签
        String xpath = "/configuration/environments";
        Element environments = (Element) document.selectSingleNode(xpath);  // Element是Node的子类，方法更多
        // 获取属性的值
        String defaultEnvironmentId = environments.attributeValue("default");
        System.out.println("默认环境的环境ID：" + defaultEnvironmentId);

        // 根据默认的环境ID 获取具体的环境environment
        xpath = "/configuration/environments/environment[@id='" + defaultEnvironmentId + "']";
        Element environment = (Element) document.selectSingleNode(xpath);
        // System.out.println(environment);

        // 获取environment节点下的TransactionManager节点
        // 使用Element的element()方法来互殴孩子节点
        Element transactionManager = environment.element("transactionManager");
        String transactionType = transactionManager.attributeValue("type");
        System.out.println("事务管理器的类型： " + transactionType);
        
        // 获取dataSource节点
        Element dataSource = environment.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println("数据源的类型：" + dataSourceType);

        // 获取dataSource节点下的所有子节点
        List<Element> propertyElts = dataSource.elements();
        // 遍历输出
        propertyElts.forEach(propertyElt -> {
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            System.out.println(name + ":" + value);
        });

        // 获取所有的Mapper标签
        // 不想从根下开始获取，从任意位置开始，获取所有的mapper标签
        xpath = "//mapper";
        List<Node> mappers = document.selectNodes(xpath);
        // 遍历
        mappers.forEach(mapper -> {
            Element mapperElt = (Element) mapper;
            String resource = mapperElt.attributeValue("resource");
            System.out.println(resource);
        });
    }
}
