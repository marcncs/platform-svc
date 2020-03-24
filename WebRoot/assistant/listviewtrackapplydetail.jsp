<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>物流全链查询</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/selectDate.js"> </SCRIPT>
		<SCRIPT language="javascript" src="../js/sorttable.js"> </SCRIPT>
		<script type="text/javascript">

var checkid="";
function CheckedObj(obj,objid){
 for(i=0; i<obj.parentNode.childNodes.length; i++)
 {
	   obj.parentNode.childNodes[i].className="table-back-colorbar";
 }
 
 obj.className="event";
 checkid=objid;
  pdmenu = getCookie("oCookie");
	 switch(pdmenu){
	 	case "1":Detail(); break;
		case "2":Track(); break;
		default:Detail();
	 }

}


	function Detail(){
	setCookie("oCookie","1");
		if(checkid!=""){
			document.all.basic.src="../assistant/wlmIdcodeDetailAction.do?ID="+checkid;
			
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function Track(){
		setCookie("oCookie","2");
		if(checkid!=""){
			document.all.basic.src="../assistant/trackDetailAction.do?OID="+checkid;
			
		}else{
			alert("请选择你要操作的记录!");
		}
	}
	function PostAll(objidcode){
			window.open("../assistant/toAddTrackApplyAction.do?idCode="+objidcode,"","height=650,width=900,top=90,left=90,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=no");
	}
</script>
	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="listdiv" style="overflow-y: auto; height: 600px;">
						<FORM METHOD="POST" name="listform" ACTION="">
						</form>
						<br>
						<c:if test="${wlmessage == 'logistics'}">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								bordercolor="#BFC0C1">
								<tr>
									<td>
										<div id="bodydiv">
											<table width="100%" border="0" cellpadding="0" cellspacing="0"
												class="title-back">
												<tr>
													<td width="10">
														<img src="../images/CN/spc.gif" width="10" height="1">
													</td>
													<td width="772">
														查询码详情
													</td>

												</tr>
											</table>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div id="listdiv" style="overflow-y: auto; height: 600px;">
											<FORM METHOD="POST" name="listform" ACTION="">
												<table class="sortable" width="100%" border="0" cellpadding="0"
													cellspacing="1">
													<tr align="center" class="title-top">
														<td width="15%">
															箱码
														</td>
														<td width="10%">
															客户名称
														</td>
														<!--<td width="10%">
															发货仓库
														</td>
														-->
														<!--<td width="8%">
															发货机构代码
														</td>
														-->
														<!--<td width="8%">
															发货机构
														</td>
														-->
														<td width="10%">
															发货日期
														</td>

														<!--<td width="10%">
															收货仓库
														</td>
														-->
														<!--<td width="8%">
															收货机构代码
														</td>
														-->
														<!--<td width="9%">
															收货机构
														</td>
														<td width="10%">
															收货日期
														</td>
														<td width="6%">
															单据类型
														</td>
														<td width="10%">
															发货清单号
														</td>
														-->
														<td width="9%">
															SAP发货单号
														</td>
													</tr>
													<logic:iterate id="tt" name="lttall">
														<tr align="left" class="table-back-colorbar"
															onClick="CheckedObj(this,'${tt.id}');">
															<td>
																${existString}
															</td>
															<td>
																${tt.inOname}
															</td>

															<!--<td>
																<windrp:getname key="warehouse" value="${tt.warehouseid}" p="d" />
															</td>
															--><!--<td>
																${tt.oid}
															</td>
															-->
															<!--<td>
																${tt.oname}
															</td>
															--><td>
																${tt.auditdate}
															</td>

															<!--<td>
																<windrp:getname key="warehouse" value="${tt.inwarehouseid}"
																	p="d" />
															</td>
															<td>
																${tt.inOid}
															</td>
															
															<td>
																${tt.inOname}
															</td>
															<td>
																${tt.pickedDate}
															</td>
															<td>
																<windrp:getname key='BSort' value="${tt.bsort }" p='f' />
															</td>
															<td>
																${tt.id}
															</td>
															--><td>
																${tt.nccode}
															</td>
														</tr>
													</logic:iterate>
												</table>
											</form>
											<br>
										</div>
									</td>
								</tr>
							</table>
						</c:if>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>

