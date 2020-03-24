<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE="javascript" src="../js/selectDate.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/prototype.js"></SCRIPT>
		<SCRIPT LANGUAGE="javascript" src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<title>WINDRP-分销系统</title>
		<script language="javascript">
	var falag = false;
	this.onload = function onLoadDivHeight() {
		document.getElementById("listdiv").style.height = (document.body.clientHeight - document
				.getElementById("bodydiv").offsetHeight)
				+ "px";
	}
	//日报下载数据
	function DownLoad() {
		var time = document.getElementsByName("DateTime")[0];
		var psid=document.getElementsByName("regionareaid")[0];
		var psidname=document.getElementsByName("regionarea")[0];
		if (time.value== "") {
			alert("请选择日期");
			falag = false;
			return;
		}else{
		falag = true;
		}
		if(psid!=null){
		    if(psid.value==""){
		    alert("请选择区域类型");
			falag = false;
			return;
		    }
		    if(psidname.value==""){
		     alert("顶级区域信息报表信息不存在");
			falag = false;
			return;
		    }
		    else{
		    falag=true;
		    }
		 }
		if (falag) {
			search.action = "downLoadReportAction.do";
			search.submit();
		}
	}
</script>

		<style type="text/css">
.STYLE1 {
	color: #FF0000
}
</style>

	</head>
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<div id="bodydiv">
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="24">
								</td>
								<td width="772">
									报表分析&gt;&gt;数据源下载
								</td>
							</tr>
						</table>
						<form name="search" method="post"
							action="toDownLoadReportAction.do">
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								style="border-bottom: 1px solid #B6D6FF;" class="specialTable">
								<tr class="table-back">
									<td class="table-back" width="10%">
										<label style="">
											日报表下载：
										</label>
									</td>
                                     <!-- 
									<td width="10" class="table-back-gray">
										<img src="../images/CN/spc.gif" width="5" height="15">
									</td>
                                     -->
									<td width="10%" align="right">
										选择日期：
									</td>
									<td>
										<input name="DateTime" type="text" id="DateTime"
											value="${DateTime}" size="12"
											onFocus="javascript:selectDate(this)" readonly="readonly"
											style="height: 18px; width: 99px">
										<span class="STYLE1">*</span>
									</td>

									<!-- 办事处经理 -->
									<c:if test="${flag==4}">
										<td width="10%">
											区域信息类型：
										</td>
										<td width="20%">
											${Regionname}
											<input type="hidden" id="officemanagerid"
												name="officemanagername" value="${Regionname}" />
										</td>
										<td width="5" class="table-back"/>
										<!--  
										<td width="5" class="table-back-gray">
											<img src="../images/CN/spc.gif" width="5" height="15">
										</td>
										-->
                                        <td width="2%" class="table-back"></td>
										<td width="15%" class="table-back">
											<input type="button" id="bt1" name="but1" class="button"
												value="数据下载" onclick="javascript:DownLoad();"
												style="height: 25px; width: 65px"/>
										</td>
										<td width="5%" class="table-back"></td>
										<td width="5%" class="table-back"></td>
									</c:if>

									<!-- 大区经理 -->
									<c:if test="${flag==5}">
										<td width="10%">
											区域信息类型：
										</td>

										<td width="20%">
											<input type="hidden" name="flag" value="${flag}">
											<input type="hidden" name="regionareaid" id="psid"
												value="${regionareaid}">
											<windrp:regiontree1 id="psid" name="regionarea"
												value="${regionarea}" />
											<span class="style1">*</span>
										</td>
										<td width="5%" class="table-back"></td>
										<!--  
										<td width="10" class="table-back-gray">
											<img src="../images/CN/spc.gif" width="5" height="15">
										</td>
										-->
										<td width="2%" class="table-back"></td>
										<td width="15%" class="table-back">
											<input type="button" id="bt1" name="but1" class="button"
												value="数据下载" onclick="javascript:DownLoad();"
												style="height: 25px; width: 65px">
										</td>
										<td width="5%" class="table-back"></td>
										<td width="5%" class="table-back"></td>
									</c:if>

									<!-- 管理员 -->
									<c:if test="${flag==0}">
										<td width="10%">
											区域信息类型：
										</td>
										<td width="20%">
											<input type="hidden" name="flag" value="${flag}">
											<input type="hidden" name="regionareaid" id="psid"
												value="${regionareaid}">
											<windrp:regiontree id="psid" name="regionarea"
												value="${regionarea}" />
											<span class="style1">*</span>
										</td>

										<td width="5%" class="table-back"></td>
										<!--  
										<td width="10" class="table-back-gray">
											<img src="../images/CN/spc.gif" width="5" height="15">
										</td>
                                         -->
										
                                         <td width="2%" class="table-back"></td>
										<td width="15%" class="table-back">
											<input type="button" id="bt1" name="but1" class="button"
												value="数据下载" onclick="javascript:DownLoad();"
												style="height: 27px; width: 84px">
										</td>
										<td width="5%" class="table-back"></td>
										<td width="5%" class="table-back"></td>
									</c:if>
								</tr>
							</table>
						</form>
					</div>
				</td>
			</tr>
		</table>
		<div style="width: 100%" id="listdiv"></div>
		<form name="excputform" method="post" action="downLoadReportAction.do">
		</form>

	</body>
</html>
