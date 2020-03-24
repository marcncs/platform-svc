<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>广州丸美</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT type="text/javascript" src="../js/validator.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/function.js"></SCRIPT>
<script language="JavaScript">
function formcheck(){
	var filedownselect = document.getElementById("filedownselect").value;
	if(filedownselect == "请选择文件名称进行下载"){
		alert('请重新选择文件名称进行下载 !');
		document.getElementById("filedownselect").focus(); 
		return false;
	}else{
		return true;
	}
}
</script>
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
              <td width="772">下载文件</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		<form name="refer" method="post" action="../sys/downLoadMarubiAction.do" onSubmit="return formcheck()">
			<input type="hidden" name="billsort" value="${billsort}">
			<fieldset align="center"> <legend>
	      		<table width="50" border="0" cellpadding="0" cellspacing="0">
			        <tr>
			          <td>基本信息</td>
			        </tr>
			    </table>
		  		</legend>
		  		<table width="100%"  border="0" cellpadding="0" cellspacing="1">
				  <tr>
			        <td width="100%">
			        	<select id="filedownselect" name="filedownselect">
			        		<option value="请选择文件名称进行下载">请选择文件名称进行下载</option>
			        		<logic:iterate id="path" name="pathList">
			        			<option value="${path}">${path}</option>
			        		</logic:iterate>
			        	</select>
						<span class="STYLE1">*</span>
			        </td>
			      </tr>
			    </table>
			</fieldset>
			<table width="100%"  border="0" cellpadding="0" cellspacing="1">
              <tr align="center">
                <td ><input type="submit" name="Submit" value="下载">&nbsp;&nbsp;
                    <input type="button" name="cancel" onClick="window.close()" value="取消"></td>
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
