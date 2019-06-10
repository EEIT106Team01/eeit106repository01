function createTopicView(dataTopicList, divTopicId) {
    $(`#${divTopicId}`).text("");
    for (let i = 0; i < dataTopicList.length; i++) {
        let divTopic = $("<div></div>").addClass("col-md-3 myTopicBlock").attr("id", `id${dataTopicList[i].id}`).css({padding: "8px", width: "259px"}).appendTo($(`#${divTopicId}`));
        if(divTopicId.substr(0, 12) == "requestTopic"){
            divTopic.css({height: "128px", border: "1px solid #CCCCCC", "border-radius": "15px"});
        }else if(divTopicId.substr(0, 10) == "shareTopic"){
            divTopic.css({height: "280px"});
        }
            if(dataTopicList[i].videoBean){
                let divVideo = $("<div></div>").addClass("col-md-12").css({"height": "150px",margin: "1px"}).appendTo(divTopic);
                let videoBean = dataTopicList[i].videoBean;
                let videoId = `topic${dataTopicList[i].id}player`;
                let videoBlock = createVideo(videoBean, videoId).appendTo(divVideo);
                createVideoPlayer(videoBean, videoId);
            
                // $("<img />", { 
                //     src: "/navbar/images/1558837130883Indian Parrot Greet Indian Parrot.mp4.jpg",
                //     height: "100%",
                //     width: "100%"
                // }).appendTo(divVideo);
            }
            let aLinkToContent = $("<a></a>").attr("href", "http://localhost:8080/forum/showContents.html?topic=" + dataTopicList[i].id).appendTo(divTopic);
                let divTopicHeader = $("<div></div>").addClass("col-md-12").css({margin: "1px","text-decoration": "underline","font-weight": "bolder"}).text(parseTopicHeader(dataTopicList[i].topicHeader)).appendTo(aLinkToContent);
                let divTopicContent = $("<div></div>").addClass("col-md-12").css({"font-size": "14px",margin: "1px"}).text(parseQuillContent(JSON.parse(dataTopicList[i].topicContent))).appendTo(aLinkToContent);
            let divMemberName = $("<div></div>").addClass("col-md-12").css({"font-size": "14px",margin: "1px",color: "#0066CC",minHeight: "34px"}).appendTo(divTopic);
                let divMemberImage = $("<div></div>").addClass("col-md-2").css({height: "34px"}).appendTo(divMemberName);
                $("<img />", { 
                    // src: "/navbar/images/notLogin.jpg",
                    src: `${dataTopicList[i].memberBean.image}`,
                    class: "img-circle myTopicImg",
                    width: "34px"
                }).appendTo(divMemberImage);
                let divMemberNameName = $("<div></div>").addClass("col-md-10").css({"padding": "7px"}).appendTo(divMemberName);
                divMemberNameName.append(`${dataTopicList[i].memberBean.name}`);
            let divTopicLikeNum = $("<div></div>").addClass("col-md-2").css({"font-size": "14px",margin: "1px",width: "45px"}).appendTo(divTopic);
                $("<i></i>").addClass("fa fa-thumbs-o-up").appendTo(divTopicLikeNum);
                divTopicLikeNum.append(`&nbsp;${dataTopicList[i].topicLikeNum}`);
            let divContentReplyNum = $("<div></div>").addClass("col-md-2").css({"font-size": "14px",margin: "1px",width: "45px"}).appendTo(divTopic);
                $("<i></i>").addClass("fa fa-commenting-o").appendTo(divContentReplyNum);
                divContentReplyNum.append(`&nbsp;${dataTopicList[i].contentReplyNum}`);
            let divPageViews = $("<div></div>").addClass("col-md-2").css({"font-size": "14px",margin: "1px",width: "45px"}).appendTo(divTopic);
                $("<i></i>").addClass("fa fa-eye").appendTo(divPageViews);
                divPageViews.append(`&nbsp;${dataTopicList[i].pageViews}`);
            let divAccidentTime = $("<div></div>").addClass("col-md-6").css({"font-size": "14px",margin: "1px",float: "right", "text-align": "right", width: "95px"}).appendTo(divTopic);
                $("<i></i>").addClass("fa fa-video-camera").appendTo(divAccidentTime);
                divAccidentTime.append(`&nbsp;${new Date(dataTopicList[i].accidentTime).yyyymmddhhii().substr(0,10)}`);
    }
                // <div class="col-md-3" style="padding: 8px">
        //     <div class="col-md-12" style="height: 150px; margin: 1px;  ">
        //         <img height="100%" width="100%" src="navbar/images/1558837130883Indian Parrot Greet Indian Parrot.mp4.jpg">
        //     </div>
        //     <a href="index.html" id="linkToContent">
        //         <div class="col-md-12" id="topicHeaderDiv" style="margin: 1px; text-decoration: underline; font-weight: bolder;">我是好一個很長的標題嗎</div>
        //         <div class="col-md-12" style="font-size: 14px; margin: 1px;  ">主要目標是發大財主要目標是發大財主要目標是發大財主要目標是發大財......</div>
        //     </a>
        //     <div class="col-md-12" id="memberNameDiv" style="font-size: 14px; margin: 1px; color: #0066CC;">
        //         <img src="/navbar/images/wowlogo.png" alt="" class="img-circle" width="34" />&nbsp;John Henderson</div>
        //     <div class="col-md-2" style="font-size: 14px; margin: 1px; width: 45px;">
        //         <i class="fa fa-thumbs-o-up"></i>&nbsp;1</div>
        //     <div class="col-md-2" style="font-size: 14px; margin: 1px; width: 45px;">
        //         <i class="fa fa-commenting-o"></i>&nbsp;322</div>
        //     <div class="col-md-2" style="font-size: 14px; margin: 1px; width: 45px;">
        //         <i class="fa fa-eye"></i>&nbsp;225</div>
        //     <div class="col-md-6" style="float: right; text-align: right; font-size: 14px; margin: 1px; width: 95px;">
        //         <i class="fa fa-video-camera"></i>&nbsp;2019-05-06</div>
        // </div>


        // if(( i + 1 ) % 3 == 1){
        //     divCardDeck = $("<div></div>").addClass("card-deck").width("900px").appendTo($(`#${divTopicId}`));
        // }
        // let divCard = $("<div></div>").addClass("card").attr("id", `id${dataTopicList[i].id}`).appendTo(divCardDeck);
        //     if(dataTopicList[i].videoBean){
        //         let videoBean = dataTopicList[i].videoBean;
        //         let videoId = `topic${dataTopicList[i].id}player`;
        //         let videoBlock = createVideo(videoBean, videoId).appendTo(divCard);
        //         createVideoPlayer(videoBean, videoId);
        //     }
        //     let divCardBody = $("<div></div>").addClass("card-body").appendTo(divCard);
        //         let h4TopicHeader = $("<h4></h4>").addClass("card-title").text(dataTopicList[i].topicHeader).appendTo(divCardBody);
        //         let pTopicContent = $("<p></p>").addClass("card-text").text(parseQuillContent(JSON.parse(dataTopicList[i].topicContent))).appendTo(divCardBody);
        //             let aLinkToContent = $("<a></a>").addClass("card-link").attr("href", "http://localhost:8080/forum/showContents.html")
        //                 .on("click", function(){
        //                     localStorage.setItem("topicBean", JSON.stringify(dataTopicList[i]));
        //                 }).text("<詳細內容>").appendTo(pTopicContent);
        //         let pMemberName = $("<p></p>").addClass("card-text").text("Fadachai").appendTo(divCardBody);
        //         let spanPageViews = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].pageViews).appendTo(divCardBody);
        //         let spanTopicLikeNum = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].topicLikeNum).appendTo(divCardBody);
        //         let spanContentReplyNum = $("<span></span>").addClass("badge badge-dark").text(dataTopicList[i].contentReplyNum).appendTo(divCardBody);
        //         let spanAccidentTime = $("<span></span>").addClass("badge badge-warning").text(dataTopicList[i].accidentTime+"hours+8").appendTo(divCardBody);
    
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

    // let videoCard = $("<div></div>").css({
    //                     margin: "0",
    //                     width: "100%"
    //                 });
        // let videoTop = $("<div></div>").css({
        //                     margin: "0"
        //                 }).appendTo(videoCard);
            let video = $("<video></video>").attr("id", videoId).attr("poster", thumbnailURI).attr("src", videoURI).prop("controls", true);
        // let videoBottom = $("<div></div>").addClass("card-body").text(videoBean.videoURI.substr(0, 13)).appendTo(videoCard);
    
    // return videoCard;
    return video;
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

function parseTopicHeader(headerString){
    let lengthLimit = 10;
    if(headerString.length > lengthLimit){
        headerString = headerString.substr(0,lengthLimit) + "......";
    }
    return headerString;
}

function parseQuillContent(quillTopicContent) {
    let ops = quillTopicContent.ops;
    let shortTopicContent = "";
    let lengthLimit = 15;
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