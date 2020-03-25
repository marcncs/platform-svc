 var getTimeObject,oldTimeObject;//取得当前对象
 function getTime() {
    var sTime;
    //这段代码处理鼠标点击的情况
    //if ("TD" == event.srcElement.tagName)
    //   if ("" != event.srcElement.innerText)
	// {//从这儿可以改显示日期的格式
	
	   sTime = document.all.hour.value + ":" + document.all.minute.value + ":" + document.all.second.value + "";
	  			getTimeObject.value=sTime;
			//	setUpdateFlag(getTimeObject);//触发事件，保存数据
				HideTimeLayer();

	 	// }
 }

function HideTimeLayer() {
	TimeLayer.style.visibility = "hidden";
}

function TimeLayerShow(){
	TimeLayer.style.visibility = "visible";
}

function RealTime(){
	var d = new Date();
	var hour = d.getHours();
	var minute = d.getMinutes();
	var second = d.getSeconds();
	if(hour<10) hour=0+hour.toString();
	if(minute<10) minute=0+minute.toString();
	if(second<10) second=0+second.toString();
	document.getElementById('realtime').value = hour + ":" + minute + ":" + second;
}

function ShowTimeLayer(t,l) {   // yy xx

	TimeLayer.style.visibility = "visible" ;
	TimeLayer.style.top = l+23;
	TimeLayer.style.left = t;
}
//定义div显示日期;
function getTimeLayer(){

	document.write("<div id=\"TimeLayer\">");
	document.write("<h1><span><a href=javascript:void(0) onclick=HideTimeLayer();>&times;</a></span>插入时间</h1>");
	document.write("<div>小时:<select id=\"hour\" name=\"hour\">");
	var d = new Date();
	 for (var i=0; i<24;  i++){
 		i<10?j=0+i.toString():j=i;
		if (d.getHours().toString()==i)
		{
			document.write("<OPTION VALUE= " + j + " selected>" + j + "</option>");
		}else{
			document.write("<OPTION VALUE= " + j + ">" + j + "</option>");
		}
	 }
	document.write("</select></div><div>分钟:<select id=\"minute\"  name=\"minute\" class=\"smallSel\">");
	 for (var i=0; i<60;  i++){
		i<10?j=0+i.toString():j=i;
		if (d.getMinutes().toString()==i)
		{
			 document.write("<OPTION VALUE= " + j + " selected>" + j + "</option>");
		}else{
			 document.write("<OPTION VALUE= " + j + ">" + j + "</option>");
		}
	}
	document.write("</select></div><div>秒钟:<select id=\"second\" name=\"second\" class=\"smallSel\">");
	 for (var i=0; i<60;  i++){
 		i<10?j=0+i.toString():j=i;
		if (d.getSeconds().toString() == i)
		{
			document.write("<OPTION VALUE= " + j + " selected>" + j + "</option>");
		}else{
			document.write("<OPTION VALUE= " + j + ">" + j + "</option>");
		}
	}
	document.write("</select></div><div><input type=button value='15:43:23' id='realtime' onclick='getTimeObject.value=this.value;HideTimeLayer();'></div>");
	document.write("<div><a href=javascript:void(0) onclick='getTime();' style='color:#000'>确定</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
	document.write("<a href=javascript:void(0) onclick='HideTimeLayer();' style='color:#000'>取消</a></div>");

	document.write("</div>");

}
//显示css
document.write("<style>#TimeLayer {background-color:#E9F3FF;position:absolute; width:144px; z-index:9999; border:2px solid #448feb; visibility: hidden; height: 140px;text-align:center;font-size:12px;} #TimeLayer select {font-size:12px; background-color:#E9F3FF} #TimeLayer h1 {background-color:#448feb;font-weight:normal;height:20px;line-height:20px;text-align:left;padding:0px 5px 0px 5px;color:#fff;font-size:12px;margin:1px 1px 10px 1px;} #TimeLayer h1 span {float:right} #TimeLayer div{margin-bottom:10px;} #TimeLayer a {text-decoration: none;color:#fff}</style>");

getTimeLayer();//显示div

function selectTime(x){
setInterval("RealTime()",1000);
getTimeObject=x;
//var xx=event.clientX;//取得x坐标;
//var yy=event.clientY;//取得y坐标;
var xx=x.offsetTop;//取得x坐标;
var yy=x.offsetLeft;//取得y坐标;
//alert(xx+"-----"+yy);
//newCalendar();
ShowTimeLayer(yy,xx);//显示出div
}
//cument.body.onclick=mm();
function mm(){
//ert();
}