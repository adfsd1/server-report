package cn.report.test;

import java.util.ArrayList;
import java.util.List;

public class Test2 {
	public static void main(String[] args) {

		
		String msg ="select * from test1 where age >= {query1} and {query2} <= 40";
		List<String> list = new Test2().extractMessage(msg );
		
		String msg1 = msg.replace("{"+list.get(0)+"}", "11");
		String msg2 = msg1.replace("{"+list.get(1)+"}", "12");
		System.out.println(msg2);
	}

	public List<String> extractMessage(String msg) {

		List<String> list = new ArrayList<String>();
		int start = 0;
		int startFlag = 0;
		int endFlag = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '{') {
				startFlag++;
				if (startFlag == endFlag + 1) {
					start = i;
				}
			} else if (msg.charAt(i) == '}') {
				endFlag++;
				if (endFlag == startFlag) {
					list.add(msg.substring(start + 1, i));
				}
			}
		}
		return list;
	}
}
