<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/presentationTLD.tld" prefix="presentation" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
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
				arrunitprice[k]=document.listform.standardsale[i].value;
				discount[k]=document.listform.discount[i].value;
				taxrate[k]=document.listform.taxrate[i].value;
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
				arrunitprice[0]=document.listform.standardsale.value;
				discount[0]=document.listform.discount.value;
				taxrate[0]=document.listform.taxrate.value;
				flag=true;//只要选中一个就设为true
		}
		
		if(flag){
			/*setCookie("productid",arrid);
			setCookie("productname",arrpordocutname);
			setCookie("specmode",arrspecmode);
			setCookie("unitid",unitid);
			setCookie("countunit",arrcountunit);
			setCookie("unitprice",arrunitprice);
			setCookie("discount",discount);
			setCookie("taxrate",taxrate); */
			var product={productid:arrid.slice(0),productname:arrpordocutname.slice(0),specmode:arrspecmode.slice(0),unitid:unitid.slice(0),countunit:arrcountunit.slice(0),unitprice:arrunitprice.slice(0),discount:discount.slice(0),taxrate:taxrate.slice(0)};
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
       <form name="search" method="post" action="selectProductAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
       
		<input type="hidden" name="cid" id="cid" value="${cid}">
          <tr class="table-back"> 
		  	<td width="10%" align="right">产品类别：</td>
            <td width="15%"><select name="KeyWordLeft" id="KeyWordLeft">
                <logic:iterate id="ps" name="uls" > 
				<option value="${ps.structcode}">
				<c:forEach var="i" begin="1" end="${fn:length(ps.structcode)/2}" step="1"><c:out value="--"/></c:forEach>${ps.sortname}</option> 
            </logic:iterate> </select></td>
			<td width="16%"  align="right">品牌：</td>
            <td width="13%" >${brand}</td>            
            <td width="11%"  align="right">名称关键字：</td>
            <td width="35%" ><input type="text" name="KeyWord">
            <input type="submit" name="Submit" value="查询"></td>
            
          </tr>
       
      </table>
       </form>
      <FORM METHOD="POST" name="listform" ACTION="">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr align="center" class="title-top"> 
	  	    <td width="3%" ><input type="checkbox" name="checkall" onClick="Check();" ></td>
            <td width="13%" >产品编号</td>
            <td width="26%">产品名称</td>
            <td width="28%">规格</td>
            <td width="13%">单位</td>
			<td width="13%">折扣率</td>
			<td width="13%">税率</td>
            <td width="17%">价格</td>
          </tr>
          <logic:iterate id="p" name="sls" > 
		  <tr class="table-back" > 
		  	<td> <input type="checkbox" name="pid" value="${p.id}" ></td>
            <td >${p.id}</td>
            <td><input type="hidden" name="productname" value="${p.productname}">${p.productname}</td>
            <td><input type="hidden" name="specmode" value="${p.specmode}">${p.specmode}</td>
            <td><input type="hidden" name="unitid" value="${p.countunit}">
              <input type="hidden" name="countunit" value="${p.countunitname}">
              ${p.countunitname}</td>
			              <td><input type="hidden" name="discount" value="${p.discount}">
			              ${p.discount}%</td>
						              <td><input type="hidden" name="taxrate" value="${p.taxrate}">
						              ${p.taxrate}%</td>
            <td><input type="hidden" name="standardsale" value="${p.price}">${p.price}</td>
          </tr>
          </logic:iterate> 
       
      </table>
       </form>
      <table width="100%"  border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="30%">
			<table  border="0" cellpadding="0" cellspacing="0">
              <tr align="center"> 
                <td width="60"><a href="javascript:Affirm();">确定</a></td>
                <td width="60"><a href="javascript:window.close();">取消</a></td>
              </tr>
            </table></td>
          <td width="70%" align="right"> <presentation:pagination target="/sales/selectProductAction.do"/>          </td>
        </tr>
      </table>
      
    </td>
  </tr>
</table>
</body>
</html>
