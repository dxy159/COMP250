

/**
 * This static class provides the isStatement() method to parse a sequence 
 * of tokens and to decide if it forms a valid statement
 * You are provided with the helper methods isBoolean() and isAssignment().
 * 
 * - You may add other methods as you deem necessary.
 * - You may NOT add any class fields.
 */
public class LanguageParser {
	
	/**
	 * Returns true if the given token is a boolean value, i.e.
	 * if the token is "true" or "false".
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	
	private static boolean isBoolean (String token) {
		
		return (token.equals("true") || token.equals("false"));
		
	}
	
	/**
	 * Returns true if the given token is an assignment statement of the
	 * type "variable=value", where the value is a non-negative integer.
	 * 
	 * DO NOT MODIFY THIS METHOD.
	 */
	private static boolean isAssignment (String token) {
		
		// The code below uses Java regular expressions. You are NOT required to
		// understand Java regular expressions, but if you are curious, see:
		// <http://java.sun.com/javase/6/docs/api/java/util/regex/Pattern.html>
		//
		//   This method returns true iff 
		//   the token matches the following structure:
		//   one or more letters (a variable), followed by
		//   an equal sign '=', followed by
		//   one or more digits.
		//   However, the variable cannot be a keyword ("if", "then", "else", 
		//   "true", "false")
		
		if (token.matches("if=\\d+") || token.matches("then=\\d+") ||
				token.matches("else=\\d+") || token.matches("end=\\d+") ||
				token.matches("true=\\d+") || token.matches("false=\\d+"))
			return false;
		else
			return token.matches("[a-zA-Z]+=\\d+");
		
	}

	/**
	 * Given a sequence of tokens through a StringSplitter object,
	 * this method returns true if the tokens can be parsed according
	 * to the rules of the language as specified in the assignment.
     */
				
	public static boolean  isStatement(StringSplitter splitter) {

		StringStack stack = new StringStack();
		int count = splitter.countTokens();
		String token;

		if (count == 0)
			return false;

		//   ADD YOUR CODE HERE
		
		
		//ITERATE THROUGH ALL TOKENS
		while(count > 0) {
			//RETRIEVE NEXT TOKEN AFTER EVERY LOOP
			token = splitter.nextToken();
			
			//CHECK IF STATEMENT IS AN ASSIGNMENT(ex. a=3) WHICH RETURNS TRUE
			//CHECK IF STATEMENT IS A BOOLEAN(ex. true) WHICH RETURNS FALSE
			if (count == 1 && isAssignment(token)) {
				return true;
			} else if (count == 1 && isBoolean(token)) {
				return false;
			}
			
			//WHEN TOKEN EQUALS "if"
			if (token.equals("if")) {
				//PUSH TOKEN ONTO STACK
				stack.push(token);
				//System.out.println("if added");
				
			//WHEN TOKEN IS A BOOLEAN
			} else if (isBoolean(token)) {
				//CHECK IF THE TOP OF STACK IS "if", RETURN FALSE OTHERWISE
				if (stack.peek().equals("if")) {
					stack.push(token);
				} else {
					return false;
				}
			//WHEN TOKEN EQUALS "then"
			} else if (token.equals("then")) {
				//CHECK IF THE TOP OF STACK IS BOOLEAN, RETURN FALSE OTHERWISE
				if (isBoolean(stack.peek())) {
					stack.push(token);
				} else {
					return false;
				}
			//WHEN TOKEN IS AN ASSIGNMENT
			} else if (isAssignment(token)) {
				//CHECK IF TOP OF STACK IS "then" OR "else", RETURN FALSE OTHERWISE
				if ((stack.peek().equals("then")) || (stack.peek().equals("else"))) {
					stack.push(token);
				} else {
					return false;
				}
				//FOR TEST #9, IF THERE IS AN ASSIGNMENT AFTER AN "end", THAT WILL RETURN FALSE
				if (stack.peek().equals("end")) {
					return false;
				}
			//WHEN TOKEN EQUALS "else"
			} else if (token.equals("else")) {
				//CHECK IF TOP OF STACK EQUALS "end" OR IS AN ASSIGNMENT, RETURN FALSE OTHERWISE
				if (stack.peek().equals("end") || isAssignment(stack.peek())) {
					stack.push(token);
				} else {
					//System.out.println("else is not preceded by assignment or then");
					return false;
				}
			//WHEN TOKEN EQUALS "end"
			} else if (token.equals("end")) {
				
				//POP ALL VALUES UNTIL YOU REACH "if"
				while (!stack.peek().equals("if")) {
					stack.pop();
				}
				stack.pop();
				//PUSH "end" ON TO STACK TO ENSURE THERE ISNT AN ASSIGNMENT OR ANY OTHER MISPLACED VALUES AFTER IT
				stack.push(token);
			} else {
				return false;
			}
			
			
//			DECREMENT COUNT
			count--;
		}
		//POP THE LAST END VALUE
		stack.pop();
		//RETURN TRUE IF STACK IS EMPTY, RETURN FALSE OTHERWISE
		if (stack.isEmpty()) {
			return true;
		}
		return false;
//		
//		
	}

}