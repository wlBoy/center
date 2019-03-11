package com.xn.hk.common.utils.os;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang3.ArrayUtils;

import com.xn.hk.common.constant.Constant;

import java.io.*;

/**
 * 
 * @ClassName: ProcUtil
 * @Package: com.xn.hk.common.utils.os
 * @Description: 进程相关的函数(使用开源的Java调用外部程序类库Apache Commons Exce)
 * @Author: wanlei
 * @Date: 2019年3月11日 上午10:57:28
 */
public class ProcUtil {

	public static String exec(String... cmd) throws Exception {
		return exec(cmd, null, Constant.UTF8, null);
	}

	public static String exec(String[] cmd, String inData, String charSet, String[] envp) throws Exception {
		try {
			StringBuffer result = new StringBuffer();
			// start command running
			Process proc = Runtime.getRuntime().exec(cmd, envp);
			// 标准输入
			if (inData != null) {
				OutputStream ostr = proc.getOutputStream();
				ostr.write(inData.getBytes(charSet));
				ostr.close();
			}
			// get command's output stream and
			// put a buffered reader input stream on it
			InputStream istr = proc.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(istr, charSet));
			InputStream estr = proc.getErrorStream();
			BufferedReader ebr = new BufferedReader(new InputStreamReader(estr, charSet));
			// read output lines from command
			String str;
			while ((str = br.readLine()) != null) {
				result.append(str);
				result.append('\n');
			}
			// wait for command to terminate
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				result.append("\n操作进程被中断");
				Thread.currentThread().interrupt();
			}
			br.close();
			if (proc.exitValue() != 0) {
				result.append("\n返回值是" + proc.exitValue());
				result.append("\n错误输出：\n");
				while ((str = ebr.readLine()) != null) {
					result.append(str);
				}
				throw new Exception(result.toString());
			}
			return result.toString();
		} catch (Exception e) {
			// 重新创建一个Exception实例，将命令信息写到message里，这里不考虑外部对异常类型的捕捉
			throw new Exception(
					String.format("excute command [ %s ] error", ArrayUtils.toString(cmd).replaceAll("[{}]", "")), e);
		}
	}

	/**
	 * 执行一个命令，期望返回为0，否则异常
	 * 
	 * @param cmd
	 * @throws Exception
	 */
	public static void exec(String cmd) throws Exception {
		exec(cmd, 0);
	}

	/**
	 * 执行一个命令，期望返回为expectExit，否则异常
	 * 
	 * @param cmd
	 * @param expectExit
	 * @throws Exception
	 */
	public static void exec(String cmd, int expectExit) throws Exception {
		try {
			CommandLine cl = CommandLine.parse(cmd);
			DefaultExecutor exec = new DefaultExecutor();
			exec.setExitValue(expectExit);
			exec.execute(cl);
		} catch (Exception e) {
			// 重新创建一个Exception实例，将命令信息写到message里，这里不考虑外部对异常类型的捕捉
			throw new Exception(String.format("excute command [ %s ] error, expectExit %s", cmd, expectExit), e);
		}
	}

	/**
	 * 执行一个命令，返回标准输出。期望返回为0，否则异常
	 * 
	 * @param cmd
	 * @return
	 * @throws Exception
	 */
	public static String checkOutput(String cmd) throws Exception {
		return checkOutput(cmd, 0);
	}

	/**
	 * 执行一个命令，返回标准输出。期望返回为expectExit，否则异常
	 * 
	 * @param cmd
	 * @param expectExit
	 * @return
	 * @throws Exception
	 */
	public static String checkOutput(String cmd, int expectExit) throws Exception {
		return checkOutput(cmd, expectExit, null);
	}

	public static String checkOutput(String cmd, int expectExit, String charsetName) throws Exception {
		return checkOutput(cmd, new int[] { expectExit }, charsetName);
	}

	/**
	 * 执行一个命令，返回标准输出。期望返回为expectExit，否则异常
	 * 
	 * @param cmd
	 * @param expectExits
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static String checkOutput(String cmd, int[] expectExits, String charsetName) throws Exception {

		try {
			ByteArrayOutputStream stdout = new ByteArrayOutputStream();
			try {
				PumpStreamHandler psh = new PumpStreamHandler(stdout);
				CommandLine cl = CommandLine.parse(cmd);
				DefaultExecutor exec = new DefaultExecutor();
				exec.setExitValues(expectExits);
				exec.setStreamHandler(psh);
				exec.execute(cl);
				if (charsetName == null) {
					return stdout.toString();
				} else {
					return stdout.toString(charsetName);
				}
			} finally {
				stdout.close();
			}
		} catch (Exception e) {
			// 重新创建一个Exception实例，将命令信息写到message里，这里不考虑外部对异常类型的捕捉
			throw new Exception(String.format("excute command [ %s ] error, expectExits %s", cmd,
					ArrayUtils.toString(expectExits).replaceAll("[{}]", "")), e);
		}

	}

}
