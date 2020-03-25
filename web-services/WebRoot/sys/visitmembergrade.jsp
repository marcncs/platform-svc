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
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function addin()
		{
			var i = 0;
			
			for (i; i < f1.selectallusers.length; i++)
			{
				if (f1.selectallusers[i].selected == true)
				{		
					if (!isinlist(f1.selectallusers[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectallusers[i].value;
						oOption.text = f1.selectallusers[i].text;
						f1.selectusers.add(oOption);	
						f1.selectallusers.remove(i);
						i--;
					}else{
						f1.selectallusers.remove(i);
						i--;
					}
				}
			}
		}
		
		function addAll(){
			for (var i=0; i < f1.selectallusers.length; i++)
			{
				if (!isinlist(f1.selectallusers[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectallusers[i].value;
					oOption.text = f1.selectallusers[i].text;
					f1.selectusers.add(oOption);
					f1.selectallusers.remove(i);
					i--;
				}else{
					f1.selectallusers.remove(i);
					i--;
				}

			}
		}
	
		function isinlist(value)
		{
			var i = 0;
			for (i; i < f1.selectusers.length; i++)
			{
				if (f1.selectusers[i].value == value)
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
					if (!isinlisttwo(f1.selectusers[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectusers[i].value;
						oOption.text = f1.selectusers[i].text;
						f1.selectallusers.add(oOption);
						f1.selectusers.remove(i);
						i--;
					}else{
						f1.selectusers.remove(i);
						i--;
					}					
				}
			}
		}
		
		function isinlisttwo(value)
		{
			var i = 0;
			for (i; i < f1.selectallusers.length; i++)
			{
				if (f1.selectallusers[i].value == value)
				{
					return true;
				}
			}
		
			return false;
		}
		
		function deloutAll(){
		
			var i = 0;
			for (i; i < f1.selectusers.length; i++)
			{
				if (!isinlisttwo(f1.selectusers[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectusers[i].value;
					oOption.text = f1.selectusers[i].text;
					f1.selectallusers.add(oOption);
					f1.selectusers.remove(i);
					i--;
				}else{
					f1.selectusers.remove(i);
					i--;
				}
			}
		
		}
		
	function savespeed(){
		var str = "";
		var i = 0;
		var count = f1.selectusers.length;
		if(count <=0 ){
			alert("请选择用户");
			return false;
		}
	
		for (i; i < count; i++)
		{
			str = str + f1.selectusers[i].value + ",";
		}
		
		f1.speedstr.value = str;
		f1.uscount.value= count;
		f1.action = "visitMemberGradeAction.do"; 
			//alert(str);
		f1.submit();
	}

</script>
</head>

<body>

<table width="100%" border="0" cellpadding="0" cellspacing="0" bordercolor="#BFC0C1">
  <tr>
    <td>
    <form name="f1" method="post" action="">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	
  <tr>
    <td><table width="100%" height="40" border="0" cellpadding="0" cellspacing="0" class="title-back">
        <tr> 
          <td width="10"><img src="../images/CN/spc.gif" width="10" height="1"></td>
                  <td width="772"> 许可会员级别 
                    <input name="ppid" type="hidden" id="ppid" value="${ppid}">
					<INPUT NAME="speedstr" TYPE="hidden">
					<INPUT NAME="uscount" TYPE="hidden">
				  </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr align="center"> 
                  <td width="44%"> 
					<select name="selectallusers" size="11" style="width: 170px; height: 300px;" multiple LANGUAGE="javascript" ondblclick="return addin();">
					  <logic:iterate id="u" name="auls" >  	
					 	<logic:iterate id="au" name="alls" > 
					  		<c:if test="${u.userid == au.userid}">
					  			<c:set var="count" value="1" scope="request"></c:set>
					  		</c:if>
					  	</logic:iterate>
					  	<c:if test="${count != 1}">
					  		<option value="${u.userid}">${u.realname}</option>
					  	</c:if>
					  	<c:set var="count" value="0" scope="request"></c:set>
					  </logic:iterate>
					</select>
						  	
				</td>
                  <td width="8%"> 
                  	<input style="width: 75px;" type="button" value="添加>"
									language=javascript onClick="addin();">                  
							<p>
								 <input style="width: 75px;" type="button" value="全部添加>>"
								language=javascript onClick="addAll();">
							<p>
								<input style="width: 75px;" type="button" value="<删除"
									language=javascript onClick="delout();">
                                
							<p>
								<input style="width: 75px;" type="button" value="<<全部删除"
									language=javascript onClick="deloutAll();">
								<br>
		</td>
                  <td width="48%"> 
                    <select name="selectusers" size="11" multiple style="width: 170px; height: 300px;" LANGUAGE=javascript ondblclick="return delout();">
                      <logic:iterate id="au" name="alls"> 
						<option value="${au.userid}">${au.realname}</option>
					 </logic:iterate>
				</select>
				</td>
              </tr>
            </table></td>
  </tr>
  <tr>
          <td align="center"><input type="Button" value="确定" onClick="savespeed();">&nbsp;&nbsp;
            <input type="button" name="Button" value="取消" onClick="window.close();"> </td>
  </tr>
 
</table>
 </form>
	</td>
  </tr>
</table>
</body>
</html>
