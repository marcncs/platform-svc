<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
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
	
	function addNew(){
		popWin("toAddProviderProductAction.do",900,600);
	}

	function Del(){
		if(checkid!=""){
			if(window.confirm("您确认要删除编号为："+checkid+"的记录吗？如果删除将永远不能恢复!")){
				popWin("../purchase/delProviderProductAction.do?ID="+checkid,500,250);
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
          <td width="772"> 相关产品 </td>
        </tr>
      </table>
       <form name="search" method="post" action="../purchase/listProviderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="10%"  align="right">关键字：</td>
            <td width="35%"><input type="text" name="KeyWord" value="${KeyWord}">
                </td>
            <td width="16%" align="right">&nbsp;</td>
            <td width="39%" class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:Del();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../purchase/listProviderProductAction.do"/></td>
        </tr>
      </table>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
            <td >编号</td>
            <td  >产品名</td>
            <td >规格</td>
            <td >计量单位</td>
            <td >条形码</td>
            <td >报价</td>
            <td >报价日期</td>
          </tr>
          <logic:iterate id="pp" name="alpl" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${pp.id}');"> 
            <td>${pp.id}</td>
            <td >${pp.pvproductname}</td>
            <td>${pp.pvspecmode}</td>
            <td><windrp:getname key='CountUnit' value='${pp.countunit}' p='d'/></td>
            <td>${pp.barcode}</td>
            <td>${pp.price}</td>
            <td><windrp:dateformat value='${pp.pricedate}' p='yyyy-MM-dd'/></td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
