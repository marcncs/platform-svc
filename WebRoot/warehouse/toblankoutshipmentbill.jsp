<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT language="javascript" src="../js/function.js"> </SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
	var checkid=0;
	var checkname="";
	function CheckedObj(obj,objid,objname){
	
	 for(i=1; i<obj.parentNode.childNodes.length; i++)
	 {
		   obj.parentNode.childNodes[i].className="table-back";
	 }
	 
	 obj.className="event";
	 checkid=objid;
	 checkname = objname;
	}

	function Affirm(){
	 	var blankoutreason = document.listform.blankoutreason.value;
		if(blankoutreason==""){
			alert("请填写作废原因!");
			return false;
		}
		showloading();
		return true;
	}

</script>
</head>

<body>
<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
          <td width="772">作废送货清单</td>
        </tr>
      </table>      
        <FORM METHOD="POST" name="listform" ACTION="../warehouse/blankoutShipmentBillAction.do" onSubmit="return Affirm()">
		<input type="hidden" name="id" value="${id}">
      <fieldset align="center"> 		  
		 <table width="100%"  border="0" cellpadding="0" cellspacing="1">
			  			
			  <tr>
				<td width="9%"  align="right">作废原因：</td>
				  <td width="26%"><input type="text" name="blankoutreason" size="80" maxlength="100"><span class="STYLE1">*</span> </td>
			  </tr>			  
			  </table>
		</fieldset>
		<table width="100%"  border="0" cellpadding="0" cellspacing="1">			 
			   <tr>
				<td align="center"><input type="submit" value="提交">&nbsp;&nbsp;<input type="button" value="取消" onClick="window.close()"></td>
				 
			  </tr>
			  </table>
        </form>
     
      
    </td>
  </tr>
</table>
</body>
</html>
