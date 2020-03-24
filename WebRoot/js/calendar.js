srcteccalenstyletext="style='font-size: 9pt; font-family: ����_GB2312;  font-weight: bolder; font-style: normal; text-decoration: none'; " ;
srcteccalenstyletextbutton="style='font-size: 9pt; font-family: ;  font-weight: ; font-style: ; text-decoration: none; height: 18px; width:80px'; " ;
srctectitlestyle="style='font-size: 9pt; font-family: Verdana;  font-weight: text-decoration: underline; text-align:center;'; " ;
srcteccalenstylecalander="style='border: 0 outset #c0c0c0; font-size: 10pt; font-family: Times New Roman;  font-weight: ; font-style: ; text-decoration: none; text-align:center;'; "; 
srcteccalenstyletextborder="style='border:0 outset #ffffe0;'" ;

function monthday(d0, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11) 
{ 
  this[0] = d0; 
  this[1] = d1; 
  this[2] = d2; 
  this[3] = d3; 
  this[4] = d4; 
  this[5] = d5; 
  this[6] = d6; 
  this[7] = d7; 
  this[8] = d8; 
  this[9] = d9; 
  this[10] = d10; 
  this[11] = d11; 
} 
function showcalendar(today,bgcolor,textcolor,tableWith,tdHeight) 

{ 
  var thisDay; 
  var monthDays = new monthday(31,28,31,30,31,30,31,31,30,31,30,31); 
  year = today.getYear(); 
  if (year<2000) year=year+1900; 
  thisDay = today.getDate(); 
  if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) monthDays[1] = 29; 
  nDays = monthDays[today.getMonth()]; 
  firstDay = today; 
  firstDay.setDate(1); 
  testMe = firstDay.getDate(); 
  if (testMe == 2) firstDay.setDate(0);   
  startDay = firstDay.getDay(); 
  document.writeln("<CENTER>"); 
  document.write("<TABLE " +srcteccalenstylecalander+" width="+tableWith+" bgcolor="+bgcolor+">"); 
  document.write("<TR "+"height="+tdHeight+"><td  COLSPAN=7>"); 
  document.write(year+ ' .  '); 
  document.write((today.getMonth()+1)+'</td></tr>'); 


  document.write("<TR ><td>Sun</td><td>Mon</td><td>Tue</td><td>Wed</td><td>Thu</td><td>Fri</td><td>Sat</td></tr>"); 
  document.write("<TR>"); 
  column = 0; 
  for (i=0; i<startDay; i++) 
  { 
   document.write("<TD height="+tdHeight+" >"); 
   column++; 
  } 
  for (i=1; i<=nDays; i++) 
  { 
   document.write("<TD height="+tdHeight); 
   if (i == thisDay) {
     document.write(" ><FONT color='#99CCFF'>") 
   	 document.write(i); 
   }else{
   	 document.write("><FONT>") 
   	 document.write(i); 

   }
   if (i == thisDay) 
    document.write("</FONT>") 
   column++; 
   if (column == 7) 
   { 
     document.write("<TR>"); 
     column = 0; 
   } 
  } 
  document.write("</TABLE>"); 
} 
var today = new Date(); 