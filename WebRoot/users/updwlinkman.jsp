<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>

<html>

<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<script type="text/javascript" src="../js/validator.js"></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
</head>
<html:errors/>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0" >
      <tr>
        <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
            <tr>
              <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
              <td width="772"> 修改联系人</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		
		<form name="validateLinkman" method="post" action="../users/updWLinkManAction.do" onsubmit="return Validator.Validate(this,2)">
		<input name="id" type="hidden" id="id" value="${wl.id}">
		<fieldset align="center"> <legend>
      <table width="50" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>基本信息</td>
        </tr>
      </table>
	  </legend>
	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	  <tr>
	  	<td width="12%"  align="right">
	  	  仓库编号：</td>
          <td width="24%"><input name="warehouseid" type="text" id="warehouseid"
           value="${wl.warehouseid}" 
            readonly></td>

</td>
          <td width="10%" align="right">联系人姓名：</td>
          <td width="21%"><input name="name" type="text"  value="${wl.name}" dataType="Require" msg="必须录入联系人姓名!">
          <span class="STYLE1">*</span></td>
          <td width="11%" align="right">手机：</td>
	      <td width="22%"><input type="text" name="mobile" value="${wl.mobile}" dataType="Mobile" msg="必须录入正确的手机号码!">
          <span class="STYLE1">*</span></td>
	  </tr>
	 <tr>
	    <td align="right">
	    	说明：
	    </td>
	  	<td colspan="4">
	  		您提交的人员姓名和电话号码等个人信息将被系统记录。拜耳收集、处理和使用您提交的个人信息是为了让您能够使用本系统的服务，接收发货短信通知。</br>
			上述的个人信息数据将由拜耳保存在中国境内或境外的拜耳关联公司或拜耳授权的第三方公司，并遵守拜耳数据隐私保护的规则及其他所在地的相关法律/法规。</br>
			如您同意上述个人数据处理申明，请点击按钮《确定》后继续使用本系统，如不同意，可以点击《取消》按钮后退出。</br>
			您有权在未来随时撤销此同意申明，您可以在系统中直接删除已提交的个人信息数据。
	  	</td> 
	  </tr> 
	  </table>
</fieldset>
		
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr>
                <td><div align="center">
                  <input type="submit" name="Submit" value="确定">&nbsp;&nbsp;
                  <input type="reset" name="cancel" onClick="javascript:window.close()" value="取消">
                </div></td>
              </tr>
        </table>
		 </form>
		
           
        </table></td>
      </tr>
    </table>
</body>
</html>
