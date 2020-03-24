<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>

		<link href="../css/dtree.css" rel="StyleSheet" type="text/css">
		<script type="text/javascript" src="../js/dtree.js"></script>
		<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
	
}

.style1 {
	color: #00009C
}
-->
</style>
	</head>


	<body>
		<table width="100%" border="0" cellpadding="0"
			cellspacing="0" class="title-back">
			<tr>
				<td style="text-align: left; padding-left: 12px;">资源管理器</td>
			</tr>
		</table>
		<div id="menuTree" class="dtree" style="overflow: auto; height: 95%; width: 220px">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
					<!--  
						<div id="idDiv1" style="position: absolute; z-index: 1; padding-bottom: 30px;" >
						<div style="height: 15px; width:160px; background-image:url('../images/1.gif'); position :absolute; top: 0px;left: 0px; z-index: 2;" id="idDiv2">
						</div>
					-->
					<div id="idDiv1" style="z-index: 1; padding-bottom: 30px;" >
					<div style="height: 15px; width:100%; background-image:url('../images/1.gif'); top: 0px;left: 0px; z-index: 2;" id="idDiv2">
					</div>
						<script type='text/javascript'>
						d = new dTree('d');
						d.config.inOrder=true;
						d.config.useLines=false;
						d.config.useStatusText=true;
						d.config.target='main';
						d.config.useIcons=false;
						d.config.useLines=true;
						 <%  
						 	if(localeLanguage != ""){
						 		if(localeLanguage == "en"){%>
						 			<logic:iterate id="c" name="mls" >
						 				${c.lmenuurlen}
						 			</logic:iterate>
						 		<%}else{%>
						 			<logic:iterate id="c" name="mls" >
						 				${c.lmenuurl}
						 			</logic:iterate>
						 		<%}
						 	}
						 %>
						document.write(d);
						</script>
					</div>
					</td>
				</tr>
			</table>
			
		</div>
	

	
	</body>
</html>
