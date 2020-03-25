<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showhp.js"> </SCRIPT>
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
		var i=x.insertCell(8);
		var j=x.insertCell(9);
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
        g.className = "table-back";
		h.className = "table-back";
		i.className = "table-back";
		j.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"16\">";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" size=\"40\" readonly value='"+p.productname+"'>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" size=\"40\" readonly value='"+p.specmode+"'>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" size=\"6\" value='"+p.unitid+"'><input name=\"unit\" type=\"text\" id=\"unit\" size=\"8\" readonly value='"+p.unitname+"'>";
		f.innerHTML="<input name=\"requiredate\" type=\"text\" id=\"requiredate\" size=\"10\" readonly value=\"<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>\" onFocus=\"selectDate(this);\">";
        g.innerHTML="<a href=\"#\" onMouseOver=\"showhp(this,$F('pid'),'"+p.productid+"')\" onMouseOut=\"hiddenhp();\"><img src=\"../images/CN/cheng.gif\" width=\"16\" height=\"16\" border=\"0\"></a>";
        h.innerHTML="<input name=\"unitprice\" type=\"text\" id=\"unitprice\" value='"+p.price+"' size=\"10\" maxlength=\"10\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"单价只能是数字!\" require=\"false\" onKeyPress=\"KeyPress(this)\">";
        i.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" onclick='this.select()' onkeydown='onlyNumber(event)' value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal();TotalSum();\" onFocus=\"SubTotal();TotalSum();\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\">";
        j.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" value=\"0.00\" size=\"10\" maxlength=\"10\" readonly>";
	
 	SubTotal();
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
	document.referForm.pname.value=p.pname;
	document.referForm.receiveaddr.value=p.addr;
	getLinkman(p.pid);
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
	document.referForm.tel.value=l.lmobile;
}

function getLinkman(v_cid){
	var url = "../sales/ajaxProviderLinkmanAction.do?cid="+v_cid;
	var myAjax = new Ajax.Request(
			url,
			{method: 'get', parameters: '', onComplete: showLman}
			);	
}
function showLman(originalRequest){
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.linkman;
	if ( lk != undefined ){			
		document.referForm.plinkman.value=lk.name;
		document.referForm.tel.value=lk.mobile;
	}
}	

function SubTotal(){
	var sum=0.00;
	var unitprice=document.referForm.unitprice;
	var quantity=document.referForm.quantity;
	var objsubsum=document.referForm.subsum;
	if ( unitprice.length){
		for (var m=0; m<unitprice.length; m++){
			objsubsum[m].value=formatCurrency(parseFloat(unitprice[m].value*quantity[m].value));
		}
	}else{
		objsubsum.value=formatCurrency(parseFloat(unitprice.value*quantity.value));
	}
}

function TotalSum(){
	var totalsum=0.00;
	var objsubsum=document.referForm.subsum;
	if ( objsubsum.length){
		for (var m=0; m<objsubsum.length; m++){
			totalsum=totalsum+parseFloat(objsubsum[m].value);
		}
	}else{
		totalsum=parseFloat(objsubsum.value);
	}
	document.referForm.totalsum.value=formatCurrency(totalsum);
}

function Check(){
		var pid = document.all("che");
		var checkall = document.all("checkall");
		if (pid==undefined){return;}
		if (pid.length){
			for(i=0;i<pid.length;i++){
					pid[i].checked=checkall.checked;
			}
		}else{
			pid.checked=checkall.checked;
		}		
	}
		
		function Chk(){
		var productid = document.referForm.productid;
		
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}	
		
		if ( !Validator.Validate(document.referForm,2) ){
		return false;
		}
		
		showloading();
		referForm.submit();
		
	}

</script>
</head>

<body style="overflow: auto;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增采购订单</td>
        </tr>
      </table>
      <form name="referForm" method="post" action="addPurchaseBillAction.do">
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
	  	<td width="10%"  align="right">供应商：</td>
          <td width="26%"><input name="pid" type="hidden" id="pid">
            <input name="pname" type="text" id="pname" size="35" readonly dataType="Require" msg="必须录入供应商!"><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td width="9%" align="right">联系人：</td>
          <td width="26%"><input name="plinkman" type="text" id="plinkman" dataType="Require" msg="必须录入联系人!" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
	      <td width="9%" align="right">联系电话：</td>
	      <td width="20%"><input name="tel" type="text" id="tel" dataType="PhoneOrMobile" msg="必须录入正确联系电话!"><span class="STYLE1">*</span></td>
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
	    <td align="right">结算方式：</td>
	    <td>${paymodename}</td>
	  </tr>
	  <tr>
	    <td  align="right">预计到货日期：</td>
	    <td><input name="receivedate" type="text" id="receivedate" onFocus="selectDate(this);" readonly value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
	    <td align="right">收货地址：</td>
	    <td><input name="receiveaddr" type="text" id="receiveaddr" size="35" maxlength="256"></td>
	    <td align="right">发票信息：</td>
	    <td><select name="invmsg">
          <logic:iterate id="c" name="icls">
            <option value="${c.id}">${c.ivname}</option>
          </logic:iterate>
        </select></td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="100%"><table  border="0" cellpadding="0" cellspacing="1">
                  <tr align="center" class="back-blue-light2">
                    <td id="elect"><img src="../images/CN/selectp.gif" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
                  </tr>
                </table></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="title-top">
				<td width="2%"><input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                <td width="11%">产品编号</td>
                <td width="20%" > 产品名称 </td>
                <td width="24%">规格</td>
                <td width="10%"> 单位</td>
                <td width="7%">需求日期</td>
                <td width="7%">相关</td>
                <td width="7%"> 单价</td>
                <td width="10%"> 数量</td>
                <td width="9%"> 金额</td>
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();"></td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="Chk();" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
