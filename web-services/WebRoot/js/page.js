var currPage=1;
var pageSize=5
var recordCount=0;//总记录
var pagenum=0; //  总页数

 function first(){
	 if(currPage==1)
		 return false;
	currPage=1;
	listResult();

	document.getElementById("first").innerHTML="首页";
	document.getElementById("prev").innerHTML="上一页";
	if(currPage!=pagenum){
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
		}

}
function prev(){
	if(currPage==1)
		 return false;
	else if(currPage>1)
	{
		currPage--;
		listResult();
	}
	if(currPage!=1){
		document.getElementById("first").innerHTML="<a href=\"javascript:void(0)\" onclick=\"first();\">首页</a>";
		document.getElementById("prev").innerHTML="<a href=\"javascript:void(0)\" onclick=\"prev();\">上一页</a>";
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
		}
		else{
			document.getElementById("first").innerHTML="首页";
			document.getElementById("prev").innerHTML="上一页";
		}
}
function next(){
	if(currPage==pagenum)
		 return false;
	else if(currPage<pagenum)
	{
		currPage++;
		listResult();
	}

		if(currPage!=pagenum){
		document.getElementById("first").innerHTML="<a href=\"javascript:void(0)\" onclick=\"first();\">首页</a>";
		document.getElementById("prev").innerHTML="<a href=\"javascript:void(0)\" onclick=\"prev();\">上一页</a>";
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
		}
		else{
			document.getElementById("next").innerHTML="下一页";
			document.getElementById("last").innerHTML="末页";
		}
}

function last(){
	if(currPage==pagenum)
		 return false;
	currPage=pagenum;
	listResult();

	if(currPage!=1){
		document.getElementById("first").innerHTML="<a href=\"javascript:void(0)\" onclick=\"first();\">首页</a>";
		document.getElementById("prev").innerHTML="<a href=\"javascript:void(0)\" onclick=\"prev();\">上一页</a>";
		}
	document.getElementById("next").innerHTML="下一页";
	document.getElementById("last").innerHTML="末页";
}

function pageno(){			//跳转
	var pageno=$F("pageno");
	if(IsNumber(pageno)){
		if(pageno>pagenum)
			currPage=pagenum;
		else if(pageno<=0)
			currPage=1;
		else
			currPage=pageno;
	}
	else
	{
		alert("请输入数字");
		return false;
	}
	listResult();


	if(currPage==1&&currPage==pagenum)
	{
		document.getElementById("first").innerHTML="首页";
		document.getElementById("prev").innerHTML="上一页";
		document.getElementById("next").innerHTML="下一页";
		document.getElementById("last").innerHTML="末页";
	}
	else if(currPage==1&&currPage!=pagenum)
	{
		document.getElementById("first").innerHTML="首页";
		document.getElementById("prev").innerHTML="上一页";
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
	}
	else if(currPage==pagenum&&currPage!=1)
	{
		//alert("afdf");
		document.getElementById("first").innerHTML="<a href=\"javascript:void(0)\" onclick=\"first();\">首页</a>";
		document.getElementById("prev").innerHTML="<a href=\"javascript:void(0)\" onclick=\"prev();\">上一页</a>";
		document.getElementById("next").innerHTML="下一页";
		document.getElementById("last").innerHTML="末页";
	}
	else if(currPage>1&&currPage<pagenum)
	{
		document.getElementById("first").innerHTML="<a href=\"javascript:void(0)\" onclick=\"first();\">首页</a>";
		document.getElementById("prev").innerHTML="<a href=\"javascript:void(0)\" onclick=\"prev();\">上一页</a>";
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
	}
}

function page1(){		//第一次加载
	//alert("dafdasfdsaf========="+pagenum);
	document.getElementById("first").innerHTML="首页";
	document.getElementById("prev").innerHTML="上一页";
	//alert("dafdasfdsaf========="+pagenum);
	
	if(pagenum>1){
		//alert("dafdasfdsaf========="+pagenum);
		document.getElementById("next").innerHTML="<a href=\"javascript:void(0)\" onclick=\"next();\">下一页</a>";
		document.getElementById("last").innerHTML="<a href=\"javascript:void(0)\" onclick=\"last();\">末页</a>";
	}
	else
	{
		document.getElementById("next").innerHTML="下一页";
		document.getElementById("last").innerHTML="末页";
	}

}