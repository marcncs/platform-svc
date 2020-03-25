<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-html" prefix="html"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles"%>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript" src="../javascripts/prototype.js"></script>
		<script type="text/javascript" src="../javascripts/capxous.js"></script>
		<link rel="stylesheet" type="text/css" href="../styles/capxous.css" />
		<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}


	function Update(){
		if(checkid>0){
		location.href("updCustomerAction.do?Cid="+checkid);
		}else{
		alert("请选择你要操作的记录!");
		}
	}



</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>

	<body>

		<table width="100%" border="1" cellpadding="0" cellspacing="1"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								消息历史记录
							</td>
						</tr>
					</table>
					<form name="search" method="post" action="listMessageAction.do">
						<table width="100%" height="55" border="0" cellpadding="0"
							cellspacing="0">

							<tr class="table-back">
								<td width="13%" align="right">
									消息来源：
								</td>
								<td width="17%">
									<select name="select" size="1">
										<option value="0">
											选择
										</option>
										<option value="1">
											我发送的
										</option>
										<option value="2">
											我接收的
										</option>
									</select>
								</td>
								<td width="21%" align="right">
									消息时间 ：
								</td>
								<td width="49%">
									<input name="BeginDate" type="text" id="BeginDate"
										onFocus="selectDate(this);">
									-至
									<input name="EndDate" type="text" id="EndDate"
										onFocus="selectDate(this);">
								</td>
							</tr>
							<tr class="table-back">
								<td align="right">
									消息内容：
								</td>
								<td>
									<input type="text" name="KeyWord" value="">
								</td>
								<td align="right">
									是否已读：
								</td>
								<td>
									${isread}
									<input type="submit" name="Submit" value="查询">
								</td>
							</tr>

						</table>
					</form>
					<FORM METHOD="POST" name="listform" ACTION="">
						<table width="100%" border="0" cellpadding="0" cellspacing="1">

							<tr align="center" class="title-top">
								<td width="68" height="35">
									编号
								</td>
								<td width="200">
									发送者
								</td>
								<td width="201">
									接收者
								</td>
								<td width="408">
									消息内容
								</td>
								<td width="234">
									消息发送时间
								</td>
								<td width="131">
									是否阅读
								</td>
							</tr>
							<logic:iterate id="ms" name="msList">

								<tr align="center" class="table-back"
									onClick="CheckedObj(this,${ms.id});">
									<td>
										${ms.id}
									</td>
									<td>
										${ms.sendusername}
									</td>
									<td>
										${ms.acceptusername}
									</td>
									<td>
										${ms.message}
									</td>
									<td>
										${ms.sendtime}
									</td>
									<td>
										${ms.strisread}
									</td>
								</tr>
							</logic:iterate>

						</table>
					</form>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<!--<td width="48%"><table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="../sales/toAddCustomerAction.do">新增</a> 
                </td>
                <td width="60"><a href="javascript:Update(${c.cid});">修改</a></td>
                <td width="60">移交</td>
                <td width="60">共享</td>
              </tr>
            </table></td>-->
							<td width="52%">
								<table border="0" cellpadding="0" cellspacing="0">

									<tr>
										<td width="50%" align="right">
											<presentation:pagination
												target="/assistant/listMessageAction.do" />
										</td>
									</tr>

								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			</table>
			</body>
					</html>