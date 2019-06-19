ymaps.ready(init);
function init() {
    var myMap;
    myMap = new ymaps.Map("map", {
        center: [53.225875, 50.194178], // Координаты объекта
        zoom: 13 // Маштаб карты
    });

    myMap.controls.add(
        new ymaps.control.ZoomControl()
    );

    myPlacemark1 = new ymaps.Placemark([53.225875, 50.194178], { // Координаты метки объекта
    }, {
        preset: "twirl#redDotIcon" // Тип метки
    });

    myMap.geoObjects.add(myPlacemark1);
    myMap.controls.remove('fullscreenControl');
    isCreate=true;
}