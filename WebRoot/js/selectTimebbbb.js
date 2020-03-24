var hour = new Array("0", "1", "2",
    "3", "4", "5", "6", "7", "8",
    "9", "10", "11","12","13","14","15","16","17","18","19","20","21","22","23");
var minute = new Array("0", "1", "2",
    "3", "4", "5", "6", "7", "8",
    "9", "10", "11","12","13","14","15","16","17","18","19","20","21","22","23",
	"24","25","26","27","28","29","30","31","32","33","34","35","36","37","38",
	"39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57",
	"58","59");
var second = new Array("0", "1", "2",
    "3", "4", "5", "6", "7", "8",
    "9", "10", "11","12","13","14","15","16","17","18","19","20","21","22","23",
	"24","25","26","27","28","29","30","31","32","33","34","35","36","37","38",
	"39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57",
	"58","59");

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

function ShowTimeLayer(t,l) {

	if(oldTimeObject==null)oldTimeObject=getTimeObject;//付值
		if(TimeLayer.style.visibility != "visible" && oldTimeObject==getTimeObject){//判断是否操作同一表
				//if(l>480)l=l-131;
				TimeLayer.style.top = t+document.body.scrollTop;
				TimeLayer.style.left = l+document.body.scrollLeft;
				TimeLayer.style.visibility = "visible";
				oldTimeObject=getTimeObject;
								}
	else if(oldTimeObject==getTimeObject){
		HideTimeLayer();
	}
	else{
				//if(l>480)l=l-131;
				TimeLayer.style.top = t+document.body.scrollTop;
				TimeLayer.style.left = l+document.body.scrollLeft;
				TimeLayer.style.visibility = "visible";
				oldTimeObject=getTimeObject;
	}
}
//定义div显示日期;
function getTimeLayer(){

	document.write("<div id=\"TimeLayer\" style=\"position:absolute; width:131; z-index:2; border:1px inset black; background-color: #f0f8ff; layer-background-color: #f0f8ff; visibility: hidden; height: 38; left:58; top:137;\"><table cellpadding=\"0\" align=\"center\"> <tr><td colspan=7 align=CENTER>时:<select id=\"hour\" name=\"hour\" class=\"smallSel\">");
	 for (var intH = 0; intH < hour.length;  intH++)
	 document.write("<OPTION VALUE= " + hour[intH] + ">" + hour[intH]);
	document.write("</select>分:<select id=\"minute\"  name=\"minute\" class=\"smallSel\">");
	 for (var intM = 0; intM < minute.length; intM++)
	 document.write("<OPTION VALUE= " + intM + ">" + minute[intM]);
	document.write("</select>秒:<select id=\"second\" name=\"second\" class=\"smallSel\">");
	for (var intS = 0; intS < second.length; intS++)
	 document.write("<OPTION VALUE= " + intS + ">" + second[intS]);
	document.write("</select></td></tr>");
	document.write("<tr><td>");
	document.write("<input type=\"submit\" name=\"Submit\" onclick='getTime();' value=\"确定\">");
	document.write("<input type=\"button\" name=\"cancel\" onclick='HideTimeLayer();' value=\"取消\">");

	document.write("</table></div>");

}
//显示css
document.write("<style>TABLE	{font-family:宋体,MS SONG,SimSun,tahoma,sans-serif; font-size:9pt;border:0px}.drpdwn	{font-family:宋体,MS SONG,SimSun,tahoma,sans-serif;font-size:9pt;color:#000066;background-color:#FFFFFF} SELECT.smallSel{    BACKGROUND-COLOR: #ffffff;    COLOR: #000080;    FONT-SIZE: 9pt} .normal{BACKGROUND: #ffffff} .today {font-weight:bold;BACKGROUND: #6699cc} .satday{color:green} .sunday{color:red} .days {font-weight:bold} .Arraw {color:#0000BB; cursor:hand; font-family:Webdings; font-size:9pt}</style>");

getTimeLayer();//显示div

function selectTime(x){
getTimeObject=x;
var xx=event.clientX;//取得x坐标;
var yy=event.clientY;//取得y坐标;
//newCalendar();
ShowTimeLayer(yy,xx);//显示出div
}
//cument.body.onclick=mm();
function mm(){
//ert();
}