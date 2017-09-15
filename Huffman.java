
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class HuffmanCoding {

	public static void main(String[] args) throws IOException {

		FileInputStream in = null;
		FileOutputStream out = null;
		String s = "";

		try {
			in = new FileInputStream("a.txt");

			int c;
			while ((c = in.read()) != -1) {
				s = s + ((char) c);
			}
		} finally {
			if (in != null) {
				in.close();
			}
		}
		s = s.toLowerCase();

		char[] letters = new char[27];
		int[] lettersOccurrence = new int[27];

		for (int i = 0; i < s.length(); i++) {
			try {
				letters[(s.charAt(i) - 97)] = s.charAt(i);
				lettersOccurrence[(s.charAt(i) - 97)] = lettersOccurrence[(s.charAt(i) - 97)] + 1;
			} catch (Exception e) {
				letters[26] = s.charAt(i);
				lettersOccurrence[26] = lettersOccurrence[26] + 1;
			}
		}

		// System.out.println("Printing UnSorted");
		// for (int i = 0; i < lettersOccurrence.length; i++) {
		// System.out.println(letters[i] + " = " + lettersOccurrence[i]);
		// }

		int[] lettersOccurrenceSorted = new int[27];
		System.arraycopy(lettersOccurrence, 0, lettersOccurrenceSorted, 0, lettersOccurrence.length);
		Arrays.sort(lettersOccurrenceSorted);

		// reversing lettersOccurrenceSorted array
		for (int i = 0; i < lettersOccurrenceSorted.length / 2; i++) {
			int temp = lettersOccurrenceSorted[i];
			lettersOccurrenceSorted[i] = lettersOccurrenceSorted[lettersOccurrenceSorted.length - 1 - i];
			lettersOccurrenceSorted[lettersOccurrenceSorted.length - 1 - i] = temp;
		}

		char[] lettersSorted = new char[27];
		int[] temp = new int[27];
		System.arraycopy(lettersOccurrence, 0, temp, 0, lettersOccurrence.length);

		int count = 0;
		for (int i = 0; i < lettersOccurrenceSorted.length; i++) {
			for (int j = 0; j < temp.length; j++) {
				if (lettersOccurrenceSorted[i] == temp[j]) {
					temp[j] = 0;

					int j2 = 0;
					for (j2 = 0; j2 < lettersSorted.length; j2++) {
						if (letters[j] == lettersSorted[j2]) {
							break;
						}
					}
					if (j2 == lettersSorted.length) {
						lettersSorted[count] = letters[j];
					}
					count++;
				}
			}
		}

		// System.out.println("Printing Sorted");
		// for (int i = 0; i < lettersOccurrence.length; i++) {
		// System.out.println(lettersSorted[i] + " = " +
		// lettersOccurrenceSorted[i]);
		// }

		int point = 0;
		for (point = 0; point < lettersOccurrenceSorted.length; point++) {
			if (lettersOccurrenceSorted[point] == 0) {
				break;
			}
		}

		// System.out.println("***********************");
		int[] temp2 = new int[point];
		for (int i = 0; i < temp2.length; i++) {
			temp2[i] = lettersOccurrenceSorted[i];
		}
		lettersOccurrenceSorted = temp2;

		char[] temp3 = new char[point];
		for (int i = 0; i < temp3.length; i++) {
			temp3[i] = lettersSorted[i];
		}
		lettersSorted = temp3;
		// System.out.println("***********************");

		System.out.println("**********Printing Sorted Short**********");
		System.out.println("Printing Sorted");
		for (int i = 0; i < lettersSorted.length; i++) {
			System.out.println(lettersSorted[i] + " = " + lettersOccurrenceSorted[i]);
		}

		// moving to a string system
		String[] lettersSortedString = new String[point];
		for (int i = 0; i < lettersSortedString.length; i++) {
			lettersSortedString[i] = "" + lettersSorted[i];
		}

		// Value Storing Array
		String[] values = new String[point];
		for (int i = 0; i < values.length; i++) {
			values[i]="";
		}

		int[] LOS = new int[point];
		System.arraycopy(lettersOccurrenceSorted, 0, LOS, 0, lettersOccurrenceSorted.length);

		String[] LSS = new String[point];
		System.arraycopy(lettersSortedString, 0, LSS, 0, lettersSortedString.length);

		while (true) {
			if (LSS.length == 1) {
				break;
			}
			// Part 01
			String left = LSS[LSS.length - 2];
			String right = LSS[LSS.length - 1];

			// use "lettersSorted" to match ; and put values to "values" array

			// for left
			for (int i = 0; i < left.length(); i++) {
				for (int j = 0; j < lettersSorted.length; j++) {
					if (left.charAt(i) == lettersSorted[j]) {
						values[j] = values[j] + 0;
					}
				}
			}

			// for right
			for (int i = 0; i < right.length(); i++) {
				for (int j = 0; j < lettersSorted.length; j++) {
					if (right.charAt(i) == lettersSorted[j]) {
						values[j] = values[j] + 1;
					}
				}
			}

			// get that one to insert into the array
			String OneToInsert = left + right;

			// Part 02

			int sumOfLastTwo = LOS[LOS.length - 2] + LOS[LOS.length - 1];

			int insertPoint = -1;
			for (int i = 0; i < LOS.length; i++) {
				if(LOS[i] <= sumOfLastTwo)   {
					insertPoint = i;
					break;
				}
			}

			LOS = addToThisPoint(LOS, insertPoint, sumOfLastTwo);
			//			System.out.println();
			//			System.out.println(sumOfLastTwo +"  "+ insertPoint);
			//			System.out.println("Printing LOS");
			//			print(LOS);

			// part 03

			LSS = addStringToThisPoint(LSS, insertPoint, OneToInsert);
			System.out.println();
			System.out.println(OneToInsert +" "+ insertPoint);
			System.out.println("Printing LSS");
			print(LSS);


		} // while ENDs

		System.out.println("-----------------------------");
		for (int i = 0; i < values.length; i++) {
			System.out.println(lettersSorted[i]+ " = "+values[i]);
		}

	}

	private static void print(int[] Array) {
		for (int i : Array) {
			System.out.print(i+" ");
		}

	}

	private static void print(String[] Array) {
		for (String s : Array) {
			System.out.print(s+" ");
		}

	}

	private static String[] addStringToThisPoint(String[] LSS, int insertPoint, String oneToInsert) {

		String[] temp = new String[LSS.length - 1];

		for (int i = 0; i < insertPoint; i++) {
			temp[i] = LSS[i];
		}
		temp[insertPoint] = oneToInsert;

		for (int i = insertPoint+1; i < temp.length; i++) {
			temp[i] = LSS[i-1];
		}

		return temp;
	}

	private static int[] addToThisPoint(int[] LOS, int insertPoint, int sum) {

		int[] temp = new int[LOS.length - 1];

		for (int i = 0; i < insertPoint; i++) {
			temp[i] = LOS[i];
			//	System.out.print(" p1 = "+LOS[i]);
		}
		temp[insertPoint] = sum;
		//System.out.println();

		for (int i = insertPoint+1; i < temp.length; i++) {
			temp[i] = LOS[i-1];
			//System.out.print(" p2 = "+LOS[i]);
		}

		return temp;
	}

}
