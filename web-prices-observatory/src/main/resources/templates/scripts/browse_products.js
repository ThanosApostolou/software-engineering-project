var GetProductsCallback = function (data) {    
    var outputContent = ' \
        <table class="table"> \
        <thead> \
            <tr> \
                <th scope="col">ID</th> \
                <th scope="col">Όνομα</th> \
                <th scope="col">Περιγραφή</th> \
                <th scope="col">Ετικέτες</th> \
                <th scope="col">Αποσυρμένο</th> \
                <th scope="col">Κατηγορία</th> \
                <th scope="col">Λεπτομέριες</th> \
            </tr> \
        </thead> \
        <tbody> \
    ';
        
    for (var p of data.products) {
        tagstring = p.tags.join();
        outputContent += ' \
            <tr> \
                <th scope="col">' + p.id + '</th> \
                <th scope="col">' + p.name + '</th> \
                <th scope="col">' + p.description + '</th> \
                <th scope="col">' + tagstring + '</th> \
                <th scope="col">' + p.withdrawn + '</th> \
                <th scope="col">' + p.category + '</th> \
                <th scope="col"><a class="nav-link" href="/show_product?id=' + p.id + '">Λεπτομέρειες</a>' + '</th> \
            </tr> \
        ';
    }

    outputContent += '</tbody></table>'

    outputContent += ' \
        <div> \
            <ul class="pagination"> \
                <li class="page-item"><a class="nav-link disabled">Συνολικά Προϊόντα: '+ data.total + '</a></li> \
    ';  
    if ([[${start}]] == 0) {
        outputContent += '<li class="page-item disabled"> ';
    } else {
        outputContent += '<li class="page-item"> ';
    }
    outputContent += '<a class="page-link" href="/browse_products?start='
                             + ([[${start}]] - 1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">Προηγούμενη</a></li>';

    for (i=1; i<=Math.ceil(data.total/data.count); i++) {
        if (i-1 == [[${start}]]) {
            outputContent += '<li class="page-item active">';
        } else {
            outputContent += '<li class="page-item">';
        }
        outputContent += '<a class="page-link" href="/browse_products?start='
                             + (i-1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">' + i + '</a></li>';
    }

    if ([[${start}]] >= Math.ceil(data.total/data.count)-1) {
        outputContent += '<li class="page-item disabled">';
    } else {
        outputContent += '<li class="page-item">';
    }
    outputContent += ' <a class="page-link" href="/browse_products?start='
                             + ([[${start}]] + 1) + '&count=' + [[${count}]] + '&status=' + [[${status}]] + '&sort=' + [[${sort}]] + '">Επόμενη</a></li> ';
    
    outputContent += ' </ul> \
        </div> \
    ';

    $('#outputDiv').html(outputContent);
};

$(document).ready(function() {
    var start = [[${start}]];
    var count = [[${count}]];
    var status = [[${status}]];
    var sort = [[${sort}]];
    var query = "start="+start+"&count="+count+"&status="+status+"&sort="+sort; 
    RestGet ("/products", query, GetProductsCallback);

    $("#BrowseProductsForm").submit(function(e) {
        e.preventDefault();
        var inputCount = $("input#inputCount").val();
        var selectStatus = $("select#selectStatus").val();
        var selectSort = $("select#selectSort").val();
        var suffix = "?count="+inputCount+"&status="+selectStatus+"&sort="+selectSort;

        location.href = "/browse_products" + suffix;
            
    });

});
