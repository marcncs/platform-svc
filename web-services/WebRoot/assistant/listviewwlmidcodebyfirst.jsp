<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<script language="JavaScript">
	var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	}
	
	function excput(){
	alert("此功能暂不可用!");
	//search.action="../assistant/excPutViewWlmIdcodeAction.do";
	//search.submit();
	//search.action="../assistant/listViewWlmIdcodeAction.do?type=2";
	}
	
	function formChk(){
		var wlmidcode = search.wlmidcode.value.trim();
		if ( wlmidcode==''){
			alert("物流码不能为空！");
			return false;
		}
		return true;
	}
	
	function onclickOpen(idcode){
		search.action="../assistant/listViewWlmIdcodeAction.do?type=1&wlmidcode="+idcode;
		search.submit();
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
          <td width="772"> 防伪防窜货管理&gt;&gt;物流码流向查询</td>
        </tr>
      </table>
       <form name="search" method="post" action="../assistant/listViewWlmIdcodeAction.do?type=2" onSubmit="return formChk()">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
         
		  <tr class="table-back"> 
            <td width="9%" align="right">物流码：</td>
            <td width="33%"><input type="text" name="wlmidcode" value="${wlmidcode}" maxlength="32" size="32">&nbsp;<span class="STYLE1">请输入物流码</span></td>
            <td width="2%" align="right">&nbsp;</td>
            <td width="4%" class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="title-func-back"> 	
    	<td width="50" >
    		<!-- 
				<a href="#" onClick="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>		
			 -->
		<td class="SeparatePage"> <pages:pager action="../assistant/listViewWlmIdcodeAction.do?type=2"/> 
	</td>
	</tr>
</table>
      </div>
	</td>
	</tr>
	<tr>
		<td>
		<br/>
		<c:if test="${viewWlmIdcode != null}">
	      <table width="100%"  border="0" cellpadding="0" cellspacing="1" >
	          <tr> 
	          	<td width="10%"><font size="2"><b>物流码：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.startno}</td>
	          </tr>
	          <tr> 
	          	<td width="10%">&nbsp;</td>
	          	<td width="90%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="10%"><font size="2"><b>客户名称：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.cname}</td>
	          </tr>
	          <tr> 
	          	<td width="10%">&nbsp;</td>
	          	<td width="90%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="10%"><font size="2"><b>产品名称：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.productname}</td>
	          </tr>
	          <tr> 
	          	<td width="10%">&nbsp;</td>
	          	<td width="90%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="10%"><font size="2"><b>规格：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.specmode}</td>
	          </tr>
	          <tr> 
	          	<td width="10%">&nbsp;</td>
	          	<td width="90%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="10%"><font size="2"><b>批次：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.batch}</td>
	          </tr>
	          <tr> 
	          	<td width="10%">&nbsp;</td>
	          	<td width="90%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="10%"><font size="2"><b>生产日期：</b></font></td>
	          	<td width="90%">${viewWlmIdcode.producedate}</td>
	          </tr>
	      </table>
	      <table width="100%"  border="0" cellpadding="0" cellspacing="1" >
	          <tr> 
	          	<td width="100%">&nbsp;</td>
	          </tr>
	          <tr> 
	          	<td width="100%"><font size="2"><a href="#" onClick="onclickOpen('${viewWlmIdcode.startno}');">展开全链查询</a></font></td>
	          </tr>
	      </table>
      </c:if>
      <br>
    </td>
  </tr>
</table>
</body>
</html>
