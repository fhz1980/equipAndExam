package com.sznikola.equipAndExam.util.hk;

import com.sznikola.equipAndExam.util.UploadUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import static org.opencv.imgcodecs.Imgcodecs.imwrite;

/**
 * 
 * @author fanzhongkui
 *
 */
@Slf4j
public class USBCameraVideo {
  VideoCapture capture;
//  public static void main(String[] args) throws InterruptedException {
////    uSBCameraUse();
//  }
  public boolean detectCamera(){
    capture = new VideoCapture(1);
    if(!capture.isOpened()) {
      System.out.println("could not load video data...");
      return false;
    }
    return true;
  }

  @SneakyThrows
  public boolean USBCameraUse(String userCode) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    String saveVideo = MessageFormat.format(".\\res\\temporaryimg\\{0}.mp4", UUID.randomUUID().toString());
    String savePic = MessageFormat.format(".\\res\\temporaryimg\\{0}.jpg", UUID.randomUUID().toString());

    // 打开摄像头或者视频文件
    int frame_width = (int) capture.get(3);
    int frame_height = (int) capture.get(4);

    final VideoWriter videoWriter = new VideoWriter(saveVideo, VideoWriter.fourcc('m', 'p', '4', 'v'), 30, new Size(frame_width, frame_height));
    /*ImageGUI gui = new ImageGUI();
    gui.createWin("OpenCV + Java视频读与播放演示", new Dimension(frame_width, frame_height));*/
    Mat frame = new Mat();

    long pre = System.currentTimeMillis();
    long current = pre;
    boolean flag = true;
    File photo = null;
    while (true) {
      if ((current - pre) / 1000 > 10) {
        videoWriter.release();
        capture.release();
        break;
      }

      if ((current - pre) / 1000 > 2 && flag) {
        flag = false;
        imwrite(savePic, frame);  //保存图片
        photo = new File(savePic);
        UploadUtil.build().uploadFile("img", new File(savePic));
      }

      boolean have = capture.read(frame);
      Core.flip(frame, frame, 1);// Win上摄像头

      if (!have) break;

      if (!frame.empty()) {
        videoWriter.write(frame);
        /*gui.imshow(conver2Image(frame));
        gui.repaint();*/
      }
      current = System.currentTimeMillis();
    }



    File file = new File(saveVideo);
    boolean b = UploadUtil.build().uploadFile("video", file);

    //删除图片视频信息
    if(Objects.nonNull(photo)){
      photo.delete();
    }
    //删除视频
    if(Objects.nonNull(file)){
      file.delete();
    }

    if (!b) {
      return false;
    }
    //后传给服务器

    return true;
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
    long pre = System.currentTimeMillis();
    long current = pre;
    boolean flag = true;
    while (true){
      if(flag){
        log.info("start");
      }
      if((current - pre) / 1000 > 4){
        log.info("lsp");
        return;
      }
      if((current - pre) / 1000 > 2 && flag){
        log.info("cxk");
        flag = false;
      }
      current = System.currentTimeMillis();
    }
  }

}