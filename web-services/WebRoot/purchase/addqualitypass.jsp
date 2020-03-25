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
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"35\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"35\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"8\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值
 
}
	

function SupperSelect(rowx){
var pid=document.referForm.pid.value;
	if(pid==null||pid=="")
	{
		alert("请输入供应商！");
		return;
	}
	showModalDialog("toSelectProviderProductAction.do?pid="+pid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	
	var strarrid=getCookie("productid");
	var strarrpordocutname=getCookie("productname");
	var strarrspecmode=getCookie("specmode");
	var strunitid=getCookie("unitid");
	var strarrcountunit=getCookie("countunit");
	var strarrunitprice=getCookie("unitprice");
	
	var arrid=strarrid.split(",");
	var arrpordocutname=strarrpordocutname.split(",");
	var arrspecmode = strarrspecmode.split(",");
	var unitid=strunitid.split(",");
	var arrcountunit=strarrcountunit.split(",");
	var arrunitprice=strarrunitprice.split(",");
	
	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.referForm.item("productid").value =arrid[0];
					document.referForm.item("productname").value =arrpordocutname[0];
					document.referForm.item("specmode").value=arrspecmode[0];
					document.referForm.item("unitid").value =unitid[0];
					document.referForm.item("unit").value =arrcountunit[0];
					document.referForm.item("quantity").value=0;
					document.referForm.item("unitprice").value =arrunitprice[0];
					}else{
						document.referForm.item("productid")[i].value =arrid[i];
						document.referForm.item("productname")[i].value =arrpordocutname[i];
						document.referForm.item("specmode")[i].value=arrspecmode[i];
						document.referForm.item("unitid")[i].value =unitid[i];
						document.referForm.item("unit")[i].value =arrcountunit[i];
						document.referForm.item("quantity")[i].value=0;
						document.referForm.item("unitprice")[i].value =arrunitprice[i];
					}
			}
			
			SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.referForm.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.referForm.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.referForm.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.referForm.item("unitid")[rowx-1+i].value =unitid[i];
			document.referForm.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.referForm.item("quantity")[rowx-1+i].value=0;
			document.referForm.item("unitprice")[rowx-1+i].value =arrunitprice[i];
			}
	}
	
}

function SelectProvide(){
	var p.showModalDialog("selectProviderAction.do",null,"dialogWidth:16cm;dialogHeight:8cm;center:yes;status:no;scrolling:no;");
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
	showModalDialog("toSelectLinkmanAction.do?pid="+pid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.cid.value=getCookie("lid");
	document.referForm.plinkman.value=getCookie("lname");
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

	var tmprowx = rowx -1;

	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value)
		//	alert("sum--"+sum);
		document.referForm.item("subsum").value=sum;
	}else{

	//var subsum = 0.00;
		for(var m=0;m<tmprowx;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
		}
		document.referForm.item("subsum")(rowx-2).value=sum;
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
	document.referForm.totalsum.value=totalsum;
}



	function Check(){
			//alert("document.activeform.checkall.values");
		if(document.referForm.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.referForm.length;i++){

			if (!document.referForm.elements[i].checked)
				if(referForm.elements[i].name != "checkall"){
				document.referForm.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.referForm.length;i++){
			if (document.referForm.elements[i].checked)
				if(referForm.elements[i].name != "checkall"){
				document.referForm.elements[i].checked=false;
				}
		}
	}

	function ChkValue(){
		var pid = document.referForm.pid;

		if(pid.value==null||pid.value==""){
			alert("供应商不能为空");
			//totalsum.focus();
			return false;
		}

		
		referForm.submit();
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
	
		var rpid = document.referForm.RPID.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		var pid=document.referForm.pid.value;
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
			document.referForm.item("productid").value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.referForm.item("productname").value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.referForm.item("specmode").value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.referForm.item("unitid").value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.referForm.item("unit").value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.referForm.item("quantity").value=0;
			document.referForm.item("unitprice").value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.referForm.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.referForm.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.referForm.item("specmode")[rowx-1].value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.referForm.item("unitid")[rowx-1].value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.referForm.item("unit")[rowx-1].value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.referForm.item("quantity")[rowx-1].value=0;
			document.referForm.item("unitprice")[rowx-1].value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}
		
	}else{
		alert("该编号不存在");
	}

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
          <td width="772"> 新增质检合格单</td>
        </tr>
      </table>
      <form name="referForm" method="post" action="addQualityPassAction.do" >
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
	  	<td width="9%"  align="right">供应商：</td>
          <td width="25%"><input name="pid" type="hidden" id="pid">
            <input name="providename" type="text" id="providename" size="35" readonly>
            <a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
          <td width="10%" align="right">联系人：</td>
          <td width="24%"><input name="plinkman" type="text" id="plinkman">
            <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td width="7%" align="right">采购类型：</td>
	      <td width="25%">${purchasesortname}</td>
	  </tr>
	  <tr>
	    <td  align="right">采购部门：</td>
	    <td><select name="purchasedept" id="purchasedept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}">${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">采购人员：</td>
	    <td><select name="purchaseid" id="purchaseid">
          <logic:iterate id="u" name="als">
            <option value="${u.userid}">${u.realname}</option>
          </logic:iterate>
        </select></td>
	    <td align="right">质检部门：</td>
	    <td><select name="qualitydept" id="qualitydept">
          <logic:iterate id="d" name="aldept">
            <option value="${d.id}">${d.deptname}</option>
          </logic:iterate>
        </select></td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
                  <tr align="center" class="back-blue-light2">
                    <td width="60"><!--<a href="javascript:SetCode('r')"><img src="../images/CN/record.gif" width="19"  border="0" title="手工录入产品编号"></a>--><a href="javascript:SetCode('e')"><img src="../images/CN/elect.gif" width="19"  border="0" title="选择"></a><!--<a href="javascript:SetCode('b')"><img src="../images/CN/bar.gif" width="19"  border="0" title="条码扫描"></a>--></td>
                    <td id="record" style="display:none"><input name="RPID" type="text" id="RPID" size="15" title="录入编号后，按回车键继续录其它编号" onKeyUp="RPIDToSearch();" onClick="this.select();" tabindex=1></td>
                    <td id="elect" >选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                    <td id="bar" style="display:none"><input name="SBAR" type="text" id="SBAR" size="30"></td>
                  </tr>
                </table></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="3%" ><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="13%">产品编号</td>
                <td width="14%"> 产品名称 </td>
                <td width="14%">规格</td>
                <td width="14%"> 单位</td>
                <td width="14%"> 单价</td>
                <td width="14%"> 数量</td>
                <td width="14%"> 金额</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10"></td>
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
