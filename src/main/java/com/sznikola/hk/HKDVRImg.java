package com.sznikola.hk;

import com.sun.jna.NativeLong;
import com.sun.jna.ptr.IntByReference;

import java.io.*;
import java.nio.ByteBuffer;
/**
 * 
 * @author fanzhongkui
 *
 */
public class HKDVRImg {
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static NativeLong lUserID;// 用户句柄
	/**
	 * 抓拍图片到文件
	 *
	 * @param imgPath     图片路径
	 * @param cate        是否走内存
	 * @param dvr         用户信息
	 * @param channelList 通道
	 */
	public static void getDVRPic2File(String ip, String port, String username, String password, String imgPath, long channel) {
		System.out.println(("-----------这里处理已经getDVRPic----------" + imgPath));

		if (!hCNetSDK.NET_DVR_Init()) {
			System.out.println("hksdk(抓图)-海康sdk初始化失败!");
			return;
		}
		// 设备信息
		HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// 注册设备
		lUserID = hCNetSDK.NET_DVR_Login_V30(ip, Short.valueOf(port), username, password, devinfo);
		// 返回一个用户编号，同时将设备信息写入devinfo
		if (lUserID.intValue() < 0) {
			System.out.println(("hksdk(抓图)-设备注册失败,错误码:" + hCNetSDK.NET_DVR_GetLastError()));
			return;
		}
		HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();
		if (!hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, devwork)) {
			// 返回Boolean值，判断是否获取设备能力
			System.out.println(("hksdk(抓图)-返回设备状态失败" + hCNetSDK.NET_DVR_GetLastError()));
		}

		String path =imgPath;
		NativeLong chanLong = new NativeLong(channel);
		HCNetSDK.NET_DVR_JPEGPARA jpeg = new HCNetSDK.NET_DVR_JPEGPARA();
		// 设置图片分辨率
		jpeg.wPicSize = 0;
		// 设置图片质量
		jpeg.wPicQuality = 0;
		// 需要加入通道
		System.out.println("-----------这里开始封装 NET_DVR_CaptureJPEGPicture---------");
		boolean is = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, chanLong, jpeg, path);
		System.out.println("-----------抓图工具返回结果----------" + is);
		if (!is) {
			System.out.println("hksdk(抓图)-抓取失败,错误码:" + hCNetSDK.NET_DVR_GetLastError());

		}
		System.out.println("-----------处理完成截图数据----------");
		// 退出登录
		hCNetSDK.NET_DVR_Logout(lUserID);
		// 释放SDK资源
		hCNetSDK.NET_DVR_Cleanup();
	}

	/**
	 * 抓拍图片到文件
	 *
	 * @param imgPath     图片路径
	 * @param cate        是否走内存
	 * @param dvr         用户信息
	 * @param channelList 通道
	 */
	public static void getDVRPic2Memory(String ip, String port, String username, String password, String imgPath,
			long channel) {
		System.out.println(("-----------这里处理已经getDVRPic----------" + imgPath));

		if (!hCNetSDK.NET_DVR_Init()) {
			System.out.println("hksdk(抓图)-海康sdk初始化失败!");
			return;
		}
		// 设备信息
		HCNetSDK.NET_DVR_DEVICEINFO_V30 devinfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// 注册设备
		lUserID = hCNetSDK.NET_DVR_Login_V30(ip, Short.valueOf(port), username, password, devinfo);
		// 返回一个用户编号，同时将设备信息写入devinfo
		if (lUserID.intValue() < 0) {
			System.out.println(("hksdk(抓图)-设备注册失败,错误码:" + hCNetSDK.NET_DVR_GetLastError()));
			return;
		}
		HCNetSDK.NET_DVR_WORKSTATE_V30 devwork = new HCNetSDK.NET_DVR_WORKSTATE_V30();
		if (!hCNetSDK.NET_DVR_GetDVRWorkState_V30(lUserID, devwork)) {
			// 返回Boolean值，判断是否获取设备能力
			System.out.println(("hksdk(抓图)-返回设备状态失败" + hCNetSDK.NET_DVR_GetLastError()));
		}
		HCNetSDK.NET_DVR_JPEGPARA jpeg = new HCNetSDK.NET_DVR_JPEGPARA();
		// 设置图片分辨率
		jpeg.wPicSize = 0;
		// 设置图片质量
		jpeg.wPicQuality = 0;
		IntByReference a = new IntByReference();
		// 设置图片大小
		ByteBuffer jpegBuffer = ByteBuffer.allocate(1024 * 1024);
		File file = new File(imgPath);
		NativeLong chanLong = new NativeLong(channel);
		// 抓图到内存，单帧数据捕获并保存成JPEG存放在指定的内存空间中
		System.out.println("-----------这里开始封装 NET_DVR_CaptureJPEGPicture_NEW---------");
		boolean is = hCNetSDK.NET_DVR_CaptureJPEGPicture_NEW(lUserID, chanLong, jpeg, jpegBuffer, 1024 * 1024, a);
		System.out.println("-----------这里开始图片存入内存----------" + is);
		if (is) {
			/**
			 * 该方式使用内存获取 但是读取有问题无法预览 linux下 可能有问题
			 */
			System.out.println("hksdk(抓图)-结果状态值(0表示成功):" + hCNetSDK.NET_DVR_GetLastError());
			// 存储到本地
			BufferedOutputStream outputStream = null;
			try {
				outputStream = new BufferedOutputStream(new FileOutputStream(file));
				outputStream.write(jpegBuffer.array(), 0, a.getValue());
				outputStream.flush();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} else {
			System.out.println("hksdk(抓图)-抓取失败,错误码:" + hCNetSDK.NET_DVR_GetLastError());
		}
		System.out.println("-----------处理完成截图数据----------");
		// 退出登录
		hCNetSDK.NET_DVR_Logout(lUserID);
		// 释放SDK资源
		hCNetSDK.NET_DVR_Cleanup();
	}
	public static void main(String[] args) throws Exception {
		getDVRPic2File("192.168.1.252", "8000", "admin", "qwer319322", "f:/hk5.jpg", 35);
	}
}
