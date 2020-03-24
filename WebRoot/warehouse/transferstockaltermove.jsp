<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/function.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/validator.js"> </SCRIPT>
<script type="text/javascript" src="../js/prototype.js"></script>
<SCRIPT type="text/javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/showSQ.js"></SCRIPT>
<script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
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
 
        a.innerHTML="<input type='checkbox' value='' name='che' >";
        b.innerHTML="<input name='productid' type='text' id='productid'  size='10' value='"+p.productid+"'>";
        c.innerHTML="<input name='nccode' type='text' id='nccode' size='10' readonly value='"+p.nccode+"'>";
		d.innerHTML="<input name='productname' type='text' id='productname' size='90' readonly value='"+p.productname+"'>";
		e.innerHTML="<input name='specmode' type='text' id='specmode' size='15' readonly value='"+p.specmode+"'>";
        f.innerHTML="<a href='#' onMouseOver=ShowSQ(this,'"+p.productid+"'); ><img src='../images/CN/stock.gif' width='16' border='0'></a>";

		var num ="1";
		g.innerHTML="<input name='num' type='text' id='num' dataType='Integer' msg='必须录入整数' onkeyup='checknum(this)' value='"+num+"' size='8' >";
		h.innerHTML="<input name='unitid' type='hidden' id='unitid'  value='"+p.unitid+"'>" + p.unitidname;
}

function checknum(obj){
	obj.value = obj.value.replace(/[^\d]/g,"");
}

