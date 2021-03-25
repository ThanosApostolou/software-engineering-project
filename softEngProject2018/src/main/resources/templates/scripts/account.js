var RegisterCallback = function (data) {
    alert("Επιτυχής Εγγραφή");
    location.reload();
};

var LoginSuccessCallback = function (data, textStatus, jqXHR) {
    var response = jqXHR.getResponseHeader('X-OBSERVATORY-AUTH');
    if (response) {
        Cookies.set('token', response, { secure: true });
        alert("Επιτυχής Είσοδος!")
        location.reload();
    }
}

var LogoutCallback = function (data) {
    Cookies.remove('token');
    alert("Επιτυχής Αποσύνδεση");
    location.reload();
};

$(document).ready(function() {

    $("#LoginForm").submit(function(e) {
        e.preventDefault();
        var inputName = $("input#inputName").val();
        var inputPassword = $("input#inputPassword").val();
        var query = {
            username: inputName,
            password: inputPassword
        };
        
        $.ajax({
            method: 'POST',
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            url:  prefix + "/login",
            data: query,
            dataType: 'json',
            success: LoginSuccessCallback,
            error: ErrorFunction
        });
    });
    
    $("#RegisterForm").submit(function(e) {
        e.preventDefault();
        var inputRegisterName = $("input#inputRegisterName").val();
        var inputRegisterEmail = $("input#inputRegisterEmail").val();
        var inputRegisterPassword = $("input#inputRegisterPassword").val();
        var query = {
            username: inputRegisterName,
            password: inputRegisterPassword,
            email: inputRegisterEmail
        };
        // RestGet (endpoint, query, callback)
        RestPost ("/users/sign_up", query, RegisterCallback);
    });

    $("#buttonLogout").click(function() {
        RestPost ("/logout", "", LogoutCallback);
    });   
});
