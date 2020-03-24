var bIsIE5=document.all;
var canusehtml='1';
var PostType=1;

if (bIsIE5){
	var IframeID=frames["Composition"];
}
else{
	var IframeID=document.getElementById("Composition").contentWindow;
	var bIsNC=true;
}

function submits(){
document.all("edit").value=IframeID.document.body.innerHTML;
}
function initx(){
var c_uname=GetCookie('username')
if (c_uname.indexOf('&')>=0){
	c_uname=c_uname.substring(0,c_uname.indexOf('&'))
}
var c_pass=GetCookie('password')
if (c_pass.indexOf('&')>=0){
	c_pass=c_pass.substring(0,c_pass.indexOf('&'))
}
var c_hpage=GetCookie('userurl')
if (c_hpage.indexOf('&')>=0){
	c_hpage=c_hpage.substring(0,c_hpage.indexOf('&'))
}
document.commentform.UserName.value=DecodeCookie(c_uname);
document.commentform.Password.value=DecodeCookie(c_pass);
document.commentform.homepage.value+=DecodeCookie(c_hpage);
}
initx();

function del_space(s)
{
	for(i=0;i<s.length;++i)
	{
	 if(s.charAt(i)!=" ")
		break;
	}

	for(j=s.length-1;j>=0;--j)
	{
	 if(s.charAt(j)!=" ")
		break;
	}

	return s.substring(i,++j);
}

function Verifycomment()
{
	 v = del_space(document.commentform.UserName.value);
     if (v.length == 0)
     {
        alert("您忘了留下名字!");
	return false;
     }
	 	v = del_space(document.commentform.commenttopic.value);
     if (v.length == 0)
     {
        alert("您忘了填写题目!");
	return false;
     }
	 
	submits(); 
	if (document.commentform.edit.value == "")
     {
        alert("内容不能为空!");
	return false;
     }
	 
	var codeid=document.getElementById("CodeStr");
	if(codeid != null){ 
		if (document.commentform.CodeStr.value == "") {
		 alert("验证码不能为空!");
		return false;
		}
	}
	return true;
}

//数据传递
function CopyData(hiddenid)
{
	if (PostType == 0 && bTextMode == 3)
	{
		PasteData()
	}
	d = IframeID.document;
	if (bTextMode == 2)
	{
		cont = d.body.innerText;
	}else{
		cont = d.body.innerHTML;  
	}
	var ChekEmptyCode = ChekEmptyCode(cont);
	if (ChekEmptyCode == '' || ChekEmptyCode == null)
	{
		cont='';
	}
	else{
		cont = correctUrl(cont);
		if (filterScript)
		cont=FilterScript(cont);
	}
	document.getElementById(hiddenid).value = cont;
}

function PasteData()
{
	var regExp;
	cont = IframeID.document.body.innerHTML;
	regExp = /<[s|t][a-z]([^>]*)>/ig
	cont = cont.replace(regExp, '');
	regExp = /<\/[s|t][a-z]([^>]*)>/ig
	cont = cont.replace(regExp, '');
	IframeID.document.body.innerHTML = cont
}

function ctlent(eventobject)
{
	if(event.ctrlKey && event.keyCode==13)
	{
		this.document.Dvform.submit();
	}
}

function putEmot(thenNo)
{
	var ToAdd = '['+thenNo+']';
	IframeID.document.body.innerHTML+=ToAdd;
	IframeID.focus();
}
function gopreview()
{
document.preview.Dvtitle.value=document.Dvform.topic.value;
document.preview.theBody.value=IframeID.document.body.innerHTML;
var popupWin = window.open('', 'preview_page', 'scrollbars=yes,width=750,height=450');
document.preview.submit()
}


