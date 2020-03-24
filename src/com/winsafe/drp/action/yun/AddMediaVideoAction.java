package com.winsafe.drp.action.yun;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.action.common.YunParameter;
import com.winsafe.drp.dao.AppMediaVideo;
import com.winsafe.drp.dao.MediaVideo;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;

import java.awt.image.BufferedImage;  
import javax.imageio.ImageIO;  
  
import org.bytedeco.javacv.FFmpegFrameGrabber;  
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter; 

public class AddMediaVideoAction extends BaseAction {

	private AppMediaVideo amv = new AppMediaVideo();
	
	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		MediaVideo mv = (MediaVideo) form;
		
		FormFile videoFile = mv.getVideo();
		String fileName =UUID.randomUUID().toString() +  videoFile.getFileName().substring(videoFile.getFileName().lastIndexOf("."));
		String filePath = SysConfig.getSysConfig().getProperty("videoFilePath");
		String imgFilePath = filePath;
		File imgFile = null;
		StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getContextPath()).append("/").toString();  
		if (fileName != null && !fileName.equals("")) {
			String videoMd5 = MD5BigFileUtil.md5(videoFile.getInputStream());
			File newVideoFile = FileUploadUtil.createEmptyFile(filePath, fileName);
			FileUploadUtil.saveUplodedFile(videoFile.getInputStream(), filePath, fileName);
			
			imgFile = fetchFrame(newVideoFile.getPath(),  imgFilePath);
			String imgMd5 = MD5BigFileUtil.md5(new FileInputStream(imgFile));
			
			mv.setUrl(tempContextUrl+"qr/video/"+videoMd5);
			mv.setSize(videoFile.getFileSize());
			mv.setImgUrl(tempContextUrl+"qr/video/img/"+imgMd5);
			mv.setPath(newVideoFile.getCanonicalPath());
			mv.setImagePath(imgFile.getCanonicalPath());
			mv.setHash(videoMd5);
			mv.setImageHash(imgMd5);
		}
		
		mv.setCreateTime(new Date());
		mv.setIsDeleted(YesOrNo.NO.getValue());
		mv.setIsPublish(YesOrNo.NO.getValue());
		mv.setManufacturerId(YunParameter.BayerManufacturerID);
		mv.setViewCount(0);
		amv.addMediaVideo(mv);
		request.setAttribute("result", "databases.add.success");
		return mapping.findForward("success");
	}
	
	/** 
     * 获取指定视频的帧并保存为图片至指定目录 
     * @param videofile  源视频文件路径 
     * @param framefile  截取帧的图片存放路径 
     * @throws Exception 
     */  
    private File fetchFrame(String videofile , String targerFilePath)  
            throws Exception {  
		FFmpegFrameGrabber ff = null;   
		File output = null;
		try {
			ff = new FFmpegFrameGrabber(videofile);  
			ff.start();  
	        int lenght = ff.getLengthInFrames();  
	        int i = 0;  
	        Frame f = null;  
	        while (i < lenght) {  
	            // 过滤前100帧  
	            f = ff.grabFrame();  
	            if ((i > 10) && (f.image != null)) {  
	                break;  
	            }  
	            i++;  
	        }  
//			IplImage img = f.image;  
//			int owidth = img.width();  
//			int oheight = img.height(); 
	       
	        Java2DFrameConverter converter = new Java2DFrameConverter();
	        String imageMat = "jpg";
	        String FileName = targerFilePath + File.separator + UUID.randomUUID().toString() + "." + imageMat;
	        BufferedImage bi = converter.getBufferedImage(f);
	        output = new File(FileName);
	        try {
	            ImageIO.write(bi, imageMat, output);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (Exception e) {
			WfLogger.error("", e);
		} finally {
			  ff.flush();  
		      ff.stop();  
		      ff.close();
		}
        
        return output;
    }
}
