package com.photovault.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

/**
 * @author guanhua
 */
public class CalculatorUtils {

    public static double calculate(Stack<String> numberAndOperatorStack){
        Stack<Double> stack = new Stack<>();

        int size = numberAndOperatorStack.size();
        for (int i = 0 ; i < size ; i++){
            if (isNumber(numberAndOperatorStack.get(i))){
                stack.push(Double.valueOf(numberAndOperatorStack.get(i)));
            }else if (isOperator(numberAndOperatorStack.get(i))&&numberAndOperatorStack.get(i).equals("+")){
                double result = stack.pop() + stack.pop();
                stack.push(result);
            }else if (isOperator(numberAndOperatorStack.get(i))&&numberAndOperatorStack.get(i).equals("-")){

                double result0 = stack.pop();
                double result1 = stack.pop();

                double result = result1 - result0;

                stack.push(result);
            }else if (isOperator(numberAndOperatorStack.get(i))&&numberAndOperatorStack.get(i).equals("x")){
                double result = stack.pop() * stack.pop();

                stack.push(result);
            }else if (isOperator(numberAndOperatorStack.get(i))&&numberAndOperatorStack.get(i).equals("÷")){

                double result0 = stack.pop();
                double result1 = stack.pop();

                double result = result1 / result0;
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public static Stack<String> infixToSuffix(String expression){
        List<String> expressionList = getExpressionList(expression);

        Stack<String> number = new Stack<>();

        Stack<String> operator = new Stack<>();

        for (int i = 0 ; i < expressionList.size() ; i++){
            if (isNumber(expressionList.get(i))){
                number.push(expressionList.get(i));
            }else if (isOperator(expressionList.get(i))){
                if (operator.empty()){
                    operator.push(expressionList.get(i));
                }else if (isOperator(operator.peek()) &&
                        operatorPriority(expressionList.get(i)) > operatorPriority(operator.peek())){

                    operator.push(expressionList.get(i));

                }else {

                    do {
                        number.push(operator.pop());
                    }while (!operator.empty()&&
                            operatorPriority(expressionList.get(i)) <= operatorPriority(operator.peek()));
                    operator.push(expressionList.get(i));

                }
            }
        }

        int operatorStackSize = operator.size();
        for (int index = operatorStackSize - 1 ; index >= 0 ; index --) {
            number.push(operator.pop());
        }
        return number;

    }

    private static List<String> getExpressionList(String expression){

        List<String> chars = new ArrayList<>();
        int index = 0;

        if (expression.charAt(0) == '-'){
            for (int i = 1 ; i < expression.length() ; i++){
                if (isOperator(expression.substring(i,i+1))){
                    index = i;
                    break;
                }
            }
            chars.add(expression.substring(0,index));
        }

        for (int i = index ; i < expression.length() ; i++){
            if (isOperator(expression.substring(i,i+1))|| expression.charAt(i) == ')'){
                chars.add(expression.substring(index,i));
                chars.add(expression.substring(i,i+1));
                index = i + 1;
            }else if (expression.charAt(i) == '('){
                chars.add(expression.substring(i,i+1));
                index = i + 1;
            }
        }
        if (index < expression.length()) {
            chars.add(expression.substring(index));
        }
        return chars;
    }

    private static boolean isNumber(String str){
        return Pattern.matches("^-?[0-9]+$", str)||
                Pattern.matches("^\\d+(\\.\\d+)?$", str)||
                Pattern.matches("^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$", str);
    }

    private static boolean isOperator(String operator){
        return operator.equals("+")||operator.equals("-")||operator.equals("x")||operator.equals("÷");
    }

    private static int operatorPriority(String operator){
        if ("+".equals(operator)|| "-".equals(operator)) {
            return 0;
        }
        if ("x".equals(operator)|| "÷".equals(operator)) {
            return 1;
        }
        return 0;
    }


}
