function createTopicView(dataTopicList, divTopicId) {
    $(`#${divTopicId}`).text("");
    let divCardDeck;
    for (let i = 0; i < dataTopicList.length; i++) {
        if(( i + 1 ) % 3 == 1){
            divCardDeck = $("<div></div>").addClass("card-deck").width("900px").appendTo($(`#${divTopicId}`));
        }
        let divCard = $("<div></div>").addClass("card").attr("id", `id${dataTopicList[i].id}`).appendTo(divCardDeck);
            if(dataTopicList[i].videoBean){
                let videoBean = dataTopicList[i].videoBean;
                let videoId = `topic${dataTopicList[i].id}player`;
                let videoBlock = createVideo(videoBean, videoId).appendTo(divCard);
                createVideoPlayer(videoBean, videoId);
            }
            let divCardBody = $("<div></div>").addClass("card-body").appendTo(divCard);
                let h4TopicHeader = $("<h4></h4>").addClass("card-title").text(dataTopicList[i].topicHeader).appendTo(divCardBody);
                let pTopicContent = $("<p></p>").addClass("card-text").text(parseQuillContent(JSON.parse(dataTopicList[i].topicContent))).appendTo(divCardBody);
                    let aLinkToContent = $("<a></a>").addClass("card-link").attr("href", "http://localhost:8080/forum/showContents.html")
                        .on("click", function(){
                            localStorage.setItem("topicBean", JSON.stringify(dataTopicList[i]));
                        }).text("<詳細內容>").appendTo(pTopicContent);
                let pMemberName = $("<p></p>").addClass("card-text").text("Fadachai").appendTo(divCardBody);
                let spanPageViews = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].pageViews).appendTo(divCardBody);
                let spanTopicLikeNum = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].topicLikeNum).appendTo(divCardBody);
                let spanContentReplyNum = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].contentReplyNum).appendTo(divCardBody);
                let spanAccidentTime = $("<span></span>").addClass("badge badge-warning").text(dataTopicList[i].accidentTime+"hours+8").appendTo(divCardBody);
    }
}

function createVideo(videoBean, videoId) {
    // console.log(videoBean);
    // console.log(videoId);

    //createVideoTag
    let videoURI = "/storage/videos/" + videoBean.videoURI;
    let thumbnailURI = "/storage/others/no-image.png";
    if (videoBean.thumbnailURI != null && videoBean.thumbnailURI.length != 0) {
        thumbnailURI = "/storage/jpgs/" + videoBean.thumbnailURI;
    }

    let videoCard = $("<div></div>").addClass("card").css({
                        margin: "0",
                        width: "100%"
                    });
        let videoTop = $("<div></div>").css({
                            margin: "0"
                        }).appendTo(videoCard);
            let video = $("<video></video>").addClass("card-img-top").attr("id", videoId).attr("poster", thumbnailURI).attr("src", videoURI).prop("controls", true).appendTo(videoTop);
        // let videoBottom = $("<div></div>").addClass("card-body").text(videoBean.videoURI.substr(0, 13)).appendTo(videoCard);
    
    return videoCard;
}

function createVideoPlayer(videoBean, videoId) {
    let thumbnailURI = "/storage/others/no-image.png";
    let gifURI = "/storage/others/no-image.png";
    if (videoBean.thumbnailURI != null && videoBean.thumbnailURI.length != 0) {
        thumbnailURI = "/storage/jpgs/" + videoBean.thumbnailURI;
    }
    if (videoBean.gifURI != null && videoBean.gifURI.length != 0) {
        gifURI = "/storage/gifs/" + videoBean.gifURI;
    }
    const player = new Plyr('#' + videoId);
    // console.log($(`#${videoId}`).next());
    // console.log($(document.getElementById(videoId).nextElementSibling));
    $(`#${videoId}`).next().hover(function () {
        $(this).css("background-image", "url('" + gifURI + "')");
    }, function () {
        $(this).css("background-image", "url('" + thumbnailURI + "')");
    });
}

function parseQuillContent(quillTopicContent) {
    let ops = quillTopicContent.ops;
    let shortTopicContent = "";
    let lengthLimit = 35;
    for (let i = 0; i < ops.length; i++){
        if (typeof ops[i].insert === "string") {
            shortTopicContent = shortTopicContent + ops[i].insert;
        }
    }
    if (shortTopicContent.length > lengthLimit){
        shortTopicContent = shortTopicContent.substr(0,lengthLimit) + "......";
    }
    return shortTopicContent;
}