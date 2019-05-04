//package com.safecode.javacv.Verificationcode;
//
//        import net.sourceforge.tess4j.ITesseract;
//        import net.sourceforge.tess4j.Tesseract;
//        import net.sourceforge.tess4j.TesseractException;
//
//        import javax.imageio.ImageIO;
//        import java.awt.*;
//        import java.awt.image.BufferedImage;
//        import java.io.File;
//        import java.io.IOException;
//
//public class code {
//
//
//    public static void main(String[] args) {
//
//        //原始验证码地址
//        String OriginalImg = "E:\\face\\fuck\\oi.jpg";
//        //识别样本输出地址
//        String ocrResult = "E:\\face\\fuck\\or.jpg";
//        //去噪点
//        code.removeBackground(OriginalImg, ocrResult);
//        //裁剪边角
//        code.cuttingImg(ocrResult);
//        //OCR识别
//        String codef = code.executeTess4J(ocrResult);
//
//        //输出识别结果
//        System.out.println("Ocr识别结果: \n" + codef);
//
//    }
//
//
//    public static void removeBackground(String imgUrl, String resUrl) {
//        //定义一个临界阈值
//        int threshold = 300;
//        try {
//            BufferedImage img = ImageIO.read(new File(imgUrl));
//            int width = img.getWidth();
//            int height = img.getHeight();
//            for (int i = 1; i < width; i++) {
//                for (int x = 0; x < width; x++) {
//                    for (int y = 0; y < height; y++) {
//                        Color color = new Color(img.getRGB(x, y));
//                        System.out.println("red:" + color.getRed() + " | green:" + color.getGreen() + " | blue:" + color.getBlue());
//                        int num = color.getRed() + color.getGreen() + color.getBlue();
//                        if (num >= threshold) {
//                            img.setRGB(x, y, Color.WHITE.getRGB());
//                        }
//                    }
//                }
//            }
//            for (int i = 1; i < width; i++) {
//                Color color1 = new Color(img.getRGB(i, 1));
//                int num1 = color1.getRed() + color1.getGreen() + color1.getBlue();
//                for (int x = 0; x < width; x++) {
//                    for (int y = 0; y < height; y++) {
//                        Color color = new Color(img.getRGB(x, y));
//
//                        int num = color.getRed() + color.getGreen() + color.getBlue();
//                        if (num == num1) {
//                            img.setRGB(x, y, Color.BLACK.getRGB());
//                        } else {
//                            img.setRGB(x, y, Color.WHITE.getRGB());
//                        }
//                    }
//                }
//            }
//            File file = new File(resUrl);
//            if (!file.exists()) {
//                File dir = file.getParentFile();
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            ImageIO.write(img, "jpg", file);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void cuttingImg(String imgUrl) {
//        try {
//            File newfile = new File(imgUrl);
//            BufferedImage bufferedimage = ImageIO.read(newfile);
//            int width = bufferedimage.getWidth();
//            int height = bufferedimage.getHeight();
//            if (width > 52) {
//                bufferedimage = code.cropImage(bufferedimage, (int) ((width - 52) / 2), 0, (int) (width - (width - 52) / 2), (int) (height));
//                if (height > 16) {
//                    bufferedimage = code.cropImage(bufferedimage, 0, (int) ((height - 16) / 2), 52, (int) (height - (height - 16) / 2));
//                }
//            } else {
//                if (height > 16) {
//                    bufferedimage = code.cropImage(bufferedimage, 0, (int) ((height - 16) / 2), (int) (width), (int) (height - (height - 16) / 2));
//                }
//            }
//            ImageIO.write(bufferedimage, "jpg", new File(imgUrl));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
//        int width = bufferedImage.getWidth();
//        int height = bufferedImage.getHeight();
//        if (startX == -1) {
//            startX = 0;
//        }
//        if (startY == -1) {
//            startY = 0;
//        }
//        if (endX == -1) {
//            endX = width - 1;
//        }
//        if (endY == -1) {
//            endY = height - 1;
//        }
//        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
//        for (int x = startX; x < endX; ++x) {
//            for (int y = startY; y < endY; ++y) {
//                int rgb = bufferedImage.getRGB(x, y);
//                result.setRGB(x - startX, y - startY, rgb);
//            }
//        }
//        return result;
//    }
//
//
//    public static String executeTess4J(String imgUrl) {
//        String ocrResult = "";
//        try {
//            ITesseract instance = new Tesseract();
//            instance.setDatapath("resources:");
////            instance.setLanguage("chi_sim");
////            instance.setLanguage("eng");
//            File imgDir = new File(imgUrl);
//            //long startTime = System.currentTimeMillis();
//            ocrResult = instance.doOCR(imgDir);
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//        return ocrResult;
//    }
//}
