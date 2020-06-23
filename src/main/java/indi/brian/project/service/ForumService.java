package indi.brian.project.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import indi.brian.project.dao.BoardDao;
import indi.brian.project.dao.Page;
import indi.brian.project.dao.PostDao;
import indi.brian.project.dao.TopicDao;
import indi.brian.project.dao.UserDao;
import indi.brian.project.domain.Board;
import indi.brian.project.domain.MainPost;
import indi.brian.project.domain.Post;
import indi.brian.project.domain.Topic;
import indi.brian.project.domain.User;

@Service
public class ForumService {
	private TopicDao topicDao;
	private UserDao userDao;
	private BoardDao boardDao;
	private PostDao postDao;

	@Autowired
	public void setTopicDao(TopicDao topicDao) {
		this.topicDao = topicDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setBoardDao(BoardDao boardDao) {
		this.boardDao = boardDao;
	}

	@Autowired
	public void setPostDao(PostDao postDao) {
		this.postDao = postDao;
	}
	
	/**
	  *  新增資料 (測試用)
	 * @param workbook
	 * @return
	 */
	public void savePostData() throws IOException {
		String path = "C:\\Users\\user\\Desktop\\test\\Post.xlsx";
		String format = path.substring(path.lastIndexOf("."));
		InputStream is = new FileInputStream(path);
		Workbook workbook = null;
		if (format.equals(".xls")) {
			workbook = new HSSFWorkbook(is);
		} else if (format.equals(".xlsx")){
			workbook = new XSSFWorkbook(is);
		}
		
		List<Map<String,String>> valueMaps = getPostValueMaps(workbook); 
		for (Map<String, String> valueMap: valueMaps) {
			Post post = new Post();
			post.setBoardId(Integer.parseInt(valueMap.get("boardId").toString()));
			Topic topic = new Topic();
			topic.setTopicId(Integer.parseInt(valueMap.get("topicId").toString()));
			post.setTopic(topic);
			User user = new User();
			user.setUserId(10);
			post.setUser(user);
			post.setPostTitle(valueMap.get("postTitle").toString());
			post.setPostText(valueMap.get("postText").toString());
			post.setCreateTime(new Date());
			postDao.save(post);
		}
	}
	
	private List<Map<String, String>> getPostValueMaps(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
		
		int firstRowIndex = sheet.getFirstRowNum();
		int lastRowIndex = sheet.getLastRowNum();
		
		List<Map<String, String>> valueMaps = new ArrayList<Map<String, String>>();
		for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
			Row row = sheet.getRow(rIndex);
			int firstCellIndex = row.getFirstCellNum();
			int lastCellIndex = row.getLastCellNum();
			Map<String, String> valueMap = new ConcurrentHashMap<String, String>();
			for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
				Cell cell = row.getCell(cIndex);
				cell.setCellType(CellType.STRING);
				if (cIndex == 0) {
					valueMap.put("boardId", cell.toString());
				} else if (cIndex == 1) {
					valueMap.put("topicId", cell.toString());
				}else if  (cIndex == 2) {
					valueMap.put("postTitle", cell.toString());
				} else if  (cIndex == 3) {
					valueMap.put("postText", cell.toString());
				}
			}
			valueMaps.add(valueMap);
		}
		return valueMaps;
	}
	
	/**
	  *  新增資料 (測試用)
	 * @param workbook
	 * @return
	 */
	public void saveTopicData() throws IOException {
		String path = "C:\\Users\\user\\Desktop\\test\\Topic.xlsx";
		String format = path.substring(path.lastIndexOf("."));
		InputStream is = new FileInputStream(path);
		Workbook workbook = null;
		if (format.equals(".xls")) {
			workbook = new HSSFWorkbook(is);
		} else if (format.equals(".xlsx")){
			workbook = new XSSFWorkbook(is);
		}
		
		List<Map<String,String>> valueMaps = getTopicValueMaps(workbook); 
		for (Map<String, String> valueMap: valueMaps) {
			Topic topic = new Topic();
			topic.setBoardId(Integer.parseInt(valueMap.get("boardId").toString()));
			topic.setTopicTitle(valueMap.get("topicTitle").toString());
			User user = new User();
			user.setUserId(10);
			topic.setUser(user);
			topic.setCreateTime(new Date());
			topic.setLastPost(new Date());
			topic.setViews(Integer.parseInt(valueMap.get("topicViews").toString()));
			topic.setReplies(Integer.parseInt(valueMap.get("topicReplies").toString()));
			topicDao.save(topic);
		}
	}
	
	/**
	 * excel內容放進map (測試用)
	 * @param workbook
	 * @return
	 */
	private List<Map<String, String>> getTopicValueMaps(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
		
		int firstRowIndex = sheet.getFirstRowNum();
		int lastRowIndex = sheet.getLastRowNum();
		
		List<Map<String, String>> valueMaps = new ArrayList<Map<String, String>>();
		for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
			Row row = sheet.getRow(rIndex);
			int firstCellIndex = row.getFirstCellNum();
			int lastCellIndex = row.getLastCellNum();
			Map<String, String> valueMap = new ConcurrentHashMap<String, String>();
			for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
				Cell cell = row.getCell(cIndex);
				cell.setCellType(CellType.STRING);
				if (cIndex == 0) {
					valueMap.put("boardId", cell.toString());
				} else if (cIndex == 1) {
					valueMap.put("topicTitle", cell.toString());
				}else if  (cIndex == 2) {
					valueMap.put("topicViews", cell.toString());
				} else if  (cIndex == 3) {
					valueMap.put("topicReplies", cell.toString());
				}
			}
			valueMaps.add(valueMap);
		}
		return valueMaps;
	}
	
	/**
	  *  新增資料 (測試用)
	 * @param workbook
	 * @return
	 */
	public void saveData() throws IOException {
		String path = "C:\\Users\\user\\Desktop\\test\\Board.xlsx";
		String format = path.substring(path.lastIndexOf("."));
		InputStream is = new FileInputStream(path);
		Workbook workbook = null;
		if (format.equals(".xls")) {
			workbook = new HSSFWorkbook(is);
		} else if (format.equals(".xlsx")){
			workbook = new XSSFWorkbook(is);
		}
		
		List<Map<String,String>> valueMaps = getValueMaps(workbook); 
		for (Map<String, String> valueMap: valueMaps) {
			Board board = new Board();
			board.setBoardName(valueMap.get("boardName").toString());
			board.setBoardDesc(valueMap.get("boardDesc").toString());
			board.setTopicNum(Integer.parseInt(valueMap.get("topicId").toString()));
			boardDao.save(board);
		}
	}
	
	/**
	 * excel內容放進map (測試用)
	 * @param workbook
	 * @return
	 */
	private List<Map<String, String>> getValueMaps(Workbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
		
		int firstRowIndex = sheet.getFirstRowNum();
		int lastRowIndex = sheet.getLastRowNum();
		
		List<Map<String, String>> valueMaps = new ArrayList<Map<String, String>>();
		for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
			Row row = sheet.getRow(rIndex);
			int firstCellIndex = row.getFirstCellNum();
			int lastCellIndex = row.getLastCellNum();
			Map<String, String> valueMap = new ConcurrentHashMap<String, String>();
			for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {
				Cell cell = row.getCell(cIndex);
				cell.setCellType(CellType.STRING);
				if (cIndex == 0) {
					valueMap.put("boardId", cell.toString());
				} else if (cIndex == 1) {
					valueMap.put("boardDesc", cell.toString());
				} else if (cIndex == 2) {
					valueMap.put("topicId", cell.toString());
				}
			}
			valueMaps.add(valueMap);
		}
		return valueMaps;
	}

	public void addTopic(Topic topic) {
		topicDao.save(topic);

		topic.getMainPost().setTopic(topic);
		topic.getMainPost().setUser(topic.getUser());
		topic.getMainPost().setCreateTime(new Date());
		topic.getMainPost().setPostTitle(topic.getTopicTitle());
		topic.getMainPost().setBoardId(topic.getBoardId());
		postDao.save(topic.getMainPost());

		User user = topic.getUser();
		user.setCredit(user.getCredit() + 10);
		userDao.update(user);
	}

	public void removeTopic(int topicId) {
		Topic topic = topicDao.get(topicId);
		
		Board board = boardDao.get(topic.getBoardId());
		board.setTopicNum(board.getTopicNum() - 1);

		User user = topic.getUser();
		user.setCredit(user.getCredit() - 50);

		topicDao.remove(topic);
		postDao.deleteTopicPosts(topicId);
	}

	public void addPost(Post post) {
		postDao.save(post);

		User user = post.getUser();
		user.setCredit(user.getCredit() + 5);
		userDao.update(user);
	}

	public void removePost(int postId) {
		Post post = postDao.get(postId);
		postDao.remove(post);
	}

	public void addBoard(Board board) {
		boardDao.save(board);
	}

	public void removeBoard(int boardId) {
		Board board = boardDao.get(boardId);
		boardDao.remove(board);
	}

	public Page getPagedTopics(int boardId, int pageNo, int pageSize) {
		return topicDao.getPagedTopics(boardId, pageNo, pageSize);
	}

	public List<Board> getAllBoards() {
		return boardDao.loadAll();
	}

	public Page getPagedPosts(int topicId, int pageNo, int pageSize) {
		return postDao.getPagedPosts(topicId, pageNo, pageSize);
	}

	public Page queryTopicByTitle(String title, int pageNo, int pageSize) {
		return topicDao.queryTopicByTitle(title, pageNo, pageSize);
	}

	public Board getBoardById(int boardId) {
		return boardDao.get(boardId);
	}

	public Topic getTopicByTopicId(int topicId) {
		return topicDao.get(topicId);
	}

	public Post getPostByPostId(int postId) {
		return postDao.get(postId);
	}

	public void addBoardManager(int boardId, String userName) {
		User user = userDao.getUserByUserName(userName);
		if (user == null) {
			throw new RuntimeException("用戶名為" + userName + "的用戶不存在。");
		} else {
			Board board = boardDao.get(boardId);
			user.getManBoards().add(board);
			userDao.update(user);
		}
	}

	public void updateTopic(Topic topic) {
		topicDao.update(topic);
	}

	public void updatePost(Post post) {
		postDao.update(post);
	}
	

	public void makeDigestTopic(int topicId){
		Topic topic = topicDao.get(topicId);
		topic.setDigest(Topic.DIGEST_TOPIC);
		User user = topic.getUser();
		user.setCredit(user.getCredit() + 100);
		topicDao.update(topic);
		userDao.update(user);
	}

}
