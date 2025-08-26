package chapter6.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chapter6.beans.User;
import chapter6.beans.UserComment;
import chapter6.beans.UserMessage;
import chapter6.service.CommentService;
import chapter6.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class TopServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        boolean isShowMessageForm = false;
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user != null) {
            isShowMessageForm = true;
        }

        //検索文字と文字列の初期値を受取る

        String searchWord = request.getParameter("word");
        String radiobutton = request.getParameter("radiobutton");
		String userId = request.getParameter("user_id");
		String start = request.getParameter("start");
        String end = request.getParameter("end");
        List<UserMessage> messages = new MessageService().select(userId, start, end, searchWord, radiobutton);

        //返信コメントを表示する
        List<UserComment> comments = new CommentService().select();

        request.setAttribute("start", start);
        request.setAttribute("end", end);
        //検索文字を画面残すためリクエスト領域にセット
        request.setAttribute("searchWord", request.getParameter("word"));
        request.setAttribute("messages", messages);
        request.setAttribute("comments", comments);
        request.setAttribute("searchWord", request.getParameter("word"));
        request.setAttribute("isShowMessageForm", isShowMessageForm);
        request.getRequestDispatcher("/top.jsp").forward(request, response);
    }
}