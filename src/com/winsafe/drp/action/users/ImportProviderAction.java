package com.winsafe.drp.action.users;



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
import com.winsafe.hbm.util.DateUtil;
import com.winsafe.hbm.util.MakeCode;

public class ImportProviderAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		
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
					p.setVocation(Integer.valueOf(sheet.getCell(2, i)
							.getContents()));
					p.setTaxcode(sheet.getCell(3, i).getContents());
					p.setCorporation(sheet.getCell(4, i).getContents());
					p.setBankaccount(sheet.getCell(5, i).getContents());
					p.setBankname(sheet.getCell(6, i).getContents());
					p.setGenre(Integer.valueOf(sheet.getCell(7, i)
							.getContents()));
					p.setTel(sheet.getCell(8, i).getContents());
					p.setFax(sheet.getCell(9, i).getContents());
					p.setMobile(sheet.getCell(10, i).getContents());
					p.setEmail(sheet.getCell(11, i).getContents());
					p.setPostcode(sheet.getCell(12, i).getContents());
					p.setAddr(sheet.getCell(13, i).getContents());
					p.setAbcsort(Integer.valueOf(sheet.getCell(14, i)
							.getContents()));
					p.setPrompt(Integer.valueOf(sheet.getCell(15, i)
							.getContents()));
					p.setTaxrate(Double.valueOf(sheet.getCell(16, i)
							.getContents()));
					p.setPaycondition(sheet.getCell(17, i).getContents());
					p.setHomepage(sheet.getCell(18, i).getContents());
					p.setProvince(Integer.valueOf(sheet.getCell(19, i)
							.getContents()));
					p.setCity(Integer.valueOf(sheet.getCell(20, i)
							.getContents()));
					p.setAreas(Integer.valueOf(sheet.getCell(21, i)
							.getContents()));
					p.setMakeorganid(sheet.getCell(22, i).getContents());
					p.setMakedeptid(Integer.valueOf(sheet.getCell(23, i)
							.getContents()));
					p.setMakeid(Integer.valueOf(sheet.getCell(24, i)
							.getContents()));
					p.setMakedate(DateUtil.StringToDate(sheet.getCell(25, i)
							.getContents()));
					p.setUpdateid(Integer.valueOf(sheet.getCell(26, i)
							.getContents()));
					p.setModifydate(DateUtil.StringToDate(sheet.getCell(27, i)
							.getContents()));
					p.setRemark(sheet.getCell(28, i).getContents());
					p.setIsactivate(Integer.valueOf(sheet.getCell(29, i)
							.getContents()));
					p.setActivateid(Integer.valueOf(sheet.getCell(30, i)
							.getContents()));
					p.setActivatedate(DateUtil.StringToDate(sheet
							.getCell(31, i).getContents()));
					p.setIsdel(Integer.valueOf(sheet.getCell(32, i)
							.getContents()));
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
