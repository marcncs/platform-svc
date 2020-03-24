<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid="";
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	FlowDetail();
	}
	
	function addNew(){
	window.open("toAddApproveFlowAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
	
	function Update(){
		if(checkid!=""){
			window.open("toUpdApproveFlowAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}
	
	function FlowDetail(){
		if(checkid!=""){
			document.all.submsg.src="listApproveFlowDetailAction.do?afid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
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
          <td width="772"> 流程列表</td>
        </tr>
      </table>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<!--<td width="50" align="center">
				<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>	-->
		  <td class="SeparatePage"><pages:pager action="../sys/listApproveFlowAction.do"/></td>
							
		</tr>
	</table>
	 <FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
            <td >编号</td>
            <td >流程名称</td>
            <td >备注</td>
          </tr>
          <logic:iterate id="f" name="alpl" ><tr class="table-back-colorbar" onClick="CheckedObj(this,'${f.id}');"> 
            <td height="20">${f.id}</td>
            <td>${f.flowname}</td>
            <td>${f.memo}</td>
            </tr>
          </logic:iterate> 
        
      </table>   
      </form>    
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:FlowDetail();"><span>流程详情</span></a></li>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
</body>
</html>
