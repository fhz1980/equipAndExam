package com.sznikola.devicestate.frame.service;

import com.sznikola.equipAndExam.util.ParameterOperate;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.awt.image.BufferedImage;
import java.text.MessageFormat;

/**
 * @author yzh
 * @Description 人脸识别培训打卡
 * @email yzhcherish@163.com
 * @data 2022-09-08  14:11
 */
public class FaceService {


    //返回image 的用户id和姓名
    /*
        BufferedImage是其实现类，是一个带缓冲区图像类，主要作用是将一幅图片加载到内存中
        （BufferedImage生成的图片在内存里有一个图像缓冲区，利用这个缓冲区我们可以很方便地
        操作这个图片）
     */
    public String judgeMember(BufferedImage image) throws RuntimeException{
        System.out.println("我经过了judgeMember");
        // 将人脸识别出来的图片传给服务器进行判断
        return  HttpService.faceFea(ParameterOperate.extract("mainService"), image);

    }

    public String judgeMemberData(BufferedImage image, String impl) throws RuntimeException{
        //签到系统的路径
        String userJudge = MessageFormat.format("{0}{1}","/common/datacollect/",impl);

        String mainService = ParameterOperate.extract("mainService");
        //将接口的url加入进去
        String url = MessageFormat.format("{0}{1}", mainService, userJudge);
        // 将人脸识别出来的图片传给服务器进行判断
        return  HttpService.faceFea(url, image);
    }

    public String judgeUserData(BufferedImage image, String impl) throws RuntimeException{
        //签到系统的路径
        String userJudge = MessageFormat.format("{0}{1}","/train/experiencedevice/",impl);

        String mainService = ParameterOperate.extract("mainService");
        //将接口的url加入进去
        String url = MessageFormat.format("{0}{1}", mainService, userJudge);
        String category =ParameterOperate.extract("category");
        String categoryName = ParameterOperate.extract("categoryName");
        // 将人脸识别出来的图片传给服务器进行判断
        return  HttpService.faceFeas(url, image, category, categoryName);
    }

    public  String judgeCloseData(String name, String impl){
        //签到系统的路径
        String userCloseJudge = MessageFormat.format("{0}{1}","/train/experiencedevice/",impl);

        String mainService = ParameterOperate.extract("mainService");
        //将接口的url加入进去
        String url = MessageFormat.format("{0}{1}", mainService, userCloseJudge);
        String category =ParameterOperate.extract("category");
        return  HttpService.closeFea(url, name, category);
    }

    // fs.mat2BI() 输入Mat 返回Mat中存放的图片（进行了一些处理）
    public BufferedImage mat2BI(Mat mat){
        //Mat mat=srcmat.clone();
//        int mode =  1;
        double scale=1920.0/mat.cols(); //cols列
        Imgproc.resize(mat, mat, new Size(mat.cols()*scale,mat.rows()*scale));
//        Core.flip(mat,mat,mode);   //> 0 图像向右翻转（水平翻转）
        int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
        if(type==BufferedImage.TYPE_3BYTE_BGR){
            for(int i=0;i<dataSize;i+=3){
                byte blue=data[i+0];
                data[i+0]=data[i+2];
                data[i+2]=blue;
            }
        }
        BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }
    // fs.mat2BI() 输入Mat 返回Mat中存放的图片（进行了一些处理）
    public BufferedImage mat2BIW(Mat mat,double Width){
        //Mat mat=srcmat.clone();
//        int mode =  1;
        double scale = Width / mat.cols(); //cols列
        Imgproc.resize(mat, mat, new Size(mat.cols()*scale,mat.rows()*scale));
//        Core.flip(mat,mat,mode);   //> 0 图像向右翻转（水平翻转）
        int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
        if(type==BufferedImage.TYPE_3BYTE_BGR){
            for(int i=0;i<dataSize;i+=3){
                byte blue=data[i+0];
                data[i+0]=data[i+2];
                data[i+2]=blue;
            }
        }
        BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }



    // 新增版本，增添了可以设置截取图片尺寸问题。
    public BufferedImage mat2BI(Mat mat,double width,double height){
        //Mat mat=srcmat.clone();
        double scale=960.0/mat.cols();
        Imgproc.resize(mat, mat, new Size(width,height));
        Core.flip(mat,mat,1);
        int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();
        byte[] data=new byte[dataSize];
        mat.get(0, 0,data);
        int type=mat.channels()==1?BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;
        if(type==BufferedImage.TYPE_3BYTE_BGR){
            for(int i=0;i<dataSize;i+=3){
                byte blue=data[i+0];
                data[i+0]=data[i+2];
                data[i+2]=blue;
            }
        }
        BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }

}
