$(document).ready(function() {
    "use strict";

    // FULLWIDTH SEARCH
    $(".s-search").on('click', function() {
        $(".search-block").addClass("search-block-act");
        $(".close-btn").addClass("close-btn-active");
    });

    $(".close-btn").on('click', function() {
        $(".search-block").removeClass("search-block-act");
        $(".close-btn").removeClass("close-btn-active");
    });

    $(".mob-menu-trigger").on('click', function() {
        $(".mob-menu").toggleClass("active");
    });

    $(".mob-menu ul li a").on('click', function() {
        $(".mob-menu").removeClass("active");
    });

    $('.remove').on('click', function() {
        $(this).parent().parent().fadeOut();
    });

    function validateEmail(email) {
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    function validate() {
        var $result = $("#result");
        var email = $("#email").val();
        $result.text("");

        if (validateEmail(email)) {
            $result.text(email + " введена");
            $result.css("color", "green");
            return true;
        } else {
            $result.text(email + " - некорректная почта ");
            $result.css("color", "red");
        }
        return false;
    }

    $("#validate").on("click", validate);

});

// RESPONSIVE MENU
$(window).load(function() {
    $('#mobnav-btn').click(
        function() {
            $('.sf-menu').toggleClass("xactive");
        });
    $('.mobnav-subarrow').click(
        function() {
            $(this).parent().toggleClass("xpopdrop");
        });
});

    $("ul.sf-menu a:not([href^='#'])").addClass("zindex");

var opac=0;
function searchFunc() {
    var elements=document.getElementsByClassName('other-pages-search');
    if (elements.length>0){
        if(opac===0){
            opac++;
            pull(elements[0]);
        } else {
            opac--;
            push(elements[0]);
        }
    }
    function pull(el){
        var img = $('.other-pages-search');
        var marg=document.getElementById("search").offsetWidth;
        var marginx=(marg )/-1;
        img.animate({
            marginLeft: "+=" + marginx + "px",
            opacity: "+=1"
        }, 1000, function () {
        });
    }
    function push(el){
        var img = $('.other-pages-search');
        var marg=document.getElementById("search").offsetWidth;
        var marginx=marg;
        img.animate({
            marginLeft: "+=" + marginx + "px",
            opacity: "+=-1"
        }, 1000, function () {
        });
    }
}



