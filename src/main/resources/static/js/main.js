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