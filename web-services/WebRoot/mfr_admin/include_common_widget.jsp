<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link href="/res/cs_online/lrkf.css" rel="stylesheet">
<script src="/res/cs_online/lrkf.js" charset='utf-8'></script>
<script>
$(function(){
	$("#lrkfwarp").lrkf({
		kftop:'140',				//距离顶部距离
		defshow:false,			//如果想默认折叠，将defshow:false,的注释打开，默认为展开
		//position:'absolute',		//如果为absolute所有浏览器在拖动滚动条时均有动画效果，如果为空则只有IE6有动画效果，其他浏览器
		qqs:[
				{'name':'客服小云','qq':'2047565197'},			//注意逗号是英文的逗号
				//{'name':'王健','qq':'5760359'},
				//{'name':'小宋','qq':'405109140'}			//注意最后一个{}不要英文逗号
		],
		tel:[
			{'name':'客服专线','tel':'021-50301707'},	//注意逗号是英文的逗号
		],
		qrCode:'/res/cs_online/qrcode_wx.jpg',	//二维码的路径，自行修改
		//more:"http://www.n369.com"				//>>更多方式		
	});
		
});
</script>