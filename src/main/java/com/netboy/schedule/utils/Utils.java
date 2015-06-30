package com.netboy.schedule.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;

/**
 * 常用工具集合
 * 
 * @author quzhu.wl 2015年4月17日下午1:43:04
 */
public class Utils {


	private ThreadLocal<Gson> gsons = new ThreadLocal<Gson>() {
		@Override
		protected Gson initialValue() {
			return new Gson();
		}
	};

	public Gson getGson() {
		return gsons.get();
	}
	/**
	 * 将字符串类型的kv格式转换为Properties 类似于map
	 * 
	 * @param info
	 * @return
	 * @throws IOException
	 */
	public static Properties str2Property(String info) throws IOException {
		StringReader reader = new StringReader(info);
		Properties properties = new Properties();
		properties.load(reader);
		return properties;
	}

	/**
	 * 带有线程池管理handler的线程池
	 */
	public void createPool() {
		// 使用guava的 threadfactory
		final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("server-switch--%d")
				.setDaemon(false).build();
		// 带有线程池满时 处理 当前线程处理机制
		ThreadPoolExecutor fetchVsearchDataExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(20), threadFactory, new ThreadPoolExecutor.DiscardPolicy());
		Runnable job = new Runnable() {

			@Override
			public void run() {
			}
		};
		Future future = fetchVsearchDataExecutor.submit(job);
		// 终止线程
		future.cancel(false);
	}

	/**
	 * 创建定时执行的线程池
	 */
	public void createFixedPool() {
		final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("vsearchServer-switch--%d")
				.setDaemon(false).build();
		Runnable job = new Runnable() {

			@Override
			public void run() {
			}
		};
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, threadFactory);
		Future future = scheduledExecutorService.scheduleAtFixedRate(job, 1, 100, TimeUnit.SECONDS);
		// 终止线程
		future.cancel(false);
	}

	/**
	 * 将list 按照指定分隔符拼接在一起
	 * 
	 * @param list
	 * @param separator
	 * @return String
	 */
	public String joinStr(List<String> list, String separator) {
		return Joiner.on(separator).skipNulls().join(list);
	}

	/**
	 * 将目标字符串按照指定的分隔符进行分割，忽略空串、前后空格，以list形式返回
	 * 
	 * @param targetStr
	 * @param separator
	 * @return
	 */
	public List<String> splitStr(String targetStr, String separator) {
		if (Strings.isNullOrEmpty(targetStr))
			return null;
		return Splitter.on(separator).omitEmptyStrings().trimResults().splitToList(targetStr);
	}

	/**
	 * 替换
	 * @param targetStr
	 * @param match
	 * @param replaceStr
	 * @return
	 */
	public String replaceStr(String targetStr, char match, String replaceStr) {
		if (Strings.isNullOrEmpty(targetStr))
			return null;
		return CharMatcher.is(match).replaceFrom(targetStr, replaceStr);
	}

}
