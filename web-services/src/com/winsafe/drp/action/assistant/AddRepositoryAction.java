package com.winsafe.drp.action.assistant;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppRepository;
import com.winsafe.drp.dao.AppRepositoryFile;
import com.winsafe.drp.dao.AppRepositoryProduct;
import com.winsafe.drp.dao.Repository;
import com.winsafe.drp.dao.RepositoryFile;
import com.winsafe.drp.dao.RepositoryForm;
import com.winsafe.drp.dao.RepositoryProduct;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class AddRepositoryAction
    extends Action {
  public ActionForward execute(ActionMapping mapping, ActionForm form,
                               HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
	  


    try {

      UsersBean users = UserManager.getUser(request);
//      Long userid = users.getUserid();
      RepositoryForm rf = (RepositoryForm) form;

      Repository r = new Repository();
      r.setId(MakeCode.getExcIDByRandomTableName(
          "repository",2,"RT"));
      r.setRtid(rf.getRtid());
      r.setTitle(rf.getTitle());
      r.setContent(rf.getContent());
      r.setMakeorganid(users.getMakeorganid());
//      r.setMakedeptid(users.getMakedeptid());
//      r.setMakeid(userid);
      r.setMakedate(DateUtil.StringToDatetime(DateUtil.getCurrentDateTime()));
      StringBuffer keyscontent = new StringBuffer();
      keyscontent.append(r.getTitle()).append(",");


      String extName = null;
      String fileName = null;
      String filePath = request.getRealPath("/");
      AppRepositoryFile arf = new AppRepositoryFile();
      int i=0;
      Hashtable fileh = rf.getMultipartRequestHandler(). getFileElements(); 
      for (Enumeration e = fileh.keys(); e.hasMoreElements(); ) { 
          String key = (String) e.nextElement();
          FormFile formfile = (FormFile) fileh.get(key); 
          fileName = formfile.getFileName().toLowerCase().trim();
    	  if ( !fileName.equals("") ){
    		  
    		  extName = fileName.substring(fileName.indexOf("."), fileName.length());
    		  if (extName != null) {
    	          InputStream fis = formfile.getInputStream();
    	          String sDateTime = DateUtil.getCurrentDateTimeString();
    	          String rfid = MakeCode.getExcIDByRandomTableName(
    	        		  "repository_file",0,"");
    	          String saveFileName = sDateTime + rfid+extName;

    	          //建立一个上传文件的输出流
    	          OutputStream fos = new FileOutputStream(filePath +
    	                                                  "/upload/" + saveFileName);
    	          String fileAddress = "upload/" + saveFileName;
    	          byte[] buffer = new byte[8192];
    	          int bytesRead = 0;
    	          while ( (bytesRead = fis.read(buffer, 0, 8192)) != -1) {

    	            //把上传的文件放到服务器的指定目录下
    	            fos.write(buffer, 0, bytesRead);
    	          }
    	          fos.close();
    	          fis.close();
    	          //构造product

    	          RepositoryFile rfile = new RepositoryFile();
    	          rfile.setId(Long.valueOf(rfid));
    	          rfile.setRid(r.getId());
    	          rfile.setTitle(rf.getFiletitle()[i]);
    	          rfile.setFilepath(fileAddress);
    	          arf.addRepositoryFile(rfile);
    	        }
    	  }
    	  i++;
      }
      //-----------------------------------------------------------
      
      String strproductid[] = request.getParameterValues("productid");
      String strproductname[] = request.getParameterValues("productname");
      String strspecmode[] = request.getParameterValues("specmode");
      AppRepositoryProduct aprp = new AppRepositoryProduct();
      for (int j = 0; j < strproductid.length; j++) {
    	  RepositoryProduct rp = new RepositoryProduct();
    	  rp.setId(Long.valueOf(MakeCode.getExcIDByRandomTableName("repository_product",0,"")));
    	  rp.setRid(r.getId());
    	  rp.setProductid(strproductid[j]);
    	  rp.setProductname(strproductname[j]);
    	  rp.setSpecmode(strspecmode[j]);
    	  aprp.addRepositoryProduct(rp);
    	  keyscontent.append(rp.getProductid()).append(",")
    	  .append(rp.getProductname()).append(",")
    	  .append(rp.getSpecmode()).append(",");
      }
      r.setKeyscontent(keyscontent.toString());
      AppRepository ar = new AppRepository();
      ar.addRepository(r);

      request.setAttribute("result", "databases.add.success");
//      DBUserLog.addUserLog(userid,"新增知识库");//日志 

      return mapping.findForward("success");
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {

    }
    return mapping.getInputForward();
  }

}
