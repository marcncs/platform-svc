<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>

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
		
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
        e.className = "table-back";
        f.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"productid\" type=\"text\" id=\"productid\" value='"+p.productid+"' size=\"16\" readonly>";
		c.innerHTML="<input name=\"productname\" type=\"text\" id=\"productname\" value='"+p.productname+"' size=\"40\" readonly>";
		d.innerHTML="<input name=\"specmode\" type=\"text\" id=\"specmode\" value='"+p.specmode+"' size=\"40\" readonly>";
        e.innerHTML="<input name=\"unitid\" type=\"hidden\" id=\"unitid\" value='"+p.unitid+"' size=\"6\"><input name=\"unit\" type=\"text\" id=\"unit\" value='"+p.unitidname+"' size=\"8\" readonly>";
        f.innerHTML="<input name=\"quantity\" type=\"text\" id=\"quantity\" value=\"1\" size=\"8\" maxlength=\"8\" onKeyPress=\"KeyPress(this)\" dataType=\"Double\" msg=\"数量只能是数字!\" require=\"false\" >";
}

function SupperSelect(){
	var wid=document.validateProvide.warehouseid.value;
	var cid=document.validateProvide.cid.value;
	if(wid==null||wid=="")
	{
		alert("请选择出货仓库！");
		return;
	}		
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}	
	var p = showModalDialog("../aftersale/toSelectSaleTradesProductAction.do?wid="+wid+"&cid="+cid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}	
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);			
	}
	
}

function getCustomerByWhere(objvalue, temp){
		if (event.keyCode != 13) { 			
			return;
		} 
		var url ="";
		if ( temp == 1 ){
			 url = '../sales/ajaxCustomerAction.do?temp=1&cid='+objvalue;
		}else if ( temp == 2 ){
			 url = '../sales/ajaxCustomerAction.do?temp=2&cname='+objvalue;
		}else if ( temp == 3 ){
			 url = '../sales/ajaxCustomerAction.do?temp=3&mobile='+objvalue;
		}else if ( temp == 4 ){
			 url = '../sales/ajaxCustomerAction.do?temp=4&officetel='+objvalue;
		}else{
			return;
		}
        var pars = '';
       	var myAjax = new Ajax.Request(
                    url,
                    {method: 'post', parameters: pars, onComplete: showCustomer}
                    );	
	}

function showCustomer(originalRequest){
		var data = eval('(' + originalRequest.responseText + ')');
		var cur = data.customer;
		var lk = data.linkman;
		var sizes = parseInt(data.sizes);	
		var temp = parseInt(data.temp);		
		if ( sizes > 1 ){
			var url ="";
			if ( temp ==1 ){
				url ="../sales/toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cid");				
			}else if ( temp ==2 ){
				url ="../sales/toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cname");
			}else if ( temp ==3 ){
				url ="../sales/toSelectSaleOrderCustomerAction.do?KeyWord="+$F("cmobile");
			}else if ( temp ==4 ){
				url ="../sales/toSelectSaleOrderCustomerAction.do?KeyWord="+$F("decidemantel");
			}else{
				return;
			}
			var c=showModalDialog(url,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");			
			setDefaultValue(c.cid, c.cname, '', c.mobile, c.officetel);	
			getLinkmanBycid(c.cid);
			getReceivemanBycid(c.cid);
			return;
		}		
		if ( cur == undefined ){
			setDefaultValue('', '', '', '', '');	
		}else{
			setDefaultValue(cur.cid,cur.cname,lk.name,cur.mobile,cur.officetel);			
		}
	}
	function setDefaultValue(cid, cname, decideman, mobile, officetel){
		document.validateProvide.cid.value=cid;
		document.validateProvide.cname.value=cname;
		document.validateProvide.decideman.value=decideman;
		document.validateProvide.cmobile.value=mobile;
		document.validateProvide.tel.value=officetel;

	}


