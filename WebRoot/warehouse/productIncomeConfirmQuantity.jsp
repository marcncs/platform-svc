<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="javascript" src="../js/sorttable.js"> </script>
<script language="javascript" src="../js/function.js"> </script>
<script language=javascript src="../js/jquery.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>WINDRP-分销系统</title>
<script language="javascript">
	function clo(){
		if (window.opener){
			window.opener.location.reload();
		}
		window.close();
	}
	 $(function() {
	 	var flag=true;
	 	$(":text").blur(function(e) {
         	$(":text").each(function(e){
         		var quantity = parseFloat($(this).val());
         		if(quantity<=0) {
     			    alert('入库数量必须是大于零');
     			    $(this).val("");
         			flag=false;
         			//flag = true;
         		}else if(quantity>0){
         			flag = true;
         		}else if($(this).val() == ""){
         			alert('入库数量不能为空，请输入数字');
         			flag=false;
         		}else{
         		}
         	});
         });
            $("#sub").click( function(e) {
                if(flag==false) {
            		alert('个别实际数量不正确，请重新输入');
            	} else {
          		   var confirmQuantity =0.0;
                   	$(":text").each(function(e){
                   		var convertq = parseFloat($(this).parent().find(":hidden").val());
                   		//alert(convertq);
                   		var quantity = parseFloat($(this).val()) * parseFloat(convertq);
                   		//alert(quantity);
                   		confirmQuantity+=quantity;
                   		confirmQuantity=parseFloat(confirmQuantity.toFixed(2));
                   	});
            		var piid = document.getElementById("piid").value;
            		$.post("../warehouse/ajaxQuantityAuditProductIncomeAction.do?id="+piid, function(result){
            		   	var data = eval('(' + result + ')');
            		   	var idcodeQuantity= parseFloat(data.idcodeQuantity);
            		   //alert(idcodeQuantity);
            		   //alert(confirmQuantity);
            			if(idcodeQuantity!=confirmQuantity){
            					 if(confirm("单据数量与条码数量不符，是否要入库？")){
            						 clickSubmit(data.id);
            					 }else{
            						 return;
            				 	 }
            		  	}else{
                		  	if(confirm("确定入库吗？")){
                		  		clickSubmit(data.id);
                		  	}
            			}
               		}); 
            	} 
            }); 
        });

	  function clickSubmit(piid)
	   {
		    addrecord.target="";
			addrecord.action="../warehouse/auditProductIncomeAction.do?type=2&PIID="+piid;
			addrecord.submit();
	 }

	  function checkinputnum(obj){
			obj.value = obj.value.replace(/[^\d]/g,"");
		}
	 
        function clearNoNum(obj)
		{
			//先把非数字的都替换掉，除了数字和.
			obj.value = obj.value.replace(/[^\d.-]/g,"");
			//必须保证第一个为数字而不是.
			obj.value = obj.value.replace(/^\./g,"");
			//保证只有出现一个.而没有多个.
			obj.value = obj.value.replace(/\.{2,}/g,".");
			//保证只有出现一个-而没有多个-
			obj.value = obj.value.replace(/\-{2,}/g,"-");
			//保证.只出现一次，而不能出现两次以上
			obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			//保证-只出现一次，而不能出现两次以上
			obj.value = obj.value.replace("-","$#$").replace(/\-/g,"").replace("$#$","-");
			//保证出现-时的合法性
			checkNum(obj.value);
		}
		function checkNum(obj){
			//判断是否存在-
			if(obj.indexOf("-") != -1){
				//判断存在-的字符的第一位是否是-
				if(obj.charAt(0) != "-"){
					alert("非法数量字符，请重新输入!");
					document.getElementById("confirmquantity").value="";
				//如果存在-的字符的第一位是-
				}else{
					//如果紧接着的第二位是.
					if(obj.charAt(1) == "."){
						alert("非法数量字符，请重新输入!");
						document.getElementById("confirmquantity").value="";
					}
				}
			}
		}
</script>
</head>

<body>
<form name="addrecord" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr>
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="8"></td>
    <td width="976"> 产品入库单详情 </td>
  </tr>
</table>
	<fieldset align="center"> <legend>
      <table width="120" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>产品入库单产品列表<input type="hidden" name="piid" id="piid" value="${piid}"/></td>
        </tr>
      </table>
	  </legend>
      <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="sortable">
        <tr align="center" class="title-top">
          <td width="10%">产品编号</td> 
          <td width="20%" >产品名称</td>
          <td width="10%">规格</td>
          <td width="10%">批次</td>
          <td width="10%">单据数量</td>
          <td width="11%">单位</td>
          <td width="20%">确认数量</td>
        </tr>
        <logic:iterate id="p" name="als" > 
        <tr class="table-back-colorbar">
          <td>${p.productid}</td> 
          <td >${p.productname}</td>
          <td>${p.specmode}</td>
          <td>${p.batch}</td>
          <td><windrp:format value="${p.boxQuantity}"/></td>
          <td><windrp:getname key='CountUnit' value='${p.unitid}' p='d'/></td>
          <td align="center"><input type="text" id="confirmquantity" name="confirmquantity" value='<windrp:format value="${p.boxQuantity}"/>' onkeyup="checkinputnum(this)"/>
          <input type ="hidden" value='<fmt:formatNumber value="${p.convertQuantity}" pattern="0.00" />' /> 
          </td>
        </tr>
        </logic:iterate> 
      </table>
	  </fieldset>
</td>
  </tr>
</table>
<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0">
		<tr align="center">
			<td align="center" colspan="5">
				<input type="button" value="提交" id="sub"/> &nbsp;&nbsp;
				<input type="button" value="关闭" onclick="clo();" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>