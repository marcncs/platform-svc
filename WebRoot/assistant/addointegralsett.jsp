<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
	function ChkValue(){
		var cid = document.validateProvide.oid;
		if( cid == "" ){
			alert("请选择机构！");
			return false;
		}
				
		return true;
	}
</script>
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>

<SCRIPT language=javascript>
//
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="1">
							</td>
							<td width="772">
								新增机构积分结算单
							</td>
						</tr>
					</table>
					<form name="validateProvide" method="post"
						action="../assistant/addOIntegralSettAction.do"
						onSubmit="return ChkValue();">

						<table width="100%" border="0" cellpadding="0" cellspacing="1">
							<tr>
								<td>

									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														基本信息
													</td>
												</tr>
											</table>
										</legend>
										<table width="100%" border="0" cellpadding="0" cellspacing="1">
											<tr>
												<td width="9%" align="right">
													结算机构：
												</td>
												<td>
													<select name="oid" id="oid">
														<option value="">
															所有机构
														</option>
														<logic:iterate id="o" name="ols">
															<option value="${o.id}">
																${o.organname}
															</option>
														</logic:iterate>
													</select>
												</td>
												<td width="9%" align="right">
													结算积分：
												</td>
												<td>
													<input type="text" name="settintegral">
												</td>
												<td width="9%" align="right">
													换取金额：
												</td>
												<td>
													<input type="text" name="settcash">
												</td>
											</tr>
										</table>
									</fieldset>

								</td>

							</tr>
						</table>
						</form>
				</td>
          </tr>
          <tr>
            <td  align="center"><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="history.back();"></td>
          </tr>
        
      </table>
       
</body>
</html>
