package com.xcomm.jmf;

import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.*;
import java.util.Vector;
public class CaptureUtil {
	/**
	 * 得到数据源
	 * @param af 音频格式
	 * @param vf 视频格式
	 * @return 数据源
	 */
	protected static DataSource getCaptureDS(AudioFormat af, VideoFormat vf) {
		DataSource dsVideo = null, dsAudio = null, ds = null;
		if (vf != null) {
			dsVideo = createDataSource(vf);
			if (dsVideo == null) {
				return null;// 表示没有获取到captureDs;
			}
		}
		if (af != null) {
			dsAudio = createDataSource(af);
		}
		if (dsVideo != null) {
			// dsVideo = new MonitorCDS(dsVideo);
			if (dsAudio == null) {
				return dsVideo;
			}
			ds = dsVideo;
		} else if (dsAudio != null) {
			return dsAudio;
		} else {
			return null;
		}
		try {
			//建立数据源
			ds = Manager.createMergingDataSource(new DataSource[] { dsAudio,
					dsVideo });
		} catch (IncompatibleSourceException e) {
			return null;
		}
		return ds;
	}
	protected static DataSource createDataSource(Format format) {
		//获取视频设备列表
		@SuppressWarnings("unchecked")
		Vector<CaptureDeviceInfo> deviceList = CaptureDeviceManager.getDeviceList(format);
		if (deviceList.size() < 1) {
			System.out.println("No Device for " + format);
			return null;
		}
		//得到列表中第一个视频设备
		CaptureDeviceInfo cdi = deviceList.elementAt(0);
		//设备定位
		MediaLocator ml = cdi.getLocator();
		DataSource ds = null;
		try {
			//建立数据源并链接
			ds = Manager.createDataSource(ml);
			ds.connect();
			if (ds instanceof CaptureDevice) {
				setCaptureFormat((CaptureDevice) ds, format);
			}
		} catch (java.io.IOException e) {
			System.out.println("IO Exception");
			return null;
		} catch (NoDataSourceException e) {
			System.out.println("No DataSource Exception");
			return null;
		}
		return ds;
	}
/**
 * 设置视频捕捉格式
 * @param cd
 * @param format
 */
	protected static void setCaptureFormat(CaptureDevice cd, Format format) {
		FormatControl[] formatControls = cd.getFormatControls();
		if (formatControls.length < 1) {
			System.out.println("No Format Control");
			return;
		}
		FormatControl formatControl = formatControls[0];
		Format[] formats = formatControl.getSupportedFormats();
		for (int i = 0; i < formats.length; i++) {
			if (formats[i].matches(format)) {
				Format f = formats[i].intersects(format);
				System.out.println(f);
				formatControl.setFormat(f);
				break;
			}
		}
	}
}
