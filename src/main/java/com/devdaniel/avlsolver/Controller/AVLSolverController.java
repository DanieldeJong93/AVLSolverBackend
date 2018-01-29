package com.devdaniel.avlsolver.Controller;

import com.devdaniel.avlsolver.Handler.AutomaticExaminationHandler;
import com.devdaniel.avlsolver.Model.GivenAnswerModel;
import com.devdaniel.avlsolver.Model.NodeModel;
import com.devdaniel.avlsolver.Model.QuestionModel;
import com.devdaniel.avlsolver.Model.QuestionRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/avl")
@RestController
public class AVLSolverController {

    @RequestMapping(
            value = "/node/model",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public NodeModel nodeModel () {
        return new NodeModel(0, new NodeModel(), new NodeModel());
    }

    @RequestMapping(
            value = "/examine",
            method = RequestMethod.POST,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public GivenAnswerModel examine (@RequestBody QuestionRequest questionRequest) {
        return new AutomaticExaminationHandler().examine(questionRequest.getQuestionModel(), questionRequest.getGivenAnswerModel());
    }
}
