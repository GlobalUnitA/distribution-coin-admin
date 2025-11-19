package com.example.distributioncoinadmin.deposit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DepositController {

    //입금 화면
    @GetMapping("/deposit")
    public String showDepositSelectPage(){
        return "deposit/select"; // templates/deposit.html로 매핑
    }
}