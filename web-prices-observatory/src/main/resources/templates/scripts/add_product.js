var AddProductCallback = function (data) {
    alert("Το προϊόν προστέθηκε!");
};

$(document).ready(function() {
    
    $("#AddProductForm").submit(function(e) {
        e.preventDefault();
        var inputName = $("input#inputName").val();
        var inputDescription = $("input#inputDescription").val();
        var inputTags = $("input#inputTags").val();
        inputTags = inputTags.split(",");
        inputTags = JSON.stringify(inputTags);
        var inputCategory = $("input#inputCategory").val();
        
        var query = {
            name: inputName,
            description: inputDescription,
            tags: JSON.parse(inputTags),
            withdrawn: false,
            category: inputCategory
        };

        RestPost ("/products", query, AddProductCallback);
    });

});
