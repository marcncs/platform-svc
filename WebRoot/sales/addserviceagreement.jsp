<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@page import="java.util.*,java.text.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<script type="text/javascript" src="../js/pub_Calendar.js"></script>
<%
        java.util.Date d = null;
        Calendar cd=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        d=cd.getTime();
%>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/Cookie.js"> </SCRIPT>
<script language="javascript">
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
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"40\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\"><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly>";
        f.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value=\"0\" size=\"10\" maxlength=\"10\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"0\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
        h.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\">";
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	//alert(rowx);
	//document.referform.item("productname")(rowx-2).value = "b";
	var p=showModalDialog("toSelectProductAction.do",null,"dialogWidth:16cm;dialogHeight:10cm;center:yes;status:no;scrolling:no;");
	
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
					document.referform.item("productid").value =arrid[0];
					document.referform.item("productname").value =arrpordocutname[0];
					document.referform.item("specmode").value=arrspecmode[0];
					document.referform.item("unitid").value =unitid[0];
					document.referform.item("unit").value =arrcountunit[0];
					document.referform.item("quantity").value=0;
					document.referform.item("unitprice").value =arrunitprice[0];
					}else{
						document.referform.item("productid")[i].value =arrid[i];
						document.referform.item("productname")[i].value =arrpordocutname[i];
						document.referform.item("specmode")[i].value=arrspecmode[i];
						document.referform.item("unitid")[i].value =unitid[i];
						document.referform.item("unit")[i].value =arrcountunit[i];
						document.referform.item("quantity")[i].value=0;
						document.referform.item("unitprice")[i].value =arrunitprice[i];
					}
			}
			
			SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.referform.item("productid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.referform.item("productname")[rowx-1+i].value =arrpordocutname[i];
			document.referform.item("specmode")[rowx-1+i].value=arrspecmode[i];
			document.referform.item("unitid")[rowx-1+i].value =unitid[i];
			document.referform.item("unit")[rowx-1+i].value =arrcountunit[i];
			document.referform.item("quantity")[rowx-1+i].value=0;
			document.referform.item("unitprice")[rowx-1+i].value =arrunitprice[i];
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
		document.referform.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referform.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.referform.item("subsum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.referform.item("subsum")(i).value);
		}
	}
	document.referform.totalsum.value=totalsum;
}


