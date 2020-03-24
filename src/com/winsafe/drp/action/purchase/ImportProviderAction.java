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

import com.winsafe.drp.dao.AppMakeConf;
import com.winsafe.drp.dao.AppProvider;
import com.winsafe.drp.dao.IdcodeUploadForm;
import com.winsafe.drp.dao.MakeConf;
import com.winsafe.drp.dao.Provider;
import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;
import com.winsafe.hbm.util.DataFormat;
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportProviderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UsersBean users = UserManager.getUser(request);
		int CCount =0,ECount=0;
		try {
			IdcodeUploadForm mf = (IdcodeUploadForm) form;

			FormFile idcodefile = (FormFile) mf.getIdcodefile();
			boolean bool = false;
			if (idcodefile != null && !idcodefile.equals("")) {

				if (idcodefile.getContentType() != null) {
					if (idcodefile.getFileName().indexOf("xls") >= 0) {
						bool = true;
					}
				}
			}
			if (bool) {
				Workbook wb = Workbook.getWorkbook(idcodefile.getInputStream());
				Sheet sheet = wb.getSheet(0);
				int row = sheet.getRows();
				AppProvider app = new AppProvider();
				AppMakeConf appm = new AppMakeConf();
				MakeConf mc = appm.getMakeConfByID("provide");
				Provider p = null;
				for (int i = 1; i < row; i++) {

					p = new Provider();
					String pid = "";
					boolean isExist = true;
					if (mc.getRunmode() == 0) {
						pid = sheet.getCell(0, i).getContents();
						Provider oldp = app.getProviderByID(pid);
						if (oldp != null) {
							isExist = false;
						}
					} else {
						pid = MakeCode.getExcIDByRandomTableName("provide", 4,
								"");
					}

					p.setPid(pid);
					p.setPname(sheet.getCell(1, i).getContents());
					p.setVocation(DataFormat.strToInt(sheet.getCell(2, i).getContents()));
					p.setTaxcode(sheet.getCell(3, i).getContents());
					p.setCorporation(sheet.getCell(4, i).getContents());
					p.setBankaccount(sheet.getCell(5, i).getContents());
					p.setBankname(sheet.getCell(6, i).getContents());
					p.setGenre(DataFormat.strToInt(sheet.getCell(7, i).getContents()));
					p.setTel(sheet.getCell(8, i).getContents());
					p.setFax(sheet.getCell(9, i).getContents());
					p.setMobile(sheet.getCell(10, i).getContents());
					
					p.setEmail(sheet.getCell(11, i).getContents());
					p.setPostcode(sheet.getCell(12, i).getContents());
					p.setAddr(sheet.getCell(13, i).getContents());
					p.setAbcsort(DataFormat.strToInt(sheet.getCell(14, i).getContents()));
					p.setPrompt(DataFormat.strToInt(sheet.getCell(15, i).getContents()));
					p.setTaxrate(Double.valueOf(sheet.getCell(16, i).getContents()));
					p.setPaycondition(sheet.getCell(17, i).getContents());
					p.setHomepage(sheet.getCell(18, i).getContents());
					p.setProvince(DataFormat.strToInt(sheet.getCell(19, i).getContents()));
					p.setCity(DataFormat.strToInt(sheet.getCell(20, i).getContents()));
					
					p.setAreas(DataFormat.strToInt(sheet.getCell(21, i).getContents()));
					p.setMakeorganid(users.getMakeorganid());
					p.setMakedeptid(users.getMakedeptid());
					p.setMakeid(users.getUserid());
					p.setMakedate(DateUtil.getCurrentDate());
					p.setUpdateid(0);
					p.setModifydate(null);
					p.setRemark(sheet.getCell(22, i).getContents());
					p.setIsactivate(DataFormat.strToInt(sheet.getCell(23, i).getContents()));
					p.setActivateid(0);
					p.setActivatedate(null);
					p.setIsdel(0);
					if (isExist) {
						app.addProvider(p);
						CCount++;
					} else {
						//WriterTxt(request, p);
						ECount++;
					}
				}
				wb.close();

			} else {

				request.setAttribute("result", "上传文件失败，请重试");
				return new ActionForward("/sys/lockrecord2.jsp");
			}

			request.setAttribute("result", "上传供应商成功,本次总共添加 :"+(CCount+ECount)+"条! 成功:"+CCount+"条! 失败："+ECount+"条!");
			return mapping.findForward("success");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("result", "上传文件失败，请重试");
			return new ActionForward("/sys/lockrecord2.jsp");

		}

	}

//	private void WriterTxt(HttpServletRequest request, Provider p) {
//		System.out.println("编号: " + p.getPid() + " 已经存在!");
//		String s = new String();
//		String s1 = new String();
//		try {
//			String path = request.getRealPath("/")+"error.txt";
//			File f = new File(path);
//			BufferedReader input = new BufferedReader(new FileReader(f));
//
//			while ((s = input.readLine()) != null) {
//				s1 += s + "\n";
//			}
//			System.out.println("文件内容：" + s1);
//			input.close();
//			s1 += "编号为："+p.getPid() +"  供应商名 为 ："+p.getPname()+" 已经存在\n" ;
//
//			BufferedWriter output = new BufferedWriter(new FileWriter(f));
//			output.write(s1);
//			output.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
