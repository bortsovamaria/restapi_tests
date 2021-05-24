package defpack.code.entity;//package code.entity;
//
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.IOException;
//
//public class Validator {
//
//    public static boolean isValid(String json) {
//        boolean retValue = false;
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
//            JsonParser parser = objectMapper.getFactory().createParser(json);
//            while (parser.nextToken() != null) {}
//            retValue = true;
//            objectMapper.readTree(json);
//        }catch(JsonParseException jpe) {
//            jpe.printStackTrace();
//        }
//        catch(IOException ioe) {
//
//        }
//        return retValue;
//    }
//}
