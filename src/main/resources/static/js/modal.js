jQuery(document).ready(function($){
    var formModal = $('.cd-user-modal1'),
        formLogin = formModal.find('#cd-login'),
        formButton=$('.close-btn'),
        formSignup = formModal.find('#cd-signup'),
        formForgotPassword = formModal.find('#cd-reset-password'),
        formModalTab = $('.cd-switcher'),
        tabLogin = formModalTab.children('li').eq(0).children('a'),
        tabSignup = formModalTab.children('li').eq(1).children('a'),
        mainNav = $('.product-thumb');



    //open modal
    mainNav.on('click', function(event){
        window.scrollBy(0, $(window).height());
        $(event.target).is(mainNav) && mainNav.children('ul').toggleClass('is-visible');
        formButton.addClass('visible');
    });


    //open login-form form
    mainNav.on('click', '.cd-signin', login_selected);


    //close modal
    formModal.on('click', function(event){
        if(  $(event.target).is('.close-btn') ) {
            formModal.removeClass('is-visible');
            formButton.removeClass('visible');
        }
    });
    //close modal when clicking the esc keyboard button
    $(document).keyup(function(event){
        if(event.which=='27'){
            formModal.removeClass('is-visible');
            formButton.removeClass('visible');
        }
    });



    function login_selected(){
        mainNav.children('ul').removeClass('is-visible');
        formModal.addClass('is-visible');
        formLogin.addClass('is-selected');
        formSignup.removeClass('is-selected');
        formForgotPassword.removeClass('is-selected');
        tabLogin.addClass('selected');
        tabSignup.removeClass('selected');
    }

});


jQuery.fn.putCursorAtEnd = function() {
    return this.each(function() {
        // If this function exists...
        if (this.setSelectionRange) {
            // ... then use it (Doesn't work in IE)
            // Double the length because Opera is inconsistent about whether a carriage return is one character or two. Sigh.
            var len = $(this).val().length * 2;
            this.focus();
            this.setSelectionRange(len, len);
        } else {
            // ... otherwise replace the contents with itself
            // (Doesn't work in Google Chrome)
            $(this).val($(this).val());
        }
    });
};