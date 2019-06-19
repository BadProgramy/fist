var carous = $("#home-shop-carousel");
carous.owlCarousel({
    itemsCustom: [
        [0, 1],
        [450, 1],
        [600, 1],
        [700, 2],
        [1000, 4]
    ],
    autoPlay: 4000,
    slideSpeed: 1000,
    navigation: false,
    pagination: true
});


function isVisible(elem) {

    var coords = elem.getBoundingClientRect();

    var windowHeight = document.documentElement.clientHeight;

    var topVisible = coords.top > 0 && coords.top < windowHeight;
    var bottomVisible = coords.bottom < windowHeight && coords.bottom > 0;

    return topVisible || bottomVisible;
}
function showVisible() {
    var imgs = document.getElementsByTagName('img');
    var img, realsrc;
    for (var i = 0; i < imgs.length; i++) {
         img = imgs[i];
         realsrc = img.getAttribute('realsrc');
        if (!realsrc) continue;
        if (isVisible(img)) {
            img.src = realsrc;
            img.setAttribute('realsrc', '');
        }
    }
}
function homeShowVisible() {
    var widgets = document.getElementsByClassName('widgets');
    if(widgets.length>0){
        if (isVisible(widgets[0])) {
            VK_Widget_Init();
            VK_Widget_Init1();
            $('.snapwidget-widget').attr('src', 'https://snapwidget.com/embed/683995');
            $(".pricing-bg").removeClass('widgets');
        }
    }
}
window.onscroll = function () {  homeShowVisible();showVisible();};
homeShowVisible();
showVisible();

var re=false;
function checkPosition(){
    var div_position = $(".lines").offset();
    var div_top = div_position.top;
    var div_left = div_position.left;
    var div_width = $(".lines").width();
    var div_height = $(".lines").height();
    var top_scroll = $(document).scrollTop();
    var left_scroll = $(document).scrollLeft();
    var screen_width = $(window).width();
    var screen_height = $(window).height();
    var see_x1 = left_scroll;
    var see_x2 = screen_width + left_scroll;
    var see_y1 = top_scroll;
    var see_y2 = screen_height + top_scroll;
    var div_x1 = div_left;
    var div_x2 = div_left + div_width;
    var div_y1 = div_top;
    var div_y2 = div_top + div_height;
    if( div_x1 >= see_x1 && div_x2 <= see_x2 && div_y1 >= see_y1 && div_y2 <= see_y2 ){
        var comma_separator_number_step = $.animateNumber.numberStepFactories.separator(' ');
        $(".lines").each(function () {
            var tcount = $(this).data("count");
            $(this).animateNumber({
                number: tcount,
                easing: 'easeInQuad',
                "font-size": "50px",
                numberStep: comma_separator_number_step}, 4000);
        });
    }
    re=true;
}
function resizeTable() {
    var secondItem = document.getElementById("secondT");
    var second = document.getElementById("secondTT");
    secondItem.style.height = document.getElementById("firstT").offsetHeight + 'px';
    secondItem.style.overflow = 'hidden';
    second.style.height = document.getElementById("firstTT").offsetHeight + 'px';
}
if(re===false) {
    $(document).ready(function () {
        resizeTable();
        checkPosition();
        $(document).scroll(function () {
            checkPosition();
            resizeTable();
        });
    });
}

var someimage = document.getElementById('content');
var myimgs = someimage.getElementsByTagName('img');
var mysrc,mysrc2;
var parent;
mysrc=myimgs[0].src;
mysrc2=myimgs[1].src;
if((mysrc.charAt(mysrc.length-2)==='/') && (mysrc.charAt(mysrc.length-1)==='0') &&(mysrc2.charAt(mysrc2.length-2)==='/') && (mysrc2.charAt(mysrc2.length-1)==='0')){
    parent=myimgs[0].parentNode;
    parent.style.display='none';
    parent=myimgs[1].parentNode;
    parent.style.display='none';
}

