function bamenCardList() {
	$(".fakeloader").fakeLoader({
        timeToHide: 1500,
        bgColor: "#34495e",
        spinner: "spinner3",
        flag: true
    });
	$.post(
		'http://www.bamenzhushou.com:8080/bamen/bamenCard',
		{
			'type':'list',
			'appType':6
		},
		function(data) {
			var json = JSON.parse(data);
			if (json.status != 200)
				return;
			var str = '';
			$.each(json.data, function(index, item) {
				str +=  "<li><a href='bamenCardDetail.html?appid=" + item.appid + "'>" + 
							"<div class='top' appid='" + item.appid + "'>" + 
								"<div class='left'>" + 
									"<img src='" + item.icon + "' alt='" + item.appname + "' title='" + item.appname + "' border='0'>" + 
								"</div>" + 
								"<div class='right'>" + 
									"<h4>" + item.appname + "</h4>" + 
									"<p>大小：" + ((item.contentlength)/1024/1024).toFixed(2) + "M</p>" + 
									"<p>" + item.breif + "</p>" + 
								"</div>" + 
							"</div>" + 
						"</a></li>";
			});
			$('div.main_con ul.game_list').html(str);
		}
	);
}

function bamenCardDetail() {
	var appid = GetQueryString("appid");
	$.post(
		'http://www.bamenzhushou.com:8080/bamen/bamenCard',
		{
			'type':'detail',
			'appid':appid
		},
		function(data) {
			var json = JSON.parse(data);
			if (json.status != 200)
				return;
			var item = json.data;
			var ul = 'ul.game_list';
			var first_li = ul + ' li.first';
			$(first_li).find('div.left').html(
				"<img src='" + item.icon + "' alt='" + item.appname + "' title='" + item.appname + "' border='0' />"
			);
			$(first_li).find('div.right').html(
				"<h4>" + item.title + "</h4>" + 
				"<p>大小：" + ((item.contentlength)/1024/1024).toFixed(2) + "M</p>" + 
				"<p>" + item.breif + "</p>"
			);
			
			var str = '';
			if (item.downadress != null)
				str += "<img src='static/images/download_android_btn.png' onclick='download(\"" + item.downadress + "\", true)' width='45%' />";
			if (item.iosdownadress != null)
				str += "<img src='static/images/download_ios_btn.png' onclick='download(\"" + item.iosdownadress + "\", false)' width='45%' />";
			$(first_li).find('.download').html(str);
			
			$(ul).find('li').eq(1).append(
				"<p style='text-indent: 8px;'>" + item.content + "</p>"
			);
			
			var imgs = item.bannershow.split(',');
			$.each(imgs, function(index, img) {
				if (img != '') {
					$('#roll-img').append(
						"<div class='swiper-slide swiper-slide-visible'><img src='" + img + "' /></div>"
					);
				}
			});
			
			
		}
	);
	
	if (is_weixin()) {
        var img = document.getElementById('img');
        img.width = 180;
    }
    var userAgent = navigator.userAgent.toLowerCase();
    /*滚动浮层 取消*/
    $('#btn-cancel').click(function () {
        $('.overlay').animate({ bottom: "-900px" });
    });

    /*提示框 取消&确认*/
    $('.btn-cancel').click(function () {
        $('.confirmBox').css('visibility', 'hidden');
    });
    $('.btn-confirm').click(function () {
        window.open("http://tui.tongbu.com/HTML/guide-tui.html");
    });
}

function loadCSS(url) {
	var cssLink = document.createElement("link");
    cssLink.rel = "stylesheet";
    cssLink.type = "text/css";
    cssLink.media = "screen";
    cssLink.href = url;
    $('head').append(cssLink);
}

function GetQueryString(name) {
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

var is_weixin = function () {
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/MicroMessenger/i) == "micromessenger") {
        return true;
    } else {
        return false;
    }
}

var newSlideSize = function slideSize() {
	var w = Math
			.ceil($(".swiper-container")
					.width());
	$(".swiper-container,.swiper-slide")
			.height(w + 50);
};

function download(url, flag) {
    if (is_weixin()) {
        var winHeight = typeof window.innerHeight != "undefined" ? window.innerHeight : document.documentElement.clientHeight;
        //兼容IOS，不需要的可以去掉
        var tip = document.getElementById("weixin-tip");
        var close = document.getElementById("close");
        tip.style.height = winHeight + "px";
        //兼容IOS弹窗整屏
        tip.style.display = "block";
        close.onclick = function () {
            tip.style.display = "none";
        }
    } else {
    	flag ? doLocation(url) : (window.location.href = url);
    }
}

function doLocation(url) {
    var a = document.createElement("a");
    if (!a.click) {
        window.location = url;
        return;
    }
    a.setAttribute("href", url);
    a.style.display = "none";
    document.body.appendChild(a);
    a.click();
}