type="text/javascript">

    ymaps.ready(init);
var myMap;

function init() {

    myMap = new ymaps.Map("map", {
        center: [53.225875, 50.194178], // Координаты объекта
        zoom: 13 // Маштаб карты
    });

    myMap.controls.add(
        new ymaps.control.ZoomControl()
    );

    myPlacemark1 = new ymaps.Placemark([53.225875, 50.194178], { // Координаты метки объекта
        /*balloonContent: "<div class='ya_map'>Заезжайте в гости!</div>" // Надпись метки*/
    }, {
        preset: "twirl#redDotIcon" // Тип метки
    });

    myPlacemark2 = new ymaps.Placemark([53.192471, 50.092919], { // Координаты метки объекта
        /*balloonContent: "<div class='ya_map'>Заезжайте в гости!</div>" // Надпись метки*/
    }, {
        preset: "twirl#redDotIcon" // Тип метки
    });

    myMap.geoObjects.add(myPlacemark1);
    myMap.geoObjects.add(myPlacemark2);
    myMap.controls.remove('fullscreenControl');
    myPlacemark.balloon.open();

};