function SupperSelect(rowx){
	var oid=document.referForm.receiveorganid.value;
	var inwid=document.referForm.inwarehouseid.value;
	var outoid=document.referForm.organid.value;
	var outwid=document.referForm.outwarehouseid.value;
	if(!outoid){
		alert("请选择调出机构！");
		return;
	}
	if(!outwid){
		alert("请选择调出仓库！");
		return;
	}
	
	if(oid==null||oid=="")
	{
		alert("请选择调入机构！");
		return;
	}
	if(!inwid){
		alert("请选择调入仓库！");
		return;
	}
	
	
	var p=showModalDialog("../common/toSelectOrganProductPriceAction.do?OID="+oid,null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
	if(p==undefined){return;}
	for(var i=0;i<p.length;i++){	
		if ( isready('productid', p[i].productid) ){
			continue;
		}
		addRow(p[i]);	
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


function SubTotal(rowx){
	var sum=0.00;
	var rowslength=dbtable.rows.length-1;
	if((dbtable.rows.length-1) <=1){
		sum=(document.forms[0].item("unitprice").value)*(document.forms[0].item("quantity").value);
		document.referForm.item("subsum").value=sum;
	}else{
		for(var m=0;m<rowslength;m++){
			sum=(document.forms[0].item("unitprice")(m).value)*(document.forms[0].item("quantity")(m).value);
			document.referForm.item("subsum")(m).value=sum;	
		}
	}
}


function TotalSum(){
	var totalsum=0.00;
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



var transferquantity=1.0;
function getTransferQuantity(unitid,proid){
	var url = '../sales/ajaxFunitAction.do?unitid='+unitid+'&pid='+proid;
    var pars = '';
    var myAjax = new Ajax.Request(
                url,
                {method: 'get', parameters: pars, onComplete: function(originalRequest){
                	var data = eval('(' + originalRequest.responseText + ')');
                	transferquantity = data.trquantity;
                    }
                });	
}

function SplitQuantity(){
	var boxnum=0.00;
	var scatternum=0.00;
	var rowslength=dbtable.rows.length-1;
	
	if((dbtable.rows.length-1) <=1){
		alert(1);
		var unitid = document.forms[0].item("unitid").value;
		var proid = document.forms[0].item("productid").value;
			
		getTransferQuantity(unitid,proid);

		boxnum=(document.forms[0].item("quantity").value)/transferquantity;
		scatternum= (document.forms[0].item("quantity").value)%transferquantity;
		document.referForm.item("boxnum").value=boxnum;
		document.referForm.item("scatternum").value=scatternum;
	}else{
		for(var m=0;m<rowslength;m++){
			var unitid = document.forms[0].item("unitid")(m).value;
			var proid = document.forms[0].item("productid")(m).value;
			
			getTransferQuantity(unitid,proid);
            
			boxnum=(document.forms[0].item("quantity")(m).value)/transferquantity;
			scatternum= (document.forms[0].item("quantity")(m).value)%transferquantity;
			document.referForm.item("boxnum")(m).value=boxnum;
			document.referForm.item("scatternum")(m).value=scatternum;
		}
	}
}

$(function(){
	 $("#dbtable tr td input[id=quantity]").live("focusout",function(){
		    var dom = $(this);
            var unitid = dom.parent("td").parent("tr").find("#unitid").val();
            var proid = dom.parent("td").parent("tr").find("#productid").val();
        	var url = '../sales/ajaxFunitAction.do?unitid='+unitid+'&pid='+proid;
            var pars = '';
            var myAjax = new Ajax.Request(
                        url,
                        {method: 'get', parameters: pars, onComplete: function(originalRequest){
                        	var data = eval('(' + originalRequest.responseText + ')');
                        	var transferquantity = data.trquantity;
                        	
                        	 var quantity = dom.parent("td").parent("tr").find("#quantity").val();
                             var boxnum = parseInt(quantity/transferquantity);
                             var scatternum = quantity%transferquantity;
                            
                             dom.parent("td").parent("tr").find("#boxnum").val(boxnum);
                             dom.parent("td").parent("tr").find("#scatternum").val(scatternum);
                            }
             });	
           
	});
})


	function Check(){

		var checkche  = document.getElementsByName("che");
		var checkall = document.getElementById("checkall");
		for(i=0;i<checkche.length;i++){
			checkche[i].checked = checkall.checked;
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
    
    function showLinkman(originalRequest)
    {
		var data = eval('(' + originalRequest.responseText + ')');
		var lk = data.linkman;
		if ( lk == undefined ){
			document.referForm.olinkman.value='';
			document.referForm.otel.value='';
		}else{
			document.referForm.olinkman.value=lk.name;
			document.referForm.otel.value=lk.mobile;
			if(document.referForm.otel.value == ""){
				document.referForm.otel.value=lk.officetel;
			}
		}
	}
	
	function SelectOrgan(){
		var p=showModalDialog("../common/selectOrganAction.do?type=vw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.receiveorganid.value=p.id;
			document.referForm.oname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.wname.value="";
			getOrganLinkmanBycid(p.id);
			
	}
  function SelectOrgan2(){
		var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if ( p==undefined){return;}
			document.referForm.organid.value=p.id;
			document.referForm.orgname.value=p.organname;
			//document.referForm.transportaddr.value=p.oaddr;
			document.referForm.owname.value="";
		//	getOrganLinkmanBycid(p.id);
			
	}
	
	function SelectLinkman(){
		var receiveorganid=document.referForm.receiveorganid.value;
		if(receiveorganid==null||receiveorganid=="")
		{
			alert("请选择调入机构！");
			return;
		}
	
			var lk=showModalDialog("../common/selectOrganLinkmanAction.do?cid="+receiveorganid,null,"dialogWidth:16cm;dialogHeight:8.5cm;center:yes;status:no;scrolling:no;");
			document.referForm.olinkman.value=lk.lname;
			var tel = lk.ltel;
			if(tel==""){
				tel=lk.mobile;
			}
			document.referForm.otel.value=tel;
		
	}
	
	function ChkValue(){
		var nums = document.all.item("num");
		
		if($('#organid').val()==""){
			alert("必须录入调出机构!");
			return false;
		}
		if( $('#outwarehouseid').val() ==""){
			alert("必须录入调出仓库!");
			return false;
		}
		if ( !Validator.Validate(document.referForm,2) ){
		return false;
		}		
		if($('#receiveorganid').val()==""){
			alert("必须录入调入机构!");
			return false;
		}
		if( $('#inwarehouseid').val() ==""){
			alert("必须录入调入仓库!");
			return false;
		}
		showloading();
		return true;
	}
	
	function selectW(dom,type){
		var id = $('#organid').val();
		selectDUW(dom,'outwarehouseid',id,type,'');
	}
	function selectWIn(dom){
		var id = $('#receiveorganid').val();
		selectDUW(dom,'inwarehouseid',id,'w','transportaddr')
	}
	function selectWIn2(dom){
		var id = $('#oldreceiveorganid').val();
		selectDUW(dom,'oldinwarehouseid',id,'w2','')
	}

</script>

</head>
<body style="overflow: auto;">
<form name="referForm" method="post" action="transferStockAlterMoveReceiveAction.do" onSubmit="return ChkValue();">
<input type="hidden" name="samid" value="<%=request.getParameter("samid") %>">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">

  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td > 转移订购单</td>
        </tr>
      </table>
	  
	  <fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="10%"  align="right">订购日期：</td>
        <td width="25%"><input name="movedate" type="text" id="movedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly="readonly"></td>
	    <td  align="right">调入机构：</td>
	    <td>
	    <input name="receiveorganid" type="hidden" id="receiveorganid">
		<input name="oname" type="text" id="oname" size="30" dataType="Require" msg="必须录入调入机构!" readonly><a href="javascript:SelectOrgan();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
		<span class="style1">*</span>
	    </td>
	    <td align="right">调入仓库：</td>
	    <td><input type="hidden" name="inwarehouseid" id="inwarehouseid" >
		<input type="text" name="wname" id="wname" onClick="selectWIn(this)" value="" readonly>
		<span class="style1">*</span>
		</td>
	  </tr>
	  <tr>
	  <td  align="right">联系人：</td>
	    <td>
	    	<input type="text" id="olinkman" name="olinkman" dataType="Require" msg="必须录入联系人!"><a href="javascript:SelectLinkman();"><img
					src="../images/CN/find.gif" width="19" height="18"
					align="absmiddle" border="0"> </a>
	    </td>
	    <td  align="right">联系电话：</td>
	    <td>
	    	<input type="text" id="otel" name="otel" dataType="PhoneOrMobile" msg="必须录入正确联系电话!" require="false">
	    </td>
	 <td align="right">收货地址：</td>
	    <td colspan="3"><input name="transportaddr" type="text" id="transportaddr" size="50" maxlength="100" dataType="Require" msg="必须录入收货地址!"><span class="style1">*</span></td>
	    
	  </tr>
	  <c:if test="${isSeedCustomer == 1}">
	  <tr>
	  	<input type="hidden" name="oldreceiveorganid" id="oldreceiveorganid" value="${smf.receiveorganid}" />
	    <td align="right" width="9%">原单据调入仓库：</td>
	    <td colspan="6"><input type="hidden" name="oldinwarehouseid" id="oldinwarehouseid" value="${smf.inwarehouseid}" dataType="Require" msg="该单据是'种子'客户单据，整单转移前先请选择'种子'客户仓库签收!"/>
		<input type="text" name="oldwname" id="oldwname" onClick="selectWIn2(this)"  value="<windrp:getname key='warehouse' value='${smf.inwarehouseid}' p='d'/>" readonly>
		<span class="style1">*<c:if test="${isSeedCustomer == 1}">该单据是'种子'客户单据，请选择'种子'客户仓库签收。若无'种子'客户仓库，请勿处理该单据，可向客服反应该情况</c:if></span>
		</td>
	  </tr>
	  </c:if>
	  <tr>
		<td align="right">
			转移原因：
		</td>
		<td colspan="5">
		<textarea name="movecause" cols="100" style="width: 100%" rows="3"
		id="movecause" dataType="Limit" max="256" msg="调拨原因必须在256个字之内"
			require="false"></textarea><br>
		<span class="td-blankout">(转移原因不能超过256个字符!)</span>
			
		</td>
	</tr>
	  </table>
	</fieldset>
      <table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center"><input type="submit" name="Submit"  value="提交">            &nbsp;&nbsp;
          <input type="button" name="button" value="取消" onClick="window.close();">          </td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
