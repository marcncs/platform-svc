<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
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
       // var f=x.insertCell(5);
		var g=x.insertCell(5);
		var h=x.insertCell(6);
		var i=x.insertCell(7);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
       // f.className = "table-back";
		g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
		//f.innerHTML="<input name=\"batch\" type=\"text\" id=\"batch\" >";
        g.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        i.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var pid=document.validateProvide.cid.value;
	if(pid==null||pid=="")
	{
		alert("请输入客户！");
		return;
	}
	var p=showModalDialog("toSelectProviderProductAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	
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
					document.validateProvide.item("unitprice").value =arrunitprice[0];
					}else{
						document.validateProvide.item("productid")[i].value =arrid[i];
						document.validateProvide.item("productname")[i].value =arrpordocutname[i];
						document.validateProvide.item("specmode")[i].value=arrspecmode[i];
						document.validateProvide.item("unitid")[i].value =unitid[i];
						document.validateProvide.item("unit")[i].value =arrcountunit[i];
						document.validateProvide.item("quantity")[i].value=0;
						document.validateProvide.item("unitprice")[i].value =arrunitprice[i];
					}
			}
			
			//SubTotal(rowx);
			//TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始

			document.validateProvide.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.validateProvide.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.validateProvide.item("unitid")[rowx-1+i].value =unitid[i];
			document.validateProvide.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.validateProvide.item("quantity")[rowx-1+i].value=0;
			document.validateProvide.item("unitprice")[rowx-1+i].value =arrunitprice[i];
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


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	//alert("dbtable.rows.length===="+dbtable.rows.length+"    dbtable.rows.==="+dbtable.rows);
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.validateProvide.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.validateProvide.item("subsum")(m).value=sum;	
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
	document.validateProvide.totalsum.value=totalsum;
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

	function ChkValue(){
		var warehouseid = document.validateProvide.warehouseid;
		var pid = document.validateProvide.cid;
		var incomedate = document.validateProvide.incomedate;
		
		if(warehouseid.value==null||warehouseid.value==""){
			alert("仓库不能为空");
			//totalsum.focus();
			return false;
		}
		
		if(pid.value==null||pid.value==""){
			alert("供应商不能为空");
			//totalsum.focus();
			return false;
		}

		if(incomedate.value==null||incomedate.value==""){
			alert("预计入库日期不能为空");
			//totalsum.focus();
			return false;
		}

		
		validateProvide.submit();
	}
	
	function SetCode(c){
		if(c=="r"){
			record.style.display = "";
			elect.style.display = "none";
			bar.style.display = "none";
		}
		if(c=="e"){
			record.style.display = "none";
			elect.style.display = "";
			bar.style.display = "none";
		}
		if(c=="b"){
			record.style.display = "none";
			elect.style.display = "none";
			bar.style.display = "";
		}
		
	}
	
	function RPIDToSearch(){
	
		var rpid = document.validateProvide.RPID.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		var pid=document.validateProvide.pid.value;
		if(pid==null||pid=="")
		{
			alert("请输入供应商！");
			return;
		}
		getProductByRPID(pid,rpid);
		}
		//alert("bb");
	}


	function getProductByRPID(pvid,rpid){
	//alert("cc");
	   var url = "../purchase/getProductByRPIDAjax.do?pid="+pvid+"&RPID="+rpid;
	   //document.getElementById("require").style.display="";
	   //document.getElementById("require").innerHTML="正在读取数据...";
       var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showResponse}
                    );
	}

	function showResponse(originalRequest){
	var product = originalRequest.responseXML.getElementsByTagName("product");
	if(product.length>=1){
	var rm = product[0];
		if((dbtable.rows.length-1) <1){
			addRow();
			//alert("-11-"+product);
			document.validateProvide.item("productid").value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname").value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode").value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid").value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit").value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity").value=0;
			document.validateProvide.item("unitprice").value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.validateProvide.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.validateProvide.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.validateProvide.item("specmode")[rowx-1].value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.validateProvide.item("unitid")[rowx-1].value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.validateProvide.item("unit")[rowx-1].value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.validateProvide.item("quantity")[rowx-1].value=0;
			document.validateProvide.item("unitprice")[rowx-1].value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}
		
	}else{
		alert("该编号不存在");
	}

	}


function SelectProvide(){
	showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.cid.value=getCookie("cid");
	document.validateProvide.cname.value=getCookie("cname");
}

function SelectLinkman(){
	var pid=document.validateProvide.cid.value;
	if(pid==null||pid=="")
	{
		alert("请选择客户！");
		return;
	}
	showModalDialog("toSelectLinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.cid.value=getCookie("lid");
	document.validateProvide.plinkman.value=getCookie("lname");
	document.validateProvide.tel.value=getCookie("ltel");
}
</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增借入单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../purchase/addBorrowIncomeAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
          <tr> 
            <td >
			
	<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">入货仓库：</td>
          <td width="23%"><select name="warehouseid">
            <logic:iterate id="w" name="alw">
              <option value="${w.id}">${w.warehousename}</option>
            </logic:iterate>
          </select></td>
          <td width="9%" align="right">出货仓库：</td>
          <td width="25%"><select name="warehouseout">
            <logic:iterate id="w" name="alw">
              <option value="${w.id}">${w.warehousename}</option>
            </logic:iterate>
          </select></td>
	      <td width="9%" align="right">采购订单号：</td>
	      <td width="24%"><input name="poid" type="text" id="poid"></td>
	  </tr>
	  <tr>
	    <td  align="right">客户：</td>
	    <td><input name="cid" type="hidden" id="cid">
          <input name="cname" type="text" id="cname" size="35">
          <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	    <td align="right">客户联系人：</td>
	    <td><input name="plinkman" type="text" id="plinkman">
          <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	    <td align="right">联系电话：</td>
	    <td><input name="tel" type="text" id="tel"></td>
	    </tr>
	  <tr>
	    <td  align="right">批次：</td>
	    <td><input name="batch" type="text" id="batch"></td>
	    <td align="right">入库日期：</td>
	    <td><input name="incomedate" type="text" id="incomedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
                  <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect" >选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    </tr>
                  </table></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="right"><table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
                <td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="14%">产品编号</td>
                <td width="20%" > 产品名称 </td>
                <td width="14%">规格</td>
                <td width="8%"> 单位</td>
                <!--<td width="15%">批次</td>-->
                <td width="6%"> 单价</td>
                <td width="9%"> 数量</td>
                <td width="11%"> 金额</td>
              </tr>
            </table>
              <table width="100%"   border="0" cellpadding="0" cellspacing="0">
                <tr class="table-back">
                  <td width="3%"  align="center"><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                  <td width="11%" align="center">&nbsp;</td>
                  <td width="7%" align="center">&nbsp;</td>
                  <td width="68%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                  <td width="11%"><input name="totalsum" type="text" id="totalsum" value="0" size="10" maxlength="10"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark"></textarea></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();" value="提交">              &nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="history.back();"></td>
          </tr>
       
      </table>
       </form>
    </td>
  </tr>
</table>
</body>
</html>
