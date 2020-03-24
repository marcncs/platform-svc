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
	var checkid=0; 
	var checkname="";
	var checkspec="";
	var checkcertcode="";
	var checkspeccode="";
	var checkpacingratio="";
	var checkmaterialdescen="";
	var checknameen="";
	var checkspecen="";

	var checkwise=""; //add by tommy 
	
	function CheckedObj(obj,objid,objname,objspecmode, objregcertcode,objspeccode,objpackingratio,objmaterialdescen,objnameen,objspecen){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 } 
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	 checkspec = objspecmode;
	 checkcertcode = objregcertcode;
	 checkspeccode = objspeccode;
	 checkpacingratio = objpackingratio;
	 checkmaterialdescen = objmaterialdescen;
	 checknameen = objnameen;
	 checkspecen = objspecen;
	}

	function Affirm(){
	if(checkid!=""){
		var p={id:checkid,productname:checkname,specmode:checkspec,regcertcode:checkcertcode,speccode:checkspeccode,packingratio:checkpacingratio,mdescen:checkmaterialdescen,productnameen:checknameen,specen:checkspecen};
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
          <td width="772"> <bean:message key='product.select.title'/></td>
        </tr>
      </table>
       <form name="search" method="post" action="../common/selectTollerProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       		<input type="hidden" name="oid" value="${oid}" />
          <tr class="table-back"> 
            <%--<td width="18%" height="22" align="right">产品类别：</td>
            <td width="20%"><input type="hidden" name="KeyWordLeft" value="${KeyWordLeft}"><windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}" /></td> --%>
            <td width="29%" align="right"><bean:message key='system.keyword'/>：</td>
            <td width="33%"><input type="text" name="KeyWord" value="${KeyWord}"></td>
             <td class="SeparatePage">
            <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"/></td>
          </tr> 
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="70"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.confirm'/></a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="70"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;<bean:message key='system.cancel'/></a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td style="text-align: right;"><pages:pager language="en" action="../common/selectTollerProductAction.do"/></td>
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
            <td><bean:message key='system.id'/></td>
            <td><bean:message key='product'/></td>
            <td><bean:message key='product.materialcode'/></td>
            <td><bean:message key='product.specification'/></td>
<%--            <td>规格明细</td>--%>
          </tr>
          <logic:iterate id="p" name="sls" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.productname}','${p.specmode}','${p.regCertCode}','${p.specCode}','${p.packingRatio}','${p.matericalEnDes}','${p.productnameen}','${p.packSizeNameEn}');" onDblClick="Affirm();"> 
            <td>${p.id}</td>
            <c:choose>
            	<c:when test="${'en' eq localeLanguage}">
            		<td>${p.productnameen}</td>
            	</c:when>
            	<c:otherwise>
            		<td>${p.productname}</td>
            	</c:otherwise>
            </c:choose>
            <td>${p.mCode}</td>
            <c:choose>
            	<c:when test="${'en' eq localeLanguage}">
            		<td>${p.packSizeNameEn}</td>
            	</c:when>
            	<c:otherwise>
            		<td>${p.specmode}</td>
            	</c:otherwise>
            </c:choose>
<%--            <td>${p.packSizeName}</td>--%>
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
