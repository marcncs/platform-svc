<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
function confirmCheck(){
	if(confirm("确定要批量授权所选机构吗？")){
		return true;
	}
	return false;
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 转仓机构&gt;&gt;批量授权设置</td>
        </tr>
      </table>
      <form name="search" method="post" action="setMoveOrganDateAction.do" onsubmit="return confirmCheck()">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
          <tr class="table-back"> 
	          <td  align="center">
	             <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	              <tr>
	                <td align="right">开始日期：</td>																																		
		            <td><input name="begindate" type="text" id="BeginDate" onFocus="javascript:selectDate(this)" size="15" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly></td>
		            <td align="right">结束日期：</td>
		            <td><input name="enddate" type="text" id="EndDate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="15" readonly></td>
	              </tr>
	             </table>
	            </td>
	            <input type="hidden" name="opids" value="${opids }"/>
          </tr>
          <tr align="center">
            <td  align="center" ><input type="Submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
      </table>
      </form>
      </div>
	</td>
	</tr>
</table>
</body>
</html>
