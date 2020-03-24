<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/sorttable.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkpid="";
	var checkname="";
	var checkspec="";
	var checkunitid = "";
	var checkunit="";
	function CheckedObj(obj,objid,objpid,objname,objspec,objunitid,objunitidname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkpid = objpid;
	 checkname = objname;
	 checkspec = objspec;
	 checkunitid = objunitid;
	 checkunit= objunitidname;
	}

	function Affirm(){
	if(checkid!=""){	
		var p={id:checkid,productid:checkpid,productname:checkname,specmode:checkspec,unitid:checkunitid,unitidname:checkunit};
		window.returnValue=p;
		window.close();
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
          <td> 选择组装产品</td>
        </tr>
      </table>
       <form name="search" method="post" action="selectAssembleProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="14%" align="right">关键字：</td>
            <td><input type="text" name="KeyWord">
            </td>
            <td class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
			</td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
		<tr align="center" class="title-func-back"> 
          <td width="50"><a href="javascript:Affirm();"><img
				src="../images/CN/affirm.gif" width="16" height="16"
				border="0" align="absmiddle">&nbsp;确定</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
           <td width="50"><a href="javascript:window.close();"><img
				src="../images/CN/cancelx.gif" width="16" height="16"
				border="0" align="absmiddle">&nbsp;取消</a></td>
				<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td  class="SeparatePage"> <pages:pager action="../machin/selectAssembleProductAction.do"/></td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="12%" >编号</td>
            <td width="19%">产品编码</td>
            <td width="29%">产品名称</td>
            <td width="25%">规格</td>
            <td width="15%">单位</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr align="center" class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.arproductid}','${p.arproductname}','${p.arspecmode}','${p.arunitid}','${p.arunitidname}');" onDblClick="Affirm();"> 
            <td >${p.id}</td>
            <td>${p.arproductid}</td>
            <td>${p.arproductname}</td>
            <td>${p.arspecmode}</td>
            <td>${p.arunitidname}</td>
            </tr>
          </logic:iterate> 
      </table>
    </td>
  </tr>
</table>
</body>
</html>
