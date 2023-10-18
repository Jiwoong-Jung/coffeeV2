package project.coffee.controller.coffee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.coffee.domain.dto.coffee.CoffeeListDTO;
import project.coffee.domain.dto.coffee.OrderDetailDTO;
import project.coffee.domain.entity.CoffeeList;
import project.coffee.domain.entity.Customer;
import project.coffee.repository.coffee.CoffeeListRepository;
import project.coffee.repository.regist.CustomerRepository;
import project.coffee.service.coffee.CoffeeService;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.IntStream;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CoffeeController {


    @Autowired
    CoffeeListRepository coffeeListRepository;

    @Autowired
    CustomerRepository customerRepository;

    private CoffeeService service = null;

    @Autowired
    CoffeeController(CoffeeService service){
        this.service = service;
    }


    @PostMapping("/reviewregist")
    public String coffee(
            Model model,
            HttpServletRequest request, HttpSession session){

        String answer = request.getParameter("answer");
        int b_no = Integer.parseInt(request.getParameter("b_no"));

        String m_id = (String)session.getAttribute("UID");

        if(service.checkLoginHere(request,m_id)) {
            service.updateOrderDetail(b_no,answer,m_id);
            return "redirect:/"+b_no;
        }

        return "/login";


    }

    @GetMapping("/{b_no}")
    public String viewCoffee(
            @PathVariable long b_no, Model model) {

        CoffeeListDTO book = service.findCoffee(b_no);
        List<OrderDetailDTO> list = service.getOrderDetailList(b_no);

        model.addAttribute("coffee", book);
        model.addAttribute("orderDetail",list);

        return "coffee";
    }

    @GetMapping("/rental")
    public String order(
            HttpServletRequest request,
            HttpSession session){

       long b_no = Long.parseLong(request.getParameter("b_no"));

        String m_id=(String)session.getAttribute("UID");

        if(service.checkLoginHere(request,m_id)) {
            service.orderCoffee(m_id, b_no);
            return "rentalconfirm";
        }
        return "login";
    }

    @GetMapping("/check")
    @ResponseBody
    public String check() {
        return "no";
    }

    @PostConstruct
    public void init() {
        IntStream.rangeClosed(1, 4).forEach(i -> {

            CoffeeList board = CoffeeList.builder()
                    .title("gabea" + i)
                    .author("" + i + ",000")
                    .build();

            coffeeListRepository.save(board);
        });

        Customer customer = Customer.builder().m_id("test").m_pw("1111").m_name("홍길동").nickname("길동").build();
        customerRepository.save(customer);
    }

}
