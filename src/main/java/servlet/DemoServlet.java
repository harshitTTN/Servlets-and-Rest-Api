package servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import order.Order;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/order")  //Context Route
public class DemoServlet extends HttpServlet {


    private List<Order> orderList = new ArrayList<Order>();

    private int counter;

    @Override
    public void init() {
        System.out.println("Inside the Init Method");
        counter = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int id = Objects.nonNull(req.getParameter("id"))?Integer.parseInt(req.getParameter("id")) : 0;
        Gson gson = new GsonBuilder().create();
        Order order = getOrderById(id,resp);

        if( id!=0 ) {

            if (order == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                PrintWriter out = resp.getWriter();
                out.println("Order with id : " + id + " Not Found");

            } else {
                resp.setHeader("ContentType", "application/json");
                PrintWriter out = resp.getWriter();
                out.println(gson.toJson(order));
                out.close();
            }
        }
        else{
            resp.setHeader("ContentType", "application/json");
           resp.getWriter().write(gson.toJson(orderList));;
           resp.setStatus(HttpServletResponse.SC_OK);
//            out.println(gson.toJson(orderList));


        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String name = req.getParameter("name");
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        if (name != null && quantity != 0){
            String status = addOrder(quantity, name, resp);
            PrintWriter out = resp.getWriter();
            out.println(status);
            out.close();
        } else{
            resp.setContentType("text/plain");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.write("Please try again later!");
            out.close();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id != null && !id.equals("")){
            String status = deleteOrder(Integer.parseInt(id), resp);
            PrintWriter out = resp.getWriter();
            out.println(status);
            out.close();
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.println("Please try again later!");
            out.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String id = req.getParameter("id");

        if (name != null  && id != null){
            String status = updateById(Integer.parseInt(id), name, resp);
            PrintWriter out = resp.getWriter();
            out.println(status);
            out.close();
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = resp.getWriter();
            out.println("Please try again later!");
            out.close();
        }
    }



    private String addOrder(int quantity, String name, HttpServletResponse resp){

        this.orderList.add(new Order(name, quantity,counter++));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        return "Order successfully added.";

    }
    private String updateById(int id, String name, HttpServletResponse response){
        for (Order order : orderList){
            if (order.getId() == id){
                order.setName(name);
                return "Order name changed successfully";
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return " Id not found";
    }

    private String deleteOrder(int id,HttpServletResponse resp){

        for(Order orders : orderList)
        {
            int i=0;
            if(orders.getId()==id)
            {
                orderList.remove(i);
                i++;

                return "Order with Id : "+id+"Deleted";

            }
        }

        return "Order with id : "+id+" not found";

    }

    private void listAllOrders()
    {
        for(Order order : orderList)
        {
            System.out.println(order);
        }

    }

    private Order getOrderById(int id,HttpServletResponse resp)
    {
        for(Order orders : orderList)
        {
            if(orders.getId()==id)
            {
                return orders;
            }
        }

        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        System.out.println("Order Not Found!");
        return null;
    }

    public void destroy()
    {
        this.orderList.clear();
        counter = 1;
    }

}