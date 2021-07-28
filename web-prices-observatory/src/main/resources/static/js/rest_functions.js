var prefix = '/observatory/api';
var ErrorFunction = function(jqXHR, textStatus, errorThrown) {
    console.log(jqXHR.status + ' ' + jqXHR.responseText);
    switch (jqXHR.status) {
        case 500:
            alert(jqXHR.status + ' ' + jqXHR.responseText);
            break;
        default:
            alert(jqXHR.status + ' ' + jqXHR.responseText);
    } 
       
};

var RestGet = function(endpoint, query, CallbackFunction) {
    var token = Cookies.get('token');
    $.ajax({
        method: 'GET',
        url:  prefix + endpoint,
        data: query,
        dataType: 'json',
        success: CallbackFunction,
        error: ErrorFunction
    });
};


var RestPost = function(endpoint, query, CallbackFunction) {
    var token = Cookies.get('token');
    $.ajax({
        headers: {
            'X-OBSERVATORY-AUTH': token
        },
        method: 'POST',
        contentType: "application/json; charset=utf-8",
        url:  prefix + endpoint,
        data: JSON.stringify(query),
        dataType: 'json',
        success: CallbackFunction,
        error: ErrorFunction
    });
};

var RestPut = function(endpoint, query, CallbackFunction) {
    var token = Cookies.get('token');

    $.ajax({
        headers: {
            'X-OBSERVATORY-AUTH': token
        },
        method: 'PUT',
        contentType: 'application/json; charset=utf-8',
        url:  prefix + endpoint,
        data: JSON.stringify(query),
        dataType: 'json',
        success: CallbackFunction,
        error: ErrorFunction
    });
};


var RestDelete = function(endpoint, CallbackFunction) {
    var token = Cookies.get('token');
    
    $.ajax({
        headers: {
            'X-OBSERVATORY-AUTH': token
        },
        method: 'DELETE',
        url:  prefix + endpoint,
        dataType: 'json',
        success: CallbackFunction,
        error: ErrorFunction
    });
};
