<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script language="JavaScript">

	var checkid=0;
	function CheckedObj(obj,objid){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++){
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;

	}

	function Adjust(){
		if(checkid>0){
		window.open("toAdjustStockAction.do?PID="+checkid,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
		
	}
	
	function ClearPrepare(){
	if(window.confirm("您确认清除吗？该动作执行后不能还原!")){
			window.open("../warehouse/coerceClearPrepareAction.do","newwindow",		"height=300,width=550,top=250,left=250,toolbar=no,menubar=no,scrollbars=auto,resizable=no,location=no,status=no");
			}
	}
	
	function UpdPrepareout(){
		if(checkid>0){
		window.open("toUpdProductStockpileAction.do?ID="+checkid,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function viewproduct(id){
		window.open("viewProductAction.do?ID="+id,"newwindow","height=250,width=700,top=250,left=250,toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");		
	}
	
	
		function excput(){
		search.target="";
		search.action="../warehouse/excPutProductStockpileIdcodeAction.do";
		search.submit();
		
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printProductStockpileIdcodeAction.do";
		search.submit();
	}
	
	function oncheck(){
		search.target="";
		search.action="../warehouse/listProductStockpileIdcodeAction.do";
		search.submit();
		
	}
	
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">库存条码</td>
        </tr>
      </table>
       <form name="search" method="post" action="listProductStockpileIdcodeAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
       
			<input type="hidden" name="WarehouseID" value="${WarehouseID}">
			<input type="hidden" name="ProductID" value="${ProductID}">
			<input type="hidden" name="Batch" value="${Batch}">
          <tr class="table-back">           
		  <td width="9%"  align="right">仓位：</td>
            <td width="23%"><input type="hidden" name="WarehouseBit" id="WarehouseBit" value="${WarehouseBit}">
              <input type="text" name="bitname" id="bitname" onClick="selectDUW(this,'WarehouseBit','${WarehouseID}','b')" value="${bitname}" readonly></td>
            <td width="18%" align="right">关键字：</td>
            <td width="18%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
			<td width="32%" class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">
		<td width="50"><a href="javascript:excput();"><img src="../images/CN/outputExcel.gif" 
		width="16" height="16" border="0" align="absmiddle">&nbsp;导出</a> </td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		<td width="51"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16"
		border="0" align="absmiddle">&nbsp;打印</a></td>
		<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>			
		  <td class="SeparatePage"><pages:pager action="../warehouse/listProductStockpileIdcodeAction.do"/></td>							
		</tr>
	</table>	
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
			<td width="6%">仓位</td>
            <td width="18%">条码</td>
            <td width="13%" >外箱条码</td>
			<td width="9%">批号</td>
			<td width="10%">生产日期</td>
			
            <td width="10%">包装数量</td>  
			<td width="3%">单位</td>  
			<td width="10%">数量</td>            
          </tr>
          <logic:iterate id="p" name="list" > 
          <tr align="center" class="table-back-colorbar" > 
			<td >${p.warehousebit}</td>
            <td >${p.idcode}</td>
            <td >${p.cartonCode}</td>
			<td >${p.batch}</td>
			<td><windrp:dateformat value="${p.producedate}" p="yyyy-MM-dd" /></td>
            <td><windrp:format value="${p.packquantity}"/></td>   
			<td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
			<td><windrp:format value="${p.quantity}"/></td>         
          </tr>
          </logic:iterate> 
        
      </table>
</form>
    </td>
  </tr>
</table>
</body>
</html>
