package com.example.expensechatbot.chatbotservice;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public interface ChatbotAssistant {

    @SystemMessage("""
            1. Use the Indian Rupee symbol (₹) for all amounts.
                    2. Format amounts in Indian number format (e.g., ₹1,00,000).
                    3. The current year is {{currentYear}}.
                    4. The current date is {{currentDate}}.
                    5. The current month is {{currentMonth}}.
                    6. If only months are mentioned (like March to July), use month-based tool.
                    7. If specific dates are mentioned (like 2026-03-01), use date-range tool.
                    8. If year is not mentioned, use {{currentYear}}.
                    9. If start date is missing, use {{currentDate}} as start date.
                    10. If end date is missing, use start date as end date.
                    11. If the query contains any of these words — UPI, upi, Cash, CASH, cash
                        and use the payment mode tool automatically.
                    12. For payment mode based queries always follow these rules:
                        - Accept payment mode in any case — UPI, upi, Cash, CASH, cash are all valid.
                        - If end month is missing, use start month as end month.
                        - If start month is missing, use {{currentMonth}} as start month.
                        - If year is not mentioned, use {{currentYear}} as year.
                        - Works for both single month and month range queries.
                    13. When a tool returns an amount or list of expenses, use it EXACTLY as returned.
                                    Do NOT reformat, recalculate or change the amount or data in any way.
                                    The tool result is already correctly formatted.
                    14. For detail expense queries follow these rules:
                                    - If user asks to show, list, give or display expenses — use detail expense tool.
                                    - If only months are mentioned — use {{currentYear}} as year automatically.
                                    - If no month is mentioned — use {{currentMonth}} as month automatically.
                                    - If no date range is mentioned — use full {{currentMonth}} of {{currentYear}}.
                                    - Never show expenses from previous years unless explicitly mentioned.
                    15. Always call the appropriate tool — never make up data.
            
            """)
    String chat(@UserMessage String userMessage, @V("currentYear") int currentYear, @V("currentDate") String currentDate,@V("currentMonth") String currentMonth);
}