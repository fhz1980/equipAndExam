package com.sznikola.hk;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.NativeLongByReference;
import org.opencv.core.Core;
/**
 * 
 * @author fanzhongkui
 *
 */
public class HKDVRVideo {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	static PlayCtrl playControl = PlayCtrl.INSTANCE;

	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
	HCNetSDK.NET_DVR_IPPARACFG m_strIpparaCfg;// IP参数
	HCNetSDK.NET_DVR_CLIENTINFO m_strClientInfo;// 用户参数

	boolean bRealPlay;// 是否在预览.
	String m_sDeviceIP;// 已登录设备的IP地址
	NativeLong lUserID;// 用户句柄
	NativeLongByReference m_lPort;// 回调预览时播放库端口指针
	FRealDataCallBack fRealDataCallBack;// 预览回调函数实现
	NativeLong lPreviewHandle;// 预览句柄
	FDecCallBack fDecCallBack;// 预览回调函数实现

	public HKDVRVideo() {
		lUserID = new NativeLong(-1);
		m_lPort = new NativeLongByReference(new NativeLong(-1));
		fDecCallBack = new FDecCallBack();
		lPreviewHandle = new NativeLong(-1);
		fRealDataCallBack = new FRealDataCallBack();
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			System.out.println("初始化失败");
		}
	}

	public boolean login(String ip, int iPort, String username, String password) {
		// 注册
		if (lUserID.longValue() > -1) {
			// 先注销
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
			lUserID = new NativeLong(-1);
		}
		m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();

		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		m_sDeviceIP = ip;
		lUserID = hCNetSDK.NET_DVR_Login_V30(ip, (short) iPort, username, password, m_strDeviceInfo);
		long userID = lUserID.longValue();
		System.out.println("userid=" + userID);
		// System.out.println(hCNetSDK.NET_DVR_GetLastError());
		if (userID == -1) {
			m_sDeviceIP = "";// 登录未成功,IP置为空
			return false;
		}
		return true;
	}

	public boolean close() {

		// 如果已经注册,注销
		if (lUserID.longValue() > -1) {
			hCNetSDK.NET_DVR_Logout_V30(lUserID);
		}
		// cleanup SDK
		hCNetSDK.NET_DVR_Cleanup();
		return true;
	}

	public void getVideo(String ip, int port, String username, String password, int channel) {
		if (login(ip, port, username, password)) {
			m_strClientInfo = new HCNetSDK.NET_DVR_CLIENTINFO();
			m_strClientInfo.lChannel = new NativeLong(channel);
			System.out.println(channel);
			m_strClientInfo.hPlayWnd = null;
			if (lPreviewHandle.longValue() > -1) {
				hCNetSDK.NET_DVR_StopRealPlay(lPreviewHandle);

			}
			lPreviewHandle = hCNetSDK.NET_DVR_RealPlay_V30(lUserID, m_strClientInfo, fRealDataCallBack, null, true);
			System.out.println(hCNetSDK.NET_DVR_GetLastError());
			long previewSucValue = lPreviewHandle.longValue();
		}
	}

	/******************************************************************************
	 * 内部类: FRealDataCallBack 实现预览回调数据
	 ******************************************************************************/
	class FRealDataCallBack implements HCNetSDK.FRealDataCallBack_V30 {
		// 预览回调
		public void invoke(NativeLong lRealHandle, int dwDataType, ByteByReference pBuffer, int dwBufSize,
				Pointer pUser) {
			switch (dwDataType) {
			case HCNetSDK.NET_DVR_SYSHEAD: // 系统头

				if (!playControl.PlayM4_GetPort(m_lPort)) // 获取播放库未使用的通道号
				{
					break;
				}

				if (dwBufSize > 0) {

					if (!playControl.PlayM4_SetStreamOpenMode(m_lPort.getValue(), PlayCtrl.STREAME_REALTIME)) // 设置实时流播放模式
					{
						break;
					}

					if (!playControl.PlayM4_OpenStream(m_lPort.getValue(), pBuffer, dwBufSize, 1024 * 1024)) // 打开流接口
					{
						break;
					}

					if (!playControl.PlayM4_Play(m_lPort.getValue(), null)) // 播放开始
					{
						break;
					}
					if (!playControl.PlayM4_SetDecCallBack(m_lPort.getValue(), fDecCallBack)) // 设置解码回调
					{
						break;
					}
				}
			case HCNetSDK.NET_DVR_STREAMDATA: // 码流数据
				if ((dwBufSize > 0) && (m_lPort.getValue().intValue() != -1)) {
					long start = System.currentTimeMillis();
					boolean isok = playControl.PlayM4_InputData(m_lPort.getValue(), pBuffer, dwBufSize);
					System.out.println(System.currentTimeMillis() - start);
					if (!isok) // 输入流数据
					{
						break;
					} else {
						System.out.println(dwBufSize);
					}
				}
			}
		}
	}



	public static void main(String[] args) throws Exception {
	   HKDVRVideo bt = new HKDVRVideo();
	   bt.getVideo("192.168.1.252", 8000, "admin","qwer319322", 36);
	   Thread.sleep(6000);
	   bt.close();
	}
}
