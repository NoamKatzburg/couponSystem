package noam;

import java.util.ArrayList;
import java.util.List;

import noam.beans.Category;

public class Test2 {
	public static void main(String[] args) {

		// String[] w = { "one", "two", "three", "four" };

		List<Integer> nums = new ArrayList<Integer>();
		nums.add(3);
		nums.add(17);
		nums.add(666);
		nums.add(5);
		System.out.printf("%10s %10s %10s", "col1", "col2", "col3");
		System.out.println();
		for (int i = 0; i < nums.size(); i++) {
			System.out.printf("%10s %10s %10s", nums.get(0), nums.get(1), nums.get(2), nums.get(3));
			System.out.println();
		}
		;
	}
}
