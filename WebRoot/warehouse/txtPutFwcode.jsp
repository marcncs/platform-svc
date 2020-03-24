<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script language="JavaScript">
	function ChkValue(){
		var startdate = document.getElementById("startdate").value;
		var enddate = document.getElementById("enddate").value;
		if(startdate ==""){
			alert("请选择开始时间！");
			return;
		}
		if(enddate ==""){
			alert("请选择结束时间！");
			return;
		}
		if(startdate > enddate){
			alert("日期输入不正确，请重新输入！");
			return;
		}
		exportform.submit();
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
	<body >
	<table width="100%" border="1" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  		<tr>
    		<td>
    			<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        			<tr> 
          				<td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          				<td width="772">请选择日期</td>
        			</tr>
      			</table>
       			<form name="exportform" method="post" action="../warehouse/txtPutFwcodeAction.do">
      				<table width="100%" border="0" cellpadding="0" cellspacing="1">      
          				<tr> 
            				<td >			
								<fieldset align="center"> 
									<legend>
      									<table width="50" border="0" cellpadding="0" cellspacing="0">
        									<tr>
          										<td>选择日期</td>
        									</tr>
      									</table>
	  								</legend>
	  								<table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  									<tr>
	  										<td width="20%" align="right">请输入日期：</td>
            								<td width="80%">
            									<input id="startdate" name="startdate" type="text" onFocus="javascript:selectDate(this)" size="12" value="${date }" readonly>-
  												<input id="enddate" name="enddate" type="text" onFocus="javascript:selectDate(this)" size="12" value="${date }" readonly>
  											</td>
	  									</tr>	
	  								</table>
								</fieldset>
							</td>
          				</tr>
				        <tr>
				            <td  align="center">
				            	<input type="button" value="确定" onClick="ChkValue();">&nbsp;&nbsp;
				            	<input type="button" value="取消" onClick="window.close();">
				            </td>
				        </tr>				        
      				</table>
     			 </form>
    		</td>
  		</tr>
	</table>
	</body>
</html>
