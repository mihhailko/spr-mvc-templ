package springexample;
import java.util.UUID;
import java.util.ArrayList;
import java.lang.System;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List; 

@Controller
public class YoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> helloResponse(Model model, @RequestParam(name = "id") int idParam){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        hfs.setMessage("id");
        hfs.setStuff(idParam);
        String response = "";
        context.close();
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


    @RequestMapping(value = "/hello-insert", method = RequestMethod.GET) //POST
    @ResponseBody
    public ResponseEntity<String> helloInsertResponse(Model model, @RequestParam(name = "id") String helloid){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        PersistenceLayer pers = new PersistenceLayer();
        String sid = UUID.randomUUID().toString();
        hfs.setMessage(sid);
        hfs.setStuff(Integer.parseInt(helloid));
        pers.initSession();
        List<IPersistence> testData;
        String respStr = new String();
        List<IPersistence> answer = new ArrayList<IPersistence>();
        Integer respons;
        respons = pers.insertWhatever(hfs);
        answer = pers.queryWhatever(hfs, "from HelloFromSpring as hs where hs.message = '" + sid + "'");
        

        context.close();
        
        pers.closeSessionFactory();
        
        try{
            ObjectMapper mapper = new ObjectMapper();
            respStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(respStr);
  
    }

    @RequestMapping(value = "/hello-list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> sessionListResponse(Model model){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        PersistenceLayer pers = new PersistenceLayer();
        pers.initSession();
        List<IPersistence> answer = new ArrayList<IPersistence>();
        String respStr = new String();
        answer = pers.queryWhatever(hfs, "from HelloFromSpring");

        context.close();
        
        pers.closeSessionFactory();
        try{
            ObjectMapper mapper = new ObjectMapper();
            respStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(answer);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
            .header("Content-Type", "application/json")
            .body(respStr);
  
    }

    @RequestMapping(value = "/hello-delete", method = RequestMethod.GET) //DELETE
    @ResponseBody
    public ResponseEntity<String> sessionListResponse(Model model, @RequestParam(name = "id") String id){
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
        HelloFromSpring hfs = (HelloFromSpring) context.getBean("helloFromSpring");
        PersistenceLayer pers = new PersistenceLayer();
        pers.initSession();
        String respAll;
        String respStr = new String();
        IPersistence o;
        o = pers.queryWhatever(hfs, new String("from HelloFromSpring as hs where hs.stuff = '" + id + "'")).get(0);
        respAll = pers.deleteWhatever(o);

        context.close();
        
        pers.closeSessionFactory();
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
