<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 
	}
	
	function addNew(){
	popWin("toAddMemberGradeRuleAction.do",700,400);
	}
	
	function Update(){
		if(checkid!=''){
			popWin("toUpdMemberGradeRuleAction.do?ID="+checkid,700,400);
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}


	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 会员级别晋级规则列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
		  <td class="SeparatePage"><pages:pager action="../sys/listMemberGradeRuleAction.do"/></td>
							
		</tr>
	</table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td width="18%"  >编号</td>
            <td width="18%" >首次起始与结束金额(元)</td>
			<td width="18%" >起始与结束积分(/年)</td>
            <td width="34%" >会员级别</td>
          </tr>
          <logic:iterate id="w" name="wls" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${w.id}');"> 
            <td height="20">${w.id}</td>
            <td><fmt:formatNumber value="${w.startprice}" pattern="0.00"/>~<fmt:formatNumber value="${w.endprice}" pattern="0.00"/></td>
			<td><fmt:formatNumber value="${w.startintegral}" pattern="0.00"/>~<fmt:formatNumber value="${w.endintegral}" pattern="0.00"/></td>
            <td>${w.gradename}</td>
            </tr>
          </logic:iterate> 
      </table>     
    </td>
  </tr>
</table>
</body>
</html>
