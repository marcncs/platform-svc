<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html>
<head>

<title>仓位列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var checkid=0;
var wbid="";
function CheckedObj(obj,objid,objwbid){

 for(i=1; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
 wbid=objwbid;
}

function addNew(){
	popWin("../sys/toAddWarehouseBitAction.do",700,400);
	}

function UpdRole(){
	if(checkid>0){
		popWin("toUpdDeptAction.do?ID="+checkid,700,400);
	}else{
		alert("请选择你要修改的记录!");
	}
}

function Del(){
	if(checkid>0){
	if(wbid=='000'){
		alert("000是默认仓位，不能删除！");
		return;
	}
	if(window.confirm("您确认要删除编号为"+wbid+"记录吗？如果删除将永远不能恢复!")){
		popWin("../sys/delWarehouseBitAction.do?ID="+checkid,500,250);
		}
	}else{
	alert("请选择你要操作的记录!");
	}
}


</script>
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
      <tr>
        <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
        <td >仓位列表</td>
      </tr>
    </table>
     <form name="search" method="post" action="../sys/listWarehouseBitAction.do?WID=${WID}">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
    <tr class="table-back"> 
      <td width="8%"  align="right">关键字：</td>
      <td><input type="text" name="KeyWord" value="${keyword}"> </td>
      <td align="right"></td>
      <td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
								src="../images/CN/search.gif" border="0" title="查询"> </td>
    </tr>
  	
	</table>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		  <td class="SeparatePage"><pages:pager action="../sys/listWarehouseBitAction.do"/></td>						
		</tr>
	</table>
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="19%" >仓位编号</td>
              <td width="39%">所属仓库</td>
            </tr>
              <logic:iterate id="d" name="wls" >
                <tr class="table-back-colorbar" onClick="CheckedObj(this,${d.id},'${d.wbid}');">
                  <td >${d.wbid}</td>              
                  <td><windrp:getname key='warehouse' value='${d.wid}' p='d'/></td>
                </tr>
              </logic:iterate>
      </table>
    </td>
  </tr>
</table>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>

