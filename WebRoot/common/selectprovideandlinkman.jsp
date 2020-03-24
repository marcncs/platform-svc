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
	var checkid=0;
	var checkname="";
	var plman="";
	var plmantel="";
	var plmanaddr="";
	function CheckedObj(obj,objid,objname,objplman,objplmantel,objplmanaddr){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 plman=objplman;
	 plmantel=objplmantel;
	 plmanaddr=objplmanaddr;
	 
	}

	function Affirm(){
	if(checkid!=""){
		var provide={pid:checkid,pname:checkname,plinkman:plman,plinkmantel:plmantel,plinkmanaddr:plmanaddr};
		window.returnValue=provide;
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
       <form name="search" method="post" action="../common/selectProvideAndLinkmanAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="16%" height="25" align="right">关键字：</td>
            <td width="84%" height="25"><input type="text" name="KeyWord" value="${KeyWord}">
              <input type="submit" name="Submit" value="查询"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager action="../common/selectProvideAndLinkmanAction.do"/></td>
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
            <td width="13%" height="25">供应商编号</td>
            <td width="58%">名称</td>
            <td width="21%">电话</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.pid}','${p.pname}','${p.plinkman}','${p.plinkmantel}','${p.plinkmanaddr}');" onDblClick="Affirm();"> 
            <td height="20">${p.pid}</td>
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
