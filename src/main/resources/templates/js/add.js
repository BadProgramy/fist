//(function($) {
$(function() {
//------------------------------------------------------------------------------
/*
var owl_0 = $('.owl-carousel-0.owl-carousel');
owl_0.owlCarousel({
    animateIn: 'uk-animation-slide-bottom', //'fadeInRight',
    animateOut: 'uk-animation-reverse uk-animation-slide-top', //'fadeOutDown',
    //animateOut: 'fadeOut',
    //animateIn: 'flipInX',
    items: 1,
    margin: 0,
    stagePadding: 0,
    dots: false,
    nav: false,
    //smartSpeed: 500,
    loop: true,
    autoplay: true,
    autoplayTimeout: 3000,
    autoplayHoverPause: false,
});
*/
//------------------------------------------------------------------------------
var owl_1 = $('.owl-carousel-1.owl-carousel');
owl_1.owlCarousel({
    items: 1,
    startPosition: 0,
    slideBy: 1,
    //center: true,
    loop: true,
    //mouseDrag: false,
    stagePadding: 0,
    margin: 32,
    dots: false,
    //nav: false,
    //navText:[0,1],
    //navElement: 'div',
    //autoWidth: true,
    autoHeight: true,
    //autoHeightClass: 'owl-height',
    //animateOut: 'fadeOut',
    //animateIn: 'fadeIn',
    smartSpeed: 1000,
    //autoplay: true,
    //autoplayTimeout: 4000,
    //autoplayHoverPause: true,

	responsiveClass:true,
	responsive: {
		768: {
			items: 1,
			startPosition: 0,
			margin: 80,
		}
	},
    onInitialized: function(event) {},
    onChange: function(event) {},
    onChanged: function(event) {},
    onTranslated: function(event) {},
});
// Go to the next item
$('.i_nav-button-next').click(function() {
    owl_1.trigger('next.owl.carousel');
});
// Go to the previous item
$('.i_nav-button-prev').click(function() {
    //owl_1.trigger('prev.owl.carousel', [300]);
    owl_1.trigger('prev.owl.carousel');
});

//------------------------------------------------------------------------------
});
//})(jQuery);