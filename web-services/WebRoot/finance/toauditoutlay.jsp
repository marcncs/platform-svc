<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	function CheckedObj(obj,objid,objname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	}

	function Affirm(){
		if(checkid!=""){
		setCookie("pid",checkid);
		setCookie("productname",checkname);
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}

</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 录入付款来源 冲借</td>
        </tr>
      </table>      
        <FORM METHOD="POST" name="listform" ACTION="auditOutlayAction.do">
		<input type="hidden" name="id" value="${id}">
      <fieldset align="center"> 		  
		 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  <tr>
				<td width="9%"  align="right">付款来源：</td>
				  <td width="26%"><select name="fundsrc">
					 <logic:iterate id="d" name="cbs">
					  <option value="${d.id}">${d.cbname}</option>
					</logic:iterate>
				  </select>
				  </td>
				  <td width="9%" align="right">本次冲借：</td>
				  <td width="22%"><input type="text" name="thisresist"></td>
			  </tr>			
			  <tr>
				<td width="9%"  align="right">实付费用：</td>
				  <td width="26%"><input type="text" name="factpay">
				  </td>
				  <td width="9%" align="right">长短款：</td>
				  <td width="22%"><input type="text" name="accidentsum"></td>
			  </tr>			  
			  </table>
		</fieldset>
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">			 
			   <tr>
				<td align="center"><input type="submit" value="提交">&nbsp;&nbsp;<input type="button" value="取消" onClick="window.close();"></td>
				 
			  </tr>
			  </table>
        </form>
     
      
    </td>
  </tr>
</table>
</body>
</html>
