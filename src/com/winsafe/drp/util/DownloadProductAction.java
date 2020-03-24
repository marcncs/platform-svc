package com.winsafe.drp.util;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.winsafe.common.util.StringUtil;
import com.winsafe.drp.dao.AppICode;
import com.winsafe.drp.dao.AppProduct;
import com.winsafe.drp.dao.FUnit;
import com.winsafe.drp.dao.ICode;
import com.winsafe.drp.dao.Product;
import com.winsafe.drp.entity.EntityManager;

/**
 * Project:bright->Class:DownloadProductAction.java
 * <p style="font-size:16px;">Description：</p>
 * Create Time Oct 8, 2011 4:33:11 PM 
 * @author <a href='fazuo.du@winsafe.com'>dufazuo</a>
 * @version 0.8
 */
public class DownloadProductAction extends Action
{
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception {
		String scanid = request.getParameter("scannerid");
		String type = request.getParameter("type");
		
		if(StringUtil.isEmpty(type)){
			//下载产品信息
			
			return doDownloadProduct(mapping,form,request,response,type);
		}else if("1".equals(type)){
			//下载产品换算比例
			
			return doDownloadFunit(mapping,form,request,response);
		}else if("3".equals(type)){
			//下载进口产品
			
			return doDownloadProduct(mapping,form,request,response,type);
		}else{
			
			return null;
		}

	}
	
	private ActionForward doDownloadProduct(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response,String type) throws Exception{
		BufferedOutputStream bos = null;
		try 
		{
			bos = new BufferedOutputStream(response.getOutputStream());
			AppProduct ap = new AppProduct();
			AppICode appICode = new AppICode();
			String whereStr = " ";
			if("3".equals(type)){
				whereStr = " where  psid like '" +Constants.PS_CODE_1+ "%'";
			}
			List<Product> list = ap.getProduct(whereStr);
			String products = "";
			if(list != null)
			{
				for (int i = 0; i < list.size(); i++) 
				{
					/*if(list.get(i).getPsid().startsWith("10401")){
						//对于进口粉
						String pi = list.get(i).getId() + ","+list.get(i).getProductname()+","+list.get(i).getSpecmode()+";";
						products = products + pi;
					}else{
						String lcode = appICode.getLCodeByPid(list.get(i).getId());
						if("".equals(lcode))
						{
							continue;
						}
						String pi = lcode + ","+list.get(i).getProductname()+","+list.get(i).getSpecmode()+";";
						products = products + pi;
					}*/
					String lcode = appICode.getLCodeByPid(list.get(i).getId());
					if("".equals(lcode)){
						continue;
					}
					String pi = lcode + ","+list.get(i).getProductname()+","+list.get(i).getSpecmode()+";";
					products = products + pi;
				}
			}
			bos.write(products.getBytes("utf-8"));
			bos.write("\r\n".getBytes());
			bos.flush();
			bos.close();
		} 
		catch (RuntimeException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if ( bos != null )
			{
				try 
				{
					bos.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}		
		return null;
	}
	
	private ActionForward doDownloadFunit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception{
		BufferedOutputStream bos = null;
		try 
		{
			StringBuilder hql=new StringBuilder();
			hql.append(" from FUnit as f,Product as p,ICode as ic ");
			hql.append(" where f.productid=p.id");
			hql.append(" and ic.productid=p.id");
			hql.append(" and length(ic.lcode)=3");
			
			List list=EntityManager.getAllByHql(hql.toString());
			
			StringBuilder outputString=new StringBuilder();
			bos = new BufferedOutputStream(response.getOutputStream());

			FUnit aFUnit;
			Product aProduct;
			ICode aICode;
			if(list != null)
			{
				for (int i = 0; i < list.size(); i++) 
				{
					Object[] o = (Object[]) list.get(i);
					aFUnit=(FUnit)o[0];
					aProduct=(Product)o[1];
					aICode=(ICode)o[2];

					if(StringUtil.isEmpty(aICode.getLcode()))
					{
						continue;
					}
					//千克的不下载
					if(aFUnit.getFunitid().equals(18)){
						continue;
					}
					outputString.append(aICode.getLcode() + ","+aFUnit.getFunitid() + ","+aFUnit.getXquantity()+";");
				}
			}
			bos.write(outputString.toString().getBytes("utf-8"));
			bos.write("\r\n".getBytes());
			bos.flush();
			bos.close();
		} 
		catch (RuntimeException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if ( bos != null )
			{
				try 
				{
					bos.close();
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
