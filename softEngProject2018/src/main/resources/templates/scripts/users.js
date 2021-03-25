var GetUserErrorCallback = function (jqXHR, textStatus, errorThrown) {
    console.log(jqXHR.status + ' ' + jqXHR.responseText);
    var outputContent = '<h2>Μόνο οι Διαχειριστές μπορούν να δουν τους υπόλοιπους χρήστες!!</h2>'
    
    $('#outputDiv').html(outputContent);
};

var DeleteUserCallback = function (data) {
    alert(data.message);
    location.reload();
};

function DeleteButton (someid) {
    RestDelete("/users/" + someid, DeleteUserCallback);
}

var GetUsersCallback = function (data) {

    var outputContent = ' \
        <table class="table"> \
        <thead> \
            <tr> \
                <th scope="col">ID</th> \
                <th scope="col">Όνομα</th> \
                <th scope="col">Email</th> \
                <th scope="col">Ενεργός</th> \
                <th scope="col">Διαχειριστής</th> \
                <th scope="col"></th> \
            </tr> \
        </thead> \
        <tbody> \
    ';
        
    for (var p of data.appUsers) {
        outputContent += ' \
            <tr> \
                <th scope="col">' + p.id + '</th> \
                <th scope="col">' + p.username + '</th> \
                <th scope="col">' + p.email + '</th> \
                <th scope="col">' + p.enabled + '</th> \
                <th scope="col">' + p.admin + '</th> \
                <th scope="col"><button type="button" class="btn btn-primary LOGGED LOGGED_ENABLE"  disabled \
                    onclick="DeleteButton(' + p.id + ')" >Διαγραφή</button></th> \
            </tr> \
        ';
    }

    outputContent += '</tbody></table>'

    outputContent += ' \
        <div> \
            <ul class="pagination"> \
                <li class="page-item"><a class="nav-link disabled">Συνολικοί Χρήστες: '+ data.total + '</a></li> \
    ';  
    if ([[${start}]] == 0) {
        outputContent += '<li class="page-item disabled"> ';
    } else {
        outputContent += '<li class="page-item"> ';
    }
    outputContent += '<a class="page-link" href="/users?start='
                             + ([[${start}]] - 1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">Προηγούμενη</a></li>';

    for (i=1; i<=Math.ceil(data.total/data.count); i++) {
        if (i-1 == [[${start}]]) {
            outputContent += '<li class="page-item active">';
        } else {
            outputContent += '<li class="page-item">';
        }
        outputContent += '<a class="page-link" href="/users?start='
                             + (i-1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">' + i + '</a></li>';
    }

    if ([[${start}]] >= Math.ceil(data.total/data.count)-1) {
        outputContent += '<li class="page-item disabled">';
    } else {
        outputContent += '<li class="page-item">';
    }
    outputContent += ' <a class="page-link" href="/users?start='
                             + ([[${start}]] + 1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">Επόμενη</a></li> ';
    
    outputContent += ' </ul> \
        </div> \
    ';

    $('#outputDiv').html(outputContent);
    if(Cookies.get('token')) {
        $("button.LOGGED_ENABLE").attr("disabled", false);
    }
};

$(document).ready(function() {
    var start = [[${start}]];
    var count = [[${count}]];
    var status = [[${status}]];
    var sort = [[${sort}]];
    var query = "start="+start+"&count="+count+"&status="+status+"&sort="+sort; 

    var token = Cookies.get('token');
    $.ajax({
        headers: {
            'X-OBSERVATORY-AUTH': token
        },
        method: 'GET',
        url:  prefix + "/users",
        data: query,
        dataType: 'json',
        success: GetUsersCallback,
        error: GetUserErrorCallback
    });

    $("#UsersForm").submit(function(e) {
        e.preventDefault();
        var inputCount = $("input#inputCount").val();
        var selectStatus = $("select#selectStatus").val();
        var selectSort = $("select#selectSort").val();
        var suffix = "?count="+inputCount+"&status="+selectStatus+"&sort="+selectSort;

        location.href = "/users" + suffix;
            
    });

    $

});
