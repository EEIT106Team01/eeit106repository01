package net.ddns.eeitdemo.eeit106team01.forum.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentBean;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleContentCurrentService;
import net.ddns.eeitdemo.eeit106team01.forum.model.ArticleTopicCurrentService;
import net.ddns.eeitdemo.eeit106team01.forum.model.MemberTempBean;

@RestController
public class ArticleContentController {

	@Autowired
	private ArticleContentCurrentService articleContentCurrentService;
	
	@Autowired
	private ArticleTopicCurrentService articleTopicCurrentService;

	@GetMapping(path = { "/articleTopics/{id}/articleContents" }, produces = { "application/json" })
	public ResponseEntity<?> getContentList(@PathVariable Integer id, @RequestParam Integer begin,
			@RequestParam Integer end, @RequestParam String orderType // orderByTime, orderByLike
	) {
		if ((id != null) && (id.intValue() > 0)) {
			if (((begin != null && begin.intValue() > 0) && (end != null && end.intValue() > 0))) {
				if (orderType.equalsIgnoreCase("orderByTime")) {
					return ResponseEntity.ok(articleContentCurrentService.findByTime(id, begin, end));
				} else if (orderType.equalsIgnoreCase("orderByLike")) {
					return ResponseEntity.ok(articleContentCurrentService.findByLike(id, begin, end));
				}
			}
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping(path = { "/articleTopics/{id}/articleContents" }, consumes = { "application/json" }, produces = {
			"application/json" })
	public ResponseEntity<?> postContent(@PathVariable Integer id,
			@RequestBody ArticleContentCurrentBean articleContentCurrentBean, BindingResult bindingResult, HttpSession httpSession) {
		if ((id != null && id.intValue() != 0) && (articleContentCurrentBean != null)) {
			if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
				Map<String, String> errors = new HashMap<String, String>();
				List<ObjectError> bindingErrors = bindingResult.getAllErrors();
				for (ObjectError bindingError : bindingErrors) {
					errors.put(bindingError.getObjectName(), bindingError.toString());
				}
				return ResponseEntity.badRequest().body(errors);
			}
//			從session抓出MemberBean資訊
			MemberTempBean memberBean = (MemberTempBean) httpSession.getAttribute("MemberBean");
			
			if ((articleContentCurrentBean.getMemberBean() == null)
					|| (articleContentCurrentBean.getMemberBean().getId() == null)
//					檢查回文者ID與session中ID是否一致
					|| (articleContentCurrentBean.getMemberBean().getId() != memberBean.getId())
					) {
				return ResponseEntity.noContent().build();
			}

			if ((articleContentCurrentBean.getContentContent() != null)
					&& (articleContentCurrentBean.getContentContent().length() != 0)
					&& (articleContentCurrentBean.getArticleTopicCurrent() != null)
					&& (id == articleContentCurrentBean.getArticleTopicCurrent().getId())) {
				java.util.Date now = new java.util.Date();
				articleContentCurrentBean.setContentLikeNum(0);
				articleContentCurrentBean.setContentReplyNum(0);
				articleContentCurrentBean.setContentCreateTime(now);
				articleContentCurrentBean.setContentUpdateTime(now);
				articleContentCurrentBean.setContentStatus("normal");
				articleContentCurrentBean.setUpdateMessage(null);
//				將MemberBean與新回文關聯
				articleContentCurrentBean.setMemberBean(memberBean);
				ArticleContentCurrentBean result = articleContentCurrentService.insert(articleContentCurrentBean);
				if (result != null) {
					if (result.getReply() == null) {
						articleTopicCurrentService.increaseContentReplyNum(result.getArticleTopicCurrent().getId());
					}
					return ResponseEntity.created(URI.create("articleTopics/" + id + "/articleContents/" + result.getId()))
							.body(result);
				}
			}
		}
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(path = { "/articleTopics/{id}/articleContents/{contentId}" }, consumes = { "application/json; charset=UTF-8" }, produces = {
	"application/json" })
	public ResponseEntity<?> updateContent(
			@PathVariable Integer id,
			@PathVariable Integer contentId,
			@RequestBody ArticleContentCurrentBean articleContentCurrentBean,
			BindingResult bindingResult,
			HttpSession httpSession
			){
		if (
				(id != null && id.intValue() != 0) && 
				(contentId != null && contentId.intValue() != 0) &&
				(articleContentCurrentBean != null)
				) {
			if ((bindingResult != null) && (bindingResult.hasFieldErrors())) {
				Map<String, String> errors = new HashMap<String, String>();
				List<ObjectError> bindingErrors = bindingResult.getAllErrors();
				for (ObjectError bindingError : bindingErrors) {
					errors.put(bindingError.getObjectName(), bindingError.toString());
				}
				return ResponseEntity.noContent().header("errorMsg", errors.toString()).build();
			}
//			從httpSession抓出MemberBean資訊
			MemberTempBean memberBean = (MemberTempBean) httpSession.getAttribute("MemberBean");
			if ((articleContentCurrentBean.getMemberBean() == null)
					|| (articleContentCurrentBean.getMemberBean().getId() == null)
//					檢查修文者ID與session中ID是否一致
					|| (articleContentCurrentBean.getMemberBean().getId() != memberBean.getId())
					) {
				return ResponseEntity.notFound().build();
			}
			
			if (
					(id == articleContentCurrentBean.getArticleTopicCurrent().getId()) &&
					(contentId == articleContentCurrentBean.getId())					
					) {
//				將MemberBean與修文關聯
				articleContentCurrentBean.setMemberBean(memberBean);
				ArticleContentCurrentBean result = articleContentCurrentService.update(articleContentCurrentBean);				
				if (result != null) {
					return ResponseEntity.ok(result);
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(path = { "/articleTopics/{id}/articleContents/{contentId}/{likeOrDislike}" }, produces = { "application/json" })
	public ResponseEntity<?> ContentWhoLike(
			@PathVariable Integer id,
			@PathVariable Integer contentId,
			@PathVariable String likeOrDislike,
			HttpSession httpSession
			) {
//		從httpSession抓出MemberBean資訊
		MemberTempBean memberBean = (MemberTempBean) httpSession.getAttribute("MemberBean");
		Integer memberId = memberBean.getId();
		Map<Integer, String> result = articleContentCurrentService.contentWhoLike(contentId, memberId, likeOrDislike.toLowerCase());
		if (result != null) {
			return ResponseEntity.ok(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(path = { "/articleTopics/{id}/articleContents/{contentId}" }, produces = { "application/json" })
	public ResponseEntity<?> deleteContent(
			@PathVariable Integer id,
			@PathVariable Integer contentId
			){
		if (
				(id != null && id.intValue() != 0) &&
				(contentId != null && contentId.intValue() != 0)
				) {
			ArticleContentCurrentBean result = articleContentCurrentService.findByPrimaryKey(contentId);
			if (result != null && !result.getContentStatus().equalsIgnoreCase("deleted")) {
				result.setContentStatus("deleted");
				result.setContentContent("{\"ops\":[{\"attributes\":{\"italic\":true},\"insert\":\"此文章已被刪除\"},{\"insert\":\"\\n\"}]}");
				
				if ((result = articleContentCurrentService.update(result)) != null) {
					return ResponseEntity.ok(result);
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
}
