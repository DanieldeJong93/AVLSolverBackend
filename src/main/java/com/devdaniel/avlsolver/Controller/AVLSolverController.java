package com.devdaniel.avlsolver.Controller;

import com.devdaniel.avlsolver.Service.AVLService;
import com.devdaniel.avlsolver.Model.GivenAnswerModel;
import com.devdaniel.avlsolver.Model.NodeModel;
import com.devdaniel.avlsolver.Model.QuestionModel;
import com.devdaniel.avlsolver.Model.QuestionRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        return new AVLService().examine(questionRequest.getQuestionModel(), questionRequest.getGivenAnswerModel());
    }

    @CrossOrigin
    @RequestMapping(
            value = "/solve",
            method = RequestMethod.POST,
            produces =  {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public NodeModel solve (@RequestBody QuestionModel questionModel) {
        return new AVLService().solve(questionModel);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
}
