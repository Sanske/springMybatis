package cn.sanske.sqlSession;

import cn.sanske.config.Function;
import cn.sanske.config.MapperBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author sanske
 * @date 2018/7/24 上午11:07
 * 解析xml文件
 **/
public class MyConfiguration {
    private static ClassLoader loader = ClassLoader.getSystemClassLoader();

    //链接数据库的读取
    public Connection build(String resource) {
        try {
            InputStream stream = loader.getResourceAsStream(resource);
            SAXReader reader = new SAXReader();
            Document document = reader.read(stream);
            Element root = document.getRootElement();
            return evalDataSource(root);
        } catch (Exception e) {
            throw new RuntimeException("error occured while evaling xml" + resource);
        }
    }

    public Connection evalDataSource(Element node) throws ClassNotFoundException {
        if (!node.getName().equals("database")) {
            System.out.println("数据库解析错误");
            throw new RuntimeException("root should be <database>");
        }

        String driverClassName = null;
        String url = null;
        String username = null;
        String password = null;
        for (Object item : node.elements("property")) {
            Element i = (Element) item;
            //判别 property的多种写法
            String value = getValue(i);
            String name = i.attributeValue("name");
            if (name == null || value == null) {
                throw new RuntimeException("[database]<property> should contain name or value");
            }

            switch (name) {
                case "url":
                    url = value;
                    break;
                case "username":
                    username = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "driverClassName":
                    driverClassName = value;
                    break;
                default:
                    System.err.println("unknow property");
                    throw new RuntimeException("unknow property");
            }

        }
       Class.forName(driverClassName);
       Connection connection = null;
       try {
         connection = DriverManager.getConnection(url, username, password);
       } catch(SQLException e) {
         throw new RuntimeException("database link error");
       }
       return connection;
    }

    //获取 property 属性的值，如果
    private String getValue(Element node) {
        return node.hasContent() ? node.getText() : node.attributeValue("value");
    }

//    //读取我们的mapper文件
    @SuppressWarnings("rawtypes")
    public MapperBean readMapper(String path) {
       MapperBean mapper = new MapperBean();
       try{
           InputStream is = loader.getResourceAsStream(path);
           SAXReader reader = new SAXReader();
           Document document = reader.read(is);
           Element root = document.getRootElement();
           mapper.setInterfaceName(root.attributeValue("nameSpace").trim());
           List<Function> list = new ArrayList<Function>();
           Iterator rootIterator = root.elementIterator();
           while(rootIterator.hasNext()) {
            Function fun = new Function();
            Element e = (Element) rootIterator.next();
            String sqltype = e.getName().trim();
            String funcName = e.attributeValue("id").trim();
            String sql = e.getText().trim();
            String reultType = e.attributeValue("resultType").trim();
            fun.setSqltype(sqltype);
            fun.setFuncName(funcName);
            Object newIntance = null;
            try {
              newIntance = Class.forName(reultType).newInstance();
            }catch(Exception e1) {
               throw new RuntimeException("readMapper newIntance...");
            }
            fun.setResultType(newIntance);
            fun.setSql(sql);
            list.add(fun);
           }
           mapper.setList(list);
       }catch(Exception e) {
           throw new RuntimeException("parse mapper error");
       }
       return mapper;
    }
}
