package indi.brian.project.dao;

import java.util.Iterator;

import org.springframework.stereotype.Repository;

import indi.brian.project.domain.Board;
import indi.brian.project.domain.LoginLog;

@Repository("boardDao")
public class BoardDao extends BaseDao<Board> {
	private static final String GET_BOARD_NUM = "select count(b.boardId) from Board b";

	public long getBoardNum() {
		Iterator iter = getHibernateTemplate().iterate(GET_BOARD_NUM);
        return ((Long)iter.next());
	}
	
	public void save(Board board) {
		this.getHibernateTemplate().save(board);
	}
}