function Check(){
			//alert("document.activeform.checkall.values");
		if(document.referform.checkall.checked){
			//alert(document.activeform.checkall.values);
			checkAll();
		}else{
			uncheckAll();
		}
	}
	
	function checkAll(){
		for(i=0;i<document.referform.length;i++){

			if (!document.referform.elements[i].checked)
				if(referform.elements[i].name != "checkall"){
				document.referform.elements[i].checked=true;
				}
		}
	}

	function uncheckAll(){
		for(i=0;i<document.referform.length;i++){
			if (document.referform.elements[i].checked)
				if(referform.elements[i].name != "checkall"){
				document.referform.elements[i].checked=false;
				}
		}
	}

	function ChkValue(){
		var sdate = document.referform.sdate;
		
		if(sdate.value==""){
		alert("约定服务时间不能为空！");
		return false;
		}
	
		referform.submit();
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
	
		var rpid = document.referform.RPID.value;
		//alert(rpid);
		if(event.keyCode==13){
		//alert("aa");
		document.getElementById('RPID').select();
		getProductByRPID(rpid);
		}
		//alert("bb");
	}


	function getProductByRPID(rpid){
	//alert("cc");
	   var url = "../sales/getProductByRPIDAjax.do?RPID="+rpid;
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
			document.referform.item("productid").value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.referform.item("productname").value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.referform.item("specmode").value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.referform.item("unitid").value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.referform.item("unit").value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.referform.item("quantity").value=0;
			document.referform.item("unitprice").value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}else{
		var rowx = dbtable.rows.length;
		  var i=1;
			addRow();
			//alert("-22-");
			document.referform.item("productid")[rowx-1].value =rm.getElementsByTagName("productid")[0].firstChild.data;
			document.referform.item("productname")[rowx-1].value =rm.getElementsByTagName("productname")[0].firstChild.data;
			document.referform.item("specmode")[rowx-1].value=rm.getElementsByTagName("specmode")[0].firstChild.data;
			document.referform.item("unitid")[rowx-1].value =rm.getElementsByTagName("unitid")[0].firstChild.data;
			document.referform.item("unit")[rowx-1].value =rm.getElementsByTagName("unitidname")[0].firstChild.data;
			document.referform.item("quantity")[rowx-1].value=0;
			document.referform.item("unitprice")[rowx-1].value =rm.getElementsByTagName("unitprice")[0].firstChild.data;
		}
		
	}else{
		alert("该编号不存在");
	}

	}
	
	function SelectCustomer(){
	var c=showModalDialog("toSelectCustomerAction.do",null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	document.referform.cid.value=c.cid;
	document.referform.cname.value=c.cname;
}

function SelectLinkman(){
	var cid=document.referform.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
	//document.validateProvide.cid.value=getCookie("lid");
	document.referform.linkman.value=lk.lname;
	document.referform.tel.value=lk.ltel;
}
</script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<SCRIPT language=javascript>

</SCRIPT>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
              <td width="772"> 添加服务预约</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="referform" method="post" action="addServiceAgreementAction.do" onSubmit="return ChkValue();">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  
	    <tr >
	      <td  align="right"><input name="cid" type="hidden" id="cid">
客户名称：</td>
	      <td><input name="cname" type="text" id="cname" >
            <a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td align="right" >联系人：</td>
	      <td width="17%" ><input name="linkman" type="text" id="linkman">
            <a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0"></a></td>
	      <td width="11%" align="right" >联系电话：</td>
	      <td width="25%" ><input name="tel" type="text" id="tel"></td>
	      </tr>
	    <tr >
	      <td  align="right">服务种类：</td>
	      <td>${scontentselect}</td>
	      <td align="right" >服务状态：</td>
	      <td >${sstatusselect}</td>
	      <td align="right" >等级：</td>
	      <td >${rankselect}</td>
	      </tr>
	    <tr >
          <td width="10%"  align="right">约定服务时间：</td>
	      <td width="27%"><input name="sdate" type="text" id="sdate" onFocus="javascript:selectDate(this)" value="<%=sdf.format(d)%>"></td>
	      <td width="10%" align="right" >问题描述：</td>
	      <td colspan="3" ><input name="question" type="text" id="question" value="" size="80"></td>
	      </tr>
		</table>
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
          <tr >
            <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                <tr align="center" class="back-blue-light2">
                  <!--<td width="60"><a href="javascript:SetCode('r')"><img src="../images/CN/record.gif" width="19"  border="0" title="手工录入产品编号"></a><a href="javascript:SetCode('e')"><img src="../images/CN/elect.gif" width="19"  border="0" title="选择"></a><a href="javascript:SetCode('b')"><img src="../images/CN/bar.gif" width="19"  border="0" title="条码扫描"></a></td>
                  <td id="record"><input name="RPID" type="text" id="RPID" size="15" title="录入编号后，按回车键继续录其它编号" onKeyUp="RPIDToSearch();" onClick="this.select();" tabindex=1></td>-->
                  <td id="elect" >选择产品： <img src="../images/CN/find.gif" width="18" height="18" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                  <!--<td id="bar" style="display:none"><input name="SBAR" type="text" id="SBAR" size="30"></td>-->
                </tr>
            </table></td>
          </tr>
        </table>
		<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
          <tr align="center" class="title-top">
            <td width="3%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
            <td width="10%">产品编号</td>
            <td width="21%" > 产品名称</td>
            <td width="28%">规格型号</td>
            <td width="10%"> 单位</td>
            <td width="9%"> 单价</td>
            <td width="10%"> 数量</td>
            <td width="9%"> 金额</td>
          </tr>
        </table>
		<table width="100%"   border="0" cellpadding="0" cellspacing="0">
          <tr align="center" class="table-back">
            <td width="20" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
            <td width="11%" align="center">&nbsp;</td>
            <td width="7%" align="center">&nbsp;</td>
            <td width="69%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">
              ：</td>
            <td width="10%" align="center"><input name="totalsum" type="text" id="totalsum" value="0" size="10" maxlength="10"></td>
          </tr>
        </table>
		</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
	    <tr >
          <td width="5%"  align="right">备注：</td>
	      <td width="95%" colspan="5"><textarea name="memo" cols="180" rows="5" id="memo"></textarea></td>
	      </tr>
	  </table>


<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><div align="center">
      <input type="button" name="Submit" onClick="ChkValue();" value="提交">
      &nbsp;&nbsp;
      <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
    </div></td>
  </tr>
</table>
</form>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>

</body>
</html>
