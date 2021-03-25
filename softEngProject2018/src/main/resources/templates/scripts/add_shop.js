var AddShopCallback = function (data) {
    alert("Το κατάστημα προστέθηκε!");
};

$(document).ready(function() {
    
    $("#AddShopForm").submit(function(e) {
        e.preventDefault();
        var inputName = $("input#inputName").val();
        var inputAddress = $("input#inputAddress").val();
        var inputLng = $("input#inputLng").val();
        var inputLat = $("input#inputLat").val();
        var inputTags = $("input#inputTags").val();
        inputTags = inputTags.split(",");
        inputTags = JSON.stringify(inputTags);
        
        var query = {
            name: inputName,
            address: inputAddress,
            lng: inputLng,
            lat: inputLat,
            tags: JSON.parse(inputTags),
            withdrawn: false
        };

        RestPost ("/shops", query, AddShopCallback);
    });

});
