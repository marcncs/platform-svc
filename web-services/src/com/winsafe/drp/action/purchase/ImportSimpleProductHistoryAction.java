package com.winsafe.drp.action.purchase;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.winsafe.drp.dao.AppBaseResource;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.AppProductPriceHistory;
import com.winsafe.drp.dao.BaseResource;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.dao.ProductPriceHistory;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DateUtil;
public class ImportSimpleProductHistoryAction extends Action {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UsersBean users = UserManager.getUser(request);
		int CCount = 0, ECount = 0;
		String errorMsg = "";
		try {
			IdcodeUploadForm  mf = (IdcodeUploadForm) form;
			FormFile PriceHisfile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (PriceHisfile != null && !PriceHisfile.equals("")) {
				if (PriceHisfile.getContentType() != null) {
					if (PriceHisfile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(PriceHisfile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				AppProduct appproduct=new AppProduct();
				AppProductPriceHistory apph = new AppProductPriceHistory();
				AppBaseResource appbs=new AppBaseResource();
				
				ProductPriceHistory productPriceHistory = null;
				for (int i = 1; i < row; i++) {
					String nccode = sheet.getCell(0, i).getContents();
					String productname=sheet.getCell(1, i).getContents();
					//解析单位
					String unit=sheet.getCell(2, i).getContents();
					BaseResource bs= appbs.getBaseResourceKey("CountUnit",unit);
					//金额
					String unitPrice=sheet.getCell(3, i).getContents();
					//解析日期值
					String   starttime=sheet.getCell(4, i).getContents();
					String startDate = null;
					if(starttime!=null && starttime.length() >0){
						
					    String separator = starttime.indexOf('/') > 0 ? "/" : "-";
					    
					    String[] starttimetemp=starttime.split(separator);
						//String[] starttimetemp=starttime.split("/");
						startDate=starttimetemp[2]+"-"+starttimetemp[1]+"-"+starttimetemp[0];
						
						if(Integer.valueOf(starttimetemp[1])>12 || Integer.valueOf(starttimetemp[1])<1){
							errorMsg += "<br/>第[" + (i + 1) + "]行：请输入正确的时间!";
							ECount++;
							continue;	
						}
						if(Integer.valueOf(starttimetemp[0])>31 || Integer.valueOf(starttimetemp[0])<1){
							errorMsg += "<br/>第[" + (i + 1) + "]行：请输入正确的时间!";
							ECount++;
							continue;	
						}
					}
					if(startDate==null ||  startDate.length()==0){
						errorMsg += "<br/>第[" + (i + 1) + "]行：请输入开始日期!";
						ECount++;
						continue;	
						
					}
					String   endtime= sheet.getCell(5, i).getContents();
					String endDate = null;
                    if(endtime!=null && endtime.length() >0){
                    	
                        String separator = endtime.indexOf('/') > 0 ? "/" : "-";
  					  
                        String[] endtimetemp=endtime.split(separator);
                      //String[] endtimetemp=endtime.split("/");
    					endDate=endtimetemp[2]+"-"+endtimetemp[1]+"-"+endtimetemp[0];
    					if(Integer.valueOf(endtimetemp[1])>12 || Integer.valueOf(endtimetemp[1])<1){
							errorMsg += "<br/>第[" + (i + 1) + "]行：请输入正确的时间!";
							ECount++;
							continue;	
						}
						if(Integer.valueOf(endtimetemp[0])>31 || Integer.valueOf(endtimetemp[0])<1){
							errorMsg += "<br/>第[" + (i + 1) + "]行：请输入正确的时间!";
							ECount++;
							continue;	
						}
					}
                    if(endDate==null ||  endDate.length() ==0){
                    	errorMsg += "<br/>第[" + (i + 1) + "]行：请输入结束日期!";
						ECount++;
						continue;
                    }
                    if(DateUtil.StringToDate(startDate).compareTo(DateUtil.StringToDate(endDate))>0  ){
                    	errorMsg += "<br/>第[" + (i + 1) + "]行：起始日期不能大于结束日期!请核查";
						ECount++;
						continue;
                    }
                    
					String   demo= sheet.getCell(6, i).getContents();
					if(demo.length()>256){
						demo.substring(0, 256);
					}
					if (nccode == null || "".equals(nccode)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行：产品编码不能为空!";
						ECount++;
						continue;
					}
					if (productname == null || "".equals(productname)) {
						errorMsg += "<br/>第[" + (i + 1) + "]行：产品名称不能为空!";
						ECount++;
						continue;
					}
				    Product pp=appproduct.findProductByNccode(nccode);
				    
					if(pp==null){
						errorMsg += "<br/>第[" + (i + 1) + "]行：  "+productname+"查无此产品!"  ;
						ECount++;
						continue;
					}
				    if(pp!=null){
				    	//判断开始日期与结束日期是否有重叠部分
						boolean isFlag = apph.getProductPriceHistoryByProductIdAndTime(pp.getId(), startDate, endDate);
						//如果有重叠部分返回
						if(isFlag){
			        		errorMsg += "<br/>第[" + (i + 1) + "]行：产品编号为"+pp.getNccode()+"的产品价格在" + startDate +"与" + endDate + "之间有重叠部分，请重新录入!"  ;
							ECount++;
							continue;
						}
				    	productPriceHistory = new ProductPriceHistory();
				    	productPriceHistory.setProductId(pp.getId());
				    	if(bs!=null){
				    		productPriceHistory.setUnitId(bs.getTagsubkey());
				    	}else{
				    		productPriceHistory.setUnitId(pp.getCountunit());
				    	}
				    	productPriceHistory.setMakeUserId(Long.valueOf(users.getUserid()));
				    	productPriceHistory.setMakeDate(DateUtil.getCurrentDate());
				    	productPriceHistory.setMakeOrganId(users.getMakeorganid());
				    	productPriceHistory.setProductName(pp.getProductname());
				    	productPriceHistory.setStartTime(DateUtil.StringToDate(startDate));
				    	productPriceHistory.setEndTime(DateUtil.StringToDate(endDate));
				    	productPriceHistory.setUnitPrice(Double.valueOf(unitPrice));
				    	productPriceHistory.setMemo(demo);
				    	apph.addProductPriceHistory(productPriceHistory);
				    }
					CCount++;
				}
				wb.close();
			} else {
				request.setAttribute("result", "上传文件失败,请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}
			request.setAttribute("result", "上传用户资料完成,本次总共添加 :"
					+ (CCount + ECount) + "条! 成功:" + CCount + "条! 失败：" + ECount
					+ "条!" + errorMsg);
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败,文件格式不对,请重试");
			return new ActionForward("/sys/lockrecord2.jsp");
		}

	}	
}
