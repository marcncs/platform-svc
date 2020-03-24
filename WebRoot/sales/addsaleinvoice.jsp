<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT type=text/javascript src="../js/selectDate.js"></SCRIPT>
<script src="../js/prototype.js"></script>
<SCRIPT language="javascript" src="../js/selectctitle.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/validator.js"> </SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>

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
 
        a.className = "table-back";
        b.className = "table-back";
        c.className = "table-back";
        d.className = "table-back";
 
        a.innerHTML="<input type=\"checkbox\" value=\"\" name=\"che\">";
        b.innerHTML="<input name=\"soid\" type=\"text\" id=\"soid\" size=\"12\" readonly>";
		c.innerHTML="<input name=\"subsum\" type=\"text\" id=\"subsum\" size=\"10\" dataType=\"Double\" msg=\"金额只能是数字!\" require=\"false\" readonly>";
		d.innerHTML="<input name=\"makedate\" type=\"text\" id=\"makedate\" size=\"12\" >";
        
		i++;
    chebox=document.all("che");  //計算total值

 	//SubTotal(dbtable.rows);
	//TotalSum();
}
	

function SupperSelect(rowx){
	var cid=document.validateProvide.cid.value;
	if(cid==null||cid=="")
	{
		alert("请选择客户！");
		return;
	}
	var p=showModalDialog("toSelectSaleOrderAction.do?cid="+cid,null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrolling:no;");
	
	var arrid=p.soid;
	var arrtotalsum=p.totalsum;
	var arrmakedate = p.makedate;

	if((dbtable.rows.length-1) <1){
		for(var i=0;i<arrid.length;i++){
				addRow();
				if(i==0){//第一次加时还不是数组
					document.validateProvide.item("soid").value =arrid[0];
					document.validateProvide.item("subsum").value =arrtotalsum[0];
					document.validateProvide.item("makedate").value=arrmakedate[0];
					
					}else{
						document.validateProvide.item("soid")[i].value =arrid[i];
						document.validateProvide.item("subsum")[i].value =arrtotalsum[i];
						document.validateProvide.item("makedate")[i].value=arrmakedate[i];
						
					}
			}
			
			//SubTotal(rowx);
			TotalSum();
	}else{
		for(var i=0;i<arrid.length;i++){
			addRow();
			document.validateProvide.item("soid")[rowx-1+i].value =arrid[i];//加i是从已经有的row行起算从0行开始
			document.validateProvide.item("subsum")[rowx-1+i].value =arrtotalsum[i];
			document.validateProvide.item("makedate")[rowx-1+i].value=arrmakedate[i];

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

function SelectCustomer(){
	var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:16cm;center:yes;status:no;scrollbars=yes;");
	if(c==undefined){return;}
	document.validateProvide.cid.value=c.cid;
	document.validateProvide.cname.value=c.cname;
	document.validateProvide.sendaddr.value=c.detailaddr;
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
		var invoicecode = document.validateProvide.invoicecode;
		var makeinvoicedate = document.validateProvide.makeinvoicedate;
		var invoicedate = document.validateProvide.invoicedate;
		
		if(cname.value==null||cname.value==""){
			alert("会员名称不能为空!");
			return false;
		}
		
		if(invoicecode.value==null||invoicecode.value==""){
			alert("发票编号不能为空!");
			return false;
		}
		if(makeinvoicedate.value==null||makeinvoicedate.value==""){
			alert("制票日期不能为空!");
			return false;
		}
		
		if(invoicedate.value==null||invoicedate.value==""){
			alert("开票日期不能为空!");
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

<body style="overflow:auto">

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772"> 新增零售发票</td>
        </tr>
      </table>
       <form name="validateProvide" method="post" action="addSaleInvoiceAction.do" onSubmit="return ChkValue();">
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
	  	<td width="9%"  align="right"><input name="cid" type="hidden" id="cid" >
会员名称：</td>
          <td width="26%"><input name="cname" type="text" id="cname" readonly><a href="javascript:SelectCustomer();"><img src="../images/CN/find.gif" width="18" height="18" border="0" align="absmiddle"></a>
          <span class="STYLE1">*</span></td>
          <td width="9%" align="right">发票编号：</td>
          <td width="22%"><input name="invoicecode" type="text" id="invoicecode">
          <span class="STYLE1">*</span></td>
	      <td width="9%" align="right">发票类型：</td>
	      <td width="25%"><windrp:select key="InvoiceType" name="invoicetype" p="n|f"/></td>
	  </tr>
	  <tr>
	    <td  align="right">制票日期：</td>
	    <td><input name="makeinvoicedate" type="text" id="makeinvoicedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly>
        <span class="STYLE1">*</span></td>
	    <td align="right">开票日期：</td>
	    <td><input name="invoicedate" type="text" id="invoicedate" onFocus="javascript:selectDate(this)" value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>" readonly>
        <span class="STYLE1">*</span></td>
	    <td align="right">发票抬头：</td>
	    <td><input name="invoicetitle" type="text" id="invoicetitle"
													size="35" onClick="selectCTitle(this, $F('cid'))"></td>
	  </tr>
	  <tr>
	    <td  align="right">寄票地址：</td>
	    <td colspan="5"><input name="sendaddr" type="text" id="sendaddr"
													size="60" onClick="selectCTitle(this, $F('cid'),'1')">
												<br/>
												</td>
	    </tr>
	  </table>
	</fieldset>
			
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
                <tr > 
                  <td width="100%" ><table  border="0" cellpadding="0" cellspacing="1">
                    <tr align="center" class="back-blue-light2">
                          <td id="elect"><img src="../images/CN/selectbill.gif" width="72" height="21" border="0" style="cursor:pointer" onClick="SupperSelect(dbtable.rows.length)" ></td>
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
                <td width="31%">零售单号</td>
                <td width="33%" >零售单金额</td>
                <td width="33%">制单日期</td>
              </tr>
            </table>
			<table width="100%"   border="0" cellpadding="0" cellspacing="0">
              <tr align="center" class="table-back">
                <td width="2%" ><a href="javascript:deleteR('che','dbtable');"><img src="../images/CN/del.gif"   border="0"></a></td>
                <td width="11%">&nbsp;</td>
                <td width="7%">&nbsp;</td>
                <td width="64%" align="right"><input type="button" name="button" value="金额小计" onClick="TotalSum();">：</td>
                <td width="15%"><input name="totalsum" type="text" id="totalsum" size="10" maxlength="10" readonly></td>
              </tr>
            </table></td>
          </tr>
          <tr>
            <td  align="center" ><table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr >
                <td width="6%" height="77" align="right"> 备注： </td>
                <td width="94%"><textarea name="memo" cols="180" rows="4" id="memo" dataType="Limit" max="256"  msg="备注必须在256个字之内" require="false"></textarea><br><span class="td-blankout">(备注长度不能超过256字符)</span></td>
              </tr>
            </table></td>
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
