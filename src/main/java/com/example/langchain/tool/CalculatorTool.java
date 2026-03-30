package com.example.langchain.tool;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Calculator Tool — provides mathematical operations.
 * The AI agent can invoke these methods to perform accurate calculations
 * instead of attempting mental math (which LLMs often get wrong).
 */
@Slf4j
@Component
public class CalculatorTool {

    @Tool("Add two numbers together and return the result")
    public double add(
            @P("The first number") double a,
            @P("The second number") double b) {
        log.info("🧮 CalculatorTool.add({}, {})", a, b);
        double result = a + b;
        log.info("Result: {}", result);
        return result;
    }

    @Tool("Subtract the second number from the first number and return the result")
    public double subtract(
            @P("The first number") double a,
            @P("The second number to subtract") double b) {
        log.info("🧮 CalculatorTool.subtract({}, {})", a, b);
        double result = a - b;
        log.info("Result: {}", result);
        return result;
    }

    @Tool("Multiply two numbers together and return the result")
    public double multiply(
            @P("The first number") double a,
            @P("The second number") double b) {
        log.info("🧮 CalculatorTool.multiply({}, {})", a, b);
        double result = a * b;
        log.info("Result: {}", result);
        return result;
    }

    @Tool("Divide the first number by the second number and return the result")
    public String divide(
            @P("The dividend (number to be divided)") double a,
            @P("The divisor (number to divide by)") double b) {
        log.info("🧮 CalculatorTool.divide({}, {})", a, b);
        if (b == 0) {
            return "Error: Division by zero is not allowed.";
        }
        double result = a / b;
        log.info("Result: {}", result);
        return String.valueOf(result);
    }

    @Tool("Calculate the square root of a number")
    public String squareRoot(@P("The number to find the square root of") double a) {
        log.info("🧮 CalculatorTool.squareRoot({})", a);
        if (a < 0) {
            return "Error: Cannot calculate square root of a negative number.";
        }
        double result = Math.sqrt(a);
        log.info("Result: {}", result);
        return String.valueOf(result);
    }

    @Tool("Calculate the power of a number (base raised to the exponent)")
    public double power(
            @P("The base number") double base,
            @P("The exponent") double exponent) {
        log.info("🧮 CalculatorTool.power({}, {})", base, exponent);
        double result = Math.pow(base, exponent);
        log.info("Result: {}", result);
        return result;
    }
}
