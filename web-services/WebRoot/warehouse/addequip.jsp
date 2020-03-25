<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language=javascript>
 

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
	
	var carbrand document.validateProvide.carbrand;
		var CName = document.validateProvide.CName;

		if(CName.value==null||CName.value==""){
			alert("客户名不能为空");
			//totalsum.focus();
			return false;
		}
		if(carbrand.value==null||carbrand.value==""){
			alert("请选择车牌号");
			//totalsum.focus();
			return false;
		}
		
		if ( !Validator.Validate(document.validateProduct,2) ){
			return false;
		}
		showloading();
		return true;
	}
	
</script>
</head>

<body   onkeydown="if(event.keyCode==122){event.keyCode=0;return false}">

<SCRIPT language=javascript>
//
</SCRIPT>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 转配送单</td>
        </tr>
      </table>
      <form name="validateProvide" method="post" action="addEquipAction.do" onSubmit="return ChkValue();">
      <table width="100%" border="0" cellpadding="0" cellspacing="1">
        
		<input type="hidden" name="bids" value="${bids}">
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
          <td width="9%"  align="right"><input name="objectsort" type="hidden" id="objectsort" value="${sb.objectsort}"><input name="cid" type="hidden" id="cid" value="${sb.cid}">
            客户名称：</td>
          <td width="21%"><input name="cname" type="text" id="cname" value="${sb.cname}" readonly></td>
          <td align="right">联系人：</td>
          <td><input name="clinkman" type="text" value="${sb.linkman}" id="clinkman" readonly><a href="javascript:SelectLinkman();"><img src="../images/CN/find.gif" align="absmiddle" border="0"></a></td>
          <td  align="right">联系电话：</td>
          <td><input name="tel" type="text" value="${sb.tel}" id="tel"></td>
        </tr>
        <tr>
          <td  align="right">货运部：</td>
          <td><windrp:select key="Transit" name="transit" p="n|d"/></td>
          <td align="right">司机：</td>
          <td><windrp:users name="motorman"/></td>
          <td align="right">车牌：</td>
          <td><select name="carbrand" id="carbrand">
              <logic:iterate id="u" name="cls">
                <option value="${u.id}">${u.carbrand}</option>
              </logic:iterate>
          </select></td>
        </tr>
        <tr>
          <td height="19" align="right">发运方式：</td>
          <td><windrp:select key="TransportMode" name="transportmode" p="n|d"/></td>
          <td  align="right">件数：</td>
          <td><input name="piece" type="text" id="piece" onKeyUp="clearNoNum(this)" dataType="Double" msg="件数只能是数字!" require="false" maxlength="10"></td>
          <td align="right">配送日期：</td>
          <td><input name="equipdate" type="text" id="equipdate" readonly onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"></td>
        </tr>
        <tr>
          <td align="right">收货地址：</td>
          <td><input name="transportaddr" type="text" value="${sb.receiveaddr}" id="transportaddr" size="50" maxlength="200"></td>
          <td  align="right">托运单号：</td>
          <td><input name="transportnum" type="text" id="transportnum" maxlength="30"></td>
          <td align="right"><!--冲加描述：--></td>
          <td><!--<input name="rushdesc" type="text" id="rushdesc" size="30">--></td>
        </tr>
		<!--<tr>
		  <td  align="right">冲加金额：</td>
		  <td><input name="rushsum" type="text" id="rushsum" ></td>
		  <td  align="right">&nbsp;</td>
		  <td>&nbsp;</td>
		  <td  align="right">&nbsp;</td>
          <td>&nbsp;</td>
        </tr>-->
      </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr> 
				<td width="51%"><table  border="0" cellpadding="0" cellspacing="1">
              <tr align="center" class="back-blue-light2">
               
                
                 
               
              </tr>
            </table></td>
                  <td width="49%" align="right"> <table  border="0" cellpadding="0" cellspacing="1">
                      <tr align="center" class="back-blue-light2"> 
                        
                      </tr>
                  </table></td>
                </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="right">
			<table width="100%" id="dbtable" border="0" cellpadding="0" cellspacing="1">
                <tr align="center" class="title-top">                 
                 <td width="21%" >送货单号</td>
				  <td width="18%" >付款方式</td>
				  <td width="22%">开票信息</td>
				  <td width="22%" >单据金额</td>
                 <!-- <td width="36%">代收金额</td>-->
                </tr>
				 <c:set var="count" value="2"/>
				<logic:iterate id="b" name="bls" >
				<tr class="table-back">
				<td><input name="sbid" type="text" value="${b.id}" id="sbid" size="20" readonly></td>
				<td ><input name="paymentmode" type="hidden" value="${b.paymentmode}"><input name="paymentmodename" type="text" value="${b.paymentmodename}" readonly></td>
				<td><input name="invmsg" type="hidden" id="invmsg" value="${b.invmsg}">
				  <input name="invmsgname" type="text" id="invmsgname" value="${b.invmsgname}" readonly></td>
				<td ><input name="billsum" type="text" value="${b.totalsum}" id="billsum" readonly></td>
				<!--<td ><input name="erasum" type="text"  id="erasum" value="" readonly></td>-->
          </tr>
           <c:set var="count" value="${count+1}"/>
        </logic:iterate>
              </table>  
             <!-- <table width="100%"  border="0" cellpadding="0" cellspacing="0">
                <tr class="table-back"> 
                  <td width="69%">&nbsp;</td>
                  <td width="20%" align="right">代收总金额：</td>
                  <td width="11%">
                    <input name="eratotalsum" type="text" id="eratotalsum" onFocus="TotalSum();" size="10" maxlength="10"></td>
                </tr>
              </table>--></td>
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
