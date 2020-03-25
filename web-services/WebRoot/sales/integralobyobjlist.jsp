<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/showbill.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}
	
	function addNew(){
	window.open("../finance/toAddReceivableAction.do","","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("toUpdReceivableAction.do?ID="+checkid,"","height=550,width=1100,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	

	
	function Del(){
		if(checkid!=""){
			window.open("../finance/delReceivableAction.do?RID="+checkid,"newwindow","height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=yes,location=no,status=no");
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
	function excput(){
		search.target="";
		search.action="../sales/excPutIntegralOByObjAction.do";
		search.submit();
		search.action="listIntegralOByObjAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../sales/printIntegralOByObjAction.do";
		search.submit();
		search.target="";
		search.action="listIntegralOByObjAction.do";
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
     <div id="bodydiv">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td>支出列表</td>
        </tr>
      </table>
      <form name="search" method="post" action="listIntegralOByObjAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="15%"  align="right"><input name="OID" type="hidden" id="OID" value="${OID}">
              <input name="OSort" type="hidden" id="OSort" value="${OSort}">
            制单日期：</td>
            <td width="28%">
              <input name="BeginDate" type="text" id="BeginDate" value="${BeginDate }" size="10" onFocus="javascript:selectDate(this)">
              - 
            <input name="EndDate" type="text" id="EndDate" value="${EndDate }" size="10" onFocus="javascript:selectDate(this)"></td>
            <td width="9%" align="right">单据号：</td>
            <td width="26%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord }"></td>
            <td width="7%" align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit2" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
        <td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
				width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
			<td width="51">
				<a href="javascript:print();"><img
						src="../images/CN/print.gif" width="16" height="16"
						border="0" align="absmiddle">&nbsp;打印</a>
			</td>
			<td width="1">
				<img src="../images/CN/hline.gif" width="2" height="14">
			</td>
          <td class="SeparatePage"><pages:pager action="../sales/listIntegralOByObjAction.do"/></td>
        </tr>
      </table>
      </div>
		<div id="listdiv" style="overflow-y: auto; height: 650px;" >
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
		    <td width="10%">编号</td>
            <td width="15%">单据号</td>
            <td width="13%">积分类别</td>
            <td width="14%">应付</td>
            <td width="14%">已付</td>
            <td width="18%">制单日期</td>
            <td width="16%">配送机构</td>
          </tr>
		  <c:set var="count" value="0"/>
          <logic:iterate id="s" name="iils" > 
          <tr class="table-back-colorbar"> 
            <td>${s.id}</td>
            <td><a href="javascript:ToBill('${s.billno}');">${s.billno}</a></td>
            <td><windrp:getname key='ISort' value='${s.isort}' p='d'/></td>
            <td align="right"><windrp:format value="${s.rout}"/></td>
            <td align="right"><windrp:format value="${s.aout}"/></td>
            <td><windrp:dateformat value='${s.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${s.equiporganid}' p='d'/></td>
            </tr>
		  <c:set var="count" value="${count+1}"/>
          </logic:iterate> 
      </table>
      </div>
	  </td>
  </tr>
</table>
</body>
</html>
