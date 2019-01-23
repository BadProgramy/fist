/******/ (function(modules) { // webpackBootstrap
/******/ 	// The module cache
/******/ 	var installedModules = {};
/******/
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/
/******/ 		// Check if module is in cache
/******/ 		if(installedModules[moduleId]) {
/******/ 			return installedModules[moduleId].exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = installedModules[moduleId] = {
/******/ 			i: moduleId,
/******/ 			l: false,
/******/ 			exports: {}
/******/ 		};
/******/
/******/ 		// Execute the module function
/******/ 		modules[moduleId].call(module.exports, module, module.exports, __webpack_require__);
/******/
/******/ 		// Flag the module as loaded
/******/ 		module.l = true;
/******/
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/
/******/
/******/ 	// expose the modules object (__webpack_modules__)
/******/ 	__webpack_require__.m = modules;
/******/
/******/ 	// expose the module cache
/******/ 	__webpack_require__.c = installedModules;
/******/
/******/ 	// identity function for calling harmony imports with the correct context
/******/ 	__webpack_require__.i = function(value) { return value; };
/******/
/******/ 	// define getter function for harmony exports
/******/ 	__webpack_require__.d = function(exports, name, getter) {
/******/ 		if(!__webpack_require__.o(exports, name)) {
/******/ 			Object.defineProperty(exports, name, {
/******/ 				configurable: false,
/******/ 				enumerable: true,
/******/ 				get: getter
/******/ 			});
/******/ 		}
/******/ 	};
/******/
/******/ 	// getDefaultExport function for compatibility with non-harmony modules
/******/ 	__webpack_require__.n = function(module) {
/******/ 		var getter = module && module.__esModule ?
/******/ 			function getDefault() { return module['default']; } :
/******/ 			function getModuleExports() { return module; };
/******/ 		__webpack_require__.d(getter, 'a', getter);
/******/ 		return getter;
/******/ 	};
/******/
/******/ 	// Object.prototype.hasOwnProperty.call
/******/ 	__webpack_require__.o = function(object, property) { return Object.prototype.hasOwnProperty.call(object, property); };
/******/
/******/ 	// __webpack_public_path__
/******/ 	__webpack_require__.p = "";
/******/
/******/ 	// Load entry module and return exports
/******/ 	return __webpack_require__(__webpack_require__.s = 21);
/******/ })
/************************************************************************/
/******/ ({

/***/ 13:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 16:
/***/ (function(module, exports) {

// removed by extract-text-webpack-plugin

/***/ }),

/***/ 21:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(4);


/***/ }),

/***/ 4:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


__webpack_require__(16);

__webpack_require__(13);

var _app = __webpack_require__(8);

var _app2 = _interopRequireDefault(_app);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.app = _app2.default;

/***/ }),

