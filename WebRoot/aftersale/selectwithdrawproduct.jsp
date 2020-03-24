<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	var arrid=new Array();
	var arrpordocutname=new Array();
	var arrspecmode = new Array();
	var unitid=new Array();
	var arrcountunit=new Array();
	var arrunitprice=new Array();
	var discount=new Array();
	var taxrate=new Array();
	var arrtaxprice=new Array();
	var arrquantity=new Array();
	var arrsubsum=new Array();
	function Affirm(){
		
		
		var flag=false;
		var k=0;
		if(document.listform.pid.length>1){
		for(var i=0;i<document.listform.pid.length;i++){
			if(document.listform.pid[i].checked){
				arrid[k]=document.listform.pid[i].value;//k保证只有选中的才放到数组里
				arrpordocutname[k]=document.listform.productname[i].value;
				arrspecmode[k]=document.listform.specmode[i].value;
				unitid[k]=document.listform.unitid[i].value;
				arrcountunit[k]=document.listform.countunit[i].value;
				arrunitprice[k]=document.listform.unitprice[i].value;
				discount[k]=document.listform.discount[i].value;
				taxrate[k]=document.listform.taxrate[i].value;
				arrtaxprice[k]=document.listform.taxprice[i].value;
				arrquantity[k]=document.listform.quantity[i].value;
				arrsubsum[k]=document.listform.subsum[i].value;
				k++;
				flag=true;//只要选中一个就设为true
			}
		}
		}else{
				arrid[0]=document.listform.pid.value;//k保证只有选中的才放到数组里
				arrpordocutname[0]=document.listform.productname.value;
				arrspecmode[0]=document.listform.specmode.value;
				unitid[0]=document.listform.unitid.value;
				arrcountunit[0]=document.listform.countunit.value;
				arrunitprice[0]=document.listform.unitprice.value;
				discount[0]=document.listform.discount.value;
				taxrate[0]=document.listform.taxrate.value;
				arrtaxprice[0]=document.listform.taxprice.value;
				arrquantity[0]=document.listform.quantity.value;
				arrsubsum[0]=document.listform.subsum.value;
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){
	/*		setCookie("productid",arrid);
			setCookie("productname",arrpordocutname);
			setCookie("specmode",arrspecmode);
			setCookie("unitid",unitid);
			setCookie("countunit",arrcountunit);
			setCookie("unitprice",arrunitprice);
			setCookie("discount",discount);
			setCookie("taxrate",taxrate);
			setCookie("batch",arrbatch);
			setCookie("quantity",arrquantity);
			setCookie("subsum",arrsubsum);
			*/

	var product={productid:arrid.slice(0),productname:arrpordocutname.slice(0),specmode:arrspecmode.slice(0),unitid:unitid.slice(0),countunit:arrcountunit.slice(0),unitprice:arrunitprice.slice(0),discount:discount.slice(0),taxrate:taxrate.slice(0),taxprice:arrtaxprice.slice(0),quantity:arrquantity.slice(0),subsum:arrsubsum.slice(0)};
			window.returnValue=product;
			window.close();
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	
	function Check(){
		if(document.listform.checkall.checked){
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.listform.length;i++){

			if (!document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.listform.length;i++){
			if (document.listform.elements[i].checked)
				if(listform.elements[i].name != "checkall"){
				document.listform.elements[i].checked=false;
				}
		}
	}
	
	this.onload =function onLoadDivHeight(){
	document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("bodydiv").offsetHeight-10)+"px";
}
</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<div id="bodydiv">
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
       <form name="search" method="post" action="../aftersale/selectWithdrawProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
          <tr class="table-back"> 
		  	<td width="14%" align="right">产品类别：</td>
            <td width="14%"><input type="hidden" name="KeyWordLeft" id="KeyWordLeft">			
			<windrp:pstree id="KeyWordLeft" name="productstruts"/></td>
            <td width="12%"  align="right">关键字：</td>
            <td width="24%" ><input type="text" name="KeyWord"></td>
            <td width="10%"  align="right">&nbsp;</td>
            <td width="26%" align="right" ><span class="SeparatePage">
              <input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询">
            </span></td>
          </tr>
        
      </table>
      </form>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back">
          <td width="50"><a href="#" onClick="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a> </td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td width="50"><a href="#" onClick="javascript:window.close();"><img src="../images/CN/cancelx.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
          <td width="1"><img src="../images/CN/hline.gif" width="2" height="14"></td>
          <td class="SeparatePage"><pages:pager action="../aftersale/selectWithdrawProductAction.do"/></td>
        </tr>
      </table>
	  </div>
	</td>
	</tr>
	<tr>
		<td>
		<div id="listdiv" style="overflow-y: auto; height: 400px;" >
		<FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1" class="sortable">
        
          <tr align="center" class="title-top"> 
	  	    <td width="4%" ><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="10%" >产品编号</td>
            <td width="14%">产品名称</td>
            <td width="15%">规格</td>
            <td width="9%">单位</td>
			<td width="7%">单价</td>
			<td width="8%">税后单价</td>
			<td width="7%">数量</td>
			<td width="7%">折扣率</td>
			<td width="9%">税率</td>
            <td width="8%">金额</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.productid}" ></td>
            <td >${p.productid}</td>
            <td><input type="hidden" name="productname" value="${p.productname}">${p.productname}</td>
            <td><input type="hidden" name="specmode" value="${p.specmode}">${p.specmode}</td>
            <td><input type="hidden" name="unitid" value="${p.unitid}">
              <input type="hidden" name="countunit" value="${p.unitidname}">
              ${p.unitidname}</td>
			<td><input type="hidden" name="unitprice" value="${p.unitprice}">${p.unitprice}</td>
			<td><input type="hidden" name="taxprice" value="${p.taxunitprice}">${p.taxunitprice}</td>
			<td><input type="hidden" name="quantity" value="${p.quantity}">${p.quantity}</td>
			<td><input type="hidden" name="discount" value="${p.discount}">
			${p.discount}%</td>
			<td><input type="hidden" name="taxrate" value="${p.taxrate}">
			${p.taxrate}%</td>
            <td><input type="hidden" name="subsum" value="${p.subsum}">${p.subsum}</td>
          </tr>
          </logic:iterate> 
      
      </table>
        </form>
      </div>
    </td>
  </tr>
</table>
</body>
</html>
