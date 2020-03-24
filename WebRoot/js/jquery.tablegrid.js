/**//*
  22009-7-3修改
  3作者：Allen
  4版权没有，随便使用
  5参考自tablegrid和tableresizer
  6功能

  8    2，列宽可拖动
10*/
(function($) {

$.fn.tablegrid = function(options){

    var opts = $.extend({}, $.fn.tablegrid.defaults, options);
    
    //拖动列宽
    var resize_columns = function(root)
    {                  
       var tbl = root.children("table");    //找到table
        var tr  = tbl.find("tr:first");  //找到第一行
        var header,newwidth;
        var resize = false;
        
        root.width(tbl.width()); //table的宽度
        tr.children("td").css("border-right",opts.col_border);  //给第一行的th加上一个css
        var left_pos = root.offset().left;
   
        endresize = function()
       {
            if(resize == true && header != null)
           {
                document.onselectstart=new Function ("return true");
                resize = false;
                root.children("table").css("cursor","");
            }  
       };
       
        tbl.mousemove(function(e)
       {
            var left = (e.clientX - left_pos);
    			
            if(resize)
            {
               var width = left - (header.offset().left - left_pos)
                    - parseInt(header.css("padding-left"))
                    - parseInt(header.css("padding-right"));
    
                if(width > 1)
                {
                    var current_width = header.width();
                    if(width > current_width)
                    {
                        var total = root.width() + ((width - header.width()));
                        root.width(total);
                        header.width(width);
                    }
                   else
                   {
                       header.width(width);
                        if(header.width() == width)
                       {
                            var total = root.width() + ((width - current_width));
                           root.width(total);
                       }
                    }
                   newwidth = width;
                }
            }
            else
            {
                if(e.target.nodeName == "TD")
                {
                    var tgt = $(e.target);
                   var dosize = (left-(tgt.offset().left-left_pos) 
                        > tgt.width()-4);
                   $(this).css("cursor",dosize?"col-resize":"");
                }
            }                  
        });
		
       
        tbl.mouseup(function(e) 
       {
            endresize();
        });
                
        tbl.bind("mouseleave",function(e)
       {
            endresize();
            return false; 
        });
        
        tr.mousedown(function(e) 
        {
           if(e.target.nodeName == "TD" 
               && $(this).css("cursor") ==  "col-resize")
            {
                header = $(e.target);                    
                resize = true;
               document.onselectstart=new Function ("return false");
            }    
            return false;
        });
        
       tr.bind('mouseleave',function(e)
        {
            if(!resize)
                root.children("table").css("cursor","");
        });
    };
    
    return this.each(function() {
        var root = $(this).wrap("<div class='roottbl' />").parent();
        resize_columns(root);
    });
};
})(jQuery);


