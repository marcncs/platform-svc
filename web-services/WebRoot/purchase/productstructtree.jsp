<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/struts-bean" prefix="bean"%>
<%@ taglib uri="/tags/struts-logic" prefix="logic"%>
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
		
		<div style="overflow: auto; height: 95%; width: 160">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
					
					<div id="idDiv1" style="position: absolute; z-index: 1; padding-bottom: 30px;" >
						<script type='text/javascript'>
						d = new dTree('d');
						d.config.inOrder=true;
						d.config.useLines=false;
						d.config.useStatusText=true;
						d.config.target='main';
						d.config.useIcons=false;
						d.config.useLines=true;
						
						<logic:iterate id="c" name="mls" >
						 
						${c.lmenuurl}
						</logic:iterate>
						document.write(d);
						</script>
					</div>
					</td>
				</tr>
			</table>
			
		</div>
	
	</body>
</html>
