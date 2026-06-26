package com.example.userexpense.serviceImpl;

import com.example.userexpense.client.UserFeignClient;
import com.example.userexpense.dto.*;
import com.example.userexpense.model.ExpenseScheduler;
import com.example.userexpense.producer.Producer;
import com.example.userexpense.repository.PaymentModeRepository;
import com.example.userexpense.repository.RegisterSchedulerRepository;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.RegisterScheduler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RegisterSchedulerImpl implements RegisterScheduler {
    @Autowired
    private RegisterSchedulerRepository registerSchedulerRepository;
    @Autowired
    UserExpenseRepository userExpenseRepository;
    @Autowired
    PaymentModeRepository paymentModeRepository;
    @Autowired
    UserFeignClient userFeignClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    Producer producer;

    @Override
    public RegisterSchedulerResponsedto registerEvent(RegisterSchedulerRequestdto registerSchedulerRequestdto,Integer userId) {
        // Registering the scheduler timing
        // Check whether the user is already configure the scheduler if yes then throw the message first delete the existing one and then register the new one
        RegisterSchedulerResponsedto registerSchedulerResponsedto = new RegisterSchedulerResponsedto();
        UserSchedulerResponsedto userSchedulerResponsedto = registerSchedulerRepository.fetchUserSchedulerDuration(userId);
        if (userSchedulerResponsedto!=null){
            if (userSchedulerResponsedto.getSchedulerEventDuration()==1 || userSchedulerResponsedto.getSchedulerEventDuration()==2  || userSchedulerResponsedto.getSchedulerEventDuration()==3){
                // Scheduler Already exist throw the message
                registerSchedulerResponsedto.setMessage("You have already configured the scheduler please delete the existing scheduler to configure the new one");
                return registerSchedulerResponsedto;
            }
        }

        if (registerSchedulerRequestdto.getSchedulerTimming()==1 || registerSchedulerRequestdto.getSchedulerTimming()==2 || registerSchedulerRequestdto.getSchedulerTimming()==3){
            ExpenseScheduler expenseScheduler  = new ExpenseScheduler(null,userId,registerSchedulerRequestdto.getSchedulerTimming());
            registerSchedulerRepository.save(expenseScheduler);
        }
        else{
            throw new RuntimeException("Invalid Request");
        }



        if (registerSchedulerRequestdto.getSchedulerTimming()==1){
            registerSchedulerResponsedto.setMessage("Your weekly scheduler is activated");
        }
        else if (registerSchedulerRequestdto.getSchedulerTimming()==2){
            registerSchedulerResponsedto.setMessage("Your monthly scheduler is activated");
        }
        else{
            registerSchedulerResponsedto.setMessage("Your yearly scheduler is activated");
        }

        return registerSchedulerResponsedto;
    }

    @Override
    @Scheduled(cron = "0 0 9 * * FRI")
    public List<UserResponsedto> fetchAllUser() throws JsonProcessingException {
        LocalDate currentDate = LocalDate.now();
        List<UserResponsedto> list = userFeignClient.fetchAllUser();
        // Iterate on the list and check whether the scheduling event is 1,2 or 3
        for (UserResponsedto userResponsedto:list){
            Optional<ExpenseScheduler> expenseScheduler = registerSchedulerRepository.findById(userResponsedto.getId());
            if (expenseScheduler.isPresent() && expenseScheduler.get().getSchedulerEvent()==1){
                // Call the cron for the weekly
                ExpenseDetailSchedulerdto expenseDetailSchedulerdto = new ExpenseDetailSchedulerdto();

                TotalExpenseWeekResponsedto totalExpenseWeekResponsedto = paymentModeRepository.totalExpenseWeekResponsedto(expenseScheduler.get().getUser_id());
                expenseDetailSchedulerdto.setTotalPayment(totalExpenseWeekResponsedto.getSum());

                TotalExpenseWeekPaymentModeResponsedto totalExpenseWeekPaymentModeResponsedto = paymentModeRepository.totalExpenseWeekPaymentModeResponsedto(expenseScheduler.get().getUser_id(),"UPI");
                expenseDetailSchedulerdto.setTotalPaymentByUpi(totalExpenseWeekPaymentModeResponsedto.getSum());

                totalExpenseWeekPaymentModeResponsedto = paymentModeRepository.totalExpenseWeekPaymentModeResponsedto(expenseScheduler.get().getUser_id(),"CASH");
                expenseDetailSchedulerdto.setTotalPaymentByCash(totalExpenseWeekPaymentModeResponsedto.getSum());

                Long cash = expenseDetailSchedulerdto.getTotalPaymentByCash();
                if (cash==null) {
                    cash = 0L;
                }
                Long upi  = expenseDetailSchedulerdto.getTotalPaymentByUpi();
                if (upi==null){
                    upi = 0L;
                }

                if (cash!=0L || upi!=0L) {
                    expenseDetailSchedulerdto.setPercentageByCash(cash / (upi + cash));
                }

                expenseDetailSchedulerdto.setSchedulerDuration(Long.valueOf(expenseScheduler.get().getSchedulerEvent()));

                EmailResponsedto emailResponsedto = userFeignClient.findById(expenseScheduler.get().getUser_id());
                expenseDetailSchedulerdto.setEmailAddress(emailResponsedto.getEmailAddress());

                producer.sendUserPaymentDetail(expenseDetailSchedulerdto);

                log.info("Cron call for the weekly");

            }
            else if (expenseScheduler.isPresent() && expenseScheduler.get().getSchedulerEvent()==2 && isLastFridayOfMonth(currentDate)){
                // Call the cron for the monthly
                TotalExpenseMonthResponsedto totalExpenseMonthResponsedto = new TotalExpenseMonthResponsedto();
                TotalExpenseMonthPaymentModeResponsedto totalExpenseMonthPaymentModeResponsedto = new TotalExpenseMonthPaymentModeResponsedto();

                ExpenseDetailSchedulerdto expenseDetailSchedulerdto = new ExpenseDetailSchedulerdto();
                totalExpenseMonthResponsedto  = paymentModeRepository.totalExpenseMonthResponsedto(expenseScheduler.get().getUser_id(),String.valueOf(currentDate.getMonth()),currentDate.getYear());
                expenseDetailSchedulerdto.setTotalPayment(totalExpenseMonthResponsedto.getSum());

                totalExpenseMonthPaymentModeResponsedto = paymentModeRepository.totalExpenseMonthPaymentModeResponsedto(expenseScheduler.get().getUser_id(),String.valueOf(currentDate.getMonth()),"UPI",currentDate.getYear());
                expenseDetailSchedulerdto.setTotalPaymentByUpi(totalExpenseMonthPaymentModeResponsedto.getSum());

                totalExpenseMonthPaymentModeResponsedto = paymentModeRepository.totalExpenseMonthPaymentModeResponsedto(expenseScheduler.get().getUser_id(),String.valueOf(currentDate.getMonth()),"CASH",currentDate.getYear());
                expenseDetailSchedulerdto.setTotalPaymentByCash(totalExpenseMonthPaymentModeResponsedto.getSum());

                Long cash = expenseDetailSchedulerdto.getTotalPaymentByCash();
                if (cash==null) {
                    cash = 0L;
                }
                Long upi  = expenseDetailSchedulerdto.getTotalPaymentByUpi();
                if (upi==null){
                    upi = 0L;
                }

                if (cash!=0L || upi!=0L) {
                    expenseDetailSchedulerdto.setPercentageByCash(cash / (upi + cash));
                }

                expenseDetailSchedulerdto.setSchedulerDuration(Long.valueOf(expenseScheduler.get().getSchedulerEvent()));

                EmailResponsedto emailResponsedto = userFeignClient.findById(expenseScheduler.get().getUser_id());
                expenseDetailSchedulerdto.setEmailAddress(emailResponsedto.getEmailAddress());

                producer.sendUserPaymentDetail(expenseDetailSchedulerdto);

                log.info("Cron call for the monthly");

            }
            else if (expenseScheduler.isPresent() && expenseScheduler.get().getSchedulerEvent()==3 && isLastFridayOfYear(currentDate)){
                // Call the cron for the yearly
                ExpenseDetailSchedulerdto expenseDetailSchedulerdto = new ExpenseDetailSchedulerdto();
                TotalExpenseYearResponsedto totalExpenseYearResponsedto = new TotalExpenseYearResponsedto();
                TotalExpenseYearPaymentModeResponsedto totalExpenseYearPaymentModeResponsedto = new TotalExpenseYearPaymentModeResponsedto();

                 totalExpenseYearResponsedto = paymentModeRepository.totalExpenseYearesponsedto(expenseScheduler.get().getUser_id(), String.valueOf(currentDate.getYear()));
                expenseDetailSchedulerdto.setTotalPayment(totalExpenseYearResponsedto.getSum());

                totalExpenseYearPaymentModeResponsedto = paymentModeRepository.totalExpenseYearesponsePaymentModedto(expenseScheduler.get().getUser_id(),String.valueOf(currentDate.getYear()),"UPI");
                expenseDetailSchedulerdto.setTotalPaymentByUpi(totalExpenseYearPaymentModeResponsedto.getSum());

                totalExpenseYearPaymentModeResponsedto = paymentModeRepository.totalExpenseYearesponsePaymentModedto(expenseScheduler.get().getUser_id(),String.valueOf(currentDate.getYear()),"CASH");
                expenseDetailSchedulerdto.setTotalPaymentByCash(totalExpenseYearPaymentModeResponsedto.getSum());

                Long cash = expenseDetailSchedulerdto.getTotalPaymentByCash();
                Long upi = expenseDetailSchedulerdto.getTotalPaymentByUpi();
                if (cash==null){
                    cash = 0L;
                }
                if (upi==null){
                    upi = 0L;
                }

                if (cash!=0L || upi!=0L) {
                    expenseDetailSchedulerdto.setPercentageByCash(cash / (upi + cash));
                }

                expenseDetailSchedulerdto.setSchedulerDuration(Long.valueOf(expenseScheduler.get().getSchedulerEvent()));

                EmailResponsedto emailResponsedto = userFeignClient.findById(expenseScheduler.get().getUser_id());
                expenseDetailSchedulerdto.setEmailAddress(emailResponsedto.getEmailAddress());

                log.info("Cron call for the yearly");

            }
        }
        return userFeignClient.fetchAllUser();
    }

    @Override
    public RegisterSchedulerResponsedto deleteRegisterScheduler (Integer userId){
       int noOfRowsAffected =  registerSchedulerRepository.deleteScheduledEvent(userId);
        RegisterSchedulerResponsedto registerSchedulerResponsedto = new RegisterSchedulerResponsedto();
        if (noOfRowsAffected==0){
            registerSchedulerResponsedto.setMessage("You have not registered for the scheduler you can choose the weekly,monthly or yearly scheduler");
        }
       if (noOfRowsAffected>0){
           registerSchedulerResponsedto.setMessage("Your registered scheduler is removed now you can register the scheduler again");
       }

       return registerSchedulerResponsedto;
    }

    private boolean isLastFridayOfMonth(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.FRIDAY && date.plusWeeks(1).getMonth() != date.getMonth();
    }

    private boolean isLastFridayOfYear(LocalDate date) {
        return date.getMonth() == Month.DECEMBER && isLastFridayOfMonth(date);
    }
}
