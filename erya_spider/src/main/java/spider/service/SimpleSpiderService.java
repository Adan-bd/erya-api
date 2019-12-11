package spider.service;


import spider.pojo.AnswerTemp;

import java.util.Set;

public interface SimpleSpiderService {
    Set<AnswerTemp> findAnswer(String question);
}
