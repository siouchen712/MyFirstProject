package indi.brian.project.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import indi.brian.project.cons.CommonConstant;
import indi.brian.project.dao.Page;
import indi.brian.project.domain.Board;
import indi.brian.project.domain.Post;
import indi.brian.project.domain.Topic;
import indi.brian.project.domain.User;
import indi.brian.project.service.ForumService;

@Controller
public class BoardManageController extends BaseController {
	private ForumService forumService;

	@Autowired
	public void setForumService(ForumService forumService) {
		this.forumService = forumService;
	}

	/**
	 * 列出所有標題
	 * @param boardId
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/board/listBoardTopics-{boardId}", method = RequestMethod.GET)
	public ModelAndView listBoardTopics(@PathVariable Integer boardId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) throws IOException {
		
		ModelAndView view = new ModelAndView();
		Board board = forumService.getBoardById(boardId);
		pageNo = pageNo == null ? 1 : pageNo;
		Page pagedTopic = forumService.getPagedTopics(boardId, pageNo, CommonConstant.PAGE_SIZE);
		view.addObject("board", board);
		view.addObject("pagedTopic", pagedTopic);
		view.setViewName("/listBoardTopics");
		return view;
	}
	
	/**
	 * 
	 * @param request
	 * @param topic
	 * @return
	 */
	@RequestMapping(value = "/board/addTopic", method = RequestMethod.POST)
	public String addTopic(HttpServletRequest request,Topic topic) {
		User user = getSessionUser(request);
		topic.setUser(user);
		Date now = new Date();
		topic.setCreateTime(now);
		topic.setLastPost(now);
		forumService.addTopic(topic);
		String targetUrl = "/board/listBoardTopics-" + topic.getBoardId()
				+ ".html";
		return "redirect:"+targetUrl;
	}
	
	@RequestMapping(value = "/board/addPost")
	public String addPost(HttpServletRequest request, Post post) {
		post.setCreateTime(new Date());
		post.setUser(getSessionUser(request));
		forumService.addPost(post);
		String targetUrl = "/board/listTopicPosts-"
				+ post.getTopic().getTopicId() + ".html";
		return "redirect:"+targetUrl;
	}
	
	/**
	 * 
	 * 
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/board/addTopicPage-{boardId}", method = RequestMethod.GET)
	public ModelAndView addTopicPage(@PathVariable Integer boardId) {
		ModelAndView view =new ModelAndView();
		view.addObject("boardId", boardId);
		view.setViewName("/addTopic");
		return view;
	}
	
	/**
	 * 列出所有帖子
	 * @param topicId
	 * @param pageNo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/board/listTopicPosts-{topicId}", method = RequestMethod.GET)
	public ModelAndView listTopicPosts(@PathVariable Integer topicId,
			@RequestParam(value = "pageNo", required = false) Integer pageNo) throws IOException {
		ModelAndView view = new ModelAndView();
		Topic topic = forumService.getTopicByTopicId(topicId);
		pageNo = pageNo == null ? 1 : pageNo;
		Page pagedPost = forumService.getPagedPosts(topicId, pageNo, CommonConstant.PAGE_SIZE);
		view.addObject("topic", topic);
		view.addObject("pagedPost", pagedPost);
		view.setViewName("/listTopicPosts");
		return view;
	}
	
	@RequestMapping(value = "/board/removeBoard", method = RequestMethod.GET)
	public String removeBoard(@RequestParam("boardIds") String boardIds) {
		String[] arrIds = boardIds.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			forumService.removeBoard(new Integer(arrIds[i]));
		}
		String targetUrl = "/index.html";
		return "redirect:"+targetUrl;
	}

	@RequestMapping(value = "/board/removeTopic", method = RequestMethod.GET)
	public String removeTopic(@RequestParam("topicIds") String topicIds,@RequestParam("boardId") String boardId) {
		String[] arrIds = topicIds.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			forumService.removeTopic(new Integer(arrIds[i]));
		}
		String targetUrl = "/board/listBoardTopics-" + boardId + ".html";
		return "redirect:"+targetUrl;
	}


	@RequestMapping(value = "/board/makeDigestTopic", method = RequestMethod.GET)
	public String makeDigestTopic(@RequestParam("topicIds") String topicIds,@RequestParam("boardId") String boardId) {
		String[] arrIds = topicIds.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			forumService.makeDigestTopic(new Integer(arrIds[i]));
		}
		String targetUrl = "/board/listBoardTopics-" + boardId + ".html";
		return "redirect:"+targetUrl;
	}
}
