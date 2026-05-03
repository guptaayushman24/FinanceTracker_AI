package com.example.expensechatbot.chatbotserviceimpl;

import com.example.expensechatbot.chatbotrepo.UserExpenseRepository;
import com.example.expensechatbot.chatbotservice.Askexpensedetail;
import com.example.expensechatbot.model.Expense;
import com.example.expensechatbot.responsedto.ExpenseSummary;

import org.springframework.data.domain.Pageable;
import java.util.List;
import com.example.expensechatbot.responsedto.Responsedto;
import com.example.expensechatbot.responsedto.SumOfExpense;
import com.example.expensechatbot.security.UserContext;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Tool("java@Tool(\"Retrieve the detailed list of expenses for the user between a given start date and end date. \" +\n" +
            "      \"Use this tool when the user wants to see, show, list or display their expenses for a specific date range. \" +\n" +
            "      \"Start date and end date must be in yyyy-MM-dd format. \" +\n" +
            "      \"Returns expense type, description, amount, payment mode and date for each expense.")
    public List<ExpenseSummary> expenseSummaryBetweenDate (@P("Start date format will be yyyy-MM-dd") String startDate,
                                                          @P("End date format will be yyyy-MM-dd") String endDate){

        return userExpenseRepository.expenseBetweenDate(userContext.getUserId(), LocalDate.parse(startDate), LocalDate.parse(endDate));

    }

    @Tool("Retrieve the detailed list of expenses for the user filtered by a specific expense type or category. " +
            "Use this tool when user wants to see, show, list or display expenses for a specific category. " +
            "For example: Entertainment, Food, Travel, Shopping, Medical, Education. " +
            "Returns expense type, description, amount, payment mode and date for each expense.")
    public List<ExpenseSummary> expenseSummaryByExpenseType(@P("Expense type or category name like Entertainment, Food, Travel, Shopping, Medical") String expenseType){
        return userExpenseRepository.expenseDetailByEntertainment(userContext.getUserId(), expenseType);
    }

    @Tool("Retrieve the detailed list of expenses for the user filtered by a specific payment mode. " +
            "Use this tool when user wants to see, show, list or display expenses for a specific payment mode. " +
            "For example: UPI,CASH or upi or cash "+
            "Returns expense type, description, amount, payment mode and date for each expense.")
    public List<ExpenseSummary> expenseSummaryByPaymentMode(@P("Payment mode like UPI, Cash or upi or cash") String paymentMode){
        return userExpenseRepository.expenseDetailByPaymentMode(userContext.getUserId(),paymentMode);
    }

    @Tool("Retrieve the most recent expenses for the user based on a given limit. " +
            "Use this tool when user says recent, latest or last N expenses. " +
            "For example: show my last 10 expenses, show recent 5 expenses.")
    public List<ExpenseSummary> expenseSummaryByLimit (@P("Number of recent expenses to fetch like 5, 10, 20. If not mentioned use 10 as default") int limit){
        return userExpenseRepository.showLatestExpenseByLimit(userContext.getUserId(), PageRequest.of(0, limit));
    }

    @Tool("Retrieve the detailed list of expenses for the user where the expense amount is within a given range. \" +\n" +
            "      \"Use this tool when user wants to see expenses greater than, less than, or between two amounts. \" +\n" +
            "      \"For example: show expenses between 1000 and 5000, show expenses above 500. \" +\n" +
            "      \"If only one amount is mentioned use it as both lower and higher value. \" +\n" +
            "      \"Returns expense type, description, amount, payment mode and date for each expense.\"")
    public List<ExpenseSummary> expenseSummaryInRange (@P("Lower amount value like 1000. If user gives only one amount use it as lower value too")
                                                           Integer lowerValue,
                                                       @P("Higher amount value like 5000. If not mentioned use lower value as higher value too")
                                                           Integer higherValue){
        return userExpenseRepository.showExpenseInARange(userContext.getUserId(),lowerValue,higherValue);
    }
}