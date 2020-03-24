<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
StockMoveDetail();
	}
	
	function StockMoveDetail(){
		if(checkid!=""){
			document.all.submsg.src="productInterconvertDetailReceiveAction.do?ID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function UploadIdcode(){
		popWin("../common/toUploadIdcodeAction.do?billsort=7", 500, 250);
	}
	
	function DownTxt(){
		excputform.action="txtPurProductInterconvertAction.do";
		excputform.submit();
	}
	function excput(){
		search.target="";
		search.action="../warehouse/excPutProductInterconvertReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductInterconvertReceiveAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListProductInterconvertReceiveAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductInterconvertReceiveAction.do";
	}
	function SelectOrgan(){

		var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");

		if ( p==undefined){return;}
			document.search.MakeOrganID.value=p.id;
			document.search.oname.value=p.organname;
			clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");

	}

this.onload =function onLoadDivHeight(){
				document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
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
          <td width="772"> 仓库管理&gt;&gt;入库&gt;&gt;产品互转签收</td>
        </tr>
      </table>
      <form name="search" method="post" action="listProductInterconvertReceiveAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    
        <tr class="table-back">
          <td width="9%"  align="right">调出仓库：</td>
          <td width="26%"><windrp:warehouse name="OutWarehouseID" value="${OutWarehouseID}" p="y"/></td>
          <td width="9%" align="right">调入仓库：</td>
          <td width="22%"><windrp:warehouse name="InWarehouseID" value="${InWarehouseID}" p="y"/></td>
          <td width="9%" align="right">是否签收：</td>
          <td width="21%"><windrp:select key="YesOrNo" name="IsComplete" p="y|f" value="${IsComplete}" /></td>
          <td width="4%">&nbsp;</td>
        </tr>
		 <tr class="table-back"> 
            <td  align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
            <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
            <td align="right">制单部门：</td>
								<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>
								</td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
            <input type="text" name="uname" id="uname"
										onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')"
										value="${uname}" readonly></td>
            <td>&nbsp;</td>
          </tr>
        <tr class="table-back">
          <td  align="right">转换日期：</td>
          <td><input name="BeginDate" type="text" id="BeginDate" onFocus="javascript:selectDate(this)" size="10" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" id="EndDate" onFocus="javascript:selectDate(this)" value="${EndDate}" size="10" readonly></td>
          <td align="right">关键字：</td>
          <td><input type="text" name="KeyWord" value="${KeyWord}"></td>
          <td align="right"></td>
          <td></td>
          <td align="right"><span class="SeparatePage">
            <input name="Submit2" type="image" id="Submit" 
					src="../images/CN/search.gif"  title="查询">
          </span></td>
        </tr>
       
      </table>
       </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a></td>
		  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		  <td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;打印</a></td>
		  <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
          <td style="text-align: right;"><pages:pager action="../warehouse/listProductInterconvertReceiveAction.do" /></td>
        </tr>
      </table>
    </div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
          <tr align="center" class="title-top"> 
            <td width="15%">编号</td>
            <td width="10%">转换日期</td>
            <td width="15%">调出仓库</td>
            <td width="19%">调入仓库</td>            
            <td width="16%">制单机构</td>
			<td width="13%">制单人</td>
            <td width="12%">是否签收</td>
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');"> 
            <td>${sa.id}</td>
            <td><windrp:dateformat value='${sa.movedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='warehouse' value='${sa.outwarehouseid}' p='d'/></td>
            <td><windrp:getname key='warehouse' value='${sa.inwarehouseid}' p='d'/></td>            
            <td><windrp:getname key='organ' value='${sa.makeorganid}' p='d'/></td>
			<td><windrp:getname key='users' value='${sa.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${sa.iscomplete}' p='f'/></td>
          </tr>
          </logic:iterate> 
      </table>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:StockMoveDetail();"><span>产品互转详情</span></a></li>
          </ul>
        </div>
        <div><IFRAME id="submsg" style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH: 100%; HEIGHT: 100%" name="submsg" src="../sys/remind.htm" frameBorder="0" scrolling="no" onload="setIframeHeight(this);"></IFRAME></div>
      </div>	
					</div>
      </td>
  </tr>
</table>
<form  name="excputform" method="post" action="">
<input type="hidden" name="MakeOrganID" id ="MakeOrganID" value="${MakeOrganID}">
<input type="hidden" name="MakeDeptID" id ="MakeDeptID" value="${MakeDeptID}">
<input type="hidden" name="MakeID" id ="MakeID" value="${MakeID}">
<input type="hidden" name="OutWarehouseID" id ="OutWarehouseID" value="${OutWarehouseID}">
<input type="hidden" name="InWarehouseID" id ="InWarehouseID" value="${InWarehouseID}">
<input type="hidden" name="IsComplete" id ="IsComplete" value="${IsComplete}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
