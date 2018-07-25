package cn.sanske.config;

import java.util.List;

/**
 * @author sanske
 * @date 2018/7/24 上午11:04
 **/
public class MapperBean {
    private String interfaceName;
    private List<Function> list;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public List<Function> getList() {
        return list;
    }

    public void setList(List<Function> list) {
        this.list = list;
    }
}
