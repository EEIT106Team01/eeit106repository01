// Image Preview
// 當上傳檔案改變時清除目前預覽圖，並且呼叫previewFiles()
//Insert
$("#upload").change(function() {
  $("#previewDiv").empty(); // 清空當下預覽
  previewFiles(this.files, "insert"); // this即為<input>元素
});
//Update
function updatePreview() {
  $("#updateImage").change(function() {
    $("#image").empty(); // 清空當下預覽
    previewFiles(this.files, "update"); // this即為<input>元素
  });
}
// 使用FileReader讀取檔案，並且回傳Base64編碼後的source
function convertFile(file) {
  return new Promise((resolve, reject) => {
    // 建立FileReader物件
    let reader = new FileReader();
    // 註冊onload事件，取得result則resolve (會是一個Base64字串)
    reader.onload = () => {
      resolve(reader.result);
    };
    // 註冊onerror事件，若發生error則reject
    reader.onerror = () => {
      reject(reader.error);
    };
    // 讀取檔案
    reader.readAsDataURL(file);
  });
}
// 在頁面上新增<img>
//Insert preview
function showPreviewImage(src, fileName) {
  let image = new Image(300); // 設定寬px
  image.id = "preImage";
  image.className = "img-thumbnail";
  image.name = fileName;
  image.src = src; // <img>中src屬性除了接url外也可以直接接Base64字串
  $("#previewDiv").append(image);
}
//Update preview
function showUpdatePreviewImage(src, fileName) {
  let image = new Image(300); // 設定寬px
  image.id = "preUpdateImage";
  image.className = "img-thumbnail";
  image.name = fileName;
  image.src = src; // <img>中src屬性除了接url外也可以直接接Base64字串
  $("#image").append(image);
}
// 預覽圖片，將取得的files一個個取出丟到convertFile()
function previewFiles(files, location) {
  if (files && files.length >= 1) {
    $.map(files, file => {
      convertFile(file)
        .then(data => {
          console.log(data); // 把編碼後的字串輸出到console
          if (location == "insert") {
            showPreviewImage(data, file.name);
          } else if (location == "update") {
            showUpdatePreviewImage(data, file.name);
          }
        })
        .catch(err => console.log(err));
    });
  }
}

