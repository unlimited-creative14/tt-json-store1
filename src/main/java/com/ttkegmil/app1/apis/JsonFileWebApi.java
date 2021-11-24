package com.ttkegmil.app1.apis;

import com.ttkegmil.app1.fs.JsonStore;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class JsonFileWebApi {
    @GetMapping("/read/{store}/{key}")
    public String readStore(@PathVariable String store, @PathVariable String key) throws IOException {
        JsonStore objStore = JsonStore.getStore(store);

        String out = objStore.getObjectMapper().writeValueAsString(objStore.read(key));
        return out;
    }

    @GetMapping("/enumerate/{store}")
    public String enumerateStore(@PathVariable String store) throws IOException {
        JsonStore objStore = JsonStore.getStore(store);

        String out = objStore.getObjectMapper().writeValueAsString(objStore.enumerate());
        return out;
    }

    @PostMapping("/write/{store}")
    public String writeStore(@PathVariable String store, @RequestBody Map<String, Object> body)
    {
        JsonStore storeObj = JsonStore.getStore(store);

        try{
            storeObj.writeBatch(body);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Write failed";
        }

        return "Done";
    }
}
