<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page contentType="text/html; charset=utf-8" %>

<html>
<head>
<title>WINDRP-分销系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/ss.css" rel="stylesheet" type="text/css">
<script language="javascript">
 var parray=new Array();
 
Array.prototype.remove=function(dx) { 
    if(isNaN(dx)||dx>this.length){
		return false;
	} 
    for(var i=0,n=0;i<this.length;i++){ 
        if(this[i]!=this[dx]){ 
            this[n++]=this[i];
        } 
    } 
    this.length-=1;
} 
</script>
</head>

<body>
<iframe name="productframe" frameborder="0" height="100%" width="100%" scrolling="no" src="selectProviderProductAction.do?pid=${pid}"></iframe>
</body>
</html>