/***/ 8:
/***/ (function(module, exports, __webpack_require__) {

"use strict";


$(function () {
    ////////////////////////////////////////////////////////////////////////////////
    var screen_xs = 1; //0 em
    var screen_sm = 544; //34 em
    var screen_md = 768; //48 em
    var screen_lg = 992; //62 em
    var screen_xl = 1200; //75 em

    var sc = new ScrollMagic.Controller();
    var ss = new ScrollMagic.Scene();
    var tl = new TimelineLite({
        //useFrames: true,
        //delay: 0,
        //onStart: function(){ },
        //onComplete: function(){ }
    });

    ////////////////////////////////////////////////////////////////////////////////
    $('[data-toggle="tooltip"]').tooltip();
    $('[data-toggle="tooltip"]').hover(function () {
        $('#' + $(this).attr('aria-describedby')).addClass('i_tooltip--' + $(this).attr('data-link-pane'));
    });
    function iDeskMouseWheel(e) {
        $('.i_d__ctrl').mousewheel(function (e, delta) {
            if ($('.i_html').hasClass('mac')) {
                this.scrollLeft -= delta;
                e.preventDefault();
            } else {
                this.scrollLeft -= delta * 40;
                e.preventDefault();
            }
        });
    };
    function iDeskUnMouseWheel(e) {
        $('.i_d__ctrl').unmousewheel();
    };
    function iLoad(e) {
        var smc_1 = new ScrollMagic.Controller({ container: '.i_d' });
        new ScrollMagic.Scene({ triggerElement: '.i_d__side-i', offset: 65, duration: 0 }).setClassToggle('.i_a--i .i_a__icon', 'i_a__icon--minimize').triggerHook('onLeave').addTo(smc_1);
        new ScrollMagic.Scene({ triggerElement: '.i_d__side-i', offset: 65, duration: 0 }).setClassToggle('.i_a--i .i_a__label', 'i_a__label--minimize').triggerHook('onLeave').addTo(smc_1);
        new ScrollMagic.Scene({ triggerElement: '.i_d__side-i', offset: 10, duration: 0 }).setClassToggle('.i_a--i .i_a__back .i_a__c img.с1', 'с1--minimize').triggerHook('onLeave').addTo(smc_1);

        if (!$('.i_html').hasClass('touch')) {
            if (!$('.i_body').hasClass('i_view--i')) {
                iDeskMouseWheel(e);
            } else {
                iDeskUnMouseWheel();
            }
            $('.i_d__side').mCustomScrollbar({ theme: 'minimal-dark' });
        }
    }
    iLoad();
    ////////////////////////////////////////////////////////////////////////////////
    /*
    var scr = (Modernizr.mq('(max-width: 991px)')) ? 'md' : 'lg';
    $(window)
    .bind('load', function(){
        $('.i_d__side').mCustomScrollbar({
            theme:'minimal-dark',
            //scrollButtons: { enable: true }
        });
        if (scr === 'md') {
            $('.i_d__side').mCustomScrollbar("disable", true);
        }
    })
    .bind('resize', function(){
        var newscr;
        newscr = (Modernizr.mq('(max-width: 991px)')) ? 'md' : 'lg';
        if (newscr !== scr && newscr === 'lg') {
            $('.i_b__w--scroll').mCustomScrollbar("update");
        } else if (newscr !== scr && newscr === 'md') {
            $('.i_b__w--scroll').mCustomScrollbar("disable", true);
        }
        scr = newscr;
        return false;
    }).trigger('resize');
    */
    ////////////////////////////////////////////////////////////////////////////////
    $(document).on('click', '.i_p__b-panel__sign', function (e) {
        $(this).toggleClass("cross");
        $('.i_panel').toggleClass("i_panel--active");
    });
    $(document).on('click', '.i_panel--active', function (e) {
        //console.log(e);
        if (e.target.className == 'i_panel i_panel--active') {
            $('.i_panel--active').toggleClass("i_panel--active");
            $('.i_p__b-panel__sign').toggleClass("cross");
        }
    });
    $(document).on('click', '.i_navi-mob__item', function (e) {
        $('.i_p__b-panel__sign').toggleClass("cross");
        $('.i_panel').toggleClass("i_panel--active");
    });
    ////////////////////////////////////////////////////////////////////////////////
    $(document).on('click', '.i_body--1 .i_a__link', function (e) {
        //e.preventDefault();
        $('.i_n__item').removeClass("i_n__item--active");
        $('.i_n__item[data-link-pane^="' + $(this).attr('data-link-pane') + '"]').addClass("i_n__item--active");

        var w = $('.i_body');
        var d = $('.i_d__ctrl');
        var i = $('.i_a[data-pane^="' + $(this).attr('data-link-pane') + '"]');

        new TimelineLite().add('i').to(i, 0, { className: '+=i_a--i' }).set(i, { className: '-=i_a--x' }).set(w, { className: '+=i_view--y' }).set(w, { className: '+=i_view--x' }, '+=.5').set(w, { className: '+=i_view--i' }, '+=.75').set(w, { className: '-=i_view--x' }, '+=.75').set(w, { className: '+=i_view--z' }).to(d, .625, { scrollTo: { x: 0 }, onComplete: function onComplete() {
                iDeskUnMouseWheel();
            } }, 'i+=.625');
    });
    $(document).on('click', '.i_body--1 .i_n__item, .i_body--1 .i_n-m__item', function (e) {
        //e.preventDefault();
        if ($('.i_panel').hasClass('i_panel--active')) {
            $('.i_panel--active').removeClass("i_panel--active");
            $('.i_p__b-panel__sign').removeClass("cross");
        }
        $('.i_n__item').removeClass("i_n__item--active");
        $('.i_n__item[data-link-pane^="' + $(this).attr('data-link-pane') + '"]').addClass("i_n__item--active");

        var w = $('.i_body');
        var x = $('.i_a--i');
        var i = $('.i_a[data-pane^="' + $(this).attr('data-link-pane') + '"]');
        if ($(this).attr('data-link-pane') != $('.i_a--i').attr('data-pane')) {
            new TimelineLite().set(w, { className: '-=i_view--ii' }).set(w, { className: '+=i_view--c' }).set(x, { className: '+=i_a--x' }).set(i, { className: '+=i_a--i' }).set(x, { className: '-=i_a--i' }).set(i, { className: '-=i_a--x' }).set(w, { className: '-=i_view--c' }, '+=1.25');
        } else {
            new TimelineLite().set(w, { className: '-=i_view--ii' });
        }
    });
    $(document).on('click', '.i_body--1 .i_menu__link', function (e) {
        //e.preventDefault();
        var w = $('.i_body');
        new TimelineLite().set(w, { className: '+=i_view--ii' });
    });
    $(document).on('click', '.i_body--1 .i_x__link', function (e) {
        //e.preventDefault();
        $('.i_n__item').removeClass("i_n__item--active");

        var w = $('.i_body');
        var i = $('.i_a--i');
        new TimelineLite().set(w, { className: '+=i_view--a' }).set(w, { className: '+=i_view--b' }).set(w, { className: '-=i_view--ii' }, '+=.5').set(w, { className: '-=i_view--i' }).set(w, { className: '+=i_view--x' }).set(w, { className: '-=i_view--x' }, '+=1.25').set(i, { className: '-=i_a--i' }).set(i, { className: '+=i_a--x', onComplete: function onComplete() {
                iDeskMouseWheel(e);
            } }).set(w, { className: '-=i_view--b' }, '+=.5').set(w, { className: '-=i_view--a' }).set(w, { className: '-=i_view--y' }).set(w, { className: '-=i_view--z' });
    });
    ////////////////////////////////////////////////////////////////////////////////
    if ($.support.pjax && !$('.i_html').hasClass('i_bx')) {
        $(document).pjax('a[data-pjax]', '.i_pjax', {
            timeout: 750,
            maxCacheLength: 0
        })
        //.on('pjax:click', function(options) { console.log(options); })
        .on('pjax:beforeSend', function (xhr, options) {}).on('pjax:popstate', function (e) {}).on('pjax:start', function (xhr, options) {}).on('pjax:send', function (xhr, options) {}).on('pjax:clicked', function (options) {}).on('pjax:beforeReplace', function (contents, options) {}).on('pjax:success', function (data, status, xhr, options) {}).on('pjax:timeout', function (xhr, options) {}).on('pjax:error', function (xhr, textStatus, error, options) {}).on('pjax:complete', function (xhr, textStatus, options) {}).on('pjax:end', function (xhr, options) {});
        $('.i_pjax').on('pjax:popstate', function (e) {
            location.reload();
        }).on('pjax:start', function (xhr, options) {}).on('pjax:beforeReplace', function (contents, options) {}).on('pjax:end', function (xhr, options) {
            iLoad();
            //$.pjax.reload('.i_n');
            //$.pjax.reload('.i_sys');
        });
    }
    ////////////////////////////////////////////////////////////////////////////////
    var closeFn;
    function closeShowingModal() {
        var showingModal = document.querySelector('.i_m--show');
        if (!showingModal) return;
        /**/$(".i_m--show .i_m__d iframe").remove();
        showingModal.classList.remove('i_m--show');
        showingModal.classList.remove('i_m--o');
        //document.body.classList.remove('i_disable-mouse');
        //document.body.classList.remove('i_disable-scroll');
        //$('.i_html').removeClass('i_disable-scroll');
        if (closeFn) {
            closeFn();
            closeFn = null;
        }
    }
    //document.addEventListener('click', function(e) {
    $(document).on('click', '[data-cta]', function (e) {
        e.preventDefault();
        //console.log(e);
        var target = e.target;
        //var target = e.target.closest('[data-cta-target]');
        //console.log(target);
        if (target.dataset.ctaTarget) {
            if (target.dataset.overlay) {
                $(target.dataset.ctaTarget).addClass('i_m--o');
            }
            closeFn = cta(target, document.querySelector(target.dataset.ctaTarget + '__d'), { duration: .5, relativeToWindow: false }, function showModal(modal) {
                $(target.dataset.ctaTarget).addClass('i_m--show'); //modal.classList.add('i_modal--show');
                /**/$(target.dataset.ctaTarget + "__v iframe").clone().appendTo(target.dataset.ctaTarget + '__d');
                //document.body.classList.add('i_disable-mouse');
                if (target.dataset.disableScroll) {
                    //document.body.classList.add('i_disable-scroll');
                    //$('.i_html').addClass('i_disable-scroll');
                }
            });
        } else if (target.classList.contains('i_m__x')) {
            closeShowingModal();
        }
    });
    document.addEventListener('keyup', function (e) {
        if (e.which === 27) {
            closeShowingModal();
        }
    });
    ////////////////////////////////////////////////////////////////////////////////
});

/***/ })

/******/ });
//# sourceMappingURL=bundle.js.map