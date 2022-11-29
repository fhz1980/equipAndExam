package com.sznikola.hk;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class USBCameraImg {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static boolean getPic2File(int camera, String filepath) {
		// 打开摄像头或者视频文件
		VideoCapture capture = new VideoCapture(camera);
		if (!capture.isOpened()) {
			System.out.println("could not load video data...");
			return false;
		}
		Mat frame = new Mat();
		boolean have = capture.read(frame);
		Core.flip(frame, frame, 1);// Win上摄像头
		if (!have)
			return false;
		if (!frame.empty()) {
			Imgcodecs.imwrite(filepath,frame);//使用opencv保存
			//使用imageIO要保存为png格式，jpg格式会发红。
			/*
			BufferedImage bufferedImage = conver2Image(frame);
			File outputfile = new File(filepath);
			try {
				ImageIO.write(bufferedImage, "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}*/
			return true;
		} else {
			return false;
		}
	}
	public static BufferedImage getPic2Memory(int camera) {
	    // 打开摄像头或者视频文件
	    VideoCapture capture = new VideoCapture(camera);
	    if(!capture.isOpened()) {
	        System.out.println("could not load video data...");
	        return null;
	      }
	      Mat frame = new Mat();
	      boolean have = capture.read(frame);
	      Core.flip(frame, frame, 1);// Win上摄像头
	      if (!have) return null;
		  if (!frame.empty()) {
			 BufferedImage bufferedImage = conver2Image(frame);
	         return bufferedImage;
		  }else {
			  return null;
		  }
	}
	public static BufferedImage conver2Image(Mat mat) {
	    int width = mat.cols();
	    int height = mat.rows();
	    int dims = mat.channels();
	    int[] pixels = new int[width*height];
	    byte[] rgbdata = new byte[width*height*dims];
	    mat.get(0, 0, rgbdata);
	    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    int index = 0;
	    int r=0, g=0, b=0;
	    for(int row=0; row<height; row++) {
	      for(int col=0; col<width; col++) {
	        if(dims == 3) {
	          index = row*width*dims + col*dims;
	          b = rgbdata[index]&0xff;
	          g = rgbdata[index+1]&0xff;
	          r = rgbdata[index+2]&0xff;
	          pixels[row*width+col] = ((255&0xff)<<24) | ((r&0xff)<<16) | ((g&0xff)<<8) | b&0xff; 
	        }
	        if(dims == 1) {
	          index = row*width + col;
	          b = rgbdata[index]&0xff;
	          pixels[row*width+col] = ((255&0xff)<<24) | ((b&0xff)<<16) | ((b&0xff)<<8) | b&0xff; 
	        }
	      }
	    }
	    setRGB( image, 0, 0, width, height, pixels);
	    return image;
	  }

	  /**
	   * A convenience method for setting ARGB pixels in an image. This tries to avoid the performance
	   * penalty of BufferedImage.setRGB unmanaging the image.
	   */
	  public static void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
	    int type = image.getType();
	    if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
	      image.getRaster().setDataElements( x, y, width, height, pixels );
	    else
	      image.setRGB( x, y, width, height, pixels, 0, width );
	  }
	  public static void main(String[] args) {
		  getPic2File(0,"f:/55.png");
	}
}
