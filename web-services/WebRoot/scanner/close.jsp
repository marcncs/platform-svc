<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'close.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script language="javascript">
	var bb = setInterval("winClose(1);",1);
	function winClose(isRefrash) 
	{ 
	window.returnValue = isRefrash; 
	if (window.opener){
		window.opener.location.reload();
		//window.opener.location.src=window.opener.location.src;
	}
	window.close(); 
	} 

</script>
  </head>
  
  <body>
   <input type="submit" name="Submit" value="" onClick="winClose(1);"> <br>
  </body>
</html>
