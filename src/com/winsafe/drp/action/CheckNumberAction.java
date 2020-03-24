package com.winsafe.drp.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.drp.util.CheckImgGenerator;
import com.winsafe.drp.util.Constants;

/**
 * @author Fox
 *
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CheckNumberAction extends Action{
        Logger logger=Logger.getLogger(CheckNumberAction.class);
        public ActionForward execute(ActionMapping mapping,ActionForm form ,HttpServletRequest request,HttpServletResponse response)throws Exception{
                response.setHeader("Pragma","No-cache");
                response.setHeader("Cache-Control","no-cache");
                response.setHeader("Expires","0");
                response.setContentType("image/jpeg");

                CheckImgGenerator tmpImgGen=new CheckImgGenerator();

                String tmpCheckNum=tmpImgGen.generateCheckImg(response.getOutputStream());
                request.getSession().setAttribute(Constants.CHECKIMG_NUMBER,tmpCheckNum);
                logger.debug("generate check number : "+tmpCheckNum);
                response.flushBuffer();

                return null;
        }


}
