package com.example.userexpense.serviceImpl;

import com.example.userexpense.dto.ExpenseAnalyzerRequestdto;
import com.example.userexpense.dto.ExpenseAnalyzerResponsedto;
import com.example.userexpense.dto.ExpenseAnalyzerSqldto;
import com.example.userexpense.repository.UserExpenseRepository;
import com.example.userexpense.service.ExpenseAnalyzerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.coyote.Response;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.userexpense.prompt.*;
@Service
public class ExpenseAnalyzerServiceImpl implements ExpenseAnalyzerService {
    @Autowired
    ChatModel chatModel;
    @Autowired
    UserExpenseRepository userExpenseRepository;

    @Autowired
    ObjectMapper objectMapper;

    private final ChatClient chatClient;
    public ExpenseAnalyzerServiceImpl(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }
    com.example.userexpense.prompt.Prompt prompt = new com.example.userexpense.prompt.Prompt();
    @Override
    public String  analyzeExpense(Integer userId, Integer year) throws JsonProcessingException {

        List<ExpenseAnalyzerResponsedto> list = new ArrayList<>();
        List<ExpenseAnalyzerSqldto> expenseSummary = userExpenseRepository.expenseSumary(userId,year);


        for (ExpenseAnalyzerSqldto expenseAnalyzerSqldto:expenseSummary){
            ExpenseAnalyzerResponsedto expenseAnalyzerResponsedto = new ExpenseAnalyzerResponsedto();
            expenseAnalyzerResponsedto.setDescription(expenseAnalyzerSqldto.getDescription());
            expenseAnalyzerResponsedto.setExpenseType(expenseAnalyzerSqldto.getExpenseType());
            expenseAnalyzerResponsedto.setValue(expenseAnalyzerSqldto.getValue());
            expenseAnalyzerResponsedto.setPaymentMode(expenseAnalyzerSqldto.getPaymentMode());
            list.add(expenseAnalyzerResponsedto);
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("year", 2025);
        payload.put("expenseData", list);
        String expenseJson = objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(payload);

        String promptInstruction = """
                You are an expert financial expense analyst.
                Use the ₹ symbol when presenting amounts.
                Do NOT convert currency.
                
               You are given a complete list of expenses for the year {{year}} in JSON format.
                Each expense object contains:
                - description (string)
                - expenseType (string)
                - value (number)
                - paymentMode (string: UPI or CASH)
                - expenseDate (YYYY-MM-DD)
                
                Your tasks:
                
                1. Calculate the total spending and the total spendning is sum of all the Values which are present in each JSON
                2. Identify which expense categories have the highest spending.
                3. Identify which expense categories have the lowest spending.
                4. Determine where the user is overspending and explain why.
                4. Analyze payment modes:
                   - Calculate total spending via UPI for these SUM all the values which are corresponding to "value" where paymentMode is "UPI" in JSON.
                   - Calculate total spending via CASH for these SUM all the values which are corresponding to "value" where paymentMode is "CASH" in JSON.
                   - Identify which payment mode is used most frequently.
                   - Identify which payment mode contributes to higher spending.
                5. Monthly trend analysis:
                   - For each month, calculate total spending.
                   - Identify expenses that increased month-over-month.
                   - Identify expenses that decreased month-over-month.
                6. Detect unusual spikes or drops in spending and explain possible reasons.
                7. Provide 3–5 actionable recommendations to optimize spending.
                
                Rules:
                - Base your analysis strictly on the provided data.
                - Do not assume missing values.
                - Be concise, structured, and insightful.
                - Use bullet points and headings for clarity.
                
                Expense Data (JSON):
                {{EXPENSE_DATA_JSON}}
            """;

        // String promptText = promptInstruction.replace("{{EXPENSE_DATA_JSON}}", expenseJson);
        String promptText = promptInstruction.replace(
                "{{EXPENSE_DATA_JSON}}",
                expenseJson
        );

        OpenAiChatOptions options = OpenAiChatOptions.builder()
                .model("gpt-5.2")
                .temperature(0.7)
                .build();

        // Creating the prompt
        Prompt prompt = new Prompt(promptText,options);

        System.out.println("===== FINAL PROMPT =====");
        System.out.println(prompt);
        System.out.println("========================");

        // Call the ChatClient
        return chatClient.prompt(prompt)
                .call()
                .content();


    }
}