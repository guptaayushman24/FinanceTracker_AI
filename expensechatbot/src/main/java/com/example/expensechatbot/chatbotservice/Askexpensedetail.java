package com.example.expensechatbot.chatbotservice;

import com.example.expensechatbot.responsedto.Responsedto;
import dev.langchain4j.agent.tool.P;
import org.apache.coyote.Response;

public interface Askexpensedetail {
    Responsedto getTotalExpenseBetweenMonths(@P("Start month name like January") String startMonth,
                                             @P("End month name like December") String endMonth,
                                             @P("Year as integer like 2025, if not provided use current year") int year);


    Responsedto getTotalExpenseBetweenTwoDate (@P("Start date format will be yyyy-MM-dd") String startDate,
                                               @P("End date format will be yyyy-MM-dd") String endDate);

    Responsedto getTotalExpenseBetweenTwoDateByPaymentMode (@P("Start date format will be yyyy-MM-dd") String startDate,
                                                            @P("End date format will be yyyy-MM-dd") String endDate,
                                                            @P("Payment mode will be UPI or upi consider as UPI and CASH or cash consider as CASH") String paymentMode);

    Responsedto getTotalExpenseByYear (@P("Year as integer like 2025, if not provided use current year") Integer year);

    Responsedto getTotalExpenseBetweenMonth (@P("Start month name like January") String startMonth,
                                             @P("End month name like December") String endMonth,
                                             @P("Payment mode for example UPI or Cash") String paymentMode,
                                             @P("Year as integer like 2025, if not provided use current year") int year);

    Responsedto sumOfExpenseInAYearByPaymentMode(@P("year as integer like 2025, if not provided use current year") Integer year,
                                                 @P("Payment mode for example UPI or Cash or upi or cash") String paymentMode);
}