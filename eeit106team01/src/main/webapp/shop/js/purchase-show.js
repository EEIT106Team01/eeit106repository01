$(function() {
    findPurchaseByMemberId(1);
    // showAjaxModal();
});

//Generate Purchase By Member Id
function findPurchaseByMemberId(id) {
    let memberId = parseInt(id);
    $.ajax({
        type: "GET",
        url: `/shop/findPurchaseById?idType=member&id=` + memberId,
        success: function(response) {
            response.forEach(element => {
                let time = getLocaleTime(element.createTime);
                let daysAgo = getLocaleTime(GetDateStr(-1));
                let id;
                if (time > daysAgo) {
                    id = element.id + `&nbsp;&nbsp;&nbsp;<span class="label label-sm label-success">New Purchase</span>`
                } else {
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
                            <a href="javascript:;" onclick="jQuery('#modal-7').modal('show', {backdrop: 'static'});" class="btn btn-info btn-lg btn-icon icon-left">
                                <i class="entypo-info"></i> 訂單明細
                            </a>
                            <a href="#" class="btn btn-danger btn-lg btn-icon icon-left">
                                <i class="entypo-help-circled"></i> 退貨申請
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


// function showAjaxModal() {
//     jQuery('#modal-7').modal('show', { backdrop: 'static' });

//     jQuery.ajax({
//         url: "data/ajax-content.txt",
//         success: function(response) {
//             jQuery('#modal-7 .modal-body').html(response);
//         }
//     });
// }