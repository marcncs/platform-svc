<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>
<%@ taglib uri="/tags/struts-tiles" prefix="tiles" %>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<SCRIPT LANGUAGE=javascript src="../js/selectDate.js"></SCRIPT>
<script src='../js/prototype.js'></script>
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function addin()
	{
		var i = 0;
		
		for (i; i < f1.selectallusers.length; i++)
		{
			if (f1.selectallusers[i].selected == true)
			{
				
				if (isinlist(f1.selectallusers[i].text) == false)
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectallusers[i].value;
					oOption.text = f1.selectallusers[i].text;
					//alert(oOption.value+"---"+oOption.text);
					//oOption.selected = true;
					f1.selectusers.add(oOption);
					//f1.selectallusers.remove(i);
					
				}
			}
		}
	}
	
	function isinlist(name)
	{
		var i = 0;
		for (i; i < f1.selectusers.length; i++)
		{
			if (f1.selectusers[i].text == name)
			{
				return true;
			}
		}
	
		return false;
	}
	
	
	function delout()
	{
		var i = 0;
		for (i; i < f1.selectusers.length; i++)
		{
			if (f1.selectusers[i].selected == true)
			{
				var oOption = document.createElement("OPTION");
				oOption.value = f1.selectusers[i].value;
				oOption.text = f1.selectusers[i].text;
				f1.selectusers.remove(i);
				//oOption.selected = true;
				//f1.selectallusers.add(oOption);
				i--;
			}
		}
	}
	
	function savespeed(){
		var str = "";
		var i = 0;
		var count = f1.selectusers.length;
		for (i; i < count; i++)
		{
			str = str + f1.selectusers[i].value + ",";
		}
		//alert("count====="+count);
		f1.speedstr.value = str;
		f1.uscount.value= count;
		f1.action = "shareCustomerAction.do"; 
			//alert(str);
		f1.submit();
	}
	
	
	


</script>
</head>

<body>
<SCRIPT language=javascript>

</SCRIPT>

<table width="100%" border="1" cellpadding="0" cellspacing="1" bordercolor="#BFC0C1">
  <tr>
    <td>
	<table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
  <tr> 
    <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
    <td width="772">共享客户</td>
  </tr>
</table>
<form name="f1" method="post" action="shareCustomerAction.do">

<table width="100%" height="45" border="0" cellpadding="0" cellspacing="0">

	<tr> 
      <td width="38%" >选择共享用户
        <INPUT NAME="speedstr" TYPE="hidden">
        <INPUT NAME="uscount" TYPE="hidden">
        <input name="cid" type="hidden" id="cid" value="${cid}"></td><td width="18%" align="right">&nbsp;</td>
      <td width="40%">&nbsp; </td>
    </tr>
			<tr align="center"> 
                  <td width="38%" align="left"><div align="center"><span id="selectallusers" >
                    <select name="selectallusers" size="11" style="width: 170px;" multiple LANGUAGE="javascript" ondblclick="return addin();">
                      <logic:iterate id="u" name="auls" > 
                        <option value="${u.userid}">${u.realname}</option>
                      </logic:iterate>
                    </select>
                    </span>
                  </div></td>
                  <td width="18%"> 
                    <input style="width: 75px;" type="button" value="添加>>" language=javascript onClick="addin();">
        		<p>
       			 <input style="width: 75px;" type="button" value="<<删除" language=javascript onClick="delout();">
        		<br>
		</td>
                  <td width="40%">
                    <select name="selectusers" size="11" multiple style="width: 170px;" LANGUAGE=javascript ondblclick="return delout();">
                      <logic:iterate id="au" name="alls">
                        <option value="${au.userid}">${au.realname}</option>
                      </logic:iterate>
                    </select>                  </td>
          </tr>
			<tr align="center">
			  <td colspan="3" align="left"><div align="center">
                <input type="submit" name="Submit" value="确定" onClick="savespeed();">
                <input type="button" name="cancel" value="取消" onClick="javascript:history.back();">
              </div></td>
		  </tr>
		 
        </table>
	   </form>

</td>
  </tr>
</table>
</body>
</html>
