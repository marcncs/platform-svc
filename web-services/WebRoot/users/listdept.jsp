<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>		 
<html>
<head>

<title>角色列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var checkid=0;
function CheckedObj(obj,objid){

 for(i=1; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;

}

function addNew(){
	popWin("../users/toAddDeptAction.do?OID=${OID}",700,400);
	}

function UpdRole(){
	if(checkid>0){
		popWin("toUpdDeptAction.do?ID="+checkid,700,400);
	}else{
		alert("请选择你要修改的记录!");
	}
}

function Del(){
	if(checkid!=""){
		if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复！") ){
			popWin2("../users/delDeptAction.do?ID="+checkid);
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
        <td width="772">部门列表</td>
      </tr>
    </table>
     <form name="search" method="post" action="../users/listDeptAction.do">
	<table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
         <input type="hidden" name="OID" value="${OID}">
    <tr class="table-back"> 
      <td width="8%"  align="right">关键字：</td>
      <td><input type="text" name="KeyWord" value="${KeyWord}"> </td>
      <td align="right"></td>
      <td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
    </tr>
  	
	</table>
	</form>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
			<td width="50" align="center">
				<a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" align="center">
				<a href="javascript:UpdRole();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
            <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			<td width="50" align="center">
				<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>		
		  <td class="SeparatePage"><pages:pager action="../users/listDeptAction.do"/></td>						
		</tr>
	</table>
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
            <tr align="center" class="title-top">
              <td width="19%" >编号</td>
              <td width="42%">部门名称</td>              
              <td width="39%">所属机构</td>
            </tr>
              <logic:iterate id="d" name="dpt" >
                <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${d.id});">
                  <td >${d.id}</td>
                  <td>${d.deptname}</td>
              
                  <td><windrp:getname key='organ' value='${d.oid}' p='d'/></td>
                </tr>
              </logic:iterate>
      </table>
    </td>
  </tr>
</table>
<br>
<IFRAME id="submsg" 
      style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" 
      name="submsg" src="" frameBorder="0" scrolling="no"></IFRAME>
</body>
</html>

