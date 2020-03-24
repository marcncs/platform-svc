<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
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
	InvoiceDetail();
	}
	
	function addNew(){
		popWin("toAddSaleInvoiceAction.do",1000,650);
	}

	function Update(){
		if(checkid>0){
			popWin("toUpdSaleInvoiceAction.do?ID="+checkid,1000,650);
		}else{
			alert("请选择你要操作的记录!");
		}
	}

	function Del(){
		if(checkid>0){
		if(window.confirm("您确认要删除编号为:"+checkid+"的记录吗？如果删除将永远不能恢复!")){
			popWin2("../sales/delSaleInvoiceAction.do?SIID="+checkid);
			}
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function InvoiceDetail(){
		if(checkid!=""){
		document.all.submsg.src="../sales/saleInvoiceDetailAction.do?ID="+checkid;
		}else{
		alert("请选择你要操作的记录!");
		}
	}
	
	function SelectOrgan(){
	var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
	if ( p==undefined){return;}
	document.search.MakeOrganID.value=p.id;
	document.search.oname.value=p.organname;
	clearUser("MakeID","uname");
}
	
	this.onload =function onLoadDivHeight(){
		document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
	}
	
	function excput(){
		search.target="";
		search.action="../sales/excPutSaleInvoiceAction.do";
		search.submit();
		search.action="listSaleInvoiceAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../sales/printSaleInvoiceAction.do";
		search.submit();
		search.target="";
		search.action="listSaleInvoiceAction.do";
	}

</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">零售管理>>零售发票</td>
  </tr>
</table>
 <form name="search" method="post" action="listSaleInvoiceAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
            <td width="11%"  align="right">发票类型：</td>
            <td width="25%"><windrp:select key="InvoiceType" name="InvoiceType" p="y|f" value="${InvoiceType}"/></td>
            <td width="8%" align="right">是否复核： </td>
            <td width="21%"><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
            <td width="9%" align="right">制单机构：</td>
            <td width="23%"><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"> </a></td>
            <td width="3%">&nbsp;</td>
          </tr>
          <tr class="table-back">
            <td  align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
              <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeOrganID'),'ou')"
										value="${uname}" readonly></td>
            <td align="right">制单日期：</td>
            <td><input name="BeginDate" type="text" onFocus="javascript:selectDate(this)" size="10" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" onFocus="javascript:selectDate(this)" size="10" value="${EndDate}" readonly></td>
            <td align="right">关键字：</td>
            <td><input type="text" name="KeyWord" value="${KeWord}" ></td>
            <td><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
            </span></td>
          </tr>
        
      </table>
</form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:addNew();"><img
											src="../images/CN/addnew.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;新增</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Update();"><img
											src="../images/CN/update.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;修改</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
          <td width="50"><a href="javascript:Del()"><img
											src="../images/CN/delete.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;删除</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>
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
          <td class="SeparatePage"><pages:pager action="../sales/listSaleInvoiceAction.do" />
          </td>
        </tr>
      </table>
	  </div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
					 <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
       
          <tr align="center" class="title-top"> 
            <td width="9%"  >编号</td>
            <td width="8%" >发票编号</td>
            <td width="20%" >会员名称</td>
            <td width="11%" >发票类型</td>
            <td width="17%" >制票日期</td>
            <td width="15%" >开票日期</td>
            <td width="8%" >总金额</td>
            <td width="12%" >是否复核</td>
          </tr>
          <logic:iterate id="s" name="alsi" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,${s.id});"> 
            <td >${s.id}</td>
            <td>${s.invoicecode}</td>
            <td>${s.cname}</td>
            <td><windrp:getname key='InvoiceType' value='${s.invoicetype}' p='f'/></td>
            <td><windrp:dateformat value='${s.makeinvoicedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:dateformat value='${s.invoicedate}' p='yyyy-MM-dd'/></td>
            <td align="right"><windrp:format value="${s.invoicesum}" p="###,##0.0"/></td>
            <td><windrp:getname key='YesOrNo' value='${s.isaudit}' p='f'/></td>
          </tr>
          </logic:iterate>
       
      </table>
       </form>
	  <br>
	   <div style="width:100%">
      	<div id="tabs1">
            <ul>
              <li><a href="javascript:InvoiceDetail();"><span>发票详情</span></a></li>
            </ul>
          </div>
          <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" 

frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>
      
					</div>
</td>
  </tr>
</table>

</body>
</html>
