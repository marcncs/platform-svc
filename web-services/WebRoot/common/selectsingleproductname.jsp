<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="javascript">
	var checkname="";
	var checkcunitname="";
	var checkcountunit="";
	function CheckedObj(obj,objname,objunitname,objcountunit){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkname = objname;
	 checkcunitname = objunitname;
	 checkcountunit = objcountunit;
	}

	function Affirm(){
	if(checkname!=""){
		var p={productname:checkname,cunitname:checkcunitname,countunit:checkcountunit};
		window.returnValue=p;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}

this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
	}	
</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv" >
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectSingleProductNameAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="18%" height="22" align="right">产品类别：</td>
            <td width="20%"><input type="hidden" name="KeyWordLeft" value="${KeyWordLeft}"><windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}" /></td> 
            <td width="29%" align="right">关键字：</td>
            <td width="33%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
             <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"/></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager action="../common/selectSingleProductNameAction.do"/></td>
        </tr>
      </table>
       </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top-lock"> 
            <td width="50%">产品名称</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.productname}','${p.countunitname}','${p.countunit}');" onDblClick="Affirm();"> 
            <td>${p.productname}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
