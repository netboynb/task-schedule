package com.netboy.schedule.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 
 * 处理大文件数据
 * 
 * @author quzhu.wl 2015年6月30日下午1:53:07
 */
public class FileLineReader {
	private static Charset charset = Charset.forName("UTF-8");

	public static abstract class LineEach {
		private File file;

		public abstract void processLine(String line, int lineNum);

		public void setFile(File file) {
			this.file = file;
		}

		public File getFile() {
			return file;
		}
	}

	private final File targetFile;
	private final BufferedReader reader;

	private int readedLine;

	public FileLineReader(File targetFile) throws IOException {
		this(targetFile, charset);
	}

	public FileLineReader(File targetFile, Charset charset) throws IOException {
		this.targetFile = targetFile;
		reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile), charset));
	}

	public void eachLine(LineEach lineEach) throws IOException {
		String line = null;
		while ((line = nextLine()) != null) {
			readedLine++;
			line = line.trim();
			if (line.length() < 1) {
				continue;
			}
			lineEach.processLine(line, readedLine);
		}
	}

	public String nextLine() throws IOException {
		return reader.readLine();
	}

	public void close() throws IOException {
		reader.close();
	}

	public File getTargetFile() {
		return targetFile;
	}

	public int getReadedLine() {
		return readedLine;
	}
}
