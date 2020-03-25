<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>

<html>

<head>
<title>来电信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<SCRIPT LANGUAGE=javascript src="../js/function.js"></SCRIPT>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">

function toaddsaleorder(){
	var op = dealform.op;
	var cid = parent.cusinfo.cid.value;
	var telphone = parent.cusinfo.tel.value;
	for (i=0; i<op.length; i++){
		if ( op[i].checked ){
			var opvalue = op[i].value;
			if ( opvalue == 1 ){
				window.open("../sales/toAddMemberAction.do?cid=${customer.cid}","注册会员","height=600,width=800,top=250,left=250,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
			}else if ( opvalue == 2 ){
				popWin("../sales/toAddSaleOrderAction.do?cid="+cid,1024,650);	
			}else if ( opvalue == 3 ){
				popWin("../aftersale/toAddWithdrawAction.do?cid="+cid,1024,650);	
			}else if ( opvalue == 4 ){
				popWin("../sales/toAddSuitAction.do?cid="+cid,1024,650);	
			}else if ( opvalue == 5 ){
				var tel="";
				var officetel="";
				if ( telphone.length == 11 ){
					tel=telphone;
				}else{
					officetel=telphone;
				}
				popWin("../sales/toAddMemberAction.do?op=call&tel="+tel+"&officetel="+officetel,1024,650);
			}	
		}
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
              <td width="772"> 业务处理</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td>
		 <form name="dealform" method="post">

	  <table width="100%"  border="0" cellpadding="0" cellspacing="1">
	 
	  <tr>
	  	<td width="13%"  align="center">
		  	<input type="radio" name="op" value="2" checked>下订单 &nbsp; &nbsp;
			<input type="radio" name="op" value="3">退货  &nbsp; &nbsp;
			<input type="radio" name="op" value="4">投诉  &nbsp; &nbsp;
			<input type="radio" name="op" value="5">注册会员
		  </td>
          
	  </tr>
	  <tr>
	
	    <td align="center"><input type="button" value="确定" onClick="toaddsaleorder()"></td>

	    </tr>
		
	  </table>
</form>
		
		</td>
      </tr>
    </table></td>
  </tr>
</table>
</body>
</html>
