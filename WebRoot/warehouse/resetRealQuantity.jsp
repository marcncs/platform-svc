 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html; charset=utf-8"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>IS-确定实际出库数量</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../js/jquery.js"></script>
		<script type="text/javascript" src="../js/function.js"></script>
		<script type="text/javascript" src="../js/jsFloatArith.js"></script>
        <SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script type="text/javascript">
        $(function() {
        	var index1=0;
        	var index2=0;
        	var kgnum=0;
        	var flag=false;
        	var isDate=false;
        
            $("input:text").blur(function(e) {
            	kgnum=0;
            	index1=0;
            	index2=0;
            	flag=true;
            	$("input[id=realboxnum]").each(function(e){
                	//最大库存数
            		var maxQuantity = parseFloat($(this).parent().prev().prev().find(":hidden").val());
            		//实际箱数
            		var batchQuantity = parseFloat($(this).val());
            		//箱到最小单位转化率
             		var xtransq = parseFloat($(this).parent().find(":hidden").val());
             		//实际散数
             		var squ = parseFloat($(this).parent().next().find(":text").val());
             		//散到最小单位转化率
            		var stransq =parseFloat($(this).parent().next().find(":hidden").val());
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
            		}else if(currentQuantity<=maxQuantity){
            			$statusTd.text('当前数量可以出库');
            			$statusTd.css('color','green');
            		} else {
            			$statusTd.text('请输入数字');
            			$statusTd.css('color','red');
            			flag=false;
            		}
            		index1+= batchQuantity;
            		index2+= squ;
            		kgnum =FloatAdd(kgnum,FloatAdd(FloatMul(batchQuantity,xtransq),FloatMul(squ, stransq)));
            	});
            	
            	if(isNaN(index1)) {
					$("#nowNum").text('');
            	} else {
        		    $("#nowNum").text(index1+"  箱  "+index2+"  散");
            	}
            });
            
            $("#sub").click( function(e) {
                if(flag==false) {
            		alert('个别批次数量不正确，请重新分配');
            		return;
            	} else {
            		var msg = "确定实际出库数量为 "+ index1+" 箱 "+index2 + " 散 吗?";
            		var size = parseFloat($("#idcodesize").text());
            		if(size==0 && index1>0){
    					alert("当前单据条码数量为0,不能复核出库.请先上传条码后再复核.");
    					return;
    				}
    				
            		if(window.confirm(msg)){
            			if(size!=parseFloat(kgnum)){
								if(window.confirm("订单的条码数量与订单数量不符，是否出库？")){
									showloading();
			            			var url = "../warehouse/auditTakeTicketRealQuantity.do";
				            		$.post(url,$("#myForm").serializeArray(),function(){
				            			alert("出库完成");
				            			window.returnValue = 1;
				            			window.close();
				            		});
								}
            			}else{
            				showloading();
	            			var url = "../warehouse/auditTakeTicketRealQuantity.do";
		            		$.post(url,$("#myForm").serializeArray(),function(){
		            			alert("出库完成");
		            			window.returnValue = 1;
		            			window.close();
		            		});
            			}
            			
            		}else{
            			//无处理
            		}
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
        var i=0;
        $(document).ready(function(){
   			$("input:text").each(function(){
				 $(this).blur();
				 return false;
   		     });
   		});
   		
	</script>
	
<style type="text/css">
.style1 {color: #FF0000}
</style>

	</head>

	<body>	
		<div  style="display: none"   id="idcodesize">
		<windrp:format value="${idcodesize}" p="#####0.00" />
		</div>
		
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
								确定出库数量
							</td>
							<td width="308" align="right">
								<table width="120" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="120" align="center">
											<a href="#" id="sub">出库</a>
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
									action="../warehouse/auditTakeTicketRealQuantity.do"	method="post"  >
								
									<input type="hidden" value="${ttid}" name="ttid"/>
									<fieldset align="center">
										<legend>
											<table width="100" border="0" cellpadding="0" cellspacing="0">
												<!--  -->
												<tr>
													<td>
														实际数量清单
													</td>
												</tr>
											</table>
										</legend>
										<div style="height: 400px; overflow: scroll;" >
										<table width="100%" border="0"
											cellpadding="0" cellspacing="1">
											<tr class="title-top">
												<td width="80px">
													产品编号
												</td>	
												<td width="200px">
													产品名称
												</td>	
												<td width="50px">
													仓位
												</td>	
												<td width="80px">
													批次
												</td>
												<td width="130px">
													最大出库量
												</td>
												<td width="130px">
													原分配数量
												</td>
												<!-- 
												<td width="60px" align="center">
													条码<br/>参考数量
												</td>
												 -->
												<td width="60px" >
													实际出库数<br/>(箱数)
												</td>
												<td width="60px" >
													实际出库数<br/>(散数)
												</td>
												<td width="130px">
													目前状态
												</td>
											</tr>
											<c:forEach var="ttdbb" items="${realttdbbs}">
												<tr class="table-back-colorbar">
													<td>
														${ttdbb.productnccode}
														<input type="hidden" name="pid" value="${ttdbb.productid}" />
													</td>
													<td>
														${ttdbb.productname}
													</td>
													<td>
														${ttdbb.warehouseBit}
													</td>
													<td>
														<input type="hidden" name="batchQuantity" />
														${ttdbb.batch}
														<input type="hidden" name="mBatch" value="${ttdbb.batch}" />
													</td>
													<td id="${ttdbb.batch}" align="center">
														${ttdbb.stockboxnum}  (箱) ${ttdbb.stockscatternum}  (散)
														<input type="hidden" name="stockQuantity" value="${ttdbb.stockQuantity}" />
													</td>
													<td align="center">
														${ttdbb.boxnum}  (箱) ${ttdbb.scatternum}  (散)
													</td>
													<td align="center">
														<input id="realboxnum" name ="realboxnum" type="text" value="${ttdbb.realboxnum }"/>
														<input type="hidden" name="xtsQuantity" value="${ttdbb.xtsQuantity}" />
													</td>
													<td align="center">
														<input id="realscatternum" name="realscatternum" type="text" value="${ttdbb.realscatternum}"/>
														<input type="hidden" name="stsQuantity" value="${ttdbb.stsQuantity}" />
													</td>
													<!-- 
													<td align="center">
														${ttdbb.barcodeQuantity}
													</td>
													-->
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
									原出库数量：
									<span id="yetNum">${totalBoxQuantity}  箱  ${totalScatterQuantity }  散</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;实际出库数量：
									<span id="nowNum">0</span>
								</div>
							</td>
						</tr>
					</table>
					</td>
					</tr>
					</table>
	</body>
</html>