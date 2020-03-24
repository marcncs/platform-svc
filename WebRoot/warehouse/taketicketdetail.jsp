<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ page import="org.apache.struts.Globals"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<title>${menu }详情</title> 
<script src="../js/function.js"></script>
<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/showCQ.js"> </SCRIPT>
<script language="javascript">
	function Audit(piid){
		showloading();
		popWin4("../warehouse/toAuditTakeTicketAction.do?id="+piid,1000,600,"newidcode");
	}
	function CancelAudit(piid){
		popWin("../warehouse/cancelAuditTakeTicketAction.do?id="+piid+"&org.apache.struts.taglib.html.TOKEN="+token);
	}
	function toaddbit(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddTakeTicketIdcodeiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,600,"newidcode");
	}
	function toaddidcode(billid,bdid,wid,isaudit){
			popWin4("../warehouse/toAddTakeTicketIdcodeiiAction.do?billid="+billid+"&bdid="+bdid+"&wid="+wid+"&isaudit="+isaudit,1000,650,"newidcode");
	}	
	function blankout(wid){
		if(window.confirm("您确认要作废该记录吗？如果作废将永远不能恢复!")){
			popWin2("../warehouse/blankoutTakeTicketAction.do?id="+wid);
			}
	}
	function PrintTakeTicket(id){
		popWin4("../warehouse/printTakeTicketAction.do?ID="+id,900,600,"print");
	}
	/*
	//验证库存量
  	function toAudit(id){
	  	$.post("../warehouse/ajaxAuditTakeTicketAction.do?id="+id, function(result){
		   	var data = eval('(' + result + ')');
			if(data.state=="overQuantity"){
				if(data.tttype!=2){
					alert("库存不足不能出库");
					return;
				}else if(data.warehouseType!=3){
					alert("库存不足不能出库");
					return;
				}else{
					var con=confirm("是否要负库存出货？");
					if(con==true){
						//确定负库存出货并继续验证出库数量
						toQuantityAudit(data.id);
					}
					if(con==false){
						return;
					}
				}
			}else{
				//确定出货并继续验证出库数量
				toQuantityAudit(data.id);
			}
	  	}); 
	}
	*/
	/*
	//验证条码数量与订单数量是否一致,wei.li 2012-2-16 ADD
	function toQuantityAudit(id){
		$.post("../warehouse/ajaxQuantityAuditTakeTicketAction.do?id="+id, function(result){
		   	var data = eval('(' + result + ')');
			if(data.state=="1"){
				var con=confirm("条码数量与订单数量不符，是否强制出货？");
				//确定无码出货
				if(con==true){
					Audit(data.id);
				}
				if(con==false){
					return;
				}
			}else{
				var con=confirm("是否正常出货？");
				//确定正常出货
				if(con==true){
					Audit(data.id);
				}
				if(con==false){
					return;
				}
			}
	  	}); 
	}
*/
	 function toAudit(id,billno){
		Audit(id);
	}
	
	//退货
	function AuditOW(id){
		Audit(id);
	}
		
		
	function AuditOM(id){
		//判断是否选择采集器
			if(!$("#scannerNo").val()){
				alert("请选择采集器编号!");
				return ;
		    }
		    //判断是否完全检货		    
		 	var url = "../warehouse/checkTakeTicketDetailPick.do";
			$.post(url,function(str){
				var json = eval('(' + str + ')');
					if(json.result == 1){
						showloading();
						var url = "toResetRealQuantityAction.do?ttid="+id; 
						var returnVal = showModalDialog(url,"newwindow","dialogWidth:1000px;dialogHeight:550px;center:yes;states:no");
						//var returnVal = popWin4(url,"newwindow","dialogWidth:1000px;dialogHeight:550px;center:yes;states:no");
						//if(returnVal == "1"){
							//因增加了showloading 每次关闭也要刷新
							window.location.href = window.location.href;
						//}
					}else if(json.result == 0){
						alert("请先完成检货再复核!");	 
				    }else if(json.result == 2){
						alert("请完成验货后再复核");
					}
			},"JSON");
	}	

	// 选择批次数量
	function selectBatchQuantity(warehouseid,ttdid, productid, quantity,unitid,type) {
		showloading();
		var returnVal = showModalDialog("ProductBatchQuantityAction.do?productid="+productid+"&warehouseid="+warehouseid+"&ttdid="+ttdid+"&quantity="+quantity+"&type="+type+"&unitid="+unitid,
			"newwindow","dialogWidth:800px;dialogHeight:600px;center:yes;status:no;");
		//if(returnVal == "1"){
			window.location.href = window.location.href;
		//}
	}
	function cancalPick(warehouseid,ttdid, productid, quantity){
		var msg = "确定要取消检货吗?";
		if(window.confirm(msg)){
			showloading();
			var url = "cancalTakeTicketDetailPicked.do?productid="+productid+"&warehouseid="+warehouseid+"&ttdid="+ttdid+"&quantity="+quantity;
			$.post(url,function(str){
				var json = eval('(' + str + ')');
				if(json.result == 1){
					alert("取消成功!");
					window.location.href = window.location.href;
				}else{
					alert("取消失败!");
					window.location.href = window.location.href;
				}
			},"JSON");
		}else{
			
		}
	}
	function pickExcput(ttid){
		var scannerNo = $("#scannerNo").val();
		//判断是否选择采集器
		if(${sof.bsort != 7}){
			if(!scannerNo){
				alert("请选择采集器编号!");
				return ;
	        }
		}
	 	//判断是否完全检货
	 	var url = "../warehouse/checkTakeTicketDetailPick.do";
		$.post(url,function(str){
			var json = eval('(' + str + ')');
			if(json.result == 1 || json.result == 2){
				popWin2("../warehouse/excPutPickTakeBillAction.do?ttid="+ttid+"&type="+1+"&scannerNo=" + scannerNo);
			}else{
				alert("请先完成检货再生成提货清单!");
			}
		},"JSON");
		
	 }
	//异步设置采集器编号
	 function setScannerNo(){
		 var scannerNoVal = $("#scannerNo").val();
		 if(!scannerNoVal){
			alert("请选择采集器编号!");
			return;
		 }
		var url = "../warehouse/setTakeTicketScannerNoAction.do";
		var ttidVal = "${sof.id}";
		var msg = "确定将单据[" + ttidVal + "]指定采集器[" + scannerNoVal + "],指定后不可修改!";
		if(window.confirm(msg)){
			 $.post(url,{ttid:ttidVal,scannerNo:scannerNoVal},function(data){
				   alert(data.msg);
				 },"json");
		}
	 }
	//单据拣货操作  1:检货中 2:检货完成
		function pickedBillNo(ttid,type){
			var url = "../warehouse/pickBillAction.do";
			var paramsStr = "{billNo : '" + ttid + "',type:'" + type + "'}";
			var params = eval("(" + paramsStr + ")");
			var msg = "确定要取消检货吗?";
			if(type == 2){
				msg = "单据[" + ttid +  "]完成拣货?";
			}
			if(type == 1){
				msg = "单据[" + ttid +  "]取消拣货?";
			}
			if(window.confirm(msg)){
				$.post(url,params,function(data){
					var dataJson = getJsonFromMsg(data)
					if(dataJson.returnCode == 0){
						if(type == 2){
							alert("单据拣货成功!");
						}
						if(type == 1){
							alert("单据取消拣货成功!");
						}
					}
					if(dataJson.returnCode == 1){
						alert(dataJson.returnMsg);
					}
					window.location.href = window.location.href;
				});
			}
		}
		
		//单据验货操作  1:验货完成 2:取消验货
		function checkedBillNo(ttid,type){
			var url = "../warehouse/checkBillAction.do";
			var paramsStr = "{billNo : '" + ttid + "',type:'" + type + "',op:'1'}";
			var params = eval("(" + paramsStr + ")");
			var msg = "确定要取消检货吗?";
			if(type == 1){
				msg = "单据[" + ttid +  "]完成验货?";
			}
			if(type == 0){
				msg = "单据[" + ttid +  "]取消验货?";
			}
			if(window.confirm(msg)){
				$.post(url,params,function(data){
					var dataJson = getJsonFromMsg(data)
					if(dataJson.returnCode == 0){
						if(type == 1){
							alert("单据验货成功!");
						}
						if(type == 0){
							alert("单据取消验货成功!");
						}
					}
					if(dataJson.returnCode == 1){
						alert(dataJson.returnMsg);
					}
					window.location.href = window.location.href;
				});
			}
		}
	/*
	  将returnCode:code
	   returnMsg:msg
	    转化为json数据
	*/
	 function getJsonFromMsg(data){
		 var str = (data.replace(/\r\n/g,'","').replace(/\=/g,'":"'));
		 str = str.substr(0,str.length-2);
		 str = '{"' + str +'}';
		 var json = eval('(' + str + ')');
		 return json;
	 }
	 function excput(){
		 document.getElementById("excput").target="";
		 document.getElementById("excput").submit();
		}
			 function excSAPput(){
		 document.getElementById("excSAPput").target="";
		 document.getElementById("excSAPput").submit();
		}
		function Import2(billid,prid) {
		window
				.open(
						"../warehouse/toImportLInTakeBillAction.do?billid="+billid+"&prid="+prid,
						"",
						"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

	}
		
		function ImportSapIdcode(billid,prid) {
			window
					.open(
							"../warehouse/toImportSapIdcodeAction.do?billid="+billid+"&prid="+prid,
							"",
							"height=300,width=500,top=190,left=190,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");

		}

</script>
	 <%-- 
	 //页面加载时判断采集器是否选择
	 $(function(){
	 	 if(${sof.bsort != 7}){
	 	 	if(!$("#scannerNo").val()){
				alert("请选择采集器编号!");
		    }
	 	 }
	 })
	 --%>
</script>
	</head>
	<body style="overflow: auto;">
		<form id="excput" name="excput" method="post"
							action="../warehouse/excPutTakeBillAction.do">
		    <input type="hidden" name="billno" value="${sof.billno}">
		</form>
		<form id="excSAPput" name="excSAPput" method="post"
							action="../warehouse/sapLInTakeBillAction.do">
		    <input type="hidden" name="billno" value="${sof.billno}">
		</form>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
						class="title-back">
						<tr>
							<td width="10">
								<img src="../images/CN/spc.gif" width="10" height="8">
							</td>
							<td width="925">
								${menu }详情
							</td>
							<td width="558" align="right">
								<table width="550" border="0" cellpadding="0" cellspacing="0">
									<c:if test="${type == '2'}">
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>
									</c:if>
									<c:if test="${type != '2'}">
										<tr>
											<ws:hasAuth operationName="/warehouse/auditTakeTicketAction.do">
												<td width="100" align="left">
													<c:choose>
														<c:when test="${sof.isaudit==0}">
														<input type="button" name="toaudit" id="toaudit" value="${flag eq 'PW' ? '退货完成' : '出库确认'}"
															onclick="javascript:toAudit('${sof.id}','${sof.billno}');" />
														</c:when>
													</c:choose>
												</td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/warehouse/excPutTakeBillAction.do">
											    <td width="80">
													<a href="javascript:excput();"><img
														src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
														align="absmiddle">&nbsp;导出条码</a>
												</td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/warehouse/toImportLInTakeBillAction.do">
											    <td width="100">
													<a href="javascript:Import2('${sof.id}','');"><img src="../images/CN/import.gif"
													width="16" height="16" border="0" align="absmiddle">&nbsp;toller条码导入</a></td>
												</td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/warehouse/printTakeTicketAction.do">
											<td width="60" align="center"><a href="javascript:PrintTakeTicket('${sof.id}');"><img
														src="../images/CN/print.gif" width="16" height="16" border="0"
														align="absmiddle">&nbsp;打印</a></td>
											</ws:hasAuth>
											<ws:hasAuth operationName="/warehouse/sapLInTakeBillAction.do">
											    <td width="80">
													<a href="javascript:excSAPput();"><img
														src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
														align="absmiddle">&nbsp;SAP导出</a>
												</td>
											</ws:hasAuth>	
											<ws:hasAuth operationName="/warehouse/toImportSapIdcodeAction.do">
											    <td width="120">
													<a href="javascript:ImportSapIdcode('${sof.id}','');"><img src="../images/CN/import.gif"
													width="16" height="16" border="0" align="absmiddle">&nbsp;SAP单据条码导入</a></td>
												</td>
											</ws:hasAuth>										
										</tr>
									</c:if>
								</table>
							</td>
						</tr>
					</table>


					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										基本信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									出货仓库：
								</td>
								<td width="27%">
									<windrp:getname key='warehouse' value='${sof.warehouseid}' p='d' />
								</td>
								<td width="8%" align="right">
									企业内部单号：
								</td>
								<td width="22%">
									${sof.nccode}
								</td>
								<td width="10%" align="right">
									出库机构：
								</td>
								<td width="22%">
									${sof.oname}
								</td>
							</tr>
							<tr>
								<td width="11%" align="right">
									收货人：
								</td>
								<td width="27%">
									${sof.rlinkman}
								</td>
								<td width="8%" align="right">
									联系电话：
								</td>
								<td width="22%">
									${sof.tel}
								</td>
								<td width="10%" align="right">
									收货仓库：
								</td>
								<td width="22%">
									<windrp:getname key='warehouse' value='${sof.inwarehouseid}' p='d' />
								</td>
							</tr>
							<tr>
								<%--<td align="right">
									检货人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.takeid}' p='d' />
								</td>
								--%><td align="right">
									打印次数：
								</td>
								<td>
									${sof.printtimes}
								</td>
								<td align="right">
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="right">
									备注：
								</td>
								<td colspan="3">
									${sof.remark}
								</td>
								<td align="right">
									是否查看:
								</td>
								<td>
									<windrp:getname key="YesOrNo" p="f" value="${IsRead}" />
								</td>

							</tr>
						</table>
					</fieldset>

					<fieldset align="center">
						<legend>
							<table width="50" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										状态信息
									</td>
								</tr>
							</table>
						</legend>
						<table width="100%" border="0" cellpadding="0" cellspacing="1"
							class="table-detail">
							<tr>
								<td width="11%" align="right">
									制单人：
								</td>
								<td width="26%">
									<windrp:getname key='users' value='${sof.makeid}' p='d' />
								</td>
								<td width="8%" align="right"></td>
								<td width="22%"></td>
								<td width="11%" align="right">
									制单日期：
								</td>
								<td width="22%">
									${sof.makedate}
								</td>
							</tr>
							<tr>
								<td align="right">
									是否复核：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isaudit}' p='f' />
								</td>
								<td align="right">
									复核人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.auditid}' p='d' />
								</td>
								<td align="right">
									复核日期：
								</td>
								<td>
									${sof.auditdate}
								</td>
							</tr>
							<c:choose>
									<c:when test="${sof.bsort == 1}">
										<tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									${sof.blankoutdate}
								</td>
							</tr>
							<tr>
								<td align="right">
									作废原因：
								</td>
								<td>
									${sof.blankoutreason}
								</td>
							</tr>
									</c:when>
								</c:choose>
