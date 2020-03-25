//dataname 定义字段名
//ctltype 定义控件类型 1.文本框 2.隐含文本框 3.只读文本框 4.可选择文本框 5.选择框 6.可查找选择框
var ctl,ctl1,ctl2,ctl3,ctl4,ctl5,ctl6;
ctl="";
function CreateDbGrid(dataname,cellname,ctltype,records){

	for(i=0;i<document.all.dbtable.cells.length;i++){
	document.all.dbtable.cells(i).innerText = dataname[i];
	
	}
	Addrow();
}


function Addrow(){
	var lstb = document.all("dbtable");
	maxcell = lstb.rows(0).cells.length;
	newrow = lstb.insertRow();
	for(i=0;i<maxcell;i++){
		newcell = newrow.insertCell();
		//newcell.innerText = "a"+i;
		ctl1="<INPUT TYPE=\"text\" NAME=\""+cellname[i]+"\" value=\"111\">";
		ctl2="<input type=\"hidden\" name=\""+cellname[i]+"\" ";
		ctl3="<INPUT TYPE=\"text\" NAME=\""+cellname[i]+"\" value=\"22\" readonly>";
		ctl4=ctl1 + "<a href=\"#\"><img src=\"images/search.gif\" width=\"18\" height=\"18\" border=\"0\" onclick=\"supperselect(this,'"+cellname[i]+"');\"></a>";
		ctl5="<SELECT NAME=\""+cellname[i]+"\">";
			for(r=0;r<records.length;r++){
			ctl5+="<option value=\""+records[r]+"\">"+records[r]+"</option>";
			}
		ctl5+="</SELECT>";
		ctl6=ctl5 + "<a href=\"#\"><img src=\"images/search.gif\" width=\"18\" height=\"18\" border=\"0\" onclick=\"supperselect(this,'"+cellname[i]+"');\"></a>";
//alert(ctltype[i]);
//alert(ctl5);
	ctl = eval("ctl"+ctltype[i]);

		newcell.innerHTML=ctl;
	}
}
function Delrow(){
var lstb=document.all("dbtable");

lstb.deleteRow() ;
}