 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head> 
		<title>WINDRP-选择批次</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/jsFloatArith.js"></script>
		<script type="text/javascript">
		
        $(function() {
            //箱数
        	var index1=0;
        	//散数
        	var index2=0;
        	var flag=false;
        	var sign = false;
        	$("input:text").blur(function(e) {
            	index1=0;
            	index2=0;
            	flag=true;
            	var xtransq;
            	var stransq;
            	$("input[id=boxnum]").each(function(e){
                	//最大实际库存
            		var maxQuantity = parseFloat($(this).parent().prev().prev().find(":hidden").val());
            		//实际箱数
            		var batchQuantity = parseFloat($(this).val());
            		//箱到最小单位转化率
            		xtransq = parseFloat($(this).parent().find(":hidden").val());
            		//实际散数
            		var squ = parseFloat($(this).parent().next().find(":text").val());
            		//散到最小单位转化率
            		stransq =parseFloat($(this).parent().next().find(":hidden").val());
            		var $statusTd = $(this).parent().next().next();
            		var currentQuantity=FloatAdd(FloatMul(batchQuantity,xtransq),FloatMul(squ,stransq));
            		if(batchQuantity<0 || squ<0) {
        			    $statusTd.text('出库数量必须是正数或零');
            			$statusTd.css('color','red');
            			flag=false;
            		} else if(currentQuantity>maxQuantity) {
            			$statusTd.text('当前数量不能出库');
            			$statusTd.css('color','red');
            			flag=false;
            		} else if(currentQuantity<=maxQuantity){
            			$statusTd.text('当前数量可以出库');
            			$statusTd.css('color','green');
            		} else {
            			$statusTd.text('请输入数字');
            			$statusTd.css('color','red');
            			flag=false;
            		}
            		index1+= batchQuantity;
            		index2+= squ;
            		
            	});
            	if(isNaN(index1) || isNaN(index2)) {
					$("#nowNum").text('');
					$("#yetNum1").text('');
					$("#yetNum2").text('');
            	} else {
        		    $("#nowNum").text(index1+"  箱  "+index2+"  散");
        		    var num1=${totalboxQuantity}-index1;
        		    var num2=${totalscatterQuantity}-index2;
        		    if(FloatAdd(FloatMul(num1,xtransq),FloatMul(num2,stransq))==0){
            		    sign  = true;
        		    	$("#yetNum1").text("0  箱  ");
            		     $("#yetNum2").text("0  散");
            		  }else{
            			  sign  = false;
            			  $("#yetNum1").text((${totalboxQuantity}-index1)+"  箱  ");
              		      $("#yetNum2").text((${totalscatterQuantity}-index2)+"  散");
            		  }
            	}
            });
            $("#sub").click( function(e) {
                if(flag==false) {
            		alert('个别批次数量不正确，请重新分配');
            		return;
            	}
            	 if(sign==false){
            		 if(index1<${totalboxQuantity} || index2<${totalscatterQuantity}) {
                 		alert('当前分配数量与实际出库数量不符，请重新分配');
                 	} else if(index1>${totalboxQuantity} || index2>${totalscatterQuantity}) {
                 		alert('当前分配数量与实际出库数量不符，请重新分配');
                 	}
            	}else if(sign==true){
            		if(window.confirm("确定捡配吗?")){
            			showloading();
	            		var url = "../warehouse/redispatchTakeTicketDetailAction.do?allId="+0;
	            		$.post(url,$("#myForm").serializeArray(),function(str){
	            			//var json = eval('(' + str + ')');
	            			alert("提交成功");
	            			window.returnValue = 1;
	            			window.close();
	            		});
            		}else{
            			return false;
            		}
                }else {
            		alert('存在非数字的输入，请重新分配');
            	}
            }); 
        });

            
        /*
        function abc() {
     	        if(index<${totalQuantity}) {
            		alert('存在尚未分配的数量，请重新分配');
            	} else if(index>${totalQuantity}) {
            		alert('当前分配数量超出剩余，请重新分配');
            	} else if(index==${totalQuantity}){
            		alert('1111');
            		$("#myForm").submit();
            		//window.close();
            	} else {
            		alert('存在非数字的输入，请重新分配');
            	}
        }
        */
        //自动初始化检查库存
        
        //var i=0;
        $(function(){
        	var errormsg = "<c:out value='${errormsg}'/>";
        	if(errormsg != ''){
        		alert(errormsg);
        	}
        	
   			$("input:text").each(function(){
				 $(this).blur();
				 return false;
   		     });
   		});
   		
	</script>
	
	</head>

	<body>
	<div style="overflow-y: scroll">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0"
						cellspacing="0" class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="925">
								选择批次
							</td>
							<td width="308" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="120" align="center">
											<a href="#" id="sub">确定检货</a>
											&nbsp;&nbsp;&nbsp;
											<a href="#" onclick="window.close();">关闭</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<td>
								<form id="myForm"
									action="../warehouse/redispatchTakeTicketDetailAction.do"
									method="post">
									<input type="hidden" name="productid" value="${productid}" />
