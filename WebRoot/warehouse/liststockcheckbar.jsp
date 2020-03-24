<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<script language="JavaScript">
	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 var pdmenu = getCookie("CheckBar");
	 switch(pdmenu){
	 	case "0":StockCheckDetail(); break;
	 	case "1":StockIdcodeDetail(); break;		
		default:StockCheckDetail();
	 }
	}
	
	function outPut(){
		popWin("../warehouse/toOutPutCheckEmptyAction.do",600,400);
	}
	
	function excput(){
		search.target="";
		search.action="../warehouse/excPutStockCheckAction.do?IsBar=1";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockCheckBarAction.do?IsBar=1";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printListStockCheckAction.do?IsBar=1";
		search.submit();
		search.target="";
		search.action="../warehouse/listStockCheckBarAction.do?IsBar=1";
	}
	
	function AddNew(){
		window.open("../warehouse/toAddStockCheckAction.do?isbar=${IsBar}","","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
	}

	function Update(){
		if(checkid!=""){
		window.open("../warehouse/toUpdStockCheckBarAction.do?ID="+checkid,"","height=650,width=1000,top=50,left=20,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockCheckDetail(){
		setCookie("CheckBar","0");
		if(checkid!=""){
			document.all.submsg.src="../warehouse/listStockCheckDetailAction.do?SCID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function StockIdcodeDetail(){
		setCookie("CheckBar","1");
		if(checkid!=""){
			document.all.submsg.src="../warehouse/listStockCheckIdcodeAction.do?SCID="+checkid;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Del(){
		if(checkid!=""){
			if ( confirm("你确认要删除编号为:"+checkid+"的记录吗?如果删除将不能恢复!") ){
				window.open("../warehouse/delStockCheckAction.do?ID="+checkid,"newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	
	function UploadIdcode(){
		window.open("../common/toUploadIdcodeAction.do?billsort=2","newwindow","height=250,width=500,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function DownTxt(){
	excputform.action="txtPurStockCheckAction.do";
	excputform.submit();
	}
	
	function ToImport(){
		window.open("../warehouse/toSelectImportCheckTableAction.do","newwindow",		"height=300,width=800,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
	}
	
	function SelectOrgan(){
var p=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
if ( p==undefined){return;}
document.search.MakeOrganID.value=p.id;
document.search.oname.value=p.organname;
clearDeptAndUser("MakeDeptID","deptname","MakeID","uname");
document.search.WarehouseID.value="";
document.search.wname.value="";
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
          <td width="772"> 仓库管理&gt;&gt;库存盘点&gt;&gt;条码产品盘点</td>
        </tr>
      </table>
      <form name="search" method="post" action="listStockCheckBarAction.do?IsBar=${IsBar}">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
	    
		<tr class="table-back"> 
            <td  align="right">制单机构：</td>
            <td><input name="MakeOrganID" type="hidden" id="MakeOrganID" value="${MakeOrganID}">
              <input name="oname" type="text" id="oname" size="30" value="${oname}" 
								readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>			</td>
            <td align="right">制单部门：</td>
								<td>
								<input type="hidden" name="MakeDeptID" id="MakeDeptID" value="${MakeDeptID}">
								<input type="text" name="deptname" id="deptname" value="${deptname}" 
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d','','MakeID','uname')" 
								 readonly>
								</td>
            <td align="right">制单人：</td>
            <td><input type="hidden" name="MakeID" id="MakeID" value="${MakeID}">
<input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="${uname}" readonly></td>
		<td></td>
          </tr>
		  <tr class="table-back">
          <td width="14%"  align="right">盘点仓库：</td>
          <td width="23%" ><input type="hidden" name="WarehouseID" id="WarehouseID" value="${WarehouseID}">
              <input type="text" name="wname" id="wname" 
				onClick="selectDUW(this,'WarehouseID',$F('MakeOrganID'),'w')" 
				value="${wname}" readonly></td>
          <td width="8%"  align="right">是否复核：</td>
          <td width="23%" ><windrp:select key="YesOrNo" name="IsAudit" p="y|f" value="${IsAudit}" /></td>
          <td width="15%" align="right">是否生成入库出库：</td>
          <td width="17%"><windrp:select key="YesOrNo" name="IsCreate" value="${IsCreate}" p="y|f"/></td>
          <td></td>
        </tr>
		<tr class="table-back">
          <td width="14%"  align="right">盘点日期：</td>
          <td width="23%" ><input name="BeginDate" type="text" id="BeginDate" size="12" onFocus="javascript:selectDate(this)" value="${BeginDate}" readonly>
-
  <input name="EndDate" type="text" id="EndDate" size="12" value="${EndDate}" onFocus="javascript:selectDate(this)" readonly></td>
          <td width="8%"  align="right">关键字：</td>
          <td width="23%" ><input type="text" name="KeyWord" value="${KeyWord}"></td>
          <td width="15%" align="right">&nbsp;</td>
          <td width="17%">&nbsp;</td>
          <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
        </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">			
			<td width="50" >
			<a href="javascript:AddNew();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;新增</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:Update();"><img src="../images/CN/update.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;修改</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:Del();"><img src="../images/CN/delete.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;删除</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
            <td width="50" >
				<a href="javascript:DownTxt();"><img src="../images/CN/downpda.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;下载</a></td>	
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
			<td width="50" >
			<a href="javascript:UploadIdcode();"><img src="../images/CN/upload.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;上传</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
						 <td width="50">
			<a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a>
		    </td>
		    <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>		
          <td width="50"><a href="javascript:print();"><img
											src="../images/CN/print.gif" width="16" height="16"
											border="0" align="absmiddle">&nbsp;打印</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"> </td>		           						
		  <td class="SeparatePage"><pages:pager action="../warehouse/listStockCheckBarAction.do"/></td>
							
		</tr>
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
            <td width="12%" >编号</td>
            <td width="13%" >盘点仓库</td>
            <td width="10%" >仓位</td>
            <td width="8%" >盘点日期</td>            
            <td width="21%" >制单机构</td>
            <td width="13%" >制单人</td>
            <td width="8%" >是否复核</td>
			<td width="12%" >是否生成入库出库</td>
            
          </tr>
          <logic:iterate id="sa" name="als" > 
          <tr class="table-back-colorbar" onClick="CheckedObj(this,'${sa.id}');"> 
            <td>${sa.id}</td>
            <td><windrp:getname key='warehouse' value='${sa.warehouseid}' p='d'/></td>
            <td>${sa.warehousebit}</td>
            <td><windrp:dateformat value='${sa.makedate}' p='yyyy-MM-dd'/></td>
            <td><windrp:getname key='organ' value='${sa.makeorganid}' p='d'/></td>
            <td><windrp:getname key='users' value='${sa.makeid}' p='d'/></td>
            <td><windrp:getname key='YesOrNo' value='${sa.isaudit}' p='f'/></td>
			<td><windrp:getname key='YesOrNo' value='${sa.iscreate}' p='f'/></td>
            
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <br>
      <div style="width:100%">
        <div id="tabs1">
          <ul>
            <li><a href="javascript:StockCheckDetail();"><span>数量表详情</span></a></li>
            <li><a href="javascript:StockIdcodeDetail();"><span>条码表详情</span></a></li>
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
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="IsCreate" id ="IsCreate" value="${IsCreate}">
<input type="hidden" name="IsAudit" id ="IsAudit" value="${IsAudit}">
<input type="hidden" name="BeginDate" id ="BeginDate" value="${BeginDate}">
<input type="hidden" name="EndDate" id ="EndDate" value="${EndDate}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
</form>
</body>
</html>
