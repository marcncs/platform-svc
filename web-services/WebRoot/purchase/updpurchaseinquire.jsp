<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);
        var d=x.insertCell(3);
        var e=x.insertCell(4);
        var f=x.insertCell(5);
		var g=x.insertCell(6);
		var h=x.insertCell(7);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"'>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" readonly value='"+p.productname+"'>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"40\" readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitname+"'>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.price+"' size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" onclick='this.select()' onkeydown='onlyNumber(event)' value=\"0\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";

 	SubTotal(dbtable.rows);
	TotalSum();
}
	

function SupperSelect(rowx){
	var pid=document.referForm.pid.value;
	if(pid==null||pid=="")
	{
		alert("请输入供应商！");
		return;
	}
	var p=showModalDialog("../common/toSelectProviderProductAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
			
	}
	
}

function SelectProvide(){
	var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){
		return;
	}
	document.referForm.pid.value=p.pid;
	document.referForm.providename.value=p.pname;
}

function SelectLinkman(){
	var pid=document.referForm.pid.value;
	if(pid==null||pid=="")
	{
		alert("请选择供应商！");
		return;
	}
	var l=showModalDialog("../common/selectPlinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	if ( l == undefined ){
		return;
	}
	document.referForm.plinkman.value=l.lname;
}

function deleteR(){
		chebox=document.all("che");
			if(chebox!=null){ 
				if (chebox.length >=1){
					for(var i=1;i<=chebox.length;i++){
					  if (chebox[i-1].checked) {
						document.getElementById('dbtable').deleteRow (i);
						i=i-1;
					  }
					}
				}else{
					if (chebox.checked ){
						document.all('dbtable').deleteRow(1);
					}
				}
		   }
	} 


function SubTotal(rowx){
	var sum=0.00;
	var tmprowx = rowx -1;

	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value)
		document.referForm.item("subsum").value=formatCurrency(sum);
	}else{

		for(var m=0;m<tmprowx;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referForm.item("subsum")(m).value=formatCurrency(sum);
		}

	}
	
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.referForm.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.referForm.item("subsum")(i).value);
		}
	}
	document.referForm.totalsum.value=formatCurrency(totalsum);
}




	
	function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
		}

	}
	

	function ChkValue(){
		var productid = document.referForm.productid;
		if ( !Validator.Validate(document.referForm,2) ){
			return false;
		}

		if(productid==undefined){
			alert("产品不能为空");
			return false;
		}

		showloading();
		return true;
	}


</script>
</head>

<body onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">
<SCRIPT language=javascript>
function click() {if (event.button==2) {alert('本页拒绝使用右键!');}}document.onmousedown=click
</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 修改采购询价记录</td>
        </tr>
      </table>
        <form name="referForm" method="post" action="updPurchaseInquireAction.do">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
      
          <tr> 
            <td  align="right"><table width="100%" height="83"  border="0" cellpadding="0" cellspacing="1">
              <tr class="table-back">
                <td  align="right"><input name="ID" type="hidden" id="ID" value="${pif.id}">
                  询价标题：</td>
                <td><input name="inquiretitle" type="text" id="inquiretitle" value="${pif.inquiretitle}"></td>
                <td width="13%" align="right">供应商：</td>
                <td width="49%"><input name="pid" type="hidden" id="pid" value="${pif.pid}">
                  <input name="providename" type="text" id="providename" value="${pif.providename}" readonly><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
              </tr>
              <tr class="table-back">
                <td width="17%"  align="right">联系人：</td>
                <td width="21%"><input name="plinkman" type="text" id="plinkman" value="${pif.plinkman}"></td>
                <td align="right">采购计划编号：</td>
                <td><input name="paid" type="text" id="paid" value="${pif.paid}"></td>
              </tr>
              <tr class="table-back">
                <td  align="right">有效天数：</td>
                <td><input name="validdate" type="text" id="validdate" value="${pif.validdate}"></td>
                <td align="right">负责人：</td>
                <td><input name="SeeTo" type="text" id="SeeTo" value="${pif.seetoname}" readonly></td>
              </tr>
              <tr>
                <td colspan="4">
                  <table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td width="90">选择产品:
					<img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>

                      <td width="60"><a href="javascript:deleteR();">删除</a></td>
                    </tr>
                </table></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="4%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="36%" > 产品</td>
                <td width="17%">规格</td>
                <td width="12%"> 单位</td>
                <td width="11%"> 单价</td>
                <td width="9%"> 数量</td>
                <td width="11%"> 金额</td>
                </tr>
			<c:set var="count" value="2"/>
            <logic:iterate id="p" name="als" > 
            <tr align="center" class="table-back">
				<td><input type="checkbox" value="${count}" name="che"></td>
                <td ><input name="productid" type="hidden" value="${p.productid}" id="productid"><input name="productname" type="text" value="${p.productname}" id="productname" size="40" readonly></td>
                <td>&nbsp;</td>
                <td><input name="unitid" type="hidden" value="${p.unitid}"><input name="unit" type="text" value="${p.unitname}" id="unit" size="8" readonly></td>
                <td><input name="unitprice" type="text" value="<fmt:formatNumber value='${p.unitprice}' pattern='0.00'/>" id="unitprice" size="10" maxlength="10"></td>
                <td><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="quantity" size="8" maxlength="8"></td>
                <td><input name="subsum" type="text" value="<fmt:formatNumber value='${p.subsum}' pattern='0.00'/>" id="subsum" size="10" maxlength="10"></td>
                </tr>
				<c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="7%" >&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();"></td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" value="${pif.totalsum}" size="10" readonly maxlength="10"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" class="table-back"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr class="table-back">
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="150" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${pif.remark}</textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" class="table-back"><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="history.back();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
