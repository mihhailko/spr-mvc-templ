import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.ResultMatcher;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration
(
  {
   "file:src/main/resources/Beans.xml",
   "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
  }
)
public class TestYoControllerTest {

    
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }


    @Test
    public void testUserController () throws Exception {

        int testValue = 0;
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        ResultMatcher contentJson = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);
        ResultMatcher expected = MockMvcResultMatchers.jsonPath("message").value("id");
        ResultMatcher expected2 = MockMvcResultMatchers.jsonPath("stuff").value(testValue);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(new String("/hello?id=" + testValue));
        this.mockMvc.perform(builder)
            .andExpect(ok)
            .andExpect(contentJson)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(expected)
            .andExpect(expected2);
    
    }

    @Test
    public void test2UserController () throws Exception {

        int testValue2 = 23456;
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();

        ResultMatcher expected3 = MockMvcResultMatchers.jsonPath("message").value("id");
        
        ResultMatcher expected4 = MockMvcResultMatchers.jsonPath("stuff").value(testValue2);

        ResultMatcher contentJson = MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(new String("/hello?id=" + testValue2));
        this.mockMvc.perform(builder)
            .andExpect(ok)
            .andExpect(contentJson)
            .andDo(MockMvcResultHandlers.print())
            .andExpect(expected3)
            .andExpect(expected4);
    
    }
}
