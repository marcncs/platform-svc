<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title>上传新文档</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
		<script language="javascript">

	function SelectName(){
		var objsort = document.refer.objsort;
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
		document.refer.cid.value=c.cid;
		document.refer.cname.value=c.cname;
	}
	
	function SelectOrgan(){
		var o=showModalDialog("../common/selectOrganAction.do",null,"dialogWidth:21cm;dialogHeight:12cm;center:yes;status:no;scrolling:no;");
		if(o==undefined){return;}
		document.refer.cid.value=o.id;
		document.refer.cname.value=o.organname;
	} 
	function ChkValue(){
        var cname = document.refer.cname;
        var doc = document.refer.doc;
		var documentname = document.refer.documentname;
		if(cname.value==null||cname.value==""||cname.value.trim()==""){
			alert("客户不能为空!");
			return false;
		}
		if(documentname.value==null||documentname.value==""||documentname.value.trim()==""){
			alert("文档名不能为空!");
			return false;
		}
		if(doc.value==null||doc.value==""){
			alert("请选择上传的文件!");
			return false;
		}
		
		showloading();
		return true;
	}
</script>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
	</head>
	<html:errors />
	<body>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			bordercolor="#BFC0C1">
			<tr>
				<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<table width="100%" height="40" border="0" cellpadding="0"
									cellspacing="0" class="title-back">
									<tr>
										<td width="10">
											<img src="../images/CN/spc.gif" width="10" height="1">
										</td>
										<td>
											上传新文档
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<form name="refer" method="post" enctype="multipart/form-data"
									action="addDocumentAction.do" onSubmit="return ChkValue();">
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
													对象类型：
												</td>
												<td>
													<input type="hidden" id="objsort" name="objsort"
														value="${objsort }">
													<windrp:getname key="ObjSort" p="f" value="${objsort}" />
												<td align="right">
													<input name="cid" type="hidden" id="cid">
													对象名称：
												</td>
												<td>
													<input name="cname" type="text" id="cname"><a href="javascript:SelectName();"><img
															src="../images/CN/find.gif" width="18" height="18"
															align="absmiddle" border="0">
													</a>
													<span class="STYLE1">*</span>
												</td>
												<td align="right">
													文档名：
												</td>
												<td>
													<input name="documentname" type="text">
													<span class="STYLE1">*</span>
												</td>
											</tr>
											<tr>
											  <td align="right">选择文件：</td>
											  <td colspan="5"><input name="doc" type="file" id="doc" size="80">
                                                <span class="STYLE1">*</span>
                                                </td>
										  </tr>
										</table>
									</fieldset>

									<table width="100%" border="0" cellpadding="0" cellspacing="1">
										<tr align="center">
											<td>
												<input type="submit" name="Submit" value="确定">
												&nbsp;&nbsp;
												<input type="reset" name="cancel"
													onClick="javascript:window.close();" value="取消">
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
