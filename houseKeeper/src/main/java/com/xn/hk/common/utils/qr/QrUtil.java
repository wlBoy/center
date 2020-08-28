package com.xn.hk.common.utils.qr;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/**
 * 
 * @ClassName: QrUtil
 * @Package: com.xn.hk.common.utils.qr
 * @Description: 二维码图片生成工具类
 * @Author: wanlei
 * @Date: 2020年8月28日 上午11:11:19
 */
public final class QrUtil {
	/**
	 * 保存二维码图片类型
	 */
	private static final String QR_CODE_IMAGE_TYPE = "png";

	private QrUtil() {
	}

	/**
	 * Generate qr code.
	 *
	 * @param stream
	 *            the stream
	 * @param qrContent
	 *            the qrContent
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	private static void generateQRCode(final OutputStream stream, final String qrContent, final int width,
			final int height) {
		try {
			final Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
			hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.name());
			hintMap.put(EncodeHintType.MARGIN, 1);
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);

			final QRCodeWriter qrCodeWriter = new QRCodeWriter();
			final BitMatrix byteMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height, hintMap);
			final int byteMatrixWidth = byteMatrix.getWidth();
			final BufferedImage image = new BufferedImage(byteMatrixWidth, byteMatrixWidth, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();

			final Graphics2D graphics = (Graphics2D) image.getGraphics();
			try {
				graphics.setColor(Color.WHITE);
				graphics.fillRect(0, 0, byteMatrixWidth, byteMatrixWidth);
				graphics.setColor(Color.BLACK);

				IntStream.range(0, byteMatrixWidth).forEach(i -> IntStream.range(0, byteMatrixWidth)
						.filter(j -> byteMatrix.get(i, j)).forEach(j -> graphics.fillRect(i, j, 1, 1)));
			} finally {
				graphics.dispose();
			}

			ImageIO.write(image, QR_CODE_IMAGE_TYPE, stream);
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 生成二维码图片至某个路径下
	 * 
	 * @param qrContent
	 * @param width
	 * @param height
	 * @param filePath
	 */
	public static void generateQRCodeImageIntoPath(String qrContent, int width, int height, String filePath) {
		try {
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, width, height);
			Path path = FileSystems.getDefault().getPath(filePath);
			MatrixToImageWriter.writeToPath(bitMatrix, QR_CODE_IMAGE_TYPE, path);
		} catch (final Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}

	}

	/**
	 * 生成二维码图片base64码
	 *
	 * @param qrContent
	 * @return
	 */
	public static String generateQRCodeImageString(String qrContent) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		generateQRCode(os, qrContent, 300, 300);
		return new String(Base64.getEncoder().encode(os.toByteArray()));
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = "D:/qrCode.png";
		String qrContent = "http://www.baidu.com";
		generateQRCodeImageIntoPath(qrContent, 300, 300, filePath);
		System.out.println(generateQRCodeImageString(qrContent));
	}
}
