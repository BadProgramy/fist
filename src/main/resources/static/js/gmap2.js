var isCreate=false;
if(!isCreate) {
    var el = document.getElementById("karta");
    el.addEventListener("click", createMap, false);

    function createMap() {
        if (!isCreate) {
            var time = 100;
            var newScript;
            newScript = document.createElement('script');
            newScript.setAttribute('type', 'text/javascript');
            newScript.setAttribute('src', 'https://api-maps.yandex.ru/2.1/?lang=ru_RU');
            for (var i = 0; i < time; i++) {
                if (newScript.addEventListener) {
                    newScript.addEventListener('load', creat, false);
                    document.getElementsByTagName('head')[0].appendChild(newScript);
                    break;
                }
            }
        }
    }
}

function creat() {
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
}