package spider.service;

import spider.pojo.AnswerTemp;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface SpiderService {
    CompletableFuture<Set<AnswerTemp>> findAnswer(String question);
}
