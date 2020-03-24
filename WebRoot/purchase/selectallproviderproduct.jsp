<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">

<script language="javascript">
	
	var arrid=new Array();
	var arrpordocutname=new Array();
	var arrspecmode = new Array();
	var unitid=new Array();
	var arrcountunit=new Array();
	var arrunitprice=new Array();
	
	function Affirm(){
		
		var flag=false;
		var k=0;
		if ( document.listform.pid==undefined){
			alert("请选择你要操作的记录!");
		return;
		}
		if(document.listform.pid.length>1){
			for(var i=0;i<document.listform.pid.length;i++){
				if(document.listform.pid[i].checked){
					arrid[k]=document.listform.pid[i].value;//k保证只有选中的才放到数组里
					arrpordocutname[k]=document.listform.productname[i].value;
					arrspecmode[k]=document.listform.specmode[i].value;
					unitid[k]=document.listform.unitid[i].value;
					arrcountunit[k]=document.listform.countunit[i].value;
					arrunitprice[k]=document.listform.price[i].value;
					k++;
					flag=true;//只要选中一个就设为true
				}
			}
		}else{
				arrid[0]=document.listform.pid.value;//k保证只有选中的才放到数组里
				arrpordocutname[0]=document.listform.productname.value;
				arrspecmode[k]=document.listform.specmode.value;
				unitid[0]=document.listform.unitid.value;
				arrcountunit[0]=document.listform.countunit.value;
				arrunitprice[0]=document.listform.price.value;
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){

			var p={productid:arrid.slice(0),productname:arrpordocutname.slice(0),specmode:arrspecmode.slice(0),unitid:unitid.slice(0),countunit:arrcountunit.slice(0),unitprice:arrunitprice.slice(0)};
			window.returnValue=p;
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
	
	

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 选择产品</td>
	    
        </tr>
      </table>
      <form name="search" method="post" action="selectAllProviderProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        
          <tr class="table-back">
            <td width="16%" align="right">产品类别：</td>
            <td width="36%">
            <input type="hidden" name="KeyWordLeft" id="KeyWordLeft" value="${KeyWordLeft}">			
			<windrp:pstree id="KeyWordLeft" name="productstruts" value="${productstruts}"/>
            </td>
            <td width="22%"  align="right"> <input name="pid" type="hidden" id="pid" value="${pid}">
            名称关键字：</td>
            <td width="26%" ><input type="text" name="KeyWord" value="${KeyWord }">
              </td>
              <td class="SeparatePage"><input name="Submit" type="image" id="Submit" src="../images/CN/search.gif" border="0" title="查询"></td>
          </tr>
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr class="title-func-back"> 
      
                <td width="50"><a href="javascript:Affirm();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;确定</a></td>
                <td width="50"><a href="javascript:window.close();"><img src="../images/CN/addnew.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;取消</a></td>
           
          <td class="SeparatePage"> <pages:pager action="../purchase/selectAllProviderProductAction.do"/>          </td>
        </tr>
      </table>
       <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
       
          <tr align="center" class="title-top"> 
	  	    <td width="3%"><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="9%" >产品编号</td>
            <td width="20%">产品名称</td>
            <td width="14%">规格</td>
            <td width="20%">单位</td>
            <td width="20%">单价</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr align="center" class="table-back-colorbar" > 
		  	<td> <input type="checkbox" name="pid" value="${p.productid}" ></td>
            <td >${p.productid}</td>
            <td><input type="hidden" name="productname" value="${p.pvproductname}">${p.pvproductname}</td>
            <td><input type="hidden" name="specmode" value="${p.pvspecmode}">${p.pvspecmode}</td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="countunit" value="${p.unitname}">
              ${p.unitname}</td>
            <td><input type="hidden" name="price" value="<fmt:formatNumber value='${p.price}' pattern='0.00'/>">
              ${p.price}</td>
		  </tr>
          </logic:iterate> 
        
      </table>
      </form>
      
    </td>
  </tr>
</table>
</body>
</html>
