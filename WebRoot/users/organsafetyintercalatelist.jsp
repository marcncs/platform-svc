<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>	
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
var productid="";
	function CheckedObj(obj,objid,objproductid){
	
		for(i=0; i<obj.parentNode.childNodes.length; i++)
		{
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
		}
	 
		obj.className="event";
	 	checkid=objid;
		productid = objproductid;
	}
	
	function addNew(){
		popWin("../users/toAddOrganSafetyAction.do?oid=${OID}",900,650);
	}

	function Update(){
		if(checkid>0){
			popWin("../users/toUpdOrganSafetyAction.do?ID="+checkid,900,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Delete(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+productid+" 的记录吗？如果删除将永远不能恢复!")){
				popWin("../users/delOrganSafetyAction.do?ID="+checkid,500,250);
			}			
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
          <td width="772"> 产品报警设置</td>
        </tr>
      </table>
      <form name="search" method="post" action="listOrganSafetyIntercalateAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="12%" align="right">产品类别：</td>
            <td width="14%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/></td>
            <td width="10%" align="right">关键字：</td>
            <td width="29%"><input type="text" name="KeyWord" value="${KeyWord}">
                </td>
            <td width="7%"  align="right">&nbsp;</td>
            <td width="28%" class="SeparatePage">
            	<input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back"> 
		<td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
		 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td width="50"><a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td width="50"><a href="javascript:Delete();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
		<td class="SeparatePage"> <pages:pager action="../users/listOrganSafetyIntercalateAction.do"/> 
		</td>
		</tr>
	</table>
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td  >产品编号 </td>
            <td >产品名称</td>
            <td > 规格 </td>
            <td >最低安全库存 </td>
            <td > 最高安全库存</td>
          </tr>
          <logic:iterate id="p" name="alpl" >
		  
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,${p.id},'${p.productid}');"> 
            <td >${p.productid}</td>
            <td>${p.productname}</td>
            <td>${p.specmode}</td>
            <td>${p.safetyl}</td>
            <td>${p.safetyh}</td>
            </tr>
          </logic:iterate> 
      </table>
	 </td>
  </tr>
</table>
</body>
</html>
