package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary
{
	private String number="0";  // string containing the binary value '0' or '1'
	/**
	* A constructor that generates a binary object.
	*
	* @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored.   Trailing zeros will be excluded and empty string will be considered as zero.
	*/
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}
	
		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}
	
		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}
	
		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);
	

		if (this.number.isEmpty()) { // replace empty strings with a single zero
			this.number = "0";
		}
	}
	/**
	* Return the binary value of the variable
	*
	* @return the binary value in a string format.
	*/
	public String getValue()
	{
		return this.number;
	}
	/**
	* Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	*
	* @param num1 The first addend object
	* @param num2 The second addend object
	* @return A binary variable with a value of <i>num1+num2</i>.
	*/
	public static Binary add(Binary num1,Binary num2)
	{
		// the index of the first digit of each number
		int ind1=num1.number.length()-1;
		int ind2=num2.number.length()-1;
		//initial variable
		int carry=0;
		String num3="";  // the binary value of the sum
		while(ind1>=0 ||  ind2>=0 || carry!=0) // loop until all digits are processed
		{
			int sum=carry; // previous carry
			if(ind1>=0){ // if num1 has a digit to add
				sum += (num1.number.charAt(ind1)=='1')? 1:0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if(ind2>=0){ // if num2 has a digit to add
				sum += (num2.number.charAt(ind2)=='1')? 1:0; // convert the digit to int and add it to sum
				ind2--; //update ind2
			}
			carry=sum/2; // the new carry
			sum=sum%2;  // the resultant digit
			num3 =( (sum==0)? "0":"1")+num3; //convert sum to string and append it to num3
		}
		Binary result=new Binary(num3);  // create a binary object with the calculated value.
		return result;
		
	}

	/**
	 * A method that generates the result of performing operation OR: bitwise logical OR.
	 * @param num1 This is the first binary variable
	 * @param num2 This is the second binary variable
	 * @return Output of function which is also in binary function.
	 */
	public static Binary or(Binary num1, Binary num2){

		int difference = num2.number.length() - num1.number.length();
		if( difference > 0){ // if difference is positive, num 1 is shorter
			// add 'difference' number of zeroes to beginning of num1 to make both values equal length
			for(int i=0; i<difference; i++){
				num1.number = "0" + num1.number;
			}
		}
		else if( difference < 0){ //if difference is negative, num2 is shorter
			// add 'difference' number of zeroes to beginning of num2 to make both values equal length
			for(int i=0; i < -difference; i++){
				num2.number = "0" + num2.number;
			}
		}

		//OR operation

		//holds result as binary value of OR method
		String result = "";

		//loop through the bits starting from least significant bit which is last index
		//both numbers are now of same length so use either as reference
		int index = num1.number.length()-1;
		for(int i=index; i >= 0; i--){
			char bitNum1 = num1.number.charAt(i);
			char bitNum2 = num2.number.charAt(i);

			if(bitNum1 == '1' || bitNum2 == '1'){
				result = "1" + result;
			}
			else{
				result = "0" + result;
			}

		}

		return new Binary(result);
	}

	/**
	 * A method that generates the result of performing operation AND: bitwise logical AND.
	 * @param num1 This is the first binary variable
	 * @param num2 This is the second binary variable
	 * @return Output of function which is also in binary function.
	 */
	public static Binary and(Binary num1, Binary num2){

		int difference = num2.number.length() - num1.number.length();
		if( difference > 0){ // if difference is positive, num 1 is shorter
			// add 'difference' number of zeroes to beginning of num1 to make both values equal length
			for(int i=0; i<difference; i++){
				num1.number = "0" + num1.number;
			}
		}
		else if( difference < 0){ //if difference is negative, num2 is shorter
			// add 'difference' number of zeroes to beginning of num2 to make both values equal length
			for(int i=0; i<-difference; i++){
				num2.number = "0" + num2.number;
			}
		}

		//AND operation

		//holds result as binary value of AND method
		String result = "";

		//loop through the bits starting from least significant bit which is last index
		//both numbers are now of same length so use either as reference
		int index = num1.number.length()-1;
		for(int i=index; i >= 0; i--){
			char bitNum1 = num1.number.charAt(i);
			char bitNum2 = num2.number.charAt(i);

			if(bitNum1 == '1' && bitNum2 == '1'){
				result = "1" + result;
			}
			else{
				result = "0" + result;
			}

		}

		return new Binary(result);
	}


	/**
	 * A method that generates the result of performing operation multiply: multiply two binary variables.
	 * @param num1 This is the first binary variable
	 * @param num2 This is the second binary variable
	 * @return Output of function which is also in binary function.
	 */

	public static Binary multiply(Binary num1, Binary num2){

		String result = "";  //holds result as a string to be converted to binary later
		int shifted=0; //tracks the number of positions to be shifted when iterated through digits in num2

		//start with last index of second number and compare to all values in num1 (LSB to MSB)
		for (int i=num2.number.length()-1;i>=0; i-- ){

			if(num2.number.charAt(i) == '1'){

				String currentAnswer = num1.number + "0".repeat(shifted); //this adds zeroes based on the shift level
				result = add(new Binary(result), new Binary(currentAnswer)).number; //add to the result
			}

			//increment because each index in num2 causes the answer to shift more to the left as seen in regular multiplication
			shifted++;
		}
		return new Binary(result);
	}
}
