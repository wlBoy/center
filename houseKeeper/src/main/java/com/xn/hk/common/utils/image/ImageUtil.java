package com.xn.hk.common.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @ClassName: ImageUtil
 * @Package: com.xn.hk.common.utils.image
 * @Description: 图片缩放工具类
 * @Author: wanlei
 * @Date: 2018年11月7日 下午2:23:28
 */
@SuppressWarnings("restriction")
public class ImageUtil {
	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	/**
	 * 压缩图片
	 * 
	 * @param srcFilePath
	 *            原始图像路径
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @return 返回处理后的图像
	 */
	public static BufferedImage zoomImage(String srcFilePath, double resizeTimes) {
		BufferedImage result = null;
		try {
			File srcfile = new File(srcFilePath);
			if (!srcfile.exists()) {
				logger.error("{}文件不存在", srcFilePath);
			}
			BufferedImage im = ImageIO.read(srcfile);
			/* 原始图像的宽度和高度 */
			int width = im.getWidth();
			int height = im.getHeight();
			/* 调整后的图片的宽度和高度 */
			int toWidth = (int) (width * resizeTimes);
			int toHeight = (int) (height * resizeTimes);
			/* 新生成结果图片 */
			result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);
			result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0,
					null);
		} catch (Exception e) {
			logger.error("压缩图片发生异常,原因为:{}", e);
		}
		return result;
	}

	/**
	 * 将压缩后的图片写到一个新路径中去
	 * 
	 * @param srcFilePath
	 *            原始图像路径
	 * @param resizeTimes
	 *            倍数,比如0.5就是缩小一半,0.98等等double类型
	 * @param desFilePath
	 *            图片写入的新路径
	 * @return 写入成功返回true，否则返回false
	 */
	public static boolean writeImg2File(String srcFilePath, double resizeTimes, String desFilePath) {
		BufferedImage im = zoomImage(srcFilePath, resizeTimes);
		boolean flag = false;
		try {
			/* 输出到文件流 */
			FileOutputStream newimage = new FileOutputStream(desFilePath);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
			/* 压缩质量 */
			jep.setQuality(0.9f, true);
			encoder.encode(im, jep);
			/* 近JPEG编码 */
			newimage.close();
			flag = true;
		} catch (Exception e) {
			logger.error("写入压缩后的图片发生异常,原因为:{}", e);
		}
		return flag;
	}

	/**
	 * 测试图片压缩
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/* 这儿填写你存放要缩小图片的文件夹全地址 */
		String inputFoler = "D:\\picture\\test\\3.jpg";
		/* 这儿填写你转化后的图片存放的文件夹 */
		String outputFolder = "D:\\picture\\test\\4.jpg";
		System.out.println(writeImg2File(inputFoler, 0.3, outputFolder));
	}
}
