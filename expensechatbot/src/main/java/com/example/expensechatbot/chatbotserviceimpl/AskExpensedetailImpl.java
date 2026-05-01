package com.example.expensechatbot.chatbotserviceimpl;

import com.example.expensechatbot.chatbotrepo.UserExpenseRepository;
import com.example.expensechatbot.chatbotservice.Askexpensedetail;
import com.example.expensechatbot.responsedto.Responsedto;
import com.example.expensechatbot.responsedto.SumOfExpense;
import com.example.expensechatbot.security.UserContext;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;

@Service
public class AskExpensedetailImpl implements Askexpensedetail {

    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Autowired
    UserContext userContext;

    @Override
    @Tool("Calculate total expense between two months in a given year")
    public Responsedto getTotalExpenseBetweenMonths(@P("Start month name like January") String startMonth,
                                                    @P("End month name like December") String endMonth,
                                                    @P("Year as integer like 2025, if not provided use current year") int year) {
        int startMonthNum = Month.valueOf(startMonth.toUpperCase()).getValue();
        int endMonthNum = Month.valueOf(endMonth.toUpperCase()).getValue();

        SumOfExpense sumOfMonthExpense = userExpenseRepository
                .somOfExpenseBetweenTwoMonth(userContext.getUserId(), startMonthNum, endMonthNum, year);

        Responsedto responsedto = new Responsedto();
        responsedto.setResponse("Your total expense from " + startMonth + " to " + endMonth
                + " in " + year + " is " + sumOfMonthExpense.getSum());
        return responsedto;
    }

    @Tool("Calculate total expense between two dates")
    public Responsedto getTotalExpenseBetweenTwoDate (@P("Start date format will be yyyy-MM-dd") String startDate,
                                                      @P("End date format will be yyyy-MM-dd") String endDate){

        Responsedto responsedto = new Responsedto();

        SumOfExpense sumOfExpenseBetweenTwoDate = userExpenseRepository.sumOfExpenseBetweenTwoDate(
                userContext.getUserId(), LocalDate.parse(startDate), LocalDate.parse(endDate));

        responsedto.setResponse("Your total expense from " + startDate
                + " to " + endDate + " is " + sumOfExpenseBetweenTwoDate.getSum());

        return responsedto;

    }

    @Tool("Calculate the total expense between two given date by the Payment Mode")
    public Responsedto getTotalExpenseBetweenTwoDateByPaymentMode (@P("Start date format will be yyyy-MM-dd") String startDate,
                                                                   @P("End date format will be yyyy-MM-dd") String endDate,
                                                                   @P("Payment mode for example UPI or Cash") String paymentMode){
        Responsedto responsedto = new Responsedto();
        SumOfExpense sumOfExpenseBetweenTwoDateWithPaymentMode = userExpenseRepository.sumOfExpenseBetweenTwoDateByPaymentMode(userContext.getUserId(), LocalDate.parse(startDate),LocalDate.parse(endDate),paymentMode);

        responsedto.setResponse("Your total expense from " + startDate
                + " to " + endDate + " is " + sumOfExpenseBetweenTwoDateWithPaymentMode.getSum()+" "+"with"+" "+paymentMode);

        return responsedto;
    }

    @Tool("Calculate the total expense of the year")
    public Responsedto getTotalExpenseByYear (@P("Year as integer like 2025, if not provided use current year") Integer year){
        Responsedto responsedto = new Responsedto();
        SumOfExpense sumOfExpenseInYear = userExpenseRepository.sumOfExpenseBetweenInAyear(userContext.getUserId(),year);

        responsedto.setResponse("Your total expense in"+" "+year+"is "+sumOfExpenseInYear.getSum());
        return responsedto;
    }

    @Tool("Calculate total expense between a START MONTH and END MONTH for a specific payment mode like UPI or Cash Use this tool ONLY when user mentions specific months like February or March to July or May")
    public Responsedto getTotalExpenseBetweenMonth (@P("Start month name like January") String startMonth,
                                                   @P("End month name like December") String endMonth,
                                                   @P("Payment mode for example UPI or Cash") String paymentMode,
                                                   @P("Year as integer like 2025, if not provided use current year") int year){

        Responsedto responsedto = new Responsedto();
        int startMonthNum = Month.valueOf(startMonth.toUpperCase()).getValue();
        int endMonthNum = Month.valueOf(endMonth.toUpperCase()).getValue();

        SumOfExpense sumOfExpenseBetweenMonthWithPaymentMode = userExpenseRepository.sumOfExpenseBetweenMonthByPaymentMode(userContext.getUserId(),startMonthNum,endMonthNum,year,paymentMode);

        responsedto.setResponse("Your total expense from"+" "+startMonth+" "+"to"+" "+endMonth+" "+" "+"by"+" "+paymentMode+" is"+" "+sumOfExpenseBetweenMonthWithPaymentMode.getSum());
        return  responsedto;

    }

    @Tool("Calculate total expense for an ENTIRE YEAR for a specific payment mode like UPI or Cash Use this tool ONLY when user asks for full year like 2026 without mentioning any specific months")
    public Responsedto sumOfExpenseInAYearByPaymentMode (@P("year as integer like 2025, if not provided use current year") Integer year,
                                                         @P("Payment mode for example UPI or Cash or upi or cash") String paymentMode) {

        Responsedto responsedto = new Responsedto();
        SumOfExpense sumOfExpenseInAYearByPaymentMode = userExpenseRepository.sumOfExpenseInAYearByPaymentMode(userContext.getUserId(), year,paymentMode);
        responsedto.setResponse("Your total expense in"+" "+year+" "+"by"+" "+paymentMode+" "+"is"+" "+sumOfExpenseInAYearByPaymentMode.getSum());

        return responsedto;
    }

}