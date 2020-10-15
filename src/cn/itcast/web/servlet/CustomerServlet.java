package cn.itcast.web.servlet;

import cn.itcast.domain.Customer;
import cn.itcast.domain.PageBean;
import cn.itcast.service.CustomerService;
import cn.itcast.servlet.BaseServlet;
import cn.itcast.utils.CommonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpRetryException;

/**
 * web层
 */
@WebServlet(name = "CustmoerServlet")
public class CustomerServlet extends BaseServlet { // 根据参数完成反射从而调用这里面的方法
    private CustomerService customerService = new CustomerService();

    /**
     * 添加客户
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 封装表单数据到Custmoer对象
         * 补全cid——使用service方法完成添加——保存成功信息——转发到msg.jsp
         */
        Customer c = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        c.setCid(CommonUtils.uuid());
        customerService.add(c);
        request.setAttribute("msg", "恭喜，添加客户成功!");
        return "f:/msg.jsp";
    }

//    /**
//     * 查询所有用户
//     * @param request
//     * @param response
//     * @return
//     * @throws ServletException
//     * @throws IOException
//     */
//    public String findAll(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        /*
//         * 调用service得到所有用户——保存到req域——转发到list.jsp
//         */
//        request.setAttribute("cstmList", customerService.findAll());
//        return "f:/list.jsp";
//    }

    /**
     * 查询所有用户
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
         * 获取pc(当前页码,不存在则=1)——给定ps值——使用service方法得到pageBean——保存到req域
         */
        int pc = getPc(request);
        int ps = 10; // 每页10行记录
        PageBean<Customer> pb = customerService.findAll(pc, ps);
        pb.setUrl(getUrl(request));
        request.setAttribute("pb", pb);
        return "f:/list.jsp";
    }

    /**
     * 获取pc
     * @param request
     * @return
     */
    private int getPc(HttpServletRequest request){
        String value = request.getParameter("pc");
        if(value == null || value.trim().isEmpty()){
            return 1;
        }
        return Integer.parseInt(value);
    }
    /**
     * 编辑用户前的准备工作
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String preEdit(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException{
        /*
         * 获取cid——根据cid调用service方法得到Customer对象——保存到req域——转发到edit.jsp显示
         */
        String cid = request.getParameter("cid");
        Customer cstm = customerService.load(cid);
        request.setAttribute("cstm", cstm);
        return "f:/edit.jsp";
    }

    /**
     * 编辑客户
     * @param request
     * @param response
     * @return
     * @throws HttpRetryException
     * @throws IOException
     */
    public String edit(HttpServletRequest request, HttpServletResponse response)
        throws HttpRetryException, IOException{
        /*
         * 封装表单数据到Customer——调用service方法完成修改——保存成功信息req域——转发到msg显示成功信息
         */
        Customer c = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        customerService.edit(c);
        request.setAttribute("msg","恭喜，编辑成功！");
        return "f:/msg.jsp";
    }

    /**
     * 删除客户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String delete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException,IOException{
        String cid = request.getParameter("cid");
        customerService.delete(cid);
        request.setAttribute("msg", "恭喜,删除客户成功！");
        return "f:/msg.jsp";
    }

    /**
     * 高级组合条件查询
     * @param request
     * @param response
     * @return
     * @throws HttpRetryException
     * @throws IOException
     */
    public String query(HttpServletRequest request, HttpServletResponse response)
    throws HttpRetryException,IOException{
        /*
         * 条件封装到cstm对象中-得到pc，给定ps，使用pc ps调用service方法得到PageBean——保存到req域——转发到list.jsp
         */
        Customer criteria = CommonUtils.toBean(request.getParameterMap(), Customer.class);
        criteria = encoding(criteria);
        int pc = getPc(request);
        int ps = 10;
        PageBean<Customer> pb = customerService.query(criteria, pc, ps);
        request.setAttribute("pb", pb);
        pb.setUrl(getUrl(request));
        return "f:/list.jsp";
    }

    /**
     * 获取请求条件
     * @param request
     * @return
     */
    private String getUrl(HttpServletRequest request){
        String contextPath = request.getContextPath(); // 获取项目名
        String servletPath = request.getServletPath(); // 获取Servlet
        String queryString = request.getQueryString(); // 获取问号后的参数
        if (queryString.contains("&pc=")){
            int index = queryString.lastIndexOf("&pc=");
            queryString = queryString.substring(0, index);
        }
        return contextPath + servletPath + "?" + queryString;
    }

    /**
     * 处理 四样 编码问题
     * @param criteria
     * @return
     * @throws UnsupportedEncodingException
     */
    public Customer encoding(Customer criteria) throws UnsupportedEncodingException {
        String cname = criteria.getCname();
        String gender = criteria.getGender();
        String celllphone = criteria.getCellphone();
        String email = criteria.getEmail();
        if (cname != null && cname.trim().isEmpty()){
            cname = new String(cname.getBytes("ISO-8859-1"), "utf-8");
            criteria.setCname(cname);
        }
        if (gender != null && gender.trim().isEmpty()){
            gender = new String(gender.getBytes("ISO-8859-1"), "utf-8");
            criteria.setGender(gender);
        }
        if (celllphone != null && celllphone.trim().isEmpty()){
            celllphone = new String(celllphone.getBytes("ISO-8859-1"), "utf-8");
            criteria.setCellphone(celllphone);
        }
        if (email != null && email.trim().isEmpty()){
            email = new String(email.getBytes("ISO-8859-1"), "utf-8");
            criteria.setEmail(email);
        }
        return  criteria;
    }
}
