<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid="";
	var p={pid:"",pname:"",tel:"",mobile:"",fax:"",email:"",addr:"",pprompt:"",taxrate:""};
	function CheckedObj(obj,objid,objname,objtel,objmobile,objfax,objemail,objaddr,objprompt,objtaxrate){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 p.pid=checkid;
	 p.pname=objname;
	 p.tel=objtel;
	 p.mobile=objmobile;
	 p.fax=objfax;
	 p.email=objemail;
	 p.addr=objaddr;
	 p.pprompt=objprompt;
	 p.taxrate=objtaxrate;
	}

	function Affirm(){
	if(checkid!=""){
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

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择供应商</td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectProviderAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="16%"  align="right">名称关键字：</td>
            <td width="84%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
			 <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
      
      </table>
        </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		  <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../common/selectProviderAction.do"/></td>
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
            <td width="13%" >供应商编号</td>
            <td width="58%">名称</td>
            <td width="21%">电话</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.pid}','${p.pname}','${p.tel}','${p.mobile}','${p.fax}','${p.email}','${p.addr}','${p.prompt}','${p.taxrate}');" onDblClick="Affirm();"> 
            <td >${p.pid}</td>
            <td>${p.pname}</td>
            <td>${p.tel}</td>
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
