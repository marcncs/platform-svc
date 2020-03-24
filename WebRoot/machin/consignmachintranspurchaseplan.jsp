<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showSQ.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 var iteration=0;
 var i=1;
 var chebox=null;
  function addRow(){
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
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"32\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"32\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";		
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" onFocus=\"SubTotal("+dbtable.rows.length+")\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var p=showModalDialog("toSelectProductAction.do" ,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:yes;");

	var arrid=p.productid;
	var arrpordocutname=p.productname;
	var arrspecmode = p.specmode;
	var unitid=p.unitid;
	var arrcountunit=p.countunit;
	var arrunitprice=p.unitprice;
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("productid").value =arrid[0];
					document.validateProvide.item("productname").value =arrpordocutname[0];
					document.validateProvide.item("specmode").value=arrspecmode[0];
					document.validateProvide.item("unitid").value =unitid[0];
					document.validateProvide.item("unit").value =arrcountunit[0];
					document.validateProvide.item("quantity").value=0;
					document.validateProvide.item("unitprice").value =formatCurrency(arrunitprice[0]);
					}else{
						document.validateProvide.item("productid")[i].value =arrid[i];
						document.validateProvide.item("productname")[i].value =arrpordocutname[i];
						document.validateProvide.item("specmode")[i].value=arrspecmode[i];
						document.validateProvide.item("unitid")[i].value =unitid[i];
						document.validateProvide.item("unit")[i].value =arrcountunit[i];
						document.validateProvide.item("quantity")[i].value=0;
						document.validateProvide.item("unitprice")[i].value =formatCurrency(arrunitprice[i]);
					}
			}
			
			SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.validateProvide.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.validateProvide.item("unitid")[rowx-1+i].value =unitid[i];
			document.validateProvide.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.validateProvide.item("quantity")[rowx-1+i].value=0;
			document.validateProvide.item("unitprice")[rowx-1+i].value =formatCurrency(arrunitprice[i]);
			}
	}
	
}

function deleteR(){
 // 第一行不能放 alert EX.alert("document.table1.chebox[0].checked="+chebox[0].checked);
 //用if來解決chebox在第一次load時其為undifined而不能delete時所使用的方法

    if(chebox!=null){
      if(document.all('dbtable').rows.length==2){
      if(chebox.checked){
         document.all('dbtable').deleteRow(1);  //Row 行是從0開始
      }
    }else{
        //    for (i=chebox.length;i>0;i--){
      for(var i=1;i<=chebox.length;i++){
                if (chebox[i-1].checked) {
 
          document.getElementById('dbtable').deleteRow ( i);
        i=i-1;
        }
 
      }
        }
    }
}

function DelAll(){
		checkAll();
		deleteR();
	}


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.validateProvide.item("subsum").value=formatCurrency(sum);
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.validateProvide.item("subsum")(m).value=formatCurrency(sum);	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("subsum")(i).value);
		}
	}
	document.validateProvide.totalsum.value=formatCurrency(totalsum);
}

function SelectCustomer(){
	var c=showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:10.5cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
}

function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var c=showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.cid.value=getCookie("lid");
	document.validateProvide.receiveman.value=c.lname;
	document.validateProvide.tel.value=c.ltel;
	//document.validateProvide.transportaddr.value=c.ltransportaddr;
}


	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.validateProvide.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.validateProvide.length;i++){

			if (!document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.validateProvide.length;i++){
			if (document.validateProvide.elements[i].checked)
				if(validateProvide.elements[i].name != "checkall"){
				document.validateProvide.elements[i].checked=false;
				}
		}
	}


	function SelectAssembleProduct(){
	showModalDialog("toSelectAssembleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.id.value=getCookie("id");
	document.validateProvide.aproductid.value=getCookie("productid");
	document.validateProvide.aproductname.value=getCookie("productname");
	document.validateProvide.aspecmode.value=getCookie("specmode");
	document.validateProvide.aunitid.value=getCookie("unitid");
	document.validateProvide.aunitidname.value=getCookie("unitidname");
	DelAll();
	document.validateProvide.aquantity.value=1;
	document.validateProvide.oquantity.value=1;
	addAjaxProduct(getCookie("id"));
	}
	
	function addAjaxProduct(objid){
        var url = 'ajaxGetAssembleProductAction.do?id='+objid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showexrate}
                    );	
    }

   function showexrate(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');		
		var cr = data.dlist;
			for(var n=0; n<cr.length; n++){
				addRow();
				//alert(cr[i].productid+cr[i].productname+cr[i].specmode+cr[i].unitid+cr[i].unitidname+cr[i].quantity+cr[i].unitprice);
					if(n==0){//第一次加时还不是数组
						document.validateProvide.item("productid").value =cr[n].productid;
						document.validateProvide.item("productname").value =cr[n].productname;
						document.validateProvide.item("specmode").value=cr[n].specmode;
						document.validateProvide.item("unitid").value =cr[n].unitid;
						document.validateProvide.item("unit").value =cr[n].unitidname;
						document.validateProvide.item("quantity").value=cr[n].quantity;
						document.validateProvide.item("unitprice").value =formatCurrency(cr[n].unitprice);
						document.validateProvide.item("subsum").value =formatCurrency(cr[n].subsum);
						}else{
							document.validateProvide.item("productid")[n].value =cr[n].productid;
							document.validateProvide.item("productname")[n].value =cr[n].productname;
							document.validateProvide.item("specmode")[n].value=cr[n].specmode;
							document.validateProvide.item("unitid")[n].value =cr[n].unitid;
							document.validateProvide.item("unit")[n].value =cr[n].unitidname;
							document.validateProvide.item("quantity")[n].value=cr[n].quantity;
							document.validateProvide.item("unitprice")[n].value =formatCurrency(cr[n].unitprice);
							document.validateProvide.item("subsum")[n].value =formatCurrency(cr[n].subsum);
						}
			}
			SubTotal(1);
			TotalSum();
	}

	function ChangeQuantity(){
		var q = document.validateProvide.aquantity.value;
		var oq = document.validateProvide.oquantity.value;
		var qu = document.validateProvide.quantity;
		if ( qu.length ){
			for (var j=0; j<qu.length; j++){
				qu[j].value = q /oq * qu[j].value ;
			}
		}else{
			qu.value = q /oq * qu.value ;
		}
		document.validateProvide.oquantity.value = q;
	}
	
	


