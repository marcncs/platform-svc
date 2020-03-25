<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	function newAdd(){
		popWin("toAddApproveFlowDetailAction.do?afid="+
		'${AFID}',700,400);
	}
	
	function Update(){
		if(checkid>0){
			popWin("toUpdApproveFlowDetailAction.do?ID="+checkid,700,400);
		}else{
			alert("<bean:message key='sys.selectrecord'/>");
		}
	}
	
	function Del(){
		if(checkid>0){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
				popWin2("../sys/delApproveFlowDetailAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}

	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert("<bean:message key='sys.nouserightkey'/>");}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 流程详情</td>
        </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
              <tr class="title-func-back"> 
                <td width="50"><a href="javascript:newAdd();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
                <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
                <td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
                <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
                <td width="50"><a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
    			<td>&nbsp; </td>	
        </tr>
      </table>     
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td >编号</td>
            <td >审阅者</td>
            <td>动作</td>
			<td>审阅顺序</td>
          </tr>
          <logic:iterate id="w" name="alpl" ><tr class="table-back-colorbar" onClick="CheckedObj(this,${w.id});"> 
            <td height="20">${w.id}</td>
            <td>${w.approveidname}</td>
            <td>${w.actidname}</td>
			<td>${w.approveorder}</td>
            </tr>
          </logic:iterate> 
      </table>
       
    </td>
  </tr>
</table>
</body>
</html>
