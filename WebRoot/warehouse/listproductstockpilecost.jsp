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
<SCRIPT language="javascript" src="../js/producttype.js"> </SCRIPT>
<script language="JavaScript">
this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight)+"px";
}

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
	  pdmenu = getCookie("stockpile");
	 switch(pdmenu){
	 	case "1":WarehouseBit(); break;
	 	case "2":Detail(); break;
		default:WarehouseBit();
	 }
	}
	
	function WarehouseBit(){
		setCookie("stockpile","1");
		if(checkid!=""){
			document.all.phonebook.src="stockStatAction.do?WarehouseID="+v_wid+"&ProductID="+v_productid+"&Batch="+v_batch;
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Detail(){
		setCookie("stockpile","2");
		if(checkid!=""){
			document.all.phonebook.src="listProductStockpileIdcodeAction.do?WarehouseID="+v_wid+"&ProductID="+v_productid+"&Batch="+v_batch;
			//document.all.phonebook.src="listProductStockpileIdcodeAction.do";
		}else{
			alert("请选择你要操作的记录!");
		}
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
		search.action="../warehouse/excPutProductStockpileCostAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductStockpileCostAction.do";
	}
	function print(){
		search.target="_blank";
		search.action="../warehouse/printProductStockpileCostAction.do";
		search.submit();
		search.target="";
		search.action="../warehouse/listProductStockpileCostAction.do";
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
          <td width="772"> 仓库管理&gt;&gt;库存成本</td>
        </tr>
      </table>
      <form name="search" method="post" action="listProductStockpileCostAction.do">
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back"> 
            <td width="8%"  align="right">仓库：</td>
            <td width="19%"><select name="WarehouseID" id="WarehouseID">
				<option value="">所有仓库</option>
                <logic:iterate id="w" name="alw" > 
				<option value="${w.id}" ${w.id==WarehouseID?"selected":""}>${w.warehousename}</option>
            </logic:iterate> </select> </td>
            <td width="13%" align="right">产品类别：</td>
            <td width="19%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">	<input type="text" name="psname" id="psname" value="${psname}" onFocus="javascript:selectptype(this, 'KeyWordLeft')" readonly>			
			</td>
			<td width="11%"  align="right">产品：</td>
            <td width="26%"><input name="ProductID" type="hidden" id="ProductID" value="${ProductID}">
              <input id="ProductName" name="ProductName" value="${ProductName}" readonly><a href="javascript:SelectSingleProduct();"><img src="../images/CN/find.gif" align="absmiddle"  border="0"></a></td>
			<td width="4%"></td>
          </tr>
          <tr class="table-back">
            <td width="8%" align="right">批次：</td>
            <td width="19%"><input name="Batch" type="text" id="Batch" value="${Batch}" maxlength="32"></td>
		  <td width="13%"  align="right">关键字：</td>
            <td width="19%"><input name="KeyWord" type="text" id="KeyWord" value="${KeyWord}" maxlength="60"></td>
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
		<td width="50"><a href="javascript:print();"><img src="../images/CN/print.gif" width="16" height="16"border="0" align="absmiddle">&nbsp;打印</a></td>
			<td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>	
		  <td class="SeparatePage"><pages:pager action="../warehouse/listProductStockpileCostAction.do"/></td>							
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
       
          <tr align="center" class="title-top-lock"> 
			<td width="10%">仓库名</td>
			<td width="10%">批次</td>
			<td width="10%">产品编号</td>
            <td width="15%" >产品名称</td>
            <td width="15%">规格</td>   
            <td width="5%">单位</td>  
            <td width="11%">实际库存</td>     
            <td width="12%">成本单价</td>             
            <td width="12%">成本合计</td>
                  
          </tr>
          <logic:iterate id="p" name="alp" > 
          <tr class="table-back-colorbar"> 
			<td >${p.warehourseidname}</td>
			<td>${p.batch}</td>
			<td>${p.productid}</td>
            <td >${p.psproductname}</td>
            <td>${p.psspecmode}</td>    
            <td><windrp:getname key='CountUnit' value='${p.countunit}' p='d'/></td>
            <td><windrp:format value='${p.allstockpile}'/></td>        
            <td align="right"><windrp:format value='${p.cost}'/></td>    
            <td align="right"><windrp:format value='${p.totalcost}'/></td>
			        
          </tr>
          </logic:iterate> 
        
      </table>
      </form>
      <table width="100%" cellspacing="1"  class="totalsumTable">
        <tr class="back-gray-light">
          <td width="10%"  align="right">本页合计：</td>
          <td align="right" width="66%"><windrp:format value="${totalstockpile}" /></td>
          <td align="right" width="12%">￥<windrp:format p="###,##0.00" value="${totalcost}" /></td>
          <td align="right" width="12%">￥<windrp:format p="###,##0.00" value="${totalsum}" /></td>
        </tr>
		<tr class="back-gray-light">
          <td  align="right">总合计：</td>
          <td align="right" ><windrp:format value="${allstockpile}" /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value="${allcost}" /></td>
          <td align="right" >￥<windrp:format p="###,##0.00" value="${allsum}" /></td>
        </tr>
      </table>
	  <br>
     
      </div>	
    </td>
  </tr>
</table>

<form  name="excputform" method="post" action="printProductStockpileCostAction.do">
<input type="hidden" name="KeyWordLeft" id ="KeyWordLeft" value="${KeyWordLeft}">
<input type="hidden" name="KeyWord" id ="KeyWord" value="${KeyWord}">
<input type="hidden" name="WarehouseID" id ="WarehouseID" value="${WarehouseID}">
<input type="hidden" name="Batch" id ="Batch" value="${Batch}">
<input type="hidden" name="ProductID" id ="ProductID" value="${ProductID}">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName}">
</form>
</body>
</html>
