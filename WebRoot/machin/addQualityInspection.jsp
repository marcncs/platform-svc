<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../common/tag.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>生产数据上传</title>
		<link href="../css/ss.css" rel="stylesheet" type="text/css">
		<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
		<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
		<SCRIPT language="javascript" src="../js/jquery.js"> </SCRIPT>
		<SCRIPT type="text/javascript" src="../js/selectDate.js"></SCRIPT>
		<script language="JavaScript">
	function formcheck() { 
		if (!Validator.Validate(document.refer, 2)) {
			return false;
		} 
		var ufile = $("#idcodefile").val().toLowerCase();
		if(ufile !="" && !endWith(ufile,".pdf")) {
			alert("只能上传pdf类型的文件!");
			return false;
		};
		showloading();
		return true;
	}
	function endWith(str,sufix){
		  if(sufix==null||sufix==""||str.length==0||sufix.length>str.length)
		     return false;
		  if(str.substring(str.length-sufix.length)==sufix)
		     return true;
		  else
		     return false;
		  return true;
	}
	function SupperSelect(){
		
		var p=showModalDialog("../common/selectSingleProductAction.do",null,"dialogWidth:20cm;dialogHeight:17cm;center:yes;status:no;scrolling:no;");
		if(p==undefined){
			return;
		}else{
			$("#mCode").val(p.nccode);
			$("#productName").val(p.productname);
			$("#specmode").val(p.specmode);
		}
	}
</script>

	</head>

	<body>
		<form name="refer" method="post" enctype="multipart/form-data"
			action="../machin/addQualityInspectionAction.do"
			onSubmit="return formcheck()">

			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="title-back">
				<tr>
					<td width="10">
						<img src="../images/CN/spc.gif" width="10" height="1">
					</td>
					<td>
						 新增质量检验信息
					</td>
				</tr>
			</table>
			<fieldset style="text-align: center;">
				<legend>
					基本信息
				</legend>
				<table width="100%" border="0" cellpadding="0" cellspacing="1">
					<tr>

						<td align="right">
							物料号：
						</td>
						<td>
							<input type="text" name="mCode" id="mCode" dataType="Require"
								msg="物料号不能为空!" readonly size="30">
							<a href="javascript:SupperSelect();"><img
									src="../images/CN/find.gif" width="18" height="18" border="0"
									align="absmiddle"> </a>
							<span class="STYLE1">*</span>
						</td>
						<td align="right">
							产品名称：
						</td>
						<td>
							<input type="text" name="productName" id="productName" readonly>
						</td>
					</tr>
					<tr>
						<td align="right">
							规格：
						</td>
						<td>
							<input type="text" name="specmode" id="specmode" readonly>
						</td>
						<td align="right">
							批次：
						</td>
						<td>
							<input type="text" name="batch" id="batch" dataType="Require"
								msg="批次不能为空!">
							<span class="STYLE1">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							检验人：
						</td>
						<td>
							<input type="text" name="inspector" id="inspector"
								dataType="Require" msg="检验人不能为空!">
							<span class="STYLE1">*</span>
						</td>
						<td align="right">
							检验日期：
						</td>
						<td>
							<input name="inspectDate" type="text" id="inspectDate"
								onFocus="javascript:selectDate(this)"
								value="<%=com.winsafe.hbm.util.DateUtil.getCurrentDateString()%>"
								readonly="readonly">
							<span class="STYLE1">*</span>
						</td>
					</tr>
					<tr>
						<td align="right">
							是否合格：
						</td>
						<td>
							<windrp:select key="YesOrNo" name="isQualified" value="1" p="y|f" />
						</td>
						<td align="right">
							检验报告：
						</td>
						<td>
							<input name="idcodefile" type="file" id="idcodefile" size="40">
						</td>
					</tr>
				</table>
			</fieldset>
			<table width="100%" border="0" cellpadding="0" cellspacing="1">
				<tr align="center">
					<td>
						<input type="submit" name="Submit" value="确定">
						&nbsp;&nbsp;
						<input type="button" name="cancel" onClick="window.close()"
							value="取消">
					</td>
				</tr>
			</table>

		</form>
	</body>
</html>
