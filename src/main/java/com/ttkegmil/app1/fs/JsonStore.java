package com.ttkegmil.app1.fs;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class JsonStore {
    private final File handle;
    private final ObjectMapper mapper;

    private static final Map<String, JsonStore> storage = new HashMap<>();
    private static final String PREFIX_PATH = "store";
    public static JsonStore getStore(String storeName)
    {
        if(!storage.containsKey(storeName))
        {
            storage.put(storeName, new JsonStore(PREFIX_PATH + "_" + storeName + ".json"));
        }

        return storage.get(storeName);
    }

    private JsonStore(String filename)
    {
        handle = new File(filename);
        mapper = new ObjectMapper();
        try
        {
            boolean file_new = handle.createNewFile();
            if(file_new)
            {
                // Write new empty object to newly created file
                FileWriter writer = new FileWriter(handle);
                writer.write("{}");
                writer.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Map<String, Object> enumerate() {
        HashMap<String, Object> data;

        try
        {
            data = mapper.readValue(handle, HashMap.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            data = new HashMap<>();
        }


        return data;
    }

    public Object read(String key) throws IOException
    {
        Map<String, Object> data = enumerate();
        if(!data.containsKey(key))
        {
            return null;
        }

        return data.get(key);
    }
    public void write(String key, Object value) throws IOException {
        Map<String, Object> data = enumerate();
        data.put(key, value);
        mapper.writeValue(handle, data);
    }

    public void writeBatch(Map<String, Object> entries) throws IOException
    {
        Map<String, Object> data = enumerate();
        data.putAll(entries);
        mapper.writeValue(handle, data);
    }

    public ObjectMapper getObjectMapper()
    {
        return mapper;
    }
}
