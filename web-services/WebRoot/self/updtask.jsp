<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<title>修改任务</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
        <SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<script language="javascript">
	function Chk(){
			var TPTitle = document.search.tptitle;
			var TPContent = document.search.tpcontent;
			var conclusiondate = document.search.conclusiondate;
			var endtime = document.search.endtime;
			var cname = document.search.cname;
			if(TPTitle.value==null||TPTitle.value.trim()==""){
				alert("标题不能为空!");
				TPTitle.focus();
				return false;
				
			} 
			if(conclusiondate.value == ""){
				alert("结束日期不能为空!");
				return false;
			}
			if(endtime.value == ""){
				alert("结束时间不能为空!");
				return false;
			}
			if(cname.value==""||cname.value.trim()==""){
				alert("对象名称不能为空!");
				cname.focus();
				return false;
				
			}
			if(TPContent.value.length>256){
				alert("内容太长!字数不能超过256个字符!");
				TPContent.select();
				return false;
			}
			
			
			return true;
		}
	
	
		function isAllcheck(obj){
		var checks = document.getElementsByName("userid");
		for(var i = 0 ; i <checks.length; i++){
			checks[i].checked = obj.checked;
		}
	}
	
	function SelectName(){
		
			var objsort = document.search.objsort;
			if(objsort.value ==1){
				SelectCustomer();
			}else{
				SelectOrgan();
			}
		}
	
		function SelectCustomer(){
			var c=showModalDialog("../common/selectMemberAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if ( c == undefined ){
				return;
			}
			document.search.cid.value=c.cid;
			document.search.cname.value=c.cname;
		}

		function SelectOrgan(){
			var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
			if(o==undefined){return;}
			document.search.cid.value=o.id;
			document.search.cname.value=o.organname;
		} 
	
</script>

	<body style="overflow-y: auto;">
		<form name="search" method="post" action="updTaskAction.do"
			onSubmit="return Chk();">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				bordercolor="#BFC0C1">
				<tr>
					<td>
						<table width="100%" height="40" border="0" cellpadding="0"
							cellspacing="0" class="title-back">
							<tr>
								<td width="10">
									<img src="../images/CN/spc.gif" width="10" height="1">
								</td>
								<td width="772">
									修改任务
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
							<table width="100%" border="0" cellpadding="0" cellspacing="1">

								<tr>
									<td align="right">
										标题：
									</td>
									<td>
										<input type="hidden" name="id" value="${tpf.id }">
										<input name="tptitle" type="text" id="tptitle" maxlength="128"
											value="${tpf.tptitle}">
									</td>
									<td align="right">
										结束日期：
									</td>
									<td>

										<input type="text" name="conclusiondate"
											onFocus="javascript:selectDate(this)"
											value="<windrp:dateformat value="${tpf.conclusiondate }" p="yyyy-MM-dd"/>"
											size="10" readonly="readonly">
										-
										<input name="endtime" type="text" id="endtime" size="10"
											onFocus="selectTime(this);" value="${tpf.endtime}"
											readonly="readonly">
											<span class="STYLE1">*</span>
									</td>
									<td align="right">
										优先级：
									</td>
									<td>
										<windrp:select key="Priority" name="priority" p="n|d"
											value="${tpf.priority }" />
									</td>
								</tr>
								<!--
								<tr>
									<td align="right">
										对象类型：
									</td>
									<td>
										<windrp:select key="ObjSort" name="objsort" p="n|f"
											value="${tpf.objsort}" />
									</td>
									<td align="right">
										对象名称：
									</td>
									<td>
										<input type="hidden" id="cid" name="cid" value="${tpf.cid}">
										<input name="cname" type="text" id="cname" readonly
											value="${tpf.cname}"><a href="javascript:SelectName();"><img
												src="../images/CN/find.gif" width="19" height="18"
												border="0" align="absmiddle"> </a>
												<span class="STYLE1">*</span>


									</td>
									<td align="right">
										
									</td>
									<td>
										
									</td>
								</tr>
                                -->
								<tr>
									<td align="right">
										状态：
									</td>
									<td>
										<windrp:select key="TaskPlanStatus" name="status" p="n|f"
											value="${tpf.status }" />
									</td>

									<td align="right">
										类型：
									</td>
									<td>
										<windrp:select key="TaskSort" name="tasksort" p="n|d"
											value="${tpf.tasksort }" />
									</td>
									<td align="right">
										完成状况：
									</td>
									<td>
										<windrp:select key="OverStatus" name="overstatus" p="n|d"
											value="${tpf.overstatus }" />
									</td>
								</tr>

								<tr>
									<td align="right">
										内容：
									</td>
									<td colspan="5">
										<textarea name="tpcontent" style="width: 100%" cols="100"
											rows="5">${tpf.tpcontent }</textarea><br><span class="td-blankout">(内容长度不能超过256字符)</span>
									</td>
								</tr>

							</table>
						</fieldset>
						<table width="100%">
							<tr>
								<td align="center">
									<input type="submit" name="Submit" value="确定">
									&nbsp;&nbsp;
									<input type="button" name="cancel" value="取消"
										onClick="javascript:window.close();">
								</td>
							</tr>
						</table>

					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
