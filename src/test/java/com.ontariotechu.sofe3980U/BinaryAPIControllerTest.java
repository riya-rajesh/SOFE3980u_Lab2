package com.ontariotechu.sofe3980U;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
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
@WebMvcTest(BinaryAPIController.class)
public class BinaryAPIControllerTest {

    @Autowired
    private MockMvc mvc;


    @Test
    public void add() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "111").param("operand2", "1010"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("10001"));
    }

    @Test
    public void add2() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param("operand2", "1010"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }


    //Three additional test cases for addition-------------------------------------


    //Tests to check differing lengths of operands don't mess up the answer: 1010 + 11 = 1101
    @Test
    public void add3() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "1010").param
                        ("operand2", "11"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1101"));
    }

    @Test
    public void add4() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "1010").param
                        ("operand2", "11"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1010))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }


    //Tests to check 111 + 1 = 1000 ensuring no bits are cut off from answer and return 000 for example
    @Test
    public void add5() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "111").param
                        ("operand2", "1"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1000"));
    }

    @Test
    public void add6() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "111").param
                        ("operand2", "1"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }


    //Tests to check 11 + 0 = 11 ensuring that the first bit being 0 of either operand doesn't get cut off and alter answer
    //and ensure adding 0 keeps result the same as operand 1. Result will have no leading 0s.
    @Test
    public void add7() throws Exception {
        this.mvc.perform(get("/add").param("operand1", "011").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("11"));
    }

    @Test
    public void add8() throws Exception {
        this.mvc.perform(get("/add_json").param("operand1", "11").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(11))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("add"));
    }


    //Tests for operands OR, AND, and Multiply-----------------------------------

    //Tests for ORing with a 0: 1101 | 0 = 1101
    @Test
    public void or1() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1101"));
    }

    @Test
    public void or2() throws Exception {
        this.mvc.perform(get("/or_json").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
    }

    //Test for ORing with 1111: 1001 | 1111 = 1111
    @Test
    public void or3() throws Exception {
        this.mvc.perform(get("/or").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1111"));
    }

    @Test
    public void or4() throws Exception {
        this.mvc.perform(get("/or_json").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("or"));
    }

    //Test for ANDing with a 0: 1101 & 0 = 0000
    @Test
    public void and1() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void and2() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
    }

    //Test for ANDing with 1111: 1001 & 1111 = 1001
    @Test
    public void and3() throws Exception {
        this.mvc.perform(get("/and").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1001"));
    }

    @Test
    public void and4() throws Exception {
        this.mvc.perform(get("/and_json").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(1001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("and"));
    }


    //Test for Multiplying with a 0: 1101 * 0 = 0
    @Test
    public void multiply1() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    public void multiply2() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "1101").param
                        ("operand2", "0"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1101))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

    //Test for Multiplying with 1111: 1001 * 1111 = 10000111
    @Test
    public void multiply3() throws Exception {
        this.mvc.perform(get("/multiply").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("10000111"));
    }

    @Test
    public void multiply4() throws Exception {
        this.mvc.perform(get("/multiply_json").param("operand1", "1001").param
                        ("operand2", "1111"))//.andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand1").value(1001))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operand2").value(1111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(10000111))
                .andExpect(MockMvcResultMatchers.jsonPath("$.operator").value("multiply"));
    }

}