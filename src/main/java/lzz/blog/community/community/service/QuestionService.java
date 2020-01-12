package lzz.blog.community.community.service;

import lzz.blog.community.community.dto.PageutilDTO;
import lzz.blog.community.community.dto.QuestionDTO;
import lzz.blog.community.community.mapper.QuestionMapper;
import lzz.blog.community.community.mapper.UserMapper;
import lzz.blog.community.community.model.Question;
import lzz.blog.community.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PageutilDTO list(Integer page, Integer size) {
        PageutilDTO pageutilDTO = new PageutilDTO();
        Integer totalCount = questionMapper.count();
        pageutilDTO.setPageView(totalCount, page, size);
        //分页参数
        Integer offset = size * (pageutilDTO.getPage() - 1);
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageutilDTO.setQuestions(questionDTOList);
        return pageutilDTO;
    }
}
