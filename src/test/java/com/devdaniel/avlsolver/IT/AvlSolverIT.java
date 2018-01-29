package com.devdaniel.avlsolver.IT;


import com.devdaniel.avlsolver.Controller.AVLSolverController;
import com.devdaniel.avlsolver.Controller.AVLSolverControllerTest;
import com.devdaniel.avlsolver.Handler.AutomaticExaminationHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.filters.CorsFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AvlSolverIT {
    private MockMvc mockMvc;

    private static final String JSON_BAD_STRING = "{\"steps\":[{\"givenStep\":{\"value\":1,\"leftChild\":null,\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}},\"correctStep\":1,\"correctAnswer\":{\"value\":1,\"leftChild\":null,\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}}},{\"givenStep\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}},\"correctStep\":1,\"correctAnswer\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}}},{\"givenStep\":{\"value\":2,\"leftChild\":{\"value\":3,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":null}},\"correctStep\":-1,\"correctAnswer\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":{\"value\":3,\"leftChild\":null,\"rightChild\":null},\"rightChild\":null}}}]}";
    private static final String JSON_GOOD_STRING = "{\"steps\":[{\"givenStep\":{\"value\":1,\"leftChild\":null,\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}},\"correctStep\":1,\"correctAnswer\":{\"value\":1,\"leftChild\":null,\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}}},{\"givenStep\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}},\"correctStep\":1,\"correctAnswer\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":null,\"rightChild\":null}}},{\"givenStep\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":{\"value\":3,\"leftChild\":null,\"rightChild\":null},\"rightChild\":null}},\"correctStep\":1,\"correctAnswer\":{\"value\":2,\"leftChild\":{\"value\":1,\"leftChild\":null,\"rightChild\":null},\"rightChild\":{\"value\":5,\"leftChild\":{\"value\":3,\"leftChild\":null,\"rightChild\":null},\"rightChild\":null}}}]}";

    @InjectMocks
    private AVLSolverController avlSolverController;

    @Before
    public void setup () {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(avlSolverController).addFilter(new CorsFilter()).build();
    }

    @Test
    public void testGetModel () throws Exception {
        mockMvc.perform(
                get("/avl/node/model"))
                .andExpect(jsonPath("value", is(0)))
                .andExpect(jsonPath("leftChild.value", is(0)))
                .andExpect(jsonPath("leftChild.leftChild", nullValue()))
                .andExpect(jsonPath("leftChild.rightChild", nullValue()))

                .andExpect(jsonPath("rightChild.value", is(0)))
                .andExpect(jsonPath("rightChild.leftChild", nullValue()))
                .andExpect(jsonPath("rightChild.rightChild", nullValue()));
    }

    @Test
    public void testSolveAVLTreeWithoutMistake () throws Exception {
        String jsonString = asJsonString(AVLSolverControllerTest.prepareGoodQuestion());

        MvcResult result = mockMvc.perform(
                post("/avl/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
                )
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(JSON_GOOD_STRING, result.getResponse().getContentAsString());
    }

    @Test
    public void testSolveAVLTreeWithMistake () throws Exception {
        String jsonString = asJsonString(AVLSolverControllerTest.prepareBadQuestion());

        MvcResult result = mockMvc.perform(
                post("/avl/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString)
        )
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals(JSON_BAD_STRING, result.getResponse().getContentAsString());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
