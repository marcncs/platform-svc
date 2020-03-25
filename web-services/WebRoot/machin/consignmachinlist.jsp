<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script type="text/javascript" src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language="JavaScript">
var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
		Detail();
	}

	function addNew(){
	popWin("../machin/toAddConsignMachinAction.do",1000,600);
	}

	function Update(){
		if(checkid!=""){
		popWin("toUpdConsignMachinAction.do?ID="+checkid,1000,600);
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		if(checkid!=""){
			document.all.submsg.src="consignMachinDetailAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function excput(){
		search.target="";
		search.action="../machin/excPutConsignMachinAction.do";
		search.submit();
	}
	function oncheck(){
		search.target="";
		search.action="../machin/listConsignMachinAction.do";
		search.submit();
	}
	function print(){
		search.target="_blank";
		search.action="../machin/printListConsignMachinAction.do";
		search.submit();
	}
	

	function Del(){
		if(checkid!=""){
		if(window.confirm("您确认要删除编号为："+checkid+" 的委外加工单吗？如果删除将永远不能恢复!")){
			popWin2("../machin/delConsignMachinAction.do?ID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	
	function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.search.PID.value=p.pid;
	document.search.ProvideName.value=p.pname;
	}
	
	//function SelectProvide(){
	//showModalDialog("toSelectProvideAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	//document.search.PID.value=getCookie("pid");
	//document.search.ProvideName.value=getCookie("pname");
	//}
	
	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	//showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	document.search.CProductID.value=p.id;
	document.search.CProductName.value=p.productname;
	}
	
	this.onload = function abc(){
		document.getElementById("abc").style.height = (document.body.clientHeight  - document.getElementById("div1").offsetHeight)+"px" ;
	}	

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="div1">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">生产组装>>委外加工</td>
  </tr>
</table>
<form name="search" method="post" action="../machin/listConsignMachinAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="11%"  align="right">供应商：</td>
            <td width="27%"><input name="PID" type="hidden" id="PID" value="${PID}">
              <input name="ProvideName" type="text" id="ProvideName" size="35" value="${ProvideName}" readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="9%" align="right">加工产品：</td>
            <td width="25%"><input name="CProductID" type="hidden" id="CProductID" value="${CProductID}">
              <input name="CProductName" type="text" id="CProductName" value="${CProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td width="8%" align="right">是否复核：</td>
            <td width="16%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}"/></td>
            <td width="4%">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <!--<td  align="right">是否结案：</td>
            <td><windrp:select key="YesOrNo" name="IsEndcase" p="y|f"/></td>-->
            <td align="right">预计完工日期：</td>
            <td><input type="text" name="BeginDate" size="12" value="${BeginDate}" readonly onFocus="javascript:selectDate(this)">
-
  <input type="text" name="EndDate" size="12" value="${EndDate}" readonly onFocus="javascript:selectDate(this)"></td>
            <td align="right">&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td class="SeparatePage"><input name="Submit" type="image" border="0" src="../images/CN/search.gif"></td>
          </tr>
       
      </table>
 </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Del();"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
           <td width="50">
		<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
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
          <td class="SeparatePage"><pages:pager action="../machin/listConsignMachinAction.do" />          </td>
        </tr>
      </table>
	  </div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="abc" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="12%" >序号</td>
            <td width="10%" >供应商</td>
            <td width="12%" >加工产品编码</td>
            <td width="15%" >加工产品名称</td>
            <td width="13%" >规格</td>
            <td width="8%" >单位</td>
            <td width="8%" >数量</td>
            <td width="10%" >预计完工日期</td>
            <td width="7%" >是否复核</td>
           <!-- <td width="8%" >是否结案</td>-->
          </tr>
	<logic:iterate id="s" name="alpi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${s.id}');"> 
            <td >${s.id}</td>
            <td >${s.pidname}</td>
            <td >${s.cproductid}</td>
            <td >${s.cproductname}</td>
            <td >${s.cspecmode}</td>
            <td >${s.cunitidname}</td>
            <td >${s.cquantity}</td>
            <td >${s.completeintenddate}</td>
            <td ><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
            <!--<td ><windrp:getname key='YesOrNo' value='${s.isendcase}' p='f'/></td>-->
          </tr>
		  </logic:iterate>
      </table>
	  <br>
	  <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:Detail();"><span>委外加工详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
		</div>
</td>
  </tr>
</table>
</body>
</html>