function Chk(){
				
		if ( !Validator.Validate(document.validateProduct,2) ){
		return false;
		}
		
		showloading();
		return true;
		
	}
</script>
</head>
<body>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 委外加工单生成采购计划</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../machin/consignMachinTransPurchasePlanAction.do" onSubmit="return Chk();">
        
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本资料</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	    <td width="9%"  align="right"><input name="id" type="hidden" id="id" value="${sof.id}">
	      计划部门：</td>
	    <td width="23%"><input name="MakeDeptID" type="hidden" id="MakeDeptID">
          <input type="text" name="deptname" id="deptname" onClick="selectDUW(this,'MakeDeptID','','d')" value="请选择" readonly></td>
	    <td width="13%" align="right">计划人：</td>
	    <td width="20%"><input type="hidden" name="MakeID" id="MakeID">
          <input type="text" name="uname" id="uname" onClick="selectDUW(this,'MakeID',$F('MakeDeptID'),'du')" value="请选择" readonly></td>
	    <td width="15%" align="right">需求日期：</td>
	    <td width="20%"><input name="requiredate" type="text" id="requiredate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="12" readonly></td>
	  </tr>
	  <tr>
	    <td height="19" align="right">建议采购日期：</td>
	    <td><input name="advicedate" type="text" id="advicedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" size="12" readonly></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
</fieldset>
			 </td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="8%">产品编码</td>
                <td width="19%" > 产品名称</td>
                <td width="12%">规格型号</td>
                <td width="10%">单位</td>
                <td width="8%">单价</td>
                <td width="8%"> 数量</td>				
                <td width="9%">现在库存</td>
                <td width="9%"> 建议采购数量</td>
                </tr>
				<c:set var="count" value="2"/>
            	<logic:iterate id="p" name="als" > 
              <tr align="center" class="table-back">
                <td align="left"><input type="checkbox" value="${count}" name="che"></td>
                <td align="left"><input name="productid" type="text" id="productid" value="${p.productid}" size="12"></td>
                <td  align="left"><input name="productname" type="text" value="${p.productname}" id="productname" size="32" readonly></td>
                <td align="left"><input name="specmode" type="text" value="${p.specmode}" id="specmode" size="32" readonly></td>
                <td align="left"><input name="unitid" type="hidden" value="${p.unitid}">
                    <input name="unit" type="text" value="${p.unitidname}" id="unit" size="8" readonly></td>
                <td align="left"><input name="unitprice" type="text" id="unitprice" onChange="SubTotal(${count});TotalSum();" value="0.00" size="10" maxlength="10" dataType="Double" msg="单价只能是数字!" require="false"></td>
                <td align="left"><input name="quantity" type="text" value="${p.quantity}" onChange="SubTotal(${count});TotalSum();" id="quantity" size="8" maxlength="8" dataType="Double" msg="数量只能是数字!" require="false"></td>
                <td align="left"><a href="#" onMouseOver="ShowSQ(this,'${p.productid}');" onMouseOut="HiddenSQ();"><img src="../images/CN/stock.gif" width="16"  border="0"></a>${p.stockpile}</td>
                <td align="left"><input name="advicequantity" type="text" id="advicequantity" value="${p.advicequantity}" size="10" maxlength="10" dataType="Double" msg="建议采购数量只能是数字!" require="false"></td>
              </tr>
			  <c:set var="count" value="${count+1}"/>
			</logic:iterate> 
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%" align="center">&nbsp;</td>
                <td width="7%" align="center">&nbsp;</td>
                <td width="69%" align="right">&nbsp;</td>
                <td width="10%" align="center">&nbsp;</td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false">${sof.remark}</textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="Submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
