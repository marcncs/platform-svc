package com.winsafe.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.winsafe.drp.dao.AppMediaImg;
import com.winsafe.drp.dao.AppMediaVideo;
import com.winsafe.drp.dao.MediaImage;
import com.winsafe.drp.dao.MediaVideo;
import com.winsafe.drp.util.ResponseUtil;
import com.winsafe.drp.util.SysConfig;
import com.winsafe.drp.util.WfLogger;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.sap.metadata.YesOrNo;
import com.winsafe.sap.util.FileUploadUtil;
import com.winsafe.sap.util.MD5BigFileUtil;
import com.winsafe.yun.services.impl.MediaImageService;

import net.sf.json.JSONObject;

@Controller
public class FileUploadController {
	
	private MediaImageService serv = new MediaImageService();
	private AppMediaVideo appVideo = new AppMediaVideo();
	private AppMediaImg appMediaImg = new AppMediaImg();
	
	@RequestMapping(value="/upload")
	public void uplaodFile(@RequestParam(value="file", required=false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String md5 = MD5BigFileUtil.md5(file.getInputStream());
		
		MediaImage image = serv.getByHash(md5);
		if(image == null) {
			String filaPath = SysConfig.getSysConfig().getProperty("imgFilePath");
			String fileName = md5;
			File newFile = FileUploadUtil.createEmptyFile(filaPath, fileName);
			//保存   
			try {
				file.transferTo(newFile);
			} catch (Exception e) {
				WfLogger.error("", e);
			}
			image = new MediaImage();
			image.setCreateTime(DateUtil.getCurrentDate());
			image.setLastModifyTime(DateUtil.getCurrentDate());
			image.setHash(md5);
			image.setMimeType(file.getContentType());
			image.setTitle(fileName);
			StringBuffer url = request.getRequestURL();  
			String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();  
			image.setUrl(tempContextUrl+"qr/img/"+md5);
			image.setSize(file.getSize());
			image.setIsDeleted(YesOrNo.NO.getValue());
			image.setPath(filaPath+fileName);
			image.setGroupId(0);
			appMediaImg.addMediaImage(image);
//			serv.addImage(image);
		} else {
			image.setLastModifyTime(DateUtil.getCurrentDate());
			appMediaImg.updMeidaImage(image);
		}
		
		JSONObject json = new JSONObject();	
//		json.put("key", "Fr-kfOzzgqcRQFJhK0XTLCXnugUD");
//		json.put("hash", "Fr-kfOzzgqcRQFJhK0XTLCXnugUD");
//		json.put("fname", "2.JPG");
//		json.put("fsize", "12106");
//		json.put("mimeType", "image/jpeg");
		json.put("key", image.getHash());
		json.put("hash", image.getHash());
		json.put("fname", image.getTitle());
		json.put("fsize", image.getSize());
		json.put("mimeType", image.getMimeType());
		json.put("url", image.getUrl());
		ResponseUtil.write(response,json.toString(),"utf-8");
		return;
	}
	
	@RequestMapping(value="/img/{hash}")
	public void ajaxDownloadFile(@PathVariable("hash") String hash, HttpServletResponse response, HttpServletRequest request) throws Exception{
		
		MediaImage image = serv.getByHash(hash);
		if(image == null) {
			return;
		}
		response.reset();
		response.setHeader("Content-Disposition","attachment;filename="+new String((image.getTitle()).getBytes("gb2312"), "ISO-8859-1"));
		response.setContentType(image.getMimeType());
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			File file = new File(image.getPath());
			if(file.exists()){
				in = new FileInputStream(file.getCanonicalPath());
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
				outp.close();
			}
		} catch (Exception e) {
			System.out.println("已经取消下载!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return;
	}
	
	@RequestMapping(value="/video/{hash}")
	public void ajaxDownloadVideoFile(@PathVariable("hash") String hash, HttpServletResponse response, HttpServletRequest request) throws Exception{
		
//		MediaImage image = serv.getByHash(hash);
		
		MediaVideo video = appVideo.getVideoByHash(hash);
		if(video == null) {
			return;
		}
		response.reset();
		response.setHeader("Content-Disposition","attachment;filename="+new String((video.getTitle()).getBytes("gb2312"), "ISO-8859-1"));
		response.setContentType(video.getMimeType());
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			File file = new File(video.getPath());
			if(file.exists()){
				in = new FileInputStream(file.getCanonicalPath());
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
				outp.close();
			}
		} catch (Exception e) {
			WfLogger.error("", e);
			System.out.println("已经取消下载!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return;
	}
	
	@RequestMapping(value="/video/img/{hash}")
	public void ajaxDownloadVideoImageFile(@PathVariable("hash") String hash, HttpServletResponse response, HttpServletRequest request) throws Exception{
		
		MediaVideo video = appVideo.getVideoByImageHash(hash);
		if(video == null) {
			return;
		}
		response.reset();
		response.setHeader("Content-Disposition","attachment;filename="+new String((video.getTitle()).getBytes("gb2312"), "ISO-8859-1"));
//		response.setContentType(video.getMimeType());
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = response.getOutputStream();
			File file = new File(video.getImagePath());
			if(file.exists()){
				in = new FileInputStream(file.getCanonicalPath());
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
				outp.close();
			}
		} catch (Exception e) {
			WfLogger.error("", e);
			System.out.println("已经取消下载!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				in = null;
			}
		}
		return;
	}
}
