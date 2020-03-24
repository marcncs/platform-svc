<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
function SelectLinkman(){
		var inorganid=document.searchform.InOrganID.value;
		if(inorganid==null||inorganid=="")
		{
			alert("请选择订购机构！");
			return;
		}
	
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+inorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.searchform.OLinkman.value=lk.lname;
			var tel = lk.ltel;
			if(tel==""){
				tel=lk.mobile;
			}
			document.searchform.OTel.value=tel;
		
	}
function SelectOrgan(){
		var p=showModalDialog("../common/selectVisitOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.MakeOrganID.value=p.id;
			document.searchform.oname.value=p.organname;
	}
function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.InOrganID.value=p.id;
			document.searchform.InOrganIDName.value=p.organname;
	}
	function SelectOrgan3(){
		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.searchform.SupplyOrganID.value=p.id;
			document.searchform.SupplyOrganIDName.value=p.organname;
	}
function excput(){
	searchform.target="";
	searchform.action="../report/excPutSupplySaleMoveOrganTotalAction.do";
	searchform.submit();
}
function oncheck(){
	searchform.target="";
	searchform.action="../report/supplySaleMoveOrganTotalAction.do";
	searchform.submit();
}
function print(){
	searchform.target="_blank";
	searchform.action="../report/printSupplySaleMoveOrganTotalAction.do";
	searchform.submit();
	
}


this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}
</script>

</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <div id="div1">
    <table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td >报表分析>>渠道>>代销按机构汇总</td>
        </tr>
      </table>
       <form name="searchform" method="post" action="supplySaleMoveOrganTotalAction.do" onSubmit="return oncheck();">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back">
            <td width="10%"  align="right">制单机构：</td>
            <td width="24%">
            <input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
			<input name="oname" type="text" id="oname" value="${oname}" size="30"
				readonly><a href="javascript:SelectOrgan();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="9%" align="right">申请机构：</td>
            <td width="25%">
            <input name="SupplyOrganID" type="hidden" id="SupplyOrganID" value="${SupplyOrganID}">
			<input name="SupplyOrganIDName" type="text" id="SupplyOrganIDName" value="${SupplyOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan3();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle">
			</a>
            </td>
            <td width="8%"  align="right">调入机构：</td>
            <td width="21%"><input name="InOrganID" type="hidden" id="InOrganID" value="${InOrganID}">
              <input name="InOrganIDName" type="text" id="InOrganIDName" value="${InOrganIDName}" size="30" 
				readonly><a href="javascript:SelectOrgan2();"><img
					src="../images/CN/find.gif" width="18" height="18"
					border="0" align="absmiddle"> </a></td>
          
          </tr>
          <tr class="table-back">
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" id="BeginDate" onFocus="selectDate(this);" 
            value="${BeginDate}" size="10" readonly="readonly">
-
  <input name="EndDate" type="text" id="EndDate" onFocus="selectDate(this);" 
  			value="${EndDate}" size="10" readonly="readonly"></td>
            <td width="9%" align="right">&nbsp;</td>
            <td width="25%">&nbsp;</td>
            <td align="right">&nbsp;</td>
          
            <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
       <table width="100%"  border="0" cellpadding="0" cellspacing="0" >
          <tr class="title-func-back"> 
          <td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		</td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td width="50">
			<a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a>
		</td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
		<td class="SeparatePage"><pages:pager action="../report/supplySaleMoveOrganTotalAction.do"/></td>
          </tr>
      </table>
      </div>
      <div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top-lock">
          <td width="11%">制单机构编号</td>
          <td width="13%">制单机构名称</td>
          <td width="9%">申请机构编号</td>
          <td width="13%">申请机构名称</td>
          <td width="9%">调入机构编号</td>
          <td width="15%">调入机构名称</td>
          <td width="8%">订购金额</td>
          <td width="8%">销售金额</td>
        </tr>
		<c:set var="totalcount" value="0"/>
        <c:set var="totals" value="0"/>
		<c:forEach items="${list}" var="p">
        <tr class="table-back-colorbar">
          <td>${p.makeorganid}</td>
          <td><windrp:getname key='organ' value='${p.makeorganid}' p='d'/></td>
          <td >${p.supplyorganid}</td>
          <td ><windrp:getname key='organ' value='${p.supplyorganid}' p='d'/></td>
          <td >${p.inorganid}</td>
          <td ><windrp:getname key='organ' value='${p.inorganid}' p='d'/></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value='${p.totalsum}' /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value='${p.stotalsum}' /></td>                
        </tr>
        <c:set var="totalcount" value="${totalcount+p.totalsum}"></c:set>
        <c:set var="totals" value="${totals+p.stotalsum}"></c:set>
		</c:forEach> 
		</table>
		<table width="100%" cellspacing="1" class="totalsumTable">
        <tr class="back-gray-light">
          <td align="right" width="12%"> 本页合计：</td>
          <td width="79%" align="right">￥<windrp:format p="###,##0.00" value='${totalcount}' />
         </td>
          <td width="9%" align="right">￥<windrp:format p="###,##0.00" value='${totals}' /></td>
        </tr>
        <c:forEach items="${allsum}" var="s">
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.allp}' /></td>
          <td align="right">￥<windrp:format p="###,##0.00" value='${s.alls}' /></td>
        </tr>
        </c:forEach>
      </table>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
