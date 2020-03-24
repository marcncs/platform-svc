package com.winsafe.drp.action.yun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.springframework.util.DigestUtils;

import com.winsafe.drp.action.BaseAction;
import com.winsafe.drp.dao.MediaImage;
import com.winsafe.sap.metadata.YesOrNo; 

public class AddMediaImgAction extends BaseAction {

	public ActionForward execute(ActionMapping mapping , ActionForm form , HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		MediaImage mi = (MediaImage) form;
		
		FormFile imgFile = mi.getImage();
		mi.setTitle(imgFile.getFileName());
		String fileName = UUID.randomUUID().toString() +  imgFile.getFileName().substring(imgFile.getFileName().lastIndexOf("."));
		String filePath = request.getSession().getServletContext().getRealPath("/");
		String imgFilePath = "";
		File file = null;
		if (fileName != null && !fileName.equals("")) {
			InputStream inputStream = imgFile.getInputStream();
			imgFilePath = filePath +"img";
			if (!new File(imgFilePath).exists()) {
				new File(imgFilePath).mkdirs();
			}
			file = new File(imgFilePath , fileName);
			OutputStream outputStream = new FileOutputStream(file.getPath());
			byte[] buffer = new byte[1048576];
			int bytesRead = 0;
			while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {			
				outputStream.write(buffer, 0, bytesRead);
			}	
			String md5 = getFileMD5(file).toUpperCase();
			outputStream.close();
			inputStream.close();
			String url = null;
			String imgUrl = null;
			if (File.separator.equals("\\")) {
				imgUrl = "../" + file.getPath().substring(file.getPath().lastIndexOf("img")).replaceAll("\\\\", "/");	
			}else {
				imgUrl = "../" + file.getPath().substring(file.getPath().lastIndexOf("img"));
			}
			mi.setUrl(imgUrl);
			mi.setSize(Long.valueOf(imgFile.getFileSize()));
			mi.setHash(md5);
		}
		
		mi.setCreateTime(new Date());
		mi.setIsDeleted(YesOrNo.NO.getValue());
		
		request.setAttribute("result", "databases.add.success");
		return mapping.findForward("success");
	}
	
	private String getFileMD5(File file) throws Exception {
        if (file == null || !file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
        finally{
        	try {
        		if(in != null){
        			in.close();
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return DigestUtils.md5DigestAsHex(digest.digest());
    }
}
