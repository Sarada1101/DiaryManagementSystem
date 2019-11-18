package diary.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * コメント登録完了画面へ遷移するServletクラス
 *
 * @author ryouta
 *
 */
@WebServlet("/commentinsertcomplete")
public class CommentInsertCompleteServlet extends HttpServlet {

    /**
     * コメント登録完了画面へ遷移する
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        System.out.println("CommentInsertCompleteServlet"); //test

        request.getRequestDispatcher("WEB-INF/jsp/commentInsertComplete.jsp").forward(request, response);
    }
}
