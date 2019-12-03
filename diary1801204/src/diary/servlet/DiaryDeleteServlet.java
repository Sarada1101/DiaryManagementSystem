package diary.servlet;

import diary.bean.DiaryBeans;
import diary.bean.StudentBeans;
import diary.commmon.StudentErrorCheck;
import diary.dao.StudentDiaryDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 指定された日誌情報をデータベースから削除するServletクラス
 *
 * @author ryouta
 */
@WebServlet("/diarydelete")
public class DiaryDeleteServlet extends HttpServlet {

    /**
     * 日誌情報をデータベースから削除する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //  TEST   /////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("DiaryDeleteServlet");
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        //ログイン済みかチェックする
        HttpSession session = request.getSession();
        StudentBeans student_beans = (StudentBeans) session.getAttribute("login-info");

        StudentErrorCheck error_check = new StudentErrorCheck();
        boolean is_login = error_check.checkLogin(student_beans);

        if (is_login) {
            DiaryBeans diary_beans = (DiaryBeans) session.getAttribute("diary-beans");

            StudentDiaryDao diary_dao = new StudentDiaryDao();
            diary_dao.deleteDiaryFromDb(diary_beans);

            session.removeAttribute("diary-beans");
            response.sendRedirect("diarydeletecomplete");
        } else {
            response.sendRedirect("studenterror");
        }
    }
}
