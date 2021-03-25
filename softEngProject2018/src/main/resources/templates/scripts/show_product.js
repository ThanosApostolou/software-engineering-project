var PostPricesCallback = function (date) {
    alert ("Επιτυχής Προσθήκη");
    location.reload();
}

var GetPricesCallback = function (data) {
    var outputContent = ' \
        <table class="table"> \
        <thead> \
            <tr> \
                <th scope="col">Τιμή</th> \
                <th scope="col">Ημερομηνία</th> \
                <th scope="col">Κατάστημα</th> \
                <th scope="col">Ετικέτες Καταστήματος</th> \
            </tr> \
        </thead> \
        <tbody> \
    ';
    
    for (var p of data.prices) {
        tagstring = p.shopTags.join();
        outputContent += ' \
            <tr> \
                <th scope="col">' + p.price + ' €</th> \
                <th scope="col">' + p.date + '</th> \
                <th scope="col"><a class="nav-link" href="/show_shop?id=' + p.shopId + '">'+ p.shopName +'</a>' + '</th> \
                <th scope="col">' + tagstring + '</th> \
            </tr> \
        ';
    }

    outputContent += '</tbody></table>'
    /*
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
    */
    $('#outputDiv').html(outputContent);
}

var GetProductIDCallback = function (data) {
    $('#inputName').val(data.name);
    $('#inputDescription').val(data.description);
    $('#inputTags').val(data.tags.join());
    $('#selectStatus').val("" + data.withdrawn); // turn bool to string
    $('#inputCategory').val(data.category);
};


var PutProductCallback = function (data) {
    alert("Το προϊόν ενημερώθηκε!");
    location.reload();
};

var DeleteProductCallback = function (data) {
    alert("Το προϊόν διαγράφηκε!");
    location.href = "/browse_products";
};

            
$(document).ready(function() {
    var id = [[${id}]];
    var query;
    // RestGet (prefix, query, callback)
    RestGet ("/products/" + id, "", GetProductIDCallback);
    var current_date = new Date().toISOString().slice(0, 10)
    query="?products="+ id + "&dateFrom=0000-01-01" + "&dateTo=" + current_date;
    RestGet ("/prices", query, GetPricesCallback);
    
    $("#ShowProductForm").submit(function(e) {
        e.preventDefault();
        var inputName = $('#inputName').val();
        var inputDescription = $('#inputDescription').val();
        var inputTags = $('#inputTags').val();
        inputTags = inputTags.split(",");
        inputTags = JSON.stringify(inputTags);
        var selectStatus = $('#selectStatus').val();
        var inputCategory = $('#inputCategory').val();

        var query = {
            name: inputName,
            description: inputDescription,
            tags: JSON.parse(inputTags),
            withdrawn: selectStatus,
            category: inputCategory
        };

        RestPut ("/products/" + [[${id}]], query, PutProductCallback) 
    });

    $("#DeleteButton").click(function(e) {
        console.log("debug", e);
        RestDelete ("/products/" + [[${id}]], DeleteProductCallback);
    });

    $("#AddPriceForm").submit(function(e) {
        e.preventDefault();
        var inputPrice = $('#inputPrice').val();
        var inputDateFrom = $('#inputDateFrom').val();
        var inputDateTo = $('#inputDateTo').val();
        var inputShopId = $('#inputShopId').val();
        var query2 = {
            price: inputPrice,
            dateFrom: inputDateFrom,
            dateTo: inputDateTo,
            productId: id,
            shopId: inputShopId
        };

        RestPost ("/prices", query2, PostPricesCallback)
    });

});
