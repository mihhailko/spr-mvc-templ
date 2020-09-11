package springexample;

import org.springframework.http.ResponseEntity;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Controller
public class YoController {

    @RequestMapping(value = "/bench", method = RequestMethod.GET)
    @ResponseBody
    public String welcome(Model model) {
        return "yo";
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> helloResponse(Model model, @RequestParam(name = "id") int idParam){
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        hfs.setMessage("id");
        hfs.setStuff(idParam);
        String response = "";
        try{
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hfs);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(response);
  
    }
    
}
