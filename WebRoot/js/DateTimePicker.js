//<script>
var DTP_MonthArray=new Array("һ��","����","����","����","����","����","����","����","����","ʮ��","ʮһ��","ʮ����");
var DTP_WeekArray=new Array("����һ","���ڶ�","������","������","������","������","������");
var DTP_Year_Str="��";
var DTP_Month_Str="��";
var DTP_Day_Str="��";
var DTP_Hour_Str="ʱ";
var DTP_Minute_Str="��";
var DTP_Second_Str="��";
var DTP_Hint_Str="����ѡ��";
var DTP_privMonth="��һ��";
var DTP_nextMonth="��һ��";
var DTP_InputNull_Str="����Ϊ��";
var DTP_Up_Char="��";
var DTP_Down_Char="  ";

var a_DTPickerCount = 0;
var currentDateTimePickerComponent = null;
var null_Object = new Object();
null_Object.style = new Object();
null_Object.name = 'null';
var CurDateCssText = "";
var AvailableDateCssText ="";
var InvalidDateCssText = "";


function DTP_getOffsetLeft(src) {
	var set=0;
	if(src)	{
		if (src.offsetParent && src.offsetParent.tagName!="DIV")
			set+= src.offsetLeft +DTP_getOffsetLeft(src.offsetParent);
		
		if(src.tagName!="BODY")	{
			var x=parseInt(src.scrollLeft,10);
			if(!isNaN(x))
				set-=x;
		}
	}
	return set;
}

function DTP_getOffsetWidth(src) {
	var set=0;
	if(src)	{
		if (src.offsetParent && src.offsetParent.tagName!="DIV")
			set=DTP_getOffsetWidth(src.offsetParent);
		else set = src.offsetWidth;
	}
	return set;
}

function DTP_getOffsetTop(src) {
	var set=0;
	if(src)	{
		if (src.offsetParent && src.offsetParent.tagName!="DIV")
			set+= src.offsetTop+DTP_getOffsetTop(src.offsetParent);
		if(src.tagName!="BODY")	{
			var y=parseInt(src.scrollTop,10);
			if(!isNaN(y))
				set-=y;
		}
	}
	return set;
}

