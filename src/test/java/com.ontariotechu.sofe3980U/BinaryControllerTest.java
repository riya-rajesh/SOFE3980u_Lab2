package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {

    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }


    @Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	@Test
	    public void postParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("result"))
			.andExpect(model().attribute("result", "1110"))
			.andExpect(model().attribute("operand1", "111"));
    }

    //Three additional test cases for addition-------------------------------------

    //Test to check differing lengths of operands don't mess up the answer: 1010 + 11 = 1101

    @Test
    public void postAdd1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1010").param("operator","+")
                        .param("operand2","11"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1101"))
                .andExpect(model().attribute("operand1", "1010"));
    }


    //Test to check 111 + 1 = 1000 ensuring no bits are cut off from answer and return 000 for example
    @Test
    public void postAdd2() throws Exception {
        this.mvc.perform(post("/").param("operand1","111").param("operator","+")
                        .param("operand2","1"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1000"))
                .andExpect(model().attribute("operand1", "111"));
    }

    //Test to check 11 + 0 = 11 ensuring that the first bit being 0 of either operand doesn't get cut off and alter answer
    //and ensure adding 0 keeps result the same as operand 1. Result will have no leading 0s.
    @Test
    public void postAdd3() throws Exception {
        this.mvc.perform(post("/").param("operand1","11").param("operator","+")
                        .param("operand2","0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "11"))
                .andExpect(model().attribute("operand1", "11"));
    }


    //Tests for operands OR, AND, and Multiply-----------------------------------

    //Test for ORing with a 0: 1101 | 0 = 1101
    @Test
    public void postOr1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1101").param("operator","|")
                        .param("operand2","0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1101"))
                .andExpect(model().attribute("operand1", "1101"));
    }

    //Test for ORing with 1111: 1001 | 1111 = 1111
    @Test
    public void postOr2() throws Exception {
        this.mvc.perform(post("/").param("operand1","1001").param("operator","|")
                        .param("operand2","1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1111"))
                .andExpect(model().attribute("operand1", "1001"));
    }

    //Test for ANDing with a 0: 1101 & 0 = 0000
    @Test
    public void postAnd1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1101").param("operator","&")
                        .param("operand2","0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0"))
                .andExpect(model().attribute("operand1", "1101"));
    }

    //Test for ANDing with 1111: 1001 & 1111 = 1001
    @Test
    public void postAnd2() throws Exception {
        this.mvc.perform(post("/").param("operand1","1001").param("operator","&")
                        .param("operand2","1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "1001"))
                .andExpect(model().attribute("operand1", "1001"));
    }

    //Test for Multiplying with a 0: 1101 * 0 = 0
    @Test
    public void postMultiply1() throws Exception {
        this.mvc.perform(post("/").param("operand1","1101").param("operator","*")
                        .param("operand2","0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "0"))
                .andExpect(model().attribute("operand1", "1101"));
    }

    //Test for Multiplying with 1111: 1001 * 1111 = 10000111
    @Test
    public void postMultiply2() throws Exception {
        this.mvc.perform(post("/").param("operand1","1001").param("operator","*")
                        .param("operand2","1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("result"))
                .andExpect(model().attribute("result", "10000111"))
                .andExpect(model().attribute("operand1", "1001"));
    }


}