package com.example.userexpense.prompt;

public class Prompt {
   public  String promptText = """
            You are an expert financial expense analyst.
            
            Analyze the following yearly expense data provided in JSON format.
            
            Tasks:
            1. Identify overspending categories and explain why.
            2. Compare payment modes and determine dominance using percentages.
            3. Analyze month-over-month spending trends.
            4. Identify major recurring or fixed expenses.
            5. Provide optimization recommendations.
            
            Output Requirements:
            
            A. Provide a section called "Key Insights" with short, clear sentences.
            
            B. Each insight must:
               - Be derived strictly from the data
               - Highlight a significant trend or pattern
               - Include numbers or percentages where applicable
               - Use simple, human-readable language
            
            C. Example insight formats (STYLE ONLY):
               - "Spending in a major category increased by X% in Month A compared to Month B."
               - "One payment mode accounts for X% of total transactions."
               - "A recurring expense represents the largest fixed cost."
            
            Rules:
            - Do not assume missing data.
            - Do not prioritize any category unless the data supports it.
            - Keep insights concise and factual.
            
            Expense Data (JSON):
            %s
            """;
}
