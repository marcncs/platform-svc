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
	var v_wid="";
	var v_bit="";
	var v_productid="";
	var v_batch="";
	function CheckedObj(obj,objid,objwid,objbit,objproductid,objbatch){
	
	 for(i=0; i<obj.parentNode.childNodes.length; i++){
		   obj.parentNode.childNodes[i].className="table-back-colorbar";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 v_wid=objwid;
	 v_bit=objbit;
	 v_productid=objproductid;
	 v_batch=objbatch;
	 Detail();
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
	
	function SelectSingleProduct(){
	var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	document.search.ProductID.value=p.id;
	document.search.ProductName.value=p.productname;
	}
	function excput(){
		search.target="";
		var Batch="${Batch}";
		search.action="../warehouse/excPutStockStatAction.do?Batch="+Batch;
		search.submit();
		
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printStockStatAction.do";
		search.submit();
	}
	
	function oncheck(){
		search.target="";
		search.action="../warehouse/stockStatAction.do";
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
          <td width="772"> 仓位统计</td>
        </tr>
      </table>
      <form name="search" method="post" action="stockStatAction.do" onSubmit="return oncheck();">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
        <input type="hidden" name="WarehouseID" value="${WarehouseID}">
        <input type="hidden" name="ProductID" value="${ProductID}">
        <input type="hidden" name="Batch" value="${Batch}">
          <tr class="table-back"> 
            <td width="8%"  align="right">仓位：</td>
            <td width="19%"><input type="hidden" name="WarehouseBit" value="${WarehouseBit}" id="WarehouseBit">
              <input type="text" name="bitname" id="bitname" onClick="selectDUW(this,'WarehouseBit','${WarehouseID}','b')" value="${bitname}" readonly></td>
            <td width="13%" align="right">产品类别：</td>
            <td width="19%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/></td>
			<td width="11%"  align="right">产品：</td>
            <td width="26%"><input name="ProductID2" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
			<td width="4%"></td>
          </tr>
          <tr class="table-back">
            <td width="8%" align="right">关键字：</td>
            <td width="19%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
		  <td width="13%"  align="right">&nbsp;</td>
            <td width="19%">&nbsp;</td>
            <td align="right">&nbsp;</td>
            <td>&nbsp;</td>
			<td class="SeparatePage"><input name="Submit" type="image" id="Submit" 
					src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
        
      </table>
      </form>
	  <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr class="title-func-back">	
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
		  <td class="SeparatePage"><pages:pager action="../warehouse/stockStatAction.do"/></td>							
		</tr>
	</table>	
	<FORM METHOD="POST" name="listform" ACTION="">
      <table class="sortable" width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
			<td width="15%">仓位</td>
			<td width="8%">批号</td>
			<td width="10%">产品编号</td>
			<td width="15%">物料号</td>
			<td width="15%" >产品名称</td>
            <td width="13%">规格</td>  
            <td >单位</td>          
            <td width="10%">当前库存量</td>           
          </tr>
          <logic:iterate id="p" name="alp" > 
          <tr class="table-back-colorbar" > 
			<td >${p.warehousebit}</td>
			<td>${p.batch}</td>
			<td>${p.productid}</td>
			<td>${p.nccode}</td>
            <td >${p.psproductname}</td>
            <td>${p.psspecmode}</td>     
            <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>    
            <td><windrp:format value="${p.strstockpile}"/></td>           
          </tr>
          </logic:iterate> 
       
      </table> 
 </form>
    </td>
  </tr>
</table>
</body>
</html>