function createDateTimePicker(ComponentType,StyleID,lYear,lMonth,lDay,lhour,lminute,lsecond){
	var componentName = new String();
	componentName = "DTPicker_"+ (a_DTPickerCount++);

	// ComponentType : 0 date+time ; 1 date ; 2 time 
	if (ComponentType >2) ComponentType = 0;

	var dtObj = dtp_getDateTimeObject(ComponentType,lYear,lMonth,lDay,lhour,lminute,lsecond);

	document.write("<input type='hidden' id="+ componentName +"_value name="+ componentName +"_value PRestrict='' PRequired='' value=''>");
	
	document.write("<span id="+ componentName +" name="+componentName+" comname=DTPicker  onclick='return dtp_onclick();' onkeydown='return dtp_onkeydown();'>");	
	if (ComponentType ==0 || ComponentType ==1) {
		document.write("<input id="+componentName+"_Year name="+componentName+"_Year  type=text value="+ dtObj.year+" size=4 maxlength=4 onfocus=\"return dtp_focus('year');\" onblur=\"return dtp_blur('year');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('year');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Year_Str+"</font>");
		document.write("<input id="+componentName+"_Month name="+componentName+"_Month type=text value="+dtObj.month+" size=2 maxlength=2 onfocus=\"return dtp_focus('month');\" onblur=\"return dtp_blur('month');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('month');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Month_Str+"</font>");
		document.write("<input id="+componentName+"_Day name="+componentName+"_Day type=text value="+dtObj.day+" size=2 maxlength=2 onfocus=\"return dtp_focus('day');\" onblur=\"return dtp_blur('day');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('day');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Day_Str+"</font>");
	}
	if (ComponentType ==0 || ComponentType ==2) {
		document.write("<input id="+componentName+"_Hour name="+componentName+"_Hour  type=text value="+ dtObj.hour+" size=2 maxlength=2 onfocus=\"return dtp_focus('hour');\" onblur=\"return dtp_blur('hour');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('hour');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Hour_Str+"</font>");
		document.write("<input id="+componentName+"_Minute name="+componentName+"_Minute type=text value="+dtObj.minute+" size=2 maxlength=2 onfocus=\"return dtp_focus('minute');\" onblur=\"return dtp_blur('minute');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('minute');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Minute_Str+"</font>");
		document.write("<input id="+componentName+"_Second name="+componentName+"_Second type=text value="+dtObj.second+" size=2 maxlength=2 onfocus=\"return dtp_focus('second');\" onblur=\"return dtp_blur('second');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('second');\">");
		document.write("<font style='BACKGROUND-color:transparent;color:black;'>"+DTP_Second_Str+"</font>");
	}	
	document.write("<span style='BACKGROUND-color:transparent;width:5px;'></span>");
	document.write("<input readonly id="+ componentName +"_drop name="+ componentName +"_drop  onclick='return dtp_drop_onclick()' title='"+DTP_Hint_Str+"' value='"+DTP_Down_Char+"'></input>");
	document.write("</span>");
	
	document.write("<div id="+ componentName +"_optionDiv name="+ componentName +"_optionDiv uplinkfor="+componentName+" comname=DTPOption onclick='return dtp_optionDiv_onclick()'>");
	if (ComponentType ==0 || ComponentType ==1) {
		document.write("	<TABLE border=0 id="+ componentName +"_DropDownTable name="+ componentName +"_DropDownTable CELLSPACING=0 onclick='dtp_ddt_click();' ondblclick='dtp_ddt_dblclick();'>");
		document.write("		<TR id="+ componentName +"_DTPTitle name="+ componentName +"_DTPTitle>");
		document.write("			<TD><span id="+ componentName +"_DTPPrivBtn name="+ componentName +"_DTPPrivBtn onclick='dtp_monthChange(-1);' title='"+DTP_privMonth+"'><</span></TD><TD  align=center colspan=5><span id="+ componentName +"_DTPYearMonth name="+ componentName +"_DTPYearMonth>     2001</span></TD><TD><span id="+ componentName +"_DTPNextBtn name="+ componentName +"_DTPNextBtn onclick='dtp_monthChange(1);' title='"+DTP_nextMonth+"'>></span></TD></TR>");
		document.write("		<TR id="+ componentName +"_DTPWeek name="+ componentName +"_DTPWeek><TD id="+ componentName +"_DTPSunday name="+ componentName +"_DTPSunday>"+DTP_WeekArray[0]+"</TD><TD id="+ componentName +"_DTPMonday name="+ componentName +"_DTPMonday>"+DTP_WeekArray[1]+"</TD><TD id="+ componentName +"_DTPTuesday name="+ componentName +"_DTPTuesday>"+DTP_WeekArray[2]+"</TD><TD id="+ componentName +"_DTPWednesday name="+ componentName +"_DTPWednesday>"+DTP_WeekArray[3]+"</TD><TD id="+ componentName +"_DTPThursday name="+ componentName +"_DTPThursday>"+DTP_WeekArray[4]+"</TD><TD id="+ componentName +"_DTPFriday name="+ componentName +"_DTPFriday>"+DTP_WeekArray[5]+"</TD><TD id="+ componentName +"_DTPSaturay name="+ componentName +"_DTPSaturay>"+DTP_WeekArray[6]+"</TD></TR>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("		<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>");
		document.write("	</TABLE>");
	}
	document.write("	<table id="+ componentName +"_DTPTimeInput name="+ componentName +"_DTPTimeInput border=0 cellpadding=0 cellspacing=0><tr>");
	document.write("	<td><table id="+ componentName +"_DTPTimeTab name="+ componentName +"_DTPTimeTab border=0 ><tr>");
	document.write("	<td align=right width=60px nowrap><input id="+componentName+"_DTPHour name="+componentName+"_DTPHour  type=text value="+ dtObj.hour+" size=2 maxlength=2 onfocus=\"return dtp_focus('hour');\" onblur=\"return dtp_blur('hour');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('hour');\"><font style='BACKGROUND-color:transparent;'>"+DTP_Hour_Str+"</font></td>");
	document.write("	<td align=left width =20px nowrap><table border=0 cellpadding=0 cellspacing=0>");
	document.write("	<tr><td><span id="+ componentName +"_DTPHourUp name="+ componentName +"_DTPHourUp onclick='return dtp_hourChange(1)'>"+DTP_Up_Char+"</span></td></tr>");
	document.write("	<tr><td><span id="+ componentName +"_DTPHourDown name="+ componentName +"_DTPHourDown onclick='return dtp_hourChange(-1)'>"+DTP_Down_Char+"</span></td></tr>");
	document.write("	</table></td>");
	document.write("	<td align=right width=30px nowrap><input id="+componentName+"_DTPMinute name="+componentName+"_DTPMinute type=text value="+dtObj.minute+" size=2 maxlength=2 onfocus=\"return dtp_focus('minute');\" onblur=\"return dtp_blur('minute');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('minute');\"><font style='BACKGROUND-color:transparent;'>"+DTP_Minute_Str+"</font></td>");
	document.write("	<td align=left width =20px nowrap><table border=0 cellpadding=0 cellspacing=0>");
	document.write("	<tr><td><span id="+ componentName +"_DTPMinuteUp name="+ componentName +"_DTPMinuteUp onclick='return dtp_minuteChange(1)'>"+DTP_Up_Char+"</span></td></tr>");
	document.write("	<tr><td><span id="+ componentName +"_DTPMinuteDown name="+ componentName +"_DTPMinuteDown onclick='return dtp_minuteChange(-1)'>"+DTP_Down_Char+"</span></td></tr>");
	document.write("	</table></td>");
	document.write("	<td align=right width=30px nowrap><input id="+componentName+"_DTPSecond name="+componentName+"_DTPSecond type=text value="+dtObj.second+" size=2 maxlength=2 onfocus=\"return dtp_focus('second');\" onblur=\"return dtp_blur('second');\" onkeypress=\"return dtp_KeyFilter('number');\" onkeydown=\"return dtp_keyDown('second');\"><font style='BACKGROUND-color:transparent;'>"+DTP_Second_Str+"</font></td>");
	document.write("	<td align=left width =20px nowrap><table border=0 cellpadding=0 cellspacing=0>");
	document.write("	<tr><td><span id="+ componentName +"_DTPSecondUp name="+ componentName +"_DTPSecondUp onclick='return dtp_secondChange(1)'>"+DTP_Up_Char+"</span></td></tr>");
	document.write("	<tr><td><span id="+ componentName +"_DTPSecondDown name="+ componentName +"_DTPSecondDown onclick='return dtp_secondChange(-1)'>"+DTP_Down_Char+"</span></td></tr>");
	document.write("	</table></td></tr></table></td>");
	document.write("	<td  width=100px  nowrap align=center><input type=checkbox id="+ componentName +"_InputNull name="+ componentName +"_InputNull onclick='return dtp_inputNull()'><label id="+ componentName +"_LabelNull  name="+ componentName +"_LabelNull  for="+ componentName +"_InputNull style='font :9pt'>"+DTP_InputNull_Str+"</label></td></tr></table>");

	document.write("</div>");	

	var dtp=document.all(componentName);
	dtp_initDTPicker(dtp,dtObj,StyleID,eval(componentName+"_optionDiv"));
	return dtp;


}

function dtp_getDateTimeObject(DT_type,lYear,lMonth,lDay,lhour,lminute,lsecond) {
	var dt=new Date();
	if(lYear==null || isNaN(parseInt(lYear,10)))
		lYear=dt.getFullYear();
	else
		lYear=parseInt(lYear,10);
	if(lMonth==null || isNaN(parseInt(lMonth,10)))
		lMonth=dt.getMonth();
	else
		lMonth=parseInt(lMonth,10)-1;
	if(lDay==null || isNaN(parseInt(lDay,10)))
		lDay=dt.getDate();
	else
		lDay=parseInt(lDay,10);
	if(lhour==null || isNaN(parseInt(lhour,10)))
		lhour=dt.getHours();
	else
		lhour=parseInt(lhour,10);
	if(lminute==null || isNaN(parseInt(lminute,10)))
		lminute=dt.getMinutes();
	else
		lminute=parseInt(lminute,10);
	if(lsecond==null || isNaN(parseInt(lsecond,10)))
		lsecond=dt.getSeconds();
	else
		lsecond=parseInt(lsecond,10);
	dt=new Date(lYear,lMonth,lDay,lhour,lminute,lsecond);
	dtO = new Object();
	dtO.type = DT_type;
	dtO.isNull = false;
	dtO.fullyear = dt.getFullYear();
	dtO.year = dt.getYear();
	dtO.month = dt.getMonth() +1;
	dtO.day = dt.getDate();
	dtO.hour = dt.getHours();
	dtO.minute = dt.getMinutes();
	dtO.second = dt.getSeconds();
	dtO.weekStr = DTP_WeekArray[dt.getDay()];
	dtO.monthStr =DTP_MonthArray[dt.getMonth()];
	dtO.DT = dt;
	
	return dtO;
}

function dtp_initDTPicker(dtp,dtObj,StyleID,dtp_optionDiv) {
	if (dtp != null ) {
		// component private property
			dtp.styleID = StyleID ;
			dtp.eventElement = null;
			dtp.oldDocOnclick = null;
			dtp.oldCell = null;
			dtp.oldCellCssText = "";
			dtp.TopGap = 0;
			dtp.LeftGap = 0;

		// component private method
			dtp.getComponent= dtp_GetComponent;
			dtp.initCssSet = dtp_InitCssSet ;
			
			dtp.displayCurCell = dtp_DisplayCurCell;
		
			dtp.locateSenderRoot=dtp_LocateSenderRoot;
			dtp.locateSenderByTag=dtp_LocateSenderByTag;
			dtp.overrideDocOnclick = dtp_OverrideDocOnclick;
			dtp.normal_on_Drop_click = dtp_Normal_Drop_onclick;
		
		// component public property
			dtp.DateTimeObject = dtObj ;
		
		// component public method
			dtp.getDateTime = dtp_GetDateTime ;
		
			dtp.getYearInput = dtp_GetYearInput ;
			dtp.getMonthInput = dtp_GetMonthInput ;
			dtp.getDayInput = dtp_GetDayInput ;
			dtp.getHourInput = dtp_GetHourInput ;
			dtp.getMinuteInput = dtp_GetMinuteInput ;
			dtp.getSecondInput = dtp_GetSecondInput ;
			dtp.getDropBtn = dtp_GetDropBtn ;
			dtp.getOptionDiv = dtp_GetOptionDiv ;
			dtp.getDropDownTable = dtp_GetDropDownTable ;
			dtp.getDTPTitle = dtp_GetDTPTitle ;
			dtp.getDTPPrivBtn = dtp_GetDTPPrivBtn ;
			dtp.getDTPYearMonth = dtp_GetDTPYearMonth ;
			dtp.getDTPNextBtn = dtp_GetDTPNextBtn ;
			dtp.getDTPWeek = dtp_GetDTPWeek ;
			dtp.getDTPSunday = dtp_GetDTPSunday ;
			dtp.getDTPMonday = dtp_GetDTPMonday ;
			dtp.getDTPTuesday = dtp_GetDTPTuesday ;
			dtp.getDTPWednesday = dtp_GetDTPWednesday ;
			dtp.getDTPThursday = dtp_GetDTPThursday ;
			dtp.getDTPFriday = dtp_GetDTPFriday ;
			dtp.getDTPSaturay = dtp_GetDTPSaturay ;

			dtp.getDTPTimeInput = dtp_GetDTPTimeInput;
			dtp.getDTPTimeTab = dtp_GetDTPTimeTab;
			dtp.getDTPHour = dtp_GetDTPHour ;
			dtp.getDTPMinute = dtp_GetDTPMinute ;
			dtp.getDTPSecond = dtp_GetDTPSecond ;
			
			dtp.getDTPHourUp = dtp_GetDTPHourUp ;
			dtp.getDTPHourDown = dtp_GetDTPHourDown ;
			dtp.getDTPMinuteUp = dtp_GetDTPMinuteUp ;
			dtp.getDTPMinuteDown = dtp_GetDTPMinuteDown ;
			dtp.getDTPSecondUp = dtp_GetDTPSecondUp ;
			dtp.getDTPSecondDown = dtp_GetDTPSecondDown ;
			
			dtp.getValueOBJ = dtp_GetValueOBJ;
			dtp.setPRestrictValue = dtp_SetValueRestrict;
			dtp.setRequired = dtp_SetRequired;
			
			dtp.getInputNull = dtp_GetInputNull;
			dtp.getLabelNull = dtp_GetLabelNull;
			
			dtp.setTopGap = dtp_SetTopGap;
			dtp.setLeftGap = dtp_SetLeftGap;
			dtp.setLineWidth = dtp_SetLineWidth;

			dtp.setCssText = dtp_SetCssText;
			
			dtp.setCurDate = dtp_SetCurDate;
			dtp.setCurTime = dtp_SetCurTime;
			dtp.setCurDateTime = dtp_SetCurDateTime;
			
			
			dtp.refreshCurDateTable = dtp_RefreshCurDateTable ;
			
			dtp.setNull = dtp_SetNull;
			dtp.isNull = dtp_IsNull;
			dtp.setNullCheck = dtp_SetNullCheck;
			
			dtp.setReadOnly = dtp_SetReadOnly;
			
		// component event

			dtp.on_DateTimeChange = null;
			dtp.on_OptionUp = null;			

		// component review

			dtp.initCssSet();
			
			dtp.getDropBtn().innerText = DTP_Down_Char;
			dtp.getInputNull().checked = false;
			dtp.setCurDateTime(dtObj.fullyear,dtObj.month,dtObj.day,dtObj.hour,dtObj.minute,dtObj.second);
	}
}

function dtp_GetDateTime(datetimeFormat) {
	var dtStr = datetimeFormat ;
	var dtObj =this.DateTimeObject ;
	
	if (dtObj.isNull) return "";
	
	if(dtStr==null || typeof(dtStr)!="string") {
		switch  (dtObj.type)  {
			case 1 : dtStr="dd-MM-yyyy"  ; break;
			case 2 : dtStr="hh:mm:ss"  ; break;
			default : dtStr="dd-MM-yyyy hh:mm:ss"  ;
		}
	}
	
	if (dtObj.type ==0 || dtObj.type == 1) {
		dtStr=dtStr.replace(/yyyy/ig,dtObj.fullyear );
		var y=""+dtObj.year;
		if(y.length>2) {
			y=y.substring(y.length-2,y.length);
		}
		dtStr=dtStr.replace(/yy/g,y);
		dtStr=dtStr.replace(/MM/g,dtObj.month);
		dtStr=dtStr.replace(/dd/g,dtObj.day);
	}
	
	if (dtObj.type ==0 || dtObj.type == 2) {
		dtStr=dtStr.replace(/hh/g,dtObj.hour);
		dtStr=dtStr.replace(/mm/g,dtObj.minute);
		dtStr=dtStr.replace(/ss/g,dtObj.second);
	}
	return dtStr;
}

function dtp_LocateSenderRoot(txtSenderType) {
	var aCom = event.srcElement;
	while (aCom !=null ) {
		if (aCom.comname==txtSenderType) {
			break;
		}
		if (aCom.uplinkfor ==null )aCom = aCom.parentElement;
		else aCom=eval(aCom.uplinkfor);
	}
	return aCom;
}

function dtp_LocateSenderByTag(txtTag) {
	var aCom = event.srcElement;
	while (aCom !=null ) {
		if (aCom.tagName==txtTag) {
			break;
		}
		aCom = aCom.parentElement;
	}
	return aCom;
}

function dtp_GetComponent(txtStr){
  var theComponent = document.all(this.name + txtStr);
  return (theComponent ==null)?null_Object:theComponent ;
}

function dtp_GetYearInput() {
  return this.getComponent("_Year");
}

function dtp_GetMonthInput() {
  return this.getComponent("_Month");
}

function dtp_GetDayInput() {
  return this.getComponent("_Day");
}

function dtp_GetHourInput() {
  return this.getComponent("_Hour");
}

function dtp_GetMinuteInput() {
  return this.getComponent("_Minute");
}

function dtp_GetSecondInput() {
  return this.getComponent("_Second");
}

function dtp_GetDropBtn() {
  return this.getComponent("_drop");
}

function dtp_GetOptionDiv() {
  return this.getComponent("_optionDiv");
}

function dtp_GetDropDownTable() {
  return this.getComponent("_DropDownTable");
}

function dtp_GetDTPTitle() {
  return this.getComponent("_DTPTitle");
}

function dtp_GetDTPPrivBtn() {
  return this.getComponent("_DTPPrivBtn");
}

function dtp_GetDTPYearMonth() {
  return this.getComponent("_DTPYearMonth");
}

function dtp_GetDTPNextBtn() {
  return this.getComponent("_DTPNextBtn");
}

function dtp_GetDTPWeek() {
  return this.getComponent("_DTPWeek");
}

function dtp_GetDTPSunday() {
  return this.getComponent("_DTPSunday");
}

function dtp_GetDTPMonday() {
  return this.getComponent("_DTPMonday");
}

function dtp_GetDTPTuesday() {
  return this.getComponent("_DTPTuesday");
}

function dtp_GetDTPWednesday() {
  return this.getComponent("_DTPWednesday");
}

function dtp_GetDTPThursday() {
  return this.getComponent("_DTPThursday");
}

function dtp_GetDTPFriday() {
  return this.getComponent("_DTPFriday");
}

function dtp_GetDTPSaturay() {
  return this.getComponent("_DTPSaturay");
}

function  dtp_GetDTPTimeInput() {
  return this.getComponent("_DTPTimeInput");
}


function  dtp_GetDTPTimeTab() {
  return this.getComponent("_DTPTimeTab");
}

function  dtp_GetDTPHour() {
  return this.getComponent("_DTPHour");
}

function  dtp_GetDTPMinute() {
  return this.getComponent("_DTPMinute");
}

function  dtp_GetDTPSecond() {
  return this.getComponent("_DTPSecond");
}

function  dtp_GetDTPHourUp() {
  return this.getComponent("_DTPHourUp");
}

function  dtp_GetDTPHourDown() {
  return this.getComponent("_DTPHourDown");
}

function  dtp_GetDTPMinuteUp() {
  return this.getComponent("_DTPMinuteUp");
}

function  dtp_GetDTPMinuteDown() {
  return this.getComponent("_DTPMinuteDown");
}

function  dtp_GetDTPSecondUp() {
  return this.getComponent("_DTPSecondUp");
}

function  dtp_GetDTPSecondDown() {
  return this.getComponent("_DTPSecondDown");
}

function dtp_GetInputNull() {
  return this.getComponent("_InputNull");
}

function dtp_GetLabelNull() {
  return this.getComponent("_LabelNull");
}

function dtp_GetValueOBJ() {
  return this.getComponent("_value");
}

function dtp_SetValueRestrict(RestrictValue) {
	this.getValueOBJ().PRestrict = RestrictValue ;
}

function dtp_SetRequired(RequiredValue) {
	this.getValueOBJ().PRequired = RequiredValue ;
}

function dtp_SetCssText(objWho,txtCss) {
	if (objWho) { 
		objWho.style.cssText=txtCss ; 
		return true ;
	} else return false;
}

function dtp_InitCssSet() {
	var aDTP = this;
	if (aDTP) {
		switch (aDTP.styleID) {
			case 1 :
				aDTP.setCssText(aDTP,"	BACKGROUND-color:white;	BORDER-TOP: black solid 0px;	BORDER-LEFT: black solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	font:9pt; width : " +((aDTP.DateTimeObject.type==1)?"120px":((aDTP.DateTimeObject.type==2)?"105px":"200px")));
				aDTP.setCssText(aDTP.getYearInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 30px");
				aDTP.setCssText(aDTP.getMonthInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDayInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getHourInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getMinuteInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getSecondInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDropBtn(),"BORDER: navy 0px solid; WIDTH: 12px; BACKGROUND-COLOR: transparent;FONT-WEIGHT: normal;    FONT-SIZE: 6.5pt;    COLOR: black; CURSOR: hand;");
				aDTP.setCssText(aDTP.getOptionDiv(),"  	BACKGROUND-color:#ffffff;	BORDER-TOP: LightGrey solid 1px;	BORDER-LEFT: LightGrey solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;    PADDING-RIGHT: 0px;    PADDING-LEFT: 0px;    PADDING-BOTTOM: 0px;    PADDING-TOP: 0px; OVERFLOW: hidden;   BACKGROUND-COLOR: transparent ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: white ; POSITION: absolute;VISIBILITY: hidden ; width:300px;left:0px;top:0px;");
				aDTP.setCssText(aDTP.getDropDownTable(),"	BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	cursor:default;	FONT-WEIGHT: normal;    FONT-SIZE: 9pt; FONT-STYLE: normal;    FONT-VARIANT: normal;    FONT-WEIGHT: normal;    LINE-HEIGHT: normal;    COLOR: black; cellspacing : 0px; width:100%");
				aDTP.setCssText(aDTP.getDTPTitle(),"	background-color:#00008b;	color:#fffacd;	font:9pt;	text-align:center;");
				aDTP.setCssText(aDTP.getDTPPrivBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPYearMonth(),"	BACKGROUND-color:transparent;font:9pt;	color:#fffacd;;");
				aDTP.setCssText(aDTP.getDTPNextBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPWeek(),"    ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: black;    FONT-FAMILY: ����;    BACKGROUND-COLOR: transparent;    TEXT-ALIGN: center ; height:16px");
				aDTP.setCssText(aDTP.getDTPSunday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPMonday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTuesday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPWednesday(),"color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPThursday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPFriday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPSaturay(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTimeInput(),"BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 0px;	BORDER-RIGHT: black solid 0px;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%");
				if (aDTP.DateTimeObject.type != 1) {
					aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;		font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;");
					//if (aDTP.DateTimeObject.type == 2) aDTP.getOptionDiv().style.width = 250;
				} else aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;VISIBILITY: hidden");
				aDTP.setCssText(aDTP.getDTPHour(),"	BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPMinute(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPSecond(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPHourUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPHourDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				CurDateCssText ="background-color:#fff0c2;color:#000000;font:9pt;text-align:center;cursor:hand;";
				AvailableDateCssText ="color:#00008b;font:9pt;text-align:center;cursor:hand;";
				InvalidDateCssText = "color:Silver;font:9pt;text-align:center;cursor:hand;";
				break;
			case 2 :
				aDTP.setCssText(aDTP,"	BACKGROUND-color:white;	BORDER-TOP: black solid 0px;	BORDER-LEFT: black solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	font:9pt; width : " +((aDTP.DateTimeObject.type==1)?"120px":((aDTP.DateTimeObject.type==2)?"105px":"200px")));
				aDTP.setCssText(aDTP.getYearInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 30px");
				aDTP.setCssText(aDTP.getMonthInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDayInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getHourInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getMinuteInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getSecondInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDropBtn(),"BORDER: navy 0px solid; WIDTH: 12px; BACKGROUND-COLOR: transparent;FONT-WEIGHT: normal;    FONT-SIZE: 6.5pt;    COLOR: black; CURSOR: hand;");
				aDTP.setCssText(aDTP.getOptionDiv(),"  	BACKGROUND-color:#ffffff;	BORDER-TOP: LightGrey solid 1px;	BORDER-LEFT: LightGrey solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;    PADDING-RIGHT: 0px;    PADDING-LEFT: 0px;    PADDING-BOTTOM: 0px;    PADDING-TOP: 0px; OVERFLOW: hidden;   BACKGROUND-COLOR: transparent ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: white ; POSITION: absolute;VISIBILITY: hidden ; width:300px;left:0px;top:0px;");
				aDTP.setCssText(aDTP.getDropDownTable(),"	BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	cursor:default;	FONT-WEIGHT: normal;    FONT-SIZE: 9pt; FONT-STYLE: normal;    FONT-VARIANT: normal;    FONT-WEIGHT: normal;    LINE-HEIGHT: normal;    COLOR: black; cellspacing : 0px; width:100%");
				aDTP.setCssText(aDTP.getDTPTitle(),"	background-color:#00008b;	color:#fffacd;	font:9pt;	text-align:center;");
				aDTP.setCssText(aDTP.getDTPPrivBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPYearMonth(),"	BACKGROUND-color:transparent;font:9pt;	color:#fffacd;;");
				aDTP.setCssText(aDTP.getDTPNextBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPWeek(),"    ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: black;    FONT-FAMILY: ����;    BACKGROUND-COLOR: transparent;    TEXT-ALIGN: center ; height:16px");
				aDTP.setCssText(aDTP.getDTPSunday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPMonday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTuesday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPWednesday(),"color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPThursday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPFriday(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPSaturay(),"	color:#00008b;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTimeInput(),"BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 0px;	BORDER-RIGHT: black solid 0px;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%");
				if (aDTP.DateTimeObject.type != 1) {
					aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;		font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;");
				} else aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;VISIBILITY: hidden");
				aDTP.setCssText(aDTP.getDTPHour(),"	BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPMinute(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPSecond(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPHourUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPHourDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				CurDateCssText ="background-color:#ffa07a;color:#000000;font:9pt;text-align:center;cursor:hand;";
				AvailableDateCssText ="color:#00008b;font:9pt;text-align:center;cursor:hand;";
				InvalidDateCssText = "color:Silver;font:9pt;text-align:center;cursor:hand;";
				break;
			case 3 : 
			
				break;
			default :
				aDTP.setCssText(aDTP,"	BACKGROUND-color:white;	BORDER-TOP: black solid 0px;	BORDER-LEFT: black solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	font:9pt; width : " +((aDTP.DateTimeObject.type==1)?"120px":((aDTP.DateTimeObject.type==2)?"105px":"200px")));
				aDTP.setCssText(aDTP.getYearInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 30px");
				aDTP.setCssText(aDTP.getMonthInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDayInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getHourInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getMinuteInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getSecondInput(),"	BACKGROUND-color:transparent;	text-align:right;	border:0;	font:9pt;	color:black; width : 16px");
				aDTP.setCssText(aDTP.getDropBtn(),"BORDER: navy 0px solid; WIDTH: 12px; BACKGROUND-COLOR: transparent;FONT-WEIGHT: normal;    FONT-SIZE: 6.5pt;    COLOR: black; CURSOR: hand;");
				aDTP.setCssText(aDTP.getOptionDiv(),"  	BACKGROUND-color:#ffffff;	BORDER-TOP: LightGrey solid 1px;	BORDER-LEFT: LightGrey solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;    PADDING-RIGHT: 0px;    PADDING-LEFT: 0px;    PADDING-BOTTOM: 0px;    PADDING-TOP: 0px; OVERFLOW: hidden;   BACKGROUND-COLOR: transparent ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: white ; POSITION: absolute;VISIBILITY: hidden ; width:300px;left:0px;top:0px;");
				aDTP.setCssText(aDTP.getDropDownTable(),"	BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 0px;	cursor:default;	FONT-WEIGHT: normal;    FONT-SIZE: 9pt; FONT-STYLE: normal;    FONT-VARIANT: normal;    FONT-WEIGHT: normal;    LINE-HEIGHT: normal;    COLOR: black; cellspacing : 0px; width:100%");
				aDTP.setCssText(aDTP.getDTPTitle(),"	background-color:#e9e5d9;	color:#fffacd;	font:9pt;	text-align:center;");
				aDTP.setCssText(aDTP.getDTPPrivBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPYearMonth(),"	BACKGROUND-color:transparent;font:9pt;	color:black;");
				aDTP.setCssText(aDTP.getDTPNextBtn(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:9pt;	width:16;	color:black;");
				aDTP.setCssText(aDTP.getDTPWeek(),"    ;FONT-WEIGHT: normal;    FONT-SIZE: 9pt;    COLOR: black;    FONT-FAMILY: ����;    BACKGROUND-COLOR: transparent;    TEXT-ALIGN: center ; height:16px");
				aDTP.setCssText(aDTP.getDTPSunday(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPMonday(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTuesday(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPWednesday(),"color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPThursday(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPFriday(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPSaturay(),"	color:black;	font:9pt;	text-align:center;	BORDER-BOTTOM: black solid 1px");
				aDTP.setCssText(aDTP.getDTPTimeInput(),"BACKGROUND-color:white;	BORDER-TOP: LightGrey solid 0px;	BORDER-LEFT: LightGrey solid 0px;	BORDER-BOTTOM: black solid 0px;	BORDER-RIGHT: black solid 0px;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%");
				if (aDTP.DateTimeObject.type != 1) {
					aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;		font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;");
				} else aDTP.setCssText(aDTP.getDTPTimeTab(),"BACKGROUND-color:transparent;	font:9pt;	text-align:center;  CELLSPACING:0;BORDER:1;width:100%;VISIBILITY: hidden");
				aDTP.setCssText(aDTP.getDTPHour(),"	BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPMinute(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPSecond(),"BORDER-TOP:  navy 0px solid;	BORDER 0px ;BACKGROUND-color:transparent;	text-align:right;	font:9pt;	color:blue; width : 16px");
				aDTP.setCssText(aDTP.getDTPHourUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPHourDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPMinuteDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondUp(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				aDTP.setCssText(aDTP.getDTPSecondDown(),"	BACKGROUND-color:silver;	BORDER-TOP: white solid 1px;	BORDER-LEFT: white solid 1px;	BORDER-BOTTOM: black solid 1px;	BORDER-RIGHT: black solid 1px;	font:5pt;	width:6;	height:6; color:black;cursor:hand;");
				CurDateCssText ="background-color:#fff0c2;color:#000000;font:9pt;text-align:center;cursor:hand;";
				AvailableDateCssText ="color:black;font:9pt;text-align:center;cursor:hand;";
				InvalidDateCssText = "color:Silver;font:9pt;text-align:center;cursor:hand;";

		}
	}
}

function dtp_Normal_Drop_onclick() {
	var curDateTime = this.DateTimeObject;
	this.setCurDateTime(curDateTime.fullyear,curDateTime.month,curDateTime.day,curDateTime.hour,curDateTime.minute,curDateTime.second);

	var dp=this;
	var sw = DTP_getOffsetWidth(dp);
	var lLeft= DTP_getOffsetLeft(dp) + dp.LeftGap;
	var lTop= DTP_getOffsetTop(dp)+ dp.TopGap + dp.offsetHeight+1;	
	if (lLeft + parseInt(this.getOptionDiv().currentStyle.width) > sw)
		lLeft = lLeft - parseInt(this.getOptionDiv().currentStyle.width) + parseInt(this.currentStyle.width);
	this.getOptionDiv().style.left = lLeft;
	
	if (this.DateTimeObject.type ==1)
		this.getOptionDiv().style.top = (lTop>170)?(lTop-170):lTop ;
	else if (this.DateTimeObject.type ==2)
		this.getOptionDiv().style.top = lTop ;
	else this.getOptionDiv().style.top = (lTop>200)?(lTop-200):lTop ;
	
	this.getOptionDiv().style.visibility = "";
	
	this.getDropBtn().innerText = DTP_Up_Char;
	if (document.body.onclick != null && typeof(document.body.onclick)=="function") document.body.click();
	this.oldDocOnclick = document.body.onclick;
	document.body.onclick = this.overrideDocOnclick;
	currentDateTimePickerComponent = this;
} 

function dtp_onclick() {
		event.cancelBubble=true;
		event.returnValue=false;

}

function dtp_onkeydown() {
		//event.cancelBubble=true;
		//event.returnValue=false;

}

function dtp_focus(df_type) {
	var src=event.srcElement;
	if(src && src.tagName=="INPUT")	{
		switch(df_type)	{
			case 'year':break;
			case 'month':break;
			case 'day':	break;
			case 'hour':	break;
			case 'minute':	break;
			case 'second':	break;
			default:;
		}
		src.select();
	}
	return true;
}

function dtp_blur(df_type) {
	var src=event.srcElement;
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker");	
	if(src && src.tagName=="INPUT" && SenderRoot !=null)	{
		var lYear= SenderRoot.DateTimeObject.fullyear ;
		var lMonth= SenderRoot.DateTimeObject.month ;
		var lDay= SenderRoot.DateTimeObject.day ;
		var lhour= SenderRoot.DateTimeObject.hour ;
		var lminute= SenderRoot.DateTimeObject.minute ;
		var lsecond= SenderRoot.DateTimeObject.second ;
		
		var val=parseInt(src.value,10);
		if(isNaN(val))	val=-1;
		switch(df_type)		{
			case 'year':
				lYear= (val==-1)?lYear:val;
				break;
			case 'month':
				lMonth= (val==-1)?lMonth:val;
				break;
			case 'day':
				lDay= (val==-1)?lDay:val;
				break;
			case 'hour':
				lhour= (val==-1)?lhour:val;
				break;
			case 'minute':
				lminute= (val==-1)?lminute:val;
				break;
			case 'second':
				lsecond= (val==-1)?lsecond:val;
				break;
			default:;
		}
		SenderRoot.setCurDateTime(lYear,lMonth,lDay,lhour,lminute,lsecond);
	}
	return true;
}

function dtp_KeyFilter(dk_type) {
	var berr=false;
	switch(dk_type)	{
		case 'date':
			if (!(event.keyCode == 45 || event.keyCode == 47 || (event.keyCode>=48 && event.keyCode<=57)))
				berr=true;
			break;
		case 'number':
			if (!(event.keyCode>=48 && event.keyCode<=57))
				berr=true;
			break;
		case 'cy':
			if (!(event.keyCode == 46 || (event.keyCode>=48 && event.keyCode<=57)))
				berr=true;
			break;
		case 'long':
			if (!(event.keyCode == 45 || (event.keyCode>=48 && event.keyCode<=57)))
				berr=true;
			break;
		case 'double':
			if (!(event.keyCode == 45 || event.keyCode == 46 || (event.keyCode>=48 && event.keyCode<=57)))
				berr=true;
			break;
		default:
			if (event.keyCode == 35 || event.keyCode == 37 || event.keyCode==38)
				berr=true;
	}
	return !berr;
}

function dtp_keyDown(dk_type) {

}

function dtp_optionDiv_onclick() {
		//event.cancelBubble=true;
		//event.returnValue=false;
}

function dtp_ddt_click() {
	var SenderRoot = dtp_LocateSenderRoot("DTPicker");	
	var src=event.srcElement;
	if(SenderRoot != null && src != null  && src.tagName=="TD") {
		var rid = src.parentElement.rowIndex;
		if (rid>=2 && rid <=7) 
				SenderRoot.setCurDate(src.year,parseInt(src.month,10)+1,parseInt(src.innerText,10));
	}
}

function dtp_RefreshCurDateTable() {
	var ddt=this.getDropDownTable();
	var curDateTime = this.DateTimeObject;
	
	if(ddt != null && ddt.name!='null') {
		var cell=null;
		var dt=new Date(curDateTime.fullyear,curDateTime.month-1,1);
		this.getDTPYearMonth().innerText = DTP_MonthArray[dt.getMonth()]+" "+dt.getFullYear();
		var wd=dt.getDay();
		dt=new Date(dt.getFullYear(),dt.getMonth(),1-wd);
		var day=dt.getDate();
		for(var i=2; i<8; i++) {
			for(var j=0; j<7; j++) {
				cell=ddt.rows[i].cells[j];
				if(cell) {
					if((curDateTime.month-1) !=dt.getMonth())
						cell.style.cssText =InvalidDateCssText;
					else {
						cell.style.cssText= AvailableDateCssText ;
						if(curDateTime.day==dt.getDate()) this.displayCurCell(cell) ;
					}
					cell.innerText=day;
					cell.year=dt.getFullYear();
					cell.month=dt.getMonth() ;
					dt.setDate(day+1);
					day=dt.getDate();
				}
			}
		}
	}
}

function dtp_SetCurDate(lyear,lmonth,lday) {
	this.setCurDateTime(lyear,lmonth,lday,this.DateTimeObject.hour,this.DateTimeObject.minute ,this.DateTimeObject.second);
}

function dtp_SetCurTime(lhour,lminute,lsecond) {
	this.setCurDateTime(this.DateTimeObject.fullyear,this.DateTimeObject.month ,this.DateTimeObject.day,lhour,lminute,lsecond);
}

function dtp_SetCurDateTime(lyear,lmonth,lday,lhour,lminute,lsecond) {
	var dt = new Date(lyear,lmonth-1,lday,lhour,lminute,lsecond);
	this.DateTimeObject.fullyear = dt.getFullYear();
	this.DateTimeObject.year = dt.getYear();
	this.DateTimeObject.month = dt.getMonth()+1 ;
	this.DateTimeObject.day = dt.getDate();
	this.DateTimeObject.hour = dt.getHours();
	this.DateTimeObject.minute = dt.getMinutes() ;
	this.DateTimeObject.second = dt.getSeconds();
	this.DateTimeObject.weekStr = DTP_WeekArray[dt.getDay()];
	this.DateTimeObject.monthStr =DTP_MonthArray[dt.getMonth()];
	this.DateTimeObject.DT =dt;
	
	if (!this.DateTimeObject.isNull) {
		this.getYearInput().value  =  this.DateTimeObject.fullyear;
		this.getMonthInput().value =  this.DateTimeObject.month;
		this.getDayInput().value =  this.DateTimeObject.day ;
		this.getHourInput().value  = this.DateTimeObject.hour ;
		this.getMinuteInput().value = this.DateTimeObject.minute ;
		this.getSecondInput().value = this.DateTimeObject.second ;
	}
	this.getDTPHour().value = this.DateTimeObject.hour ;
	this.getDTPMinute().value = this.DateTimeObject.minute ;
	this.getDTPSecond().value = this.DateTimeObject.second ;
	this.getValueOBJ().value = this.getDateTime();
	this.refreshCurDateTable();
	if (this.on_DateTimeChange !=null) return this.on_DateTimeChange(this);	
}

function dtp_DisplayCurCell(srcCell) {
	if (this.oldCell != null) this.oldCell.style.cssText =	this.oldCellCssText ;
	this.oldCell = srcCell;
	this.oldCellCssText = srcCell.style.cssText;	
	srcCell.style.cssText=CurDateCssText;
}

function dtp_ddt_dblclick() {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker");
	var acom = SenderRoot.getDTPPrivBtn();
	var bcom = SenderRoot.getDTPNextBtn();
	var src= dtp_LocateSenderByTag("SPAN");
	if (src ==null || (acom.id != src.id && bcom.id != src.id))	dtp_UP_DTPOption();
}

function dtp_drop_onclick() {
	var SenderRoot = dtp_LocateSenderRoot("DTPicker");
	if (SenderRoot!=null ) {
		Sender= dtp_LocateSenderByTag("SPAN");		
		SenderRoot.eventElement = Sender;
		if (currentDateTimePickerComponent != SenderRoot) {
			if (currentDateTimePickerComponent !=null ) dtp_OverrideDocOnclick();
			SenderRoot.normal_on_Drop_click();
			//event.cancelBubble=true;
			//event.returnValue=false;			
		} else {
			dtp_OverrideDocOnclick();
		}	
	}	
}

function dtp_OverrideDocOnclick() {
	var eventCom = dtp_LocateSenderRoot("DTPOption");
	if (currentDateTimePickerComponent !=null && eventCom==null) dtp_UP_DTPOption();
}

function dtp_UP_DTPOption() {
	if (currentDateTimePickerComponent !=null) {
 		currentDateTimePickerComponent.getOptionDiv().style.visibility = "hidden";
 		currentDateTimePickerComponent.getOptionDiv().style.left =0;
 		currentDateTimePickerComponent.getOptionDiv().style.top =0;
		currentDateTimePickerComponent.getDropBtn().innerText = DTP_Down_Char;
		document.body.onclick = currentDateTimePickerComponent.oldDocOnclick ;
		var theCom = currentDateTimePickerComponent;
		currentDateTimePickerComponent = null;
		if (theCom.on_OptionUp !=null) return theCom.on_OptionUp(this);	
		//event.cancelBubble=true;
		//event.returnValue=false;
	}
}

function dtp_monthChange(lStep) {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker")
	var src=event.srcElement;
	if(src !=null && SenderRoot != null)
	{
		var ddt=SenderRoot.getDropDownTable();
		if(ddt != null ) {
			var dt = new Date(SenderRoot.DateTimeObject.year,SenderRoot.DateTimeObject.month-1,SenderRoot.DateTimeObject.day);
			var lOldMonth=SenderRoot.DateTimeObject.month -1;
			var lOldDay=SenderRoot.DateTimeObject.day;
			dt.setDate(1);
			dt.setMonth(lOldMonth+lStep+1);
			dt.setDate(0);
			if(dt.getDate()>lOldDay) dt.setDate(lOldDay);
			SenderRoot.setCurDate(dt.getFullYear(),dt.getMonth()+1,dt.getDate());
			//SenderRoot.normal_on_Drop_click();
		}
	}
}

function dtp_hourChange(lStep) {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker")
	var src=event.srcElement;
	if(src !=null && SenderRoot != null) {
		var ddt=SenderRoot.getDropDownTable();
		if(ddt != null ) {
			var curHour = parseInt(SenderRoot.getDTPHour().value ) ;
			if (isNaN(curHour)) return false;
			curHour = curHour + lStep;
			if (curHour >=24) curHour = curHour -24 ;
			if (curHour <0 ) curHour = curHour +24;
			var dt = new Date(SenderRoot.DateTimeObject.year,SenderRoot.DateTimeObject.month-1,SenderRoot.DateTimeObject.day,SenderRoot.DateTimeObject.hour,SenderRoot.DateTimeObject.minute,SenderRoot.DateTimeObject.second);
			dt.setHours(curHour);
			SenderRoot.getDTPHour().value = dt.getHours();
			SenderRoot.setCurTime(dt.getHours(),dt.getMinutes(),dt.getSeconds());
		}
	}
}

function dtp_minuteChange(lStep) {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker")
	var src=event.srcElement;
	if(src !=null && SenderRoot != null) {
		var ddt=SenderRoot.getDropDownTable();
		if(ddt != null ) {
			var curMinute = parseInt(SenderRoot.getDTPMinute().value ) ;
			if (isNaN(curMinute)) return false;
			curMinute = curMinute + lStep;
			if (curMinute >=60) curMinute = curMinute -60 ;
			if (curMinute <0 ) curMinute = curMinute +60;
			var dt = new Date(SenderRoot.DateTimeObject.year,SenderRoot.DateTimeObject.month-1,SenderRoot.DateTimeObject.day,SenderRoot.DateTimeObject.hour,SenderRoot.DateTimeObject.minute,SenderRoot.DateTimeObject.second);
			dt.setMinutes(curMinute);
			SenderRoot.getDTPMinute().value = dt.getMinutes() ;
			SenderRoot.setCurTime(dt.getHours(),dt.getMinutes(),dt.getSeconds());
		}
	}
}

function dtp_secondChange(lStep) {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker")
	var src=event.srcElement;
	if(src !=null && SenderRoot != null) {
		var ddt=SenderRoot.getDropDownTable();
		if(ddt != null) {
			var curSecond = parseInt(SenderRoot.getDTPSecond().value ) ;
			if (isNaN(curSecond)) return false;
			curSecond = curSecond + lStep;
			if (curSecond >=60) curSecond = curSecond -60 ;
			if (curSecond <0 ) curSecond = curSecond +60;
			var dt = new Date(SenderRoot.DateTimeObject.year,SenderRoot.DateTimeObject.month-1,SenderRoot.DateTimeObject.day,SenderRoot.DateTimeObject.hour,SenderRoot.DateTimeObject.minute,SenderRoot.DateTimeObject.second);
			dt.setSeconds(curSecond);
			SenderRoot.getDTPSecond().value = dt.getSeconds() ;
			SenderRoot.setCurTime(dt.getHours(),dt.getMinutes(),dt.getSeconds());
		}
	}
}

function dtp_inputNull() {
	var SenderRoot =  dtp_LocateSenderRoot("DTPicker") ;
	var ck = SenderRoot.getInputNull();
	if (ck.checked) {
		SenderRoot.DateTimeObject.isNull = true ;
		SenderRoot.getYearInput().value  =  "";
		SenderRoot.getMonthInput().value =  "";
		SenderRoot.getDayInput().value =  "";
		SenderRoot.getHourInput().value  =  "";
		SenderRoot.getMinuteInput().value =  "";
		SenderRoot.getSecondInput().value =  "";
		
		SenderRoot.getYearInput().disabled= true;
		SenderRoot.getMonthInput().disabled= true;
		SenderRoot.getDayInput().disabled= true;
		SenderRoot.getHourInput().disabled= true;
		SenderRoot.getMinuteInput().disabled= true;
		SenderRoot.getSecondInput().disabled= true;
		SenderRoot.getValueOBJ().value = SenderRoot.getDateTime();
		dtp_UP_DTPOption();
		if (SenderRoot.on_DateTimeChange !=null) return SenderRoot.on_DateTimeChange(SenderRoot);	
	} else {
		SenderRoot.DateTimeObject.isNull = false ;
		SenderRoot.getYearInput().value  = SenderRoot.DateTimeObject.fullyear ;
		SenderRoot.getMonthInput().value =  SenderRoot.DateTimeObject.month ;
		SenderRoot.getDayInput().value =  SenderRoot.DateTimeObject.day;
		SenderRoot.getHourInput().value  =  SenderRoot.DateTimeObject.hour;
		SenderRoot.getMinuteInput().value =  SenderRoot.DateTimeObject.minute;
		SenderRoot.getSecondInput().value =  SenderRoot.DateTimeObject.second;

		SenderRoot.getYearInput().disabled= false;
		SenderRoot.getMonthInput().disabled= false;
		SenderRoot.getDayInput().disabled= false;
		SenderRoot.getHourInput().disabled= false;
		SenderRoot.getMinuteInput().disabled= false;
		SenderRoot.getSecondInput().disabled= false;
		SenderRoot.getValueOBJ().value = SenderRoot.getDateTime();
		if (SenderRoot.on_DateTimeChange !=null) return SenderRoot.on_DateTimeChange(SenderRoot);	
	}
}

function dtp_SetNull() {
	var ck = this.getInputNull();
		
	this.DateTimeObject.isNull = true ;
	this.getYearInput().value  =  "";
	this.getMonthInput().value =  "";
	this.getDayInput().value =  "";
	this.getHourInput().value  =  "";
	this.getMinuteInput().value =  "";
	this.getSecondInput().value =  "";
	
	this.getYearInput().disabled= true;
	this.getMonthInput().disabled= true;
	this.getDayInput().disabled= true;
	this.getHourInput().disabled= true;
	this.getMinuteInput().disabled= true;
	this.getSecondInput().disabled= true;
	this.getValueOBJ().value = this.getDateTime();

	var tmpStr = ck.outerHTML;
	var tmpInt = tmpStr.indexOf("CHECKED");
	if (tmpInt<0) {
		tmpInt = tmpStr.indexOf(">");
		tmpStr = tmpStr.substring(0,tmpInt) +" CHECKED>";
		ck.outerHTML = tmpStr;
	}
	if (this.on_DateTimeChange !=null) return this.on_DateTimeChange(this);	
}

function dtp_IsNull() {
	return this.DateTimeObject.isNull;
}


function  dtp_SetTopGap(tgValue) {
	this.TopGap = tgValue;
}
			
function dtp_SetLeftGap(lgValue) {
	this.LeftGap = lgValue;
}

function dtp_SetNullCheck(nc) {
	var ck = this.getInputNull();
	ck.disabled = nc;
}

function dtp_SetReadOnly(ro) {
	if(ro) {
		this.getYearInput().disabled=true;
		this.getMonthInput().disabled=true;
		this.getDayInput().disabled=true;
		this.getHourInput().disabled=true;
		this.getMinuteInput().disabled=true;
		this.getSecondInput().disabled=true;
		this.getDropBtn().onclick = null ;
		this.getDropBtn().style.color = "gray";
	} else {
		this.getYearInput().disabled=false;
		this.getMonthInput().disabled=false;
		this.getDayInput().disabled=false;
		this.getHourInput().disabled=false;
		this.getMinuteInput().disabled=false;
		this.getSecondInput().disabled=false;
		this.getDropBtn().onclick = dtp_drop_onclick ;
		this.getDropBtn().style.color = "black";
	}
}

function dtp_SetTxtDate(dTxt) {
	if (dTxt != null && dTxt != "" && dTxt.toLowerCase() != "null")  {
		dArray = dTxt.split("-");
		if (dArray.length!=3) return false;
		var yearInt = parseInt(dArray[0],10);
		var monthInt = parseInt(dArray[1],10);
		var dayInt = parseInt(dArray[2],10);
		
		if (isNaN(yearInt) || isNaN(monthInt) || isNaN(dayInt) )
			return false;
		this.setCurDate(yearInt,monthInt,dayInt);
		return true;
	} else this.setNull();
}

function dtp_SetTxtTime(tTxt) {
	if (tTxt != null && tTxt!="" && tTxt.toLowerCase()!="null") {
		tArray = tTxt.split(":");
		if (tArray.length!=3) return false;
		var hourInt = parseInt(tArray[0],10);
		var minuteInt = parseInt(tArray[1],10);
		var secondInt = parseInt(tArray[2],10);
		
		if (isNaN(hourInt) || isNaN(minuteInt) || isNaN(secondInt) )
			return false;
		this.setCurTime(hourInt,minuteInt,secondInt);
		return true;
	} else this.setNull();
}

function dtp_SetTxtDateTime(dtTxt) {
	if (dtTxt != null && dtTxt !="" && dtTxt.toLowerCase() !="null") {
		dtArray = dtTxt.split(" ");
		if (dtArray.length!=2) return false;
		dArray = dtArray[0].split("-");
		if (dArray.length!=3) return false;
		tArray = dtArray[1].split(":");
		if (tArray.length!=3) return false;
		var yearInt = parseInt(dArray[0],10);
		var monthInt = parseInt(dArray[1],10);
		var dayInt = parseInt(dArray[2],10);
		var hourInt = parseInt(tArray[0],10);
		var minuteInt = parseInt(tArray[1],10);
		var secondInt = parseInt(tArray[2],10);
		
		if (isNaN(yearInt) || isNaN(monthInt) || isNaN(dayInt) || isNaN(hourInt) || isNaN(minuteInt) || isNaN(secondInt) )
			return false;
		this.setCurDateTime(yearInt,monthInt,dayInt,hourInt,minuteInt,secondInt);
		return true;
	} else this.setNull();
}

function dtp_SetLineWidth(wd) {
	
	var g = (this.DateTimeObject.type==1)?57:((this.DateTimeObject.type==2)?57:90);
	var owd = parseInt(this.currentStyle.width)-g;
	if (isNaN(owd)) return false;
	this.style.width = wd;
	var cwd = parseInt(this.currentStyle.width)-g;
	if (isNaN(cwd)) return false;
	
	if (this.DateTimeObject.type==1 || this.DateTimeObject.type==0) {
		var w1 = 0;
		w1 = parseInt(this.getYearInput().currentStyle.width);
		if (!isNaN(w1)) this.getYearInput().style.width = parseInt(w1*cwd/owd);
		w1 = parseInt(this.getMonthInput().currentStyle.width);
		if (!isNaN(w1)) this.getMonthInput().style.width = parseInt(w1*cwd/owd);
		w1 = parseInt(this.getDayInput().currentStyle.width);
		if (!isNaN(w1)) this.getDayInput().style.width = parseInt(w1*cwd/owd);
	}
	if (this.DateTimeObject.type==2 || this.DateTimeObject.type==0) {
		w1 = parseInt(this.getHourInput().currentStyle.width);
		if (!isNaN(w1)) this.getHourInput().style.width = parseInt(w1*cwd/owd);
		w1 = parseInt(this.getMinuteInput().currentStyle.width);
		if (!isNaN(w1)) this.getMinuteInput().style.width = parseInt(w1*cwd/owd);
		w1 = parseInt(this.getSecondInput().currentStyle.width);
		if (!isNaN(w1)) this.getSecondInput().style.width = parseInt(w1*cwd/owd);
	}
}
