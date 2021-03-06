// function autocomplete(inp, arr) {
function autocomplete(inp) {
    // disable the submit when init
    $(':input[type="submit"]').prop('disabled', true);
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function (e) {
        var a, b, val = this.value;
        /*close any already open lists of autocompleted values*/
        closeAllLists();
        if (!val) { return false; }
        currentFocus = -1;
        /*create a DIV element that will contain the items (values):*/
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        /*append the DIV element as a child of the autocomplete container:*/
        this.parentNode.appendChild(a);
        // get String Array from ajax
        $.ajax({
            url: `/autocomplete/${val}`,
            type: "GET",
            success: function (data, textStatus, jqXHR) {
                if (data.length > 0) {
                    let arr = [];
                    $.each(data, function (index, value) {
                        arr.push(value);
                    });
                    /*for each item in the array...*/
                    for (let i = 0; i < arr.length; i++) {
                        let boldCount = 1;
                        /*check if the item starts with the same letters as the text field value:*/
                        for (let j = 0; j < arr[i].length; j++) {
                            if (j + val.length <= arr[i].length) {
                                if (boldCount > 0) {
                                    if (arr[i].substr(j, val.length).toUpperCase() == val.toUpperCase()) {
                                        boldCount--;
                                        /*create a DIV element for each matching element:*/
                                        b = document.createElement("DIV");
                                        /*make the matching letters bold:*/
                                        b.innerHTML = arr[i].substr(0, j);
                                        b.innerHTML += "<strong>" + arr[i].substr(j, val.length) + "</strong>";
                                        b.innerHTML += arr[i].substr(j + val.length);
                                        /*insert a input field that will hold the current array item's value:*/
                                        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                                        /*execute a function when someone clicks on the item value (DIV element):*/
                                        b.addEventListener("click", function (e) {
                                            /*insert the value for the autocomplete text field:*/
                                            inp.value = this.getElementsByTagName("input")[0].value;
                                            /*close the list of autocompleted values,
                                            (or any other open lists of autocompleted values:*/
                                            closeAllLists();
                                            $.ajax({
                                                url: "/queryarticleTopics/" + $("#myInput").val(),
                                                type: "GET",
                                                success: function (data, textStatus, jqXHR) {
                                                    if (data.length == 1) {
                                                        // localStorage.setItem("topicBean", JSON.stringify(data[0]));
                                                        location.href = "/forum/showContents.html?topic=" + data[0].id;
                                                    } else {
                                                        console.log("incorrect too many results");
                                                    }
                                                }
                                            });
                                        });
                                        a.appendChild(b);
                                        // b = <div>so<strong>lo</strong>mon<input type='hidden' value='solomon'></div>
                                        // a = 
                                        // <div id='myInputautocomplete-list' class='autocomplete-items'>
                                        //     b
                                        //     b
                                        // </div>
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    });
    /*execute a function presses a key on the keyboard:*/
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode == 40) {
            /*If the arrow DOWN key is pressed,
            increase the currentFocus variable:*/
            currentFocus++;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 38) { //up
            /*If the arrow UP key is pressed,
            decrease the currentFocus variable:*/
            currentFocus--;
            /*and and make the current item more visible:*/
            addActive(x);
        } else if (e.keyCode == 13) {
            /*If the ENTER key is pressed, prevent the form from being submitted,*/
            e.preventDefault();
            if (currentFocus > -1) {
                /*and simulate a click on the "active" item:*/
                if (x) x[currentFocus].click();
            }
        }
    });
    //unlock or lock the submit button
    inp.addEventListener("keyup", function () {
        if ($("#myInput").val() != '') {
            $(':input[type="submit"]').prop('disabled', false);
        } else {
            $(':input[type="submit"]').prop('disabled', true);
        }
    })
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}