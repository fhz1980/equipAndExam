package com.sznikola.hk;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.ByteByReference;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;


/**
 * 
 * @author fanzhongkui
 *
 */
class FDecCallBack implements PlayCtrl.DecCallBack {
	Mat src;
	Mat dst;
	int count=0;
	BufferedImage bufferedImage;
	ImageGUI gui = new ImageGUI();
	public FDecCallBack() {
		gui.createWin("OpenCV + Java视频读与播放演示", new Dimension(500, 500));
	}
    @Override
    public void invoke(NativeLong nPort, ByteByReference pBuffer, NativeLong nSize, PlayCtrl.FRAME_INFO frameInfo, NativeLong nReserved1, NativeLong nReserved2) {
        //if (++count % 6 == 0) {
            try {
                handle(pBuffer, nSize.intValue(), frameInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}
    }

    //这样在回调函数DecCallBack 中可以得到视音频数据，其中视频数据是YV12格式的，音频数据是PCM格式的。
    public void handle(ByteByReference pBuffer, int dwBufSize, PlayCtrl.FRAME_INFO frameInfo) {
        src = null;
        dst = null;
        bufferedImage = null;
       int width = frameInfo.nWidth.intValue();
        int height = frameInfo.nHeight.intValue();
        byte[] byteArray = pBuffer.getPointer().getByteArray(0, dwBufSize);

        src = new Mat(height + height / 2, width, CvType.CV_8UC1);
        src.put(0, 0, byteArray);
        dst = new Mat(height, width, CvType.CV_8UC3);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_YUV2BGR_YV12);
        //Imgcodecs.imwrite("f:/"+count+".jpg", dst);
        gui.imshow(conver2Image(dst));
        gui.repaint();

        
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

    public static void setRGB( BufferedImage image, int x, int y, int width, int height, int[] pixels ) {
      int type = image.getType();
      if ( type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB )
        image.getRaster().setDataElements( x, y, width, height, pixels );
      else
        image.setRGB( x, y, width, height, pixels, 0, width );
    }
    
    public static BufferedImage Mat2BufImg (Mat matrix, String fileExtension) {
        // convert the matrix into a matrix of bytes appropriate for
        // this file extension
        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(fileExtension, matrix, mob);
        // convert the "matrix of bytes" into a byte array
        byte[] byteArray = mob.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufImage;
    }

}
