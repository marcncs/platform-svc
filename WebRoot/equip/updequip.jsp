<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
function addRow(p){
    var x = document.all("dbtable").insertRow(dbtable.rows.length);
 
        var a=x.insertCell(0);
        var b=x.insertCell(1);
        var c=x.insertCell(2);       
        var d=x.insertCell(3);
        var e=x.insertCell(4);
 
       	a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
		d.className = "table-back";
		e.className = "table-back";
        
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"sbid\" type=\"text\" size=\"20\" value='"+p.sbid+"' readonly>";
		c.innerHTML="<input name=\"paymentmode\" type=\"hidden\" value='"+p.paymentmode+"'><input name=\"paymentmodename\" type=\"text\" size=\"20\" value='"+p.paymentmodename+"' readonly>";
		d.innerHTML="<input name=\"invmsg\" type=\"hidden\" value='"+p.invmsg+"'><input name=\"invmsgname\" type=\"text\" size=\"20\" value='"+p.invmsgname+"' readonly>";
		e.innerHTML="<input name=\"billsum\" type=\"text\" size=\"20\" value='"+p.totalsum+"' readonly>";
        //e.innerHTML="<input name=\"erasum\" type=\"text\" onchange=\"TotalSum();\" size=\"20\" >"; 
}
	

function SupperSelect(){
	var cid=document.validateProvide.cid.value; 
	if(cid==null||cid==""){
		alert("请选择客户名称！");
		return;
	}
	var bill=showModalDialog("toSelectBillAction.do?cid="+cid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if ( bill==undefined){return;}
	for(var i=0;i<bill.length;i++){			
		if ( isready('sbid', bill[i].sbid) ){
			continue;
		}
		addRow(bill[i]);		
	}
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

function getOrganLinkmanBycid(objcid){
        var url = '../sales/ajaxOrganLinkmanAction.do?cid='+objcid;
        var pars = '';
        
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
	function getCustomerLinkmanBycid(objcid){
        var url = '../sales/ajaxCustomerLinkmanAction.do?cid='+objcid;
        var pars = '';
       var myAjax = new Ajax.Request(
                    url,
                    {method: 'get', parameters: pars, onComplete: showLinkman}
                    );	
    }
    
    function getProviderLinkman(v_cid){
		var url = "../sales/ajaxProviderLinkmanAction.do?cid="+v_cid;
		var myAjax = new Ajax.Request(
				url,
				{method: 'get', parameters: '', onComplete: showLinkman}
				);	
	}
    
    
    function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk == undefined ){
			document.validateProvide.clinkman.value='';
			document.validateProvide.tel.value='';
		}else{
			document.validateProvide.clinkman.value=lk.name;
			document.validateProvide.tel.value=lk.mobile;
			if(document.validateProvide.tel.value == ""){
				document.validateProvide.tel.value=lk.officetel;
			}
		}
	}


function SelectCustomer(){
		var os = document.validateProvide.objectsort.value;
		if(os==0){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
			if ( o==undefined){return;}
			document.validateProvide.cid.value=o.id;
			document.validateProvide.cname.value=o.organname;
			getOrganLinkmanBycid(o.id);
		}
		if(os==1){
			var m=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
			if ( m==undefined){return;}
			document.validateProvide.cid.value=m.cid;
			document.validateProvide.cname.value=m.cname;
			getCustomerLinkmanBycid(m.cid);
		}
		if(os==2){
			var p=showModalDialog("../common/selectProviderAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
			if ( p==undefined){return;}
			document.validateProvide.cid.value=p.pid;
			document.validateProvide.cname.value=p.pname;
			getProviderLinkman(p.pid);
		}
	}
	
function SelectLinkman(){
	var os = document.validateProvide.objectsort.value;
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid==""){
		alert("请选择客户！");
		return;
	}
	if(os==0){
		var linkman=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if ( linkman==undefined){return;}
		document.validateProvide.clinkman.value=linkman.lname;
		document.validateProvide.tel.value=linkman.mobile ;
		document.validateProvide.transportaddr.value=linkman.addr ;
	}
	if(os==1){
		var linkman=showModalDialog("../common/selectLinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if ( linkman==undefined){return;}
		document.validateProvide.clinkman.value=linkman.lname;
		document.validateProvide.tel.value=linkman.mobile ;
		document.validateProvide.transportaddr.value=linkman.ltransportaddr ;
	}
	if(os==2){
		var linkman=showModalDialog("../common/selectPlinkmanAction.do?cid="+cid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
		if ( linkman==undefined){return;}
		document.validateProvide.clinkman.value=linkman.lname;
		document.validateProvide.tel.value=linkman.ltel ;
		//document.validateProvide.transportaddr.value=linkman.addr ;
	}
}

function TotalSum(){
	var totalsum=0.00;
	//alert(dbtable.rows.length);
	if((dbtable.rows.length-1) <=1){
		totalsum=totalsum+parseFloat(document.validateProvide.item("erasum").value);
	}else{
		for(var i=0;i<(dbtable.rows.length -1);i++)
		{
		  totalsum=totalsum+parseFloat(document.validateProvide.item("erasum")(i).value);
		}
	}
	var rushsum=0.00;
	if(document.validateProvide.item("rushsum").value.length>0){
		rushsum=parseFloat(document.validateProvide.item("rushsum").value);
	} 
	document.validateProvide.eratotalsum.value=totalsum-rushsum;
}


	function Check(){
		if(document.validateProvide.checkall.checked){
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
		var cid = document.validateProvide.cname;
		var sbid = document.validateProvide.sbid;
		if(cid.value.trim()==""){
			alert("对象名称不能为空");
			cid.focus();
			return false;
		}

		if ( !Validator.Validate(document.validateProduct,2) ){
			return false;
		}
		if ( sbid==undefined){
			alert("请选择送货单");
			return false;
		}
		showloading();
		return true;
	}
</script>

</head>
<body style="overflow:auto">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">修改配送单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="../equip/updEquipAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		<input type="hidden" name="id" value="${sof.id}">
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
          <td width="9%"  align="right">对象类型：</td>
          <td width="21%"><input type="hidden" name="objectsort" value="${sof.objectsort}">
          <windrp:getname key='ObjectSort' value='${sof.objectsort}' p='f'/></td>
          <td width="13%" align="right"><input name="cid" type="hidden" id="cid" value="${sof.cid}">
对象名称：</td>
          <td width="25%"><input name="cname" type="text" id="cname" value="${sof.cname}" readonly><a href="javascript:#;"><img src="../images/CN/find.gif" border="0" align="absmiddle"></a><span class="STYLE1">*</span></td>
          <td width="10%"  align="right">联系人：</td>
          <td width="22%"><input name="clinkman" type="text" id="clinkman" value="${sof.clinkman}" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" align="absmiddle" border="0"></a></td>
        </tr>
        <tr>
          <td  align="right">联系电话：</td>
          <td><input name="tel" type="text" id="tel" maxlength="26" dataType="PhoneOrMobile" msg="联系电话格式不正确!" require="false" value="${sof.tel}"></td>
          <td align="right">货运部：</td>
          <td><windrp:select key="Transit" name="transit" value="${sof.transit}" p="n|d"/></td>
          <td align="right">司机：</td>
          <td><windrp:users name="motorman" value="${sof.motorman}"/></td>
        </tr>
        <tr>
          <td height="19" align="right">车牌：</td>
          <td><select name="carbrand" id="carbrand">
            <logic:iterate id="u" name="cls">
              <option value="${u.id}" ${u.id==sof.carbrand?"selected":""}>${u.carbrand}</option>
            </logic:iterate>
          </select></td>
          <td  align="right">发运方式：</td>
          <td><windrp:select key="TransportMode" name="transportmode" value="${sof.transportmode}" p="n|d"/></td>
          <td align="right">件数：</td>
          <td><input name="piece" type="text" id="piece" value="<windrp:format value='${sof.piece}'/>" onKeyUp="clearNoNum(this)" dataType="Double" msg="件数只能是数字!" require="false" maxlength="10"></td>
        </tr>
        <tr>
          <td align="right">配送日期：</td>
          <td><input name="equipdate" type="text" id="equipdate" readonly onFocus="javascript:selectDate(this)" value="${sof.equipdate}"></td>
          <td  align="right">收货地址：</td>
          <td><input name="transportaddr" type="text" id="transportaddr" size="30" maxlength="200" value="${sof.addr}"></td>
          <td align="right">托运单号：</td>
          <td><input name="transportnum" type="text" id="transportnum" maxlength="30" value="${sof.transportnum}"></td>
        </tr>
		<!--<tr>
		  <td  align="right">冲加描述：</td>
		  <td><input name="rushdesc" type="text" id="rushdesc" maxlength="64" value="${sof.rushdesc}"></td>
		  <td  align="right">冲加金额：</td>
		  <td><input name="rushsum" type="text" id="rushsum" maxlength="10" onKeyUp="clearNoNum(this)" dataType="Double" msg="冲加金额只能是数字!" require="false" value="${sof.rushsum}"></td>
		  <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>-->
      </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
				<td width="51%"><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
               
                
                <td id="elect" ><img src="../images/CN/selectbill.gif" border="0" style="cursor:pointer" onClick="SupperSelect()" ></td>
               
              </tr>
            </table></td>
                  
                </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
                <tr align="center" class="title-top"> 
                  <td width="3%"> 
<input type="checkbox" name="checkall" value="on" onClick="Check();"></td>
                  <td width="27%" >送货单号</td>
				  <td width="21%" >结算方式</td>
				  <td width="21%" >开票信息</td>
				  <td width="28%" >单据金额</td>
                  <!--<td width="36%">应代收金额</td>-->
                </tr>
			<c:set var="count" value="2"/>
            <logic:iterate id="p" name="edls" > 
              <tr class="table-back">
                <td ><input type="checkbox" value="${count}" name="che"></td>
                <td ><input name="sbid" type="text" id="sbid" size="20" value="${p.sbid}" readonly></td>
                <td><input name="paymentmode" type="hidden" id="paymentmode" value="${p.paymentmode}"><input name="paymentmodename" type="text" id="paymentmodename" size="20" value="${p.paymentmodename}" readonly></td>
				<td><input name="invmsg" type="hidden" id="invmsg" value="${p.invmsg}"><input name="invmsgname" type="text" id="invmsgname" size="20" value="${p.invmsgname}" readonly></td>
                <td><input name="billsum" type="text" id="billsum" size="20" value="${p.billsum}" readonly></td>
              </tr>
			  <c:set var="count" value="${count+1}"/>
			</logic:iterate> 
              </table>  
              <table width="100%"  border="0" cellpadding="0" cellspacing="0">
                <tr class="table-back"> 
				  <td width="3%" ><a href="javascript:deleteR();"><img src="../images/CN/del.gif"   border="0"></a></td>	
                  <td width="66%">&nbsp;</td>
                  <td width="20%" align="right"><!--代收总金额：--></td>
                  <td width="11%">
                    <!--<input name="eratotalsum" type="text" id="eratotalsum" size="10" maxlength="10">--></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td  align="center">&nbsp;</td>
          </tr>
          <tr>
            <td  align="center"><input type="submit" name="Submit" value="提交">&nbsp;&nbsp;
            <input type="button" name="Submit2" value="取消" onClick="window.close();"></td>
          </tr>
        
      </table>
      </form>
    </td>
  </tr>
</table>
</body>
</html>
