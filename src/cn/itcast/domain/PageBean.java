package cn.itcast.domain;

import java.util.List;

public class PageBean<T> {
    private int pc; // 当前页码 pc
    // private int tp; // 总页数 tp
    private int tr; // 总记录数 tr
    private int ps; // 每页记录数 ps
    private List<T> beanList; // 当前页的记录
    private String url; // 请求条件的url参数

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getTp() {
        int tp = tr / ps; // 总页数=总记录数/每页记录数
        return tr % ps == 0 ? tp : tp + 1; // 处理有余数情况——总页数 + 1
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }
}
