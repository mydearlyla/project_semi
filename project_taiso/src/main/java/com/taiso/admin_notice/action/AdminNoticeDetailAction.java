package com.taiso.admin_notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.taiso.admin_notice.db.BoardDTO;
import com.taiso.admin_notice.db.noticeDAO;

public class AdminNoticeDetailAction implements Notice {

	@Override
	public NoticeForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : AdminNoticeDetailAction_execute() 호출");
		
		// 1) 파일 업로드
		// 업로드 가상폴더 생성 /upload
		String realPath = request.getRealPath("./upload");
		System.out.println(" M : realPath : " + realPath);
		// 첨부파일 크기 지정 / 10MB
		int maxSize = 10 * 1024 * 1024;
		
		// 파일업로드 -> 파일업로드 객체 생성(MultipartRequest)
		MultipartRequest multi 
							= new MultipartRequest(
									request,
									realPath,
									maxSize,
									"UTF-8",
									new DefaultFileRenamePolicy()
									);
		
		// 전달정보(파라메터) 저장
		int bo_num = Integer.parseInt(multi.getParameter("bo_num"));
		String pageNum = multi.getParameter("pageNum");
		
				
		noticeDAO dao = new noticeDAO();
		// 글 조회수 1증가 -> DAO 1증가 메서드 호출
		dao.updateReadcount(bo_num);
		System.out.println(" M : 조회수 1증가 완료! ");
		
		// 글 가져오기
		BoardDTO boDTO = dao.getNoticeDetail(bo_num);
		
		// request 영역에 글정보를 저장
		request.setAttribute("boDTO", boDTO);
		request.setAttribute("pageNum", pageNum);
		
		// 페이지 이동(준비)
		NoticeForward forward = new NoticeForward();
		forward.setPath("./admin_notice/adminNoticeDetail.jsp");
		forward.setRedirect(false);
		
		return forward;


	}

}
