package com.example.demo;

import com.example.demo.db.DB;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "Synonym-Dictionary", value = "/dictionary-servlet")
public class DictionaryServlet extends HttpServlet {
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        List<String> keysAsArray = new ArrayList<>(DB.dictionary.keySet());
        Random r = new Random();
        String key = keysAsArray.get(r.nextInt(keysAsArray.size()));
        String value = DB.dictionary.get(key);
        resp.getWriter().printf("<h3>Synonyms of the day: %s ---> %s </h3>", key, value);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String key = req.getParameter("key");
        String value = req.getParameter("value");
        resp.setContentType("text/html");
        DB.dictionary.put(key, value);
        resp.getWriter().printf("<h3>You have sent us synonyms: %s ---> %s</h3>", key, value);
    }

    public void doGetDictionary(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        var writer = resp.getWriter();
        writer.println("<h1>Dictionary</h1>");
        DB.dictionary.forEach((key, value) -> writer.printf("\n%s ---> %s", key, value));
    }

    public void doSynonym(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String word = req.getParameter("word");
        resp.setContentType("text/html");
        if (DB.dictionary.containsKey(word)){
            resp.getWriter().printf("<h3>Synonym for %s ---> %s</h3>", word, DB.dictionary.get(word));
        }else
            resp.getWriter().printf("<h3>There is no synonym for %s</h3>", word);
    }



    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        System.out.println(method);
        switch (method) {
            case "GET":
                this.doGet(req, resp);
                break;
            case "POST":
                this.doPost(req, resp);
                break;
            case "DICTIONARY":
                this.doGetDictionary(req, resp);
                break;
            case "SYNONYM":
                this.doSynonym(req, resp);
                break;
            default : {
                String errMsg = lStrings.getString("http.method_not_implemented");
                Object[] errArgs = new Object[]{method};
                errMsg = MessageFormat.format(errMsg, errArgs);
                resp.sendError(501, errMsg);
            }
        }
    }
}
