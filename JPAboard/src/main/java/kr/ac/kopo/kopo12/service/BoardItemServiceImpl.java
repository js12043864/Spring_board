package kr.ac.kopo.kopo12.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.ac.kopo.kopo12.domain.BoardItem;


@Service
public class BoardItemServiceImpl implements BoardItemService {

	@Override
	public int[] number(List<BoardItem> boardItemList, int from) {
		// TODO Auto-generated method stub
		int totalCnt = boardItemList.size();	//게시글 총 수
		int finPage = totalCnt % 10 == 0 ? totalCnt / 10 : (totalCnt / 10) + 1;	//마지막 페이지
		if( from < 1) {
			from = 1;
		}else if (from > finPage) {
			from = finPage;
		}	//예외처리
		int startNum = from * 10 <= totalCnt ? (from * 10) - 1 : totalCnt - 1;	//시작 번호
		int endNum = (from - 1) * 10;
		int cur_page = 0;
		if( from % 10 == 0) {
			cur_page = from / 10;
		}else {
			cur_page = from / 10 + 1;
		}
		
		int prPage = (cur_page / 10) * 10 + 1;
		if(cur_page % 10 == 0 ) {
			prPage = ((cur_page / 10) - 1) * 10 + 1;
		}
		int[] number = {startNum,endNum,prPage, finPage, from};
		return number;
	}

	@Override
	public String date() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(cal.getTime());
		return date;
	}

}
