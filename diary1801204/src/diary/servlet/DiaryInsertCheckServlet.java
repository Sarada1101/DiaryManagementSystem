package diary.servlet;

import diary.bean.DiaryBeans;
import diary.bean.StudentBeans;
import diary.commmon.StudentErrorCheck;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 日誌登録画面で入力した値を取得した後、日誌登録確認画面へ遷移するServletクラス
 *
 * @author ryouta
 */
@WebServlet("/diaryinsertcheck")
public class DiaryInsertCheckServlet extends HttpServlet {

    /**
     * 日誌登録画面で入力した値、本日の日付、ログインしている学生の学籍番号を取得しBeansに格納した後、日誌登録確認画面へ遷移する
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //  TEST   /////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("DiaryInsertCheckServlet");
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        ///ログイン済みかチェックする
        HttpSession session = request.getSession();
        StudentBeans student_beans = (StudentBeans) session.getAttribute("login-info");

        StudentErrorCheck error_check = new StudentErrorCheck();
        boolean is_login = error_check.checkLogin(student_beans);

        if (is_login) {
            String good_point = request.getParameter("good-point");
            String bad_point = request.getParameter("bad-point");
            String student_comment = request.getParameter("student-comment");

            String today = (String) session.getAttribute("today");

            String class_code = ((StudentBeans) session.getAttribute("login-info")).getClass_code();
            String student_id = ((StudentBeans) session.getAttribute("login-info")).getStudent_id();

            DiaryBeans diary_beans = new DiaryBeans();
            diary_beans.setClass_code(class_code);
            diary_beans.setInsert_date(today);
            diary_beans.setStudent_id(student_id);
            diary_beans.setGood_point(good_point);
            diary_beans.setBad_point(bad_point);
            diary_beans.setStudent_comment(student_comment);

            session.setAttribute("diary-beans", diary_beans);
            request.getRequestDispatcher("WEB-INF/jsp/diaryInsertCheck.jsp").forward(request, response);
        } else {
            response.sendRedirect("studenterror");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //  TEST   /////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("DiaryInsertCheckServlet");
        ///////////////////////////////////////////////////////////////////////////////////////////////////

        response.sendRedirect("studenterror");
    }
}
