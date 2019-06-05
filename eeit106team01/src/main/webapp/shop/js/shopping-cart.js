//drag and drop event
$("#draggable-events").draggable({
  start: onDragStart,
  drag: onDrag,
  stop: onDragStop
});

var $startCounter = $(".start-event");
var $dragCounter = $(".drag-event");
var $stopCounter = $(".stop-event");

var counts = {
  start: 0,
  drag: 0,
  stop: 0
};

function onDragStart() {
  counts.start++;
  updateCounter($startCounter, counts.start);
}

function onDrag() {
  counts.drag++;
  updateCounter($dragCounter, counts.drag);
}

function onDragStop() {
  counts.stop++;
  updateCounter($stopCounter, counts.stop);
}

function updateCounter(selector, value) {
  $(selector).text(value);
}

//revert
$("#draggable-events").draggable({
  revert: true
});

//Card Accordion
//clicks
$("#card-shopping-cart").click(function() {
  openCardAccordion("shopping-cart");
});

function openCardAccordion(type) {
  if (
    type == "shopping-cart" &&
    $("#accordion-shopping-cart img").attr("src") != undefined
  ) {
    enableSlideToggle(type);
  }
}

//enableSlideToggle
function enableSlideToggle(type) {
  if (type == "shopping-cart") {
    $("#accordion-shopping-cart").slideToggle("slow", "linear");
  }
}

//mouseover event
$("#card-shopping-cart").mouseover(function() {
  $("#card-shopping-cart .card-img-top").attr(
    "class",
    "card-img-top mr-1 mouseover"
  );
});
$("#card-shopping-cart").mouseout(function() {
  $("#card-shopping-cart .card-img-top").attr("class", "card-img-top mr-1");
});

//get all from cart(localStorage)
function allStorage() {
  var values = [],
    keys = Object.keys(localStorage),
    i = keys.length;
  while (i--) {
    values.push(localStorage.getItem(keys[i]));
  }
  return values;
}

//add to cart(localStorage)
function addToCart() {
  var cartLocalStorage = localStorage;
  var cartLocalStorageCount = localStorage.length;

  $(document).ready(function() {
    $("#card-shopping-cart-count").text(cartLocalStorageCount);
    if (cartLocalStorageCount > 0) {
      var jsonArray = [];
      allStorage().forEach(element => {
        var result = JSON.parse(element);
        jsonArray.push(result);
      });

      //clear
      deletePurchase = function(data) {
        $(".deletePurchase").ready(function() {
          cartLocalStorage.removeItem(data.id);
          location.href = location.href;
        });
      };

      var purchaseButton = `<div class="card">
                <a name="" id="purchaseButton" class="btn btn-transparent" href="#" role="button">
                    <span class='fa fa-shopping-cart'> 去結帳</span>
                </a>
            </div>`;

      jsonArray.forEach(element => {
        var text = [];
        var closeIcon = [];
        var img = [];
        $.each(element, function(key, val) {
          if (key.match(/^(name)$/) && val != null) {
            text.push(
              "<span class='small text-justify'>品名: " + val.substr(0, 8) + "</span></br>"
            );
          } else if (key.match(/^(price)$/)) {
            text.push(
              "<span class='small text-justify'>價錢: " + "$" + val + "</span></br>"
            );
          } else if (key.match(/^(quantity)$/)) {
            text.push("<span class='small text-justify'>數量: " + val + "</span>");
          } else if (key.match(/^(id)$/)) {
            text.push(
              "<span class='hidden' id='" + val + "'>" + val + "</span>"
            );
            closeIcon.push(
              "<span class='badge pull-right' onclick='deletePurchase(this)' id='" + val + "'><span class='fa fa-close'></span></span>"
            );
          } else if (key.match(/^(image)$/) && val != null) {
            img.push("<img style='width:80px' src='" + val + "'></br>");
          }
        });
        $(closeIcon.join("")).appendTo("#accordion-shopping-cart img");
        $(img.join("")).appendTo("#accordion-shopping-cart");
        $(text.join("")).appendTo("#accordion-shopping-cart");
      });
      $(purchaseButton).appendTo("#accordion-shopping-cart");
    }
  });

  $("#shoppingCartButton").click(function() {
    var productURL = location.href;
    var productId = parseInt(
      productURL.substr(productURL.indexOf("?") + 1, productURL.length)
    );
    var productName = $("#productName").text();
    var productPrice = parseInt(
      $("#productPrice")
        .text()
        .substr(
          $("#productPrice")
            .text()
            .indexOf("$") + 1,
          $("#productPrice").text().length
        )
    );
    var productQuantity = parseInt($("#quantity option:selected").val());
    var productImage = $("#productLeftInfo img").attr("src");

    //create json object
    var productInfoJson = new Object();
    productInfoJson.id = productId;
    productInfoJson.name = productName;
    productInfoJson.price = productPrice;
    productInfoJson.quantity = productQuantity;
    productInfoJson.image = productImage;
    var productData = JSON.stringify(productInfoJson);

    if (null != cartLocalStorage.getItem(productId)) {
      cartLocalStorage.removeItem(cartLocalStorage.getItem(productId));
      cartLocalStorage.setItem(productId, productData);
    } else {
      cartLocalStorage.setItem(productId, productData);
    }

    var result = cartLocalStorage.getItem(productId);
  });

  //button click reload
  $("#shoppingCartButton").click(function() {
    location.href = location.href;
  });
}
