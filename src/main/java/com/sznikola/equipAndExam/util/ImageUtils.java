package com.sznikola.equipAndExam.util;

import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ImageUtils {

    @Autowired
    Image image;

	//通过URL下载图片
	public static BufferedImage downloadBufferedImageFromUrl(String url,String type) {
        try {
//        	byte[] bs = url.getBytes("GBK");
//        	String url1 = new String(bs, Charset.forName("utf-8"));
//        	System.out.println(url1);
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            File file = new File("tmpPhoto."+type);
            OutputStream os = new FileOutputStream(file);
            InputStream ins = httpUrl.getInputStream();
            int bytesRead;
            int len = 8192;
            byte[] buffer = new byte[len];
            while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
            httpUrl.disconnect();
            return ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 public static BufferedImage drawFace(BufferedImage image){
	        Graphics g = image.getGraphics();

	        Graphics2D g2 = (Graphics2D)g;  //g是Graphics对象
	        g2.setStroke(new BasicStroke(5.0f));
	        //画头
	        //最大版
	        g2.drawOval(140+100, 90, 480, 540);
//	        g2.drawOval(0, 90, 480, 540); //0,90 为画布起点
//	        g2.drawOval(350, 190, 270, 340);
	        /*//画眼睛
	        g.drawOval(400, 340, 80, 50);
	        g.drawOval(500, 340, 80, 50);
	        //画鼻子
	        g.drawArc(140, 150, 100, 150, -90, 90);
	        g.drawArc(260, 150, 100, 150, 180, 90);
	        //画嘴巴
	        g.drawOval(440, 430, 100, 50);*/
	        return image;
	    }

	    public static BufferedImage drawRect(BufferedImage image,int x,int y,int width,int height) {

	        Graphics g = image.getGraphics();
	        g.setColor(Color.RED);//画笔颜色
	        g.drawRect(x,y,width,height);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
	        //g.dispose();
	        return image;
	    }
	    
	    public static BufferedImage deepCopy(BufferedImage bi) {

	        ColorModel cm = bi.getColorModel();

	        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();

	        WritableRaster raster = bi.copyData(null);

	        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);

	    }

    public static ImageIcon imageScaled(BufferedImage image, int targetW, int targetH) throws IOException {
        BufferedImage copy = ImageUtils.deepCopy(image);

        int h = image.getHeight();
        int w = image.getWidth();

        double scale=w/(double)h;
        double targetScale = targetW/(double)targetH;

        if(scale == targetScale){
            return new ImageIcon(copy.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));
        }
        //切去部分高度
        else if(targetScale > scale){
            int cutH = (int) ((targetScale-scale) * h)/2;
            BufferedImage subimage = copy.getSubimage(0, cutH, w, h - cutH);
            File file = new File("./res/temporaryimg");
            if (!file.exists()) {
                file.mkdirs();
            }
            ImageIO.write(subimage,"jpg",new File("./res/temporaryimg/a.jpg"));

            return new ImageIcon(subimage.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));


        }else if(targetScale < scale){

            int cutW = (int) ((scale-targetScale) * w)/2;
            BufferedImage subimage = copy.getSubimage( cutW,0 , w - cutW , h );
            File file = new File("./res/temporaryimg");
            if (!file.exists()) {
                file.mkdirs();
            }
            ImageIO.write(subimage,"jpg",new File("./res/temporaryimg/a.jpg"));

            return new ImageIcon(subimage.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));
        }
        return null;
    }

    public static ImageIcon imageScaled2(BufferedImage image, int targetW, int targetH) throws IOException {
        BufferedImage copy = ImageUtils.deepCopy(image);

        int h = image.getHeight();
        int w = image.getWidth();

        double scale=w/(double)h;
        double targetScale = targetW/(double)targetH;

        if(scale == targetScale){
            return new ImageIcon(copy.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));
        }
        //切去部分高度
        else if(targetScale > scale){
            int cutH = (int) ((targetScale-scale) * h)/2;
            BufferedImage subimage = copy.getSubimage(0, cutH, w, h - cutH);

            return new ImageIcon(subimage.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));


        }else if(targetScale < scale){

            int cutW = (int) ((scale-targetScale) * w)/2;
            BufferedImage subimage = copy.getSubimage( cutW,0 , w - cutW , h );
            return new ImageIcon(subimage.getScaledInstance(targetW,targetH,Image.SCALE_DEFAULT));
        }
        return null;
    }


}
