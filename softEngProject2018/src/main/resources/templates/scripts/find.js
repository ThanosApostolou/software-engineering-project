var GetPricesCallback = function (data) {

    var outputContent = ' \
        <table class="table"> \
        <thead> \
            <tr> \
                <th scope="col">Τιμή</th> \
                <th scope="col">Ημερομηνία</th> \
                <th scope="col">Προϊόν</th> \
                <th scope="col">Ετικέτες Προϊόντος</th> \
                <th scope="col">Κατάστημα</th> \
                <th scope="col">Ετικέτες Καταστήματος</th> \
            </tr> \
        </thead> \
        <tbody> \
    ';
        
    for (var p of data.prices) {
        producttagsstring = p.productTags.join();
        shoptagsstring = p.shopTags.join();
        outputContent += ' \
            <tr> \
                <th scope="col">' + p.price + '</th> \
                <th scope="col">' + p.date + '</th> \
                <th scope="col"><a class="nav-link" href="/show_product?id=' + p.productId + '">'+ p.productName +'</a>' + '</th> \
                <th scope="col">' + producttagsstring + '</th> \
                <th scope="col"><a class="nav-link" href="/show_shop?id=' + p.shopId + '">'+ p.shopName +'</a>' + '</th> \
                <th scope="col">' + shoptagsstring + '</th> \
            </tr> \
        ';
    }

    outputContent += '</tbody></table>'

    var query = "&count="+[[${count}]]+"&sort="+[[${sort}]];
    
    if ([[${geoDist}]] != null) {
        query += "&geoDist="+[[${geoDist}]];
    }
    if ([[${geoLng}]] != null) {
        query += "&geoLng="+[[${geoLng}]];
    }
    if ([[${geoLat}]] != null) {
        query += "&geoLat="+[[${geoLat}]];
    }
    if ([[${dateFrom}]] != null) {
        query += "&dateFrom="+[[${dateFrom}]];
    }
    if ([[${dateTo}]] != null) {
        query += "&dateTo="+[[${dateTo}]];
    }
    if ([[${shops}]] != null) {
        query += "&shops="+[[${shops}]];
    }
    if ([[${products}]] != null) {
        query += "&products="+[[${products}]];
    }
    if ([[${tags}]] != null) {
        query += "&tags="+[[${tags}]];
    }

    outputContent += ' \
        <div> \
            <ul class="pagination"> \
                <li class="page-item"><a class="nav-link disabled">Συνολικά Αποτελέσματα: '+ data.total + '</a></li> \
    ';  
    if ([[${start}]] == 0) {
        outputContent += '<li class="page-item disabled"> ';
    } else {
        outputContent += '<li class="page-item"> ';
    }
    outputContent += '<a class="page-link" href="/find?start='
                             + ([[${start}]] - 1) + query + '">Προηγούμενη</a></li>';

    for (i=1; i<=Math.ceil(data.total/data.count); i++) {
        if (i-1 == [[${start}]]) {
            outputContent += '<li class="page-item active">';
        } else {
            outputContent += '<li class="page-item">';
        }
        outputContent += '<a class="page-link" href="/find?start='
                             + (i-1) + query + '">' + i + '</a></li>';
    }

    if ([[${start}]] >= Math.ceil(data.total/data.count)-1) {
        outputContent += '<li class="page-item disabled">';
    } else {
        outputContent += '<li class="page-item">';
    }
    outputContent += ' <a class="page-link" href="/find?start='
                             + ([[${start}]] + 1) + query + '">Επόμενη</a></li> ';
    
    outputContent += ' </ul> \
        </div> \
    ';

    $('#outputDiv').html(outputContent);

}

$(document).ready(function() {
    var start = [[${start}]];
    var count = [[${count}]];
    var geoDist = [[${geoDist}]];
    var geoLng = [[${geoLng}]];
    var geoLat = [[${geoLat}]];
    var dateFrom = [[${dateFrom}]];
    var dateTo = [[${dateTo}]];
    var shops = [[${shops}]];
    var products = [[${products}]];
    var tags = [[${tags}]];
    var sort = [[${sort}]];

    var query = "start="+start+"&count="+count+"&sort="+sort;
    
    if (geoDist != null) {
        query += "&geoDist="+geoDist;
    }
    if (geoLng != null) {
        query += "&geoLng="+geoLng;
    }
    if (geoLat != null) {
        query += "&geoLat="+geoLat;
    }
    if (dateFrom != null) {
        query += "&dateFrom="+dateFrom;
    }
    if (dateTo != null) {
        query += "&dateTo="+dateTo;
    }
    if (shops != null) {
        query += "&shops="+shops;
    }
    if (products != null) {
        query += "&products="+products;
    }
    if (tags != null) {
        query += "&tags="+tags;
    }

    RestGet ("/prices", query, GetPricesCallback);

    $("#FindForm").submit(function(e) {
        e.preventDefault();
        var inputCount = $("input#inputCount").val();
        var inputGeoDist = $("input#inputGeoDist").val();
        var inputGeoLng = $("input#inputGeoLng").val();
        var inputGeoLat = $("input#inputGeoLat").val();
        var inputDateFrom = $("input#inputDateFrom").val();
        var inputDateTo = $("input#inputDateTo").val();
        var inputShops = $("input#inputShops").val();
        var inputProducts = $("input#inputProducts").val();
        var inputTags = $("input#inputTags").val();
        var selectSort = $("select#selectSort").val();


        var query = "count="+inputCount+"&sort="+selectSort;
        
        if (inputGeoDist != "") {
            query += "&geoDist="+inputGeoDist;
        }
        if (inputGeoLng != "") {
            query += "&geoLng="+inputGeoLng;
        }
        if (inputGeoLat != "") {
            query += "&geoLat="+inputGeoLat;
        }
        if (inputDateFrom != "") {
            query += "&dateFrom="+inputDateFrom;
        }
        if (inputDateTo != "") {
            query += "&dateTo="+inputDateTo;
        }
        if (inputShops != "") {
            query += "&shops="+inputShops;
        }
        if (inputProducts != "") {
            query += "&products="+inputProducts;
        }
        if (inputTags != "") {
            query += "&tags="+inputTags;
        }

        location.href = "/find?" + query;
            
    });

});

