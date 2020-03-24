<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="../js/prototype.js"></script>
<script type="text/javascript" src="../js/function.js"></script>
<script type="text/javascript" src="../js/validator.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="../js/Currency.js"></script>
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
        g.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" onclick='this.select()' onkeydown='onlyNumber(event)' value=\"1\" size=\"8\" maxlength=\"8\" onchange=\"SubTotal("+dbtable.rows.length+");TotalSum();\" onFocus=\"SubTotal("+dbtable.rows.length+");TotalSum();\">";
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
	getLinkman(p.pid);
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
	}
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

<body style="overflow: auto;">
 <form name="referForm" method="post" action="addPurchaseInquireAction.do"  onsubmit="return ChkValue();">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增采购询价记录</td>
        </tr>
      </table>
      <table width="100%" border="0" cellpadding="0"  cellspacing="1">
       
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
	  	<td width="11%"  align="right">询价标题：</td>
          <td width="21%"><input name="inquiretitle" type="text" id="inquiretitle" maxlength="56" dataType="Require" msg="必须录入询价标题!"><span class="STYLE1">*</span></td>
          <td width="11%" align="right">供应商：</td>
          <td width="23%"><input name="pid" type="hidden" id="pid">
            <input name="providename" type="text" id="providename" readonly dataType="Require" msg="必须录入供应商!"><a href="javascript:SelectProvide();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
	      <td width="9%" align="right">联系人：</td>
	      <td width="25%"><input name="plinkman" type="text" id="plinkman" dataType="Require" msg="必须录入联系人!" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
	  </tr>
	  <tr>
	    <td  align="right">采购计划编号：</td>
	    <td><input name="ppid" type="text" id="ppid" value="${purchaseplanid}"></td>
	    <td align="right">有效天数：</td>
	    <td><input name="validdate" type="text" id="validdate" value="3"  maxlength="5" onKeyDown="onlyNumber(event);">/天<span class="STYLE1">*</span></td>
	    <td align="right">&nbsp;</td>
	    <td>&nbsp;</td>
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
			<table width="100%"  id="dbtable" border="0" cellpadding="0" cellspacing="1">
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
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();"></td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10"></td>
              </tr>
            </table></td>
          </tr>
          <tr>
          </tr>
      </table>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td align="right" width="6%"> 备注： </td>
                <td ><textarea name="remark" cols="100" style="width: 100%" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table>
            <table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr align="center"> 
                  <td width="33%"><input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                    <input type="button" name="Submit2" value="取消"  onClick="window.close();"> 
                    </td>
                </tr>
              
            </table>
    </td>
  </tr>
</table>
 </form>
</body>
</html>