<%--									<input type="hidden" name="xnum" value="${Xnum}" />--%>
									<input type="hidden" value="${ttdbbs}" name="ttdbbs"/>
									<table width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr >
												<td>&nbsp;&nbsp;产品名称：</td>
												<td align="left" colspan="3">
													&nbsp;&nbsp;${productName}
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
											</table>
									<fieldset align="center">
										<legend>
											<table width="50" border="0" cellpadding="0" cellspacing="0">
												<tr>
													<td>
														选择批次
													</td>
												</tr>
											</table>
										</legend>
										
										<div style="height: 400px;overflow: scroll;">
										<table width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr class="title-top">
												<td width="100">
													仓位
												</td>	
												<td width="150">
													批次
												</td>
												<td width="200">
													最大可出库数量
												</td>
												<td width="180" align="center">
													原分配数量
												</td>
												<td width="100" align="center">
													手动调整(箱数)
												</td>
												<td width="100" align="center">
													手动调整(散数)
												</td>
												<td width="250">
													目前状态
												</td>
											</tr>
											<c:forEach var="ttdbb" items="${ttdbbs}">
												<tr class="table-back-colorbar">
													<td>
														${ttdbb.warehouseBit}
													</td>
													<td>
														<input type="hidden" name="batchQuantity" />
														${ttdbb.batch}
														<input type="hidden" name="monthBatch" value="${ttdbb.batch}"/>
													</td>
													<td id="${ttdbb.batch}" align="center">
														${ttdbb.stockboxnum}  (箱) ${ttdbb.stockscatternum}  (散)
														<input type="hidden" name="stockQuantity" value="${ttdbb.stockQuantity}" />
													</td>
													<td align="center">
														${ttdbb.boxnum}  (箱) ${ttdbb.scatternum}  (散)
													</td>
													<td align="center">
														<input id="boxnum" name ="boxnum" type="text" value="${ttdbb.boxnum }"/>
														<input type="hidden" name="xtsQuantity" value="${ttdbb.xtsQuantity}" />
													</td>
													<td align="center">
														<input id="scatternum" name="scatternum" type="text" value="${ttdbb.scatternum}"/>
														<input type="hidden" name="stsQuantity" value="${ttdbb.stsQuantity}" />
													</td>
													<td align="center">
														&nbsp;
													</td>
												</tr>
											</c:forEach>
										</table>
										</div>
									</fieldset>
								</form>
								<br />
								<br />
								<div align="center">
									可分配数量：
									<span id="yetNum1">${totalboxQuantity}  箱</span><span id="yetNum2">${totalscatterQuantity} 散</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已分配数量：
									<span id="nowNum">0</span>
								</div>
							</td>
						</tr>
					</table>
					</td>
					</tr>
					</table>
					</div>
	</body>
</html>