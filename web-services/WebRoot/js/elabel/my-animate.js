/**
 * 页面上的动态效果功能
 * 
 * 包括返回页面顶部按钮的显示、隐藏按钮，菜单的隐藏与显示灯动画效果
 * 
 * @param $ 
 */

(function ($) {
	//隐藏返回顶部按钮
	$(".BackTop").hide();
	
	$().ready(function() {
		//显示或隐藏返回顶部按钮
		$(window).scroll(function(){  
	        if ($(window).scrollTop() > 100) {
	        	//使用淡入效果来显示返回顶部按钮
	            $(".BackTop").fadeIn(1000);  
	        } else {
	        	//使用淡出效果来隐藏返回顶部按钮
	            $(".BackTop").fadeOut(1000);
	        }  
	    });
		
		//当点击跳转链接后，回到页面顶部位置，平滑的回到顶部
		$('.BackTop').click(function(){
			$('html,body').animate({scrollTop:0},500);
		});
		
		//菜单的隐藏与显示
		$(window).scroll(function(){
			if ($(window).scrollTop() > 5) {
				//使用淡入效果来隐藏菜单
				//$(".header").css("height", "113px");
				$("#menu-main").slideUp(500);
			} else {
				//使用淡出效果来显示菜单
				//$(".header").css("height", "180px");
				$("#menu-main").slideDown(500);
			}  
		});
	});
}(jQuery));