// Search
$("#search").click(function() {
  $("#image").empty();
  $("#updateImageButton").empty();
  $("#content thead tr").empty();
  $("#content tbody tr").empty();
  const url = "http://localhost:8080/shop/findReviewById";
  var data = { idType: $("#idType").val(), id: $("#id").val() };
  $.ajax({
    type: "GET",
    url: url,
    data: data,
    success: function(result) {
      var textHead = [];
      var text = [];
      var img = [];
      $.each(result[0], function(key, val) {
        if (key.match(/^(image)$/) && val != null) {
          img.push(
            "<img id='currentImage' src='data:image/jpeg;base64," +
              val +
              "'class='img-thumbnail'>"
          );
        } else if (key.match(/^(createTime|updatedTime)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push("<td>" + new Date(val) + "</td>");
        } else if (key.match(/^(rating)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push(
            "<td><input type='number' id='updateRating' class='form-control' value='" +
              val +
              "'></input></td>"
          );
        } else if (key.match(/^(comment)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push(
            "<td><textarea id='updateComment' class='form-control' rows='2' value='" +
              val +
              "'>" +
              val +
              "</textarea></td>"
          );
        } else if (key.match(/^(id)$/)) {
          textHead.push("<th scope='col' class='" + key + "'>" + key + "</th>");
          text.push("<td scope='row' id='reviewId'>" + val + "</td>");
        } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push("<td>" + val.id + "</td>");
        }
      });
      $(img.join("")).appendTo("#image");
      var updateImageInput =
        "<input type='file' name='' class='form-control-file mt-2 mb-2' id='updateImage' placeholder='' data-target='image' accept='.png,.jpg,.jpeg'>";
      $(updateImageInput).appendTo("#updateImageButton");
      $(textHead.join("")).appendTo("#content thead tr");
      $(text.join("")).appendTo($("#content tbody tr").eq(0));
      var updateButton =
        "<td colspan='8'><button type='button' name='' id='update' class='btn btn-secondary btn-lg btn-block'>Update</button></td>";
      $(updateButton).appendTo($("#content tbody tr").eq(1));
      updatePreview();
      updateReview();
    }
  })
    .done(function() {
      console.log("success");
    })
    .fail(function(result) {
      console.log(result);
      var errorsHead = [];
      var errors = [];
      $.each(result, function(key, val) {
        if (key.match(/^(statusText|responseText)$/)) {
          errorsHead.push("<th class='" + key + "'>" + key + "</th>");
          errors.push("<td>" + val + "</td>");
        } else if (key.match(/^(status)$/)) {
          errorsHead.push(
            "<th scope='col' class='" + key + "'>" + key + "</th>"
          );
          errors.push("<td scope='row'>" + val + "</td>");
        }
      });
      $(errorsHead.join("")).appendTo("#content thead tr");
      $(errors.join("")).appendTo($("#content tbody tr").eq(0));
    });
});

//Insert
$("#insert").click(function() {
  $("#image").empty();
  $("#content thead tr").empty();
  $("#content tbody tr").empty();
  const url = "http://localhost:8080/shop/newReviews";

  //Parse type
  var rating = parseFloat($("#rating").val());
  var memberId = parseInt($("#memberId").val());
  var purchaseListId = parseInt($("#purchaseListId").val());
  var productId = parseInt($("#productId").val());
  //Image
  var image = $("#preImage").attr("src");
  var imgByte = image.substr(image.indexOf(",") + 1, image.length);

  var json = new Object();
  json.rating = rating;
  json.comment = $("#comment").val();
  json.imageBase64 = imgByte;
  json.memberId = { id: memberId };
  json.purchaseListId = { id: purchaseListId };
  json.productId = { id: productId };
  var data = JSON.stringify([json]);
  // console.log(data);

  $.ajax({
    type: "POST",
    url: url,
    data: data,
    contentType: "application/json; charset=utf-8",
    success: function(result) {
      var textHead = [];
      var text = [];
      var img = [];
      $.each(result[0], function(key, val) {
        if (key.match(/^(image)$/) && val != null) {
          img.push(
            "<img src='data:image/jpeg;base64," +
              val +
              "'class='img-thumbnail'>"
          );
        } else if (key.match(/^(createTime|updatedTime)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push("<td>" + new Date(val) + "</td>");
        } else if (key.match(/^(rating|comment)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push("<td>" + val + "</td>");
        } else if (key.match(/^(id)$/)) {
          textHead.push("<th scope='col' class='" + key + "'>" + key + "</th>");
          text.push("<td scope='row'>" + val + "</td>");
        } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
          textHead.push("<th class='" + key + "'>" + key + "</th>");
          text.push("<td scope='row'>" + val.id + "</td>");
        }
      });
      $(img.join("")).appendTo("#image");
      $(textHead.join("")).appendTo("#content thead tr");
      $(text.join("")).appendTo($("#content tbody tr").eq(0));
    }
  })
    .done(function() {
      console.log("success");
    })
    .fail(function(result) {
      console.log(result);
      var errorsHead = [];
      var errors = [];
      $.each(result, function(key, val) {
        if (key.match(/^(statusText|responseText)$/)) {
          errorsHead.push("<th class='" + key + "'>" + key + "</th>");
          errors.push("<td>" + val + "</td>");
        } else if (key.match(/^(status)$/)) {
          errorsHead.push(
            "<th scope='col' class='" + key + "'>" + key + "</th>"
          );
          errors.push("<td scope='row'>" + val + "</td>");
        }
      });
      $(errorsHead.join("")).appendTo("#content thead tr");
      $(errors.join("")).appendTo($("#content tbody tr").eq(0));
    });
});

//Update
function updateReview() {
  $("#update").click(function() {
    const url = "http://localhost:8080/shop/updateReview";

    //Parse type
    var rating = parseFloat($("#updateRating").val());
    var reviewId = parseInt($("#reviewId").text());
    //Image
    var newImageVal = $("#preUpdateImage").attr("src");
    var currentImageVal = $("#currentImage").attr("src");
    if (newImageVal != null) {
      var imgByte = newImageVal.substr(
        newImageVal.indexOf(",") + 1,
        newImageVal.length
      );
    } else if (newImageVal == null && currentImageVal != null) {
      var imgByte = currentImageVal.substr(
        currentImageVal.indexOf(",") + 1,
        currentImageVal.length
      );
    }

    var json = new Object();
    json.id = reviewId;
    json.rating = rating;
    json.comment = $("#updateComment").val();
    if (imgByte != null) {
      json.image = imgByte;
    } else {
      json.image = "null";
    }
    var data = JSON.stringify(json);
    console.log(data);

    $.ajax({
      type: "PUT",
      url: url,
      data: data,
      contentType: "application/json; charset=utf-8",
      success: function(result) {
        var textHead = [];
        var text = [];
        var img = [];
        $.each(result[0], function(key, val) {
          if (key.match(/^(image)$/) && val != null) {
            img.push(
              "<img src='data:image/jpeg;base64," +
                val +
                "'class='img-thumbnail'>"
            );
          } else if (key.match(/^(createTime|updatedTime)$/)) {
            textHead.push("<th class='" + key + "'>" + key + "</th>");
            text.push("<td>" + new Date(val) + "</td>");
          } else if (key.match(/^(rating|comment)$/)) {
            textHead.push("<th class='" + key + "'>" + key + "</th>");
            text.push("<td>" + val + "</td>");
          } else if (key.match(/^(id)$/)) {
            textHead.push(
              "<th scope='col' class='" + key + "'>" + key + "</th>"
            );
            text.push("<td scope='row'>" + val + "</td>");
          } else if (key.match(/^(memberId|purchaseListId|productId)$/)) {
            textHead.push("<th class='" + key + "'>" + key + "</th>");
            text.push("<td scope='row'>" + val.id + "</td>");
          }
        });
        $(img.join("")).appendTo("#image");
        $(textHead.join("")).appendTo("#content thead tr");
        $(text.join("")).appendTo($("#content tbody tr").eq(0));
      }
    })
      .done(function() {
        console.log("success");
      })
      .fail(function(result) {
        console.log(result);
        var errorsHead = [];
        var errors = [];
        $.each(result, function(key, val) {
          if (key.match(/^(statusText|responseText)$/)) {
            errorsHead.push("<th class='" + key + "'>" + key + "</th>");
            errors.push("<td>" + val + "</td>");
          } else if (key.match(/^(status)$/)) {
            errorsHead.push(
              "<th scope='col' class='" + key + "'>" + key + "</th>"
            );
            errors.push("<td scope='row'>" + val + "</td>");
          }
        });
        $(errorsHead.join("")).appendTo("#content thead tr");
        $(errors.join("")).appendTo($("#content tbody tr").eq(0));
      });
  });
}

//drag and drop
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
$("#card-discount").click(function() {
  openCardAccordion("discount");
});
$("#card-recommended").click(function() {
  openCardAccordion("recommended");
});
$("#card-customer-service").click(function() {
  openCardAccordion("customer-service");
});

function openCardAccordion(type) {
  if (
    type == "shopping-cart" &&
    $("#accordion-shopping-cart img").attr("src") != undefined
  ) {
    enableSlideToggle(type);
  } else if (
    type == "discount" &&
    ("" != $("#accordion-discount img").attr("src")) != undefined
  ) {
    enableSlideToggle(type);
  } else if (
    type == "recommended" &&
    ("" != $("#accordion-recommended img").attr("src")) != undefined
  ) {
    enableSlideToggle(type);
  } else if (
    type == "customer-service" &&
    ("" != $("#accordion-customer-service img").attr("src")) != undefined
  ) {
    enableSlideToggle(type);
  }
}

//enableSlideToggle
function enableSlideToggle(type) {
  if (type == "shopping-cart") {
    $("#accordion-shopping-cart").slideToggle("slow", "linear");
  } else if (type == "discount") {
    $("#accordion-discount").slideToggle("slow", "linear");
  } else if (type == "recommended") {
    $("#accordion-recommended").slideToggle("slow", "linear");
  } else if (type == "customer-service") {
    $("#accordion-customer-service").slideToggle("slow", "linear");
  }
}

//image mouse on
//shopping-cart
$("#card-shopping-cart").mouseover(function() {
  $("#card-shopping-cart .card-img-top").attr(
    "class",
    "card-img-top mr-1 mouseover"
  );
});
$("#card-shopping-cart").mouseout(function() {
  $("#card-shopping-cart .card-img-top").attr("class", "card-img-top mr-1");
});
//discount
$("#card-discount").mouseover(function() {
  $("#card-discount .card-img-top").attr(
    "class",
    "card-img-top mr-1 mouseover"
  );
});
$("#card-discount").mouseout(function() {
  $("#card-discount .card-img-top").attr("class", "card-img-top mr-1");
});
//recommended
$("#card-recommended").mouseover(function() {
  $("#card-recommended .card-img-top").attr(
    "class",
    "card-img-top mr-1 mouseover"
  );
});
$("#card-recommended").mouseout(function() {
  $("#card-recommended .card-img-top").attr("class", "card-img-top mr-1");
});
//customer-service
$("#card-customer-service").mouseover(function() {
  $("#card-customer-service .card-img-top").attr(
    "class",
    "card-img-top mr-1 mouseover"
  );
});
$("#card-customer-service").mouseout(function() {
  $("#card-customer-service .card-img-top").attr("class", "card-img-top mr-1");
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

      jsonArray.forEach(element => {
        var text = [];
        var closeIcon = [];
        var img = [];
        var purchaseButton = `<div class="card">
                <a name="" id="purchaseButton" class="btn btn-transparent" href="#" role="button">
                    <span class='fa fa-shopping-cart'> 去結帳</span>
                </a>
            </div>`;
        $.each(element, function(key, val) {
          if (key.match(/^(name)$/) && val != null) {
            text.push(
              "<p class='m-0 p-0''>" +
                "品名" +
                val.substr(0, 8) +
                "..." +
                "</p>"
            );
          } else if (key.match(/^(price)$/)) {
            text.push("<p class='m-0 p-0''>" + "價錢" + "$" + val + "</p>");
          } else if (key.match(/^(quantity)$/)) {
            text.push("<p class='m-0 p-0''>" + "數量" + val + "</p>");
          } else if (key.match(/^(id)$/)) {
            text.push(
              "<p class='d-none m-0 p-0' id='" + val + "'>" + val + "</p>"
            );
            closeIcon.push(
              "<span class='badge align-top mt-1' onclick='deletePurchase(this)' id='" +
                val +
                "'><span class='fa fa-close'></span></span>"
            );
          } else if (key.match(/^(image)$/) && val != null) {
            img.push("<img style='width:100px' src='" + val + "'>");
          }
        });
        $(closeIcon.join("")).appendTo("#accordion-shopping-cart");
        $(img.join("")).appendTo("#accordion-shopping-cart");
        $(text.join("")).appendTo("#accordion-shopping-cart");
        $(purchaseButton).appendTo("#accordion-shopping-cart");
      });
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

  // button click reload
  $("#shoppingCartButton").click(function() {
    location.href = location.href;
  });

  //clear
  // function deletePurchase(item) {
  //     $('.deletePurchase').click(function () {
  //         console.log(item);
  //         alert('s');
  //         // cartLocalStorage.clear;
  //         location.href = location.href;
  //     });
  // }
}

//Review
//
$(function() {
  generateCurrentFilterText();
  console.log(reviewCount);
});

//TAGS
let currentFilter = $(`#currentFilter`).text();
let commentFilterCurrentStatus = $(
  `.comment-filters a[class='current']`
).text();
let reviewCount = $(`#reviewCount`).text();

//Generate Current Filter Text
function generateCurrentFilterText() {
  $(`#currentFilter`).prepend(commentFilterCurrentStatus);
}

//Generate Reviews
function generateReviews(productJson, memberJson) {}
// A Review
/*
<li>
    <div class="comment-checkbox">
		<div class="checkbox checkbox-replace">
			<input type="checkbox">
		</div>
	</div>
		<div class="comment-details">
		    <div class="comment-head">
				<a href="#">Ksenia Sawicka</a> commented on 
                <a href="#">The Women of the Flames</a>
			</div>
				<p class="comment-text">Servants contempt as although addition dashwood is procured. 
                Interest in yourself an do of numerous feelings cheerful confined.
				</p>
			<div class="comment-footer">
				<div class="comment-time">Today - 21:05</div>
            <div class="action-links">
                <a href="#" class="approve"><i class="entypo-check"></i>Approve</a>
                <a href="#" class="delete"><i class="entypo-cancel"></i>Delete</a>
                <a href="javascript:;" onclick="jQuery('#modal-edit-comment').modal('show');" class="edit"><i class="entypo-pencil"></i>Edit</a>
            </div>
         </div>
    </div>
</li>
*/
