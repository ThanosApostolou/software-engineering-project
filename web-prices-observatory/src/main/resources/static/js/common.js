$(document).ready(function() {
    if(!Cookies.get('token')) {
        $('.LOGGED').hide();
        $('.NOT_LOGGED').show();
        $("input.LOGGED_ENABLE").attr("disabled", true);
        $("select.LOGGED_ENABLE").attr("disabled", true);
        $("button.LOGGED_ENABLE").attr("disabled", true);
    } else {
        $('.NOT_LOGGED').hide();
        $('.LOGGED').show();
        $("input.LOGGED_ENABLE").attr("disabled", false);
        $("select.LOGGED_ENABLE").attr("disabled", false);
        $("button.LOGGED_ENABLE").attr("disabled", false);
    }

    $("#searchNavbar").submit(function(e) {
        e.preventDefault();
        var inputSearchNavbar = $("input#inputSearchNavbar").val();
        location.href = "/find?tags=" + inputSearchNavbar;
    });

});
