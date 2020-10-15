package cn.itcast.service;

import cn.itcast.Dao.CustomerDao;
import cn.itcast.domain.Customer;
import cn.itcast.domain.PageBean;

/**
 * 业务层
 */
public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();

    /**
     * 添加客户
     * @param c
     */
    public void  add(Customer c){
        customerDao.add(c);
    }

//    /**
//     * 查询所有客户
//     * @return
//     */
//    public List<Customer> findAll(){
//        return customerDao.finAll();
//    }

    /**
     * 查询所有客户
     * @return
     */
    public PageBean<Customer> findAll(int pc, int ps){
        return customerDao.findAll(pc, ps);
    }

    /**
     * 根据cid查询客户
     * @param cid
     * @return
     */
    public  Customer load(String cid) {
        return customerDao.load(cid);
    }

    /**
     * 编辑客户信息
     * @param c
     */
    public void edit(Customer c) {
        customerDao.eidt(c);
    }

    /**
     * 删除客户
     * @param cid
     */
    public void delete(String cid) {
        customerDao.delete(cid);
    }

    /**
     * 高级组合条件查询
     * @param criteria
     * @return
     */
    public PageBean<Customer> query(Customer criteria, int pc, int ps) {
        return  customerDao.query(criteria, pc, ps);
    }
}
