package com.netboy.schedule.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * 
 * 
 * @author quzhu.wl 2015年6月30日下午1:54:07
 */
public class FileLineReaderTest {

	@Test
	public void testEachLine() throws IOException, InterruptedException {

		File localFile = new File("F:\\resultFile");
		Charset charset = Charset.forName("UTF-8");

		final FileLineReader fileLineReader = new FileLineReader(localFile, charset);

		final DealLineEach dealLineEach = new DealLineEach();

		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					long start = System.currentTimeMillis();
					fileLineReader.eachLine(dealLineEach);
					long count = System.currentTimeMillis() - start;
					System.out.println("total use " + count + "ms");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
		while (true) {
			Thread.sleep(5000);
			System.out.println("");
		}
	}

}
