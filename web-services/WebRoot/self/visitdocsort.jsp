<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>
<%@include file="../common/tag.jsp"%>
<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="../css/xtree.css" type="text/css">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<SCRIPT language="javascript" src="../js/prototype.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/selectduw.js"> </SCRIPT>
<SCRIPT language="javascript" src="../js/xtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xloadtree.js"></SCRIPT>
<SCRIPT language="javascript" src="../js/xmlextras.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" src="../js/treeselect.js"></SCRIPT>

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
		f1.action = "visitDocSortAction.do"; 
			//alert(str);
		f1.submit();
	}
	
	function showusers(originalRequest){
			for(var i =0 ; i < f1.selectallusers.length ; i++){
				f1.selectallusers.remove(i);
				i--;
			}
			var data = eval('(' + originalRequest.responseText + ')');
			var lk = data.userslist;
			if ( lk == undefined ){
			}else{	
				for(var j=0; j<lk.length; j++){
					var oOption = document.createElement("OPTION");
					oOption.value = lk[j].userid;
					oOption.text = lk[j].realname;
					f1.selectallusers.add(oOption);
				}
			}
		}
		
		function getUsersByOid(v_oid){

			var url = '../users/ajaxOrganUsersAction.do?oid='+v_oid;
			var pars = '';
			var myAjax = new Ajax.Request(
						url,
						{method: 'get', parameters: pars, onComplete: showusers}
						);	
		}
		
		function getUsersByDept(v_did){
			var url = '../users/ajaxDeptUsersAction.do?did='+v_did;
			var pars = '';
			var myAjax = new Ajax.Request(
						url,
					{method: 'get', parameters: pars, onComplete: showusers}
					);	
		}
		

		function tshow(organid){
			getUsersByOid(organid.value);
		}
		

		function dshow(obj){			
			getUsersByDept(obj.value);
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
                  <td width="772"> 许可文件类型 
                    <input name="dsid" type="hidden" id="dsid" value="${dsid}">
					<INPUT NAME="speedstr" TYPE="hidden">
					<INPUT NAME="uscount" TYPE="hidden">
				  </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td>
    
          
    <table width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
			<tr>
            	<td width="14%">&nbsp;</td>
            	<td  colspan="4">
        <table>
        <tr>
            <td width="60">机构名称：</td>
            <td align="left">
            <input id="MakeOrganID" type="hidden" value="${porganid}">
            <script language="javascript">
            document.getElementById('MakeOrganID').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				getUsersByOid($F('MakeOrganID'));
			});
			var pstree = new WebFXLoadTree("${porganname}","../organStrutsAction.do?id=${porganid}","javascript:show('${porganid}')");
            CreateTreeSelect('MakeOrganID','organname',pstree,'${porganname}',300);
            </script>
            </td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td width="60">部门名称：</td>
            <td align="left"><input type="hidden" style="z-index:800" name="MakeDeptID" id="MakeDeptID" value="" ><input type="text" name="deptname" id="deptname"
            onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d')"
            value="" onChange="getUsersByDept(this);" readonly></td>
            <script type="text/javascript">
			document.getElementById('MakeDeptID').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				getUsersByDept($F('MakeDeptID'));
			});
		    </script>
            <td>&nbsp;</td>
        </tr>
    </table>
                </td>
            </tr>            
            <tr > 
            	  <td width="14%"></td>
                  <td width="30%"> 
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
                  <td width="11%"> 
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
                <td width="8%"></td>
                  <td width="37%"> 
                    <select name="selectusers" size="11" multiple style="width: 170px; height: 300px; z-index:5" LANGUAGE=javascript ondblclick="return delout();">
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
