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
import java.util.List; 

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

    @RequestMapping(value = "/h2insert", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> h2InsertResponse(Model model, @RequestParam(name = "id") int idParam){
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        PersistenceLayer pers = new PersistenceLayer();
        pers.initSession();
        hfs.setMessage("id");
        hfs.setStuff(idParam);
        Integer respons;
        String respStr = new String();
        respons = pers.addStuff(hfs);
        try{
            ObjectMapper mapper = new ObjectMapper();
            respStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respons);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(respStr);
  
    }

    @RequestMapping(value = "/h2selectall", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> h2SelectAllResponse(Model model){
        ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        PersistenceLayer pers = new PersistenceLayer();
        pers.initSession();
        List respAll;
        String respStr = new String();
        respAll = pers.listStuff();
        try{
            ObjectMapper mapper = new ObjectMapper();
            respStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(respAll);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(respStr);
  
    }
    
    
}
