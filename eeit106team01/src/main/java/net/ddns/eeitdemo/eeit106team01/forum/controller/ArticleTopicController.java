package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.net.URI;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentService;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;

@RestController
public class ArticleTopicController {

	@Autowired
	private ServletContext application;

	@Autowired
	private ArticleTopicCurrentService articleTopicCurrentService;
	
	@GetMapping(path = { "/articleTopics" }, produces = { "application/json" })
	public ResponseEntity<?> getTopicList(
			@RequestParam(required = false) Integer begin,
			@RequestParam(required = false) Integer end,
			@RequestParam(required = false) String topicType,	//requestTopic shareTopic
			@RequestParam(required = false) String orderType,	//orderByTime orderByLike
			@RequestParam(required = false) String likeTopicHeader,
			@RequestParam(required = false) Double lowerLatitude,
			@RequestParam(required = false) Double upperLatitude,
			@RequestParam(required = false) Double lowerLongitude,
			@RequestParam(required = false) Double upperLongitude
			) {
		System.out.println("getTopicList method running");
		System.out.println("begin: " + begin);
		System.out.println("end: " + end);
		System.out.println("topicType: " + topicType);
		System.out.println("orderType: " + orderType);
		System.out.println("likeTopicHeader: " + likeTopicHeader);
		System.out.println("lowerLatitude: " + lowerLatitude);
		System.out.println("upperLatitude: " + upperLatitude);
		System.out.println("lowerLongitude: " + lowerLongitude);
		System.out.println("upperLongitude: " + upperLongitude);
		
		//orderColumn:  orderByLike: topicLikeNum    orderByTime: topicUpdateTime
		
		List<ArticleTopicCurrentBean> findRange = null;
		if((begin != null) && (end != null) && (("shareTopic".equals(topicType)) || ("requestTopic".equals(topicType))) &&
			(lowerLatitude == null) && (upperLatitude == null) && 
			(lowerLongitude == null) && (upperLongitude == null)) {
			if("orderByTime".equals(orderType)) {
				if(("".equals(likeTopicHeader)) || (likeTopicHeader == null)) {					
					findRange = articleTopicCurrentService.findByLastRange(begin, end, topicType, "topicUpdateTime", null);
				}else {
					findRange = articleTopicCurrentService.findByLastRange(begin, end, topicType, "topicUpdateTime", likeTopicHeader);
				}
			}else if("orderByLike".equals(orderType)) {
				if(("".equals(likeTopicHeader)) || (likeTopicHeader == null)) {
					findRange = articleTopicCurrentService.findByLastRange(begin, end, topicType, "topicLikeNum", null);					
				}else {
					findRange = articleTopicCurrentService.findByLastRange(begin, end, topicType, "topicLikeNum", likeTopicHeader);
				}
			}
		} else if ((begin == null) && (end == null) && 
				(("".equals(orderType)) || (orderType == null)) &&
				(("".equals(likeTopicHeader)) || (likeTopicHeader == null)) &&
				(lowerLatitude != null) && (upperLatitude != null) && 
				(lowerLongitude != null) && (upperLongitude != null)){
			findRange = articleTopicCurrentService.findByCoordinateRange(lowerLatitude, upperLatitude, lowerLongitude, upperLongitude);
		}
		
		if (findRange != null) {
//			System.err.println(findRange.get(0).toString());
			return ResponseEntity.ok(findRange);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping(path = { "/articleTopics/{id}" }, produces = { "application/json" })
	public ResponseEntity<?> getTopic(@PathVariable(name = "id") int id) {
		System.out.println("getTopic method running");
		ArticleTopicCurrentBean findOne = articleTopicCurrentService.findByPrimaryKey(id);
		if (findOne != null) {
			return ResponseEntity.ok(findOne);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(path = { "/queryarticleTopics/{likeTopicHeader}" }, produces = { "application/json" })
	public ResponseEntity<?> getTopicByTopicHeader(@PathVariable String likeTopicHeader) {
		System.out.println("getTopicByTopicHeader method running");
		List<ArticleTopicCurrentBean> findResult = articleTopicCurrentService.findLikeTopicHeader(likeTopicHeader);
		if (findResult != null) {
			return ResponseEntity.ok(findResult);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping(path = { "/autocomplete/{inputString}" }, produces = { "application/json" })
	public ResponseEntity<?> searchAutocompleteTopicHeader(@PathVariable String inputString) {
		System.out.println("searchAutocompleteTopicHeader method running");
		List<String> findResult = articleTopicCurrentService.findAutocompleteByTopicHeader(inputString);
		if (findResult != null && findResult.size() != 0) {
			return ResponseEntity.ok(findResult);
		} else {
			return ResponseEntity.ok().build();
		}
	}
	
	@GetMapping(path = { "/countTopics" })
	public ResponseEntity<?> countTopics(
			@RequestParam(required = false) String topicType,	//requestTopic shareTopic
			@RequestParam(required = false) String likeTopicHeader
			) {
		System.out.println("countTopics method running");
		System.out.println("topicType: " + topicType);
		System.out.println("likeTopicHeader: " + likeTopicHeader);
		Long countResult = null;
		if(("shareTopic".equals(topicType)) || ("requestTopic".equals(topicType))) {
			if(("".equals(likeTopicHeader)) || (likeTopicHeader == null)) {
				countResult = articleTopicCurrentService.findTopicNum(topicType, null);
			}else {				
				countResult = articleTopicCurrentService.findTopicNum(topicType, likeTopicHeader);
			}
		}
		System.err.println("countResult: " + countResult);
		if (countResult != null) {				
			return ResponseEntity.ok(countResult);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(path = { "/articleTopics" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> postTopic(@RequestBody ArticleTopicCurrentBean requestbody, BindingResult bindingResult, HttpSession httpSession) {
		System.out.println("postTopic method running");
		System.out.println(requestbody.toString());
		Map<String, String> errors = new HashMap<String, String>();
		
		if(bindingResult != null) {
			if(bindingResult.hasFieldErrors()) {
				System.out.println("postTopic binding error");
				List<ObjectError> bindingErrors = bindingResult.getAllErrors();
				for (ObjectError bindingError : bindingErrors) {
					errors.put(bindingError.getObjectName(), bindingError.toString());
				}
				return ResponseEntity.badRequest().body(errors);
			}
		}
		
		MemberTempBean memberBean = (MemberTempBean) httpSession.getAttribute("MemberBean");
		if((requestbody.getMemberBean() == null)
				|| requestbody.getMemberBean().getId() == null
				|| requestbody.getMemberBean().getId().intValue() != memberBean.getId().intValue()) {
//			errors.put("loginError", "請先登入");
			System.err.println("請先登入");
		}
		
		if(requestbody.getTopicHeader() != null) {
			if(requestbody.getTopicHeader().length() == 0) {
				errors.put("topicHeaderError", "請輸入標題");
			}
		}
		
		if("shareTopic".equals(requestbody.getTopicType())) {
//			if(requestbody.getVideoBean() == null) {
//				errors.put("videoBeanError", "分享文請上傳影片");
//			}
		} else if("requestTopic".equals(requestbody.getTopicType())) {
//			if(requestbody.getVideoBean() != null) {
//				errors.put("videoBeanError", "協尋文請誤上傳影片");
//			}
		} else {
			errors.put("topicTypeError", "請輸入主題類型");
		}
		

		if(!("northernRegion".equals(requestbody.getTopicRegion())
				|| "centralRegion".equals(requestbody.getTopicRegion())
				|| "southernRegion".equals(requestbody.getTopicRegion())
				|| "easternRegion".equals(requestbody.getTopicRegion()))) {
			errors.put("topicRegionError", "請輸入主題地區分類");
		}
		
		if(requestbody.getAccidentTime() != null) {
			Calendar inputCalendar = Calendar.getInstance();
		    Calendar nowCalendar = Calendar.getInstance();
		    inputCalendar.setTime(requestbody.getAccidentTime());
		    nowCalendar.setTime(new java.util.Date());
		    int n = 0;  
		    while (!inputCalendar.after(nowCalendar)) { // 循環對比，直到相等，n就是想要的結果
		    	n++;
	            inputCalendar.add(Calendar.MONTH, 1);   // 比較月份，月份+1
	        }
		    System.out.println("相差的月份: " + n);
		    if (n > 24) {
		    	errors.put("accidentTimeError", "請輸入兩年內的時間");
		    	return ResponseEntity.badRequest().body(errors);
		    }
		}
			
		if(requestbody.getAccidentLocation() != null) {
			if(requestbody.getAccidentLocation().length() == 0) {
				errors.put("accidentLocationError", "請輸入事故地點說明");
			}
		}
		
		if((requestbody.getAccidentLocationLatitude() == null)
				|| (requestbody.getAccidentLocationLongitude() == null)) {
			errors.put("accidentLocationCoordinateError", "請在地圖中選取事故地點");
		}
		
		if(requestbody.getTopicContent() != null) {
			if(requestbody.getTopicContent().length() == 0) {
				errors.put("topicContentError", "請輸入文章內容");
			}
		}
		
		if(!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}
		
		java.util.Date nowDate = new java.util.Date();
		requestbody.setTopicLikeNum(0);
		requestbody.setContentReplyNum(0);
		requestbody.setTopicCreateTime(nowDate);
		requestbody.setTopicUpdateTime(nowDate);
		requestbody.setTopicStatus("normal");
		requestbody.setTopicContentUpdateTime(nowDate);
		requestbody.setPageViews(0);
		requestbody.setUpdateMessage("使用者發文");
		
		ArticleTopicCurrentBean insertResult = articleTopicCurrentService.insert(requestbody);
		if (insertResult != null) {
			return ResponseEntity
					.created(URI.create(application.getContextPath() + "/articleTopics/" + insertResult.getId()))
					.body(insertResult);
		} else {
			return ResponseEntity.noContent().build();
		}
	}

	@PutMapping(path = { "/articleTopics/{id}" }, consumes = { "application/json" }, produces = { "application/json" })
	public ResponseEntity<?> updateTopic(@PathVariable(name = "id") int id,
			@RequestBody ArticleTopicCurrentBean requestbody) {
		System.out.println("updateTopic method running");
		if (id == requestbody.getId().intValue()) {
			requestbody.setTopicUpdateTime(new java.util.Date());
			requestbody.setUpdateMessage("使用者修改");
			ArticleTopicCurrentBean updateResult = articleTopicCurrentService.updateIgnoreNullColumn(requestbody);
			if (updateResult != null) {
				return ResponseEntity.ok(updateResult);
			} else {
				return ResponseEntity.notFound().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping(path = { "/articleTopics/{id}" })
	public ResponseEntity<?> deleteTopic(@PathVariable(name = "id") int id) {
		System.out.println("deleteTopic method running");
//		boolean deleteResult = articleTopicCurrentService.delete(id);
		ArticleTopicCurrentBean findOne = articleTopicCurrentService.findByPrimaryKey(id);
		ArticleTopicCurrentBean deleteResult = null;
		if(findOne != null && !"deleted".equals(findOne.getTopicStatus()) ) {
			findOne.setTopicStatus("deleted");
			deleteResult = articleTopicCurrentService.updateIgnoreNullColumn(findOne);
		}
		
		if (deleteResult != null) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
