$(function() {
    findPurchaseByMemberId(1);
});

//Generate Purchase By Member Id
function findPurchaseByMemberId(id) {
    let memberId = parseInt(id);
    $.ajax({
        type: "GET",
        url: `/shop/findPurchaseById?idType=member&id=` + memberId,
        success: function(response) {
            console.log(response);
            response.forEach(element => {
                let time = getLocaleTime(element.createTime);
                let daysAgo = getLocaleTime(GetDateStr(-1));
                console.log(element);
                let id;
                if (time > daysAgo) {
                    // console.log(`new`);
                    id = element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">New Purchase</span>`
                } else {
                    // console.log(`not new`);
                    id = element.id;
                }
                let totalPrice = element.productTotalPrice + element.deliverPrice;
                let refundStatus = `-`;

                $(`.datatable tbody`).append(
                    `<tr>
                        <td>` + refundStatus + `</td>
                        <td>` + id + ` </td>
                        <td>` + time + `</td>
                        <td>` + totalPrice + `</td>
                        <td>
                            <a href="#" class="btn btn-info btn-lg btn-icon icon-left">
                                <i class="entypo-info"></i> 訂單明細
                            </a>
                            <a href="#" class="btn btn-danger btn-lg btn-icon icon-left">
                                <i class="entypo-pencil"></i> 退貨申請
                            </a>
                        </td>
                    </tr>`
                );
            });
        }
    });
}

/* <div class="checkbox checkbox-replace">
    <input type="checkbox" id="chk-1">
</div>*/