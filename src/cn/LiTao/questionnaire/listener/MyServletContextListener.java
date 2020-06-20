package cn.LiTao.questionnaire.listener;

import cn.LiTao.questionnaire.dependent.QuestionnaireContainer;
import cn.LiTao.questionnaire.pojo.Questionnaire;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        QuestionnaireContainer container = QuestionnaireContainer.getContainer(servletContext);

        servletContext.setAttribute("questionnaireContainer", container);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
