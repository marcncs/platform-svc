<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<base target="_self">
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	//var checkname="";
	//var checktel="";
	var pl={lid:"",lname:"",ltel:"",lmobile:""};
	function CheckedObj(obj,objid,objname,objtel,objmobile){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 pl.lid=checkid;
	 pl.lname=objname;
	 pl.ltel=objtel;
	 pl.lmobile=objmobile;
	}

	function Affirm(){
	if(checkid>0){
		//var l={lid:checkid,lname:checkname,ltel:checktel};
		window.returnValue=pl;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function addNew(){	
		window.open("../purchase/toAddPlinkmanAction.do?pid=${pid}","dddddds","height=400,width=900,top=250,left=100,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
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
          <td width="772"> 选择联系人</td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectPlinkmanAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="12%"  align="right">关键字：</td>
            <td width="18%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
            <td width="20%" >&nbsp;</td>
            <td width="23%" >&nbsp;</td>
            <td width="27%" align="right" ><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
          </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:addNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>
          <td class="SeparatePage"><pages:pager action="../common/selectPlinkmanAction.do"/></td>
        </tr>
      </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="35%">联系人姓名</td>
            <td width="10%">性别</td>
            <td width="25%">电话</td>
            <td width="30%">手机</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${p.id},'${p.name}','${p.officetel}','${p.mobile}');" onDblClick="Affirm();"> 
            <td>${p.name}</td>
            <td><windrp:getname key='Sex' value='${p.sex}' p='f'/></td>
            <td>${p.officetel}</td>
            <td>${p.mobile}</td>
          </tr>
          </logic:iterate> 
      </table>
	  </div>
    </td>
  </tr>
</table>
</body>
</html>
