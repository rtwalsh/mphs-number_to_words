// Copyright(c)2023 Excalibur Solutions, Inc. - All Rights Reserved

/*
 * Name: 	NumberToWords.java
 * Author: 	Robert Walsh
 * Date:	February 12, 2023
 * 
 * Description:
 * 
 * 	This program takes a number and converts it words 
 * 	as they would be written on a check. 
 * 
 *	The program supports numbers < 10,000
 */

package com.excalibursolutions.NumberToWords;

import java.util.Scanner;

public class NumberToWords {

	public static int MAX_NUMBER = 10000;
	
	public static String[] DIGIT_WORDS =  { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
	public static String[] TENS_DIGIT_WORDS = { "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety" };
	public static String[] TEENS_DIGIT_WORDS = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };
	
	public static void main(String[] args) {
		String input;
		if (args.length > 0) {
			input = args[0];
		} else {
			System.out.println("Please enter a number to convert to words: ");
			Scanner scanner = new Scanner(System.in);
			input = scanner.next();
			scanner.close();
		}
		
		try {
			NumberToWords numberToWords = new NumberToWords(Integer.parseInt(input));
			System.out.println(input + " in words is " + numberToWords.toString());
		} catch (NumberFormatException e) {
			System.out.println("Error: Unable to convert '" + input + "' to words.");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	private int thousandsDigit;
	private int hundredsDigit;
	private int tensDigit;
	private int onesDigit;
	
	public NumberToWords(int number) throws Exception {
		parseIntoDigits(number);
	}
	
	private void parseIntoDigits(int number) throws Exception {
		if (number < MAX_NUMBER) {
			thousandsDigit = number / 1000;			// this is integer division, so fractional part will be discarded
			if (thousandsDigit > 0) {
				number -= thousandsDigit * 1000;	// strip the thousands digit from the number to convert
			}
			
			hundredsDigit = number / 100;
			if (hundredsDigit > 0) {
				number -= hundredsDigit * 100;		// strip the hundreds digit from the number to convert
			}
			
			tensDigit = number / 10;
			if (tensDigit > 0) {
				number -= tensDigit * 10;			// strip the tens digit from the number to convert
			}
			
			onesDigit = number;						// what's left is the ones digit
		} else {
			throw new Exception("Number to convert must be less than " + MAX_NUMBER);
		}
	}
	
	public String toString() {
		String output = formatThousands();
		output += formatHundreds(output);
		output += formatTens(output);
		output += formatOnes(output);
		return output;
	}
	
	private String simpleFormat(String soFar, String placeValue, int digit) {
		String output = "";
		if (digit > 0) {
			if ((soFar != null) && !soFar.isEmpty()) {
				output += " ";
			}
			output += DIGIT_WORDS[digit - 1] + " " + placeValue;
		}
		return output;
	}

	private String formatThousands() {
		return simpleFormat(null, "thousand", thousandsDigit);
	}
	
	private String formatHundreds(String soFar) {
		return simpleFormat(soFar, "hundred", hundredsDigit);
	}

	private String formatTens(String soFar) {
		String output = "";
		if (tensDigit > 0) {
			if (!soFar.isEmpty()) {
				output += " ";
			}
			
			if (tensDigit == 1) {
				output += TEENS_DIGIT_WORDS[onesDigit];
			} else {
				output += TENS_DIGIT_WORDS[tensDigit - 2];
			}
		}
		return output;
	}		
	
	private String formatOnes(String soFar) {
		String output = "";
		if ((onesDigit > 0) && (tensDigit != 1)) {
			if (tensDigit > 1) {
				output += "-";
			} else if (!soFar.isEmpty()) {
				output += " ";
			}
			output += DIGIT_WORDS[onesDigit - 1];
		}
		return output;
	}
}
