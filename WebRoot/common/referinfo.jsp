<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8"%>
<%@include file="../common/tag.jsp"%>
<html>
	<head>
		<title></title>
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
			
			for (i; i < f1.selectusers.length; i++)
			{
				if (f1.selectusers[i].selected == true)
				{		
					if (!isinlist(f1.selectusers[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectusers[i].value;
						oOption.text = f1.selectusers[i].text;
						f1.selectreferusers.add(oOption);	
						f1.selectusers.remove(i);
						i--;
					}else{
						f1.selectusers.remove(i);
						i--;
					}
				}
			}
		}
		
		function addAll(){
			for (var i=0; i < f1.selectusers.length; i++)
			{
				if (!isinlist(f1.selectusers[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectusers[i].value;
					oOption.text = f1.selectusers[i].text;
					f1.selectreferusers.add(oOption);
					f1.selectusers.remove(i);
					i--;
				}else{
					f1.selectusers.remove(i);
					i--;
				}

			}
		}
	
		function isinlist(value)
		{
			var i = 0;
			for (i; i < f1.selectreferusers.length; i++)
			{
				if (f1.selectreferusers[i].value == value)
				{
					return true;
				}
			}
		
			return false;
		}
		
		
	
	
		function delout()
		{
			var i = 0;
			for (i; i < f1.selectreferusers.length; i++)
			{
				if (f1.selectreferusers[i].selected == true)
				{
					if (!isinlisttwo(f1.selectreferusers[i].value))
					{
						var oOption = document.createElement("OPTION");
						oOption.value= f1.selectreferusers[i].value;
						oOption.text = f1.selectreferusers[i].text;
						f1.selectusers.add(oOption);
						f1.selectreferusers.remove(i);
						i--;
					}else{
						f1.selectreferusers.remove(i);
						i--;
					}					
				}
			}
		}
		
		function isinlisttwo(value)
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
		
		function deloutAll(){
		
			var i = 0;
			for (i; i < f1.selectreferusers.length; i++)
			{
				if (!isinlisttwo(f1.selectreferusers[i].value))
				{
					var oOption = document.createElement("OPTION");
					oOption.value= f1.selectreferusers[i].value;
					oOption.text = f1.selectreferusers[i].text;
					f1.selectusers.add(oOption);
					f1.selectreferusers.remove(i);
					i--;
				}else{
					f1.selectreferusers.remove(i);
					i--;
				}
			}
		
		}
		
		
		function savespeed(){
			var str = "";
			var i = 0;
			var count = f1.selectreferusers.length;
			if(count <=0 ){
				alert("请选择用户");
				return false;
			}
			for (i; i < count; i++)
			{
				if(str==""){
					str = f1.selectreferusers[i].value;
				}else{
					str += ","+f1.selectreferusers[i].value;
				}
			}
			f1.speedstr.value = str;
			f1.action="../common/saveReferInfoAction.do";
			return true;
		}
		
		function showusers(originalRequest){
			for(var i =0 ; i < f1.selectusers.length ; i++){
				f1.selectusers.remove(i);
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
					f1.selectusers.add(oOption);
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
			document.getElementById("MakeDeptID").value="";
			document.getElementById("deptname").value="";
		}
		function dshow(obj){
			getUsersByDept(obj.value);
		}

</script>
	</head>
	<body>
		<div style="text-align: center;">
			<form action="../common/saveReferInfoAction.do"
				onSubmit="return savespeed();" method="post" id="f1">
				<table width="500px;" border="0">
				<tr>
					<th colspan="3">
						<c:choose>
							<c:when test="${ReferType=='WorkReport'}">提交工作报告</c:when>
							<c:when test="${ReferType=='ServiceAgreement'}">分配服务预约</c:when>
							<c:when test="${ReferType=='Task'}">分配任务对象</c:when>
							<c:when test="${ReferType=='Affiche'}">公告浏览对象</c:when>
						</c:choose>
					
					</th>
				</tr>
					<tr>
						<td colspan="3">
						<table>
						<tr>
						<td width="60">机构名称：</td>
						<td align="left">
							<input type="hidden" id="speedstr" name="speedstr">
							<input id="MakeOrganID" type="hidden" value="${organid}">
							<script language="javascript">
								 document.getElementById('MakeOrganID').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				getUsersByOid($F('MakeOrganID'));
			});
								var pstree = new WebFXLoadTree("${organname}","../organStrutsAction.do?id=${organid}","javascript:show('${organid}')");
								CreateTreeSelect('MakeOrganID','organname',pstree,'${organname}',300);
							</script>
						</td>
						<td>&nbsp;</td>
					</tr>
                    <tr>
						<td width="60">部门名称：</td>
						<td align="left"><input type="hidden" name="MakeDeptID" id="MakeDeptID"><input type="text" name="deptname" id="deptname"
								onClick="selectDUW(this,'MakeDeptID',$F('MakeOrganID'),'d')"
								value="" onChange="getUsersByDept(this);" readonly>
                          <script type="text/javascript">
			document.getElementById('MakeDeptID').attachEvent('onpropertychange',function(o){
				if(o.propertyName!='value')return;
				getUsersByDept($F('MakeDeptID'));
			});
		    </script>       
                                </td>
						<td>&nbsp;</td>
					</tr>
					</table>
						</td>
					</tr>
					<tr>
						<td>
							<select name="selectusers" size="11" multiple
								style="width: 199px; height: 300px;" LANGUAGE=javascript
								ondblclick="return addin();">
                                <logic:iterate id="u" name="listUsers" >  	
                                <logic:iterate id="au" name="existUsers" > 
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
						<td>
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
						<td>
							<select name="selectreferusers" size="11" multiple
								style="width: 199px; height: 300px;" LANGUAGE=javascript
								ondblclick="return delout();">
								<c:forEach var="u" items="${existUsers}">
									<option value="${u.userid}">
										${u.realname}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="3" style="text-align: center;">
							<input type="submit" id="btn" value="确定">
						</td>
					</tr>
				</table>
			</form>
		</div>


	</body>
</html>