function foreColor()
{
	if (!validateMode()) return;
	if (bIsIE5){
		var arr = showModalDialog("Dialog/selcolor.html", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0; help:0");
		if (arr != null) FormatText('forecolor', arr);
		else IframeID.focus();
	}else
		{
		FormatText('forecolor', '');
		}
}

function backColor()
{
	if (!validateMode()) return;
	if (bIsIE5)
	{
		var arr = showModalDialog("Dialog/selcolor.html", "", "dialogWidth:18.5em; dialogHeight:17.5em; status:0; help:0");
		if (arr != null) FormatText('backcolor', arr);
		else IframeID.focus();
	}else
		{
		FormatText('backcolor', '');
		}
}

function correctUrl(cont)
{
	var regExp;
	var url=location.href.substring(0,location.href.lastIndexOf("/")+1);
	cont=rCode(cont,location.href+"#","#");
	cont=rCode(cont,url,"");
	cont=rCode(cont,"<a>　</a>","");
	return cont;
}


function cleanHtml()
{
	if (bIsIE5){
	var fonts = IframeID.document.body.all.tags("FONT");
	}else{
	var fonts = IframeID.document.getElementsByTagName("FONT");
	}
	var curr;
	for (var i = fonts.length - 1; i >= 0; i--) {
		curr = fonts[i];
		if (curr.style.backgroundColor == "#ffffff") curr.outerHTML = curr.innerHTML;
	}
}

function getPureHtml()
{
	var str = "";
	str = IframeID.document.body.innerHTML;
	str=correctUrl(str);
	return str;
}

function FormatUrl(html)
{
	var regExp = /<a.*href=\"(.*)\"[^>]*>/gi;
	html = html.replace(regExp,"<a href=$1 target=\"_blank\" >")
  return html;
}


function getEl(sTag,start)
{
	while ((start!=null) && (start.tagName!=sTag)) start = start.parentElement;
	return start;
}

//选择内容替换文本
function InsertSymbol(str1)
{
	IframeID.focus();
	if (bIsIE5) selectRange();
	edit.pasteHTML(str1);
}


//选择事件
function selectRange(){
	selection =	IframeID.document.selection;
	edit		=	selection.createRange();
	RangeType =	selection.type;
}

//应用html
function specialtype(Mark1, Mark2){
	var strHTML;
	if (bIsIE5){
		selectRange();
		if (RangeType == "Text"){
			if (Mark2==null)
			{
				strHTML = "<" + Mark1 + ">" + edit.htmlText + "</" + Mark1 + ">"; 
			}else{
				strHTML = Mark1 + edit.htmlText +  Mark2; 
			}
			edit.pasteHTML(strHTML);
			IframeID.focus();
			edit.select();
		}
		else{window.alert("请选择相应内容！")}	
	}
	else{
		if (Mark2==null)
		{
		strHTML	=	"<" + Mark1 + ">" + IframeID.document.body.innerHTML + "</" + Mark1 + ">"; 
		}else{
		strHTML = Mark1 + IframeID.document.body.innerHTML +  Mark2; 
		}
		IframeID.document.body.innerHTML=strHTML
		IframeID.focus();
	}
}

// 修改编辑栏高度
function Size(num)
{
	var obj=document.getElementById("edit");
	if (num>0){
		obj.style.height=num+"px";
		obj.style.width="100%";
		}
	else{
		obj.style.height="";
		obj.style.width=-num+"px";
	}
}

function getText()
{
	if (bTextMode==2)
		return IframeID.document.body.innerText;
	else
	{
		cleanHtml();
		return IframeID.document.body.innerHTML;
	}
}

function putText(v)
{
	if (bTextMode==2)
		IframeID.document.body.innerText = v;
	else
		IframeID.document.body.innerHTML = v;
}
function doSelectClick(str, el)
{
	var Index = el.selectedIndex;
	if (Index != 0){
		el.selectedIndex = 0;
		FormatText(str,el.options[Index].value);
	}
}
//查找配对字符出现次数,没有结果为0
function TabCheck(word,str){
	var tp=0
	chktp=str.search(word);
	if (chktp!=-1)
	{
	eval("var tp=\""+str+"\".match("+word+").length")
	}
	return tp;
}

//Colour pallete top offset
function getOffsetTop(elm) {
	var mOffsetTop = elm.offsetTop;
	var mOffsetParent = elm.offsetParent;
	while(mOffsetParent){
		mOffsetTop += mOffsetParent.offsetTop;
		mOffsetParent = mOffsetParent.offsetParent;
	}
	return mOffsetTop;
}

//Colour pallete left offset
function getOffsetLeft(elm) {
	var mOffsetLeft = elm.offsetLeft;
	var mOffsetParent = elm.offsetParent;
	while(mOffsetParent) {
		mOffsetLeft += mOffsetParent.offsetLeft;
		mOffsetParent = mOffsetParent.offsetParent;
	}
	return mOffsetLeft;
}

//Function to hide colour pallete
function hideColourPallete() {
	document.getElementById("colourPalette").style.visibility="hidden";
}


function rand() {
	return parseInt((1000)*Math.random()+1);
}


function GetRangeReference(editor)
{
	editor.focus();
	var objReference = null;
	var RangeType = editor.document.selection.type;
	var selectedRange = editor.document.selection.createRange();
	
	switch(RangeType)
	{
	case 'Control' :
		if (selectedRange.length > 0 ) 
		{
			objReference = selectedRange.item(0);
		}
	break;
	case 'None' :
		objReference = selectedRange.parentElement();
		break;
	case 'Text' :
		objReference = selectedRange.parentElement();
		break;
	}
	return objReference
}

function CheckTag(item,tagName)
{
	if (item.tagName.search(tagName)!= -1)
	{
		return item;
	}
	if (item.tagName == 'BODY')
	{
		return false;
	}
	item=item.parentElement;
	return CheckTag(item,tagName);
}


function InsertSymbol(str1)
{
	Composition.focus();
	if (bIsIE5) selectRange();	
	edit.pasteHTML(str1);
}

function foremot()
{
	var arr = showModalDialog("Dialog/emot.html", "", "dialogWidth:26em; dialogHeight:16em; status:0; help:0");
	
	if (arr != null)
	{
		InsertSymbol(arr);
		IframeID.focus();
	}
	else IframeID.focus();
}

function forimg()
{
if (bIsIE5){		
	var arr=showModalDialog("Dialog/img.html",window, "dialogWidth:23em; dialogHeight:11em; status:0; help:0");
	IframeID.focus();
	if (arr != null)
	{		
		arrStr="<img src='"+arr[0]+"' border='"+arr[2]+"' style='float:"+arr[1]+"'>"
		InsertSymbol(arrStr);
		IframeID.focus();
		
	}
	else IframeID.focus();
}
else {UserDialog('CreateLink');}
}


function help()
{
	var arr=showModalDialog("Dialog/help.html",window, "dialogWidth:23em; dialogHeight:11em; status:0; help:0");
}


function forlink()
{
if (bIsIE5){		
	var arr=showModalDialog("Dialog/link.html",window, "dialogWidth:23em; dialogHeight:11em; status:0; help:0");
	IframeID.focus();
	if (arr != null)
	{		
		InsertSymbol(arr);
		IframeID.focus();
		
	}
	else IframeID.focus();
}
else {UserDialog('CreateLink');}
}

function getHTML() {
	var html;
	if (!bTextMode) 
	{
	html = IframeID.document.body.innerHTML
	}
	else
	{
	html = IframeID.document.body.innerText
	}
	return html;
}
