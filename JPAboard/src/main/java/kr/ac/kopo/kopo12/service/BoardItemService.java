package kr.ac.kopo.kopo12.service;

import java.util.List;

import kr.ac.kopo.kopo12.domain.BoardItem;
public interface BoardItemService {
	int[] number(List<BoardItem> boardItemList, int from);
	String date();
}