function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.cmobile.value=c.mobile;
	document.validateProvide.tel.value=c.officetel;

	getLinkmanBycid(c.cid);
}

function SelectLinkman(){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	var lk=showModalDialog("../common/toSelectLinkmanAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	document.validateProvide.decideman.value=lk.lname;
	//document.validateProvide.tel.value=lk.ltel;
	//document.validateProvide.transportaddr.value=lk.ltransportaddr;	
	//setSelectValue('transit',lk.transit);
}

function getLinkmanBycid(objcid){
	var url = '../sales/ajaxCustomerLinkmanAction.do?cid='+objcid;
	var pars = '';
	var myAjax = new Ajax.Request(
		url,
		{method: 'get', parameters: pars, onComplete: showLinkman}
		);	
}

function showLinkman(originalRequest)
{
	var data = eval('(' + originalRequest.responseText + ')');
	var lk = data.linkman;
	if ( lk == undefined ){
		document.validateProvide.decideman.value='';
	}else{
		document.validateProvide.decideman.value=lk.name;
	}
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

	function ChkValue(){
        var cname = document.validateProvide.cname;
		var tradesdate=document.validateProvide.tradesdate;
		var productid = document.validateProvide.productid;
		
		if(cname.value==null||cname.value==""){
			alert("会员不能为空!");
			return false;
		}
		if(tradesdate.value==null||tradesdate.value==""){
			alert("预计取货日期不能为空!");
			return false;
		}
		
		if(productid==undefined){
			alert("请选择产品！");
			return false;
		}
		
		if ( !Validator.Validate(document.validateProvide,2) ){
		return false;
		}

		showloading();
		
		validateProvide.submit();
		//return true;
	}
</script>
</head>

<body style="overflow:auto">
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增零售换货</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="../aftersale/addSaleTradesAction.do">
        
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
	  	<td width="9%"  align="right">会员编号：</td>
          <td width="21%"><input name="cid" type="text" id="cid" onKeyUp="getCustomerByWhere(this.value,1)" maxlength="32"><span class="STYLE1">*</span></td>
          <td width="13%" align="right">会员名称：</td>
          <td width="23%"><input name="cname" type="text" id="cname" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td align="right">联系人：</td>
          <td><input name="decideman" type="text" id="decideman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a></td>
	  </tr>
	  <tr>
	    <td align="right">会员手机：</td>
	    <td><input name="cmobile" type="text" id="cmobile" onKeyUp="getCustomerByWhere(this.value,3)" maxlength="11"></td>
	    <td  align="right">联系电话：</td>
	    <td><input name="tel" type="text" id="tel" maxlength="26"></td>
	    <td align="right">换货类型：</td>
	    <td><windrp:select key="TradesSort" name="tradessort" p="n|f"/></td>
	    </tr>
	  <tr>
	    
	    <td align="right">入货仓库：</td>
	    <td><windrp:warehouse name="warehouseinid"/></td>
		<td  align="right">出货仓库：</td>
	    <td><windrp:warehouse name="warehouseid"/></td>
	    <td align="right">预计取货日期：</td>
	    <td><input name="tradesdate" type="text" id="tradesdate" readonly onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>">
	      <span class="STYLE1">*</span></td>
	    </tr>
	  </table>
</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                      <td id="elect" > <img src="../images/CN/selectp.gif" width="72" height="21" border="0" style="cursor:pointer" onClick="SupperSelect()" ></td>
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
                <td width="19%">产品编号</td>
                <td width="22%" > 产品名称</td>
                <td width="20%">规格型号</td>
                <td width="13%"> 单位</td>
                <td width="14%"> 数量</td>				
                </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
               <td width="64%" align="right"> <!--<input type="button" name="button" value="金额小计" onClick="TotalSum();">：--></td>
                <td width="15%"><!--<input name="totalsum" type="text" id="totalsum" size="10" maxlength="10">--></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="remark" cols="180" rows="4" id="remark" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center"><input type="button" name="Submit" onClick="ChkValue();" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
