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


