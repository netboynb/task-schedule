package com.netboy.schedule.utils;

import com.netboy.schedule.utils.FileLineReader.LineEach;

public class DealLineEach extends LineEach {

	@Override
	public void processLine(String line, int lineNum) {
		System.out.println(lineNum);
	}

}
