package com.xn.hk.common.utils.slide;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 
 * @ClassName: SlideVerifyCodeEntity
 * @Package: com.xn.hk.common.utils.slide
 * @Description: 滑块验证码生成工具类
 * @Author: wanlei
 * @Date: 2020年11月20日 上午10:46:46
 */
public class SlideVerifyImageUtil {
	/**
     * 模板图宽度
     */
    private static int CUT_WIDTH = 50;
    /**
     * 模板图高度
     */
    private static int CUT_HEIGHT = 50;
    /**
     * 抠图凸起圆心
     */
    private static int circleR = 5;
    /**
     * 抠图内部矩形填充大小
     */
    private static int RECTANGLE_PADDING = 8;
    /**
     * 抠图的边框宽度
     */
    private static int SLIDER_IMG_OUT_PADDING = 1;
    /**
     * 裁剪之后的图片格式
     */
    private static String SLIDER_IMG_TYPE = "png";
    private static String SLIDER_IMG_TYPE_SUFFIX = ".png";

    /**
     * 生成拼图样式
     *
     * @return
     */
    private static int[][] getBlockData() {
        int[][] data = new int[CUT_WIDTH][CUT_HEIGHT];
        Random random = new Random();
        // (x-a)²+(y-b)²=r²
        // x中心位置左右5像素随机
        double x1 = RECTANGLE_PADDING + (CUT_WIDTH - 2 * RECTANGLE_PADDING) / 2.0 - 5 + random.nextInt(10);
        // y 矩形上边界半径-1像素移动
        double y1_top = RECTANGLE_PADDING - random.nextInt(3);
        double y1_bottom = CUT_HEIGHT - RECTANGLE_PADDING + random.nextInt(3);
        double y1 = random.nextInt(2) == 1 ? y1_top : y1_bottom;

        double x2_right = CUT_WIDTH - RECTANGLE_PADDING - circleR + random.nextInt(2 * circleR - 4);
        double x2_left = RECTANGLE_PADDING + circleR - 2 - random.nextInt(2 * circleR - 4);
        double x2 = random.nextInt(2) == 1 ? x2_right : x2_left;
        double y2 = RECTANGLE_PADDING + (CUT_HEIGHT - 2 * RECTANGLE_PADDING) / 2.0 - 4 + random.nextInt(10);

        double po = Math.pow(circleR, 2);
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                // 矩形区域
                boolean fill;
                if ((i >= RECTANGLE_PADDING && i < CUT_WIDTH - RECTANGLE_PADDING)
                        && (j >= RECTANGLE_PADDING && j < CUT_HEIGHT - RECTANGLE_PADDING)) {
                    data[i][j] = 1;
                    fill = true;
                } else {
                    data[i][j] = 0;
                    fill = false;
                }
                // 凸出区域
                double d3 = Math.pow(i - x1, 2) + Math.pow(j - y1, 2);
                if (d3 < po) {
                    data[i][j] = 1;
                } else {
                    if (!fill) {
                        data[i][j] = 0;
                    }
                }
                // 凹进区域
                double d4 = Math.pow(i - x2, 2) + Math.pow(j - y2, 2);
                if (d4 < po) {
                    data[i][j] = 0;
                }
            }
        }
        // 边界阴影
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                // 四个正方形边角处理
                for (int k = 1; k <= SLIDER_IMG_OUT_PADDING; k++) {
                    // 左上、右上
                    if (i >= RECTANGLE_PADDING - k && i < RECTANGLE_PADDING
                            && ((j >= RECTANGLE_PADDING - k && j < RECTANGLE_PADDING)
                            || (j >= CUT_HEIGHT - RECTANGLE_PADDING - k
                            && j < CUT_HEIGHT - RECTANGLE_PADDING + 1))) {
                        data[i][j] = 2;
                    }

                    // 左下、右下
                    if (i >= CUT_WIDTH - RECTANGLE_PADDING + k - 1 && i < CUT_WIDTH - RECTANGLE_PADDING + 1) {
                        for (int n = 1; n <= SLIDER_IMG_OUT_PADDING; n++) {
                            if (((j >= RECTANGLE_PADDING - n && j < RECTANGLE_PADDING)
                                    || (j >= CUT_HEIGHT - RECTANGLE_PADDING - n
                                    && j <= CUT_HEIGHT - RECTANGLE_PADDING))) {
                                data[i][j] = 2;
                            }
                        }
                    }
                }

                if (data[i][j] == 1 && j - SLIDER_IMG_OUT_PADDING > 0 && data[i][j - SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j - SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && j + SLIDER_IMG_OUT_PADDING > 0 && j + SLIDER_IMG_OUT_PADDING < CUT_HEIGHT
                        && data[i][j + SLIDER_IMG_OUT_PADDING] == 0) {
                    data[i][j + SLIDER_IMG_OUT_PADDING] = 2;
                }
                if (data[i][j] == 1 && i - SLIDER_IMG_OUT_PADDING > 0 && data[i - SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i - SLIDER_IMG_OUT_PADDING][j] = 2;
                }
                if (data[i][j] == 1 && i + SLIDER_IMG_OUT_PADDING > 0 && i + SLIDER_IMG_OUT_PADDING < CUT_WIDTH
                        && data[i + SLIDER_IMG_OUT_PADDING][j] == 0) {
                    data[i + SLIDER_IMG_OUT_PADDING][j] = 2;
                }
            }
        }
        return data;
    }

    /**
     * 抠出拼图
     *
     * @param oriImage
     * @param targetImage
     * @param blockImage
     * @param x
     * @param y
     */
    private static void cutImgByTemplate(BufferedImage oriImage, BufferedImage targetImage, int[][] blockImage, int x,
                                         int y) {
        for (int i = 0; i < CUT_WIDTH; i++) {
            for (int j = 0; j < CUT_HEIGHT; j++) {
                int _x = x + i;
                int _y = y + j;
                int rgbFlg = blockImage[i][j];
                int rgb_ori = oriImage.getRGB(_x, _y);
                // 原图中对应位置变色处理
                if (rgbFlg == 1) {
                    // 抠图上复制对应颜色值
                    targetImage.setRGB(i, j, rgb_ori);
                    // 原图对应位置颜色变化
                    oriImage.setRGB(_x, _y, Color.LIGHT_GRAY.getRGB());
                } else if (rgbFlg == 2) {
                    targetImage.setRGB(i, j, Color.WHITE.getRGB());
                    oriImage.setRGB(_x, _y, Color.GRAY.getRGB());
                } else if (rgbFlg == 0) {
                    // int alpha = 0;
                    targetImage.setRGB(i, j, rgb_ori & 0x00ffffff);
                }
            }

        }
    }

    /**
     * 获取图片
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static BufferedImage getBufferedImage(String path) throws IOException {
        File file = new File(path);
        if (file.isFile()) {
            return ImageIO.read(file);
        }
        return null;
    }

    /**
     * 保存裁剪之后的图片
     *
     * @param image
     * @param file
     * @throws Exception
     */
    private static void writeImg(BufferedImage image, String file) throws Exception {
        byte[] imagedata = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, SLIDER_IMG_TYPE, bos);
        imagedata = bos.toByteArray();
        File outFile = new File(file);
        FileOutputStream out = new FileOutputStream(outFile);
        out.write(imagedata);
        out.close();
    }

    /**
     * 将图片转换为Base64
     *
     * @param image
     * @return
     * @throws IOException
     */
    private static String getImageBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, SLIDER_IMG_TYPE, out);
        // 转成byte数组
        byte[] bytes = out.toByteArray();
        // 生成Base64编码
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 随机从原图片目录获取一张图片并裁剪，并返回裁剪之后的图片base64对象
     *
     * @param originImgDirectory 原图片目录
     * @return
     */
    public static SlideVerifyImage getRandomSlideVerifyImage(String originImgDirectory) throws Exception {
        return getRandomSlideVerifyImage(originImgDirectory, false, null);
    }

    /**
     * 随机从原图片目录获取一张图片并裁剪，并返回裁剪之后的图片base64对象
     *
     * @param originImgDirectory 原图片目录
     * @param isSaveImage        是否保存裁剪之后的图片
     * @param saveDirectory      裁剪之后的图片保存目录
     * @return
     */
    public static SlideVerifyImage getRandomSlideVerifyImage(String originImgDirectory, boolean isSaveImage,
                                                             String saveDirectory) throws Exception {
        // 从文件夹中读取所有待选择文件名
        ArrayList<String> imageFileNames = getFileNamesFromDic(originImgDirectory);
        // 随机获取其中一个文件
        int random = (int) Math.round(Math.random() * (imageFileNames.size() - 1));
        String randomImgName = imageFileNames.get(random);
        String randomImgPath = originImgDirectory + File.separator + randomImgName;
        return getSlideVerifyImage(randomImgName, randomImgPath, isSaveImage, saveDirectory);
    }

    /**
     * 指定一张图片并裁剪，并返回裁剪之后的图片base64对象
     *
     * @param imageName 图片名称
     * @param imagePath 图片路径
     * @return
     * @throws Exception
     */
    public static SlideVerifyImage getSlideVerifyImage(String imageName, String imagePath) throws Exception {
        return getSlideVerifyImage(imageName, imagePath, false, null);
    }

    /**
     * 指定一张图片并裁剪，并返回裁剪之后的图片base64对象
     *
     * @param imageName     图片名称
     * @param imagePath     图片路径
     * @param isSaveImage   是否保存裁剪之后的图片
     * @param saveDirectory 裁剪之后的图片保存目录
     * @return
     * @throws Exception
     */
    public static SlideVerifyImage getSlideVerifyImage(String imageName, String imagePath, boolean isSaveImage,
                                                       String saveDirectory) throws Exception {
        BufferedImage originImage = getBufferedImage(imagePath);
        // 生成拼图样式，并进行图片裁剪处理
        int[][] data = getBlockData();
        int locationX = CUT_WIDTH + new Random().nextInt(originImage.getWidth() - CUT_WIDTH * 3);
        int locationY = CUT_HEIGHT + new Random().nextInt(originImage.getHeight() - CUT_HEIGHT) / 2;
        BufferedImage markImage = new BufferedImage(CUT_WIDTH, CUT_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
        cutImgByTemplate(originImage, markImage, data, locationX, locationY);
        // 是否保存裁剪之后的图片至指定目录中
        if (isSaveImage) {
            // 生成裁剪之后的图片名称，考虑图片覆盖，简单设置四位随机数
            int r = (int) Math.round(Math.random() * 8999) + 1000;
            String name = imageName.substring(0, imageName.indexOf('.'));
            String afterName = name + "_after" + r + SLIDER_IMG_TYPE_SUFFIX;
            String markName = name + "_after_mark" + r + SLIDER_IMG_TYPE_SUFFIX;
            writeImg(originImage, saveDirectory + File.separator + afterName);
            writeImg(markImage, saveDirectory + File.separator + markName);
        }
        return new SlideVerifyImage(getImageBase64(originImage), getImageBase64(markImage), locationX, locationY);
    }

    /**
     * 获取文件夹下所有文件名
     *
     * @param dicPath
     * @return
     */
    private static ArrayList<String> getFileNamesFromDic(String dicPath) {
        File dic = new File(dicPath);
        ArrayList<String> imageFileNames = new ArrayList<String>();
        File[] dicFileList = dic.listFiles();
        for (File f : dicFileList) {
            imageFileNames.add(f.getName());
        }
        return imageFileNames;
    }

    /**
     * 测试方法
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // 原图片目录
        String directoryPath = "D:/picture/captcha-pic";
        // 裁剪之后图片保存目录
        String savePath = "D:/picture/cut";
        System.out.println(getRandomSlideVerifyImage(directoryPath, true, savePath));
    }

}
