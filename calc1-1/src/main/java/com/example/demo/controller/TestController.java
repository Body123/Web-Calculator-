package com.example.demo.controller;

import java.util.Stack;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller

public class TestController {
	
	@RequestMapping("/")
	public String stert () {
		
		return "index";
	}
	@ResponseBody
	@GetMapping("/eual")
	public String  eual(@RequestParam String exp) {
		//string after edit the expression for negative in the first 
		 String editExp=EditExpression(exp);
		 if(editExp.equals("Invalid Expression")) {
			 //return invalid if error occur
				return "Invalid Expression";
			}
		 //String after infix to post fix
		 String infixToPostfix=infixToPostfix(editExp);
		 if(infixToPostfix.equals("Invalid Expression")) {
			 //return invalid if error occur

				return "Invalid Expression";
			}
		 //final result 
		 String result = evaluatePostfix(infixToPostfix);
		 return result;
		
	}
	
	/////////////////////////mod function ////////////////////////////
	@ResponseBody
	@GetMapping("/percent")
	public String  mod(@RequestParam String percent) {
		 float number =Float.parseFloat(percent);
		 
		 
		 return String.valueOf(number/100);
		
	}
	////////////////////square function ///////////////////
	@ResponseBody
	@GetMapping("/square")
	public String  square(@RequestParam String square) {
		 float number =Float.parseFloat(square);
		 
		 
		 return String.valueOf(number*number);
		
	}
	
	////////////////square root function ////////////////////
	@ResponseBody
	@GetMapping("/squareroot")
	public String  squareroot(@RequestParam String squareroot) {
		 float number =Float.parseFloat(squareroot);
		 
		 if(number<0) {
			 return "Invalid Expression";
		 }
		 return String.valueOf(Math.sqrt(number));
		
	}
	
	////////////////////////////////////1/x function ////////////////
	@ResponseBody
	@GetMapping("/oneOverX")
	public String  oneOverX(@RequestParam String oneOverX) {
		 float number =Float.parseFloat(oneOverX);
		 if(number==0) {
			 return "Invalid Expression";
		 }
		 return String.valueOf(1/number);
		
	}
	///////////////toggle function /////////////////////
	@ResponseBody
	@GetMapping("/toggle")
	public String  toggle(@RequestParam String toggle) {
		 float number =Float.parseFloat(toggle)*-1;
		 if(number==0) {
			 return "Invalid Expression";
		 }
		 return String.valueOf(number);
		
	}
	/////////////////////////priority of the top of the stack  ///////////////////
	
	static int periotityOfTop(char ch) 
    { 
        switch (ch) 
        { 
        case '+': 
        case '-': 
            return 1; 
       
        case '*': 
        case '/': 
            return 2; 
        } 
        return -1; 
    } 
	//////////////////infix to post fix function 
	
	static String infixToPostfix(String expression) 
    { 
		
		for(int o=0; o<expression.length();o++) {
			if(expression.charAt(o)=='+'||expression.charAt(o)=='-'||expression.charAt(o)=='*'||expression.charAt(o)=='/') {
				while((o+1)<expression.length()&&expression.charAt(o)==' ') {
					o++;
				}
			if(expression.charAt(o)=='+'||expression.charAt(o)=='-'||expression.charAt(o)=='(') {
				expression=EditExpression(expression);
				}
			}
		}
        // initializing empty String for result 
        String result = new String(""); 
          
        // initializing empty stack 
        Stack<Character> Stack = new Stack<>(); 
          
        for(int i=0; i<expression.length();i++) {
            
			if(expression.charAt(i)==' ') {
				continue; ///check spaces 
			}
			//check ing or symbols 
			else if(Character.isDigit(expression.charAt(i))||(expression.charAt(i)=='.')) {
				while(Character.isDigit(expression.charAt(i))||(expression.charAt(i)=='.')) {
					result+=expression.charAt(i++); //concatenate the string 
					if(i==expression.length()) break; 
				}
				i=i-1;///subtract counter 
				result+=' '; //concatenate the space 
                                
				continue;
				////////////////////handling the square square brackets////////////////
			}else if(expression.charAt(i)=='(') {
				Stack.push(expression.charAt(i));
			}else if(expression.charAt(i)==')') {
				if(Stack.isEmpty()){
                             ///////////////check errors        
					return "Invalid Expression"; 
                                }
				while(Stack.peek()!= (Object)'(') {
				result+=Stack.pop(); //pop from stack 
				result+=' ';//add space to result 
				}if(Stack.isEmpty()){
                             /////check error 
					return "Invalid Expression"; 
                                }
				Stack.pop();////////pop from stack 
				
			}else {
				while(!Stack.isEmpty()&&periotityOfTop(expression.charAt(i))<=periotityOfTop(Stack.peek())){
					result+=Stack.pop();//pop from stack 
					result+=' ';
				}
				if(!(expression.charAt(i)=='+')&&!(expression.charAt(i)=='-')&&!(expression.charAt(i)=='/')&&!(expression.charAt(i)=='*')) {
                            //////////////////check error occur             
					return "Invalid Expression"; 
				}
				Stack.push(expression.charAt(i));
				
			}
			
		}
       
        // pop all the operators from the stack 
        while (!Stack.isEmpty()){ 
            if(Stack.peek() == '(') 
                return "Invalid Expression"; //check errors 
            result += Stack.pop(); //pop from stack 
         } 
        
        String[] checkForPoint = result.split(" ");
        /////////////////handling point sign ///////////////
        for(String x:checkForPoint) {
        	int counter=0;
        	for(int subX=0;subX<x.length();subX++) {
        		if(x.charAt(subX)=='.') {//check occurrence of point sign 
        			counter++;
        		}
        	}
        	if(counter>1) {
        		//check error
        		return "Invalid Expression"; 
        	}
        }
        	
        return result; ////return the result 
    }
	
