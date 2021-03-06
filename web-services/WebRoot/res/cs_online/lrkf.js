(function($) {
    $.fn.lrkf = function(options) {
        var opts = {
            position: "fixed",
            btntext: "客服在线",
            qqs: [{
                name: "技术支持",
                qq: "5760359"
            }],
            tel: "",
			qrCode:'',
            more: null,
            kftop: "120",
            z: "99999",
            defshow: true,
            callback: function() {}
        },
        $body = $("body");
        $.extend(opts, options);
        if (!$("#lrkfwarp").length > 0) {
            $body.append("<div id='lrkfwarp' class='lrkf lrkfshow' style=" + opts.position + "><a href='#' class='lrkf_btn lrkf_btn_hide' id='lrkf_btn' onfocus='this.blur()'>" + opts.btntext + "</a><div class='lrkf_box'><div class='lrkf_header'><a href='#' title='关闭' class='x' id='lrkf_x'></a></div><div class='lrkf_con' id='lrkf_con'><ul class='kflist'></ul></div><div class='lrkf_foot'></div></div></div><style>html{overflow-x:hidden;}#lrkfwarp ul{padding-left:0; margin:0;list-style-type: none;}.lrkf{font-size:13px;position:fixed;}.lrkf a{ display:block; color:#666; text-decoration:none; font-size:13px;}#lrkfwarp img{ border:none;vertical-align:middle; margin-right:4px; margin-top:-2px;}.lrkf_con{padding:6px 8px;}.lrkf_con li.qq{padding:5px 0;}.lrkf_con li.tel{ line-height:1.35; padding-bottom:4px;}.lrkf_con li.more{ padding:2px 0;}.lrkf_con li.tel b{ display:block; color:#C00;}.lrkf_tool a{ display:block; padding:8px 10px; text-align:center;}.lrkf_con .hr{padding:0;height:0;font-size:0;line-height:0;clear:both;margin:4px 0;border-bottom:#fff solid 1px;border-top:#CFCFCF solid 1px;border-left:none;border-right:none;}.lrkf_btn{position:absolute; top:20px;width:22px;left:-22px;display:block;text-align:center;padding:10px 0;}.lrkf .lrkf_xc{ position:absolute; bottom:-14px; right:6px;font-size:10px;display:none;}</style>")
        }
        var $lrkfwarp = $("#lrkfwarp"),
        $lrkf_con = $("#lrkf_con"),
        $kflist = $lrkf_con.children("ul"),
        $lrkf_x = $("#lrkf_x"),
        $lrkf_btn = $("#lrkf_btn"),
        $lrkfwarp_w = $lrkfwarp.outerWidth() * 1 + 1;
        $lrkfwarp.css({
            top: opts.kftop + "px",
            "z-index": opts.z
        });
        if (!opts.defshow) {
            $lrkfwarp.removeClass("lrkfshow").css({
                right: -$lrkfwarp_w
            })
        }
        var json = {
            options: opts.qqs
        };
        json = eval(json.options);
        $.each(json,
        function(i, o) {
            $kflist.append("<li class='qq'><a target='_blank' href='http://wpa.qq.com/msgrd?v=3&uin=" + o.qq + "&site=qq&menu=yes'><img src='http://wpa.qq.com/pa?p=2:" + o.qq + ":52' title='点击这里给我发消息'>" + o.name + "</a></li>");
            $kflist.append();
        });
        if (opts.tel) {
            $kflist.append("<li class='hr'></li>");
            var json_tel = {
                options: opts.tel
            };
            json_tel = eval(json_tel.options);
            $.each(json_tel,
            function(i, o) {
                $kflist.append("<li class=tel>" + o.name + ":<b>" + o.tel + "</b></li>")
            })
        }
        if(opts.qrCode){
        	$kflist.append("<li class='hr'></li>扫描关注微信");
        	$kflist.append("<li class='lrkf_qrcode'><img src='"+opts.qrCode+"'/></li>");
		 }
        if (opts.more) {
            $kflist.append("<li class='hr'></li><li class='more'><a href='" + opts.more + "'>>>更多方式</a></li>")
        }
        var $lrkfwarptop = $lrkfwarp.offset().top;
        if ($.browser.msie && $.browser.version == 6 || opts.position == "absolute") {
            $(window).scroll(function() {
                var offsetTop = $lrkfwarptop + $(window).scrollTop() + "px";
                $lrkfwarp.animate({
                    top: offsetTop
                },
                {
                    duration: 600,
                    queue: false
                })
            })
        }
        $lrkf_x.click(function() {
            $lrkfwarp.hide();
            return false
        });
        $lrkf_btn.click(function() {
            if ($lrkfwarp.hasClass("lrkfshow")) {
                $lrkfwarp.animate({
                    right: -$lrkfwarp_w
                },
                function() {
                    $lrkfwarp.removeClass("lrkfshow")
                })
            } else {
                $lrkfwarp.addClass("lrkfshow").animate({
                    right: 0
                })
            }
            return false
        })
    }
})(jQuery);