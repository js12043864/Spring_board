package kr.ac.kopo.kopo12.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	
	@Column
	private String title;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="board")		//mappedBy=board
	//cascade -> 게시판 지워지면 해당하는 게시글 삭제됨 //
	//lazy
	//eager = 즉시로딩
	// 지연로딩	lazy로 하는게 보통 - transactional을 보통 붙임 transactional붙으면 db 안다침
	//
	private List<BoardItem> boardItems;	
	
	public Board() {
		
	}
	
	public List<BoardItem> getBoardItem() {
		return boardItems;
	}

	public void setBoardItem(List<BoardItem> boardItem) {
		this.boardItems = boardItem;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