	static String  evaluatePostfix(String expression) 
    { 
        //create a stack 
        Stack<Float> StackForEvaluate=new Stack<>(); 
          /////////define variables ////////
        float addedToStack,firstOperand,secondOperand,result=0;
		String sub="";
		for(int i=0; i<expression.length();i++) {
			if(expression.charAt(i)==' ') {
				///////////////////check spaces 
				continue;
			}
			if(Character.isDigit(expression.charAt(i))||expression.charAt(i)=='.') {
				while((i+1)<expression.length()&&expression.charAt(i+1)!=' ') {
					sub+=expression.charAt(i++);///concatenate string
				}
				sub+=expression.charAt(i++); ///concatenate string
				
				addedToStack=Float.parseFloat(sub);//parse string to float 
				StackForEvaluate.push(addedToStack); //push to stack
				sub="";
				
				
			}
			else {
				secondOperand=(float) StackForEvaluate.pop();//pop operands 
				firstOperand=(float) StackForEvaluate.pop();//pop operands 
				String checkForInvalid=ourOperation(firstOperand,secondOperand,expression.charAt(i));
				if(checkForInvalid.equals("Invalid Expression")) {
					return "Invalid Expression";//check errors 
				}
				result=Float.parseFloat(checkForInvalid);//parse to float 
				StackForEvaluate.push(result);//push in the stack 
			}
		}
                
                if(!StackForEvaluate.isEmpty())
		result=(float) StackForEvaluate.pop();///////////pop the result 
                else{/////////////check errors 
                	return "Invalid Expression";
                }  
		//result=(float) StackForEvaluate.pop();
		return String.valueOf(result);  ////////////return the result 
    } 
	///////////////////////check operation 
	public static String ourOperation(float x,float y,char operator) {
		float result=0;
		if(operator=='+') {//add
			result=x+y;
		}else if(operator=='-') {//subtract
			result=x-y;
		}else if(operator=='*') {//multiplication 
			result=x*y;
		}else if(operator=='/') {
			if(y==0) {////check divide by zero 
				return "Invalid Expression"; 
				
			}
			result=x/y;
		}
		else {
			//check errors happens 
			return "Invalid Expression"; 
			
                        
		}
		
		return String.valueOf(result);
		
	}
	
	
	public static  String EditExpression(String x) {
		String y=""; //variable of the result 
		
		for(int i=0;i<x.length();i++) {
			//check having one sign in the first after spaces
			if((y=="")&&x.charAt(i)==' '){
				///check for space 
				while((i+1<x.length())&&(x.charAt(i+1)==' ')) {
					i++;//increment counter 
                                        

				}
				if((i+1<x.length())&&(x.charAt(i+1)=='-'||x.charAt(i+1)=='+')) {
					i++;//increment counter 
					if(x.charAt(i)=='-')y+="(0-";//if sign negative in the first 
					else y+="(0+";
					if((((i+1)<x.length())&&x.charAt(i+1)=='(')) {
						i++;
						y+=x.charAt(i);////////////check the char
						while(((i+1)<x.length())&&!(x.charAt(i+1)==')')) {
							i++;////increment counter 
							y+=x.charAt(i);
						}
						y+=")";
						continue;
					}else {
					while(((i+1)<x.length())&&(Character.isDigit(x.charAt(1+i))||Character.isLetter(x.charAt(1+i))||x.charAt(1+i)=='.')) {
						i++;
						y+=x.charAt(i);
					}
					y+=" )";
					continue;
					}
				}
			}
			//check having one sign in the first
			if((y=="")&&(x.charAt(0)=='-'||x.charAt(0)=='+')){
				
				if(x.charAt(0)=='-')y+="(0-"; //check for negative in the first 
				else y+="(0+";
				if((((i+1)<x.length())&&x.charAt(i+1)=='(')) {
					i++;
					y+=x.charAt(i);///concatenate string
					while(((i+1)<x.length())&&!(x.charAt(i+1)==')')) {
						i++;
						y+=x.charAt(i);/////concatenate string
					}
					y+=")";/////////////////end of the operation 
					continue;
				}else {
				while(((i+1)<x.length())&&(Character.isDigit(x.charAt(1+i))||Character.isLetter(x.charAt(1+i))||x.charAt(1+i)=='.')) {
					i++;//increment counter 
					y+=x.charAt(i);///concatenate string
				}
				y+=')';//end the operation 
                                
				continue;
				}
			}
			      //check having one sign after the operand
			if(x.charAt(i)=='+'||x.charAt(i)=='-'||x.charAt(i)=='/'||x.charAt(i)=='*') {
				y+=x.charAt(i);
				
				while(((i+1)<x.length())&&!Character.isDigit(x.charAt(1+i))&&!Character.isLetter(x.charAt(1+i))&&!(x.charAt(1+i)=='.')) {
					i++;///increment counter 
					if(x.charAt(i)=='+') {////////////check for plus 
						while(((i+1)<x.length())&&x.charAt(i+1)==' ') {
							i++;
							y+=' ';
						}
						//check wrong cases
						if(((i+1)<x.length())&&x.charAt(i+1)=='+') throw new RuntimeException("Wrong expression");
						y+="(0+";/////concatenate string
						if((((i+1)<x.length())&&x.charAt(i+1)=='(')) { 
							i++;//increment counter 
							y+=x.charAt(i);///////concatenate string
							while(((i+1)<x.length())&&!(x.charAt(i+1)==')')) {
								i++;//increment counter 
								y+=x.charAt(i);///concatenate string
							}
							y+=")";///end of the operation 
								
						}else {										
						while(((i+1)<x.length())&&!(x.charAt(1+i)=='+')&&!(x.charAt(1+i)=='-')&&!(x.charAt(1+i)=='/')&&!(x.charAt(1+i)=='*')) {
							i++;//increment counter 
							
							y+=x.charAt(i);///concatenate string
						}
						y+=")";//end of the operation 
						break;
						}
					}else if(x.charAt(i)=='-') {
						y+="(0-";///concatenate string check negative in the first 
						if((((i+1)<x.length())&&x.charAt(i+1)=='(')) {
							i++;//increment counter 
							y+=x.charAt(i);///concatenate string
							while(((i+1)<x.length())&&!(x.charAt(i+1)==')')) {
								i++;//increment counter 
								y+=x.charAt(i);///concatenate string
							}
							y+=")";//end the operation 
								
						}else {
						while(((i+1)<x.length())&&x.charAt(i+1)==' ') {////check for space 
							i++;//increment counter 
							y+=' ';///concatenate string
						}
						if(((i+1)<x.length())&&x.charAt(i+1)=='-') throw new RuntimeException("Wrong expression");
						while(((i+1)<x.length())&&!(x.charAt(1+i)=='+')&&!(x.charAt(1+i)=='-')&&!(x.charAt(1+i)=='/')&&!(x.charAt(1+i)=='*')) {
							i++;//increment counter 
							y+=x.charAt(i);///concatenate string
						}
						y+=")";//end the operation 
						break;
						}
					}else if(x.charAt(i)=='*'||x.charAt(i)=='/') throw new RuntimeException("Wrong expression");
					else {
						y+=x.charAt(i);///concatenate string
					}
					
				}
				
			}
			else {
				y+=x.charAt(i);///concatenate string
			}
		}
               
		return y;
		
	}
	
}