<%--							
							<tr>
								<td align="right">
									是否作废：
								</td>
								<td>
									<windrp:getname key='YesOrNo' value='${sof.isblankout}' p='f' />
								</td>
								<td align="right">
									作废人：
								</td>
								<td>
									<windrp:getname key='users' value='${sof.blankoutid}' p='d' />
								</td>
								<td align="right">
									作废日期：
								</td>
								<td>
									${sof.blankoutdate}
								</td>
							</tr>
							--%>
							<%--
	   <tr>

	    <td  align="right">是否验货：</td>
	    <td><windrp:getname key='checkedStatus' value='${sof.isChecked}' p='f'/></td>
	    <td align="right">验货人：</td>
	    <td><windrp:getname key='users' value='${sof.checkedId}' p='d'/></td>
	    <td align="right">验货日期：</td>
	    <td>${sof.checkedDate}</td>
	    </tr>
	     --%>
						</table>
					</fieldset>
					<%--
	<c:if test="${sof.bsort != 7}">
		<fieldset align="center"> 
		<legend>
    		采集器信息
	 	 </legend>
		  <table width="100%"  border="0" cellpadding="0" cellspacing="1" class="table-detail">
		  <tr>
		  	<td width="11%"  align="right">采集器编号：</td>
		  	
	          <td width="26%">
					<c:if test="${sof.isPicked != null && sof.isPicked != 0 }">
						<input type="text" name="scannerNo" id="scannerNo" value="${sof.scannerNo}" readonly="readonly"/>
						
					</c:if>
					
					<c:if test="${sof.isPicked == null || sof.isPicked == 0 }">
					<select name="scannerNo" id="scannerNo" onchange="setScannerNo();">
					<option value="">
						-选择-
					</option>
					<logic:iterate id="c" name="scannerUsers">
					<option value="${c.scanner}" ${c.scanner == sof.scannerNo ?"selected":""} >${c.scanner}</option>
					</logic:iterate>
					</select>
					</c:if>
					
			  </td>
	          <td width="8%" align="right"></td>
	          <td width="22%"></td>
		      <td width="11%" align="right"></td>
		      <td width="22%"></td>
		  </tr>
		  </table>
		</fieldset>
	</c:if>
	--%>

					<fieldset align="center">
						<legend>
							<table width="110" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										检货物品清单列表
									</td>
								</tr>
							</table>
						</legend>
						<table class="sortable" width="100%" border="0" cellpadding="0"
							cellspacing="1">
							<tr align="center" class="title-top">
								<td width="7%">
									产品编号
								</td>
								<td width="10%">
									物料号
								</td>
								<td width="20%">
									产品名称
								</td>
								<td width="10%">
									规格
								</td>
								<td width="4%">
									序号
								</td>
								<td width="8%">
									计划数量
								</td>
								<td width="8%">
									确认数量
								</td>
								<td width="5%">
									单位
								</td>
							</tr>
							<logic:iterate id="p" name="als">
								<tr class="table-back-colorbar">
									<td>
										${p.productid}
									</td>
									<td>
										${p.nccode}
									</td>
									<td>
										${p.productname}
									</td>
									<td>
										${p.specmode}
									</td>
									<td><span onmouseover="ShowTCQ(this,'${p.productid}','${sof.id}');"><a href="javascript:toaddidcode('${sof.id}','${p.id}','${sof.warehouseid}','${sof.isaudit}')"><img src="../images/CN/code.gif"  border="0" title="录入条码"></span></a></td>
										<td>
											<windrp:format value="${p.quantity}" p="###,##0.00" />
										</td>
										<td>
											<windrp:format value="${p.realQuantity}" p="###,##0.00" />
										</td>
										<td>
											<windrp:getname key='CountUnit' value='${p.unitid}' p='d' />
										</td>
								</tr>
							</logic:iterate>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		<%
			session.setAttribute("sof", request.getAttribute("sof"));
		%>
	</body>
</html>
