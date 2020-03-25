package com.winsafe.drp.util.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.winsafe.drp.dao.UserManager;
import com.winsafe.drp.dao.UsersBean;


/**
 * 设备与运维管理系统
 * <p style="font-size:16px;">Description：</p>
 * Create Time Dec 4, 2012 4:29:30 PM 
 * @author <a href='xiaoyong.huang@winsafe.cn'>huangxy</a>
 * @version 0.8
 */
public class AuthorityTag extends BodyTagSupport
{
	private static final long serialVersionUID = 1L;

	/** 权限操作名称，也可以是权限操作组合名称 */
	private String operationName;

	@Override
	public int doStartTag() throws JspException{
		
		if (operationName.startsWith("!")){
			operationName = operationName.replaceFirst("!", "");
			if (hasAuthorization()){
				//不显示标签间的内容
				return SKIP_BODY;
				
			}
			else{
				//显示标签间的内容
				return EVAL_BODY_INCLUDE;
			}
		}
		
		//根据显示权限处理是否显示标签间内容
		if (hasAuthorization())
		{
			//显示标签间的内容
			return EVAL_BODY_INCLUDE;
		}
		else
		{
			//不显示标签间的内容
			return SKIP_BODY;
		}
	}


	/**
	 * 
	 * 验证是否有操作权限
	 * Create Time Dec 4, 2012 4:28:55 PM 
	 * @return
	 * @author huangxy
	 */
	private boolean hasAuthorization()
	{
		//获取页面请求
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
	    UsersBean users=null;
		try {
			users = UserManager.getUser(request);
		} catch (Exception e) {
		}
		if(users==null){
			return false;
		}
	    int userid = users.getUserid();
	    
		return UserManager.isPermit(operationName, userid);
	}

	public String getOperationName()
	{
		return operationName;
	}

	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}
}

