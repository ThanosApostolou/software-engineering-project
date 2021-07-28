var GetShopIDCallback = function (data) {
    $('#inputName').val(data.name);
    $('#inputAddress').val(data.address);
    $('#inputLng').val(data.lng);
    $('#inputLat').val(data.lat);
    $('#inputTags').val(data.tags.join());
    $('#selectStatus').val("" + data.withdrawn); // turn bool to string

    var InitMapCallback = function () {
        // The location of Uluru
        var uluru = {lat: data.lat, lng: data.lng};
        // The map, centered at Uluru
        var map = new google.maps.Map(
            document.getElementById('map'), {zoom: 12, center: uluru});
        // The marker, positioned at Uluru
        var marker = new google.maps.Marker({position: uluru, map: map});
    }

    $.ajax({
        url: 'https://maps.googleapis.com/maps/api/js?key=AIzaSyBZKBLZIzolWTUufN7clP4tlH2z_sQJSF8&callback=initMap&language=el&region=GR', 
        type: 'GET',
        dataType: 'jsonp',
        crossDomain: true,
        success: InitMapCallback
    });
};

var PutShopCallback = function (data) {
    alert("Το κατάστημα ενημερώθηκε!");
    location.reload();
};

var DeleteShopCallback = function (data) {
    alert("Το κατάστημα διαγράφηκε!");
    location.href = "/browse_shops";
};

            
$(document).ready(function() {
    var id = [[${id}]];
    
    // RestGet (prefix, query, callback)
    RestGet ("/shops/" + id, "", GetShopIDCallback);
    
    $("#ShowShopForm").submit(function(e) {
        e.preventDefault();
        
        var inputName = $('#inputName').val();
        var inputAddress = $('#inputAddress').val();
        var inputLng = $('#inputLng').val();
        var inputLat = $('#inputLat').val();
        var inputTags = $('#inputTags').val();
        inputTags = inputTags.split(",");
        inputTags = JSON.stringify(inputTags);
        var selectStatus = $('#selectStatus').val();

        var query = {
            name: inputName,
            address: inputAddress,
            lng: inputLng,
            lat: inputLat,
            tags: JSON.parse(inputTags),
            withdrawn: selectStatus
        };

        RestPut ("/shops/" + [[${id}]], query, PutShopCallback)
        
    });

    $("#DeleteButton").click(function(e) {
        console.log("debug", e);
        RestDelete ("/shops/" + [[${id}]], DeleteShopCallback);
    });
});
