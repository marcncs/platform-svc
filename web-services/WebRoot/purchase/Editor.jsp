<%@page contentType="text/html; charset=utf-8" %>
<LINK href="css/css.css" type="text/css" rel="stylesheet">

<body onLoad="PageOnLoad()">
      <DIV id="edit">
		
      <UL>
        <SELECT language="javascript" class="TBGen" id="FontSize" onChange="FormatText('fontsize',this[this.selectedIndex].value);">
          <OPTION class="heading" selected>字号</OPTION>
          <OPTION value="1">1</OPTION>
          <OPTION value="2">2</OPTION>
          <OPTION value="3">3</OPTION>
          <OPTION value="4">4</OPTION>
          <OPTION value="5">5</OPTION>
          <OPTION value="6">6</OPTION>
          <OPTION value="7">7</OPTION>
        </SELECT>
        <LI language="javascript" class="Btn" onmouseover="this.className='BtnMouseOverUp';" title="加粗" 
          onclick="FormatText('bold', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/bold.gif" width="16" height="16" class=Ico> </LI>
        <LI language="javascript" class="Btn"
          onmouseover="this.className='BtnMouseOverUp';" title="斜体"
          onclick="FormatText('italic', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/italic.gif" width="16" height="16" class=Ico> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="下划线"
          onclick="FormatText('underline', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/underline.gif" width="16" height="16" class=Ico> </LI>
        <LI language="javascript" class="Btn"
          onmouseover="this.className='BtnMouseOverUp';" title="取消格式" 
          onclick="FormatText('RemoveFormat', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/removeformat.gif" width="16" height="16" class=Ico> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="左对齐" 
          onclick="FormatText('justifyleft', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';" NAME="Justify"><IMG src="img/aleft.gif" width="16" height="16" 
          class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="居中" 
          onclick="FormatText('justifycenter', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';" NAME="Justify"><IMG src="img/center.gif" width="16" height="16" 
          class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="右对齐"
          onclick="FormatText('justifyright', '');ondrag='return false;'" 
          onmouseout="this.className='Btn';" NAME="Justify"><IMG src="img/aright.gif" width="16" height="16" 
          class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="插入表情" 
          onclick=foremot() onMouseOut="this.className='Btn';"><IMG src="img/smiley.gif" width="16" height="16" 
          class="Ico"> </LI>
        <LI language="javascript" class="Btn" id="forecolor"
          onmouseover="this.className='BtnMouseOverUp';" title="字体颜色" 
          onclick="foreColor()"; onMouseOut="this.className='Btn';" 
          name="forecolor"><IMG src="img/fgcolor.gif" width="16" height="16" class="Ico"> </LI>
        <LI language="javascript" class="Btn" id="backcolor"
          onmouseover="this.className='BtnMouseOverUp';" title="字体背景颜色"
          onclick="backColor();ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/fbcolor.gif" width="16" height="16" class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="插入图片" 
          onclick="forimg();ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/img.gif" width="16" height="16" class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="插入超级链接" 
          onclick="forlink();ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/wlink.gif" width="16" height="16" class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="去掉超级链接" 
          onclick="FormatText('Unlink');ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/unlink.gif" width="16" height="16" class="Ico"> </LI>
        <LI language="javascript" class="Btn" 
          onmouseover="this.className='BtnMouseOverUp';" title="清理代码" 
          onclick="CleanCode();ondrag='return false;'" 
          onmouseout="this.className='Btn';"><IMG 
          src="img/cleancode.gif" width="16" height="16" class="Ico"> </LI>
              
      </UL>
      <UL id="PostiFrame" style="HEIGHT: 100%">
      <IFRAME onBlur="document.all.content.value=Composition.document.body.innerHTML" class="Composition" id="Composition" marginWidth="5" marginHeight="5" src="img/board.htm" width="100%" height="100%"></IFRAME>
	  </UL>
      <UL style="text-align:right">
        <A href="javascript:Size(-360)"><IMG src="img/minus.gif" width="20" height=20 border=0></A>
		<A href="javascript:Size(360)"><IMG src="img/plus.gif" width="20" height=20 border=0></A>
      </UL>
    </DIV>
	<Script Src="JS/DhtmlEdit.js"></Script>
    <Script src="JS/editor_s.js" type="text/javascript"></Script>
</body>