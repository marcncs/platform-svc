<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="ws" uri="ws" %>
<html>
<head>
<title>ScannerList--扫描器列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	var checkcname="";
	var pdmenu=0;
	var warHouseId={warhouseid:""};
	function CheckedObj(obj,objid,objwarhouseid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 warHouseId.warhouseid=objwarhouseid;
	}

	function addNew(){
		window.open("../scanner/toAddScannerAction.do","","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
			window.open("../scanner/toUpdateScannerAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的产品吗?") ){
				window.open("../scanner/delScannerAction.do?ID="+checkid,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function OutPut(){
		excputform.action="excPutScannerAction.do";
		excputform.submit();
	}
	
	function scannerUpdate(){

		if(checkid!=""){
			document.all.submsg.src="productProviderCompareAction.do?PDID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Import(){		
		window.open("../scanner/toImportScannerAction.do","","height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		
	}
	
	function ProductHistory(){
	setCookie("PdCookie","2");
		if(checkid!=""){
			document.all.submsg.src="listProductHistoryAction.do?productid="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Affirm(){
		if(checkid!=""){
		window.returnValue=warHouseId;
		window.close();
		}else{
		alert("请选择你要操作的记录!");
		}

	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<body>
<SCRIPT language=javascript>
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="bodydiv">
      <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 采集器仓库设置&gt;&gt;仓库号
            <input type="hidden" name="ID" value="${id}"></td>
        </tr>
      </table>
    <form name="search" method="post" action="../sys/toListScannerAction.do" >
     </form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
 			 <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
      		 <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
  			 <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>							
		</table>
	  </div>
	 </td>
	</tr>
	<tr>
	 <td>
	  <div id="listdiv" style="overflow-y: auto; height: 600px;" >	
       <FORM METHOD="POST" name="listform" ACTION="">
        <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top"> 
            <td  width=20%>仓库号</td>
            <td  width=40%>仓库名</td>
            <td  width=40%>机构号</td>
            
          </tr>
          <%-- 输出Scanner表中的数据 --%>
          <logic:iterate id="p" name="WareHouse" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${p.id}','${p.id}');"  onDblClick="Affirm();">
            <td>${p.id}</td>
     		<td>${p.warehousename}</td>
     		<td>${p.makeorganid}</td>
          </tr>
          </logic:iterate> 
      	</table>
       </form>
       </div>
       </table>
       <br>
<form  name="excputform" method="post" action="excPutScannerAction.do">
<input type="hidden" name="idSearch" id ="idSearch" value="${idSearch}">
<input type="hidden" name="modelSearch" id ="modelSearch" value="${modelSearch}"/>
<input type="hidden" name="osVersionSearch" id ="osVersionSearch" value="${osVersionSearch}"/>
<input type="hidden" name="statusSearch" id ="statusSearch" value="${statusSearch}"/>
<input type="hidden" name="installDateSearch" id ="installDateSearch" value="${installDateSearch}"/>
<input type="hidden" name="appVersionSearch" id ="appVersionSearch" value="${appVersionSearch}"/>
<input type="hidden" name="appVerUpDateSearch" id ="appVerUpDateSearch" value="${appVerUpDateSearch}"/>
<%-- 
<input type="hidden" name="wHCodeSearch" id ="wHCodeSearch" value="${wHCodeSearch}"/>
--%>
<input type="hidden" name="scannerNameSearch" id ="scannerNameSearch" value="${scannerNameSearch}"/>
<input type="hidden" name="lastUpDateSearch" id ="lastUpDateSearch" value="${lastUpDateSearch}"/>
<input type="hidden" name="scannerImeiNSearch" id ="scannerImeiNSearch" value="${scannerImeiNSearch}"/>

</form>
</body>
</html>
