package kr.ac.kopo.kopo12.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BoardItem {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private int id;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private String date;
	@Column 
	private Long parentId;
	
	//private List<BoardItem> comments;
	@ManyToOne(optional=false)
	@JoinColumn(name="board_id")
	private Board board;
	
	
	public BoardItem() {
		
	}
	
	public BoardItem(int id) {
		this.id = id;
	}
	
	public BoardItem(String content, Long parentId, String date) {
		this.content = content;
		this.parentId = parentId;
		this.date = date;
	}
	
	public BoardItem(String title, String content, String date) {
		this.title = title;
		this.content = content;
		this.date = date;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
		
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
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

	@Override
	public String toString() {
		return "BoardItem [id=" + id + ", title=" + title + ", content=" + content + ", date=" + date + ", parentId="
				+ parentId + ", board=" + board + "]";
	}
	
}
