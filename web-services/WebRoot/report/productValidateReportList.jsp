<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>WINDRP-分销系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectduw2.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery-1.11.1.min.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/highcharts.js"> </SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<script language="javascript">
			var checkid="";
			function CheckedObj(obj,objid){
			
				for(i=0; i<obj.parentNode.childNodes.length; i++)
				{
				   obj.parentNode.childNodes[i].className="table-back-colorbar";
				}
				obj.className="event";
				pvmenu = getCookie("pvCookie");
				checkid=objid;
				switch(pvmenu){
					case "1":
				 	if(document.getElementById("detailUrl")){
				 		detail(); break;
					}
					default:detail();
				}
			}
			// 查看详情
			function detail(){
				setCookie("pvCookie","1");
					if(checkid!=""){
						document.all.basic.src="../report/productValidateDetailAction.do?idcode="+checkid;
					}else{
						alert("请选择你要操作的记录!");
					}
				}
			
			function SelectSingleProduct(){
				var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
				if(p==undefined){return;}
				document.searchform.productId.value=p.id;
				document.searchform.productName.value=p.productname;
				}
			function SelectOrgan(){
				var p=showModalDialog("../common/selectOrganAction.do?type=rw",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
				if ( p==undefined){return;}
					document.searchform.organId.value=p.id;
					document.searchform.organName.value=p.organname;
					
					document.searchform.warehouseId.value="";
					document.searchform.wname.value="";
				}
			function SelectSingleProductName(){
			var p=showModalDialog("../common/selectSingleProductNameAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
			if(p==undefined){return;}
			document.searchform.ProductName.value=p.productname;
			document.searchform.packSizeName.value="";
			document.searchform.packsizename.value="";
			}
			function formChk(){
				showloading();
				return true;
			}
			this.onload = function abc(){
				document.getElementById("listdiv").style.height = (document.body.clientHeight  - document.getElementById("querydiv").offsetHeight)+"px" ;
			}
			// 用于显示图表
			$(function () {
			    $('#container').highcharts({
			    	colors:['#4a86bc','#c55353'],
			        title: {
			            text: '查询数量分析图${ cateType }',
			            x: -20 //center
			        },
			        subtitle: {
			            text: '',
			            x: -20
			        },
			        xAxis: {
			            categories: ${categories}
			        },
			        yAxis: {
			            title: {
			                text: '查询次数'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: ''
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle',
			            borderWidth: 0
			        },
			        series: [{
			            name: '总数',
			            data: ${totleData}
			        }, {
			            name: '真',
			            data: ${trueData}
			        }]
			    });
			});
			function OutPut() {
				excputform.action = "../report/excputProductValidateReportAction.do";
				excputform.submit();
			}
</script>
	</head>
	<body>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="querydiv">
						<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0"
							class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="0">
								</td>
								<td>
									${menusTrace }
								</td>
							</tr>
						</table>
						<form name="searchform" method="post"
							action="../report/productValidateReportAction.do" onSubmit="return formChk();">
							<input type="hidden" name="queryFlag" id="queryFlag" value="${queryFlag }"/>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="table-back">
									<td align="right">
										区域：
									</td>
									<td>
										<select name="region">
										<option value="">
											-请选择-
										</option>
										<logic:iterate id="r" name="regions">
											<option value="${r.regioncode}" ${r.regioncode==region?"selected":""}>${r.sortname}</option>
										</logic:iterate>
										</select>
									</td>
									<td align="right">
										产品名称：
									</td>
									<td>
										<input id="ProductName" name="ProductName"
											value="${ProductName}" readonly>
										<a href="javascript:SelectSingleProductName();"><img
												src="../images/CN/find.gif" align="absmiddle" border="0">
										</a>
									</td>
									<td align="right">
										规格：
									</td>
									<td>
										<input type="hidden" name="packSizeName" id="packSizeName"
											value="${packSizeName}">
										<input type="text" name="packsizename" id="packsizename" value="${packsizename}"
											onClick="selectDUW(this,'packSizeName',$('#ProductName').val(),'psn','')" readonly>
									</td>
									
								</tr>
								<tr class="table-back">
									<td  align="right">
										日期：
									</td>
									<td>
									<input name="beginDate" type="text" id="beginDate" size="10" value="${beginDate}"
										onFocus="javascript:selectDate(this)" readonly>
									-
									<input name="endDate" type="text" id="endDate" size="10" value="${endDate}"
									onFocus="javascript:selectDate(this)" readonly>
									</td>
									<td colspan="3">
										&nbsp;
									</td>
									<td class="SeparatePage">
										<input name="Submit" type="image" id="Submit"
											src="../images/CN/search.gif" border="0" title="查询">
									</td>
								</tr>
							</table>
						</form>
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr class="title-func-back">
							<c:if test="${prompt=='view'}">
								<ws:hasAuth operationName="/report/excputProductValidateReportAction.do">
									<td width="50">
										<a href="javascript:OutPut();"><img
												src="../images/CN/outputExcel.gif" width="16" height="16" border="0"
												align="absmiddle">&nbsp;导出</a>
									</td>
									<td class="SeparatePage">
									</td>
								</ws:hasAuth>
								</c:if>
							</tr>
						</table>
					</div>
					<div id="listdiv" style=" overflow-y: auto; height: 300px;">
						<div id="container" style="width: 500px; height: 280px; margin: 0 auto"></div>
						<div>
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr class="title-func-back">
									<td style="font-weight: bold;align:"left";>
										查询超过(包括)${pvQueryCount }次的码统计
									</td>
									<td class="SeparatePage">
										<pages:pager action="../report/productValidateReportAction.do" onsubmit="formChk()"/>
									</td>
								</tr>
							</table>
							<table width="100%" border="0" cellpadding="0" cellspacing="1"
								class="sortable">
								<tr align="center" class="title-top-lock">
									<td>
										查询码
									</td>
									<td>
										首次查询时间
									</td>
									<td>
										首次查询电话/IP
									</td>
									<td>
										累计次数
									</td>
									<td >
										防伪码属性
									</td>
								</tr>
								<c:forEach items="${list}" var="d">
									<tr class="table-back-colorbar" onClick="CheckedObj(this,'${d.idcode}');">
										<td>
											${d.idcode }
										</td>
										<td>
											${d.queryDate }
										</td>
										<td>
											${d.queryAddr }
										</td>
										<td>
											${d.queryCount }
										</td>
										<td>
											<windrp:getname key='TrueOrFalse' value='${d.chkTrue }' p='f' />
										</td>
									</tr>
								</c:forEach>
	
							</table>
						</div>
						<div id="detail" name="detail">
								<IFRAME id="basic" style="WIDTH: 100%; HEIGHT: 100%"
									name="basic" src="../sys/remind.htm" frameBorder=0 scrolling=no
									onload="setIframeHeight(this)"></IFRAME>
						</div>
					</div>
				</td>
			</tr>
		</table>
<form  name="excputform" method="post" action="excputProductValidateReportAction.do">
<input type="hidden" name="ProductName" id ="ProductName" value="${ProductName }">
<input type="hidden" name="packSizeName" id ="packSizeName" value="${packSizeName }">
<input type="hidden" name="beginDate" id ="beginDate" value="${beginDate }">
<input type="hidden" name="endDate" id ="endDate" value="${endDate }">
<input type="hidden" name="region" id ="region" value="${region}">
<input type="hidden" name="queryFlag" id ="queryFlag" value="${queryFlag}">
	</body>
</html>
