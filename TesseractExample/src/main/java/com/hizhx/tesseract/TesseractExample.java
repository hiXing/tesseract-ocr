package com.hizhx.tesseract;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class TesseractExample {
    public static void main(String[] args) {
       main2();
    }
    /**
     *
     * @param srImage 图片路径
     * @param ZH_CN 是否使用中文训练库,true-是
     * @return 识别结果
     */
    public static String FindOCR(String srImage, boolean ZH_CN) {
        System.out.println("start");
        double start=System.currentTimeMillis();
        File imageFile = new File(srImage);
        if (!imageFile.exists()) {
            return "图片不存在";
        }
        try {

            BufferedImage textImage = ImageIO.read(imageFile);
            Tesseract instance= new Tesseract();
            instance.setDatapath("tessdata");//设置训练库
//        instance.setDatapath("C:\\ProgramFiles(x86)\\Tesseract-OCR\\tessdata");//设置训练库的位置
            instance.setLanguage("num");// 添加自定义字库，<span style="color:#FF0000;">后面的自己训练的字库</span>
            if (ZH_CN)
                instance.setLanguage("chi_sim");//中文识别
            String result = "";
            result = instance.doOCR(textImage);//支持很多数据类型，比如：BufferedImage、File等
            double end=System.currentTimeMillis();
            System.out.println("耗时"+(end-start)/1000+" s");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "发生未知错误";
        }
    }

    /**
     * 图片锐化与放大
     *
     * @return
     */
    private BufferedImage enlargement(BufferedImage image) {
        image = ImageHelper
                .convertImageToGrayscale(ImageHelper.getSubImage(image, 0, 0, image.getWidth(), image.getHeight())); // 对图片进行处理
        image = ImageHelper.getScaledInstance(image, image.getWidth() * 5, image.getHeight() * 5); // 将图片扩大5倍
        return image;
    }
    public static void main2() {
//        String pathname = System.getProperty("user.dir") + "/myimages";
        String pathname = "D:\\OCR\\myimages";
        System.err.println("pa:::::::"+pathname);
        File root = new File(pathname);
        ITesseract instance = new Tesseract();

        try {
            File[] files = root.listFiles();
            for (File file : files) {
                String result = instance.doOCR(file);
                String fileName = file.toString().substring(file.toString().lastIndexOf("\\")+1);
                System.out.println("图片名：" + fileName +" 识别结果："+result);
            }
        } catch (TesseractException e) {
            System.err.println(e.getMessage());
        }
    }
}
