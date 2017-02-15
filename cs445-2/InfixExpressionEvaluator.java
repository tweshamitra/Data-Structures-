/* Twesha Mitra- Assignment 2
*  For this assignment, I used the pseudocode in textbook as a reference for the algorithm
*  Additionally, I used Professor Garrison's lecture slides.  
*/
package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operators;
    StackInterface<Double> operands;
    int precedence1; //precedence for operator at top of stack
    int precedence2; //precedence for current operator 

    /**
     * Initializes the solver to read an infix expression from input.
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // Declare that - and / are regular characters (ignore their regex
        // meaning)
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operators = new ArrayStack<Character>();
        operands = new ArrayStack<Double>();
    }

    /**
     * A type of runtime exception thrown when the given expression is found to
     * be invalid
     */
    class ExpressionError extends RuntimeException {
        ExpressionError(String msg) {
            super(msg);
        }
    }

    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     */
    public static void main(String[] args) {
        InfixExpressionEvaluator solver =
                        new InfixExpressionEvaluator(System.in);
        Double value = solver.evaluate();
        if (value != null) {
            System.out.println(value);
        }
    }

    /**
     * Evaluates the expression parsed by the tokenizer and returns the
     * resulting value.
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    processOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '%':
                    // If the token is any of the above characters, process it
                    // is an operator
                    processOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '[':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case ']':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    processCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        processRemainingOperators();

        // Return the result of the evaluation
        // TODO: Fix this return statement
        Double result=operands.peek();
        return result;
    }

    /**
     * Processes an operand.
     */
    void processOperand(double operand) {
    	 if(operand>9)
    	 	throw new ExpressionError("Only one operator allowed");
    	else
    		operands.push(operand);
    }

    /**
     * Processes an operator.
     */
    void processOperator(char operator) {
        // TODO: Complete this method
    	if(operators.isEmpty()){
    		operators.push(operator);
    		}
    	else {
    		if(getPrecedence(operator)==false){
    			operators.push(operator);
    		}
    		else {
    			while(getPrecedence(operator)==true)
    			{
    				boolean result= getPrecedence(operator);
    				evaluateinfix();
    			}
    			operators.push(operator);
    		}
    	}
    }

    /**
     * Processes an open bracket.
     */
    void processOpenBracket(char openBracket) {
    	operators.push(openBracket);
    	
    }

    /**
     * Processes a close bracket.
     */
    void processCloseBracket(char closeBracket){
    	if(closeBracket==')'){
       		if(operators.peek()=='('){
        		operators.pop();
        	} else if(operators.peek()=='['){
        		throw new ExpressionError("Expression Error");
        	}
        	else {
        		while(!(operators.peek()=='(')){
        			if(operators.peek()=='['){
        				throw new ExpressionError("Expression Error");
        			}
        			else{ 
        				evaluateinfix();
        				}
	         	}
	         	operators.pop();
	         }
   		 	}	
		if(closeBracket==']'){
			if(operators.peek()=='['){
				operators.pop();
			} else if(operators.peek()=='('){
				throw new ExpressionError("Expression Error");
			} else {
				while(!(operators.peek()=='[')){
					if(operators.peek()=='('){
						throw new ExpressionError("Expression Error");
					} else {
						evaluateinfix();
					}
				}
				operators.pop();
			}
		}
	}
    /**
     * Processes any remaining operators leftover on the operators stack
     */
    void processRemainingOperators() {
    	while(!(operators.isEmpty())){
    		evaluateinfix();
    	}
    }
    /* This evaluate method performs the arithmetic operations and pushes the 
    * result onto the top of the stack. 
    */
    void evaluateinfix(){
    	char opt=operators.pop();
    	double opd1=operands.pop();
    	double opd2=operands.pop();
    	double evalresult=0.00;
    	if (opt=='+')
    		evalresult=(opd2+opd1);
    	if(opt=='-')
    		evalresult=(opd2-opd1);
    	if(opt=='*')
    		evalresult=(opd2*opd1);
    	if(opt=='/'){
    		if(opd1==0)
    			throw new ExpressionError("Cannot divide by zero");
    		else	
    			evalresult=(opd2/opd1);
    	}
    	if(opt=='%')
    		evalresult=(opd2%opd1);
    	if(opt=='^')
    		evalresult=Math.pow(opd2,opd1);
    	operands.push(evalresult);
    }
    /*
     This method gets the precedence of the operators. If the current operator 
     has a lower precedence than the operator at the top of the stack, 
     the method returns true. 
     @ param operator
    */
    boolean getPrecedence(char operator){
    	boolean result=false;
    	char peekoperator=operators.peek();
    	switch(peekoperator){
    	case '+':
    	case '-':
    		precedence1=1;
    		break;
    	case '*':
    	case '/':
    	case '%':
    		precedence1=2;
    		break;
    	case '^':
    		precedence1=3;
    		break;
    	case '(':
    		precedence1=0;
    		break;
    	case ')':
    		precedence1=100;
    		break;
    	case '[':
    		precedence1=0;
    		break;
    	case ']':
    		precedence1=150;
    		break;
		}
		switch(operator){
    	case '+':
    	case '-':
    		precedence2=1;
    		break;
    	case '*':
    	case '/':
    	case '%':
    		precedence2=2;
    		break;
    	case '^':
    		precedence2=3;
    		break;
    	case '(':
    		precedence2=0;
    		break;
    	case ')':
    		precedence2=100;
    		break;
    	case '[':
    		precedence2=0;
    		break;
    	case ']':
    		precedence2=150;
    		break;
    	}
    	if(operators.isEmpty());
    		result=false;
    	if(precedence1<precedence2)
    		result=false;
    	if(precedence1>=precedence2)
    		result=true;
    	return result;
    	
    }

}

