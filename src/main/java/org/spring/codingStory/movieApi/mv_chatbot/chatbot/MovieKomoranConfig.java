package org.spring.codingStory.movieApi.mv_chatbot.chatbot;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.spring.codingStory.movieApi.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

//  사용자 사전 -> user.dic 에 값을 자동으로 저장하는 클래스
//  구문 내에서 사용자 사전에 포함된 단어가 출현하면 사용자 사전에 정의된 품사를 우선 적용
//  주로 사람이름, 영화제목, 부서 등과 같이 고유명사를 인식하는데 활용

//  //KOMORAN에서 기본으로 제공되는 LIGHT 모델 사용
//  Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);

////사용자 사전 적용. 원하는 위치에 사용자 사전 파일을 생성한 후 경로만 지정해주면 됩니다.
//komoran.setUserDic("user_data/dic.user")


@Configuration
public class MovieKomoranConfig {
    private static final String USER_DIC = "movie.dic";

    @Autowired
    private MovieRepository movieRepository;

    @Bean
    public Komoran komoran() {
        updateUserDic();
        Komoran komoran = new Komoran(DEFAULT_MODEL.LIGHT);
        komoran.setUserDic(USER_DIC);
        return komoran;
    }

    private void updateUserDic() {
        Set<String> keys = new HashSet<>();
        readExistingUserDic(keys);
        addMovieTitlesToUserDic(keys);
        writeUserDic(keys);
    }

    private void readExistingUserDic(Set<String> keys) {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_DIC))) {
            String data;
            while ((data = br.readLine()) != null) {
                if (!data.startsWith("#")) {
                    String[] str = data.split("\\t");
                    keys.add(str[0]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMovieTitlesToUserDic(Set<String> keys) {
        movieRepository.findAll().forEach(e -> {
            keys.add(e.getMovieNm());
            keys.add(e.getMovieCd());
            keys.add(e.getAudiAcc());
            keys.add(String.valueOf(e.getMovieRank()));

        });
    }

    private void writeUserDic(Set<String> keys) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_DIC))) {
            for (String key : keys) {
                bw.write(key + "\tNNP\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}